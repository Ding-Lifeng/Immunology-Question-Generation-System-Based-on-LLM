package edu.ssadmin.server.controller.admin.user.vo.profile;


import edu.ssadmin.server.controller.admin.user.vo.user.UserBaseVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileRespVO extends UserBaseVO {
    private Long id;
    private String loginIp;
    private LocalDateTime loginDate;
}
