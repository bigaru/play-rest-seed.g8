package controllers

import models.Book
import models.daos.BookRepository
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

class HomeController(bookRepo: BookRepository, cc: ControllerComponents)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def index() = Action.async{
    Future.successful(Ok("Meow"))
  }

  def getAll = Action.async{
    bookRepo.getAll.map{books =>
      Ok( Json.toJson(books) )
    }
  }

  def getOne(isbn: String) = Action.async{
    bookRepo.getOne(isbn).map{
      case Some(book) => Ok( Json.toJson(book) )
      case _ => NotFound("not found")
    }
  }

  def createOne = Action.async(parse.json){ req =>
    val body = req.body.asOpt[Book]
    body match {
      case Some(newOne) =>
        bookRepo.addOne(newOne).map {
          case Some(book) => Ok(Json.toJson(book))
          case _ => InternalServerError("failed to insert")
        }

      case _ => Future.successful(
        BadRequest("invalid body format")
      )
    }
  }

  def update(isbn: String) = Action.async(parse.json){ req =>
    val body = req.body.asOpt[Book]
    body match {
      case Some(newOne) =>
        bookRepo.updateOne(isbn, newOne).map {
          case Some(book) => Ok(Json.toJson(book))
          case _ => InternalServerError("failed to update")
        }

      case _ => Future.successful(
        BadRequest("invalid body format")
      )
    }
  }

  def delete(isbn: String) = Action.async{
    bookRepo.deleteOne(isbn).map{
      case Some(book) => Ok( Json.toJson(book) )
      case _ => InternalServerError("failed to delete")
    }
  }
}
