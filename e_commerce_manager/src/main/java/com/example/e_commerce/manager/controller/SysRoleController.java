package com.example.e_commerce.manager.controller;

import com.example.e_commerce.manager.service.SysRoleService;
import com.example.e_commerce.model.dto.system.AssginMenuDto;
import com.example.e_commerce.model.dto.system.SysRoleDto;
import com.example.e_commerce.model.entity.system.SysRole;
import com.example.e_commerce.model.vo.common.Result;
import com.example.e_commerce.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    //查询所有角色
    @GetMapping("/findAllRoles/{userId}")
    public Result findAllRoles(@PathVariable("userId") Long userId){
        Map<String, Object> map = sysRoleService.findAllRoles(userId);
        return Result.build(map, ResultCodeEnum.SUCCESS);
    }

    //角色列表的方法
    @PostMapping("/findSysRoleByPageAndCondition/{current}/{limit}")
    public Result findSysRoleByPageAndCondition(@PathVariable("current") Integer current,
                                                @PathVariable("limit") Integer limit,
                                                @RequestBody SysRoleDto sysRoleDto){
        //pageHelper插件实现分页
        PageInfo<SysRole> sysRolePageInfo = sysRoleService.findSysRoleByPageAndCondition(sysRoleDto, current, limit);
        return Result.build(sysRolePageInfo, ResultCodeEnum.SUCCESS);
    }

    //角色添加
    @PostMapping("/addSysRole")
    public Result addSysRole(@RequestBody SysRole sysRole){
        boolean flag = sysRoleService.addSysRole(sysRole);

        if (flag){
            return Result.build(null, ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(null, 500, "角色添加失败,请确认相关信息无误后重试！");
        }
    }

    //修改角色
    @PutMapping("/updateSysRole")
    public Result updateSysRole(@RequestBody SysRole sysRole){
        boolean flag = sysRoleService.updateSysRole(sysRole);

        if (flag){
            return Result.build(null, ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(null, 500, "修改角色失败，请确认相关信息无误后重试！");
        }
    }

    //删除角色
    @DeleteMapping("/deleteSysRoleById/{roleId}")
    public Result deleteSysRoleById(@PathVariable("roleId") Long roleId){
        boolean flag = sysRoleService.deleteSysRoleById(roleId);

        if (flag){
            return Result.build(null, ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(null, 500, "删除角色失败，请重试！");
        }
    }

    //保存角色分配菜单数据
    @PostMapping("/allocateMenus")
    public Result allocateMenus(@RequestBody AssginMenuDto assginMenuDto){
        boolean flag = sysRoleService.allocateMenus(assginMenuDto);

        if (flag){
            return Result.build(null, ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(null, 500, "菜单分配失败！");
        }
    }
}
