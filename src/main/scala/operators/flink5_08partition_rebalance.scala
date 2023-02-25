package operators

import org.apache.flink.api.scala.createTypeInformation
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

object flink5_08partition_rebalance {
  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    val stream: DataStream[UserEvent] = env.addSource(new ClickSource)

//    2. 轮询分区（Round-Robin）
//    stream.rebalance.print("rebalance").setParallelism(4)

    //并行度发生变化，这里默认也是rebalance
    stream.print("rebalance").setParallelism(4)


    env.execute()

  }
}
