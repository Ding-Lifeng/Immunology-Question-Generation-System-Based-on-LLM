package edu.ssadmin.server.controller.admin.auth;

import edu.ssadmin.common.pojo.CommonResult;
import edu.ssadmin.server.controller.admin.auth.vo.LoginReqVO;
import edu.ssadmin.server.controller.admin.auth.vo.LoginRespVO;
import edu.ssadmin.server.service.auth.AdminAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static edu.ssadmin.common.pojo.CommonResult.success;

@Api(tags = "管理后台 - 认证")
@RestController
@RequestMapping("/system/auth")
public class AdminAuthController {

    @Resource
    private AdminAuthService authService;

    @PostMapping("/login")
    @ApiOperation("使用账号密码登录")
    public CommonResult<LoginRespVO> login(@RequestBody @Valid LoginReqVO reqVO) {
        return success(authService.login(reqVO));
    }

    @PostMapping("/refresh-token")
    @ApiOperation("刷新令牌")
    public CommonResult<LoginRespVO> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        return success(authService.refreshToken(refreshToken));
    }

    @PostMapping("/logout")
    @ApiOperation("退出系统")
    public CommonResult<Boolean> logout(HttpServletRequest request) {
        return success(true);
    }
}
