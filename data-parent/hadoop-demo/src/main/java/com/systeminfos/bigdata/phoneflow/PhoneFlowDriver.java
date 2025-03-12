package com.systeminfos.bigdata.phoneflow;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.Iterator;

/**
 * @Author laoyan
 * @Description TODO
 * @Date 2022/8/1 14:33
 * @Version 1.0
 */
class PhoneFlowMapper extends Mapper<LongWritable, Text, Text, PhoneFlowWritable> {

    // 将每一句话，都变为   手机号码 -->  PhoneFlowWritable对象
    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, PhoneFlowWritable>.Context context) throws IOException, InterruptedException {

        String line = value.toString();
        String[] arr = line.split("\\s+");// \s 表示一个空白符，+ 表示出现一次到多次
        String phone = arr[1];
        int upFlow = Integer.parseInt(arr[arr.length - 3]);
        int downFlow = Integer.parseInt(arr[arr.length - 2]);

        PhoneFlowWritable phoneFlowWritable = new PhoneFlowWritable(phone, upFlow, downFlow);
        context.write(new Text(phone), phoneFlowWritable);
    }
}

//   手机号 --> 流量数据PhoneFlowWritable      手机号码 --> 统计的结果
class PhoneFlowReducer extends Reducer<Text, PhoneFlowWritable, Text, Text> {

    @Override
    protected void reduce(Text key, Iterable<PhoneFlowWritable> values, Reducer<Text, PhoneFlowWritable, Text, Text>.Context context) throws IOException, InterruptedException {

        int upFlowNum = 0;
        int downFlowNum = 0;
        Iterator<PhoneFlowWritable> iterator = values.iterator();
        while (iterator.hasNext()) {
            PhoneFlowWritable phoneFlowWritable = iterator.next();
            upFlowNum += phoneFlowWritable.getUpFlow();
            downFlowNum += phoneFlowWritable.getDownFlow();
        }
        StringBuffer sb = new StringBuffer();
        sb.append("手机号" + key + "流量统计：");
        sb.append("上行流量是:" + upFlowNum);
        sb.append("下行流量是:" + downFlowNum);
        sb.append("总的流量是:" + (upFlowNum + downFlowNum));
        context.write(key, new Text(sb.toString()));

    }
}


public class PhoneFlowDriver {

    public static void main(String[] args) throws Exception {

        Configuration configuration = new Configuration();
        // 使用本地的文件系统，而不是hdfs
        configuration.set("fs.defaultFS", "file:///");
        // 使用本地的资源（CPU,内存等）, 也可以使用yarn平台跑任务
        configuration.set("mapreduce.framework.name", "local");
        Job job = Job.getInstance(configuration, "手机流量统计");
        // map任务的设置
        job.setMapperClass(PhoneFlowMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(PhoneFlowWritable.class);

        // reduce任务的设置
        job.setReducerClass(PhoneFlowReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        // 设置要统计的数据的路径，结果输出路径
        FileInputFormat.setInputPaths(job, new Path("/Users/zhangqi/Documents/Workspaces/systeminfos/systeminfos-java-parent/data-parent/hadoop-demo/src/main/resources/phoneflow/input"));
        // ouput文件夹一定不要出现，否则会报错
        FileOutputFormat.setOutputPath(job, new Path("/Users/zhangqi/Documents/Workspaces/systeminfos/systeminfos-java-parent/data-parent/hadoop-demo/src/main/resources/phoneflow/output"));
        // 等待任务执行结束
        boolean b = job.waitForCompletion(true);
        // 此处是一定要退出虚拟机的
        System.exit(b ? 0 : -1);
    }
}