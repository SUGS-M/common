package com.myy.common.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myy.common.usercenter.entity.User;
import com.myy.common.usercenter.vo.UserSearchVo;
import com.myy.common.usercenter.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 用户表Mapper接口
 *
 * @author myy
 * @date 2025-05-25
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询分页数据
     *
     * @param search 分页查询对象
     * @return 返回分页数据
     */
    IPage<UserVo> findByPage(UserSearchVo search);

    User findByUsername(String username);
}
