package com.example.demo.service;

import com.example.demo.entity.ExamContent;
import com.example.demo.mapper.ExamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExamService {
    @Autowired
    private ExamMapper examMapper;

    @Transactional
    public void addExamContent(ExamContent examContent) {
        examMapper.insertExamContent(examContent);
    }

    public void deleteExam(ExamContent examContent) {
        examMapper.deleteExam(examContent);
    }

    public void updateExam(ExamContent examContent) {
        examMapper.updateExam(examContent);
    }

    public List<ExamContent> getExamInfo(Long uid) {
        return examMapper.getExamInfo(uid);
    }
}
