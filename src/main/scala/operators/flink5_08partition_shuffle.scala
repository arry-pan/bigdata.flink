package operators

import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala.createTypeInformation
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

object flink5_08partition_shuffle {
  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    val stream: DataStream[UserEvent] = env.addSource(new ClickSource)

//    1. 随机分区（shuffle）
    stream.shuffle.print("shuffle").setParallelism(4)

    env.execute()

  }
}
