package example

import com.google.inject.Inject
import scalikejdbc.{AutoSession, DBSession}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * 3に魅力を感じるクラス
  */
class ThreeThreeThree @Inject()(numbersRepository: NumbersRepository) {
  def getNumbers()(implicit session: DBSession): Future[Seq[Int]] = numbersRepository.list()

  def getThrees()(implicit session: DBSession): Future[Seq[Int]] = getNumbers().map(_.filter(_ == 3))

  def getHeadThrees()(implicit session: DBSession): Future[Seq[Int]] = getNumbers().map(_.filter(_.toString.startsWith("3")))

  def getLength3Numbers()(implicit session: DBSession): Future[Seq[Int]] = getNumbers().map(_.filter(_.toString.length == 3))

  def sumHeadThreesAndLength3()(implicit session: DBSession): Future[Int] =
    for{
      headThrees <- getHeadThrees()
      length3Numbers <- getLength3Numbers()
    } yield headThrees.sum + length3Numbers.sum
}
