package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.AuthService

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AuthController @Inject()(cc: ControllerComponents,
                               authService: AuthService)
                              (implicit ec: ExecutionContext)
  extends AbstractController(cc) {

  def redirectUri(code: Option[String]): Action[AnyContent] = Action.async { implicit req =>
    code match {
      case Some(c) =>
        authService.createAuthSession(c).value map {
          case Right(t) =>
            Redirect(routes.HomeController.view())
              .addingToSession(
                "access_token" -> t.accessToken,
                "username" -> t.preferredUsername
              )
          case Left(_) => Redirect(routes.HomeController.view())
        }
      case None => Future.successful(BadRequest("No code was received!"))
    }
  }

}
