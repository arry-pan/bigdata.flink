package operators

import org.apache.flink.api.common.serialization.SimpleStringEncoder
import org.apache.flink.api.scala.createTypeInformation
import org.apache.flink.core.fs.Path
import org.apache.flink.streaming.api.functions.sink.filesystem.rollingpolicies.DefaultRollingPolicy
import org.apache.flink.streaming.api.functions.sink.filesystem.{RollingPolicy, StreamingFileSink}
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

import java.util.concurrent.TimeUnit

object flink5_09sink_file {
  def main(args: Array[String]): Unit = {

    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)

    val stream: DataStream[UserEvent] = env.addSource(new ClickSource)

    //以文本形式写入到文件
    val fileSink: StreamingFileSink[String] = StreamingFileSink
      //行编码
      .forRowFormat(new Path("./output"), new SimpleStringEncoder[String]("utf-8"))
      .withRollingPolicy(
        //设置文件滚动策略
        DefaultRollingPolicy.builder()
          .withRolloverInterval(TimeUnit.MINUTES.toMillis(15)) //累计1分钟
          .withInactivityInterval(TimeUnit.MINUTES.toMillis(5)) //5分钟不活动
          .withMaxPartSize(1024 *1024*1024) //文件大小达到1G
          .build()
      )
      .build()

    //先转换为String，再添加fileSink
    stream.map(_.toString).addSink(fileSink)
      //并行写入4个文件
      .setParallelism(4)

    env.execute()

  }
}
