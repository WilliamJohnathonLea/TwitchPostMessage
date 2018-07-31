package models

import play.api.data._
import play.api.data.Forms._
import play.api.libs.json.{Json, Format}

case class TtsForm(text: String, voice: String)

object TtsForm {

  implicit val format: Format[TtsForm] = Json.format[TtsForm]

  val form = Form(
    mapping(
      "tts.text" -> nonEmptyText,
      "tts.voice" -> nonEmptyText
    )(TtsForm.apply)(TtsForm.unapply)
  )

}
