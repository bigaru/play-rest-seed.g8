import org.scalatestplus.play._
import org.scalatestplus.play.components.OneAppPerSuiteWithComponents
import play.api.BuiltInComponents
import play.api.test.Helpers._
import play.api.test._

class BookControllerSpec extends PlaySpec with OneAppPerSuiteWithComponents {
  private val predefinedComponents = new FakeComponentsForBook(context)
  val controller = predefinedComponents.bookController

  override def components: BuiltInComponents = predefinedComponents


  "getAll" should {

    val request = FakeRequest()
    val result = controller.getAll.apply(request)

    "return OK" in {
      status(result) mustBe OK
    }

    "be in json format" in {
      contentType(result) mustBe Some("application/json")
    }

    "return all available books" in {
      contentAsString(result) must include ("""[{"isbn":"978-1328869333","name":"1984","author":"George Orwell","pages":304,"paidPrice":13.49},{"isbn":"978-0143124177","name":"Mastery","description":"Each one of us has within us the potential to be a Master.","author":"Robert Greene","pages":352},{"isbn":"978-0375507250","name":"Cloud Atlas","author":"David Mitchell","pages":528}]""")
    }

  }
}
