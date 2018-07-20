package connectors

import config.AppConfig
import javax.inject.{Inject, Singleton}
import models.{ClientError, HttpError, ServerError, TtsForm}
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
        println(response.body)
        if(response.status >= 400 && response.status <= 499) {
          Left(ClientError)
        } else if(response.status >= 500 && response.status <= 599) {
          Left(ServerError)
        } else {
          Right(():Unit)
        }
      }
  }

}
