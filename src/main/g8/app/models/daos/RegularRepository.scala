package models.daos

import scala.concurrent.Future

trait RegularRepository[T, SELECTOR] {

  def getAll: Future[Seq[T]]

  def getOne(id: SELECTOR): Future[Either[ErrorMsg, T]]

  def addOne(item: T): Future[Either[ErrorMsg, T]]

  def addMany(items: Seq[T]): Future[Either[ErrorMsg, Boolean]]

  def updateOne(id: SELECTOR, newOne: T): Future[Either[ErrorMsg, T]]

  def deleteOne(id: SELECTOR): Future[Either[ErrorMsg, T]]

}
