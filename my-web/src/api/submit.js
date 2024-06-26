import request from '@/utils/request'

// 用户注册
export function submit(name, password, real_name, gender, phoneNum, position, school) {
    const data = {
        name,
        password,
        real_name,
        gender,
        phoneNum,
        position,
        school
    }
    return request({
        url: '/user/add',
        method: 'post',
        data: data
    })
}