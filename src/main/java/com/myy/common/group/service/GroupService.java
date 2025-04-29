package com.myy.common.group.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.myy.common.group.entity.Group;
import com.myy.common.group.mapper.GroupMapper;
import com.myy.common.group.vo.GroupSearchVo;
import com.myy.common.group.vo.GroupVo;
import com.myy.common.base.PageData;
import com.myy.common.base.BaseService;
import com.myy.common.exception.ParameterException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 小组表  Service服务
 *
 * @author myy
 * @date 2025-04-22
 */
@Service
@Transactional(readOnly = true)
public class GroupService extends BaseService<GroupMapper,Group> {

    /**
     * 保存或更新
     *
     * @param vo 提交参数
     * @return
     */
    @Transactional
    public String saveOrUpdate(GroupVo vo) {
        validate(vo);
        Group po = null;
        if (StrUtil.isNotBlank(vo.getId())) {
            po = getById(vo.getId());
            if (po == null) {
                throw new ParameterException("无效的ID");
            }
        }
        if (po == null) { //新增
            po = BeanUtil.toBean(vo, Group.class);
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
    private void validate(GroupVo vo) {
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
    public PageData<GroupVo> findByPage(GroupSearchVo search) {
        return PageData.of(baseMapper.findByPage(search));
    }


}
