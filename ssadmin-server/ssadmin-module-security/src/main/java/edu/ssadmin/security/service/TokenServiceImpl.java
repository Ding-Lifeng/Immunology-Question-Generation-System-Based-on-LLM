package edu.ssadmin.security.service;

import edu.ssadmin.common.pojo.AccessToken;
import edu.ssadmin.security.util.JwtTokenUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class TokenServiceImpl implements TokenService {

    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Override
    public AccessToken createAccessToken(String username,Long userId) {
        // 生成访问令牌和刷新令牌
        String accessToken = jwtTokenUtil.generateAccessToken(username);
        String refreshToken = jwtTokenUtil.generateRefreshToken(username);

        // 创建 AccessToken 对象并设置属性
        AccessToken token = new AccessToken();
        token.setUserId(userId);
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);

        // 设置过期时间，可以根据你的 JWT 配置来调整
        LocalDateTime expiresTime = LocalDateTime.ofInstant(
                jwtTokenUtil.getExpirationDateFromToken(accessToken).toInstant(),
                ZoneId.systemDefault());
        token.setExpiresTime(expiresTime);

        return token;
    }
}
