package DataStreamAPI;

import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.streaming.api.scala.DataStream;
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment;

public class Flink01_executionMode {
    public static void main(String[] args) {
        /*
        * 从 1.12.0 版本起，Flink 实现了 API 上的流批统一
        * */
//  批处理环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
//        基于 ExecutionEnvironment 读入数据创建的数据集合，就是 DataSet；
        DataSource<String> ds = env.readTextFile("input/data.txxt");

//        流处理环境
        StreamExecutionEnvironment env1 = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> ds1 = env1.readTextFile("input/data.txt");

//  从 1.12.0 版本起，Flink 实现了 API 上的流批统一。
//        （1）通过命令行配置
//        bin/flink run -Dexecution.runtime-mode=BATCH ...
//        （2）通过代码配置
        StreamExecutionEnvironment env2 = StreamExecutionEnvironment.getExecutionEnvironment();
        env2.setRuntimeMode(RuntimeExecutionMode.BATCH); //设置为批处理
//        建议: 不要在代码中配置，而是使用命令行。这同设置并行度是类似的：在提交作业时指
//        定参数可以更加灵活，同一段应用程序写好之后，既可以用于批处理也可以用于流处理。


    }
}
