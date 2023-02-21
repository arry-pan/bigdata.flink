package DataStreamAPI;


import akka.protobuf.Descriptors;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.io.PojoCsvInputFormat;
import org.apache.flink.api.java.io.RowCsvInputFormat;
import org.apache.flink.api.java.typeutils.PojoField;
import org.apache.flink.api.java.typeutils.PojoTypeInfo;
import org.apache.flink.api.java.typeutils.TypeExtractor;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.types.Row;

import java.util.ArrayList;
import java.util.List;

public class Flink02_source_readFile {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.setParallelism(1);

//        //1、从文件中读取数据
//        DataStreamSource<String> stream1 = env.readTextFile("C:\\Users\\panshirui\\Documents\\user.csv");
//        stream1.print();

//        //2.从文件读取数据，返回类型
//        TypeInformation[] fieldTypes = new TypeInformation[]{BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.STRING_TYPE_INFO,BasicTypeInfo.LONG_TYPE_INFO};
//        Path path = new Path("C:\\Users\\panshirui\\Documents\\user.csv");
//        RowCsvInputFormat rowCsvInputFormat = new RowCsvInputFormat(path, fieldTypes);
//        DataStreamSource<Row> rowDataStreamSource = env.readFile(rowCsvInputFormat, "C:\\Users\\panshirui\\Documents\\user.csv");
//        rowDataStreamSource.print();


        //3.读取csv，返回POJO
        Path path1 = new Path("C:\\Users\\panshirui\\Documents\\user.csv");
        List<PojoField> fields = new ArrayList<PojoField>();
        PojoTypeInfo<Event> typeInfo = new PojoTypeInfo<>(Event.class, fields);


        PojoCsvInputFormat pojoCsvInputFormat = new PojoCsvInputFormat(path1, typeInfo);
        DataStreamSource pojoStream = env.readFile(pojoCsvInputFormat, "C:\\Users\\panshirui\\Documents\\user.csv");

        pojoStream.print();

        env.execute();

    }
}
