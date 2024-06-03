package edu.ssadmin.server.dataobject.base;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public abstract class BaseDO implements Serializable {

    /**
     * 创建时间
     */

    private LocalDateTime createTime;
    /**
     * 最后更新时间
     */

    private LocalDateTime updateTime;
    /**
     * 创建者 id 编号
     *
     */

    private String creator;

    /**
     * 更新者 id 编号
     *
     */
    private String updater;

    /**
     * 是否删除
     */
    private Boolean deleted;

}
