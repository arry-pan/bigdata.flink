package operators

import org.apache.flink.api.common.functions.MapFunction
import org.apache.flink.api.scala.createTypeInformation
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

object flink5_01_map {
  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    val stream: DataStream[UserEvent] = env.fromElements(
      new UserEvent("Mary2", "./home", 1000L),
      new UserEvent("Bob2", "./cart", 2000L)
    )

    //1.匿名函数实现MapFunction
    stream.map(mapper = new MapFunction[UserEvent, String] {
      override def map(value: UserEvent) = value.user
    }).print()

    //2. 自定义函数实现MapFunction
    stream.map(new userMap()).print()

    //3. lamdbal表达式实现
    stream.map(_.timestamp).print()

    env.execute()

//    实现MapFunction接口，自定义map函数
    class userMap extends MapFunction[UserEvent, String]{
      override def map(value: UserEvent): String = value.url
    }
  }

}
