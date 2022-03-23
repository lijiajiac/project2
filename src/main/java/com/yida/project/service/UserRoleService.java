package com.yida.project.service;

import org.springframework.web.bind.annotation.RequestParam;

/**
 * (UserRole)表服务接口
 *
 * @author makejava
 * @since 2022-03-12 18:41:02
 */
public interface UserRoleService {

    void delete(@RequestParam("id") Integer id);

}
