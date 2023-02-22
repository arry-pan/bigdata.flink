package operators

import org.apache.flink.api.scala.createTypeInformation
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

object opetrator01_overview {
  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    //    1 Map :DataStream → DataStream
    val ds1: DataStream[Int] = env.fromElements(1, 2, 3, 4)
//    ds1.map(_*2).print()

//    2 FlatMap
    val ds2: DataStream[String] = env.fromElements("hello flink", "hello scala")
//    ds2.flatMap(_.split(" ")).print()

//    3 Filter
//    ds1.filter(_ % 2 == 0).print()

//    4 KeyBy DataStream → KeyedStream
val ds3: DataStream[(String, Int)] = env.fromCollection(List(("a", 1), ("b", 2), ("a", 3)))
//    ds3.keyBy(_._1).print()
//    ds3.keyBy(_._2).print()

//    Reduce: KeyedStream → DataStream
//    Reduce 输入，输出类型不变
val ds4: DataStream[(String, Int)] = env.fromCollection(List(("a", 1), ("b", 2), ("a", 3)))
    ds4.keyBy(_._1).reduce((x,y)=>(x._1, x._2 + y._2)).print()

    env.execute()

  }

}
