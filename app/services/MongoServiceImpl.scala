package services

import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.{Cursor, ReadPreference}
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.api.commands.{MultiBulkWriteResult, UpdateWriteResult}
import reactivemongo.bson.{BSONDocument, BSONDocumentHandler}

import scala.concurrent.{ExecutionContext, Future}

class MongoServiceImpl[T](collectionName:String)
                         (implicit ec: ExecutionContext, reactiveMongoApi: ReactiveMongoApi, bsonDocumentHandler: BSONDocumentHandler[T])
  extends MongoService[T] {

  private def collection: Future[BSONCollection] = reactiveMongoApi.database.map(_.collection(collectionName))

  def getOne(query: BSONDocument, sort: BSONDocument = BSONDocument()): Future[Option[T]] = {
    collection.flatMap(
      _.find(query, None)
        .sort(sort)
        .one[T])
  }

  def getMany(query:BSONDocument = BSONDocument(), sort: BSONDocument = BSONDocument(), countDocs: Int = -1): Future[Seq[T]] = {
    collection.flatMap(
      _.find(query, None)
        .sort(sort)
        .cursor[T]()
        .collect[Seq](countDocs, Cursor.FailOnError[Seq[T]]()
      )
    )
  }

  def addOne(newItem: T): Future[Option[T]] = {
    collection.flatMap(
      _.insert(newItem)
       .map( writeResult =>
          if (writeResult.ok) Some(newItem)
          else None
        )
    )
  }

  def addMany(items: Seq[T]): Future[MultiBulkWriteResult] = {
    collection.flatMap(
      _.insert[T](ordered = false)
       .many(items)
    )
  }

  def updateOne(selector: BSONDocument, updateModifier: BSONDocument): Future[Option[T]] = {
    collection.flatMap(
      _.findAndUpdate(selector, updateModifier, fetchNewObject = true)
       .map(_.result[T])
    )
  }

  def updateMany(selector: BSONDocument, updateModifier: BSONDocument): Future[UpdateWriteResult] = {
    collection.flatMap(
      _.update(selector, updateModifier, multi = true)
    )
  }

  def deleteOne(selector: BSONDocument): Future[Option[T]] = {
    collection.flatMap(
      _.findAndRemove(selector)
       .map(_.result[T])
    )
  }

}