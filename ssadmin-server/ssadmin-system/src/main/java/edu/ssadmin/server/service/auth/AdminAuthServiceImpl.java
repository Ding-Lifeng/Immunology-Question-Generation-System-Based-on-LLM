package edu.ssadmin.server.service.auth;

import edu.ssadmin.common.enums.CommonStatusEnum;
import edu.ssadmin.common.exception.GlobalErrorCodeConstants;
import edu.ssadmin.common.utils.ValidationUtils;
import edu.ssadmin.security.service.TokenService;
import edu.ssadmin.security.util.JwtTokenUtil;
import edu.ssadmin.server.controller.admin.auth.vo.LoginReqVO;
import edu.ssadmin.server.controller.admin.auth.vo.LoginRespVO;
import edu.ssadmin.server.convert.auth.AuthConvert;
import edu.ssadmin.common.pojo.AccessToken;
import edu.ssadmin.server.dataobject.user.AdminUserDO;
import edu.ssadmin.server.service.user.AdminUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.Validator;

import static edu.ssadmin.common.exception.ErrorCodeConstants.*;
import static edu.ssadmin.common.exception.util.ServiceExceptionUtil.exception;

@Service
public class AdminAuthServiceImpl implements AdminAuthService{

    @Resource
    private AdminUserService userService;

    @Resource
    private TokenService tokenService;

    @Resource
    private AuthConvert authConvert;

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    private Validator validator;

    /**
     * 验证码的开关，默认为 true
     */
    @Value("${ssadmin.captcha.enable:true}")
    private Boolean captchaEnable;

    @Override
    public LoginRespVO login(LoginReqVO reqVO) {
        // 校验验证码
        verifyCaptcha(reqVO);

        // 使用账号密码，进行登录校验
        AdminUserDO user = authenticate(reqVO.getUsername(), reqVO.getPassword());

        // 创建 Token 令牌并返回
        return createTokenAfterLoginSuccess(user.getUsername(),user.getId());
    }

    @Override
    public LoginRespVO refreshToken(String refreshToken) {
        if (!jwtTokenUtil.validateToken(refreshToken)) {
            throw exception(GlobalErrorCodeConstants.BAD_REQUEST);
        }
        // 获取用户名
        String username = jwtTokenUtil.getUsernameFromToken(refreshToken);

        AdminUserDO user = userService.getUserByUsername(username);

        if (user == null) {
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }

        // 返回token
        return createTokenAfterLoginSuccess(username,user.getId());
    }

    @Override
    public AdminUserDO authenticate(String username, String password) {

        AdminUserDO user = userService.getUserByUsername(username);

        // 校验账号是否存在
        if (user == null) {
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }

        // 校验密码是否正确
        if (!userService.isPasswordMatch(password, user.getPassword())) {
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }

        Integer status = user.getStatus();
        Integer enabledStatus = CommonStatusEnum.ENABLE.getStatus();

        if (status == null || !status.equals(enabledStatus)) {
            throw exception(AUTH_LOGIN_USER_DISABLED);
        }

        return user;
    }

    private LoginRespVO createTokenAfterLoginSuccess(String username,Long userId) {
        // 创建访问令牌
        AccessToken accessToken = tokenService.createAccessToken(username,userId);

        // 构建返回结果
        return authConvert.convert(accessToken);
    }

    void verifyCaptcha(LoginReqVO reqVO) {
        // 如果验证码关闭，则不进行校验
        if (!captchaEnable) {
            return;
        }
        // 校验验证码,使用LoginReqVO.CodeEnableGroup.class
        ValidationUtils.validate(validator, reqVO, LoginReqVO.CodeEnableGroup.class);
    }
}
