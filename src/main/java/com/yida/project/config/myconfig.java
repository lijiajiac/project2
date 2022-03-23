package com.yida.project.config;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Properties;

/**
 * implements WebMvcConfigurer
 * 向springmvc注册权限拦截器
 */
@Configuration
public class myconfig implements WebMvcConfigurer {
    //设置数据库厂商提供者
    @Bean
    public DatabaseIdProvider getDatabaseIdProvider() {
        DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
        Properties properties = new Properties();
        properties.setProperty("MySQL", "mysql");
        properties.setProperty("Oracle", "oracle");
        databaseIdProvider.setProperties(properties);
        return databaseIdProvider;
    }
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(getLoginInterceptor()) //添加拦截对象
//                .addPathPatterns("/**")//拦截所有 url
//                .excludePathPatterns("/update","/delete","/select","/add","/Employee","/base","/admin","/login","/","/logout","/**/*.css","/**/*.js","/**/*.jpg","/**/*.png");//排除指定 url 和静态资源
//    }
//    @Bean
//    public LoginInterceptor getLoginInterceptor() {
//        return new LoginInterceptor();
//    }
}
