package services

import com.softwaremill.macwire.wire
import models.{Book, BookStaple}
import play.modules.reactivemongo.ReactiveMongoApiFromContext

trait ServiceModule { this: ReactiveMongoApiFromContext =>

  lazy val bookMongo: MongoService[Book] = {
    val collectionName = "books"
    wire[DefaultMongoService[Book]]
  }

  lazy val stapleMongo: MongoService[BookStaple] = {
    val collectionName = "staples"
    wire[DefaultMongoService[BookStaple]]
  }
}
