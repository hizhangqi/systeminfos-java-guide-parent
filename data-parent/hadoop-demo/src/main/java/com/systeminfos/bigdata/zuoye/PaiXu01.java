package com.systeminfos.bigdata.zuoye;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;


class PaiXuMapper2 extends Mapper<LongWritable, Text, IntWritable2, Text> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String s = value.toString();
        String[] split = s.split("\\s+");
        int visitNum = Integer.valueOf(split[1]);
        String name = split[0];
        context.write(new IntWritable2(visitNum), new Text(name));
    }
}

public class PaiXu01 {

    public static void main(String[] args) throws Exception {

        Configuration configuration = new Configuration();
        // 使用本地的文件系统，而不是hdfs
        configuration.set("fs.defaultFS", "file:///");
        // 使用本地的资源（CPU,内存等）, 也可以使用yarn平台跑任务
        configuration.set("mapreduce.framework.name", "local");
        Job job = Job.getInstance(configuration, "排序01");

        // 指定 map
        job.setMapperClass(PaiXuMapper2.class);
        // hello 1
        job.setMapOutputKeyClass(IntWritable2.class);
        job.setMapOutputValueClass(Text.class);

        // 此处也可以使用绝对路径
        FileInputFormat.setInputPaths(job, "../WordCount/mr04/input/");
        FileOutputFormat.setOutputPath(job, new Path("../WordCount/mr04/output2"));

        boolean result = job.waitForCompletion(true);

        // 返回结果如果为true表示任务成功了，正常退出，否则非正常退出
        System.exit(result ? 0 : -1);
    }
}