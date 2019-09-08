import models.{Book, BookStaple}
import reactivemongo.bson.BSONObjectID

package object daos {
  type ErrorMsg = (Int, String)

  type BookRepository     = RegularRepository[Book, String]
  type BookRepositoryImpl = RegularRepositoryImpl[Book, String]

  type StapleRepository     = RegularRepository[BookStaple, BSONObjectID]
  type StapleRepositoryImpl = RegularRepositoryImpl[BookStaple, BSONObjectID]

  object DbSyntax {
    implicit class UpdateOps[A](value: A) {
      def updateModifier(implicit instance: UpdateModifier[A]) = {
        instance.updateModifier(value)
      }
    }

    implicit class MakeSelectorOps[A](value: A) {
      def makeSelector(implicit instance: MakeSelector[A]) = {
        instance.makeSelector(value)
      }
    }
  }
}
