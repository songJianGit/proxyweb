package com.xxsword.xitem.admin.config;

import com.xxsword.xitem.admin.interceptor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 后台拦截器
        InterceptorRegistration admin = registry.addInterceptor(loginInterceptor);
        // 配置不拦截的路径
        // admin.excludePathPatterns("/admin/system/login");
        // 配置拦截的路径
        admin.addPathPatterns("/admin/**");
    }

}