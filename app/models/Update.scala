package models

import reactivemongo.bson.BSONDocument

trait Update[A] {
  def updateModifier(item: A): BSONDocument
}

object Update {
  implicit val updateBook = new Update[Book] {
    def updateModifier(book: Book) = BSONDocument(
      "name" -> book.name,
      "description" -> book.description,
      "author" -> book.author,
      "pages" -> book.pages,
      "paidPrice" -> book.paidPrice
    )
  }

  implicit val updateBookStaple = new Update[BookStaple] {
    def updateModifier(staple: BookStaple) = BSONDocument(
      "name" -> staple.name,
      "description" -> staple.description,
      "books" -> staple.books
    )
  }

}


object UpdateSyntax {
  implicit class UpdateOps[A](value: A) {
    def updateModifier(implicit instance: Update[A]) = {
      instance.updateModifier(value)
    }
  }
}