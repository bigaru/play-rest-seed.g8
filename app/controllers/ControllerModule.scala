package controllers

import com.softwaremill.macwire.wire
import models.daos.DaoModule

import scala.concurrent.ExecutionContext

trait ControllerModule {
  daoModule: DaoModule =>

  implicit val ec: ExecutionContext = daoEC

  lazy val homeController = wire[HomeController]
  lazy val bookController = wire[BookController]
  lazy val stapleController = wire[StapleController]
}
