package models.daos

import reactivemongo.bson.BSONDocument

trait Updateable {
  def updateModifier: BSONDocument
}
