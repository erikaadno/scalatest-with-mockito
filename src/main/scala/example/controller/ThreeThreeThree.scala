package example.controller

import com.google.inject.Inject
import example.repository.NumbersRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * 3に魅力を感じるクラス
  */
class ThreeThreeThree @Inject()(numbersRepository: NumbersRepository) {
  def getNumbers: Future[Seq[Int]] = numbersRepository.list()

  def get3Numbers(): Future[Seq[Int]] = numbersRepository.listN(3)

  def getThrees: Future[Seq[Int]] = getNumbers.map(_.filter(_ == 3))

  def getHeadThrees: Future[Seq[Int]] = getNumbers.map(_.filter(_.toString.startsWith("3")))

  def getLength3Numbers: Future[Seq[Int]] = getNumbers.map(_.filter(_.toString.length == 3))

  def sumHeadThreesAndLength3(): Future[Int] =
    for {
      headThrees <- getHeadThrees
      length3Numbers <- getLength3Numbers
    } yield headThrees.sum + length3Numbers.sum
}
