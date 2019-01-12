package controllers

import models.daos.BookRepository
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

class HomeController(itemRepo: BookRepository, cc: ControllerComponents)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def index() = Action.async{
    Future.successful(Ok("Meow"))
  }
}
