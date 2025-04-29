package com.myy.common.group.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.myy.common.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * 小组表实体 对应表名sys_group
 *
 * @author myy
 * @date 2025-04-22
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_group")
public class Group extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 小组ID */
    @TableId
    private long id;
    /** 小组名称 */
    @TableField("name")
    private String name;
    /** 小组描述 */
    @TableField("description")
    private String description;
    /** 小组logo URL */
    @TableField("logo")
    private String logo;
    /** 创建者/组长用户ID */
    @TableField("owner_id")
    private long ownerId;
    /** 成员数量 */
    @TableField("member_count")
    private int memberCount;
    /** 最大成员数(null表示不限制) */
    @TableField("max_members")
    private int maxMembers;
    /** 隐私类型(1-公开 2-私密 3-申请加入) */
    @TableField("privacy_type")
    private int privacyType;
    /** 小组标签(JSON格式) */
    @TableField("tags")
    private String tags;
    /** 状态(0-禁用 1-正常) */
    @TableField("status")
    private int status;
}
