package com.example.demo.entity;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String name;
    private String password;
    private String real_name;  // 实名制准备
    private String gender;
    private Integer phoneNum;
    private String position;
    private String school;
}

