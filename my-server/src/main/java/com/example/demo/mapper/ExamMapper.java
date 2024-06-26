package com.example.demo.mapper;

import com.example.demo.entity.ExamContent;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ExamMapper {
    void insertExamContent(ExamContent examContent);

    void deleteExam(ExamContent examContent);

    void updateExam(ExamContent examContent);

    List<ExamContent> getExamInfo(Long uid);
}
