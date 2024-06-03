package edu.ssadmin.server.service.user;

import edu.ssadmin.security.util.PasswordUtil;
import edu.ssadmin.server.dataobject.user.AdminUserDO;
import edu.ssadmin.server.mapper.user.AdminUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Resource
    private AdminUserMapper userMapper;

    @Override
    public AdminUserDO getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public boolean isPasswordMatch(String rawPassword, String encodedPassword) {
        return PasswordUtil.verify(rawPassword, encodedPassword);
    }
}
