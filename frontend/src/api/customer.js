import request from "../utils/request";

export const selectAllCustomer=()=>{
    return request.get("/customers")
}


export const exportBatch = ()=>{
    return request.get("/customers/export",{
        responseType: "blob"
    })
}

//增加供应商接口
export const addCustomerService = (customer) => {
    return request.post("/customers", customer)
}

//删除供应商接口
export const deleteCustomerService = (id) => {
    return request.delete(`/customers/${id}`)
}

//批量删除供应商接口
export const deleteCustomerBatchService = (ids) => {
    return request.delete('/customers/batch',{data:ids})
}
//修改供应商信息接口
export const updateCustomerService = (customer) => {
    return request.put("/customers", customer)
}


//条件分页查询接口
export const customerList = (customer, pageNum, pageSize) => {
    return request({
        url: "/customers/page",
        method: "post",
        params: {
            pageNum: pageNum,
            pageSize: pageSize
        },
        data: customer
    })
}



