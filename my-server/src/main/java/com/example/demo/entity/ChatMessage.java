package com.example.demo.entity;

import lombok.Data;

import java.util.Map;

@Data
public class ChatMessage {
    private String content;
    private String sessionId;  // 添加sessionId字段
    private Map<String, String> queryParams;  // 确保在消息中包含查询参数
}
