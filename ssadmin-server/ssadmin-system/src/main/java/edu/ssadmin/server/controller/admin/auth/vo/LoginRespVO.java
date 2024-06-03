package edu.ssadmin.server.controller.admin.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@ApiModel("管理后台 - 登录 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRespVO {

    @ApiModelProperty(value = "用户编号", required = true, example = "1011")
    private Long userId;

    @ApiModelProperty(value = "访问令牌", required = true, example = "wqqw##w")
    private String accessToken;

    @ApiModelProperty(value = "刷新令牌", required = true, example = "wqqw##w12")
    private String refreshToken;

    @ApiModelProperty(value = "过期时间", required = true)
    private LocalDateTime expiresTime;
}
