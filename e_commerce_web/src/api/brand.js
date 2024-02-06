import request from "@/utils/request";

const api_name = '/admin/product/brand'

//查询所以品牌
export const FindAllBrand = () => {
    return request({
        url: `${api_name}/findAll`,
        method: 'get',
    })
}

//列表
export const BrandList = (page, limit) => {
    return request({
        url: `${api_name}/${page}/${limit}`,
        method: 'get',
    })
}

//添加
export const AddBrand = (brand) => {
    return request({
        url: `${api_name}/save`,
        method: 'post',
        data: brand,
    })
}

//修改
export const UpdateBrand = (brand) => {
    return request({
        url: `${api_name}/update`,
        method: 'put',
        data: brand,
    })
}

//删除
export const DeletedBrandById = (id) => {
    return request({
        url: `${api_name}/deleted/${id}`,
        method: 'delete',
    })
}