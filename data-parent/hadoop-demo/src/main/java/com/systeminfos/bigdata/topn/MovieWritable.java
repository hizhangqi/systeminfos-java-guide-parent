package com.systeminfos.bigdata.topn;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
* @Author laoyan
* @Description TODO
* @Date 2022/8/2 14:26
* @Version 1.0
*/
public class MovieWritable implements Writable {
    
    private String movie;
    private int rate;
    private String timeStamp;
    private String uid;
    
    public MovieWritable() {
    }
    
    public MovieWritable(String movie, int rate, String timeStamp, String uid) {
        this.movie = movie;
        this.rate = rate;
        this.timeStamp = timeStamp;
        this.uid = uid;
    }
    
    public String getMovie() {
        return movie;
    }
    
    public void setMovie(String movie) {
        this.movie = movie;
    }
    
    public int getRate() {
        return rate;
    }
    
    public void setRate(int rate) {
        this.rate = rate;
    }
    
    public String getTimeStamp() {
        return timeStamp;
    }
    
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
    
    public String getUid() {
        return uid;
    }
    
    public void setUid(String uid) {
        this.uid = uid;
    }
    
    @Override
    public String toString() {
        return "RatingWritable{" +
            "movie='" + movie + '\'' +
            ", rate=" + rate +
            ", timeStamp='" + timeStamp + '\'' +
            ", uid='" + uid + '\'' +
            '}';
    }
    
    @Override
    public void write(DataOutput out) throws IOException {
        
        out.writeUTF(movie);
        out.writeInt(rate);
        out.writeUTF(timeStamp);
        out.writeUTF(uid);
    }
    
    @Override
    public void readFields(DataInput in) throws IOException {
        
        movie = in.readUTF();// ctrl + d
        rate = in.readInt();
        timeStamp = in.readUTF();
        uid = in.readUTF();
    }
}
