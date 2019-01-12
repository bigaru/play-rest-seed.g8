package models

import reactivemongo.bson.BSONDocument

trait DbItem[A] {
  def updateModifier(item: A): BSONDocument
  def validate(item: A): Boolean
}
