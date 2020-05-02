package com.jdrj.common.utils;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class JedisPoolUtil {
    private static JedisPool jedisPool;

    static {
        if (jedisPool == null) {
            try {
                init();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 初始化Jedis连接池
     *
     * @throws IOException
     */
    private static void init() throws IOException {
        //加载配置文件
        //开始配置JedisPool
        String host = "127.0.0.1";
        int port = 6379;
        String auth = "123123";
        int poolTimeOut = 2000;
        //判断使用默认的配置方式还是采用自定义配置方式,
        boolean isSetDefault = false;
        if (isSetDefault) {
            jedisPool = new JedisPool(new GenericObjectPoolConfig(), host, port, poolTimeOut, auth);
        } else {
            JedisPoolConfig config = new JedisPoolConfig();
            String blockWhenExhausted = "true";
            if (blockWhenExhausted != null) {
                config.setBlockWhenExhausted(Boolean.parseBoolean(blockWhenExhausted));
            }
            String evictionPolicyClassName = "org.apache.commons.pool2.impl.DefaultEvictionPolicy";
            if (evictionPolicyClassName != null) {
                config.setEvictionPolicyClassName(evictionPolicyClassName);
            }
            String jmxEnabled = "true";
            if (jmxEnabled != null) {
                config.setJmxEnabled(Boolean.parseBoolean(jmxEnabled));
            }
            String lifo = "true";
            if (lifo != null) {
                config.setLifo(Boolean.parseBoolean(lifo));
            }
            String maxIdle = "8";
            if (maxIdle != null) {
                config.setMaxIdle(Integer.parseInt(maxIdle));
            }
            String maxTotal = "8";
            if (maxTotal != null) {
                config.setMaxTotal(Integer.parseInt(maxTotal));
            }
            String maxWaitMillis = "-1";
            if (maxWaitMillis != null) {
                config.setMaxWaitMillis(Long.parseLong(maxWaitMillis));
            }
            String minEvictableIdleTimeMillis = "1800000";
            if (minEvictableIdleTimeMillis != null) {
                config.setMinEvictableIdleTimeMillis(Long.parseLong(minEvictableIdleTimeMillis));
            }
            String minIdle = "0";
            if (minIdle != null) {
                config.setMinIdle(Integer.parseInt(minIdle));
            }
            String numTestsPerEvictionRun = "3";
            if (numTestsPerEvictionRun != null) {
                config.setNumTestsPerEvictionRun(Integer.parseInt(numTestsPerEvictionRun));
            }
            String softMinEvictableIdleTimeMillis = "1800000";
            if (softMinEvictableIdleTimeMillis != null) {
                config.setSoftMinEvictableIdleTimeMillis(Long.parseLong(softMinEvictableIdleTimeMillis));
            }
            String testOnBorrow = "false";
            if (testOnBorrow != null) {
                config.setTestOnBorrow(Boolean.parseBoolean(testOnBorrow));
            }
            String testWhileIdle = "false";
            if (testWhileIdle != null) {
                config.setTestWhileIdle(Boolean.parseBoolean(testWhileIdle));
            }
            String timeBetweenEvictionRunsMillis = "-1";
            if (timeBetweenEvictionRunsMillis != null) {
                config.setTimeBetweenEvictionRunsMillis(Long.parseLong(timeBetweenEvictionRunsMillis));
            }
            jedisPool = new JedisPool(config, host, port, poolTimeOut, auth);
        }

    }

    public static Jedis getJedis() {
        return jedisPool.getResource();
    }

    public static void closeJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}
