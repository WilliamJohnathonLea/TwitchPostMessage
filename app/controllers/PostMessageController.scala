package controllers

import config.AppConfig
import connectors.{ApiGatewayConnector, S3Connector, SqsConnector}
import javax.inject.{Inject, Singleton}
import models.{ApiResponse, TtsForm}
import play.api.i18n.I18nSupport
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PostMessageController @Inject()(cc: ControllerComponents,
                                      gatewayConnector: ApiGatewayConnector,
                                      sqsConnector: SqsConnector,
                                      s3Connector: S3Connector)
                                     (implicit appConfig: AppConfig, ec: ExecutionContext)
  extends AbstractController(cc) with I18nSupport {

  lazy val voices = Seq(
    "Emma" -> "Emma [English - British]",
    "Brian" -> "Brian [English - British]",
    "Joanna" -> "Joanna [English - American]",
    "Justin" -> "Justin [English - American]"
  )

  def view: Action[AnyContent] = Action { implicit req =>
    Ok(views.html.post_message(TtsForm.form, voices))
  }

  def postMessage: Action[AnyContent] = Action.async { implicit req =>
    TtsForm.form.bindFromRequest().fold(
      formWithErrors => Future.successful(BadRequest(views.html.post_message(formWithErrors, voices))),
      ttsForm => {
        gatewayConnector.postTtsData(ttsForm).map {
          case Right(_) => Redirect(routes.PostMessageController.view())
            .flashing("success" -> "tts.post.success")
          case Left(_) => Redirect(routes.PostMessageController.view())
            .flashing("error" -> "tts.post.error")
        }
      }
    )
  }

  def getNextAudioFile: Action[AnyContent] = Action.async {
    sqsConnector.getMessage.flatMap {
      case Some(message) =>
        sqsConnector.removeMessage(message.getReceiptHandle).map { _ =>
          Ok(Json.toJson(ApiResponse(OK, message.getBody)))
        }
      case None => Future.successful(NoContent)
    }
  }

  def downloadAudioFile(name: String): Action[AnyContent] = Action {
    Ok.chunked(s3Connector.downloadFile(name))
  }

}
