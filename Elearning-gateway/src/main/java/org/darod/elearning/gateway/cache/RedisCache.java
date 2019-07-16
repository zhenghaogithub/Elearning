package org.darod.elearning.gateway.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.darod.elearning.gateway.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.util.Collection;
import java.util.Set;

/**
 * @author Darod
 * @version 1.0
 * @date 2019/7/7 0007 14:45
 */
@Component
public class RedisCache<K, V> implements Cache<K, V> {
    @Autowired
    private RedisUtils redisUtils;

    private final String CACHE_PREFIX = "elearning-gateway-cache:";

    private String getKey(K k) {
        if (k instanceof String) {
            return (CACHE_PREFIX + k);
        }
        String temp = new String(SerializationUtils.serialize(k));
        return CACHE_PREFIX + temp;
    }

    @Override
    public V get(K k) throws CacheException {
        System.out.println("从redis获取数据");
        Object object = redisUtils.get(getKey(k));
        if (object != null) {
            return (V) object;
        }
        return null;
    }

    @Override
    public V put(K k, V v) throws CacheException {
        String key = getKey(k);
        redisUtils.set(key, v, 600);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        String key = getKey(k);
        Object o = redisUtils.get(key);
        redisUtils.del(key);
        if (o != null) {
            return (V) o;
        }
        return null;
    }

    @Override
    public void clear() throws CacheException {
        //
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }
}
