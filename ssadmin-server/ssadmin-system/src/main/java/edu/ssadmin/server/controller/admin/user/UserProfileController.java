package edu.ssadmin.server.controller.admin.user;

import edu.ssadmin.common.pojo.CommonResult;
import edu.ssadmin.server.controller.admin.user.vo.profile.UserProfileRespVO;
import edu.ssadmin.server.utils.AdminLoginInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static edu.ssadmin.common.pojo.CommonResult.success;

@Api(tags = "管理后台 - 用户个人中心")
@RestController
@RequestMapping("/system/user/profile")
public class UserProfileController {

    @Resource
    AdminLoginInfo adminLoginInfo;

    @GetMapping("/get")
    @ApiOperation("获得登录用户信息")
    public CommonResult<UserProfileRespVO> profile() {
        String username = adminLoginInfo.getLoginUsername();
        UserProfileRespVO resp = new UserProfileRespVO();
        resp.setNickname(username);
        return success(resp);
    }
}
