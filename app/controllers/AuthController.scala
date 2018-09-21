package controllers

import javax.inject.{Inject, Singleton}
import models.{ClientSideError, ServerSideError}
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.AuthService

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AuthController @Inject()(cc: ControllerComponents,
                               authService: AuthService)
                              (implicit ec: ExecutionContext)
  extends AbstractController(cc) {

  def redirectUri(code: Option[String]): Action[AnyContent] = Action.async {
    code match {
      case Some(c) =>
        authService.requestTokens(c).value map {
          case Right(t) => Ok(Json.toJson(t))
          case Left(ClientSideError) => BadRequest("Client issue")
          case Left(ServerSideError) => InternalServerError("Server issue")
        }
      case None => Future.successful(BadRequest("No code was received!"))
    }
  }

}
