import request from "../utils/request";






//增加供应商接口
export const addDepartmentService = (department) => {
    return request.post("/departments", department)
}

//删除供应商接口
export const deleteDepartmentService = (id) => {
    return request.delete(`/departments/${id}`)
}

//批量删除供应商接口
export const deleteDepartmentBatchService = (ids) => {
    return request.delete('/departments/batch',{data:ids})
}
//修改供应商信息接口
export const updateDepartmentService = (department) => {
    return request.put("/departments", department)
}


//条件分页查询接口
export const departmentList = (pageNum, pageSize) => {
    return request({
        url: "/departments/page",
        method: "post",
        params: {
            pageNum: pageNum,
            pageSize: pageSize
        }
    })
}



//查询所有供应商
export const selectAllDepartment = ()=>{
    return request.get("/departments")
}

