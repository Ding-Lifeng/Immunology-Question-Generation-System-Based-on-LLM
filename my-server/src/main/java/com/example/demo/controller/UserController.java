package com.example.demo.controller;

import com.example.demo.common.CommonResult;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtTokenUtil;
import com.example.demo.util.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/add")
    public CommonResult<?> addUser(@RequestBody User user) {
        userService.addUser(user);
        String data = "用户添加成功!";
        return CommonResult.success(data);
    }

    @PostMapping("/change")
    public CommonResult<?> changeUserInfo(@RequestHeader("Authorization") String token, @RequestBody User user) {
        long uid = Long.parseLong(jwtTokenUtil.getUidFromToken(token.split(" ")[1]));
        userService.changeUserInfo(uid, user);
        String data = "用户修改成功!";
        return CommonResult.success(data);
    }

    @GetMapping("/getInfo")
    public CommonResult<?> getUserInfo(@RequestHeader("Authorization") String token) {
        long uid = Long.parseLong(jwtTokenUtil.getUidFromToken(token.split(" ")[1]));
        User data= userService.getUserInfo(uid);
        return CommonResult.success(data);
    }

    @PostMapping("/login")
    public CommonResult<?> login(@RequestBody User loginUser) {
        boolean success = userService.loginUser(loginUser.getName(), loginUser.getPassword());
        long uid;

        if (success) {
            uid = userService.findUserUid(loginUser.getName());
            // 生成访问令牌和刷新令牌
            String accessToken = jwtTokenUtil.generateAccessToken(Long.toString(uid));
            String refreshToken = jwtTokenUtil.generateRefreshToken(Long.toString(uid));
            TokenResponse token_resp = new TokenResponse(accessToken,refreshToken);

            return CommonResult.success(token_resp);
        } else {
            return CommonResult.error(401, "用户名或密码错误!");
        }
    }
}
