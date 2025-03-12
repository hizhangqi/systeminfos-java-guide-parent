package com.systeminfos.bigdata.hive;

import org.apache.hadoop.hive.metastore.api.Database;
import org.apache.hadoop.hive.metastore.HiveMetaStoreClient;
import org.apache.hadoop.hive.conf.HiveConf;

public class HiveMetastoreDemo {
    public static void main(String[] args) {
        try {
            // 创建 HiveConf 对象
            HiveConf conf = new HiveConf();
            conf.setVar(HiveConf.ConfVars.METASTOREURIS, "thrift://bigdata01:9083");

            // 创建 Metastore 客户端
            HiveMetaStoreClient client = new HiveMetaStoreClient(conf);

            // 获取数据库列表
            for (String dbName : client.getAllDatabases()) {
                System.out.println("Database: " + dbName);
            }

            // 创建一个新数据库
            Database newDb = new Database();
            newDb.setName("test_db");
            newDb.setDescription("Test Database");
            newDb.setLocationUri("hdfs://bigdata01:8020/user/hive/warehouse/test_db");
            client.createDatabase(newDb);

            System.out.println("Created new database: test_db");

            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}