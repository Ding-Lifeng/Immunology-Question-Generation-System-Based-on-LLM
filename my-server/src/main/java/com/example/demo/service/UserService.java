package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public static String getMD5(String input, String salt) {
        try {
            input = input + salt; // 将密码和盐结合
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void addUser(User user) {
        user.setPassword(getMD5(user.getPassword(), "行易xy"));
        userMapper.insertUser(user);
    }

    public void changeUserInfo(long uid, User user) {
        user.setPassword(getMD5(user.getPassword(), "行易xy"));
        userMapper.updateUserInfo(uid, user);
    }

    public boolean loginUser(String name, String password) {
        User user = userMapper.findUserByUsername(name);
        if (user != null) {
            // 验证密码
            String saltedPassword = getMD5(password, "行易xy");
            return saltedPassword.equals(user.getPassword());
        }
        return false;
    }

    public long findUserUid(String username){
        User user = userMapper.findUserByUsername(username);
        return user.getId();
    }

    public User getUserInfo(long uid){
        User existedUser = userMapper.findUserByUid(uid);
        // 清空密码-防止重复加密
        existedUser.setPassword(null);
        return existedUser;
    }
}
