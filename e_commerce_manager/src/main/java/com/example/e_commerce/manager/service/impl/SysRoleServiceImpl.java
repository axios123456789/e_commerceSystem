package com.example.e_commerce.manager.service.impl;

import com.example.e_commerce.manager.mapper.SysRoleAndMenuRelation;
import com.example.e_commerce.manager.mapper.SysRoleAndUserRelation;
import com.example.e_commerce.manager.mapper.SysRoleMapper;
import com.example.e_commerce.manager.service.SysRoleService;
import com.example.e_commerce.manager.util.ManualCreateTransaction;
import com.example.e_commerce.model.dto.system.AssginMenuDto;
import com.example.e_commerce.model.dto.system.SysRoleDto;
import com.example.e_commerce.model.entity.system.SysRole;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleAndUserRelation sysRoleAndUserRelation;

    @Autowired
    private SysRoleAndMenuRelation sysRoleAndMenuRelation;

    @Autowired
    private PlatformTransactionManager transactionManager;

    /**
     * 角色列表方法
     * @param sysRoleDto
     * @param current
     * @param limit
     * @return
     */
    @Override
    public PageInfo<SysRole> findSysRoleByPageAndCondition(SysRoleDto sysRoleDto, Integer current, Integer limit) {
        //设置分页参数
        PageHelper.startPage(current, limit);

        //条件查询所有数据
        List<SysRole> sysRoles = sysRoleMapper.findSysRoleByCondition(sysRoleDto);

        //封装成pageInfo对象
        PageInfo<SysRole> sysRolePageInfo = new PageInfo<>(sysRoles);

        return sysRolePageInfo;
    }

    /**
     * 角色添加
     * @param sysRole
     */
    @Override
    @Transactional
    public boolean addSysRole(SysRole sysRole) {
        try {
            sysRoleMapper.addSysRole(sysRole);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 修改角色
     * @param sysRole
     * @return
     */
    @Override
    @Transactional
    public boolean updateSysRole(SysRole sysRole) {
        try {
            sysRoleMapper.updateSysRole(sysRole);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 逻辑删除角色
     * @param roleId
     * @return
     */
    @Override
    @Transactional
    public boolean deleteSysRoleById(Long roleId) {
        try {
            sysRoleMapper.deleteSysRoleById(roleId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 查询所有角色
     * @param userId
     * @return
     */
    @Override
    public Map<String, Object> findAllRoles(Long userId) {
        //查询所有角色
        List<SysRole> sysRoles = sysRoleMapper.findAllRoles();

        //查询已经分配了的角色
        List<Long> roleIds = sysRoleAndUserRelation.getRoleIdsByUserId(userId);

        Map<String, Object> map = new HashMap<>();
        map.put("allRoleList", sysRoles);
        map.put("sysUserRoles", roleIds);

        return map;
    }

    /**
     * 保存角色分配菜单数据
     * @param assginMenuDto
     * @return
     */
    @Override
    @Transactional
    public boolean allocateMenus(AssginMenuDto assginMenuDto) {
        TransactionStatus status = transactionManager.getTransaction(ManualCreateTransaction.getManualCreateTransaction());
        try {
            //删除角色分配过的菜单数据
            sysRoleAndMenuRelation.deleteHaveAllocateMenuDataByRoleId(assginMenuDto.getRoleId());

            //保存分配数据
            List<Map<String, Number>> menuIdList = assginMenuDto.getMenuIdList();
            if (menuIdList != null && menuIdList.size() > 0){
                sysRoleAndMenuRelation.addAllocateData(assginMenuDto);
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
