package com.example.controller;

import com.cacheFramework.*;
import com.cacheFramework.nativeCache.CaffeineCacheBuilder;
import com.cacheFramework.redis.RedisCacheBuilder;
import com.cacheFramework.util.DefaultCacheMonitor;
import com.cacheFramework.util.FastjsonKeyConvertor;
import com.cacheFramework.util.JavaValueDecoder;
import com.cacheFramework.util.JavaValueEncoder;
import com.example.dao.KeyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class CacheController {

    @Autowired
    private KeyDao keyDao;

    @GetMapping("/test")
    public Map<String, String> test() throws InterruptedException {
        Map<String, String> map = new HashMap<>();

        CacheLoader<String, String> loader = new CacheLoader<String, String>() {
            @Override
            public String load(String key) throws Throwable {
                //从数据库中刷新缓存
                String value = keyDao.selectValue(key).getTestValue();
                return value;
            }
        };
        Cache<String, String> nativeCache = CaffeineCacheBuilder.createCaffeineCacheBuilder()
                .limit(100)
                .expireAfterWrite(30, TimeUnit.SECONDS)
                .buildCache();
//        cache.put(1,1);
//        Thread.sleep(6000);
//        System.out.println(cache.get(1));

//        Jedis jedis = new Jedis("106.14.218.162", 6379);
//        jedis.auth("969zmyZMY");
//        String value = jedis.ping();
//        System.out.println(value);

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(10);
        config.setMaxIdle(10);

        JedisPool jp = new JedisPool(config, "106.14.218.162", 6379, 10000,"969zmyZMY");

        Cache<String, String> redisCache = RedisCacheBuilder.createRedisCacheBuilder().
                keyConvertor(FastjsonKeyConvertor.INSTANCE)
                .valueEncoder(JavaValueEncoder.INSTANCE)
                .valueDecoder(JavaValueDecoder.INSTANCE)
                .jedisPool(jp)
                .keyPrefix("prefixCache")
                .expireAfterWrite(30, TimeUnit.SECONDS)
                .buildCache();

        Cache<String, String> multiCache = MultiLevelCacheBuilder.createMultiLevelCacheBuilder()
                .addCache(nativeCache, redisCache)
                .loader(loader)
                .refreshPolicy(RefreshPolicy.newPolicy(5, TimeUnit.SECONDS))
                .expireAfterWrite(30, TimeUnit.SECONDS)
                .buildCache();

        CacheMonitor mcMonitor = new DefaultCacheMonitor("multiCache");
        multiCache.config().getMonitors().add(mcMonitor);

        return map;
    }
}
