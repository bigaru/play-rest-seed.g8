package models.daos

import models.Update
import reactivemongo.api.commands.MultiBulkWriteResult
import reactivemongo.bson.BSONDocument
import services.MongoService
import models.UpdateSyntax.UpdateOps

import scala.concurrent.{ExecutionContext, Future}

class RegularRepositoryImpl[T, SELECTOR](mongoService: MongoService[T], ec: ExecutionContext)(implicit makeSelector: SELECTOR => BSONDocument, updateInstance: Update[T])
 extends RegularRepository[T, SELECTOR] {

  def getAll: Future[Seq[T]] =
    mongoService.getMany()

  def getOne(id: SELECTOR): Future[Option[T]] =
    mongoService.getOne(makeSelector(id))

  def addOne(item: T): Future[Option[T]] =
    mongoService.addOne(item)

  def addMany(items: Seq[T]): Future[MultiBulkWriteResult] =
    mongoService.addMany(items)

  def updateOne(id: SELECTOR, newOne: T): Future[Option[T]] =
    mongoService.updateOne(makeSelector(id), newOne.updateModifier)

  def deleteOne(id: SELECTOR): Future[Option[T]] =
    mongoService.deleteOne(makeSelector(id))

}
