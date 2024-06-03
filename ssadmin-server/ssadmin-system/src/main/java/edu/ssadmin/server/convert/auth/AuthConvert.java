package edu.ssadmin.server.convert.auth;

import edu.ssadmin.server.controller.admin.auth.vo.LoginRespVO;
import edu.ssadmin.common.pojo.AccessToken;
import org.springframework.stereotype.Component;

@Component
public class AuthConvert {
    public LoginRespVO convert(AccessToken bean) {
        if ( bean == null ) {
            return null;
        }

        // 使用AccessToken bean的属性值创建LoginRespVO对象
        LoginRespVO loginRespVO = new LoginRespVO();
        loginRespVO.setUserId(bean.getUserId());
        loginRespVO.setAccessToken(bean.getAccessToken());
        loginRespVO.setRefreshToken(bean.getRefreshToken());
        loginRespVO.setExpiresTime(bean.getExpiresTime());

        return loginRespVO;
    }
}
