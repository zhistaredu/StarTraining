import Cookies from 'js-cookie'

const TokenKey = 'AuthorizationToken'

export function getToken() {
  // return Cookies.get(TokenKey)
  return window.localStorage.getItem(TokenKey)
}

export function setToken(token) {
  return window.localStorage.setItem(TokenKey, token)
}

export function removeToken() {
  return window.localStorage.removeItem(TokenKey)
}
