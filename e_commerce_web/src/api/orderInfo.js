import request from "@/utils/request";

const api_name = '/admin/order/orderInfo'

export const GetOrderStatisticsData = searchObj => {
    return request({
        url: `${api_name}/getOrderStatisticsData`,
        method: 'get',
        params: searchObj,
    })
}