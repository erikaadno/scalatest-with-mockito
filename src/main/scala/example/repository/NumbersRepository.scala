package example.repository

import com.google.inject.name.{Named, Names}
import com.google.inject.{AbstractModule, Inject}
import scalikejdbc._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * 数字をたくさん返すマン
  */
trait NumbersRepository {
  def list(): Future[Seq[Int]]

  def listN(n: Int): Future[Seq[Int]]
}

case class Number(id: Long, num: Int)

object Number extends SQLSyntaxSupport[Number] {
  override val tableName = "numbers"

  def apply(rs: WrappedResultSet) = new Number(
    rs.long("id"), rs.int("num"))
}


class NumbersRepositoryImpl @Inject()(implicit @Named("session") session: DBSession) extends NumbersRepository {
  override def list(): Future[Seq[Int]] = Future {
    sql"select * from numbers".map(rs => Number(rs)).list.apply().map(_.num)
  }

  override def listN(n: Int): Future[Seq[Int]] = Future {
    sql"select * from numbers limit $n".map(rs => Number(rs)).list.apply().map(_.num)
  }
}

class NumbersRepositoryModule(implicit session: DBSession) extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[DBSession]).annotatedWith(Names.named("session")).toInstance(session)
    bind(classOf[NumbersRepository]).to(classOf[NumbersRepositoryImpl])
  }
}