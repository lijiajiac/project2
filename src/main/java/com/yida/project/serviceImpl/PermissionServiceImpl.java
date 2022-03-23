package com.yida.project.serviceImpl;

import com.yida.project.mapper.PermissionMapper;
import com.yida.project.service.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (Permission)表服务实现类
 *
 * @author makejava
 * @since 2022-03-10 19:05:14
 */
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {
    @Resource
    private PermissionMapper permissionDao;

}
