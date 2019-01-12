package services

import com.softwaremill.macwire.wire
import models.Book
import play.modules.reactivemongo.ReactiveMongoApiFromContext

trait ServiceModule {
  reactiveMongoComponents: ReactiveMongoApiFromContext =>

  lazy val bookMongo: MongoService[Book] = {
    val collectionName = "books"
    wire[MongoServiceImpl[Book]]
  }
}
