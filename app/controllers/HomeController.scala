package controllers

import models.daos.ItemRepository
import play.api.libs.json.Json
import play.api.mvc._
import reactivemongo.bson.BSONObjectID

import scala.concurrent.ExecutionContext

class HomeController (itemRepo: ItemRepository, cc: ControllerComponents)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def index() = Action.async{
    itemRepo.getOne(BSONObjectID.parse("5c27aae554dfebb17f400e09").get)
            .map(item => Ok(Json.toJson(item)))
  }
}
