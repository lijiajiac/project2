package com.yida.project.serviceImpl;

import com.yida.project.entity.Servetwo;
import com.yida.project.mapper.ServetwoMapper;
import com.yida.project.service.ServetwoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("servetwoService")
public class ServetwoServiceImppl implements ServetwoService {
    @Autowired(required = false)
    private ServetwoMapper servetwoMapper;

    @Override
    public void deleteon(int s) {
        servetwoMapper.deleteByPrimaryKey(s);
    }

    @Override
    public List<Servetwo> selectall() {
        return servetwoMapper.selectAll();
    }
}
