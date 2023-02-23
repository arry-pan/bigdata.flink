package operators

import org.apache.flink.api.common.functions.MapFunction
import org.apache.flink.api.java.functions.KeySelector
import org.apache.flink.api.scala.createTypeInformation
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

object flink5_04_keyby {
  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    val stream: DataStream[UserEvent] = env.fromElements(
      new UserEvent("Mary", "./home", 1000L),
      new UserEvent("Bob", "./cart", 2000L),
      new UserEvent("Mary", "./cart", 3000L)
    )

    //1.lamdba表达式
    stream.keyBy(e=>e.user).print()

    //2. 匿名函数
    stream.keyBy(new KeySelector[UserEvent,String] {
      override def getKey(value: UserEvent) = {
        value.user
      }
    }).print()

    env.execute()
  }

}
