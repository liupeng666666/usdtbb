package com.whp.usdtbb.utils;

import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class RedisUtils {
    private static JedisPool pool = null;

    static {
        if (pool == null) {
            JedisPoolConfig config = new JedisPoolConfig();
            //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
            //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
            config.setMaxTotal(500);
            //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
            config.setMaxIdle(5);
            //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
            config.setMaxWaitMillis(1000 * 100);
            //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(true);
            if (PropertiesUtils.KeyValue("redis.state").equals("true")) {
                pool = new JedisPool(config, PropertiesUtils.KeyValue("redis.host"), Integer.parseInt(PropertiesUtils.KeyValue("redis.port")), 10000, PropertiesUtils.KeyValue("redis.auth"));
            } else {
                pool = new JedisPool(config, PropertiesUtils.KeyValue("redis.host"), Integer.parseInt(PropertiesUtils.KeyValue("redis.port")));
            }

        }
    }

    public static void returnResource(Jedis redis) {
        if (redis != null) {
            redis.close();
        }
    }

    /**
     * 方法描述 获取Jedis实例
     *
     * @return
     * @author yaomy
     * @date 2018年1月11日 下午4:56:58
     */
    public static Jedis getJedis() {
        Jedis jedis = pool.getResource();
        return jedis;
    }


    public static String get(String key, int select) {
        Jedis jedis = getJedis();
        jedis.select(select);
        String value = jedis.get(key);
        returnResource(jedis);
        return value;
    }

    public static void set(String key, String value, int select) {
        Jedis jedis = getJedis();
        jedis.select(select);
        jedis.set(key, value);
        returnResource(jedis);
    }

    /**
     * 插入到右侧
     *
     * @param key
     * @param value
     * @param select
     * @param num
     */
    public static void LPUSH(String key, String value, int select, int num) {
        Jedis jedis = getJedis();
        jedis.select(select);
        jedis.lpush(key, value);
        jedis.ltrim(key, 0, num);
        returnResource(jedis);
    }

    /**
     * list 插入到左侧
     *
     * @param key
     * @param value
     * @param select
     */
    public static void rpush(String key, String value, int select) {
        Jedis jedis = getJedis();
        jedis.select(select);
        jedis.rpush(key, value);
        returnResource(jedis);
    }

    /**
     * map 添加
     *
     * @param key
     * @param map
     * @param select
     */
    public static void HMSET(String key, Map<String, String> map, int select) {
        Jedis jedis = getJedis();
        jedis.select(select);
        jedis.hmset(key, map);
        returnResource(jedis);
    }

    /**
     * map 查询
     *
     * @param key
     * @param name
     * @param select
     * @return
     */
    public static String HMGET(String key, String name, int select) {
        String value = "";
        Jedis jedis = getJedis();
        jedis.select(select);
        List<String> list = jedis.hmget(key, name);
        if (list.size() > 0) {
            value = list.get(0);
        }
        returnResource(jedis);
        return value;
    }

    /**
     * list 查询
     *
     * @param key
     * @param start
     * @param end
     * @param select
     * @return
     */
    public static List<String> lrange(String key, int start, int end, int select) {
        Jedis jedis = getJedis();
        jedis.select(select);
        List<String> list = jedis.lrange(key, start, end);
        returnResource(jedis);
        return list;
    }

    /**
     * list 修改
     *
     * @param key
     * @param index
     * @param value
     * @param select
     */
    public static void LSET(String key, int index, String value, int select) {
        System.out.println(index + "=====" + value);
        Jedis jedis = getJedis();
        jedis.select(select);
        jedis.lset(key, index, value);
        returnResource(jedis);
    }

    /**
     * list 删除
     *
     * @param key
     * @param select
     */
    public static void LPOP(String key, int select) {
        Jedis jedis = getJedis();
        jedis.select(select);
        jedis.lpop(key);
        returnResource(jedis);
    }

    /**
     * 获取map所有value
     */
    public static List<String> HVALS(String key, int select) {
        Jedis jedis = getJedis();
        jedis.select(select);
        List<String> list = jedis.hvals(key);
        returnResource(jedis);
        return list;
    }

    /**
     * list 删除
     *
     * @param key
     * @param select
     */
    public static void LREM(String key, int select, String value) {
        Jedis jedis = getJedis();
        jedis.select(select);
        jedis.lrem(key, 0, value);
        returnResource(jedis);
    }
}
