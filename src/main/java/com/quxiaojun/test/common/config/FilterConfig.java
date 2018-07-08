package com.quxiaojun.test.common.config;

import com.quxiaojun.test.common.filter.SessionFilter;
import com.quxiaojun.test.common.services.SessionService;
import com.quxiaojun.test.common.util.SessionServiceHolder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.Filter;
import java.io.Serializable;

@Configuration
public class FilterConfig {
    /**
     * 配置sessionFilter
     */
    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(sessionFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("sessionFilter");
        return registration;
    }

    /**
     * 配置SessionServiceHolder
     */
    @Bean
    public SessionServiceHolder sessionUtil(RedisTemplate<Serializable, Serializable> redisTemplate){
        SessionService sessionService = new SessionService();
        sessionService.setRedisTemplate(redisTemplate);

        SessionServiceHolder util = new SessionServiceHolder();
        util.setSessionService(sessionService);
        return util;
    }

    private Filter sessionFilter() {
        return new SessionFilter();
    }
}
