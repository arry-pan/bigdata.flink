package operators

import org.apache.flink.api.common.functions.ReduceFunction
import org.apache.flink.api.java.functions.KeySelector
import org.apache.flink.api.scala.createTypeInformation
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

object flink5_06_reduce {
  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    val stream: DataStream[UserEvent] = env.fromElements(
      new UserEvent("Mary", "./home", 1000L),
      new UserEvent("Bob", "./cart", 2000L),
      new UserEvent("Bob", "./home", 2500L),
      new UserEvent("Mary", "./cart", 3000L),
      new UserEvent("Mary", "./cart", 4000L)
    )

    //1.lamdba表达式
    //统计用户点击量，取最大点击量用户
    stream.map(e=>(e.user, 1))
      //按用户分组
      .keyBy(_._1)
      // 每到一条数据，用户 pv 的统计值加 1
      .reduce((x,y)=>(x._1, x._2+y._2))
      /// 为每一条数据分配同一个 key，将聚合结果发送到一条流中
      .keyBy(_=>true)
      .reduce(new ReduceFunction[(String, Int)] {
        override def reduce(value1: (String, Int), value2: (String, Int)) = {
          // 将累加器更新为当前最大的 pv 统计值，然后向下游发送累加器的值
          if(value1._2 > value2._2) value1 else value2
        }
      })
      .print()

    env.execute()
  }

}
