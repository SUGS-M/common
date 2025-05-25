package com.myy.common.usercenter.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.myy.common.common.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.util.Date;

/**
 * 附件表实体 对应表名sys_file
 *
 * @author myy
 * @date 2025-05-25
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_file")
public class file extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**  */
    @TableId
    private String id;
    /**  */
    @TableField("FileName")
    private String filename;
    /**  */
    @TableField("FilePath")
    private String filepath;
    /**  */
    @TableField("FileSize")
    private long filesize;
    /**  */
    @TableField("FileType")
    private String filetype;
    /**  */
    @TableField("RelatedRecordID")
    private int relatedrecordid;
    /**  */
    @TableField("Description")
    private String description;
}
