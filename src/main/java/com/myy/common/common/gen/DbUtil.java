package com.myy.common.common.gen;

import cn.hutool.core.util.StrUtil;
import cn.hutool.db.ds.DSFactory;
import cn.hutool.db.ds.simple.SimpleDSFactory;
import cn.hutool.setting.Setting;
import com.myy.common.common.exception.ConfigException;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class DbUtil {

    private static final Map<String, String> javaType = new HashMap<>();
    private static final Map<String, String> importMap = new HashMap<>();

    public static String DB_TYPE = null;

    public static void init() {
        try {
            // 加载YML文件
            InputStream inputStream = DbUtil.class.getClassLoader().getResourceAsStream("application-de.yml");
            Yaml yaml = new Yaml();
            // 解析YML数据
            Map<String, Object> data = yaml.load(inputStream);
            // 读取配置信息
            Setting setting = new Setting();
            String url = getConfig(data, "spring.datasource.url");
            setting.put("url", url);
            setting.put("username", getConfig(data, "spring.datasource.username"));
            setting.put("password", getConfig(data, "spring.datasource.password"));
            setting.put("showSql", "true");
            setting.put("formatSql", "false");
            setting.put("showParams", "true");
            setting.put("sqlLevel", "debug");
            DSFactory.setCurrentDSFactory(new SimpleDSFactory(setting));
            initJavaType(url);
//            VelocityUtils.initVelocity();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private static void initJavaType(String url) {
        String dbType = dbType(url);
        DbUtil.DB_TYPE = dbType;
        if (StrUtil.equals(dbType, "dm")) {
            javaType.put("int", "int");
            javaType.put("integer", "int");
            javaType.put("number", "long");
            javaType.put("float", "float");
            javaType.put("double", "double");
            javaType.put("boolean", "boolean");
            javaType.put("varchar", "String");
            javaType.put("varchar2", "String");
            javaType.put("nvarchar2", "String");
            javaType.put("date", "Date");
            javaType.put("time", "Date");
            javaType.put("timestamp", "Date");
            javaType.put("datetime", "Date");
            javaType.put("decimal", "BigDecimal");
            javaType.put("blob", "byte[]");
        } else if (StrUtil.equals(dbType, "mysql")) {
            javaType.put("varchar", "String");
            javaType.put("char", "String");
            javaType.put("blob", "byte[]");
            javaType.put("text", "String");
            javaType.put("integer", "long");
            javaType.put("tinyint", "int");
            javaType.put("smallint", "int");
            javaType.put("mediumint", "int");
            javaType.put("bit", "boolean");
            javaType.put("bigint", "long");
            javaType.put("float", "float");
            javaType.put("double", "double");
            javaType.put("decimal", "BigDecimal");
            javaType.put("boolean", "boolean");
            javaType.put("int", "int");
            javaType.put("date", "Date");
            javaType.put("time", "Date");
            javaType.put("datetime", "Date");
            javaType.put("timestamp", "Date");
            javaType.put("year", "Date");
        } else if (StrUtil.equals(dbType, "oracle")) {
            javaType.put("number", "long");
            javaType.put("raw", "byte[]");
            javaType.put("blob raw", "byte[]");
            javaType.put("char", "String");
            javaType.put("date", "Date");
            javaType.put("float", "float");
            javaType.put("varchar2", "String");
            javaType.put("timestamp", "Date");
        } else {
            throw new ConfigException(dbType + "数据库未配置字段类型");
        }
        importMap.put("Date", "java.util.Date");
        importMap.put("BigDecimal", "java.math.BigDecimal");
    }

    public static String dbType(String url) {
        String dbType = null;
        if (StrUtil.isNotBlank(url)) {
            if (url.startsWith("jdbc:mysql")) {
                dbType = "mysql";
            } else if (url.startsWith("jdbc:oracle")) {
                dbType = "oracle";
            } else if (url.startsWith("jdbc:dm")) {
                dbType = "dm";
            } else if (url.startsWith("jdbc:kingbase")) {
                dbType = "kingbasees";
            }
            if (dbType == null) {
                throw new ConfigException(StrUtil.format("数据库url转化数据库错误:{}", url));
            }
        }
        return dbType;
    }

    public static String getImport(String type) {
        return importMap.get(type);
    }

    public static String getJavaType(String typeName) {
        if (typeName.contains("(")) {
            String name = typeName.substring(0, typeName.indexOf("("));
            return javaType.get(name.toLowerCase());
        } else {
            return javaType.get(typeName.toLowerCase());
        }
    }

    private static String getConfig(Map<String, Object> data, String key) {
        List<String> keys = StrUtil.split(key, ".");
        Map<String, Object> map = new HashMap<>(data);
        for (String k : keys) {
            Object v = map.get(k);
            if (v instanceof Map) {
                map = (Map<String, Object>) v;
            } else {
                return v.toString();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getJavaType("TIMESTAMP(6)"));
    }


}
