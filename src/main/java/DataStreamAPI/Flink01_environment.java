package DataStreamAPI;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import scala.collection.immutable.Seq;
public class Flink01_environment {

        public static void main(String[] args) throws Exception {

//                // 1、创建执行环境
//                // 自动适应执行环境
//                StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//
//                //2.创建本地执行环境
//                StreamExecutionEnvironment localEnv = StreamExecutionEnvironment.createLocalEnvironment(1);

                //3.创建集群执行环境
                StreamExecutionEnvironment remoteEnv = StreamExecutionEnvironment.createRemoteEnvironment(
                        "hadoop102",
                        43040,
                        "D:\\03.Projects\\bigdata.flink\\target\\flinkTutorial-1.0-SNAPSHOT.jar"
                );

                DataStreamSource<String> streamText = remoteEnv.socketTextStream("hadoop103", 9999);

                streamText.print();

                remoteEnv.execute();



        }

}
