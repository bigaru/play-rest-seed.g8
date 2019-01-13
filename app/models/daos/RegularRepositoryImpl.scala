package models.daos

import models.{MakeSelector, UpdateModifier}
import reactivemongo.api.commands.MultiBulkWriteResult
import services.MongoService
import models.DbSyntax._
import play.api.http.Status._

import scala.concurrent.{ExecutionContext, Future}

class RegularRepositoryImpl[T, SELECTOR](mongoService: MongoService[T])(implicit ec: ExecutionContext, updateInstance: UpdateModifier[T], makeSelectorInstance: MakeSelector[SELECTOR])
 extends RegularRepository[T, SELECTOR] {

  def getAll: Future[Seq[T]] =
    mongoService.getMany()

  def getOne(id: SELECTOR): Future[Either[(Int,String), T]] =
    mongoService.getOne(id.makeSelector).map{
      case Some(item) => Right(item)
      case _ => Left((NOT_FOUND, "not found"))
    }

  def addOne(item: T): Future[Option[T]] =
    mongoService.addOne(item)

  def addMany(items: Seq[T]): Future[MultiBulkWriteResult] =
    mongoService.addMany(items)

  def updateOne(id: SELECTOR, newOne: T): Future[Option[T]] =
    mongoService.updateOne(id.makeSelector, newOne.updateModifier)

  def deleteOne(id: SELECTOR): Future[Option[T]] =
    mongoService.deleteOne(id.makeSelector)

}
