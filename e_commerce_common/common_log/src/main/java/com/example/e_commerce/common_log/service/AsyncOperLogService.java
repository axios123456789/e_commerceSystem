package com.example.e_commerce.common_log.service;

import com.example.e_commerce.model.entity.system.SysOperLog;

public interface AsyncOperLogService {
    void saveSysOperLog(SysOperLog sysOperLog); //把日志信息添加数据库里面
}
