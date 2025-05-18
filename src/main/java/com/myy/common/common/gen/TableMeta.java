package com.myy.common.common.gen;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableMeta {

    private String className;
    private String schema;
    private String tableName;
    private String comment;
    private List<TableMetaColumn> columns;

    private Set<String> sets;
    private Set<String> searchSets;

    public TableMeta(String schema, String tableName, String comment) {
        this.schema = schema;
        this.tableName = tableName;
        this.comment = comment;
    }

    public Set<String> getEntityImport() {
        Set<String> sets = new HashSet<>();
        for (TableMetaColumn col : columns) {
            if (col.isNotBaseField()) {
                String im = DbUtil.getImport(col.getJavaType());
                if (StrUtil.isNotBlank(im)) {
                    sets.add(im);
                }
            }
        }
        return sets;
    }

    public Set<String> getVoImport() {
        Set<String> sets = new HashSet<>();
        for (TableMetaColumn col : columns) {
            if (col.isNotBaseField()) {
                String im = DbUtil.getImport(col.getJavaType());
                if (StrUtil.isNotBlank(im)) {
                    sets.add(im);
                    if ("Date".equals(col.getJavaType())) {
                        sets.add("com.fasterxml.jackson.annotation.JsonFormat");
                        sets.add("org.springframework.format.annotation.DateTimeFormat");
                    }
                }
            }
        }
        return sets;
    }

    public Set<String> getSearchImport() {
        Set<String> sets = new HashSet<>();
        for (TableMetaColumn col : columns) {
            if ("1".equals(col.getSearch())) {
                String im = DbUtil.getImport(col.getJavaType());
                if (StrUtil.isNotBlank(im)) {
                    sets.add(im);
                    if ("Date".equals(col.getJavaType())) {
                        sets.add("com.fasterxml.jackson.annotation.JsonFormat");
                        sets.add("org.springframework.format.annotation.DateTimeFormat");
                    }
                }
            }
        }
        return sets;
    }

    public Boolean javaTypeSearchHas(String dt) {
        if (searchSets == null) {
            searchSets = new HashSet<>();
            for (TableMetaColumn col : columns) {
                if ("1".equals(col.getSearch())) {
                    searchSets.add(col.getJavaType());
                }
            }
        }
        return searchSets.contains(dt);
    }

    public String genOrderColumns() {
        StringBuilder ocs = new StringBuilder(100);
        for (TableMetaColumn col : columns) {
            if (col.isNotBaseField()) {
                if (!ocs.isEmpty()) {
                    ocs.append(", ");
                }
                ocs.append("\"").append(col.getSname()).append("\"");
            }
        }
        return ocs.toString();
    }

    /**
     * 新增搜索属性，多个以逗号隔开
     */
    public void addSearch(String fields) {
        if (StrUtil.isNotBlank(fields)) {
            Set<String> fieldSet = Set.of(fields.split(","));
            for (TableMetaColumn column : columns) {
                if (fieldSet.contains(column.getEname())) {
                    column.setSearch("1");
                }
            }
        }
    }
}
