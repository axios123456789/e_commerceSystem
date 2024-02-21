package com.example.e_commerce.manager.mapper;

import com.example.e_commerce.model.entity.system.SysOperLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysOperLogMapper {
    //把日志信息添加数据库里面
    void insert(SysOperLog sysOperLog);
}
