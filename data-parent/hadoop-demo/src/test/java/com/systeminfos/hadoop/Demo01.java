package com.systeminfos.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;

/**
 * @Author laoyan
 * @Description TODO
 * @Date 2022/7/28 14:52
 * @Version 1.0
 */
public class Demo01 {

    @Test
    public void test01() throws IOException {
        // 配置的意思
        Configuration configuration = new Configuration();
        // hdfs的连接地址
        configuration.set("fs.defaultFS", "hdfs://bigdata01:8020");
        FileSystem fileSystem = FileSystem.get(configuration);
        System.out.println(fileSystem);
    }

    @Test
    public void test02() throws Exception {
        //  URL  和  URI
        //  URL  和  URI
        //  URL = 中华人民共和国
        // 指的是互联网上比较具体的第一个内容，地址
        //  URI = 共和国   员工的编号9527
        //  cookie  session
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://bigdata01:8020"), new Configuration());
        System.out.println(fileSystem);
    }

    @Test
    public void test03() throws Exception {
        // 配置的意思
        Configuration configuration = new Configuration();
        // hdfs的连接地址
        configuration.set("fs.defaultFS", "hdfs://bigdata01:8020");
        FileSystem fileSystem = FileSystem.newInstance(configuration);
        System.out.println(fileSystem);
    }

    @Test
    public void test04() throws Exception {
        // 配置的意思
        Configuration configuration = new Configuration();

        FileSystem fileSystem = FileSystem.newInstance(new URI("hdfs://bigdata01:8020"), configuration);
        System.out.println(fileSystem);
    }
}
