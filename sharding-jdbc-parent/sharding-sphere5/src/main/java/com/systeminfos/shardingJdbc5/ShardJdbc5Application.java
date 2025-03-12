package com.systeminfos.shardingJdbc5;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.systeminfos.shardingJdbc5.mapper")
@ComponentScan(basePackages = { "com.systeminfos"})
public class ShardJdbc5Application {

    public static void main(String[] args) {
        SpringApplication.run(ShardJdbc5Application.class, args);
    }

}
