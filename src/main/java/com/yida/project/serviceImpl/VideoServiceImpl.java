package com.yida.project.serviceImpl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yida.project.entity.Video;
import com.yida.project.mapper.VideoMapper;
import com.yida.project.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@Service("videoService")
public class VideoServiceImpl implements VideoService {

    @Autowired(required = false)
    private VideoMapper videoMapper;

    @Override
    public void addon(Video v) {
        videoMapper.addon(v);
    }

    @Override
    public List<Video> selectall() {
        return videoMapper.selectall();
    }

    @Override
    public void updateon(Video v) {
        videoMapper.updateon(v);
    }


    @Override
    public Video selecton(Integer id) {
        return videoMapper.selecton(id);
    }

    @Override
    public void deleteon(Integer id) {
        videoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Video> Readrn(String title, Integer scenic_id, String begin, String end) {
        return videoMapper.seleteall(title, scenic_id, begin, end);
    }
}
