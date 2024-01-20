package com.example.e_commerce.manager.mapper;

import com.example.e_commerce.model.dto.system.AssginMenuDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SysRoleAndMenuRelation {
    //查询分配过的菜单id
    List<Long> selectMenuIdsByRoleId(Long roleId);

    //删除角色分配过的菜单数据
    @Update("update sys_role_menu set is_deleted = 1 where role_id = #{roleId}")
    void deleteHaveAllocateMenuDataByRoleId(Long roleId);

    //保存分配数据
    void addAllocateData(AssginMenuDto assginMenuDto);

    //将isHalf变为半开状态
    void updateSysRoleMenuIsHalf(Long id);
}
