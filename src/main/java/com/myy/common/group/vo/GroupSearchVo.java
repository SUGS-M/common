package com.myy.common.group.vo;

import com.myy.common.base.PageSearch;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * 小组表  搜索VO
 *
 * @author myy
 * @date 2025-04-22
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class GroupSearchVo extends PageSearch<GroupVo> {

}
