package controllers

import config.AppConfig
import javax.inject.Inject
import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import scala.concurrent.ExecutionContext

class HomeController @Inject()(cc: ControllerComponents)
                              (implicit appConfig: AppConfig, ec: ExecutionContext)
  extends AbstractController(cc) with I18nSupport {

  def view: Action[AnyContent] = Action { implicit req =>
    Ok(views.html.index())
  }

}
