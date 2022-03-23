package com.yida.project.shiro;


import com.yida.project.cache.ShiroByteSource;
import com.yida.project.entity.Permission;
import com.yida.project.entity.Role;
import com.yida.project.entity.User;
import com.yida.project.service.RoleService;
import com.yida.project.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;

/**
 * 给shiro权限管理提供数据支持
 */
public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Override   //授权认证:角色认证，权限认证
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        System.out.println("正在授权认证! 主题对象=" + username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //从数据库获取当前用户所对应的角色 ，添加角色
        User user = userService.selectName(username);
        HashSet<Role> roles = user.getRoles();
        if (!ObjectUtils.isEmpty(roles)) { //当改用户对应的角色不为空时添加
            for (Role r : roles) {
                simpleAuthorizationInfo.addRole(r.getName());
                Role role = roleService.selectRoles(r.getId());//获取当前角色对应的权限字符串
                HashSet<Permission> permission = role.getPermission();
                if (!ObjectUtils.isEmpty(permission)) {
                    for (Permission p : permission) {
                        simpleAuthorizationInfo.addStringPermission(p.getName());//添加权限字符串
                    }
                }
            }
        }
        return simpleAuthorizationInfo;
    }

    @Override  //登录认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1.把 AuthenticationToken 类型转换成 UsernamePasswordToken 类型
        UsernamePasswordToken upt = (UsernamePasswordToken) authenticationToken;
        //2.从 upt 中获取前端传过来的用户名
        String username = upt.getUsername();
        //3.通过 username 到数据库里查询有没有该用户
        User user = userService.selectName(username);
        //4.如果 user 不存在，抛出没有账户异常
        if (user == null) {
            throw new UnknownAccountException("该用户不存在");
        }
        //5.如果用户被禁用，抛出锁定用户异常
        if (user.getState() == 0) {
            throw new LockedAccountException("该用户已被禁用");
        }
        //6.根据用户信息构建 AuthenticationToken 接口实例对象返回
        //principal:一个对象，可以是 user 对象，也可以是用户名，用户 id 等
        //hashedCredentials:从数据库中查询出来的密码
        //credentialsSalt: 从数据库中查询出来的盐值，这里先给个 null（后面会讲）
        //realmName：我们自定义的 ShiroRealm 的名称
        //ByteSource byteSource = ByteSource.Util.bytes(user.getSalt());//得到加密需要盐值
        ByteSource byteSource = new ShiroByteSource(user.getSalt());//盐值，针对 Redis 缓存，使用这个!
        AuthenticationInfo authInfo = new SimpleAuthenticationInfo(username, user.getPassword(), byteSource, getName());
        return authInfo;
    }

}
