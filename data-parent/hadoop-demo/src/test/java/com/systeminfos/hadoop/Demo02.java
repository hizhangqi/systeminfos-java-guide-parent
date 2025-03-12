package com.systeminfos.hadoop;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

public class Demo02 {

    @Test
    public void testUpload() throws Exception {
        System.setProperty("HADOOP_USER_NAME", "root");
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", "hdfs://bigdata01:8020");
        FileSystem fileSystem = FileSystem.get(configuration);
        Path localPath = new Path("/Users/zhangqi/Documents/Workspaces/systeminfos/systeminfos-java-parent/data-parent/hadoop-demo/src/test/resources/a.txt");
        Path hdfsPath = new Path("/hadoop/test");
        fileSystem.copyFromLocalFile(localPath, hdfsPath);
        System.out.println("上传成功！");

        //删除/a.txt
        hdfsPath = new Path("/a.txt");
        //fileSystem.delete(hdfsPath, true);
        System.out.println("删除成功！");
    }

}