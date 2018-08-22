package controllers

import connectors.TwitchConnector
import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AuthController @Inject()(cc: ControllerComponents,
                               twitchConnector: TwitchConnector)
                              (implicit ec: ExecutionContext)
  extends AbstractController(cc) {

  def redirectUri(code: Option[String]): Action[AnyContent] = Action.async {
    code match {
      case Some(c) =>
        twitchConnector.requestTokens(c) map { response =>
          Ok(response.json)
        }
      case None => Future.successful(BadRequest("something isn't right!"))
    }
  }

}
