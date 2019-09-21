package daos

import services.MongoService
import play.api.http.Status._
import MakeSelector.ops._
import CreateUpdate.ops._

import scala.concurrent.ExecutionContext

class RegularRepositoryImpl[T, SELECTOR](mongoService: MongoService[T])(
    implicit ec: ExecutionContext,
    createUpdateInstance: CreateUpdate[T],
    makeSelectorInstance: MakeSelector[SELECTOR]
) extends RegularRepository[T, SELECTOR] {

  def getAll =
    mongoService.getMany()

  def getOne(id: SELECTOR) =
    mongoService.getOne(id.toSelector).map {
      case Some(item) => Right(item)
      case _          => Left((NOT_FOUND, "not found"))
    }

  def addOne(newOne: T) =
    mongoService.addOne(newOne).map {
      case Some(insertedOne) => Right(insertedOne)
      case _                 => Left((INTERNAL_SERVER_ERROR, "failed to insert"))
    }

  def addMany(items: Seq[T]) =
    mongoService.addMany(items).map { result =>
      if (result) Right(true) else Left((INTERNAL_SERVER_ERROR, "failed to insert"))
    }

  def updateOne(id: SELECTOR, newOne: T) =
    mongoService.updateOne(id.toSelector, newOne.createUpdate).map {
      case Some(updatedOne) => Right(updatedOne)
      case _                => Left((NOT_FOUND, "not found"))
    }

  def deleteOne(id: SELECTOR) =
    mongoService.deleteOne(id.toSelector).map {
      case Some(deletedOne) => Right(deletedOne)
      case _                => Left((NOT_FOUND, "not found"))
    }

}
