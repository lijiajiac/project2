package com.yida.project.service;

import com.yida.project.entity.ReleasingNotices;
import com.yida.project.entity.Video;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoService {
    //添加
    public void addon(Video v);

    //查所有
    public List<Video> selectall();

    //修改
    public void updateon(Video v);


    //查一个
    public Video selecton(Integer id);

    //删除
    public void deleteon(Integer id);

    //模糊查询
    public List<Video> Readrn(@Param("title") String title, @Param("scenic_id") Integer scenic_id, @Param("begin") String begin, @Param("end") String end);
}
