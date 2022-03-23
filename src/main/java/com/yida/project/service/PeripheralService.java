package com.yida.project.service;

import com.yida.project.entity.Peripheral;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PeripheralService {
    //添加
    public void add(Peripheral p);

    //查所有
    public List<Peripheral> selectall();

    //刪除一個
    public void deleteon(Integer id);

    //查一个
    public Peripheral selecton(Integer id);

    public List<Peripheral> selectall(@Param("title") String title, @Param("classify_id") Integer classify_id
            , @Param("begin") String begin, @Param("end") String end);

    public void Update(Peripheral r);
}
