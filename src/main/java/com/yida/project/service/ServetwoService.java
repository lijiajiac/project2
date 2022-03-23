package com.yida.project.service;

import com.yida.project.entity.Servetwo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ServetwoService {
    public void deleteon(int s);

    public List<Servetwo> selectall();
}
