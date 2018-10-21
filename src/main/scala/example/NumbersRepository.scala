package example

import scalikejdbc.{AutoSession, DBSession}

import scala.concurrent.Future

/**
  * 数字をたくさん返すマン
  */
trait NumbersRepository {
  def list()(implicit session: DBSession): Future[Seq[Int]]
  def listN(n: Int): Future[Seq[Int]]
}


class NumbersRepositoryImpl extends NumbersRepository {
  override def list()(implicit session: DBSession): Future[Seq[Int]] = ???

  override def listN(n: Int): Future[Seq[Int]] = ???
}