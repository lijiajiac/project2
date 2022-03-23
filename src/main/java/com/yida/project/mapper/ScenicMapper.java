package com.yida.project.mapper;

import com.yida.project.entity.Scenic;
import org.apache.ibatis.annotations.CacheNamespaceRef;
import tk.mybatis.mapper.common.Mapper;

@CacheNamespaceRef(name = "com.yida.project.mapper.ReleasingNoticesMapper")
public interface ScenicMapper extends Mapper<Scenic> {
    public Scenic Read(Integer id);
}
