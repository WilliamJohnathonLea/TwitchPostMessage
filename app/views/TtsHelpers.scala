package views

object TtsHelpers {

  import views.html.helper.FieldConstructor
  implicit val fieldConstructor: FieldConstructor = FieldConstructor(views.html.ttsFieldConstructor.f)

}
