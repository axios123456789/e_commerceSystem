import request from "@/utils/request";

const base_api = '/admin/system/sysUser'

//用户列表
export const getSysUserListByPageAndCondition = (current, limit, queryDto) => {
    return request({
        url: `${base_api}/findSysUserListByPageAndCondition/${current}/${limit}`,
        method: 'get',
        params: queryDto,
    })
}

//用户添加
export const addSysUser = (sysUser) => {
    return request({
        url: `${base_api}/addSysUser`,
        method: 'post',
        data: sysUser,
    })
}

//用户修改
export const updateSysUser = (sysUser) => {
    return request({
        url: `${base_api}/updateSysUser`,
        method: 'put',
        data: sysUser,
    })
}

//用户删除
export const deleteSysUser = (userId) => {
    return request({
        url: `${base_api}/deleteSysUserById/${userId}`,
        method: 'delete',
    })
}

//分配角色
export const allocateRoles = (assignRoleVo) => {
    return request({
        url: `${base_api}/allocateRoles`,
        method: 'post',
        data: assignRoleVo,
    })
}