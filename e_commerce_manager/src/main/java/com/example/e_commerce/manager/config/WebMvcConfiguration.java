package com.example.e_commerce.manager.config;

import com.example.e_commerce.manager.interceptor.LoginAuthInterceptor;
import com.example.e_commerce.manager.properties.UserProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Autowired
    private LoginAuthInterceptor loginAuthInterceptor;

    @Autowired
    private UserProperties userProperties;

    //注册拦截器
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginAuthInterceptor)
                .excludePathPatterns("/admin/system/index/login",
                        "/admin/system/index/generateValidateCode")
                .excludePathPatterns(userProperties.getNoAuthUrls())//不需要拦截的路径
                .addPathPatterns("/**");    //需要拦截的路径
    }

    //跨域
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")      // 添加路径规则
                .allowCredentials(true)               // 是否允许在跨域的情况下传递Cookie
                .allowedOriginPatterns("*")           // 允许请求来源的域规则
                .allowedMethods("*")
                .allowedHeaders("*") ;
    }
}
