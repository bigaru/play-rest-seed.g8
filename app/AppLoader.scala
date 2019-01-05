
import _root_.controllers.{AssetsComponents, HomeController}
import com.softwaremill.macwire._
import models.Item
import models.daos.ItemRepository
import play.api.ApplicationLoader.Context
import play.api._
import play.api.mvc.ControllerComponents
import play.api.routing.Router
import play.filters.HttpFiltersComponents
import play.modules.reactivemongo.ReactiveMongoApiFromContext
import router.Routes
import services.{MongoService, MongoServiceImpl}

import scala.concurrent.ExecutionContext

class AppLoader extends ApplicationLoader {
  def load(context: Context): Application = new AppComponents(context).application
}

class AppComponents(context: Context)
  extends ReactiveMongoApiFromContext(context)
  with AssetsComponents
  with HttpFiltersComponents {

  implicit val ec: ExecutionContext = executionContext
  implicit val cc: ControllerComponents = controllerComponents

  lazy val itemMongo: MongoService[Item] = {
    val collectionName = "items"
    wire[MongoServiceImpl[Item]]
  }

  lazy val itemRepository = {
    import Item.makeSelector
    wire[ItemRepository]
  }
  lazy val homeController = wire[HomeController]

  LoggerConfigurator(context.environment.classLoader).foreach {
    _.configure(context.environment, context.initialConfiguration, Map.empty)
  }

  lazy val router: Router = {
    val prefix: String = "/"
    wire[Routes]
  }

}
