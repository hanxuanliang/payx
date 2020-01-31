package com.hxl.payx.config;

import com.hxl.payx.interceptor.UserLoginStatus;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description: 拦截器 配置类
 * @Author: hanxuanliang
 * @Date: 2020/1/29 17:48
 * <p>
 * Http层面的拦截，使用拦截器；
 * 包层面的拦截，使用AOP；
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserLoginStatus())
                .addPathPatterns("/**")
                .excludePathPatterns("/error",
                        "/user/login", "/user/register",
                        "/categories", "/pages", "/pages/*", "/product/*",
                        "/cart");
    }
}
