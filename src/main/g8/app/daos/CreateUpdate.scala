package daos

import simulacrum._
import reactivemongo.bson.BSONDocument

@typeclass trait CreateUpdate[A] {
  def createUpdate(item: A): BSONDocument
}
