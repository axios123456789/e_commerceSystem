import request from "@/utils/request";

const api_name = '/admin/product/category'

//根据parent_id获取下级节点
export const FindCategoryByParentId = (parentId) => {
    return request({
        url: `${api_name}/findCategoryList/${parentId}`,
        method: 'get',
    })
}

//导出方法
export const ExportCategoryData = () => {
    return request({
        url: `${api_name}/exportData`,
        method: 'get',
        responseType: 'blob'    //指定响应类型为‘blob’即二进制类型，用于表示大量二进制数据
    })
}