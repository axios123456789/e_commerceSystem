package com.example.e_commerce.manager.controller;

import com.example.e_commerce.common_util.AuthContextUtil;
import com.example.e_commerce.manager.service.SysMenuService;
import com.example.e_commerce.manager.service.SysUserService;
import com.example.e_commerce.manager.service.ValidateCodeService;
import com.example.e_commerce.model.dto.system.LoginDto;
import com.example.e_commerce.model.entity.system.SysUser;
import com.example.e_commerce.model.vo.common.Result;
import com.example.e_commerce.model.vo.common.ResultCodeEnum;
import com.example.e_commerce.model.vo.system.LoginVo;
import com.example.e_commerce.model.vo.system.SysMenuVo;
import com.example.e_commerce.model.vo.system.ValidateCodeVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "用户接口")
@RestController
@RequestMapping(value = "/admin/system/index")
public class IndexController {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ValidateCodeService validateCodeService;

    @Autowired
    private SysMenuService sysMenuService;

    //查询用户可以操作菜单
    @GetMapping("/menus")
    public Result menus(){
        List<SysMenuVo> sysMenuVos = sysMenuService.findMenusByUserId();
        return Result.build(sysMenuVos, ResultCodeEnum.SUCCESS);
    }

    //退出登录
    @GetMapping("/logout")
    public Result logout(@RequestHeader(name = "token") String token){
        sysUserService.logout(token);

        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //获取当前登录用户信息
    /*@GetMapping("/getUserInfo")
    public Result getUserInfo(@RequestHeader(name = "token") String token){
        SysUser sysUser = sysUserService.getUserInfo(token);

        return Result.build(sysUser, ResultCodeEnum.SUCCESS);
    }*/
    @GetMapping("/getUserInfo")
    public Result getUserInfo(){
        return Result.build(AuthContextUtil.get(), ResultCodeEnum.SUCCESS);
    }

    //生成图片验证码
    @GetMapping("/generateValidateCode")
    public Result<ValidateCodeVo> generateValidateCode(){
        ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode();
        return Result.build(validateCodeVo,ResultCodeEnum.SUCCESS);
    }

    //用户登录
    @Operation(summary = "登录的方法")
    @PostMapping("/login")
    public Result login(@RequestBody LoginDto loginDto){
        LoginVo loginVo = sysUserService.login(loginDto);
        return Result.build(loginVo, ResultCodeEnum.SUCCESS);
    }
}
