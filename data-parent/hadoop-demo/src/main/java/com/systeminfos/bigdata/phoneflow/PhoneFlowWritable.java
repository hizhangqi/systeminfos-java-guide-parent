package com.systeminfos.bigdata.phoneflow;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @Author laoyan
 * @Description TODO
 * @Date 2022/8/1 14:29
 * @Version 1.0
 */
// 自定义一个数据类型
public class PhoneFlowWritable implements Writable {

    private String phone;
    private int upFlow;
    private int downFlow;

    // 此处需要指定一个空参数的构造方法，否则报错：
    // java.lang.NoSuchMethodException: com.bigdata.phoneflow.PhoneFlowWritable.<init>()
    public PhoneFlowWritable() {
    }

    public PhoneFlowWritable(String phone, int upFlow, int downFlow) {
        this.phone = phone;
        this.upFlow = upFlow;
        this.downFlow = downFlow;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getUpFlow() {
        return upFlow;
    }

    public void setUpFlow(int upFlow) {
        this.upFlow = upFlow;
    }

    public int getDownFlow() {
        return downFlow;
    }

    public void setDownFlow(int downFlow) {
        this.downFlow = downFlow;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(phone);
        out.writeInt(upFlow);
        out.writeInt(downFlow);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        phone = in.readUTF();
        upFlow = in.readInt();
        downFlow = in.readInt();
    }
}