package com.yida.project.serviceImpl;

import com.yida.project.mapper.UserRoleMapper;
import com.yida.project.service.UserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (UserRole)表服务实现类
 *
 * @author makejava
 * @since 2022-03-12 18:41:02
 */
@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {
    @Resource
    private UserRoleMapper userRoleDao;

    @Override
    public void delete(Integer id) {
        userRoleDao.deleteByPrimaryKey(id);
    }
}
