package operators

import org.apache.flink.api.scala.createTypeInformation
import org.apache.flink.streaming.api.functions.source.{RichParallelSourceFunction, SourceFunction}
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

object flink5_08partition_rescale {
  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    // 这里使用了并行数据源的富函数版本
    // 这样可以调用 getRuntimeContext 方法来获取运行时上下文的一些信息
    val stream: DataStream[Int] = env.addSource(new RichParallelSourceFunction[Int] {
      override def run(ctx: SourceFunction.SourceContext[Int]) = {
        for (i <- 0 until 10) {
          // 将奇数发送到索引为 1 的并行子任务
          // 将偶数发送到索引为 2 的并行子任务
          if ((i % 2) == getRuntimeContext.getIndexOfThisSubtask) {
            ctx.collect(i + 1)
          }
        }
      }

      override def cancel() = {}
    })
      .setParallelism(2)


    //默认不重新分区
    stream.print("default").setParallelism(2)

      //使用rescale重分区，奇数在1,2分区，偶数在3,4分区
    stream.rescale.print("rescale").setParallelism(4)

      //使用rebalance重分区，奇数在偶数在每个分区打乱
    stream.rebalance.print("rescale").setParallelism(4)

    env.execute()

  }
}
