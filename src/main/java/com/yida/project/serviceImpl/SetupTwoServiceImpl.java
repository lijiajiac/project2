package com.yida.project.serviceImpl;

import com.yida.project.entity.SetupTwo;
import com.yida.project.mapper.SetupTwoMapper;
import com.yida.project.service.SetupTwoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("setupTwoService")
public class SetupTwoServiceImpl implements SetupTwoService {
    @Autowired(required = false)
    private SetupTwoMapper setupTwoMapper;

    @Override
    public void updateon(SetupTwo s) {
        setupTwoMapper.updateByPrimaryKeySelective(s);
    }

    @Override
    public SetupTwo selecton(Integer id) {
        return setupTwoMapper.selectByPrimaryKey(id);
    }
}
