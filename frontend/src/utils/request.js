import axios from 'axios'
import router from "@/router";
import {Message} from 'element-ui';
// 创建可一个新的axios对象
const request = axios.create({
    baseURL: process.env.VUE_APP_BASEURL,   // 后端的接口地址  ip:port
    timeout: 30000                          // 30s请求超时
})

// request 拦截器
// 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';        // 设置请求头格式
    let user = JSON.parse(localStorage.getItem("xm-user") || '{}')  // 获取缓存的用户信息
    if (user != null)
        config.headers['Authorization'] = user.token  // 把token放入请求头的Authorization字段

    return config
}, error => {

    console.error('request error: ' + error) // for debug
    return Promise.reject(error)
});

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
    //当响应码时2xx的时候触发
    response => {
        if (response.data instanceof Blob)
            return response;
        let res = response.data;
        if (res.code == 200)
            return res
        else {
            Message.error(res.msg ? res.msg : '服务异常')
            return Promise.reject(res)
        }
    },
    //当响应码不是2xx的时候触发
    error => {
        if (error.response.status == 401) {
            Message('请先登录')
            router.push('/login')
        } else {
            Message.error('服务异常')
            console.log(error)
            return Promise.reject(error)
        }
    }
)


export default request
