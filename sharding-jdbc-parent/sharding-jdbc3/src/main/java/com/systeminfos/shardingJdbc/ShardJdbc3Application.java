package com.systeminfos.shardingJdbc;

import io.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, SpringBootConfiguration.class})
@MapperScan("com.systeminfos.shardingJdbc.mapper")
public class ShardJdbc3Application {

    public static void main(String[] args) {
        SpringApplication.run(ShardJdbc3Application.class, args);
    }

}
