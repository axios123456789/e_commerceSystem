import request from "@/utils/request";

const base_api = '/admin/system/sysRole'

//角色列表
export const GetSysRoleListByPage = (current, limit, queryDto) => {
    return request({
        //``模板字符串
        url: `${base_api}/findSysRoleByPageAndCondition/${current}/${limit}`,
        method: 'post',
        //接口@requestBody 前端 data：名称，以json格式传输
        //接口没有注解，前端 params：名称
        data: queryDto,
    })
}

//添加角色
export const addSysRole = (sysRole) => {
    return request({
        url: `${base_api}/addSysRole`,
        method: 'post',
        data: sysRole,
    })
}

//修改角色
export const updateSysRole = (sysRole) => {
    return request({
        url: `${base_api}/updateSysRole`,
        method: 'put',
        data: sysRole,
    })
}

//删除角色
export const deleteSysRoleById = (roleId) => {
    return request({
        url: `${base_api}/deleteSysRoleById/${roleId}`,
        method: 'delete',
    })
}

//查询所有角色
export const getAllRoles = (userId) => {
    return request({
        url: `${base_api}/findAllRoles/${userId}`,
        method: 'get',
    })
}

//分配菜单
export const allocateMenus = (assignMenuDto) => {
    return request({
        url: `${base_api}/allocateMenus`,
        method: 'post',
        data: assignMenuDto,
    })
}