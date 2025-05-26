package com.myy.common.usercenter.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 附件表  VO
 *
 * @author myy
 * @date 2025-05-25
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FileVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**  */
    private String id;
    /**  */
    @NotBlank
    private String filename;
    /**  */
    @NotBlank
    private String filepath;
    /**  */
    @NotNull
    private long filesize;
    /**  */
    @NotBlank
    private String filetype;
    /**  */
    private int relatedrecordid;
    /**  */
    private String description;
}
