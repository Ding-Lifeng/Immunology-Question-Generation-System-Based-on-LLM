import request from '@/utils/request'

export function changeUserInfo(name, password, real_name, gender, phoneNum, position, school) {
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
        url: '/user/change',
        method: 'post',
        data: data
    })
}

export function getUserInfo() {

    return request({
        url: '/user/getInfo',
        method: 'get',
    })
}