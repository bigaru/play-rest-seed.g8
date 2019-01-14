package models

import reactivemongo.bson.BSONDocument

trait UpdateModifier[A] {
  def updateModifier(item: A): BSONDocument
}

object UpdateModifier {
  implicit val updateBook = new UpdateModifier[Book] {
    def updateModifier(book: Book) = BSONDocument(
      "name" -> book.name,
      "description" -> book.description,
      "author" -> book.author,
      "pages" -> book.pages,
      "paidPrice" -> book.paidPrice
    )
  }

  implicit val updateBookStaple = new UpdateModifier[BookStaple] {
    def updateModifier(staple: BookStaple) = BSONDocument(
      "name" -> staple.name,
      "description" -> staple.description,
      "books" -> staple.books
    )
  }

}
