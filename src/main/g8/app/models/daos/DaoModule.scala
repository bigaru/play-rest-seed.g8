package models.daos

import com.softwaremill.macwire.wire
import play.api.BuiltInComponentsFromContext
import services.ServiceModule

trait DaoModule { this: BuiltInComponentsFromContext with ServiceModule =>
  lazy val bookRepository: BookRepository         = wire[BookRepositoryImpl]
  lazy val bookStapleRepository: StapleRepository = wire[StapleRepositoryImpl]
}
