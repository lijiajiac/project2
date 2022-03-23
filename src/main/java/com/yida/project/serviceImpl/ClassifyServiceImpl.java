package com.yida.project.serviceImpl;

import com.yida.project.entity.Classify;
import com.yida.project.mapper.ClassifyMapper;
import com.yida.project.service.ClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("classifyService")
public class ClassifyServiceImpl implements ClassifyService {

    @Autowired(required = false)
    private ClassifyMapper classifyMapper;

    @Override
    public List<Classify> selectall() {
        return classifyMapper.selectAll();
    }

    @Override
    public Classify readon(Integer id) {
        return classifyMapper.selectByPrimaryKey(id);
    }
}
