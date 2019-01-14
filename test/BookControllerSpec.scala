import org.scalatestplus.play._
import org.scalatestplus.play.components.OneAppPerSuiteWithComponents
import play.api.BuiltInComponents
import play.api.libs.json.Json
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

  "existing getOne" should {

    val request = FakeRequest()
    val result = controller.getOne("978-0679463047").apply(request)

    "return OK" in {
      status(result) mustBe OK
    }

    "be in json format" in {
      contentType(result) mustBe Some("application/json")
    }

    "return a book" in {
      contentAsString(result) must include ("""{"isbn":"978-0679463047","name":"Ghostwritten","author":"David Mitchell","pages":448}""")
    }

  }

  "non-existing getOne" should {

    val request = FakeRequest()
    val result = controller.getOne("non-0679463047").apply(request)

    "return NotFound" in {
      status(result) mustBe NOT_FOUND
    }

    "be in text format" in {
      contentType(result) mustBe Some("text/plain")
    }

    "contain right text" in {
      contentAsString(result) must include ("""not found""")
    }

  }

  "successful addOne" should {
    val jsonBody =
      """{
        |"isbn":"978-0425284629",
        |"name":"Skin in the Game",
        |"description":"Hidden Asymmetries in Daily Life",
        |"author":"Nassim Nicholas Taleb",
        |"pages":304,
        |"paidPrice":18.99
        |}""".stripMargin
    val request = FakeRequest()
                  .withBody(Json.parse(jsonBody))
    val result = controller.createOne.apply(request)

    "return Created" in {
      status(result) mustBe CREATED
    }

    "be in json format" in {
      contentType(result) mustBe Some("application/json")
    }

    "return inserted book" in {
      contentAsString(result) must include ("""{"isbn":"978-0425284629","name":"Skin in the Game","description":"Hidden Asymmetries in Daily Life","author":"Nassim Nicholas Taleb","pages":304,"paidPrice":18.99}""")
    }

  }

  "wrongly formatted addOne" should {
    val jsonBody =
      """{
        |"id":"978-0425284629",
        |"name":"Skin in the Game",
        |"autoro":"Nassim Nicholas Taleb",
        |"paged":304
        |}""".stripMargin
    val request = FakeRequest()
                  .withBody(Json.parse(jsonBody))
    val result = controller.createOne.apply(request)

    "return BadRequest" in {
      status(result) mustBe BAD_REQUEST
    }

    "be in text format" in {
      contentType(result) mustBe Some("text/plain")
    }

    "contain right text" in {
      contentAsString(result) must include ("""invalid book format""")
    }

  }

  "addOne with existing id" should {
    val jsonBody =
      """{
        |"isbn":"978-0812973815",
        |"name":"The Black Swan",
        |"author":"Nassim Nicholas Taleb",
        |"pages":480
        |}""".stripMargin
    val request = FakeRequest()
                  .withBody(Json.parse(jsonBody))
    val result = controller.createOne.apply(request)

    "return InternalServerError" in {
      status(result) mustBe INTERNAL_SERVER_ERROR
    }

    "be in text format" in {
      contentType(result) mustBe Some("text/plain")
    }

    "contain right text" in {
      contentAsString(result) must include ("""failed to insert""")
    }

  }

  "successful update" should {
    val jsonBody =
      """{
        |"isbn":"978-0141031484",
        |"name":"Fooled by Randomness",
        |"author":"Nassim Nicholas Taleb",
        |"pages":368
        |}""".stripMargin
    val request = FakeRequest()
      .withBody(Json.parse(jsonBody))
    val result = controller.update("978-0141031484").apply(request)

    "return OK" in {
      status(result) mustBe OK
    }

    "be in json format" in {
      contentType(result) mustBe Some("application/json")
    }

    "return updated book" in {
      contentAsString(result) must include ("""{"isbn":"978-0141031484","name":"Fooled by Randomness","author":"Nassim Nicholas Taleb","pages":368}""")
    }

  }

  "wrongly formatted update" should {
    val jsonBody =
      """{
        |"id":"978-0141031484",
        |"namo":"Fooled by Randomness",
        |"auto":"Nassim Nicholas Taleb",
        |"pages":368
        |}""".stripMargin
    val request = FakeRequest()
      .withBody(Json.parse(jsonBody))
    val result = controller.update("978-0141031484").apply(request)

    "return BadRequest" in {
      status(result) mustBe BAD_REQUEST
    }

    "be in text format" in {
      contentType(result) mustBe Some("text/plain")
    }

    "contain right text" in {
      contentAsString(result) must include ("""invalid book format""")
    }

  }

  "non-existing update" should {
    val jsonBody =
      """{
        |"isbn":"non-0141031484",
        |"name":"Fooled by Randomness",
        |"author":"Nassim Nicholas Taleb",
        |"pages":368
        |}""".stripMargin
    val request = FakeRequest()
      .withBody(Json.parse(jsonBody))
    val result = controller.update("non-0141031484").apply(request)

    "return BadRequest" in {
      status(result) mustBe NOT_FOUND
    }

    "be in text format" in {
      contentType(result) mustBe Some("text/plain")
    }

    "contain right text" in {
      contentAsString(result) must include ("""not found""")
    }

  }

  "successful delete" should {
    val request = FakeRequest()
    val result = controller.delete("978-0141038223").apply(request)

    "return OK" in {
      status(result) mustBe OK
    }

    "be in json format" in {
      contentType(result) mustBe Some("application/json")
    }

    "return updated book" in {
      contentAsString(result) must include ("""{"isbn":"978-0141038223","name":"Antifragile","author":"Nassim Nicholas Taleb","pages":544}""")
    }

  }

  "non-existing delete" should {
    val request = FakeRequest()
    val result = controller.delete("non-0141038223").apply(request)

    "return BadRequest" in {
      status(result) mustBe NOT_FOUND
    }

    "be in text format" in {
      contentType(result) mustBe Some("text/plain")
    }

    "contain right text" in {
      contentAsString(result) must include ("""not found""")
    }

  }


}
