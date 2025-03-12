package com.systeminfos.bigdata.zuoye;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @基本功能:
 * @program:MapReduce
 * @author: 闫哥
 * @create:2024-01-24 14:56:44
 **/
class StudentWritable implements WritableComparable<StudentWritable> {

    private String name;
    private String subject;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    private int score;


    @Override
    public int compareTo(StudentWritable o) {
        return o.getScore() - this.score;
    }

    @Override
    public void write(DataOutput out) throws IOException {

        out.writeUTF(name);
        out.writeUTF(subject);
        out.writeInt(score);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        name = in.readUTF();
        subject = in.readUTF();
        score = in.readInt();
    }

    @Override
    public String toString() {
        return name + " " + subject + " " + score;
    }
}

class ScoreMapper extends Mapper<LongWritable, Text, Text, StudentWritable> {

    Text text;
    StudentWritable student;

    @Override
    protected void setup(Mapper<LongWritable, Text, Text, StudentWritable>.Context context) throws IOException, InterruptedException {
        text = new Text();
        student = new StudentWritable();
    }

    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, StudentWritable>.Context context) throws IOException, InterruptedException {
        if (key.get() != 0) {
            String line = value.toString();
            String[] arr = line.split("\\s+");
            text.set(arr[1]);
            student.setName(arr[0]);
            student.setSubject(arr[1]);
            student.setScore(Integer.parseInt(arr[2]));
            context.write(text, student);
        }
    }
}

class ScoreReducer extends Reducer<Text, StudentWritable, Text, NullWritable> {
    Text text;

    @Override
    protected void setup(Reducer<Text, StudentWritable, Text, NullWritable>.Context context) throws IOException, InterruptedException {
        text = new Text();
    }

    @Override
    protected void reduce(Text key, Iterable<StudentWritable> values, Reducer<Text, StudentWritable, Text, NullWritable>.Context context) throws IOException, InterruptedException {
        ArrayList<StudentWritable> list = new ArrayList<>();
        for (StudentWritable student : values) {
            //list.add(student);
            StudentWritable studentWritable = new StudentWritable();
            studentWritable.setScore(student.getScore());
            studentWritable.setName(student.getName());
            studentWritable.setSubject(student.getSubject());
            list.add(studentWritable);
        }
        // 对list 进行排序
        Collections.sort(list);
        for (StudentWritable studentWritable : list) {
            text.set(studentWritable.toString());
            context.write(text, NullWritable.get());
        }
    }
}

public class ScoreDriver {


    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", "file:///");
        configuration.set("mapreduce.framework.name", "local");
        Job job = Job.getInstance(configuration, "自定义分组");

        job.setMapperClass(ScoreMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(StudentWritable.class);

        job.setPartitionerClass(StudentPartitioner.class);

        job.setReducerClass(ScoreReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);
        // 不走reduce ，只运行map
        job.setNumReduceTasks(3);

        job.setJarByClass(ScoreDriver.class);

        FileInputFormat.setInputPaths(job, args[0]);
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // 这个地方的true表示运行一部分，就提交一部分的进度日志
        boolean b = job.waitForCompletion(true);
        System.exit(b ? 0 : -1);
    }
}
