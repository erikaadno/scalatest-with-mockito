package example

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{AsyncFunSpec, BeforeAndAfter, Matchers}

class UsersSpec extends AsyncFunSpec with Matchers with MockitoSugar with BeforeAndAfter {

  describe("first test") {
    it("失敗テスト") {
      assert(1 == 1)
    }
  }
}
