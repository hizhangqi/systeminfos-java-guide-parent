package com.systeminfos.springboot3demo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Arrays;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.single.host}")
    private String host;
    @Value("${spring.redis.single.port}")
    private int port;
    @Value("${spring.redis.cluster.nodes}")
    private String clusterNodes;
    @Value("${spring.redis.cluster.max-redirects}")
    private int maxRedirects;
    @Value("${spring.redis.sentinel.master}")
    private String sentinelMaster;
    @Value("${spring.redis.sentinel.nodes}")
    private String sentinelNodes;


    @Bean(name = "standaloneConnectionFactory")
    public RedisConnectionFactory standaloneConnectionFactory() {
        RedisConfiguration standaloneConnectionFactory = new RedisStandaloneConfiguration(host, port);
        return new LettuceConnectionFactory(standaloneConnectionFactory);
    }

    @Primary
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> defaultRedisTemplate(
            @Qualifier("standaloneConnectionFactory") RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        // 设置 key 和 value 的序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


    @Bean(name = "clusterConnectionFactory")
    public LettuceConnectionFactory clusterConnectionFactory() {
        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration();
        String[] split = clusterNodes.split(",");
        Arrays.stream(split).forEach(node -> clusterConfiguration.clusterNode(new RedisNode(node.split(":")[0], Integer.parseInt(node.split(":")[1]))));
        clusterConfiguration.setMaxRedirects(maxRedirects);
        return new LettuceConnectionFactory(clusterConfiguration);
    }

    @Bean(name = "clusterRedisTemplate")
    public RedisTemplate<String, Object> clusterRedisTemplate(@Qualifier("clusterConnectionFactory") LettuceConnectionFactory clusterConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(clusterConnectionFactory);
        // 设置 key 和 value 的序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean(name = "sentinelConnectionFactory")
    public LettuceConnectionFactory sentinelConnectionFactory() {
        RedisSentinelConfiguration sentinelConfiguration = new RedisSentinelConfiguration();
        sentinelConfiguration.master(sentinelMaster);
        String[] split = sentinelNodes.split(",");
        Arrays.stream(split).forEach(node -> sentinelConfiguration.sentinel(new RedisNode(node.split(":")[0], Integer.parseInt(node.split(":")[1]))));
        return new LettuceConnectionFactory(sentinelConfiguration);
    }

    @Bean(name = "sentinelRedisTemplate")
    public RedisTemplate<String, Object> sentinelRedisTemplate(@Qualifier("sentinelConnectionFactory") LettuceConnectionFactory sentinelConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(sentinelConnectionFactory);
        // 设置 key 和 value 的序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}