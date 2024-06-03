package edu.ssadmin.server.dataobject.user;

import edu.ssadmin.server.dataobject.base.TenantBaseDO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserDO extends TenantBaseDO {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 加密后的密码
     *
     */
    private String password;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 最后登录IP
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    private LocalDateTime loginDate;

}
