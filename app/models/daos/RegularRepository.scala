package models.daos

import models.DbItem
import reactivemongo.api.commands.MultiBulkWriteResult
import reactivemongo.bson.{BSONDocument, BSONDocumentWriter}
import services.MongoService

import scala.concurrent.{ExecutionContext, Future}

class RegularRepository[T <: DbItem, SELECTOR]()(implicit ec: ExecutionContext, mongoService: MongoService[T], bsonSelectorWriter: BSONDocumentWriter[SELECTOR]){

  def getAll: Future[Seq[T]] =
    mongoService.getMany()

  def getOne(id: SELECTOR): Future[Option[T]] =
    mongoService.getOne(BSONDocument("_id" -> id))

  def addOne(item: T): Future[Option[T]] =
    mongoService.addOne(item)

  def addMany(items: Seq[T]): Future[MultiBulkWriteResult] =
    mongoService.addMany(items)

  def updateOne(id: SELECTOR, newOne: T): Future[Option[T]] =
    mongoService.updateOne(BSONDocument("_id" -> id), newOne.updateModifier)

  def deleteOne(id: SELECTOR): Future[Option[T]] =
    mongoService.deleteOne(BSONDocument("_id" -> id))

}
