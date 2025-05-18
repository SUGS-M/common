package com.myy.common.usercenter.user.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.myy.common.usercenter.user.entity.user;
import com.myy.common.usercenter.user.mapper.userMapper;
import com.myy.common.usercenter.user.vo.userSearchVo;
import com.myy.common.usercenter.user.vo.userVo;
import com.myy.common.common.base.PageData;
import com.myy.common.common.base.BaseService;
import com.myy.common.common.exception.ParameterException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 用户表  Service服务
 *
 * @author myy
 * @date 2025-05-18
 */
@Service
@Transactional(readOnly = true)
public class userService extends BaseService<userMapper,user> {

    /**
     * 保存或更新
     *
     * @param vo 提交参数
     * @return
     */
    @Transactional
    public String saveOrUpdate(userVo vo) {
        validate(vo);
        user po = null;
        if (StrUtil.isNotBlank(vo.getId())) {
            po = getById(vo.getId());
            if (po == null) {
                throw new ParameterException("无效的ID");
            }
        }
        if (po == null) { //新增
            po = BeanUtil.toBean(vo, user.class);
            po.setId(IdUtil.objectId());
            po.init();
            save(po);
            return "保存成功";
        } else {
            BeanUtil.copyProperties(vo, po);
            po.initByUpdate();
            updateById(po);
            return "更新成功";
        }
    }

    /**
     * 验证对象
     *
     * @param vo 提交参数
     */
    private void validate(userVo vo) {
        if (vo == null) {
            throw new ParameterException("参数不能为空");
        }

    }

    /**
     * 查询分页数据
     *
     * @param search 分页查询对象
     * @return
     */
    public PageData<userVo> findByPage(userSearchVo search) {
        return PageData.of(baseMapper.findByPage(search));
    }


}
