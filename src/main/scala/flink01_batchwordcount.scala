import org.apache.flink.api.scala.{DataSet, ExecutionEnvironment}

object flink01_batchwordcount {
  def main(args: Array[String]): Unit = {
//     1. 创建执行环境
    val env: ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment

    // 2. 从文件读取数据 按行读取(存储的元素就是每行的文本)
    val lines: DataSet[String] = env.readTextFile("input/word.txt")

    import org.apache.flink.api.scala._
    val sumValue: AggregateDataSet[(String, Int)] = lines.flatMap(_.split(" ")).map((_, 1)).groupBy(0).sum(1)

    sumValue.print()
  }
}
