package com.example.e_commerce.manager.service.impl;

import com.example.e_commerce.common_service.exception.MyException;
import com.example.e_commerce.common_util.AuthContextUtil;
import com.example.e_commerce.manager.mapper.SysMenuMapper;
import com.example.e_commerce.manager.mapper.SysRoleAndMenuRelation;
import com.example.e_commerce.manager.service.SysMenuService;
import com.example.e_commerce.manager.util.MenuHelper;
import com.example.e_commerce.model.entity.system.SysMenu;
import com.example.e_commerce.model.vo.common.ResultCodeEnum;
import com.example.e_commerce.model.vo.system.SysMenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysRoleAndMenuRelation sysRoleAndMenuRelation;

    /**
     * 菜单列表
     * @return
     */
    @Override
    public List<SysMenu> findNodes() {
        //查询所有菜单
        List<SysMenu> sysMenus = sysMenuMapper.findAllMenus();
        if (CollectionUtils.isEmpty(sysMenus)){
            return null;
        }

        //调用工具类将查到的数据处理成要求的格式
        List<SysMenu> trees = MenuHelper.buildTree(sysMenus);

        return trees;
    }

    /**
     * 添加菜单
     * @param sysMenu
     * @return
     */
    @Override
    public boolean addMenu(SysMenu sysMenu) {
        try {
            sysMenuMapper.addMenu(sysMenu);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 修改菜单
     * @param sysMenu
     * @return
     */
    @Override
    public boolean updateMenu(SysMenu sysMenu) {
        try {
            sysMenuMapper.updateMenu(sysMenu);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 删除菜单
     * @param id
     * @return
     */
    @Override
    public boolean deleteMenuById(Long id) {
        //根据当前菜单id，查询是否包含子菜单
        int childrenCountById = sysMenuMapper.getChildrenCountById(id);
        if (childrenCountById > 0){
            throw new MyException(ResultCodeEnum.NODE_ERROR);
        }

        try {
            //删除
            sysMenuMapper.deleteMenuById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 查询所有菜单和已经分配的菜单id
     * @param roleId
     * @return
     */
    @Override
    public Map<String, Object> findAllMenusWithRoleId(Long roleId) {
        //查询所有菜单
        List<SysMenu> allMenus = sysMenuMapper.findAllMenus();
        List<SysMenu> sysMenus = MenuHelper.buildTree(allMenus);

        //查询分配过的菜单id
        List<Long> menuIds = sysRoleAndMenuRelation.selectMenuIdsByRoleId(roleId);

        //封装数据
        Map<String, Object> map = new HashMap<>();
        map.put("sysMenuList", sysMenus);
        map.put("roleMenuIds", menuIds);

        return map;
    }

    /**
     * 查询用户可以操作菜单
     * @return
     */
    @Override
    public List<SysMenuVo> findMenusByUserId() {
        //得到当前用户id
        Long userId = AuthContextUtil.get().getId();

        //根据id查询到当前用户可以操作的菜单
        List<SysMenu> sysMenus = sysMenuMapper.findMenusByUserId(userId);

        //将查询到的数据封装成要求的格式
        List<SysMenu> sysMenus1 = MenuHelper.buildTree(sysMenus);
        List<SysMenuVo> sysMenuVos = buildMenus(sysMenus1);

        return sysMenuVos;
    }

    /**
     * 查询父菜单
     * @param parentId
     * @return
     */
    @Override
    public SysMenu selectParentMenu(Long parentId) {
        SysMenu sysMenu = sysMenuMapper.selectParentMenu(parentId);

        return sysMenu;
    }

    // 将List<SysMenu>对象转换成List<SysMenuVo>对象
    private List<SysMenuVo> buildMenus(List<SysMenu> menus) {

        List<SysMenuVo> sysMenuVoList = new LinkedList<SysMenuVo>();
        for (SysMenu sysMenu : menus) {
            SysMenuVo sysMenuVo = new SysMenuVo();
            sysMenuVo.setTitle(sysMenu.getTitle());
            sysMenuVo.setName(sysMenu.getComponent());
            List<SysMenu> children = sysMenu.getChildren();
            if (!CollectionUtils.isEmpty(children)) {
                sysMenuVo.setChildren(buildMenus(children));
            }
            sysMenuVoList.add(sysMenuVo);
        }
        return sysMenuVoList;
    }
}
