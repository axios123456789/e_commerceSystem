import request from "@/utils/request";

const api_name = '/admin/product/productUnit'

//查询所有
export const FindAllProductUnit = () => {
    return request({
        url: `${api_name}/findAll`,
        method: 'get',
    })
}