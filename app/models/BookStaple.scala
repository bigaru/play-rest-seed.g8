package models

import play.api.libs.json.{Format, Json}
import reactivemongo.bson.{BSONDocument, BSONDocumentHandler, BSONObjectID, Macros}
import reactivemongo.play.json._

case class BookStaple(
             _id: Option[BSONObjectID],
             name: Option[String],
             description: Option[String],
             books: Option[Seq[Book]]
)

object BookStaple {
  implicit val bookStapleBson: BSONDocumentHandler[BookStaple] = Macros.handler[BookStaple]
  implicit val bookStapleJson: Format[BookStaple] = Json.format[BookStaple]

  implicit def makeSelector(id: BSONObjectID): BSONDocument = BSONDocument("_id" -> id)
}


