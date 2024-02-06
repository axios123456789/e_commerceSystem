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