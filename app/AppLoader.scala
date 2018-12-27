
import _root_.controllers.{AssetsComponents, HomeController}
import com.softwaremill.macwire._
import play.api.ApplicationLoader.Context
import play.api._
import play.api.mvc.ControllerComponents
import play.api.routing.Router
import play.filters.HttpFiltersComponents
import router.Routes

import scala.concurrent.ExecutionContext

class AppLoader extends ApplicationLoader {
  def load(context: Context): Application = new AppComponents(context).application
}

class AppComponents(context: Context) extends BuiltInComponentsFromContext(context)
  with AssetsComponents
  with HttpFiltersComponents {

  implicit lazy val ec: ExecutionContext = executionContext
  implicit lazy val cc: ControllerComponents = controllerComponents

  lazy val homeController: HomeController = wire[HomeController]

  LoggerConfigurator(context.environment.classLoader).foreach {
    _.configure(context.environment, context.initialConfiguration, Map.empty)
  }

  lazy val router: Router = {
    val prefix: String = "/"
    wire[Routes]
  }

}
