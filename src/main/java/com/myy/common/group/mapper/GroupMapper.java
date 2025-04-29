package com.myy.common.group.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myy.common.group.entity.Group;
import com.myy.common.group.vo.GroupSearchVo;
import com.myy.common.group.vo.GroupVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 小组表Mapper接口
 *
 * @author myy
 * @date 2025-04-22
 */
@Mapper
@Repository
public interface GroupMapper extends BaseMapper<Group> {

    /**
     * 查询分页数据
     *
     * @param search 分页查询对象
     * @return 返回分页数据
     */
    IPage<GroupVo> findByPage(GroupSearchVo search);
}
