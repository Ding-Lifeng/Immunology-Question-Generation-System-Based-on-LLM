package com.example.demo.entity;

import lombok.Data;

@Data
public class ExamContent {
    private Long exam_id;
    private Long uid;
    private String time;
    private String keyPoints;
    private String content;
}
