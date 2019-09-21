package models

import daos.CreateUpdate
import play.api.libs.json.{Format, Json}
import reactivemongo.bson.Macros.Annotations.Key
import reactivemongo.play.json._
import reactivemongo.bson.{BSONDocument, BSONDocumentHandler, Macros}

// format: off
case class Book(
  @Key("_id")  isbn: String,
                    name: String,
                    description: Option[String],
                    author: String,
                    pages: Int,
                    paidPrice: Option[Double]
)
// format: on

object Book {
  implicit val bookBson: BSONDocumentHandler[Book] = Macros.handler[Book]
  implicit val bookJson: Format[Book]              = Json.format[Book]

  implicit val updateBook = new CreateUpdate[Book] {
    def createUpdate(book: Book) = BSONDocument(
      "name"        -> book.name,
      "description" -> book.description,
      "author"      -> book.author,
      "pages"       -> book.pages,
      "paidPrice"   -> book.paidPrice
    )
  }
}
