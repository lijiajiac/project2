package com.yida.project.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 处理一些程序运行时的异常
 */
@Slf4j
@ControllerAdvice
public class HandlerException {
    //前后端不分离时的异常处理返回
    @ExceptionHandler(RuntimeException.class)
    public String exceptionPage(Exception e) {
        if (e instanceof UnknownAccountException) {
            System.out.println("登录认证失败,用户不存在! 原因=" + e.getMessage());
            return "redirect:/login";
        } else if (e instanceof LockedAccountException) {
            System.out.println("登录认证失败,用户被禁用! 原因=" + e.getMessage());
            return "redirect:/login";
        } else if (e instanceof IncorrectCredentialsException) {
            System.out.println("登录认证失败,密码不正确! 原因=" + e.getMessage());
            return "redirect:/login";
        } else if (e instanceof AuthenticationException) {
            System.out.println("登录认证失败! 原因=" + e.getMessage());
            return "redirect:/login";
        } else if (e instanceof UnauthorizedException) {
            System.out.println("授权认证失败,未授权访问! 原因=" + e.getMessage());
            return "unauthorized";
        } else if (e instanceof UnauthenticatedException) {
            System.out.println("授权认证失败,没有登录! 原因=" + e.getMessage());
            return "redirect:/login";
        } else if (e instanceof AuthorizationException) {
            System.out.println("授权认证失败! 原因=" + e.getMessage());
            return "unauthorized";
        }
        System.out.println(e.getMessage());
        return "redirect:/login";
    }

/*
    //如果你想给前端返回 json 信息，可以采用如下：
    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public R exceptionPage(Exception e) {
        String msg = null;
        int code = HttpStatus.UNAUTHORIZED.value();
        if (e instanceof UnknownAccountException) {
            msg = "登录认证失败,用户不存在!";
        } else if (e instanceof LockedAccountException) {
            msg = "登录认证失败,用户被禁用!";
        } else if (e instanceof IncorrectCredentialsException) {
            msg = "登录认证失败,密码不正确!";
        } else if (e instanceof AuthenticationException) {
            msg = "登录认证失败!";
        } else if (e instanceof UnauthorizedException) {
            msg = "授权认证失败,未授权访问!";
        } else if (e instanceof UnauthenticatedException) {
            msg = "授权认证失败,没有登录!";
        } else if (e instanceof AuthorizationException) {
            msg = "授权认证失败!";
        } else {
            msg = e.getMessage();
        }
        System.out.println(msg + "原因=" + e.getMessage());
        return R.error(code, msg);
    }
*/

}
