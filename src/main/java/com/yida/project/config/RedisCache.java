package com.yida.project.config;


import com.yida.project.cache.ApplicationContextUtils;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.DigestUtils;

/**
 * 自定义Redis缓存
 */

public class RedisCache implements Cache {
    //这里的ID实际上是mapper里namespace的值
    private final String id;

    public RedisCache(String id) {
        this.id = id;
    }


    @Override
    public String getId() {
        return this.id;
    }

    @Override//向指定key添加信息   insert into
    public void putObject(Object key, Object value) {
        System.out.println("=====>key=" + (key.toString()) + ", value=" + value);
        getRedisTemplate().opsForHash().put(id, md5Key(key.toString()), value);
    }

    @Override//获取指定key的信息      select
    public Object getObject(Object key) {
        System.out.println("----->key=" + (key.toString()));
        return getRedisTemplate().opsForHash().get(id, md5Key(key.toString()));
    }

    @Override//删除指定大key下的指定小
    public Object removeObject(Object key) {
        System.out.println("清除指定的key");
        getRedisTemplate().opsForHash().delete(id, md5Key(key.toString()));
        return null;
    }

    @Override//情空数据库
    public void clear() {
        System.out.println("清空数据库");
        getRedisTemplate().delete(id);
    }

    @Override//获取指定数据库数量
    public int getSize() {
        System.out.println("获取总大小");
        return getRedisTemplate().opsForHash().size(id).intValue();
    }


    //获取redisTemplate单例对象
    public RedisTemplate getRedisTemplate() {
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
        redisTemplate.setKeySerializer(new StringRedisSerializer());//key设置字符串类型
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());//hash key设置字符串类型
        return redisTemplate;
    }

    /*封装一个私有方法，专门把 key 进行 md5 加密，让 key 更简短!
 注意：Md5 加密算法的特点:
 1. 一切文件或字符串都能生成 32 位 16 进制的 md5 字符串；
 2. 不同文件或字符串生成的 md5 字符串一定不同；
 3. 相同文件或字符串，多次生成的 md5 结果一致。
 */
    private String md5Key(String key) {
        String k = DigestUtils.md5DigestAsHex(key.getBytes());
        return k;
    }


}
