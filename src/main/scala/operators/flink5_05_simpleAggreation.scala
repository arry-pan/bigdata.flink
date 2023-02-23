package operators

import org.apache.flink.api.java.functions.KeySelector
import org.apache.flink.api.scala.createTypeInformation
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

object flink5_05_simpleAggreation {
  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    val stream: DataStream[UserEvent] = env.fromElements(
      new UserEvent("Mary", "./home", 1000L),
      new UserEvent("Bob", "./cart", 2000L),
      new UserEvent("Mary", "./cart", 3000L)
    )

    //1.lamdba表达式
//    stream.keyBy(e=>e.user).sum(2).print()
//    stream.keyBy(e=>e.user).sum("timestamp").print()
//    stream.keyBy(e=>e.user).max(2).print()
//    stream.keyBy(e=>e.user).max("timestamp").print()
    stream.keyBy(e=>e.user).maxBy(2).print()
//    stream.keyBy(e=>e.user).maxBy("timestamp").print()


    env.execute()
  }

}
