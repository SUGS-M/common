package com.myy.common.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myy.common.usercenter.entity.File;
import com.myy.common.usercenter.vo.FileSearchVo;
import com.myy.common.usercenter.vo.FileVo;
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
public interface FileMapper extends BaseMapper<File> {

    /**
     * 查询分页数据
     *
     * @param search 分页查询对象
     * @return 返回分页数据
     */
    IPage<FileVo> findByPage(FileSearchVo search);
}
