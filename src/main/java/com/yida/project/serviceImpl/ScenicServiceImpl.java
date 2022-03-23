package com.yida.project.serviceImpl;

import com.yida.project.entity.Scenic;
import com.yida.project.mapper.ScenicMapper;
import com.yida.project.service.ScenicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("scenicService")
public class ScenicServiceImpl implements ScenicService {

    @Autowired(required = false)
    private ScenicMapper scenicMapper;

    @Override
    public List<Scenic> SeleteAll() {
        return scenicMapper.selectAll();
    }

    @Override
    public Scenic Read(Integer id) {
        return scenicMapper.selectByPrimaryKey(id);
    }

    @Override
    public Scenic selecton(Integer id) {
        return scenicMapper.Read(id);
    }


}
