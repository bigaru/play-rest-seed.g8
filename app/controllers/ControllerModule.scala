package controllers

import com.softwaremill.macwire.wire
import models.daos.DaoModule

trait ControllerModule {
  daoModule: DaoModule =>

  lazy val homeController = wire[HomeController]
}
