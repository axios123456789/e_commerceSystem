package com.example.e_commerce.manager.mapper;

import com.example.e_commerce.model.entity.system.SysRoleUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysRoleAndUserRelation {
    //根据userId查询roleId
    @Select("select role_id from sys_user_role where user_id = #{userId} and is_deleted = 0")
    List<Long> getRoleIdsByUserId(Long userId);

    //删除已经分配过的角色
    void deleteRoleByUserId(Long userId);

    //添加角色到关联关系表中通过用户
    void addRolesByUserId(List<Map> userAndRole);
}
