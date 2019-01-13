package models

import reactivemongo.bson.{BSONDocument, BSONObjectID}

trait MakeSelector[A] {
  def makeSelector(item: A): BSONDocument
}

object MakeSelector {
  implicit val makeStringSelector = new MakeSelector[String] {
    def makeSelector(id: String) = BSONDocument("_id" -> id)
  }

  implicit val makeObjectIdSelector = new MakeSelector[BSONObjectID] {
    def makeSelector(id: BSONObjectID) = BSONDocument("_id" -> id)
  }

}
