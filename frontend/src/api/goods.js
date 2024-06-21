import request from "../utils/request";


export const exportBatch = () => {
    return request.get("/goods/export", {
        responseType: "blob"
    })
}

//增加供应商接口
export const addGoodsService = (goods) => {
    return request.post("/goods", goods)
}

//删除供应商接口
export const deleteGoodsService = (id) => {
    return request.delete(`/goods/${id}`)
}

//批量删除供应商接口
export const deleteGoodsBatchService = (ids) => {
    return request.delete('/goods/batch', {data: ids})
}
//修改供应商信息接口
export const updateGoodsService = (goods) => {
    return request.put("/goods", goods)
}


//条件分页查询接口
export const goodsList = (goods, pageNum, pageSize) => {
    return request({
        url: "/goods/page",
        method: "post",
        params: {
            pageNum: pageNum,
            pageSize: pageSize
        },
        data: goods
    })
}


export const selectAllGoods = () => {
    return request.get("/goods")
}



