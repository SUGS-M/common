package com.myy.common.common.gen;

import com.myy.common.common.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableMetaColumn {

    /**
     * sql表字段
     **/
    private String sname;
    /**
     * 属性名
     */
    private String ename;
    /**
     * 字段备注
     **/
    private String cname;
    /**
     * 是否为主键
     **/
    private boolean isPk;
    /**
     * java类型
     **/
    private String javaType;
    /**
     * 字段值默认
     */
    private String columnDef;
    /**
     * 最大长度
     */
    private int maxSize;
    /**
     * 保留小数点数
     */
    private int digit;
    /**
     * 是否必填
     */
    private String required;
    /**
     * 是否搜索字段
     */
    private String search;

    public boolean isNotBaseField() {
        return !BaseEntity.contain(ename);
    }

    public boolean isPk() {
        return isPk;
    }

    public boolean required() {
        return !isPk && "1".equals(required);
    }
}
