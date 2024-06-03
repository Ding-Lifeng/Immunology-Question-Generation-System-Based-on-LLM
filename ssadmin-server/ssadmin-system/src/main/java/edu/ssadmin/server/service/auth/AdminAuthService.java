package edu.ssadmin.server.service.auth;

import edu.ssadmin.server.controller.admin.auth.vo.LoginReqVO;
import edu.ssadmin.server.controller.admin.auth.vo.LoginRespVO;
import edu.ssadmin.server.dataobject.user.AdminUserDO;

public interface AdminAuthService {

    LoginRespVO login(LoginReqVO reqVO);

    LoginRespVO refreshToken(String refreshToken);

    AdminUserDO authenticate(String username, String password);
}
