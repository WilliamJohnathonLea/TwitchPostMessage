package controllers

import javax.inject.{Inject, Singleton}
import models.TtsForm
import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

@Singleton
class AppController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with I18nSupport {

  def home: Action[AnyContent] = Action { implicit req =>
    Ok(views.html.index(TtsForm.form))
  }

  def postMessage: Action[AnyContent] = Action { implicit req =>
    TtsForm.form.bindFromRequest().fold(
      formWithErrors => BadRequest(views.html.index(formWithErrors)),
      _ => Redirect(routes.AppController.home())
    )
  }

}
