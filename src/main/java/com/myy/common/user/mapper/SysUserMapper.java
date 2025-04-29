package com.myy.common.user.mapper;

import com.myy.common.user.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author SHData
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2025-04-22 16:09:09
* @Entity com.myy.common.user.entity.SysUser
*/
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}




