package com.systeminfos.bigdata.temp;

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

class TempMaxMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    Text year = null;
    IntWritable temp = null;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {

        year = new Text();
        temp = new IntWritable();
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 最终的结果   年份  温度
        String line = value.toString();
        // 获取年份
        String _year = line.substring(15, 19);
        // 获取温度以及符号
        int _temp = Integer.parseInt(line.substring(87, 92));
        String validateCode = line.substring(92, 93);//获取校验码
        if (_temp == 9999 || validateCode.matches("[^01459]")) {
            return; // 表示代码终止
        }
        year.set(_year);
        temp.set(_temp);
        context.write(year, temp);
    }
}

class TempMaxReducer extends Reducer<Text, IntWritable, Text, Text> {

    Text text = null;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        text = new Text();
    }

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        // 1901 [xxx,xxx,xxx]
        int maxTemp = Integer.MIN_VALUE; // 因为设置为哪个数字都不合适
        for (IntWritable temp : values) {
            // 求两个值的最大值
            maxTemp = Integer.max(maxTemp, temp.get());
        }


        text.set("这一年的最高温是" + maxTemp);
        context.write(key, text);
    }
}


public class TempMaxDriver {


    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        // 使用本地的文件系统，而不是hdfs
        configuration.set("fs.defaultFS", "file:///");
        // 使用本地的资源（CPU,内存等）, 也可以使用yarn平台跑任务
        configuration.set("mapreduce.framework.name", "local");
        Job job = Job.getInstance(configuration, "统计最高温");

        // 指定 map
        job.setMapperClass(TempMaxMapper.class);
        // hello 1
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        // 指定 reduce
        job.setReducerClass(TempMaxReducer.class);
        // hello 5
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        // 此处也可以使用绝对路径
        FileInputFormat.setInputPaths(job, "../WordCount/mr03/input/");
        FileOutputFormat.setOutputPath(job, new Path("../WordCount/mr03/output"));

        // 这个true表示将日志打印给前端，false 表只执行
        boolean result = job.waitForCompletion(true);

        // 返回结果如果为true表示任务成功了，正常退出，否则非正常退出
        System.exit(result ? 0 : -1);
    }
}