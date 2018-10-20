package example

import org.mockito.Mockito._
import org.scalatest._
import org.scalatest.mockito.MockitoSugar

import scala.concurrent.Future

class ThreeThreeThreeSpec extends AsyncFunSpec with Matchers with MockitoSugar {
  val numbers = Seq(1, 2, 100, 3, 3, 100, 30, 200, 3, 30, 700, 20, 3, 33, 35)

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
    it("からの数列からは、長さが3の数字は取り出せない") {
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
  }
}
