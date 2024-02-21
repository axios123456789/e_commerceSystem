package com.example.e_commerce.manager.service.impl;

import com.example.e_commerce.common_log.service.AsyncOperLogService;
import com.example.e_commerce.manager.mapper.SysOperLogMapper;
import com.example.e_commerce.model.entity.system.SysOperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AsyncOperLogServiceImpl implements AsyncOperLogService {
    @Autowired
    private SysOperLogMapper sysOperLogMapper;

    /**
     * 把日志信息添加数据库里面
     * @param sysOperLog
     */
    @Override
    public void saveSysOperLog(SysOperLog sysOperLog) {
       sysOperLogMapper.insert(sysOperLog);
    }
}
