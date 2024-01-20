package com.example.e_commerce.manager.mapper;

import com.example.e_commerce.model.entity.system.SysMenu;
import com.example.e_commerce.model.vo.system.SysMenuVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SysMenuMapper {
    //查询所有菜单
    List<SysMenu> findAllMenus();

    //添加菜单
    void addMenu(SysMenu sysMenu);

    //修改菜单
    void updateMenu(SysMenu sysMenu);

    //根据id查询子菜单的数量
    @Select("select count(*) from sys_menu where parent_id = #{id} and is_deleted = 0")
    int getChildrenCountById(Long id);

    //逻辑删除菜单
    @Update("update sys_menu set is_deleted = 1 where id = #{id}")
    void deleteMenuById(Long id);

    //查询用户可以操作菜单
    List<SysMenu> findMenusByUserId(Long userId);

    //查询父菜单
    @Select("select * from sys_menu where id = #{parentId} and is_deleted = 0")
    SysMenu selectParentMenu(Long parentId);
}
