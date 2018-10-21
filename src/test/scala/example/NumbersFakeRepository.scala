package example

import com.google.inject.AbstractModule
import scalikejdbc._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class Number(id: Long, num: Int)

object Number extends SQLSyntaxSupport[Number] {
  override val tableName = "numbers"

  def apply(rs: WrappedResultSet) = new Number(
    rs.long("id"), rs.int("num"))
}


class NumbersFakeRepository extends NumbersRepository {
  val n = Number.syntax("n")

  override def list()(implicit session: DBSession): Future[Seq[Int]] = Future {
    sql"select * from numbers".map(rs => Number(rs)).list.apply().map(_.num)
  }

  override def listN(n: Int): Future[Seq[Int]] = Future(Seq())
}

class NumbersFakeRepositoryModule extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[NumbersRepository]).to(classOf[NumbersFakeRepository])
  }
}
