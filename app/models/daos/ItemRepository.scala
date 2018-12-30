package models.daos

import models.Item
import services.MongoService
import scala.concurrent.ExecutionContext

class ItemRepository()(implicit ec: ExecutionContext, mongoService: MongoService[Item]) extends BaseRepository[Item]{


}
