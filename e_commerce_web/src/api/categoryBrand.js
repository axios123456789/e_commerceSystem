import request from "@/utils/request";

const api_name = '/admin/product/categoryBrand'

//条件分页查询分类品牌
export const GetCategoryBrandPageList = (page, limit, searchObj) => {
    return request({
        url: `${api_name}/${page}/${limit}`,
        method: 'get',
        params: searchObj,
    })
}

//添加
export const SaveCategoryBrand = (categoryBrand) => {
    return request({
        url: `${api_name}/save`,
        method: 'post',
        data: categoryBrand,
    })
}

//修改
export const EditCategoryBrand = (categoryBrand) => {
    return request({
        url: `${api_name}/update`,
        method: 'put',
        data: categoryBrand,
    })
}

//删除
export const DeleteCategoryBrandById = (id) => {
    return request({
        url: `${api_name}/deleteById/${id}`,
        method: 'delete',
    })
}