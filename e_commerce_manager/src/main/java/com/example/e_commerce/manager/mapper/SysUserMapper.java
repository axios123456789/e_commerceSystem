package com.example.e_commerce.manager.mapper;

import com.example.e_commerce.model.dto.system.SysUserDto;
import com.example.e_commerce.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserMapper {
    //根据名字查询用户信息
//    SysUser getUserInfoByUserName(String userName);

    //条件查询用户信息
    List<SysUser> findSysUserListByCondition(SysUserDto sysUserDto);

    //添加用户
    void addSysUser(SysUser sysUser);

    //根据用户名查询用户
    SysUser getSysUserByUserName(String userName);

    //修改用户
    void updateSysUser(SysUser sysUser);

    //逻辑删除用户根据id
    void deleteSysUserById(Long userId);
}
