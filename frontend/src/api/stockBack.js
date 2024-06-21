import request from "../utils/request";



//商品退货
export const addStockBackService = (stockBack) => {
    return request.post("/stockBacks", stockBack)
}



//条件分页查询接口
export const stockBackList = (stockBack, pageNum, pageSize) => {
    return request({
        url: "/stockBacks/page",
        method: "post",
        params: {
            pageNum: pageNum,
            pageSize: pageSize
        },
        data: stockBack
    })
}


export const selectAllStockBack = ()=>{
    return request.get("/stockBacks")
}






