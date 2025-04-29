package com.myy.common.gen;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.DSFactory;
import cn.hutool.db.meta.MetaUtil;
import cn.hutool.db.meta.Table;
import cn.hutool.db.meta.TableType;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class TabMetaUtil {

    private static final Map<String, Entity> dbTypeMap = new HashMap<>();

    /**
     * 获取指定库中的所有表结构
     *
     * @param schema
     * @return
     */
    public static List<TableMeta> tableMetas(String schema) {
        List<String> tables = MetaUtil.getTables(DSFactory.get(), schema, TableType.TABLE);
        List<TableMeta> list = new ArrayList<>();
        tables.forEach(tab -> list.add(tableMeta(schema, tab)));
        return list;
    }

    /**
     * 获取指定表的表结构
     *
     * @param schema
     * @param tableName
     * @return
     */
    public static TableMeta tableMeta(String schema, String tableName) {
        TableMeta tableMeta = new TableMeta(schema, tableName, null);
        List<TableMetaColumn> columnList = new ArrayList<>();
        tableMeta.setColumns(columnList);
        if ("oracle".equals(DbUtil.DB_TYPE)) {
            String sql = "select lower(t.column_name) sname,t.data_type DATA_TYPE,t.data_length len,c.comments cname,\n" +
                    "      (SELECT CASE WHEN t.nullable='Y' THEN '1' ELSE '0' END FROM DUAL) required,\n" +
                    "      (SELECT CASE WHEN EXISTS(select 1 from user_constraints s, user_cons_columns m\n" +
                    "      where upper(m.table_name)=upper(?) and m.table_name=s.table_name\n" +
                    "      and m.constraint_name=s.constraint_name and s.constraint_type='P' and m.column_name = t.column_name) THEN 'PRI' END FROM DUAL) key\n" +
                    "      FROM user_tab_cols t, user_col_comments c\n" +
                    "      WHERE upper(t.table_name)=upper(?)\n" +
                    "      and c.table_name=t.table_name\n" +
                    "      and c.column_name=t.column_name\n" +
                    "      and t.hidden_column='NO'\n" +
                    "      order by t.column_id";
            try {
                List<DbColumn> columns = Db.use().query(sql, DbColumn.class, tableName, tableName);
                if (CollUtil.isNotEmpty(columns)) {
                    columns.forEach(column -> {
                        TableMetaColumn col = new TableMetaColumn();
                        col.setSname(column.getSname());
                        col.setCname(column.getCname());
                        col.setEname(StrUtil.toCamelCase(column.getSname().toLowerCase()));
                        col.setPk("PRI".equals(column.getKey()));
                        col.setJavaType(DbUtil.getJavaType(column.getDataType()));
                        col.setMaxSize(column.getLen());
                        col.setDigit(0);
                        col.setColumnDef(null);
                        col.setRequired(column.getRequired());
                        columnList.add(col);
                    });
                }
            } catch (SQLException e) {
                log.error("", e);
            }
        } else {
            Table table = MetaUtil.getTableMeta(DSFactory.get(), null, schema, tableName);
            tableMeta.setComment(table.getComment());
            tableMeta.setClassName(StrUtil.toCamelCase(table.getTableName()));
            table.getColumns().forEach(column -> {
                TableMetaColumn col = new TableMetaColumn();
                col.setSname(column.getName());
                col.setCname(column.getComment());
                col.setEname(StrUtil.toCamelCase(column.getName().toLowerCase()));
                col.setPk(column.isPk());
                col.setJavaType(DbUtil.getJavaType(column.getTypeName()));
                col.setMaxSize((int) column.getSize());
                col.setDigit(column.getDigit());
                col.setColumnDef(column.getColumnDef());
                col.setRequired(column.isNullable() ? "0" : "1");
                columnList.add(col);
            });
        }
        return tableMeta;
    }

    public static void main(String[] args) {
        DbUtil.init();
        TableMeta tabMeta = tableMeta("", "test");
//        TableMeta tabColMeta = tableMeta("ph-compj", "t_tab_column");
//        try {
//            TableInfo tabInfo = new TableInfo(tabMeta);
//            TableInfo tabColInfo = new TableInfo(tabColMeta);
//            Tab tab = new Tab(tableMeta("ph-compj", "test"));
//            tab.setId(IdUtil.fastSimpleUUID());
//            tab.setProjectId("compj");
//            Map<String, Object> tabPo = BeanUtil.beanToMap(tab);
//            SqlUtils.saveOrUpdate(tabInfo, tabPo);
//            for (int i = 0; i < tab.getColumns().size(); i++) {
//                TabColumn col = tab.getColumns().get(i);
//                col.setId(IdUtil.fastSimpleUUID());
//                col.setTabId(tab.getId());
//                col.setSort(i);
//                Map<String, Object> colPo = BeanUtil.beanToMap(col);
//                SqlUtils.saveOrUpdate(tabColInfo, colPo);
//            }
//        } catch (ParameterEmptyException e) {
//            log.error("", e);
//        }
    }
}
