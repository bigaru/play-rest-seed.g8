import models.{Book, BookStaple}
import reactivemongo.bson.BSONObjectID

package object daos {
  type ErrorMsg = (Int, String)

  type BookDao        = BasicDao[Book, String]
  type BookRepository = DefaultRepository[Book, String]

  type StapleDao        = BasicDao[BookStaple, BSONObjectID]
  type StapleRepository = DefaultRepository[BookStaple, BSONObjectID]
}
