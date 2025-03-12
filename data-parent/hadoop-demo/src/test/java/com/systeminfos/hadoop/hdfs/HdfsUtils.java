package com.systeminfos.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class HdfsUtils {

    private FileSystem fileSystem;

    @Before
    public void setUp() throws IOException {
        System.setProperty("HADOOP_USER_NAME", "root");
        // 在每个测试方法之前执行的准备工作
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://bigdata01:8020"); // 设置 HDFS 地址
        fileSystem = FileSystem.get(conf);

    }

    @After
    public void tearDown() throws IOException {
        // 在每个测试方法之后执行的清理工作
        fileSystem.close();
    }

    @Test
    public void testUploadFile() throws IOException {
        String localFilePath = "/Users/zhangqi/Documents/Workspaces/systeminfos/systeminfos-java-parent/data-parent/hadoop-demo/src/test/resources/edip";
        String hdfsFilePath = "/hadoop/input/";
        fileSystem.copyFromLocalFile(new Path(localFilePath), new Path(hdfsFilePath));

    }

    @Test
    public void testCreateFile() throws IOException {
        String hdfsFilePath = "/hadoop/input/newfile.txt";
        fileSystem.createNewFile(new Path(hdfsFilePath));
    }

    @Test
    public void testDownloadFile() throws IOException {
        String hdfsFilePath = "/hadoop/input/newfile.txt";
        String localFilePath = "/Users/zhangqi/Documents/Workspaces/systeminfos/systeminfos-java-parent/data-parent/hadoop-demo/src/test/resources/file.txt";
        fileSystem.copyToLocalFile(new Path(hdfsFilePath), new Path(localFilePath));
    }

    @Test
    public void testDeleteFile() throws IOException {
        String hdfsFilePath = "/hadoop/input/newfile.txt";
        fileSystem.delete(new Path(hdfsFilePath), false);
    }

    @Test
    public void testMkDir() throws IOException {
        fileSystem.mkdirs(new Path("/hadoop/input"));
        System.out.println("创建文件夹成功");
    }

    @Test
    public void testRename() throws IOException {
        fileSystem.rename(new Path("/hadoop/input/edip"), new Path("/hadoop/input/aaa.txt"));
    }

}