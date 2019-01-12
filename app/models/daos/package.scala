package models

import reactivemongo.bson.BSONDocument

package object daos {
  type BookRepository = RegularRepository[Book, String]
  type BookRepositoryImpl = RegularRepositoryImpl[Book, String]

}
