import request from "@/utils/request.ts";

export function login(data) {
    return request({
        url: '/auth/login',
        method: 'post',
        data,
        headers: {'showLoading': false}
    })
}

export const codeImgSrc = "/api/auth/verifyCode"