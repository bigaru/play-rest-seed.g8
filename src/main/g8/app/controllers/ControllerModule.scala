package controllers

import com.softwaremill.macwire.wire
import daos.DaoModule
import play.api.BuiltInComponentsFromContext

trait ControllerModule { this: BuiltInComponentsFromContext with DaoModule =>
  lazy val homeController   = wire[HomeController]
  lazy val bookController   = wire[BookController]
  lazy val stapleController = wire[StapleController]
}
