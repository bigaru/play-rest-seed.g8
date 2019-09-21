import models.{Book, BookStaple}
import reactivemongo.bson.BSONObjectID

package object daos {
  type ErrorMsg = (Int, String)

  type BookRepository     = RegularRepository[Book, String]
  type BookRepositoryImpl = RegularRepositoryImpl[Book, String]

  type StapleRepository     = RegularRepository[BookStaple, BSONObjectID]
  type StapleRepositoryImpl = RegularRepositoryImpl[BookStaple, BSONObjectID]
}
