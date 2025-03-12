package com.systeminfos.bigdata;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @Author laoyan
 * @Description TODO
 * @Date 2022/8/1 14:06
 * @Version 1.0
 */
public class UserWritable implements Writable {

    private String name;
    private int age;

    public UserWritable(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // 序列化
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(name);
        out.writeInt(age);
    }

    // 反序列化
    @Override
    public void readFields(DataInput in) throws IOException {

        // 进行反序列化的时候，读取的顺序一定要跟序列化的时候的顺序一致，否则报错
        name = in.readUTF();
        age = in.readInt();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
