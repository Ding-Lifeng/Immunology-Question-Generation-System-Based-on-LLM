package edu.ssadmin.server.controller.admin.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@ApiModel(value = "管理后台 - 账号密码登录 Request VO", description = "登录需要传递的参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginReqVO {
    @ApiModelProperty(value = "账号", required = true, example = "user")
    @NotEmpty(message = "登录账号不能为空")
    @Length(min = 4, max = 16, message = "账号长度为 4-16 位")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "账号格式为数字以及字母")
    private String username;

    @ApiModelProperty(value = "密码", required = true, example = "1234abc")
    @NotEmpty(message = "密码不能为空")
    @Length(min = 4, max = 16, message = "密码长度为 4-16 位")
    private String password;


    @ApiModelProperty(value = "验证码", required = true,
            example = "abcd",
            notes = "验证码开启时，需要传递")
    @NotEmpty(message = "验证码不能为空", groups = CodeEnableGroup.class)
    private String captcha;

    /**
     * 开启验证码的 Group
     */
    public interface CodeEnableGroup {}
}
