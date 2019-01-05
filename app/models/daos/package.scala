package models

import reactivemongo.bson.BSONObjectID

package object daos {
  type ItemRepository = RegularRepository[Item, BSONObjectID]
}
