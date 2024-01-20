package com.example.e_commerce.manager.mapper;

import com.example.e_commerce.model.dto.system.SysRoleDto;
import com.example.e_commerce.model.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysRoleMapper {
    //条件查询角色
    List<SysRole> findSysRoleByCondition(SysRoleDto sysRoleDto);

    //添加角色
    void addSysRole(SysRole sysRole);

    //修改角色
    void updateSysRole(SysRole sysRole);

    //逻辑删除角色
    void deleteSysRoleById(Long roleId);

    //查询所有角色
    @Select("select id, role_name from sys_role where is_deleted = 0")
    List<SysRole> findAllRoles();
}
