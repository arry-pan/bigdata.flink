package DataStreamAPI;


import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.ArrayList;

public class Flink02_source_operator {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.setParallelism(1);

        //1、从集合中读取数据
        ArrayList<Event> clicks = new ArrayList<>();
        clicks.add(new Event("Mary","./home",1000L));
        clicks.add(new Event("Bob","./cart",2000L));
        DataStream<Event> stream = env.fromCollection(clicks);

        stream.print();


        //2、也可以直接从元素读取
        DataStreamSource<Event> stream2 = env.fromElements(
                new Event("Mary2", "./home", 1000L),
                new Event("Bob2", "./cart", 2000L)
        );

        stream2.print();

        env.execute();

    }
}
