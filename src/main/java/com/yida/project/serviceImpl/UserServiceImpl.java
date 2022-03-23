package com.yida.project.serviceImpl;

import com.yida.project.entity.User;
import com.yida.project.mapper.UserMapper;
import com.yida.project.service.UserService;
import com.yida.project.util.SaltUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2022-03-10 19:03:29
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userDao;


    @Value("${Credential.AlgorithmName}")  //加密方法
    private String algorithmName;

    @Value("${Credential.hashIterations}")  //加密次数
    private Integer hashIterations;

    @Override
    public User selectName(String name) {
        return userDao.selectName(name);
    }

    @Override
    public void createuser(String name, String password) {
        User user = new User();
        user.setName(name);
        user.setCreatedate(new Date());
        String salt = SaltUtils.srand(8);//加盐
        SimpleHash newpwd = new SimpleHash(algorithmName, password, salt, hashIterations);//得到进行后端二次加密后的密码保存到数据库
        user.setPassword(newpwd.toString());
        user.setSalt(salt);
        userDao.insertSelective(user);

        adduser(2, user.getId());//给用户添加默认角色
    }

    @Override
    public void adduser(Integer roleid, Integer userid) {
        userDao.adduser(roleid, userid);
    }

    @Override
    public List<User> selectAllusers() {
        return userDao.selectAll();
    }

    @Override
    @Transactional  //修改个人信息
    public void updateuser(User user) {
        String salt = SaltUtils.srand(8);
        user.setSalt(salt);
        user.setCreatedate(new Date());
        SimpleHash newpwd = new SimpleHash(algorithmName, user.getPassword(), salt, hashIterations);//得到进行后端二次加密后的密码保存到数据库
        user.setPassword(newpwd.toString());
        userDao.updateByPrimaryKeySelective(user);
    }

    @Override
    public User selectone(Integer id) {
        return userDao.selectByPrimaryKey(id);
    }

    @Override
    public void deleteon(Integer id) {

        userDao.deleteByPrimaryKey(id);
    }

    @Override
    public User user_select(Integer id) {
        User user = userDao.user_select(id);
        return user;
    }

}
