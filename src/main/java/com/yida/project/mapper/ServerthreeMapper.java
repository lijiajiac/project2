package com.yida.project.mapper;

import com.yida.project.config.RedisCache;
import com.yida.project.entity.Serverthree;
import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.common.Mapper;

//指定自定义 redis 缓存 ,单张表操作时的引用方法
@CacheNamespace(implementation = RedisCache.class)
public interface ServerthreeMapper extends Mapper<Serverthree> {
}
