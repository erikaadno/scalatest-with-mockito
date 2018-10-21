package example

import com.google.inject.{Guice, Injector}
import org.mockito.Mockito._
import org.scalatest._
import org.scalatest.mockito.MockitoSugar
import scalikejdbc.{AutoSession, ConnectionPool}
import scalikejdbc._

import scala.concurrent.Future

class ThreeThreeThreeSpec extends AsyncFunSpec with Matchers with MockitoSugar with BeforeAndAfter {
  val numbers = Seq(1, 2, 100, 3, 3, 100, 30, 200, 3, 30, 700, 20, 3, 33, 35)

  implicit val session: DBSession = AutoSession

  def setupFakeDB(): Unit = {
    Class.forName("org.h2.Driver")
    ConnectionPool.singleton("jdbc:h2:mem:number", "user", "pass")

    sql"DROP TABLE IF EXISTS numbers".execute.apply()

    sql"""
create table numbers (
    id serial not null primary key,
    num int(32)
)
""".execute.apply()

    numbers foreach { num =>
      sql"insert into numbers (num) values ($num)".update.apply()
    }
  }

  describe("getNumbers") {

    it("たくさんの数字が取得できる") {

      val numbersRepo = mock[NumbersRepository]
      when(numbersRepo.list()).thenReturn(Future(numbers))
      val three3 = new ThreeThreeThree(numbersRepo)

      val numbersFuture = three3.getNumbers()
      verify(numbersRepo, times(1)).list()

      numbersFuture.map(nums => {
        assert(nums == numbers)
      })
    }

    it("H2からたくさんの数字が取得できる") {
      setupFakeDB()

      val injector: Injector = Guice.createInjector(new NumbersRepositoryModule)
      val three3 = injector.getInstance(classOf[ThreeThreeThree])
      val numbersFuture = three3.getNumbers()


      numbersFuture.map(nums => {
        assert(nums == numbers)
      })
    }
  }

  describe("getThrees") {
    it("空の数列からは、3が取り出せない") {
      val numbersRepo = mock[NumbersRepository]
      val three3 = new ThreeThreeThree(numbersRepo)
      val three3Spy = spy(three3)

      when(three3Spy.getNumbers()).thenReturn(Future(Seq()))

      val threesFuture = three3.getThrees()

      threesFuture.map(threes => {
        assert(threes == Seq())
      })
    }

    it("たくさんの数字から3だけを取り出す") {
      val numbersRepo = mock[NumbersRepository]
      val three3 = new ThreeThreeThree(numbersRepo)
      val three3Spy = spy(three3)

      when(three3Spy.getNumbers()).thenReturn(Future(numbers))

      val threesFuture = three3.getThrees()

      threesFuture.map(threes => {
        assert(threes == Seq(3, 3, 3, 3))
      })
    }
  }

  describe("getHeadThrees") {
    it("空の数列からは、頭文字が3の数字は取り出せない") {
      val numbersRepo = mock[NumbersRepository]
      val three3 = new ThreeThreeThree(numbersRepo)
      val three3Spy = spy(three3)

      when(three3Spy.getNumbers()).thenReturn(Future(Seq()))

      val headThreesFuture = three3.getHeadThrees()

      headThreesFuture.map(headThrees => {
        assert(headThrees == Seq())
      })
    }

    it("たくさんの数字から頭文字が3の数字だけを取り出す") {
      val numbersRepo = mock[NumbersRepository]
      val three3 = new ThreeThreeThree(numbersRepo)
      val three3Spy = spy(three3)

      when(three3Spy.getNumbers()).thenReturn(Future(numbers))

      val headThreesFuture = three3.getHeadThrees()

      headThreesFuture.map(headThrees => {
        assert(headThrees == Seq(3, 3, 30, 3, 30, 3, 33, 35))
      })
    }
  }

  describe("getLength3Numbers") {
    it("空の数列からは、長さが3の数字は取り出せない") {
      val numbersRepo = mock[NumbersRepository]
      val three3 = new ThreeThreeThree(numbersRepo)
      val three3Spy = spy(three3)

      when(three3Spy.getNumbers()).thenReturn(Future(Seq()))

      val length3NumbersFuture = three3.getLength3Numbers()

      length3NumbersFuture.map(length3Numbers => {
        assert(length3Numbers == Seq())
      })
    }

    it("たくさんの数字から長さが3の数字だけを取り出す") {
      val numbersRepo = mock[NumbersRepository]
      val three3 = new ThreeThreeThree(numbersRepo)
      val three3Spy = spy(three3)

      when(three3Spy.getNumbers()).thenReturn(Future(numbers))

      val length3NumbersFuture = three3.getLength3Numbers()

      length3NumbersFuture.map(length3Numbers => {
        assert(length3Numbers == Seq(100, 100, 200, 700))
      })
    }

    describe("sumHeadThreesAndLength3") {
      it("空の数列から頭文字が3の数列と長さが3の数列をそれぞれ取り出し、その合計値を求めようとしても0である") {
        val numbersRepo = mock[NumbersRepository]
        val three3 = new ThreeThreeThree(numbersRepo)
        val three3Spy = spy(three3)

        when(three3Spy.getNumbers()).thenReturn(Future(Seq()))

        val sumHeadThreesAndLength3Future = three3.sumHeadThreesAndLength3()

        sumHeadThreesAndLength3Future.map(sumHeadThreesAndLength3 => {
          assert(sumHeadThreesAndLength3 == 0)
        })
      }
    }

    it("たくさんの数字から頭文字が3の数列と長さが3の数列をそれぞれ取り出し、その合計値を求める") {
      val numbersRepo = mock[NumbersRepository]
      val three3 = new ThreeThreeThree(numbersRepo)
      val three3Spy = spy(three3)

      when(three3Spy.getNumbers()).thenReturn(Future(numbers))

      val sumHeadThreesAndLength3Future = three3.sumHeadThreesAndLength3()

      sumHeadThreesAndLength3Future.map(sumHeadThreesAndLength3 => {
        assert(sumHeadThreesAndLength3 == 1240)
      })
    }
  }
}