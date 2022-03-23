package com.yida.project.serviceImpl;

import com.yida.project.entity.Role;
import com.yida.project.mapper.RoleMapper;
import com.yida.project.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * (Role)表服务实现类
 *
 * @author makejava
 * @since 2022-03-10 19:04:53
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleMapper roleDao;


    @Override
    public Role selectRoles(@RequestParam("roleid") Integer roleid) {
        return roleDao.selectRoles(roleid);
    }
}
