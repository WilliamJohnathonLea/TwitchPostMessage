package models

import play.api.data._
import play.api.data.Forms._

case class TtsForm(text: String, voice: String)

object TtsForm {

  val form = Form(
    mapping(
      "text" -> nonEmptyText,
      "voice" -> nonEmptyText
    )(TtsForm.apply)(TtsForm.unapply)
  )

}
