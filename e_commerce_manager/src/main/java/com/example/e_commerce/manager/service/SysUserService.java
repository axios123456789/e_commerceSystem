package com.example.e_commerce.manager.service;

import com.example.e_commerce.model.dto.system.AssginRoleDto;
import com.example.e_commerce.model.dto.system.LoginDto;
import com.example.e_commerce.model.dto.system.SysUserDto;
import com.example.e_commerce.model.entity.system.SysUser;
import com.example.e_commerce.model.vo.system.LoginVo;
import com.github.pagehelper.PageInfo;

public interface SysUserService {
    LoginVo login(LoginDto loginDto);   //用户登录

    SysUser getUserInfo(String token);  //获取用户信息

    void logout(String token);  //退出登录

    PageInfo<SysUser> findSysUserListByPageAndCondition(Integer pageNum, Integer pageSize, SysUserDto sysUserDto);    //条件分页查询用户信息列表

    boolean addSysUser(SysUser sysUser);    //添加用户

    boolean updateSysUser(SysUser sysUser); //修改用户信息

    boolean deleteSysUserById(Long userId); //用户逻辑删除

    boolean allocateRoles(AssginRoleDto assginRoleDto); //为用户分配角色
}
