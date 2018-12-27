package services

import reactivemongo.api.commands.{MultiBulkWriteResult, UpdateWriteResult}
import reactivemongo.bson.BSONDocument

import scala.concurrent.Future

trait MongoService[T]{
  def getOne(query: BSONDocument): Future[Option[T]]

  def getOne(query: BSONDocument, sort: BSONDocument): Future[Option[T]]

  def getMany(query: BSONDocument): Future[Seq[T]]

  def addOne(newItem: T): Future[Option[T]]

  def addMany(items: Seq[T]): Future[MultiBulkWriteResult]

  def updateOne(selector: BSONDocument, updateModifier: BSONDocument): Future[Option[T]]

  def updateMany(selector: BSONDocument, updateModifier: BSONDocument): Future[UpdateWriteResult]

  def delete(selector: BSONDocument): Future[Option[T]]
}