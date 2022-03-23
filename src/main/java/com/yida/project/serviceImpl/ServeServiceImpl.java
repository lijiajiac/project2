package com.yida.project.serviceImpl;

import com.yida.project.entity.Serve;
import com.yida.project.mapper.ServeMapper;
import com.yida.project.service.ServeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("serveService")
public class ServeServiceImpl implements ServeService {
    @Autowired(required = false)
    private ServeMapper serveMapper;

    @Override
    public void add(Serve s) {
        serveMapper.insertSelective(s);
    }

    @Override
    public List<Serve> selestall() {
        return serveMapper.selectAll();
    }

    @Override
    public void deleteon(Integer id) {
        serveMapper.deleteByPrimaryKey(id);
    }
}
