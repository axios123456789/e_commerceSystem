package com.example.e_commerce.common_log.aspect;

import com.example.e_commerce.common_log.annotation.Log;
import com.example.e_commerce.common_log.service.AsyncOperLogService;
import com.example.e_commerce.common_log.utils.LogUtil;
import com.example.e_commerce.model.entity.system.SysOperLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect     //表示这个类为切面类
@Component
public class LogAspect {
    @Autowired
    private AsyncOperLogService asyncOperLogService;

    //环绕通知
    @Around(value = "@annotation(sysLog)")  //表示加了Log这个注解就执行这个环绕通知，相关参数在方法中
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint, Log sysLog) {//ProceedingJoinPoint可以用来调用业务方法以及获得相关参数信息
        SysOperLog sysOperLog = new SysOperLog();

        //业务方法调用之前，封装数据
        LogUtil.beforeHandleLog(sysLog, joinPoint, sysOperLog);

        //业务方法
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();

            //调用业务方法之后，封装数据
            LogUtil.afterHandleLog(sysLog, proceed, sysOperLog, 0, null);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            LogUtil.afterHandleLog(sysLog, proceed, sysOperLog, 1, throwable.getMessage());
            throw new RuntimeException();
        }

        //调用service方法把日志信息添加数据库里面
        asyncOperLogService.saveSysOperLog(sysOperLog);

        return proceed;
    }
}
