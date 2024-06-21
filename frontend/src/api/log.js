import request from "../utils/request";




export const exportBatch = ()=>{
    return request.get("/logs/export",{
        responseType: "blob"
    })
}



//删除供应商接口
export const deleteLogService = (id) => {
    return request.delete(`/logs/${id}`)
}

//批量删除供应商接口
export const deleteLogBatchService = (ids) => {
    return request.delete('/logs/batch',{data:ids})
}

//条件分页查询接口
export const logList = (log, pageNum, pageSize) => {
    return request({
        url: "/logs/page",
        method: "get",
        params: {
            pageNum: pageNum,
            pageSize: pageSize
        },
        data: log
    })
}


export const selectAllLog = ()=>{
    return request.get("/logs")
}






