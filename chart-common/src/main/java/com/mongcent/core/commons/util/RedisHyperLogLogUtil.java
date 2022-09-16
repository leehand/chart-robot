package com.mongcent.core.commons.util;

import com.mongcent.core.commons.SpringContextHolder;
import org.springframework.data.redis.core.HyperLogLogOperations;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Description redis进行HyperLogLog操作的相关封装。跟其他的分开吧
 * @Author zl
 * @Date 2019/10/21
 **/
public class RedisHyperLogLogUtil {
    private static RedisTemplate<String, Object> redisTemplate = SpringContextHolder.getBean("redisTemplate");

    /**
     * 往HyperLogLog里加入多个元素。这里强制使用字符串，防止序列化的时候两个对象判断为非同一个
     *
     * @param key
     * @param value
     * @return
     */
    public static long add(String key, String... value) {
        return getHyperLogLog().add(key, value);
    }

    /**
     * 删除整个集合
     *
     * @param key
     */
    public static void delete(String key) {
        getHyperLogLog().delete(key);
    }

    /**
     * 统计一个或多个集合里有几个数
     *
     * @param keys
     * @return
     */
    public static long count(String... keys) {
        return getHyperLogLog().size(keys);
    }

    /**
     * 合并多个集合，并将结果存到目标集合中
     *
     * @param destKey 将结果存到的key里
     * @param keys    要合并的多个key
     * @return
     */
    public long union(String destKey, String... keys) {
        return getHyperLogLog().union(destKey, keys);
    }

    private static HyperLogLogOperations<String, Object> getHyperLogLog() {
        if (redisTemplate == null) {
            throw new IllegalArgumentException("没有找到redisTemplate");
        }
        return redisTemplate.opsForHyperLogLog();
    }
}
