package models.daos

import models.{MakeSelector, UpdateModifier}
import services.MongoService
import models.DbSyntax._
import play.api.http.Status._

import scala.concurrent.{ExecutionContext, Future}

class RegularRepositoryImpl[T, SELECTOR](mongoService: MongoService[T])(implicit ec: ExecutionContext, updateInstance: UpdateModifier[T], makeSelectorInstance: MakeSelector[SELECTOR])
 extends RegularRepository[T, SELECTOR] {

  def getAll =
    mongoService.getMany()

  def getOne(id: SELECTOR) =
    mongoService.getOne(id.makeSelector).map{
      case Some(item) => Right(item)
      case _ => Left((NOT_FOUND, "not found"))
    }

  def addOne(newOne: T) =
    mongoService.addOne(newOne).map{
      case Some(insertedOne) => Right(insertedOne)
      case _ => Left((INTERNAL_SERVER_ERROR, "failed to insert"))
    }

  def addMany(items: Seq[T]) =
    mongoService.addMany(items).map{ result =>
      if (result) Right(true)
      else Left((INTERNAL_SERVER_ERROR, "failed to insert"))
    }

  def updateOne(id: SELECTOR, newOne: T) =
    mongoService.updateOne(id.makeSelector, newOne.updateModifier).map{
      case Some(updatedOne) => Right(updatedOne)
      case _ => Left((INTERNAL_SERVER_ERROR, "failed to update"))
    }

  def deleteOne(id: SELECTOR) =
    mongoService.deleteOne(id.makeSelector).map{
      case Some(deletedOne) => Right(deletedOne)
      case _ => Left((NOT_FOUND, "not found"))
    }

}
