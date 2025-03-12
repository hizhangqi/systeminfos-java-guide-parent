package com.systeminfos.bigdata;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @Author laoyan
 * @Description TODO
 * @Date 2022/8/1 10:02
 * @Version 1.0
 */
public class WordCountDriver {
    public static void main(String[] args) throws Exception {

        Configuration configuration = new Configuration();
        // 使用本地的文件系统，而不是hdfs
        configuration.set("fs.defaultFS","file:///");
        // 使用本地的资源（CPU,内存等）, 也可以使用yarn平台跑任务
        configuration.set("mapreduce.framework.name", "local");

        //configuration.set("HADOOP_USER_NAME", "root");
        //configuration.set("fs.defaultFS", "hdfs://bigdata01:8020");
        //configuration.set("mapreduce.framework.name", "yarn");
        Job job = Job.getInstance(configuration, "单词统计WordCount");
        // map任务的设置
        job.setMapperClass(WordCountMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        // reduce任务的设置
        job.setReducerClass(WordCountReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // 设置要统计的数据的路径，结果输出路径
        FileInputFormat.setInputPaths(job, new Path("/Users/zhangqi/Documents/Workspaces/systeminfos/systeminfos-java-parent/data-parent/hadoop-demo/src/main/resources/input"));
        // ouput文件夹一定不要出现，否则会报错
        FileOutputFormat.setOutputPath(job, new Path("/Users/zhangqi/Documents/Workspaces/systeminfos/systeminfos-java-parent/data-parent/hadoop-demo/src/main/resources/output3"));
        // 等待任务执行结束
        boolean b = job.waitForCompletion(true);
        // 此处是一定要退出虚拟机的
        System.exit(b ? 0 : -1);
    }
}