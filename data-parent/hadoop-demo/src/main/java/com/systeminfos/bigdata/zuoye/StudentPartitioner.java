package com.systeminfos.bigdata.zuoye;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * @基本功能:
 * @program:MapReduce
 * @author: 闫哥
 * @create:2024-01-24 15:05:53
 **/
public class StudentPartitioner extends Partitioner<Text, StudentWritable> {


    @Override
    public int getPartition(Text text, StudentWritable studentWritable, int numPartitions) {
        String subject = text.toString();
        if (subject.equals("语文")) {
            return 0;
        } else if (subject.equals("英语")) {
            return 1;
        } else {
            return 2;
        }
    }
}