import request from "../utils/request";




export const exportBatch = ()=>{
    return request.get("/stocks/export",{
        responseType: "blob"
    })
}

//增加供应商接口
export const addStockService = (stock) => {
    return request.post("/stocks", stock)
}

//删除供应商接口
export const deleteStockService = (id) => {
    return request.delete(`/stocks/${id}`)
}

//批量删除供应商接口
export const deleteStockBatchService = (ids) => {
    return request.delete('/stocks/batch',{data:ids})
}
//修改供应商信息接口
export const updateStockService = (stock) => {
    return request.put("/stocks", stock)
}


//条件分页查询接口
export const stockList = (stock, pageNum, pageSize) => {
    return request({
        url: "/stocks/page",
        method: "post",
        params: {
            pageNum: pageNum,
            pageSize: pageSize
        },
        data: stock
    })
}


export const selectAllStock = ()=>{
    return request.get("/stocks")
}






