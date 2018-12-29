package services

import reactivemongo.api.commands.{MultiBulkWriteResult, UpdateWriteResult}
import reactivemongo.bson.BSONDocument

import scala.concurrent.Future

trait MongoService[T]{
  def getOne(query: BSONDocument, sort: BSONDocument = BSONDocument()): Future[Option[T]]

  def getMany(query:BSONDocument = BSONDocument(), sort: BSONDocument = BSONDocument(), countDocs: Int = -1): Future[Seq[T]]

  def addOne(newItem: T): Future[Option[T]]

  def addMany(items: Seq[T]): Future[MultiBulkWriteResult]

  def updateOne(selector: BSONDocument, updateModifier: BSONDocument): Future[Option[T]]

  def updateMany(selector: BSONDocument, updateModifier: BSONDocument): Future[UpdateWriteResult]

  def deleteOne(selector: BSONDocument): Future[Option[T]]
}