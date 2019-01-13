package models.daos

import com.softwaremill.macwire.wire
import play.api.mvc.ControllerComponents
import play.modules.reactivemongo.ReactiveMongoApiFromContext
import services.ServiceModule

import scala.concurrent.ExecutionContext

trait DaoModule extends ServiceModule{
  reactiveMongoComponents: ReactiveMongoApiFromContext =>

  implicit val ec: ExecutionContext = executionContext
  implicit val cc: ControllerComponents = controllerComponents

  lazy val bookRepository: BookRepository = wire[BookRepositoryImpl]
  lazy val bookStapleRepository: StapleRepository = wire[StapleRepositoryImpl]
}
