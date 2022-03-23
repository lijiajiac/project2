package com.yida.project.mapper;

import com.yida.project.entity.ReleasingNotices;
import org.apache.ibatis.annotations.CacheNamespaceRef;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@CacheNamespaceRef(name = "com.yida.project.mapper.ReleasingNoticesMapper") //指定自定义 redis 缓存引用
public interface ReleasingNoticesMapper extends Mapper<ReleasingNotices> {
    //添加
    public void add(ReleasingNotices r);

    //修改
    public void Update(ReleasingNotices r);

    //查一个
    public ReleasingNotices selectOn(Integer id);

    //查所有
    public List<ReleasingNotices> ReadAll();

    //模糊查询
    public List<ReleasingNotices> seleteall(@Param("title") String title, @Param("scenic_id") Integer scenic_id
            , @Param("begin") String begin, @Param("end") String end);
}
