package controllers

import connectors.{ApiGatewayConnector, SqsConnector}
import javax.inject.{Inject, Singleton}
import models.{ApiResponse, TtsForm}
import play.api.i18n.I18nSupport
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AppController @Inject()(cc: ControllerComponents,
                              gatewayConnector: ApiGatewayConnector,
                              sqsConnector: SqsConnector)
                             (implicit ec: ExecutionContext)
  extends AbstractController(cc) with I18nSupport {

  lazy val voices = Seq(
    "Emma" -> "Emma [English - British]",
    "Brian" -> "Brian [English - British]",
    "Joanna" -> "Joanna [English - American]",
    "Justin" -> "Justin [English - American]"
  )

  def home: Action[AnyContent] = Action { implicit req =>
    Ok(views.html.index(TtsForm.form, voices))
  }

  def postMessage: Action[AnyContent] = Action.async { implicit req =>
    TtsForm.form.bindFromRequest().fold(
      formWithErrors => Future.successful(BadRequest(views.html.index(formWithErrors, voices))),
      ttsForm => {
        gatewayConnector.postTtsData(ttsForm).map {
          case Right(_) => Redirect(routes.AppController.home())
            .flashing("success" -> "Message posted successfully.")
          case Left(_) => Redirect(routes.AppController.home())
            .flashing("error" -> "There was an error, please try again.")
        }
      }
    )
  }

  def getAudioFile: Action[AnyContent] = Action.async {
    sqsConnector.getMessage.flatMap {
      case Some(message) =>
        sqsConnector.removeMessage(message.getReceiptHandle).map { _ =>
          Ok(Json.toJson(ApiResponse(OK, message.getBody)))
        }
      case None => Future.successful(NoContent)
    }
  }

}
