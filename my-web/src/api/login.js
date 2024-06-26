import request from '@/utils/request'

// 用户登录
export function login(name, password) {
  const data = {
    name,
    password
  }
  return request({
    url: '/user/login',
    method: 'post',
    data: data
  })
}