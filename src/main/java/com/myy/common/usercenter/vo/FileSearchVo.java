package com.myy.common.usercenter.vo;

import com.myy.common.common.base.PageSearch;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * 附件表  搜索VO
 *
 * @author myy
 * @date 2025-05-25
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class FileSearchVo extends PageSearch<FileVo> {

}
