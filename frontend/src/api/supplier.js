import request from "../utils/request";




export const exportBatch = ()=>{
    return request.get("/suppliers/export",{
        responseType: "blob"
    })
}

//增加供应商接口
export const addSupplierService = (supplier) => {
    return request.post("/suppliers", supplier)
}

//删除供应商接口
export const deleteSupplierService = (id) => {
    return request.delete(`/suppliers/${id}`)
}

//批量删除供应商接口
export const deleteSupplierBatchService = (ids) => {
    return request.delete('/suppliers/batch',{data:ids})
}
//修改供应商信息接口
export const updateSupplierService = (supplier) => {
    return request.put("/suppliers", supplier)
}


//条件分页查询接口
export const supplierList = (supplier, pageNum, pageSize) => {
    return request({
        url: "/suppliers/page",
        method: "post",
        params: {
            pageNum: pageNum,
            pageSize: pageSize
        },
        data: supplier
    })
}


//查询所有供应商
export const selectAllSupplier = ()=>{
    return request.get("/suppliers")
}


