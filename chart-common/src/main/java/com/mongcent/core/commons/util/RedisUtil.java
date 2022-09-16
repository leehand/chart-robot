package com.mongcent.core.commons.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mongcent.core.commons.SpringContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * redis缓存工具类
 *
 */
public class RedisUtil {

    private static RedisTemplate<String, Object> redisTemplate = SpringContextHolder.getBean("redisTemplate");

    private static Integer EXPIRE = 1800; // 过期时间默认1800秒（30分钟）

    /**
     * 将value对象写入缓存
     *
     * @param key
     * @param value
     * @param seconds 失效时间(秒)
     */
    public static void set(String key, Object value, int seconds) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

    /**
     * 将value对象写入缓存
     *
     * @param key
     * @param value
     */
    public static void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, EXPIRE, TimeUnit.SECONDS);
    }

    public synchronized static Object get(final String key) {
        return redisTemplate.boundValueOps(key).get();
    }

    /**
     * 删除缓存<br>
     * 根据key精确匹配删除
     *
     * @param key
     */
    public static void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(Arrays.asList(key));
            }
        }
    }

    /**
     * 批量删除<br>
     * （该操作会执行模糊查询，请尽量不要使用，以免影响性能或误删）
     *
     * @param pattern
     */
    public static void batchDel(String... pattern) {
        for (String kp : pattern) {
            redisTemplate.delete(redisTemplate.keys(kp + "*"));
        }
    }

    /**
     * 取得缓存（int型）
     *
     * @param key
     * @return
     */
    public static Integer getInt(String key) {
        String value = (String) redisTemplate.boundValueOps(key).get();
        if (StringUtils.isNotBlank(value)) {
            return Integer.valueOf(value);
        }
        return null;
    }

    /**
     * 取得缓存（字符串类型）
     *
     * @param key
     * @return
     */
    public static String getStr(String key) {
        return (String) redisTemplate.boundValueOps(key).get();
    }

    /**
     * 取得缓存（字符串类型）
     *
     * @param key
     * @return
     */
    public static String getStr(String key, boolean retain) {
        String value = (String) redisTemplate.boundValueOps(key).get();
        if (!retain) {
            redisTemplate.delete(key);
        }
        return value;
    }

    /**
     * 获取缓存<br>
     * 注：基本数据类型(Character除外)，请直接使用get(String key, Class<T> clazz)取值
     *
     * @param key
     * @return
     */
    public static Object getObj(String key) {
        return redisTemplate.boundValueOps(key).get();
    }

    /**
     * 获取缓存<br>
     * 注：java 8种基本类型的数据请直接使用get(String key, Class<T> clazz)取值
     *
     * @param key
     * @param retain 是否保留
     * @return
     */
    public static Object getObj(String key, boolean retain) {
        Object obj = redisTemplate.boundValueOps(key).get();
        if (!retain) {
            redisTemplate.delete(key);
        }
        return obj;
    }

    /**
     * 获取缓存<br>
     * 注：该方法暂不支持Character数据类型
     *
     * @param key   key
     * @param clazz 类型
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String key, Class<T> clazz) {
        return (T) redisTemplate.boundValueOps(key).get();
    }

    /**
     * 获取缓存，转换成List<br>
     * 注：该方法暂不支持Character数据类型
     *
     * @param key   key
     * @param clazz 类型
     * @return
     */
    public static <T> List<T> getAsList(String key, Class<T> clazz) {
        return JSONArray.parseArray(JSON.toJSONString(redisTemplate.boundValueOps(key).get()), clazz);
    }

    /**
     * 获取缓存，转换成Set<br>
     * 注：该方法暂不支持Character数据类型
     *
     * @param key   key
     * @param clazz 类型
     * @return
     */
    public static <T> Set<T> getAsSet(String key, Class<T> clazz) {
        List<T> lst = getAsList(key, clazz);
        if (lst != null) {
            return new HashSet<T>(lst);
        }
        return null;
    }

    /**
     * 递减操作
     *
     * @param key
     * @param by
     * @return
     */
    public static double decr(String key, double by) {
        return redisTemplate.opsForValue().increment(key, -by);
    }

    /**
     * 递增操作
     *
     * @param key
     * @param by
     * @return
     */
    public static double incr(String key, double by) {
        return redisTemplate.opsForValue().increment(key, by);
    }

    /**
     * 获取double类型值
     *
     * @param key
     * @return
     */
    public static double getDouble(String key) {
        String value = (String) redisTemplate.boundValueOps(key).get();
        if (StringUtils.isNotBlank(value)) {
            return Double.valueOf(value);
        }
        return 0d;
    }

    /**
     * 设置double类型值
     *
     * @param key
     * @param value
     * @param time  失效时间(秒)
     */
    public static void setDouble(String key, double value, int seconds) {
        redisTemplate.opsForValue().set(key, String.valueOf(value));
        if (seconds > 0) {
            redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
        }
    }

    /**
     * 设置double类型值
     *
     * @param key
     * @param value
     * @param time  失效时间(秒)
     */
    public static void setInt(String key, int value, int seconds) {
        redisTemplate.opsForValue().set(key, String.valueOf(value));
        if (seconds > 0) {
            redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
        }
    }

    /**
     * 将map写入缓存
     *
     * @param key
     * @param map
     * @param time 失效时间(秒)
     */
    public static <T> void setMap(String key, Map<String, T> map, int seconds) {
        redisTemplate.opsForHash().putAll(key, map);
        if (seconds > 0) {
            redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
        }
    }

    /**
     * 向key对应的map中添加缓存对象
     *
     * @param key
     * @param map
     */
    public static <T> void addMap(String key, Map<String, T> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 向key对应的map中添加缓存对象
     *
     * @param key   cache对象key
     * @param field map对应的key
     * @param value 值
     */
    public static void addMap(String key, String field, String value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 向key对应的map中添加缓存对象
     *
     * @param key   cache对象key
     * @param field map对应的key
     * @param obj   对象
     */
    public static <T> void addMap(String key, String field, T obj) {
        redisTemplate.opsForHash().put(key, field, obj);
    }

    /**
     * 获取map缓存
     *
     * @param key
     * @return
     */
    public static <T> Map<String, T> mget(String key) {
        BoundHashOperations<String, String, T> boundHashOperations = redisTemplate.boundHashOps(key);
        return boundHashOperations.entries();
    }

    /**
     * 获取map缓存中的某个对象
     *
     * @param key
     * @param field
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getMapField(String key, String field, Class<T> clazz) {
        return (T) redisTemplate.boundHashOps(key).get(field);
    }

    /**
     * 删除map中的某个对象
     *
     * @param key   map对应的key
     * @param field map中该对象的key
     * @author lh
     */
    public void delMapField(String key, String... field) {
        BoundHashOperations<String, Object, Object> boundHashOperations = redisTemplate.boundHashOps(key);
        boundHashOperations.delete(field);
    }

    /**
     * 指定缓存的失效时间
     *
     * @param key  缓存KEY
     * @param time 失效时间(秒)
     */
    public static void expire(String key, final int seconds) {
        redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

    /**
     * 添加set
     *
     * @param key
     * @param value
     */
    public static void sadd(String key, String... value) {
        redisTemplate.boundSetOps(key).add(value);
    }

    /**
     * 删除set集合中的对象
     *
     * @param key
     * @param value
     */
    public static void srem(String key, String... value) {
        redisTemplate.boundSetOps(key).remove(value);
    }

    /**
     * set重命名
     *
     * @param oldkey
     * @param newkey
     */
    public static void srename(String oldkey, String newkey) {
        redisTemplate.boundSetOps(oldkey).rename(newkey);
    }

    /**
     * 模糊查询keys
     *
     * @param pattern
     * @return
     */
    public static Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

}
