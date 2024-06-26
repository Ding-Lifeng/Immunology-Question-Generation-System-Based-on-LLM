import request from '@/utils/request';

// 发送消息到 GPT 模型
export function sendMessageToGpt(content, sessionId) {
    const data = {
        content,
        sessionId
    };

    return request({
        url: '/chatBLM/send',
        method: 'post',
        data: data
    });
}