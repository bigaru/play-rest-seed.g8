package models

import reactivemongo.bson.BSONDocument

trait DbItem {

  def updateModifier: BSONDocument
}
