import org.apache.flink.api.scala.createTypeInformation
import org.apache.flink.streaming.api.scala.{DataStream, KeyedStream, StreamExecutionEnvironment}

object flink02_StreamWordCount {
  def main(args: Array[String]): Unit = {
    //     1. 创建执行环境
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    //2.读取文件
    val lines: DataStream[String] = env.socketTextStream("localhost",9999)

    // 3. 转换数据格式
    val wordToOne: DataStream[(String, Long)] = lines.flatMap(_.split(" ")).map((_, 1L))
    // 4. 分组
    val wordGroup: KeyedStream[(String, Long), String] = wordToOne.keyBy(_._1)
    // 5. 求和
    val wordCount: DataStream[(String, Long)] = wordGroup.sum(1)
    // 6. 打印
    wordCount.print
    // 7. 执行
    env.execute
  }

}
