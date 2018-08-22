package connectors

import config.AppConfig
import javax.inject.{Inject, Singleton}
import play.api.libs.ws.{WSClient, WSResponse}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TwitchConnector @Inject()(ws: WSClient, appConfig: AppConfig) {

  def requestTokens(code: String)(implicit ec: ExecutionContext): Future[WSResponse] = {
    ws.url("https://id.twitch.tv/oauth2/token")
      .withQueryStringParameters(
        "client_id" -> appConfig.clientId,
        "client_secret" -> appConfig.clientSecret,
        "grant_type" -> "authorization_code",
        "redirect_uri" -> "http://localhost:9000/redirect-uri",
        "code" -> code
      )
      .execute("POST")
  }

}
