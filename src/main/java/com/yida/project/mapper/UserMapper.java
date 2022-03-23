package com.yida.project.mapper;

import com.yida.project.entity.User;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.common.Mapper;

import java.util.Set;

/**
 * (User)表数据库访问层
 *
 * @author makejava
 * @since 2022-03-10 19:03:28
 */
public interface UserMapper extends Mapper<User> {

    //根据用户名得到当前用户的所有信息
    public User selectName(String name);

    //给注册用户添加普通用户角色
    void adduser(@RequestParam("roleid") Integer roleid, @RequestParam("userid") Integer userid);

    User user_select(@RequestParam("uid") Integer id);
}

