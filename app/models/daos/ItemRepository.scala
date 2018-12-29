package models.daos

import models.Item
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import services.MongoService

import scala.concurrent.{ExecutionContext, Future}

class ItemRepository()(implicit ec: ExecutionContext, mongoService: MongoService[Item]) extends BaseRepository[Item]{

  def updateOne(id: BSONObjectID, newOne: Item): Future[Option[Item]] =
    mongoService.updateOne(BSONDocument("_id" -> id), newOne.updateModifier())

}
