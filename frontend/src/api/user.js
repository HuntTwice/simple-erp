import request from "../utils/request";

//用户登录接口
export const login = (user) => {
    return request.post("/users/login", user)
}

//查询当前用户信息接口
export const userInfoService=(username)=>{
    return request.get(`/users/${username}`)
}

//增加用户接口
export const addUserService = (user) => {
    return request.post("/users", user)
}

//删除用户接口
export const deleteUserService = (id) => {
    return request.delete(`/users/${id}`)
}

//批量删除用户接口
export const deleteUserBatchService = (ids) => {
    return request.delete('/users/batch',{data:ids})
}
//修改用户信息接口
export const updateUserService = (user) => {
    return request.put("/users", user)
}


//条件分页查询接口
export const userList = (user, pageNum, pageSize) => {
    return request({
        url: "/users/page",
        method: "post",
        params: {
            pageNum: pageNum,
            pageSize: pageSize
        },
        data: user
    })
}



