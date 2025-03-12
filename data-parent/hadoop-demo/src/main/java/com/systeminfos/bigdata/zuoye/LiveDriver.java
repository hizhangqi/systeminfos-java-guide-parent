package com.systeminfos.bigdata.zuoye;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @基本功能:
 * @program:MapReduce
 * @author: 闫哥
 * @create:2024-01-24 16:02:36
 **/
class LiveWritable implements WritableComparable<LiveWritable> {

    private String user;
    private int peopleNum;
    private int liveTime;


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(int peopleNum) {
        this.peopleNum = peopleNum;
    }

    public int getLiveTime() {
        return liveTime;
    }

    public void setLiveTime(int liveTime) {
        this.liveTime = liveTime;
    }

    @Override
    public int compareTo(LiveWritable o) {
        if (this.peopleNum == o.peopleNum) {
            return o.liveTime - this.liveTime;
        }
        return o.peopleNum - this.peopleNum;
    }

    @Override
    public void write(DataOutput out) throws IOException {

        out.writeUTF(user);
        out.writeInt(peopleNum);
        out.writeInt(liveTime);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        user = in.readUTF();
        peopleNum = in.readInt();
        liveTime = in.readInt();
    }

    @Override
    public String toString() {
        return user + " " + peopleNum + " " + liveTime;
    }
}

class LiveMapper extends Mapper<LongWritable, Text, LiveWritable, NullWritable> {

    LiveWritable liveWritable;

    @Override
    protected void setup(Mapper<LongWritable, Text, LiveWritable, NullWritable>.Context context) throws IOException, InterruptedException {
        liveWritable = new LiveWritable();
    }

    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, LiveWritable, NullWritable>.Context context) throws IOException, InterruptedException {
        String line = value.toString();
        if (key.get() != 0) {
            String[] arr = line.split("\\s+");
            liveWritable.setLiveTime(Integer.valueOf(arr[2]));
            liveWritable.setPeopleNum(Integer.valueOf(arr[1]));
            liveWritable.setUser(arr[0]);
            context.write(liveWritable, NullWritable.get());
        }
    }
}

public class LiveDriver {

    public static void main(String[] args) throws Exception {

        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", "file:///");
        configuration.set("mapreduce.framework.name", "local");
        Job job = Job.getInstance(configuration, "排序案例");

        job.setMapperClass(LiveMapper.class);
        job.setMapOutputKeyClass(LiveWritable.class);
        job.setMapOutputValueClass(NullWritable.class);


        FileInputFormat.setInputPaths(job, args[0]);
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // 这个地方的true表示运行一部分，就提交一部分的进度日志
        boolean b = job.waitForCompletion(true);
        System.exit(b ? 0 : -1);
    }

}
