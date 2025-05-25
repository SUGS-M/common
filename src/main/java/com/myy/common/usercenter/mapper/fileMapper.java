package com.myy.common.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myy.common.usercenter.entity.file;
import com.myy.common.usercenter.vo.fileSearchVo;
import com.myy.common.usercenter.vo.fileVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 附件表Mapper接口
 *
 * @author myy
 * @date 2025-05-25
 */
@Mapper
@Repository
public interface fileMapper extends BaseMapper<file> {

    /**
     * 查询分页数据
     *
     * @param search 分页查询对象
     * @return 返回分页数据
     */
    IPage<fileVo> findByPage(fileSearchVo search);
}
