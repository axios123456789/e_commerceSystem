package com.example.e_commerce.manager.controller;

import com.example.e_commerce.manager.service.SysUserService;
import com.example.e_commerce.model.dto.system.AssginRoleDto;
import com.example.e_commerce.model.dto.system.SysUserDto;
import com.example.e_commerce.model.entity.system.SysUser;
import com.example.e_commerce.model.vo.common.Result;
import com.example.e_commerce.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    //条件分页查询用户信息接口
    @GetMapping("/findSysUserListByPageAndCondition/{pageNum}/{pageSize}")
    public Result findSysUserListByPageAndCondition(@PathVariable("pageNum") Integer pageNum,
                                                    @PathVariable("pageSize") Integer pageSize,
                                                    SysUserDto sysUserDto){
        PageInfo<SysUser> pageInfo = sysUserService.findSysUserListByPageAndCondition(pageNum, pageSize, sysUserDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    //添加接口
    @PostMapping("/addSysUser")
    public Result addSysUser(@RequestBody SysUser sysUser){
        boolean flag = sysUserService.addSysUser(sysUser);

        if (flag){
            return Result.build(null, ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(null, 500, "添加用户失败，请检查添加信息无误后重试！");
        }
    }

    //修改接口
    @PutMapping("/updateSysUser")
    public Result updateSysUser(@RequestBody SysUser sysUser){
        boolean flag = sysUserService.updateSysUser(sysUser);

        if (flag){
            return Result.build(null, ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(null, 500, "修改失败，请检查修改信息后重试！");
        }
    }

    //用户删除接口
    @DeleteMapping("/deleteSysUserById/{userId}")
    public Result deleteSysUserById(@PathVariable("userId") Long userId){
        boolean flag = sysUserService.deleteSysUserById(userId);

        if (flag){
            return Result.build(null, ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(null, 500, "删除失败，请重试！");
        }
    }

    //分配角色的接口
    @PostMapping("/allocateRoles")
    public Result allocateRoles(@RequestBody AssginRoleDto assginRoleDto){
        boolean flag = sysUserService.allocateRoles(assginRoleDto);

        if (flag){
            return Result.build(null, ResultCodeEnum.SUCCESS);
        }else {
            return Result.build(null, 500, "角色分配失败！");
        }
    }
}
