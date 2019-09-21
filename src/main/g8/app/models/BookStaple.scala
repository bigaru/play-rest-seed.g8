package models

import daos.CreateUpdate
import play.api.libs.json.{Format, Json}
import reactivemongo.bson.{BSONDocument, BSONDocumentHandler, BSONObjectID, Macros}
import reactivemongo.play.json._

case class BookStaple(
    _id: Option[BSONObjectID],
    name: String,
    description: Option[String],
    books: Seq[Book]
)

object BookStaple {
  implicit val bookStapleBson: BSONDocumentHandler[BookStaple] = Macros.handler[BookStaple]
  implicit val bookStapleJson: Format[BookStaple]              = Json.format[BookStaple]

  implicit val updateBookStaple = new CreateUpdate[BookStaple] {
    def createUpdate(staple: BookStaple) = BSONDocument(
      "name"        -> staple.name,
      "description" -> staple.description,
      "books"       -> staple.books
    )
  }
}
