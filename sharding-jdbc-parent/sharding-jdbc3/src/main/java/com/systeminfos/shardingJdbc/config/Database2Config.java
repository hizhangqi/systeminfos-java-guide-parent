package com.systeminfos.shardingJdbc.config;

import javax.sql.DataSource;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.alibaba.druid.pool.DruidDataSource;

@Data
@ConfigurationProperties(prefix = "database2")
@PropertySource(value = {"classpath:database.properties"})
@Component
public class Database2Config {
	
	private String url;
    private String username;
    private String password;
    private String driverClassName;
    private String databaseName;

    public DataSource createDataSource() {
        DruidDataSource result = new DruidDataSource();
        result.setDriverClassName(getDriverClassName());
        result.setUrl(getUrl());
        result.setUsername(getUsername());
        result.setPassword(getPassword());
        System.out.println("DATABASE: "+ databaseName);
        return result;
    }
	
}
