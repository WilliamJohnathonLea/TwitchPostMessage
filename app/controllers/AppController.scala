package controllers

import javax.inject.{Inject, Singleton}
import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

@Singleton
class AppController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with I18nSupport {

  def home: Action[AnyContent] = Action {
    Ok
  }

}
