package com.yida.project.config;

import com.yida.project.cache.RedisCacheManager;
import com.yida.project.shiro.ShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

/**
 * shiro配置类
 */
@Configuration
public class ShiroConfig {


    @Value("${Credential.AlgorithmName}")
    private String algorithmName;
    @Value("${Credential.hashIterations}")
    private Integer hashIterations;

    /**
     * 配置 shiro 的拦截规则
     * 注意：拦截规则，是有序的，即从上到下；拦截范围，从小到大。
     * 上面的匹配了，下面的就不会再生效！
     */
    @Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        //没有登录的情况下，访问了需要登录认证的页面，跳转的页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        //登录成功后，跳转的页面
        shiroFilterFactoryBean.setSuccessUrl("/main/indexs");
        //登录成功后，访问没权限的页面，跳转的页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");
        //设置拦截规则
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/MD5/md5a.js", "anon");//anon 表示匿名访问，不用登录
        filterChainDefinitionMap.put("/bt4/js/bootstrap.min.js", "anon");//anon 表示匿名访问，不用登录
        filterChainDefinitionMap.put("/bt4/js/popper.min.js", "anon");//anon 表示匿名访问，不用登录
        filterChainDefinitionMap.put("/bt4/css/bootstrap.css", "anon");//anon 表示匿名访问，不用登录
        filterChainDefinitionMap.put("/bt4/js/jquery-3.3.1.min.js", "anon");//anon 表示匿名访问，不用登录
        filterChainDefinitionMap.put("/css/login.css", "anon");//anon 表示匿名访问，不用登录
        filterChainDefinitionMap.put("/login", "anon");//anon 表示匿名访问，不用登录
        filterChainDefinitionMap.put("/loginData", "anon");//anon 表示匿名访问，不用登录
        //filterChainDefinitionMap.put("/verifyCode", "anon");//anon 表示匿名访问，不用登录
        filterChainDefinitionMap.put("/main/**", "authc");//登录后才能访问
        //filterChainDefinitionMap.put("/main/**", "user");//user表示登录用户或记住我，才能访问
        filterChainDefinitionMap.put("/logout", "logout");//logout 表示登出
        filterChainDefinitionMap.put("/**", "authc");//authc 表示登录后，才能访问
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }
    /**
     * 安全管理器
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());//设置 Realm
        //securityManager.setRememberMeManager(cookieRememberMeManager());//设置记住我
        return securityManager;
    }
    /**
     * 设置记住我管理器
     *//*
    @Bean
    public CookieRememberMeManager cookieRememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }

    @Bean //设置记住我的有效时长
    public SimpleCookie rememberMeCookie(){
        //这个参数是 cookie 的名称，对应前端页面的 checkbox 的 name=rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setMaxAge(120);//cookie 的有效时间为 30 天，单位秒
        return simpleCookie;
    }*/

    /**
     * 指定密文匹配器(即对前端的密码进行加密的格式)
     * 必须与后端数据库保存的加密密码。的加密方式一致
     *
     * @return
     */
    @Bean
    public ShiroRealm shiroRealm() {
        ShiroRealm sRealm = new ShiroRealm();
        //密文密码匹配器
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(algorithmName);//设置加密算法(MD5,SHA1,SHA-256)
        hashedCredentialsMatcher.setHashIterations(hashIterations);//设置加密次数
        sRealm.setCredentialsMatcher(hashedCredentialsMatcher);//设置加密校验匹配器

       //Redis 缓存
        sRealm.setCacheManager(new RedisCacheManager());
        sRealm.setCachingEnabled(true);//开启全局缓存
        sRealm.setAuthenticationCachingEnabled(true);//开启登录认证缓存

        sRealm.setAuthenticationCacheName("com.yida.project.shiro.ShiroRealm.authenticationCache");//自定义登录认证缓存名称
        sRealm.setAuthorizationCachingEnabled(true);//开启授权认证缓存
        sRealm.setAuthorizationCacheName("com.yida.project.shiro.ShiroRealm.authorizationCache");//自定义授权认证缓存名称

        return sRealm;
    }

    /**
     * 开启 shiro 注解，保证能够在 spring 组件中使用 shiro 的自身注解
     * 一般注解在 controller 类里
     */
    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authAttrSourceAdvisor = new
                AuthorizationAttributeSourceAdvisor();
        authAttrSourceAdvisor.setSecurityManager(securityManager());
        return authAttrSourceAdvisor;
    }






}
