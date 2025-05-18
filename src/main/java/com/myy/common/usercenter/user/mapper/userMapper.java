package com.myy.common.usercenter.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myy.common.usercenter.user.entity.user;
import com.myy.common.usercenter.user.vo.userSearchVo;
import com.myy.common.usercenter.user.vo.userVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 用户表Mapper接口
 *
 * @author myy
 * @date 2025-05-18
 */
@Mapper
@Repository
public interface userMapper extends BaseMapper<user> {

    /**
     * 查询分页数据
     *
     * @param search 分页查询对象
     * @return 返回分页数据
     */
    IPage<userVo> findByPage(userSearchVo search);
}
