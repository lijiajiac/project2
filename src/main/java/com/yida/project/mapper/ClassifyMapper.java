package com.yida.project.mapper;

import com.yida.project.entity.Classify;
import org.apache.ibatis.annotations.CacheNamespaceRef;
import tk.mybatis.mapper.common.Mapper;

@CacheNamespaceRef(name = "com.yida.project.mapper.PeripheralMapper") //指定自定义 redis 缓存引用
public interface ClassifyMapper extends Mapper<Classify> {
    public Classify Read(Integer id);
}
