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
}
