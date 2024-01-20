package com.example.e_commerce.manager.service;

import com.example.e_commerce.model.entity.system.SysMenu;
import com.example.e_commerce.model.vo.system.SysMenuVo;

import java.util.List;
import java.util.Map;

public interface SysMenuService {
    List<SysMenu> findNodes();  //菜单列表

    boolean addMenu(SysMenu sysMenu);   //添加菜单

    boolean updateMenu(SysMenu sysMenu);    //修改菜单

    boolean deleteMenuById(Long id);    //删除菜单

    Map<String, Object> findAllMenusWithRoleId(Long roleId);    //查询所有菜单和已经分配的菜单id

    List<SysMenuVo> findMenusByUserId();    //查询用户可以操作菜单

    SysMenu selectParentMenu(Long parentId);    //查询父菜单
}
