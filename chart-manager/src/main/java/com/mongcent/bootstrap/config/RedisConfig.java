package com.mongcent.bootstrap.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis配置
 *
 * @author zl
 */
@Configuration
public class RedisConfig {

    @Autowired
    private LettuceConnectionFactory lettuceConnectionFactory;

    /**
     * 用fastjson来序列化redis里的数据
     * springSessionDefaultRedisSerializer 这个是为了spring session的。他自动注入这个名称的bean
     *
     * @return
     * @see org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration
     */
    @Bean(name = "springSessionDefaultRedisSerializer")
    public RedisSerializer<Object> redisSerializer() {
        return new GenericFastJsonRedisSerializer();
    }

    @Bean
    public StringRedisSerializer stringRedisSerializer() {
        return new StringRedisSerializer();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        //shareNativeConnection参数，默认是只用同一个连接，不能提高并发。设为false才能并发。但是，redis本来就是单线程的啊？
//        lettuceConnectionFactory.setShareNativeConnection(false);
        RedisSerializer redisSerializer = redisSerializer();
        StringRedisSerializer stringRedisSerializer = stringRedisSerializer();
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        //value用fastjson来序列化
        redisTemplate.setHashValueSerializer(redisSerializer);
        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
