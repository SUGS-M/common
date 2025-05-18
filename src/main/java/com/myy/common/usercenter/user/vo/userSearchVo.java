package com.myy.common.usercenter.user.vo;

import com.myy.common.common.base.PageSearch;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * 用户表  搜索VO
 *
 * @author myy
 * @date 2025-05-18
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class userSearchVo extends PageSearch<userVo> {

}
