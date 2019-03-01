import _root_.controllers.{AssetsComponents, ControllerModule}
import com.softwaremill.macwire._
import models.daos.DaoModule
import play.api.ApplicationLoader.Context
import play.api._
import play.api.routing.Router
import play.filters.HttpFiltersComponents
import play.modules.reactivemongo.ReactiveMongoApiFromContext
import router.Routes

class AppLoader extends ApplicationLoader {
  def load(context: Context): Application = new AppComponents(context).application
}

class AppComponents(context: Context)
    extends ReactiveMongoApiFromContext(context)
    with AssetsComponents
    with DaoModule
    with ControllerModule
    with HttpFiltersComponents {

  LoggerConfigurator(context.environment.classLoader).foreach {
    _.configure(context.environment, context.initialConfiguration, Map.empty)
  }

  lazy val router: Router = {
    val prefix: String = "/"

    val bookRoutes   = wire[Book.Routes]
    val stapleRoutes = wire[Staple.Routes]
    wire[Routes]
  }

}
