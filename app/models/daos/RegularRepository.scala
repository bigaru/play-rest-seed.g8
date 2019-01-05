package models.daos

import reactivemongo.api.commands.MultiBulkWriteResult
import scala.concurrent.Future

trait RegularRepository[T <: Updateable, SELECTOR]{

  def getAll: Future[Seq[T]]

  def getOne(id: SELECTOR): Future[Option[T]]

  def addOne(item: T): Future[Option[T]]

  def addMany(items: Seq[T]): Future[MultiBulkWriteResult]

  def updateOne(id: SELECTOR, newOne: T): Future[Option[T]]

  def deleteOne(id: SELECTOR): Future[Option[T]]

}
