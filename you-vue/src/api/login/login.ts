import request from "@/utils/request.ts";

export function login(data) {
    return request({
        url: '/login',
        method: 'post',
        data
    })
}

export const codeImgSrc = "/api/auth/verifyCode"