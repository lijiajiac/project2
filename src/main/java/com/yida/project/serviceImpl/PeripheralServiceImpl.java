package com.yida.project.serviceImpl;

import com.yida.project.entity.Peripheral;
import com.yida.project.mapper.PeripheralMapper;
import com.yida.project.service.PeripheralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("peripheralService")
public class PeripheralServiceImpl implements PeripheralService {
    @Autowired(required = false)
    private PeripheralMapper peripheralMapper;

    @Override
    public void add(Peripheral p) {
        peripheralMapper.add(p);
    }

    @Override
    public List<Peripheral> selectall() {
        return peripheralMapper.selectAll();
    }

    @Override
    public void deleteon(Integer id) {
        peripheralMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Peripheral selecton(Integer id) {
        return peripheralMapper.selecton(id);
    }

    @Override
    public List<Peripheral> selectall(String title, Integer classify_id, String begin, String end) {
        return peripheralMapper.selectall(title, classify_id, begin, end);
    }

    @Override
    public void Update(Peripheral r) {
        peripheralMapper.updateByPrimaryKeySelective(r);
    }
}
