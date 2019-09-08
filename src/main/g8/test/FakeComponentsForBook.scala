import models.Book
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import play.api.ApplicationLoader.Context
import reactivemongo.bson.BSONDocument
import services.MongoService
import daos.DbSyntax._

import scala.concurrent.Future

class FakeComponentsForBook(context: Context) extends AppComponents(context) with MockitoSugar {
  override lazy val bookMongo = mock[MongoService[Book]]

  val books = {
    val b1 = Book("978-1328869333", "1984", None, "George Orwell", 304, Some(13.49))
    val b2 = Book("978-0143124177",
                  "Mastery",
                  Some("Each one of us has within us the potential to be a Master."),
                  "Robert Greene",
                  352,
                  None)
    val b3 = Book("978-0375507250", "Cloud Atlas", None, "David Mitchell", 528, None)
    Seq(b1, b2, b3)
  }
  when(bookMongo.getMany()) thenReturn Future.successful(books)

  val theBook = Book("978-0679463047", "Ghostwritten", None, "David Mitchell", 448, None)
  when(bookMongo.getOne(BSONDocument("_id" -> "978-0679463047"))) thenReturn Future.successful(Some(theBook))

  when(bookMongo.getOne(BSONDocument("_id" -> "non-0679463047"))) thenReturn Future.successful(None)

  val insertedBook = Book("978-0425284629",
                          "Skin in the Game",
                          Some("Hidden Asymmetries in Daily Life"),
                          "Nassim Nicholas Taleb",
                          304,
                          Some(18.99))
  when(bookMongo.addOne(insertedBook)) thenReturn Future.successful(Some(insertedBook))

  val duplicatedBook = Book("978-0812973815", "The Black Swan", None, "Nassim Nicholas Taleb", 480, None)
  when(bookMongo.addOne(duplicatedBook)) thenReturn Future.successful(None)

  val updatedBook = Book("978-0141031484", "Fooled by Randomness", None, "Nassim Nicholas Taleb", 368, None)
  when(bookMongo.updateOne("978-0141031484".makeSelector, updatedBook.updateModifier)) thenReturn Future.successful(
    Some(updatedBook))

  when(bookMongo.updateOne(
    "non-0141031484".makeSelector,
    Book("non-0141031484", "Fooled by Randomness", None, "Nassim Nicholas Taleb", 368, None).updateModifier)) thenReturn Future
    .successful(None)

  val deletedBook = Book("978-0141038223", "Antifragile", None, "Nassim Nicholas Taleb", 544, None)
  when(bookMongo.deleteOne("978-0141038223".makeSelector)) thenReturn Future.successful(Some(deletedBook))

  when(bookMongo.deleteOne("non-0141038223".makeSelector)) thenReturn Future.successful(None)

}
