package com.yida.project.service;

import com.yida.project.entity.Setup;

public interface SetupService {
    public void add(Setup s);

    public Setup selecton(Integer id);

    public void updateon(Setup s);
}
