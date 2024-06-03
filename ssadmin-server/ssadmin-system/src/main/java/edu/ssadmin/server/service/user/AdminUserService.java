package edu.ssadmin.server.service.user;

import edu.ssadmin.server.dataobject.user.AdminUserDO;

public interface AdminUserService {

    AdminUserDO getUserByUsername(String username);

    boolean isPasswordMatch(String rawPassword, String encodedPassword);

}
