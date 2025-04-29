package com.myy.common.base;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myy.common.exception.ParameterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Slf4j
public abstract class BaseService<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    /**
     * 通过ID查询实体对象，并且转换成clazz类型
     */
    public <E> E findById(Serializable id, Class<E> clazz) {
        if (ObjectUtil.isEmpty(id)) {
            throw new ParameterException("id不能为空");
        }
        T po = getById(id);
        if (po != null) {
            return BeanUtil.toBean(po, clazz);
        }
        return null;
    }

    @Transactional
    public String deleteById(Serializable id) {
        if (ObjectUtil.isEmpty(id)) {
            throw new ParameterException("id不能为空");
        }
        return removeById(id) ? "删除成功" : "删除失败";
    }

}
