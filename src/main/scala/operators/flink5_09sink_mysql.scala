package operators

import org.apache.flink.api.common.RuntimeExecutionMode
import org.apache.flink.api.scala.createTypeInformation
import org.apache.flink.connector.jdbc.{JdbcConnectionOptions, JdbcSink, JdbcStatementBuilder}
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

import java.sql.PreparedStatement

object flink5_09sink_mysql {
  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

//    val stream: DataStream[UserEvent] = env.fromElements(
//      new UserEvent("Mary1", "./home", 1000L),
//      new UserEvent("Bob1", "./cart", 2000L),
//      new UserEvent("Mary2", "./home", 3000L),
//      new UserEvent("Bob2", "./cart", 4000L)
//    )

    env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC); //设置为批处理
    val stream: DataStream[UserEvent] = env.addSource(new ClickSource)

    stream.print()

    stream.addSink(JdbcSink.sink(
      "insert into clicks(user,url) values(?,?)",
      new JdbcStatementBuilder[UserEvent]{
        override def accept(t: PreparedStatement, u: UserEvent) = {
          t.setString(1,u.user)
          t.setString(2,u.url)
        }
      },
      new JdbcConnectionOptions.JdbcConnectionOptionsBuilder()
        .withUrl("jdbc:mysql://hadoop102:3306/spark_test")
        .withDriverName("com.mysql.jdbc.Driver")
        .withUsername("root")
        .withPassword("psr,119")
        .build()
    ))


    env.execute()

  }
}
