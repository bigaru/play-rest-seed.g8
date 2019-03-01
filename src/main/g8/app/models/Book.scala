package models

import play.api.libs.json.{Format, Json}
import reactivemongo.bson.Macros.Annotations.Key
import reactivemongo.play.json._
import reactivemongo.bson.{BSONDocumentHandler, Macros}

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
}
