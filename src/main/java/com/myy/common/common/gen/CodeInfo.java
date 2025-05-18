package com.myy.common.common.gen;

import lombok.Data;

@Data
public class CodeInfo {

    /**
     * 包路径
     */
    private String packagePath;
    /**
     * 模块
     */
    private String module;
    /**
     * 作者
     */
    private String author;
    /**
     * 表schema
     */
    private String schema;
    /**
     * 表名
     */
    private String tableName;
    /**
     * java类名
     */
    private String className;
    /**
     * 实体名称
     */
    private String entityName;
    /**
     * 实体继承名称
     */
    private String entityBaseName = "BaseEntity";
    /**
     * 搜索字段
     */
    private String searchs;
    /**
     * 是否需要excel导入导出
     */
    private boolean excel;
    /**
     * 是否需要controller
     */
    private boolean controller;

}
