package controllers

import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

class HomeController(cc: ControllerComponents)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def index() = Action.async {
    Future.successful(
      Redirect("/swagger/index.html", Map("url" -> Seq("/assets/swagger.json")))
    )
  }

}
