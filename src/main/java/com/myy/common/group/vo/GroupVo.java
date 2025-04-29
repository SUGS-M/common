package com.myy.common.group.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
/**
 * 小组表  VO
 *
 * @author myy
 * @date 2025-04-22
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class GroupVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 小组ID */
    private long id;
    /** 小组名称 */
    @NotBlank
    private String name;
    /** 小组描述 */
    private String description;
    /** 小组logo URL */
    private String logo;
    /** 创建者/组长用户ID */
    @NotNull
    private long ownerId;
    /** 成员数量 */
    @NotNull
    private int memberCount;
    /** 最大成员数(null表示不限制) */
    private int maxMembers;
    /** 隐私类型(1-公开 2-私密 3-申请加入) */
    @NotNull
    private int privacyType;
    /** 小组标签(JSON格式) */
    private String tags;
    /** 状态(0-禁用 1-正常) */
    @NotNull
    private int status;
}
