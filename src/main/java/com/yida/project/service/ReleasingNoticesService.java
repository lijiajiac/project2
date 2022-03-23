package com.yida.project.service;

import com.yida.project.entity.ReleasingNotices;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

public interface ReleasingNoticesService {
    //添加
    public void add(ReleasingNotices r);

    //修改
    public void Update(ReleasingNotices r);

    //查一个
    public ReleasingNotices selectOn(Integer id);

    //查所有
    public List<ReleasingNotices> ReadAll();

    //模糊查询
    public List<ReleasingNotices> Readrn(@Param("title") String title, @Param("scenic_id") Integer scenic_id, @Param("begin") String begin, @Param("end") String end);

    //删一个
    public void delete(Integer id);
}
