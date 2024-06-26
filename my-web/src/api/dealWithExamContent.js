import request from '@/utils/request';

// 获取当前时间，精确到分钟，并转换为中国时间
function getChinaTime() {
    const now = new Date();
    now.setHours(now.getHours());
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0');
    const day = String(now.getDate()).padStart(2, '0');
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');

    return `${year}-${month}-${day}T${hours}:${minutes}`;
}

// 提交旅行规划内容
export function saveExamContent(planContent, keyPoints) {
    const data = {
        time: getChinaTime(), // 获取当前时间，精确到分钟
        keyPoints: keyPoints,
        content: planContent
    };
    return request({
        url: '/exam/add',
        method: 'post',
        data: data
    });
}

export function getExams() {

    return request({
        url: '/exam/getInfo',
        method: 'get',
    })
}

export function deleteExam(examId) {
    const data = {
        exam_id: examId
    };
    return request({
        url: `/exam/deleteExam`,
        method: 'post',
        data: data
    });
}

export function updateExam(exam) {
    const data = {
        exam_id: exam.exam_id,
        time: exam.time,
        keyPoints: exam.keyPoints,
        content: exam.content
    };
    return request({
        url: '/exam/updateExam',
        method: 'post',
        data: data
    });
}