package edu.ssadmin.server.dataobject.base;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class TenantBaseDO extends BaseDO {

    /**
     * 多租户编号
     */
    private Long tenantId;

}