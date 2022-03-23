package com.yida.project.mapper;

import com.yida.project.entity.Message;
import org.apache.ibatis.annotations.CacheNamespaceRef;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@CacheNamespaceRef(name = "com.yida.project.mapper.MessageMapper") //指定自定义 redis 缓存引用
public interface MessageMapper extends Mapper<Message> {
    public void inser(Message me);

    //修改焦点
    public void updateon(Message me);

    //焦点消息
    public List<Message> selectme(Integer focue);

    //模糊查询搜索按钮
    public List<Message> selectallto(@Param("title") String title
            , @Param("begin") String begin, @Param("end") String end);

    //模糊查询搜索按钮
    public List<Message> selectallto2(@Param("title") String title
            , @Param("begin") String begin, @Param("end") String end);
}
