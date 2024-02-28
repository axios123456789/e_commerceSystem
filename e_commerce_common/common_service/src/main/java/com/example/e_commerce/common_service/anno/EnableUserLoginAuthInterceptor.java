package com.example.e_commerce.common_service.anno;

import com.example.e_commerce.common_service.config.UserWebMvcConfig;
import com.example.e_commerce.common_service.interceptor.UserLoginAuthInterceptor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@Import(value = {UserLoginAuthInterceptor.class, UserWebMvcConfig.class})
public @interface EnableUserLoginAuthInterceptor {
}
