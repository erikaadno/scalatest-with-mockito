package example

import com.google.inject.Inject

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * 3に魅力を感じるクラス
  */
class ThreeThreeThree @Inject()(numbersRepository: NumbersRepository) {
  def getNumbers(): Future[Seq[Int]] = numbersRepository.list()
}
