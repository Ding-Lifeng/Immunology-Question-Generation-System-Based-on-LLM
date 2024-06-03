package edu.ssadmin.common.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccessToken {

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 访问令牌
     */
    private String accessToken;

    /**
     * 刷新令牌
     */
    private String refreshToken;

    /**
     * 过期时间
     */
    private LocalDateTime expiresTime;
}
