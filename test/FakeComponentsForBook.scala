import models.Book
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import play.api.ApplicationLoader.Context
import services.MongoService

import scala.concurrent.Future

class FakeComponentsForBook(context: Context) extends AppComponents(context)
  with MockitoSugar
{
  override lazy val bookMongo = mock[MongoService[Book]]

  val books = {
    val b1 = Book("978-1328869333", "1984", None, "George Orwell", 304, Some(13.49))
    val b2 = Book("978-0143124177", "Mastery", Some("Each one of us has within us the potential to be a Master."), "Robert Greene", 352, None)
    val b3 = Book("978-0375507250", "Cloud Atlas", None, "David Mitchell", 528, None)
    Seq(b1, b2, b3)
  }
  when(bookMongo.getMany()) thenReturn Future.successful(books)
}
