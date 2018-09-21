package connectors

import config.AppConfig
import javax.inject.{Inject, Singleton}
import models.{ClientSideError, HttpError, ServerSideError, TwitchTokenResponse}
import play.api.libs.ws.{WSClient, WSResponse}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TwitchConnector @Inject()(ws: WSClient, appConfig: AppConfig) {

  private def queryParamsWithCode(code: String) = Seq(
    "client_id" -> appConfig.clientId,
    "client_secret" -> appConfig.clientSecret,
    "grant_type" -> "authorization_code",
    "redirect_uri" -> "http://localhost:9000/redirect-uri",
    "code" -> code
  )

  def requestTokens(code: String)(implicit ec: ExecutionContext): Future[Either[HttpError, TwitchTokenResponse]] = {
    ws.url("https://id.twitch.tv/oauth2/token")
      .withQueryStringParameters(queryParamsWithCode(code):_*)
      .execute("POST")
      .map { response =>
        if(response.status >= 400 && response.status <= 499) {
          Left(ClientSideError)
        } else if(response.status >= 500 && response.status <= 599) {
          Left(ServerSideError)
        } else {
          Right(response.json.as[TwitchTokenResponse])
        }
      }
  }

}
