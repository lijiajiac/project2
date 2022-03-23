package com.yida.project.mapper;

import com.yida.project.entity.Role;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.common.Mapper;

/**
 * (Role)表数据库访问层
 *
 * @author makejava
 * @since 2022-03-10 19:04:53
 */
public interface RoleMapper extends Mapper<Role> {

    public Role selectRoles(@RequestParam("roleid") Integer roleid);

}

