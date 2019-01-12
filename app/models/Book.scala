package models

import play.api.libs.json.{Format, Json}
import reactivemongo.bson.Macros.Annotations.Key
import reactivemongo.play.json._
import reactivemongo.bson.{BSONDocument, BSONDocumentHandler, Macros}

case class Book(
  @Key("_id")  isbn: Option[String],
                    name: Option[String],
                    description: Option[String],
                    author: Option[String],
                    pages: Option[Int],
                    paidPrice: Option[Double]
)

object Book {
  implicit val bookBson: BSONDocumentHandler[Book] = Macros.handler[Book]
  implicit val bookJson: Format[Book] = Json.format[Book]

  implicit def makeSelector(id: String): BSONDocument = BSONDocument("_id" -> id)
}
