
import _root_.controllers.{AssetsComponents, HomeController}
import com.softwaremill.macwire._
import models.Item
import models.daos.RegularRepository
import play.api.ApplicationLoader.Context
import play.api._
import play.api.mvc.ControllerComponents
import play.api.routing.Router
import play.filters.HttpFiltersComponents
import play.modules.reactivemongo.{DefaultReactiveMongoApi, ReactiveMongoApi}
import reactivemongo.api.MongoConnection
import reactivemongo.api.MongoConnection.ParsedURI
import reactivemongo.bson.{BSONDocumentWriter, BSONObjectID, Macros}
import router.Routes
import services.{MongoService, MongoServiceImpl}

import scala.concurrent.ExecutionContext

class AppLoader extends ApplicationLoader {
  def load(context: Context): Application = new AppComponents(context).application
}

class AppComponents(context: Context)
  extends BuiltInComponentsFromContext(context)
  with AssetsComponents
  with HttpFiltersComponents {

  private val configuredUri = configuration.get[String]("mongodb.uri")
  private val configuredDb  = configuration.get[String]("mongodb.dbname")
  private val mongodbUri:ParsedURI = MongoConnection.parseURI(configuredUri).get

  implicit val ec: ExecutionContext = executionContext
  implicit val cc: ControllerComponents = controllerComponents
  //TODO replace with ReactiveMongoApiComponents
  implicit val reactiveMongoApi: ReactiveMongoApi = new DefaultReactiveMongoApi("mainMongoDB", mongodbUri, configuredDb, true, configuration, applicationLifecycle)
  implicit val bsonObjectIDWriter: BSONDocumentWriter[BSONObjectID] = Macros.writer[BSONObjectID]

  //TODO pass argmunt still using macwire
  implicit lazy val mongoService: MongoService[Item] = new MongoServiceImpl[Item]("items")

  lazy val itemRepository = new RegularRepository[Item, BSONObjectID]()
  lazy val homeController: HomeController = wire[HomeController]

  LoggerConfigurator(context.environment.classLoader).foreach {
    _.configure(context.environment, context.initialConfiguration, Map.empty)
  }

  lazy val router: Router = {
    val prefix: String = "/"
    wire[Routes]
  }

}
