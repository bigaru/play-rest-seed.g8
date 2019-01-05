package services

import com.softwaremill.macwire.wire
import models.Item
import play.modules.reactivemongo.ReactiveMongoApiFromContext

trait ServiceModule {
  reactiveMongoComponents: ReactiveMongoApiFromContext =>

  lazy val itemMongo: MongoService[Item] = {
    val collectionName = "items"
    wire[MongoServiceImpl[Item]]
  }
}
