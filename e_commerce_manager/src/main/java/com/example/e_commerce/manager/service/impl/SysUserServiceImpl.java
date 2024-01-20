package com.example.e_commerce.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.example.e_commerce.common_service.exception.MyException;
import com.example.e_commerce.manager.util.ManualCreateTransaction;
import com.example.e_commerce.manager.mapper.SysRoleAndUserRelation;
import com.example.e_commerce.manager.mapper.SysUserMapper;
import com.example.e_commerce.manager.service.SysUserService;
import com.example.e_commerce.model.dto.system.AssginRoleDto;
import com.example.e_commerce.model.dto.system.LoginDto;
import com.example.e_commerce.model.dto.system.SysUserDto;
import com.example.e_commerce.model.entity.system.SysUser;
import com.example.e_commerce.model.vo.common.ResultCodeEnum;
import com.example.e_commerce.model.vo.system.LoginVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleAndUserRelation sysRoleAndUserRelation;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 用户登录
     * @param loginDto
     * @return
     */
    @Override
    public LoginVo login(LoginDto loginDto) {
        //校验验证码
        //1.获取输入的验证码和存储到redis中的key的名称
        String captcha = loginDto.getCaptcha();
        String codeKey = loginDto.getCodeKey();

        //2.根据key在redis中查询验证码的值
        String redisCode = (String) redisTemplate.opsForValue().get("user:validate" + codeKey);

        //3.比较输入的验证码和查询到的验证码是否一致
        //4.如果不一致，提示用户校验失败
        if (StrUtil.isEmpty(redisCode) || !StrUtil.equalsIgnoreCase(redisCode, captcha)){
            throw new MyException(ResultCodeEnum.VALIDATECODE_ERROR);
        }

        //5.如果一致，删除redis中的验证码
        redisTemplate.delete("user:validate" + codeKey);

        //1.获取提交的用户名，loginDto中获取
        //2.根据用户名查询sys_user表
        SysUser sysUser = sysUserMapper.getSysUserByUserName(loginDto.getUserName());

        //3.如果查不到数据，用户不存在，返回错误信息
        if (sysUser == null){
            throw new MyException(ResultCodeEnum.LOGIN_ERROR);
        }

        //4.如果查到了用户信息，用户存在
        //5.获取输入的密码，比较输入的密码与数据库的密码是否一致
        String databasePassword = sysUser.getPassword();
        //对输入的密码进行加密
        String inputPassword = DigestUtils.md5DigestAsHex(loginDto.getPassword().getBytes());
        if (!inputPassword.equals(databasePassword)){
            throw new MyException(ResultCodeEnum.LOGIN_ERROR);
        }

        //6.如果密码一致，登录成功，否则失败
        //7.登录成功，生成唯一标识token
        String token = UUID.randomUUID().toString().replaceAll("-", "");

        //8.把登录成功信息存到redis里面
        redisTemplate.opsForValue().set("user:login"+token,
                JSON.toJSONString(sysUser),
                3,
                TimeUnit.HOURS);

        //9.返回loginVo对象
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        return loginVo;
    }

    /**
     * 获取用户信息
     * @param token
     * @return
     */
    @Override
    public SysUser getUserInfo(String token) {
        //1.根据token在redis中查询对应value
        String jsonUser = (String) redisTemplate.opsForValue().get("user:login" + token);

        //2.将查到的json数据转化成sysUser
        SysUser sysUser = JSON.parseObject(jsonUser, SysUser.class);

        return sysUser;
    }

    /**
     * 退出登录
     * @param token
     */
    @Override
    public void logout(String token) {
        redisTemplate.delete("user:login"+token);
    }

    /**
     * 条件分页查询用户信息
     * @param pageNum
     * @param pageSize
     * @param sysUserDto
     * @return
     */
    @Override
    public PageInfo<SysUser> findSysUserListByPageAndCondition(Integer pageNum, Integer pageSize, SysUserDto sysUserDto) {
        //设置分页
        PageHelper.startPage(pageNum, pageSize);

        //条件查询所有用户数据
        List<SysUser> sysUsers = sysUserMapper.findSysUserListByCondition(sysUserDto);

        //封装成pageInfo对象
        PageInfo<SysUser> sysUserPageInfo = new PageInfo<>(sysUsers);

        return sysUserPageInfo;
    }

    /**
     * 添加用户
     * @param sysUser
     * @return
     */
    @Override
    @Transactional
    public boolean addSysUser(SysUser sysUser) {
        //判断用户名是否重复
        SysUser sysUser1 = sysUserMapper.getSysUserByUserName(sysUser.getUserName());
        if (sysUser1 != null){
            throw new MyException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }

        try {
            //对密码进行加密
            String password = DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes());
            sysUser.setPassword(password);

            //设置状态为1
            sysUser.setStatus(1);

            //添加操作
            sysUserMapper.addSysUser(sysUser);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 修改用户信息
     * @param sysUser
     * @return
     */
    @Override
    @Transactional
    public boolean updateSysUser(SysUser sysUser) {
        try {
            sysUserMapper.updateSysUser(sysUser);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 用户逻辑删除
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public boolean deleteSysUserById(Long userId) {
        try {
            sysUserMapper.deleteSysUserById(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 为用户分配角色
     * @param assginRoleDto
     * @return
     */
    @Override
    @Transactional
    public boolean allocateRoles(AssginRoleDto assginRoleDto) {
        TransactionStatus status = transactionManager.getTransaction(ManualCreateTransaction.getManualCreateTransaction());
        try {
            //分配角色前先删除之前已经分配过的角色
            sysRoleAndUserRelation.deleteRoleByUserId(assginRoleDto.getUserId());

            if (assginRoleDto.getRoleIdList().size() > 0) {
                //处理数据
                List<Map> userAndRole = assginRoleDto.getRoleIdList().stream().map(roleId -> {
                    Map mp = new HashMap();
                    mp.put("userId", assginRoleDto.getUserId());
                    mp.put("roleId", roleId);
                    return mp;
                }).collect(Collectors.toList());

                //分配角色
                sysRoleAndUserRelation.addRolesByUserId(userAndRole);
            }
        } catch (Exception e) {
            e.printStackTrace();
            transactionManager.rollback(status);
            return false;
        }

        transactionManager.commit(status);
        return true;
    }
}
