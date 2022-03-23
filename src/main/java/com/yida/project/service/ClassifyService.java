package com.yida.project.service;

import com.yida.project.entity.Classify;

import java.util.List;

public interface ClassifyService {
    public List<Classify> selectall();

    public Classify readon(Integer id);
}
