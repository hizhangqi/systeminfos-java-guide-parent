package com.systeminfos.bigdata.zuoye;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class IntWritable2 implements WritableComparable<IntWritable2> {

    private int value;

    public IntWritable2() {
    }

    public IntWritable2(int value) {
        set(value);
    }

    /**
     * Set the value of this IntWritable.
     */
    public void set(int value) {
        this.value = value;
    }

    /**
     * Return the value of this IntWritable.
     */
    public int get() {
        return value;
    }

    // 这个方法别人忙你调用的，只要存在排序的地方就会调用这个方法。
    // java 基础  比如各种排序方法  Collections.sort(list)
    @Override
    public int compareTo(IntWritable2 o) {

        int thisValue = this.value;
        int thatValue = o.get();
        return (thisValue > thatValue ? -1 : (thisValue == thatValue ? 0 : 1));

    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(value);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        value = in.readInt();
    }

    @Override
    public String toString() {
        return value + "";
    }
}
