package operators

import org.apache.flink.api.common.functions.{FlatMapFunction, MapFunction}
import org.apache.flink.api.scala.{createTypeInformation, scalaNothingTypeInfo}
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.util.Collector

object flink5_03flatmap {
  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    val stream: DataStream[UserEvent] = env.fromElements(
      new UserEvent("Mary2", "./home", 1000L),
      new UserEvent("Bob2", "./cart", 2000L)
    )

    stream.flatMap(new FlatMapFunction[UserEvent,String] {
      override def flatMap(value: UserEvent, out: Collector[String]) = {
        out.collect(value.user)
      }
    }).print()

    stream.flatMap(e=>List(e.user)).print()


    env.execute()


  }

}
