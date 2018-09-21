package controllers

import com.auth0.jwt.JWT
import connectors.TwitchConnector
import javax.inject.{Inject, Singleton}
import models.TwitchTokenResponse
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import scala.collection.JavaConverters.iterableAsScalaIterable
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

@Singleton
class AuthController @Inject()(cc: ControllerComponents,
                               twitchConnector: TwitchConnector)
                              (implicit ec: ExecutionContext)
  extends AbstractController(cc) {

  def redirectUri(code: Option[String]): Action[AnyContent] = Action.async {
    code match {
      case Some(c) =>
        twitchConnector.requestTokens(c) map { response =>
          val tokens = response.json.as[TwitchTokenResponse]
          Try(JWT.decode(tokens.id_token)) foreach { t =>
            iterableAsScalaIterable(t.getAudience).toSeq foreach println
          }
          Ok(response.json)
        }
      case None => Future.successful(BadRequest("something isn't right!"))
    }
  }

}
