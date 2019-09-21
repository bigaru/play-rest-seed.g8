package daos

import com.softwaremill.macwire.wire
import play.api.BuiltInComponentsFromContext
import services.ServiceModule

trait DaoModule { this: BuiltInComponentsFromContext with ServiceModule =>
  lazy val bookRepository: BookDao         = wire[BookRepository]
  lazy val bookStapleRepository: StapleDao = wire[StapleRepository]
}
