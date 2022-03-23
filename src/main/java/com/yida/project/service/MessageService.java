package com.yida.project.service;

import com.yida.project.entity.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageService {
    public void add(Message me);

    public List<Message> selectall();

    public Message selecton(Integer id);

    public void updateon(Message me);

    public void delete(Integer id);

    public List<Message> meselect(Integer focue);

    //模糊查询搜索按钮
    public List<Message> selectallto(@Param("title") String title
            , @Param("begin") String begin, @Param("end") String end);

    //模糊查询搜索按钮
    public List<Message> selectallto2(@Param("title") String title
            , @Param("begin") String begin, @Param("end") String end);
}
