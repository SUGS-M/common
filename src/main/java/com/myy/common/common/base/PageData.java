package com.myy.common.common.base;


import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageData<T> implements Serializable {

    /**
     * 每页显示条数，默认 10
     */
    protected long pageSize = 0;
    /**
     * 总数
     */
    private long total;
    /**
     * 当前分页数据
     */
    private List<T> data;
    /**
     * 当前页数
     */
    private long currPage = 1;
    /**
     * 总页数
     */
    private long pages;

    public static <T> PageData<T> of(IPage<T> page) {
        PageData<T> res = new PageData<>();
        res.setTotal(page.getTotal());
        res.setData(page.getRecords());
        res.setCurrPage(page.getCurrent());
        res.setPages(page.getPages());
        res.setPageSize(page.getSize());
        return res;
    }

    public PageData<T> total(long total) {
        this.total = total;
        return this;
    }

    public PageData<T> data(List<T> data) {
        this.data = data;
        return this;
    }
}
