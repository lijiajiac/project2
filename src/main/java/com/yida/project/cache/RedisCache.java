package com.yida.project.cache;


import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Collection;
import java.util.Set;

/**
 * 自定义 Redis 缓存
 *
 * @param <K>
 * @param <V>
 */
public class RedisCache<K, V> implements Cache<K, V> {
    private String cacheName;

    public RedisCache() {
    }

    public RedisCache(String cacheName) {
        this.cacheName = cacheName;
    }

    @Override
    public V get(K k) throws CacheException {
        System.out.println("get key=" + k);
        V v = (V) getRedisTemplate().opsForHash().get(this.cacheName,
                k.toString());
        return v;
    }

    @Override
    public V put(K k, V v) throws CacheException {
        System.out.println("put key=" + k + ", value=" + v);
        getRedisTemplate().opsForHash().put(this.cacheName, k.toString(), v);
        return null;
    }

    @Override //删除当前指定key下的所有数据
    public V remove(K k) throws CacheException {
        System.out.println("remove key=" + k);
        return (V) getRedisTemplate().opsForHash().delete(this.cacheName,k.toString());
    }

    @Override//清空当前数据库的所有数据
    public void clear() throws CacheException {
        System.out.println("clear");
        getRedisTemplate().delete(this.cacheName);
    }

    @Override
    public int size() {
        return getRedisTemplate().opsForHash().size(this.cacheName).intValue();
    }

    @Override
    public Set<K> keys() {
        return getRedisTemplate().opsForHash().keys(this.cacheName);
    }

    @Override
    public Collection<V> values() {
        return getRedisTemplate().opsForHash().values(this.cacheName);
    }

    //自定义方法，返回 redisTemplate
    public RedisTemplate getRedisTemplate() {
        RedisTemplate redisTemplate =
                (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
