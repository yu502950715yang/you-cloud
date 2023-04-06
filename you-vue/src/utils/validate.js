/**
 * Created by PanJiaChen on 16/11/18.
 */

/**
 * @param {string} path
 * @returns {Boolean}
 */
export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path)
}

/**
 * 验证登录名称
 * @param username
 * @returns {boolean}
 */
export function isUsername(username) {
  const reg = /^[A-Za-z0-9]{1,20}$/
  return reg.test(username)
}
