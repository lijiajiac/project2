package com.yida.project.service;

import com.yida.project.entity.User;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2022-03-10 19:03:29
 */
public interface UserService {

    User selectName(@RequestParam("name") String name);

    //注册用户
    void createuser(@RequestParam("name") String name, @RequestParam("password") String password);

    //用户默认权限
    void adduser(@RequestParam("roleid") Integer roleid, @RequestParam("userid") Integer userid);

    //查询所有的用户信息
    List<User> selectAllusers();

    //修改用户信息
    void updateuser(User user);

    //根据ID查询指定用户
    User selectone(Integer id);

    //根据ID删除指定用户
    void deleteon(Integer id);

    //删除中间表中关联此用户的角色信息

    //查询中间表中用户的ID
    User user_select(@RequestParam("uid") Integer id);
}
