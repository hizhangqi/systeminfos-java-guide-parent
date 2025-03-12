package com.systeminfos.bigdata;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @Author laoyan
 * @Description TODO
 * @Date 2022/8/1 9:35
 * @Version 1.0
 * <p>
 * Mapper中的四个泛型跟什么照应：
 * 1、LongWritable  行偏移量，一般都是LongWritable ，这一行的数据是从第几个字符开始计算的，因为数据量很多这个值也会很大，所以使用Long
 * 2、Text     指的是这一行数据
 * 3、Text     Map任务输出的Key值的类型           单词
 * 4、IntWritable     Map任务输出的Key值的类型     1
 */
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    // Ctrl + o  可以展示哪些方法可以重写
    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {
        // key 值 指的是行偏移量
        // value 指的是 这一行数据
        //hello qianfeng hello 1999 hello beijing hello
        String line = value.toString();
        // [hello,qianfeng,hello,1999,hello,beijing,hello]
        String[] arr = line.split(" ");
        //  hello-> 1,qianfeng->1,hello->1,1999->1,hello->1,beijing->1,hello->1
        for (String word : arr) {
            context.write(new Text(word), new IntWritable(1));
        }

    }
}
