package com.myy.common.base;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myy.common.exception.ParameterException;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.List;
import java.util.Set;

/**
 * 搜索基础类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class PageSearch<T> extends Page<T> {

    @Serial
    private static final long serialVersionUID = -6228047853926635234L;
    /**
     * 当前页数
     */
    protected long currPage = 1;

    /**
     * 每页显示条数，默认 10
     */
    protected long pageSize = 10;

    /**
     * 排序,确保column指定正确
     */
    protected List<OrderItem> orders;
    /**
     * 排序字符串
     */
    @JsonIgnore
    protected boolean needOrder = true;
    /**
     * 查询数据
     */
    @JsonIgnore
    private List<T> data;
    /**
     * 总数
     */
    @JsonIgnore
    private long total;

    /**
     * 获取需要排序的列
     */
    public Set<String> orderColumns() {
        return Set.of();
    }

    public boolean getNeedOrder() {
        if (needOrder && orders != null && !orders.isEmpty()) {
            Set<String> searchs = orderColumns();
            if (CollUtil.isEmpty(searchs)) {
                throw new ParameterException("搜索对象中允许排序字段未配置");
            }
            for (OrderItem item : orders) {
                String field = item.getColumn();
                if (field.contains(".")) {
                    field = item.getColumn().split("\\.")[1];
                }
                if (!searchs.contains(field)) { //校验列是否正确
                    throw new ParameterException(StrUtil.format("排序字段{}不在允许排序范围{}内", item.getColumn(), Convert.toStr(searchs)));
                }
            }
            this.needOrder = false;
        }
        return needOrder;
    }


    @Override
    public List<OrderItem> orders() {
        return orders;
    }

    @Override
    public List<T> getRecords() {
        return data;
    }

    @Override
    public Page<T> setRecords(List<T> records) {
        this.data = records;
        return this;
    }

    @Override
    public long getTotal() {
        return total;
    }

    @Override
    public Page<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    @Override
    public long getSize() {
        return pageSize;
    }

    @Override
    public Page<T> setSize(long size) {
        pageSize = size;
        return this;
    }

    @Override
    public long getCurrent() {
        return currPage;
    }

    @Override
    public Page<T> setCurrent(long current) {
        currPage = current;
        return this;
    }
}
