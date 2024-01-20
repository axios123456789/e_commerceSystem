package com.example.e_commerce.manager.util;

import com.example.e_commerce.model.entity.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

//封装树形菜单数据
public class MenuHelper {

    //递归实现封装过程
    public static List<SysMenu> buildTree(List<SysMenu> sysMenus){
        //创建list集合用于封装最终数据
        List<SysMenu> trees = new ArrayList<>();

        //遍历所有菜单集合
        for (SysMenu sysMenu:sysMenus){
            //找到递归操作的入口， 第一层菜单
            if (sysMenu.getParentId().longValue() == 0){
                trees.add(findChildren(sysMenu, sysMenus));
            }
        }
        
        return trees;
    }

    //递归查找下层菜单
    private static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> sysMenus) {
        //初始化children
        sysMenu.setChildren(new ArrayList<>());

        //递归查询
        for (SysMenu item:sysMenus){
            if (item.getParentId().longValue() == sysMenu.getId().longValue()){
                sysMenu.getChildren().add(findChildren(item, sysMenus));
            }
        }

        return sysMenu;
    }
}
