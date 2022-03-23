package com.yida.project.serviceImpl;

import com.yida.project.entity.Setup;
import com.yida.project.mapper.SetupMapper;
import com.yida.project.service.SetupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SetupService")
public class SetupServiceImpl implements SetupService {
    @Autowired(required = false)
    private SetupMapper setupMapper;

    @Override
    public void add(Setup s) {
        setupMapper.insertSelective(s);
    }

    @Override
    public Setup selecton(Integer id) {
        return setupMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateon(Setup s) {
        setupMapper.updateByPrimaryKeySelective(s);
    }

}
