package com.systeminfos.bigdata.hive;

import org.apache.hadoop.hive.metastore.api.Table;
import org.apache.hadoop.hive.metastore.IMetaStoreClient;
import org.apache.hadoop.hive.metastore.HiveMetaStoreClient;
import org.apache.hadoop.conf.Configuration;
import org.apache.thrift.TException;

import java.util.List;

public class HiveMetastoreExample {
    public static void main(String[] args) throws TException {
        // 配置 Hive Metastore
        Configuration conf = new Configuration();
        conf.set("hive.metastore.uris", "thrift://bigdata01:9083");

        // 创建 Metastore 客户端
        IMetaStoreClient client = new HiveMetaStoreClient(conf);

        // 获取所有数据库
        List<String> databases = client.getAllDatabases();
        System.out.println("Databases: " + databases);

        // 获取指定数据库的所有表
        List<String> tables = client.getAllTables("videos");
        System.out.println("Tables in default database: " + tables);

        // 获取指定表的元数据
        Table table = client.getTable("videos", "stu");
        System.out.println("Table Metadata: " + table);

        // 关闭客户端
        client.close();
    }
}