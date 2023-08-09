import {ref, computed} from "vue";
import {defineStore} from "pinia";

export const useTokenStore = defineStore("token", () => {
    let tokenKey = "user-token"

    // 登录token
    const token = ref("")

    const getToken = computed(() => {
        return sessionStorage.getItem(tokenKey) || token
    })

    function setToken(token) {
        token.value = token
        sessionStorage.setItem(tokenKey, token)
    }

    function cleanToken() {
        token.value = ''
        sessionStorage.removeItem(tokenKey)
    }

    return {getToken, setToken, cleanToken}
})