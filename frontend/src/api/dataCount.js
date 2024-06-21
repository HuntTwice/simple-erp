import request from "../utils/request";

//获取销售订单数
export const getSaleOrdersCount = () => {

}


//获取采购订单数
export const getStockOrdersCount = () => {
}


//获取库存总数
export const getAllGoodsNum = () => {

}

//获取销售总额
export const getTotalSalePrice = () => {

}


export const getData = () => {
    return request.get("/count")
}


//获取近一周的销售额度
export const getSaleNearlyWeek = () => {
    return request.get("/count/weekSale")
}


//获取库存占比
export const getStoreCount = () => {
    return request.get("count/store")
}
