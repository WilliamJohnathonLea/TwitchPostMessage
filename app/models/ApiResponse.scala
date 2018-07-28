package models

import play.api.libs.json.{Format, Json}

case class ApiResponse(code: Int, message: String)

object ApiResponse {

  implicit val format: Format[ApiResponse] = Json.format[ApiResponse]

}
