package connectors

import config.AppConfig
import javax.inject.{Inject, Singleton}
import models.{ClientSideError, HttpError, ServerSideError, TtsForm}
import play.api.libs.json.Json
import play.api.libs.ws.WSClient

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ApiGatewayConnector @Inject()(ws: WSClient, appConfig: AppConfig) {

  def postTtsData(data: TtsForm)(implicit ec: ExecutionContext): Future[Either[HttpError, Unit]] = {
    ws.url(appConfig.gatewayUrl)
      .withHttpHeaders("x-api-key" -> appConfig.gatewayKey)
      .post(Json.toJson(data))
      .map { response =>
        if(response.status >= 400 && response.status <= 499) {
          Left(ClientSideError)
        } else if(response.status >= 500 && response.status <= 599) {
          Left(ServerSideError)
        } else {
          Right(():Unit)
        }
      }
  }

}
