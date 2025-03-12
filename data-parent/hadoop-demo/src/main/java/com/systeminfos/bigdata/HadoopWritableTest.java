package com.systeminfos.bigdata;

import org.apache.hadoop.io.*;

import java.io.*;

public class HadoopWritableTest {
    public static void main(String[] args) throws IOException {
        Text name = new Text("Alice");
        IntWritable age = new IntWritable(30);

        // 序列化
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        DataOutputStream dataOut = new DataOutputStream(byteOut);
        name.write(dataOut);
        age.write(dataOut);

        byte[] serializedData = byteOut.toByteArray();
        System.out.println("Writable序列化数据大小: " + serializedData.length);

        // 反序列化
        DataInputStream dataIn = new DataInputStream(new ByteArrayInputStream(serializedData));
        Text deserializedName = new Text();
        IntWritable deserializedAge = new IntWritable();
        deserializedName.readFields(dataIn);
        deserializedAge.readFields(dataIn);
    }
}