package com.systeminfos.shardingJdbc4;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.systeminfos.shardingJdbc4.mapper")
public class ShardJdbc4Application {

    public static void main(String[] args) {
        SpringApplication.run(ShardJdbc4Application.class, args);
    }

}
