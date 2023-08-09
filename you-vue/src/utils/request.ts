import axios from "axios";
import {ElMessageBox, ElMessage} from "element-plus";
import {useTokenStore} from "@/store/token.ts";
import {showFullScreenLoading, tryHideFullScreenLoading} from "@/utils/loading.ts";

const store = useTokenStore()

const service = axios.create({
    // 使每次请求都会带一个 /api 前缀
    baseURL: "/api",
    timeout: 5000
})

service.interceptors.request.use(
    config => {
        if (store.getToken != "") {
            config.headers["token"] = store.getToken
        }
        if (config.headers["showLoading"] != false) {
            showFullScreenLoading()
        }
        return config
    },
    error => {
        return Promise.reject(error)
    }
)

service.interceptors.response.use(
    response => {
        const res = response.data
        if (response.headers["token"]) {
            store.setToken(response.headers["token"])
        }
        if (response.config.headers["showLoading"] != false) {
            tryHideFullScreenLoading()
        }
        if (res.code !== 200) {
            if (res.code === 50008 || res.code === 50012 || res.code === 50014) {
                // 重新登录
                ElMessageBox.confirm("登录超时，请重新登录", "提示", {
                    confirmButtonText: "重新登录",
                    type: "warning"
                }).then(() => {
                    store.cleanToken()
                    location.reload()
                })
            } else {
                ElMessage({
                    message: res.msg || "Error",
                    type: "error",
                    duration: 5 * 1000
                })
            }
            return Promise.reject(new Error(res.msg || "error"))
        } else {
            return res
        }
    },
    error => {
        tryHideFullScreenLoading()
        ElMessage({
            message: error.message,
            type: "error",
            duration: 5 * 1000
        })
        return Promise.reject(error)
    }
)

export default service