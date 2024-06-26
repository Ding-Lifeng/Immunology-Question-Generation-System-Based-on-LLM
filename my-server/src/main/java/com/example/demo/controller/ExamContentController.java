package com.example.demo.controller;

import com.example.demo.common.CommonResult;
import com.example.demo.entity.ExamContent;
import com.example.demo.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.util.JwtTokenUtil;

import java.util.List;

@RestController
@RequestMapping("/exam")
public class ExamContentController {
    @Autowired
    private ExamService examService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/add")
    public CommonResult<?> addTripPlan(@RequestHeader("Authorization") String token, @RequestBody ExamContent examContent){
        long uid = Long.parseLong(jwtTokenUtil.getUidFromToken(token.split(" ")[1]));
        examContent.setUid(uid);
        examService.addExamContent(examContent);
        String data = "试题保存成功!";
        return CommonResult.success(data);
    }

    @GetMapping("/getInfo")
    public CommonResult<?> getTripInfo(@RequestHeader("Authorization") String token) {
        long uid = Long.parseLong(jwtTokenUtil.getUidFromToken(token.split(" ")[1]));
        List<ExamContent> data= examService.getExamInfo(uid);
        return CommonResult.success(data);
    }

    @PostMapping("/deleteExam")
    public CommonResult<?> deleteTripPlan(@RequestHeader("Authorization") String token, @RequestBody ExamContent examContent) {
        long uid = Long.parseLong(jwtTokenUtil.getUidFromToken(token.split(" ")[1]));
        examContent.setUid(uid);
        examService.deleteExam(examContent);
        String data = "试题删除成功!";
        return CommonResult.success(data);
    }

    @PostMapping("/updateExam")
    public CommonResult<?> updateTripPlan(@RequestHeader("Authorization") String token, @RequestBody ExamContent examContent) {
        long uid = Long.parseLong(jwtTokenUtil.getUidFromToken(token.split(" ")[1]));
        examContent.setUid(uid);
        examService.updateExam(examContent);
        String data = "试题修改成功!";
        return CommonResult.success(data);
    }
}
