package daos

import simulacrum._
import reactivemongo.bson.{BSONDocument, BSONObjectID}

@typeclass trait MakeSelector[A] {
  def toSelector(item: A): BSONDocument
}

object MakeSelector {
  implicit val makeStringSelector = new MakeSelector[String] {
    def toSelector(id: String) = BSONDocument("_id" -> id)
  }

  implicit val makeObjectIdSelector = new MakeSelector[BSONObjectID] {
    def toSelector(id: BSONObjectID) = BSONDocument("_id" -> id)
  }
}
