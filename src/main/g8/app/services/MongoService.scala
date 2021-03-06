package services

import reactivemongo.bson.BSONDocument
import scala.concurrent.Future

trait MongoService[T] {
  def getOne(query: BSONDocument, sort: BSONDocument = BSONDocument()): Future[Option[T]]

  def getMany(query: BSONDocument = BSONDocument(),
              sort: BSONDocument = BSONDocument(),
              countDocs: Int = -1): Future[Seq[T]]

  def addOne(newItem: T): Future[Option[T]]

  def addMany(items: Seq[T]): Future[Boolean]

  def updateOne(selector: BSONDocument, updateModifier: BSONDocument): Future[Option[T]]

  def updateMany(selector: BSONDocument, updateModifier: BSONDocument): Future[Boolean]

  def deleteOne(selector: BSONDocument): Future[Option[T]]
}
