package com.yida.project.serviceImpl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yida.project.entity.ReleasingNotices;
import com.yida.project.mapper.ReleasingNoticesMapper;
import com.yida.project.service.ReleasingNoticesService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@Service("releasingNoticesService")
public class ReleasingNoticesServiceImpl implements ReleasingNoticesService {
    @Autowired(required = false)
    private ReleasingNoticesMapper releasingNoticesMapper;

    @Override //添加
    public void add(ReleasingNotices r) {
        releasingNoticesMapper.add(r);
    }

    @Override//修改
    public void Update(ReleasingNotices r) {
        releasingNoticesMapper.Update(r);
    }

    @Override//查一个
    public ReleasingNotices selectOn(Integer id) {
        return releasingNoticesMapper.selectOn(id);
    }

    @Override//查所有
    public List<ReleasingNotices> ReadAll() {
        return releasingNoticesMapper.ReadAll();
    }

    @Override//多条件查询
    public List<ReleasingNotices> Readrn(@Param("title") String title, @Param("scenic_id") Integer scenic_id, @Param("begin") String begin, @Param("end") String end) {
        return releasingNoticesMapper.seleteall(title, scenic_id, begin, end);
    }

    @Override//删除一个
    public void delete(Integer id) {
        releasingNoticesMapper.deleteByPrimaryKey(id);
    }

}
