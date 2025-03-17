package com.systeminfos.shardingJdbc31;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.systeminfos.shardingJdbc31.mapper")
public class ShardJdbc31Application {

    public static void main(String[] args) {
        SpringApplication.run(ShardJdbc31Application.class, args);
    }

}
