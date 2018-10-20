package example

import scala.concurrent.Future

/**
  * 数字をたくさん返すマン
  */
trait NumbersRepository {
  def list(): Future[Seq[Int]]
}


class NumbersRepositoryImpl extends NumbersRepository {
  override def list(): Future[Seq[Int]] = ???
}
