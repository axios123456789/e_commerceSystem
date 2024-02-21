package com.example.e_commerce.common_log.annotation;

import com.example.e_commerce.common_log.enums.OperatorType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*自定义注解，需要加入源注解*/
@Target(ElementType.METHOD)   //表示该注解可以用的地方，这里可以用的地方是所有方法上
@Retention(RetentionPolicy.RUNTIME)     //表示注解的生命周期，这里的生命周期是运行时
public @interface Log {

    public String title() ;								// 模块名称
    public OperatorType operatorType() default OperatorType.MANAGE;	// 操作人类别
    public int businessType() ;     // 业务类型（0其它 1新增 2修改 3删除）
    public boolean isSaveRequestData() default true;   // 是否保存请求的参数
    public boolean isSaveResponseData() default true;  // 是否保存响应的参数
}
