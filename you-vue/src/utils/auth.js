import Cookies from 'js-cookie'
import cache from '@/plugins/cache'

const TokenNameKey = 'token-name'

export function getToken() {
    return Cookies.get(getTokenName())
}

export function setToken(token) {
    return Cookies.set(getTokenName(), token)
}

export function removeToken() {
    return Cookies.remove(getTokenName())
}

export function getTokenName() {
    return cache.local.get(TokenNameKey) ?? ''
}

export function setTokenName(token) {
    return cache.local.set(TokenNameKey, token)
}

export function removeTokenName() {
    return cache.local.remove(TokenNameKey)
}