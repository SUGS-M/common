package com.myy.common.common.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myy.common.usercenter.utils.UserUtil;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 基础实体类，包含公共字段
 */
@Data

public class BaseEntity implements Serializable {

    public static final Set<String> IGNORES = Set.of("delFlag", "createBy", "createTime", "updateBy", "updateTime");
    @Serial
    private static final long serialVersionUID = 1L;
//    /**
//     * 创建机构id
//     */
//    @TableField(value = "CREATE_JGID", fill = FieldFill.INSERT)
//    private String createJgid;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

//    /**
//     * 更新机构id
//     */
//    @TableField(value = "UPDATE_JGID", fill = FieldFill.INSERT)
//    private String updateJgid;

    /**
     * 更新人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 删除标记（1-已删除，0-未删除）
     */
    @JsonIgnore
    @TableField(value = "DEL_FLAG")
    @TableLogic(value = "0", delval = "1")
    private String delFlag;

//    /**
//     * 备注
//     */
//    @TableField(value = "REMARKS")
//    private String remarks;


    /**
     * 判断是否基础通用属性
     */
    public static boolean contain(String name) {
        return IGNORES.contains(name);
    }


    public void initByUpdate() {
        this.updateTime = LocalDateTime.now();
        this.updateBy = UserUtil.getUserId();
    }
    public void init() {
        this.delFlag = "0";
        this.createTime = LocalDateTime.now();
        this.updateTime = this.createTime;
        this.createBy = UserUtil.getUserId();
        this.updateBy = UserUtil.getUserId();
    }

    public void initByUpdate(String userId) {
        this.updateBy = userId;
        this.updateTime = LocalDateTime.now();
    }
    public void init(String userId) {
        this.delFlag = "0";
        this.createTime = LocalDateTime.now();
        this.updateTime = this.createTime;
        this.createBy = userId;
        this.updateBy = userId;
    }
}
