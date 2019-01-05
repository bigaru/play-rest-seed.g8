package models

import play.api.libs.json.{Format, Json}
import reactivemongo.play.json._
import reactivemongo.bson.{BSONDocument, BSONDocumentHandler, BSONObjectID, Macros}

case class Item(
                 _id: Option[BSONObjectID],
                 name: Option[String],
                 kind: Option[String],
                 weight: Option[Int]
               ) extends DbItem
{

  def updateModifier = BSONDocument(
    "name" -> name,
    "kind" -> kind,
    "weight" -> weight,
  )
}

object Item {
  implicit val itemBson: BSONDocumentHandler[Item] = Macros.handler[Item]
  implicit val itemJson: Format[Item] = Json.format[Item]

}