package com.quxiaojun.test.common.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

import java.io.Serializable;

@Configuration
public class RedisConfig {


    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.password}")
    private String password;

    private Environment environment;

    /**
     * 配置JedisConnectionFactory
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(jedisPoolConfig());
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setPort(port);
        jedisConnectionFactory.setTimeout(timeout);
        jedisConnectionFactory.setPassword(password);
        jedisConnectionFactory.setUsePool(true);
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<Serializable, Serializable> redisTemplate(){
        RedisTemplate<Serializable, Serializable> redisTemplate = new RedisTemplate<Serializable, Serializable>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(){
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    private JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(Integer.valueOf(environment.getProperty("spring.redis.jedis.pool.max-active")));
        jedisPoolConfig.setMaxIdle(Integer.valueOf(environment.getProperty("spring.redis.jedis.pool.max-idle")));
        jedisPoolConfig.setMinIdle(Integer.valueOf(environment.getProperty("spring.redis.jedis.pool.min-idle")));
        jedisPoolConfig.setMaxWaitMillis(Integer.valueOf(environment.getProperty("spring.redis.jedis.pool.max-wait")));
        return jedisPoolConfig;
    }
}
