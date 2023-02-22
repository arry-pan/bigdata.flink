package operators

import org.apache.flink.api.common.functions.{FilterFunction, MapFunction}
import org.apache.flink.api.scala.{createTypeInformation, scalaNothingTypeInfo}
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

object flink5_filter {
  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    val stream: DataStream[UserEvent] = env.fromElements(
      new UserEvent("Mary2", "./home", 1000L),
      new UserEvent("Bob2", "./cart", 2000L),
      new UserEvent("Mary", "./cart", 3000L)
    )

    //1.匿名函数
    stream.filter(new FilterFunction[UserEvent] {
      override def filter(value: UserEvent) = value.url.contains("home")
    }).print()

    //2. 自定义函数
    stream.filter(new userFilter()).print()

    //3. ladmbal表达式
    stream.filter(_.user.equals("Mary")).print()

    env.execute()

    class userFilter extends FilterFunction[UserEvent]{
      override def filter(value: UserEvent): Boolean = value.timestamp > 1000 && value.timestamp < 3000
    }

  }

}
