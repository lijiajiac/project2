package com.yida.project.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 登录界面控制器类
 */
@Slf4j
@Controller
public class LoginController {

     @Value("${server.port}")
     private Integer port;

    @GetMapping("/login")
    public String login(Model model) {
            model.addAttribute("port", port);
            return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "logout";
    }


    /* @RequestParam(value = "rememberMe",required = false,defaultValue = "false")Boolean rememberMe,*/
    @PostMapping("/loginData")
    public String loginDate(@RequestParam("name") String name,
                            @RequestParam("password") String password,
                            HttpSession session, HttpServletRequest request) {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty((password))) {
            session.setAttribute("erro", "用户名或密码不能为空");
            System.out.println("用户名或密码不能为空");
            return "redirect:/login";
        }
        /*//校验验证码
        String verifyCode = (String) session.getAttribute("verifyCode");
        if (!verifyCode.equalsIgnoreCase(code)) {
            throw new RuntimeException("验证码错误");
        }*/
            Subject subject = SecurityUtils.getSubject();//获取主题对象
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(name, password);  //把前端传递的用户名和密码交给s
            subject.login(usernamePasswordToken);//登录认证}
            HttpSession session1 =  request.getSession();
            session1.setAttribute("Token", usernamePasswordToken);
        //usernamePasswordToken.setRememberMe(rememberMe);//记住我
        return "redirect:/main/indexs";
    }
}
