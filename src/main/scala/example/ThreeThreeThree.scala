package example

import com.google.inject.Inject

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * 3に魅力を感じるクラス
  */
class ThreeThreeThree @Inject()(numbersRepository: NumbersRepository) {
  def getNumbers(): Future[Seq[Int]] = numbersRepository.list()

  def getThrees(): Future[Seq[Int]] = getNumbers().map(_.filter(_ == 3))

  def getHeadThrees(): Future[Seq[Int]] = getNumbers().map(_.filter(_.toString.startsWith("3")))

  def getLength3Numbers(): Future[Seq[Int]] = getNumbers().map(_.filter(_.toString.length == 3))

  def sumHeadThreesAndLength3(): Future[Int] = Future(0)
}
