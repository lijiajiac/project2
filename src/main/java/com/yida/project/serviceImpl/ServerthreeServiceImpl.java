package com.yida.project.serviceImpl;

import com.yida.project.entity.Serverthree;
import com.yida.project.mapper.ServerthreeMapper;
import com.yida.project.service.ServerthreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("serverthreeService")
public class ServerthreeServiceImpl implements ServerthreeService {
    @Autowired(required = false)
    private ServerthreeMapper serverthreeMapper;

    @Override
    public void add(Serverthree s) {
        serverthreeMapper.insertSelective(s);
    }

    @Override
    public Serverthree selectin(Integer id) {
        return serverthreeMapper.selectByPrimaryKey(id);
    }
}
