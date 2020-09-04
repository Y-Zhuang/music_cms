package com.zhuang.music_cms.config;

import com.zhuang.music_cms.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @Package     : com.zhuang.music_cms.config
 * @ClassName   : WebConfig 
 * @Description : web配置类
 * @Author      : Zhuang
 * @Date        : 2020-04-22 0:11
 */

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/**").excludePathPatterns("/user/login");
    }

}
