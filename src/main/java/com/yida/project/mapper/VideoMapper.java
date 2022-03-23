package com.yida.project.mapper;

import com.yida.project.entity.ReleasingNotices;
import com.yida.project.entity.Video;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface VideoMapper extends Mapper<Video> {

    //添加
    public void addon(Video v);

    //查所有
    public List<Video> selectall();

    //修改
    public void updateon(Video v);


    //查一个
    public Video selecton(Integer id);

    //模糊查询
    public List<Video> seleteall(@Param("title") String title, @Param("scenic_id") Integer scenic_id
            , @Param("begin") String begin, @Param("end") String end);
}
