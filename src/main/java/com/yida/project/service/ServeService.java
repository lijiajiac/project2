package com.yida.project.service;

import com.yida.project.entity.Serve;

import java.util.List;

public interface ServeService {
    public void add(Serve s);

    public List<Serve> selestall();

    public void deleteon(Integer id);
}
