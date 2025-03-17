package com.systeminfos.springboot3demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;

@MapperScan("com.systeminfos.springboot3demo.mapper")
@SpringBootApplication(exclude = RedisReactiveAutoConfiguration.class)
public class Springboot3demoApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot3demoApplication.class, args);
    }

}
