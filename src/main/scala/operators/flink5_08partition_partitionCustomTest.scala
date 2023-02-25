package operators

import org.apache.flink.api.common.functions.Partitioner
import org.apache.flink.api.scala.createTypeInformation
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

object flink5_08partition_partitionCustomTest {
  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    val stream: DataStream[Int] = env.fromElements(1,2,3,4,5,6)

    //自定义分区，使用partitionCustom
    //参数1.实现Partitioner分区器
    //参数1.获取key
    stream.partitionCustom(new Partitioner[Int] {
      override def partition(key: Int, numPartitions: Int) = (key + 1) % 2
    }, data=>data)
      .print()
      .setParallelism(4)

    env.execute()

  }
}
