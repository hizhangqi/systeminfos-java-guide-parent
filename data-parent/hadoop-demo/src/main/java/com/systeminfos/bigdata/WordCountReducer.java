package com.systeminfos.bigdata;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

/**
 * @Author laoyan
 * @Description TODO
 * @Date 2022/8/1 9:52
 * @Version 1.0
 *
 * Reducer的四个泛型：
 * 1、因为map结果的输出就是reduce计算的输入，所以前面两个泛型其实是Map任务的输出结果的Key和Value的数据类型
 * 2、因为我们的输出结果是   Hello--> 5
 *      Key --> Hello  --> Text
 *      Value --> 5    --> Intwritable
 */
public class WordCountReducer extends Reducer<Text, IntWritable,Text,IntWritable> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
        // key  指的就是 某个单词
        // values 其实是一个集合  [1,1,1,1,1,1....]
        Iterator<IntWritable> iterator = values.iterator();
        /**
         *    int --> IntWritable    new IntWritable(int类型 的数据)
         *    IntWritable --> int     调用get方法即可
         *    String --> Text       new Text(String放入)
         *    Text --> String        toString 方法即可
         */
        int count = 0;
        while(iterator.hasNext()){
            IntWritable num = iterator.next();//  1
            count = count + num.get();
        }
        context.write(key,new IntWritable(count));
    }
}
