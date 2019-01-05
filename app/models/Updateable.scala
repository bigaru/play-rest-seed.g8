package models

import reactivemongo.bson.BSONDocument

trait Updateable {
  def updateModifier: BSONDocument
}
