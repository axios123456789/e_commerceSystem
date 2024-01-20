package com.example.e_commerce.manager.controller;

import com.example.e_commerce.manager.mapper.SysRoleAndMenuRelation;
import com.example.e_commerce.manager.service.SysMenuService;
import com.example.e_commerce.model.entity.system.SysMenu;
import com.example.e_commerce.model.vo.common.Result;
import com.example.e_commerce.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysRoleAndMenuRelation sysRoleAndMenuRelation;

    //菜单列表接口
    @GetMapping("/findNodes")
    public Result findNodes(){
        List<SysMenu> sysMenus = sysMenuService.findNodes();
        return Result.build(sysMenus, ResultCodeEnum.SUCCESS);
    }

    //添加菜单接口
    @PostMapping("/addMenu")
    public Result addMenu(@RequestBody SysMenu sysMenu){
        boolean flag = sysMenuService.addMenu(sysMenu);

        //添加子菜单，把父菜单的isHalf半开状态
        updateSysRoleMenu(sysMenu);

        if (flag){
            return Result.build(null, ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(null, 500, "添加菜单失败，请检查添加信息后重试！");
        }
    }

    //新添子菜单，将父菜单的isHalf半开 1
    private void updateSysRoleMenu(SysMenu sysMenu) {
        //获取当前添加菜单的父菜单
        SysMenu parentSysMenu = sysMenuService.selectParentMenu(sysMenu.getParentId());

        if (parentSysMenu != null){
            //把isHalf半开
            sysRoleAndMenuRelation.updateSysRoleMenuIsHalf(parentSysMenu.getId());

            //递归调用
            updateSysRoleMenu(parentSysMenu);
        }
    }

    //修改菜单接口
    @PutMapping("/updateMenu")
    public Result updateMenu(@RequestBody SysMenu sysMenu){
        boolean flag = sysMenuService.updateMenu(sysMenu);

        if (flag){
            return Result.build(null, ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(null, 500, "修改菜单失败，请检查修改信息后重试！");
        }
    }

    //删除菜单接口
    @DeleteMapping("/deleteMenuById/{id}")
    public Result deleteMenuById(@PathVariable("id") Long id){
        boolean flag = sysMenuService.deleteMenuById(id);

        if (flag) {
            return Result.build(null, ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(null, 500, "删除失败，请重试！");
        }
    }

    //查询所有菜单和角色分配过的菜单
    @GetMapping("/findAllMenusWithRoleId/{roleId}")
    public Result findAllMenusWithRoleId(@PathVariable("roleId") Long roleId){
        Map<String, Object> map = sysMenuService.findAllMenusWithRoleId(roleId);
        return Result.build(map, ResultCodeEnum.SUCCESS);
    }
}
