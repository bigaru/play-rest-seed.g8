package models

import reactivemongo.bson.{BSONDocument, BSONObjectID}

package object daos {
  trait Updateable {
    def updateModifier: BSONDocument
  }

  type ItemRepository = RegularRepository[Item, BSONObjectID]
  type ItemRepositoryImpl = RegularRepositoryImpl[Item, BSONObjectID]

}
