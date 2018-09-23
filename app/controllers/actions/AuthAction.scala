package controllers.actions

import javax.inject.Inject
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

class AuthAction @Inject()(parser: BodyParsers.Default)
                          (implicit ec: ExecutionContext)
  extends ActionBuilderImpl(parser) {

  override def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]): Future[Result] = {
    request.session.get("access_token") map { token =>
      block(request)
    } getOrElse {
      Future.successful(Results.Redirect(controllers.routes.HomeController.view()))
    }
  }

}
