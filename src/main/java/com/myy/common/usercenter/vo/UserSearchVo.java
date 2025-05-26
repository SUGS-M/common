package com.myy.common.usercenter.vo;

import com.myy.common.common.base.PageSearch;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * 用户表  搜索VO
 *
 * @author myy
 * @date 2025-05-25
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class UserSearchVo extends PageSearch<UserVo> {

}
