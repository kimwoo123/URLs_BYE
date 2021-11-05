import { loginToken } from 'src/api/auth'
import {
	saveAuthToCookie,
  saveUserToCookie,
  saveUseridToCookie,
  deleteCookie
} from 'src/utils/cookies'

export async function LOGIN({ commit }, userData) {
  const loginData = {
    'email': userData.useremail,
    'nickname': userData.nickname,
    'avatar': userData.avatar,
  }
  console.log(userData.token);
  commit('setToken', userData.token);
  commit('setUserName', userData.nickname)
  saveAuthToCookie(userData.token)
  saveUserToCookie(userData.nickname)
  const { data } = await loginToken(loginData);
  commit('setUserid', data['_id'])
  saveUseridToCookie(data['_id'])
}

export async function LOGOUT({ commit }) {
  commit('clearUsername')
  commit('clearToken')
  commit('clearUserid')
  deleteCookie('til-auth')
  deleteCookie('til-user')
}