import request from "../utils/request";




export const exportBatch = ()=>{
    return request.get("/sales/export",{
        responseType: "blob"
    })
}

//增加供应商接口
export const addSaleService = (sale) => {
    return request.post("/sales", sale)
}

//删除供应商接口
export const deleteSaleService = (id) => {
    return request.delete(`/sales/${id}`)
}

//批量删除供应商接口
export const deleteSaleBatchService = (ids) => {
    return request.delete('/sales/batch',{data:ids})
}
//修改供应商信息接口
export const updateSaleService = (sale) => {
    return request.put("/sales", sale)
}


//条件分页查询接口
export const saleList = (sale, pageNum, pageSize) => {
    return request({
        url: "/sales/page",
        method: "post",
        params: {
            pageNum: pageNum,
            pageSize: pageSize
        },
        data: sale
    })
}


export const selectAllSale = ()=>{
    return request.get("/sales")
}






