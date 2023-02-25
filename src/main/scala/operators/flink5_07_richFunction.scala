package operators

import org.apache.flink.api.common.functions.{MapFunction, RichMapFunction}
import org.apache.flink.api.scala.createTypeInformation
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

object flink5_07_richFunction {
  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    val stream: DataStream[UserEvent] = env.fromElements(
      new UserEvent("Mary1", "./home", 1000L),
      new UserEvent("Bob1", "./cart", 2000L),
      new UserEvent("Mary2", "./home", 3000L),
      new UserEvent("Bob2", "./cart", 4000L)
    )

    stream.map(new myRichMapFunction).print()

    env.execute()

//    map的富函数
    class myRichMapFunction extends RichMapFunction[UserEvent,String]{
      override def open(parameters: Configuration): Unit = {
        // 做一些初始化工作
        // 例如建立一个和 MySQL 的连接
        println(s"索引为${getRuntimeContext().getIndexOfThisSubtask}的任务开始")
      }

      override def map(value: UserEvent): String = value.user

      override def close(): Unit = {
        // 清理工作，关闭和 MySQL 数据库的连接。
        println(s"索引为${getRuntimeContext().getIndexOfThisSubtask}的任务结束")
      }
    }

  }
}
