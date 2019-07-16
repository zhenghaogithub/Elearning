package org.darod.elearning.gateway.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/7 0007 14:44
 */
public class RedisCacheManager implements CacheManager {

    @Autowired
    private RedisCache redisCache;

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return redisCache;
    }
}
