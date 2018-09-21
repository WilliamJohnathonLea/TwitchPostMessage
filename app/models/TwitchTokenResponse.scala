package models

import play.api.libs.json.{Format, Json}

case class TwitchTokenResponse(access_token: String,
                               refresh_token: String,
                               expires_in: Long,
                               scope: Seq[String],
                               id_token: String,
                               token_type: String)

object TwitchTokenResponse {

  implicit val format: Format[TwitchTokenResponse] = Json.format[TwitchTokenResponse]

}
