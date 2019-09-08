package controllers

import cats.data.EitherT
import cats.implicits._
import models.BookStaple
import daos.StapleRepository
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import reactivemongo.bson.BSONObjectID
import scala.concurrent.{ExecutionContext, Future}

class StapleController(stapleRepo: StapleRepository, cc: ControllerComponents)(implicit ec: ExecutionContext)
    extends AbstractController(cc) {

  private def validateBody(body: JsValue): Future[Either[(Int, String), BookStaple]] = Future {
    body.asOpt[BookStaple] match {
      case Some(validStaple) => Right(validStaple)
      case _                 => Left((BAD_REQUEST, "invalid book staple format"))
    }
  }

  def getAll = Action.async {
    stapleRepo.getAll.map { staples =>
      Ok(Json.toJson(staples))
    }
  }

  def getOne(id: BSONObjectID) = Action.async {
    stapleRepo.getOne(id).map {
      case Right(book)         => Ok(Json.toJson(book))
      case Left((status, msg)) => new Status(status)(msg)
    }
  }

  def createOne = Action.async(parse.json) { req =>
    val result = for (validStaple <- EitherT(validateBody(req.body));
                      insertedStaple <- EitherT(stapleRepo.addOne(validStaple))) yield insertedStaple

    result.value.map {
      case Right(staple)       => Created(Json.toJson(staple))
      case Left((status, msg)) => new Status(status)(msg)
    }
  }

  def update(id: BSONObjectID) = Action.async(parse.json) { req =>
    val result = for (validStaple <- EitherT(validateBody(req.body));
                      updatedStaple <- EitherT(stapleRepo.updateOne(id, validStaple))) yield updatedStaple

    result.value.map {
      case Right(staple)       => Ok(Json.toJson(staple))
      case Left((status, msg)) => new Status(status)(msg)
    }
  }

  def delete(id: BSONObjectID) = Action.async {
    stapleRepo.deleteOne(id).map {
      case Right(staple)       => Ok(Json.toJson(staple))
      case Left((status, msg)) => new Status(status)(msg)
    }
  }
}
