package com.systeminfos.bigdata.zuoye;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @基本功能:
 * @program:MapReduce
 * @author: 闫哥
 * @create:2024-01-24 14:13:46
 **/
class FlowCleanMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

    Text text;

    @Override
    protected void setup(Mapper<LongWritable, Text, Text, NullWritable>.Context context) throws IOException, InterruptedException {

        text = new Text();
    }

    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, NullWritable>.Context context) throws IOException, InterruptedException {
        String line = value.toString();
        // 过滤掉第一行
        if (key.get() != 0) {
            String[] arr = line.split("\\s+");

            String phone = arr[1];
            String upFlow = arr[arr.length - 3];
            String downFlow = arr[arr.length - 2];
            if (phone.equals("null") || upFlow.equals("null") || downFlow.equals("null") || arr.length != 9) {
                return;
            }
            if (!phone.matches("1[3-9][0-9]{9}")) {
                return;
            }
            StringBuffer sb = new StringBuffer();
            sb.append(phone + " ").append(upFlow + " ").append(downFlow);
            text.set(sb.toString());
            context.write(text, NullWritable.get());
        }
    }
}

public class FlowCleanDriver {

    public static void main(String[] args) throws Exception {

        Configuration configuration = new Configuration();
        configuration.set("HADOOP_USER_NAME", "root");
        configuration.set("fs.defaultFS", "file:///");
        configuration.set("mapreduce.framework.name", "local");
        Job job = Job.getInstance(configuration, "数据清洗");

        job.setMapperClass(FlowCleanMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);

        // 不走reduce ，只运行map
        job.setNumReduceTasks(0);

        FileInputFormat.setInputPaths(job, args[0]);
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // 这个地方的true表示运行一部分，就提交一部分的进度日志
        boolean b = job.waitForCompletion(true);
        System.exit(b ? 0 : -1);
    }

}