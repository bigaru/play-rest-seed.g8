package controllers

import cats.implicits._
import cats.data.EitherT
import models.Book
import models.daos.BookRepository
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

class BookController(bookRepo: BookRepository, cc: ControllerComponents)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  private def validateBody(body: JsValue): Future[Either[(Int, String), Book]] = Future {
    body.asOpt[Book] match {
      case Some(validBook) => Right(validBook)
      case _               => Left((BAD_REQUEST, "invalid book format"))
    }
  }

  def getAll = Action.async {
    bookRepo.getAll.map { books =>
      Ok(Json.toJson(books))
    }
  }

  def getOne(isbn: String) = Action.async {
    bookRepo.getOne(isbn).map {
      case Right(book)         => Ok(Json.toJson(book))
      case Left((status, msg)) => new Status(status)(msg)
    }
  }

  def createOne = Action.async(parse.json) { req =>
    val result = for (validBook <- EitherT(validateBody(req.body));
                      insertedBook <- EitherT(bookRepo.addOne(validBook))) yield insertedBook

    result.value.map {
      case Right(book)         => Created(Json.toJson(book))
      case Left((status, msg)) => new Status(status)(msg)
    }
  }

  def update(isbn: String) = Action.async(parse.json) { req =>
    val result = for (validBook <- EitherT(validateBody(req.body));
                      updatedBook <- EitherT(bookRepo.updateOne(isbn, validBook))) yield updatedBook

    result.value.map {
      case Right(book)         => Ok(Json.toJson(book))
      case Left((status, msg)) => new Status(status)(msg)
    }
  }

  def delete(isbn: String) = Action.async {
    bookRepo.deleteOne(isbn).map {
      case Right(book)         => Ok(Json.toJson(book))
      case Left((status, msg)) => new Status(status)(msg)
    }
  }
}
