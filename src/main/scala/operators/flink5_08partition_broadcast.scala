package operators

import org.apache.flink.api.scala.createTypeInformation
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

object flink5_08partition_broadcast {
  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    val stream: DataStream[UserEvent] = env.addSource(new ClickSource)

//    1. 广播分区（broadcast）
    stream.broadcast.print("广播分区").setParallelism(4)

//    2. 全局分区（global）
    stream.global.print("全局分区").setParallelism(4)

    env.execute()
  }
}
