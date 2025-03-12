package com.systeminfos.bigdata.topn;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class TopNMapper extends Mapper<LongWritable, Text, IntWritable, MovieWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        /**
         *  读取一行数据，得到一个json ，解析json ，获取 uid 和 name  和评分
         */
        String jsonStr = value.toString();
        // 如何将一个json解析呢？ 可以使用如下工具类： jackson fastjson gson 等

        ObjectMapper objectMapper = new ObjectMapper();
        MovieWritable movie = objectMapper.readValue(jsonStr, MovieWritable.class);
        System.out.println("mapper端数据：" + movie);
        context.write(new IntWritable(Integer.valueOf(movie.getUid())), movie);
    }
}

class TopNReducer extends Reducer<IntWritable, MovieWritable, Text, NullWritable> {

    @Override
    protected void reduce(IntWritable key, Iterable<MovieWritable> values, Context context) throws IOException, InterruptedException {
        //  uid  后面是他评价的所有电影信息

        // 后面开始进行排序，取前五名
        List<MovieWritable> list = new ArrayList<MovieWritable>();
        for (MovieWritable movie : values) {
            // 不能在这个地方直接add ,否则数据会重复
            // list.add(movie);
            MovieWritable rate = new MovieWritable();
            rate.setRate(movie.getRate());
            rate.setMovie(movie.getMovie());
            rate.setTimeStamp(movie.getTimeStamp());
            rate.setUid(movie.getUid());
            list.add(rate);
        }
        System.out.println(list);
        // 排好了顺序
        Collections.sort(list, new Comparator<MovieWritable>() {
            @Override
            public int compare(MovieWritable m1, MovieWritable m2) {
                return m2.getRate() - m1.getRate();
            }
        });

        System.out.println(list);

        // 取前五个
        int length = Math.min(5, list.size());
        // StringBuffer 和 StringBuilder      StringBuffer是线程安全的
        StringBuffer sb = new StringBuffer(key.get() + "最喜欢的五部的电影是：\n");
        for (int i = 0; i < length; i++) {
            MovieWritable movie = list.get(i);
            sb.append(movie.getMovie() + ",分数为：" + movie.getRate() + "\n");
        }
        context.write(new Text(sb.toString()), NullWritable.get());

    }
}

public class TopNDriver {

    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        // 使用本地的文件系统，而不是hdfs
        configuration.set("fs.defaultFS", "file:///");
        // 使用本地的资源（CPU,内存等）, 也可以使用yarn平台跑任务
        configuration.set("mapreduce.framework.name", "local");
        Job job = Job.getInstance(configuration, "电影排名");

        // 指定 map
        job.setMapperClass(TopNMapper.class);
        // hello 1
        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(MovieWritable.class);

        job.setReducerClass(TopNReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        // 此处也可以使用绝对路径
        FileInputFormat.setInputPaths(job, "/Users/zhangqi/Documents/Workspaces/systeminfos/systeminfos-java-parent/data-parent/hadoop-demo/src/main/resources/topn/input");
        FileOutputFormat.setOutputPath(job, new Path("/Users/zhangqi/Documents/Workspaces/systeminfos/systeminfos-java-parent/data-parent/hadoop-demo/src/main/resources/topn/output"));

        boolean result = job.waitForCompletion(true);

        // 返回结果如果为true表示任务成功了，正常退出，否则非正常退出
        System.exit(result ? 0 : -1);
    }
}
