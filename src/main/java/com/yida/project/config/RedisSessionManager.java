package com.yida.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * redis 会话管理
 */

@Configuration
@EnableRedisHttpSession //开启 redis 会话管理
public class RedisSessionManager {
}
