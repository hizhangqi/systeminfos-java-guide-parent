package com.systeminfos.bigdata;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * @Author laoyan
 * @Description TODO
 * @Date 2022/8/1 11:22
 * @Version 1.0
 * <p>
 * Map任务 --> Partitioner  --> Reducer
 * Partitioner 其实就是Map端的输出
 */
public class WordCountPartitioner extends Partitioner<Text, IntWritable> {

    // 分区的区号，一定是从0开始的，中间不能断   0 1 2 3 4..
    @Override
    public int getPartition(Text text, IntWritable intWritable, int i) {
        // text就是一个单词
        String letter = text.toString();
        char c = letter.charAt(0);
        if (c >= 'a' && c <= 'p') {
            return 0;
        } else if (c >= 'q' && c <= 'z') {
            return 1;
        } else {
            return 2;
        }

    }
}