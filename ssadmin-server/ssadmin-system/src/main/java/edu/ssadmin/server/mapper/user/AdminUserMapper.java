package edu.ssadmin.server.mapper.user;

import edu.ssadmin.server.dataobject.user.AdminUserDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminUserMapper {
    AdminUserDO selectByUsername(String username);
}
