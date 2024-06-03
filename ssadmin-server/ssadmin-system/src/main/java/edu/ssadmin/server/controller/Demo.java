package edu.ssadmin.server.controller;

import edu.ssadmin.common.pojo.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Demo {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @GetMapping("/demo")
    public CommonResult<?> getHelloMsg() {
        System.out.println("This is a system.out message.");
        logger.info("This is an info log message.");
        return CommonResult.success(" 添加了Log输出");
    }
}
