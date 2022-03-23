package com.yida.project.service;

import com.yida.project.entity.Scenic;

import java.util.List;

public interface ScenicService {
    public List<Scenic> SeleteAll();

    public Scenic Read(Integer id);

    public Scenic selecton(Integer id);
}
