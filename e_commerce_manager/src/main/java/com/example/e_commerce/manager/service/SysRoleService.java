package com.example.e_commerce.manager.service;

import com.example.e_commerce.model.dto.system.AssginMenuDto;
import com.example.e_commerce.model.dto.system.SysRoleDto;
import com.example.e_commerce.model.entity.system.SysRole;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface SysRoleService {
    PageInfo<SysRole> findSysRoleByPageAndCondition(SysRoleDto sysRoleDto, Integer current, Integer limit); //角色列表方法

    boolean addSysRole(SysRole sysRole);   //角色添加

    boolean updateSysRole(SysRole sysRole); //角色修改

    boolean deleteSysRoleById(Long roleId); //逻辑删除角色

    Map<String, Object> findAllRoles(Long userId);  //查询所有角色

    boolean allocateMenus(AssginMenuDto assginMenuDto); //保存角色分配菜单数据
}
