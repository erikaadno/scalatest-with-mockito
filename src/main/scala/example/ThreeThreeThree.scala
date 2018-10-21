package example

import com.google.inject.Inject
import com.google.inject.name.Named
import scalikejdbc.{AutoSession, DBSession}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * 3に魅力を感じるクラス
  */
class ThreeThreeThree @Inject()(numbersRepository: NumbersRepository)(implicit @Named("session") session: DBSession) {
  def getNumbers(): Future[Seq[Int]] = numbersRepository.list()

  def get3Numbers(): Future[Seq[Int]] = numbersRepository.listN(3)

  def getThrees(): Future[Seq[Int]] = getNumbers().map(_.filter(_ == 3))

  def getHeadThrees(): Future[Seq[Int]] = getNumbers().map(_.filter(_.toString.startsWith("3")))

  def getLength3Numbers(): Future[Seq[Int]] = getNumbers().map(_.filter(_.toString.length == 3))

  def sumHeadThreesAndLength3(): Future[Int] =
    for {
      headThrees <- getHeadThrees()
      length3Numbers <- getLength3Numbers()
    } yield headThrees.sum + length3Numbers.sum
}
