package com.myy.common.gen;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.Properties;

@Slf4j
public class GenUtil {
    public static final String UTF8 = "UTF-8";
    /**
     * 项目空间路径
     */
    private static final String PROJECT_PATH = "main/java";
    /**
     * mybatis空间路径
     */
    private static final String MYBATIS_PATH = "main/resources/mapper";


   private static void createEntityFile(VelocityContext context) {
        String packagePath = context.get("packagePath").toString();
        String className = context.get("ClassName").toString();
        String module = context.get("module").toString();
        String javaPath = PROJECT_PATH + "/" + StrUtil.replace(packagePath, ".", "/");
        // 处理module为空的情况
        String moduleDir = StrUtil.isEmpty(module) ? "" : "/" + module;
        String fileName = StrUtil.format("{}/entity/{}.java", javaPath + moduleDir, className);
        convertTpl(context, "template/entity.java.vm", fileName);
   }


    private static void createVoFile(VelocityContext context) {
        String packagePath = context.get("packagePath").toString();
        String className = context.get("ClassName").toString();
        String module = context.get("module").toString();
        String javaPath = PROJECT_PATH + "/" + StrUtil.replace(packagePath, ".", "/");
        // 处理module为空的情况
        String moduleDir = StrUtil.isEmpty(module) ? "" : "/" + module;
        String fileName = StrUtil.format("{}/vo/{}Vo.java", javaPath + moduleDir, className);
        convertTpl(context, "template/vo.java.vm", fileName);
    }

    private static void createSearchVoFile(VelocityContext context) {
        String packagePath = context.get("packagePath").toString();
        String className = context.get("ClassName").toString();
        String module = context.get("module").toString();
        String javaPath = PROJECT_PATH + "/" + StrUtil.replace(packagePath, ".", "/");
        // 处理module为空的情况
        String moduleDir = StrUtil.isEmpty(module) ? "" : "/" + module;
        String fileName = StrUtil.format("{}/vo/{}SearchVo.java", javaPath + moduleDir, className);
        convertTpl(context, "template/search.java.vm", fileName);
    }

    private static void createMapperJavaFile(VelocityContext context) {
        String packagePath = context.get("packagePath").toString();
        String className = context.get("ClassName").toString();
        String module = context.get("module").toString();
        String javaPath = PROJECT_PATH + "/" + StrUtil.replace(packagePath, ".", "/");
        // 处理module为空的情况
        String moduleDir = StrUtil.isEmpty(module) ? "" : "/" + module;
        String fileName = StrUtil.format("{}/mapper/{}Mapper.java", javaPath + moduleDir, className);
        convertTpl(context, "template/mapper.java.vm", fileName);
    }

    private static void createMapperXmlFile(VelocityContext context) {
        String className = context.get("ClassName").toString();
        String module = context.get("module").toString();
        String mybatisPath = StrUtil.isEmpty(module) ? MYBATIS_PATH : MYBATIS_PATH + "/" + module;
        String fileName = StrUtil.format("{}/{}Mapper.xml", mybatisPath, className);
        convertTpl(context, "template/mapper.xml.vm", fileName);
    }

    private static void createServiceJavaFile(VelocityContext context) {
        String packagePath = context.get("packagePath").toString();
        String className = context.get("ClassName").toString();
        String module = context.get("module").toString();
        String javaPath = PROJECT_PATH + "/" + StrUtil.replace(packagePath, ".", "/");
        // 处理module为空的情况
        String moduleDir = StrUtil.isEmpty(module) ? "" : "/" + module;
        String fileName = StrUtil.format("{}/service/{}Service.java", javaPath + moduleDir, className);
        convertTpl(context, "template/service.java.vm", fileName);
    }

    private static void createControllerJavaFile(VelocityContext context) {
        String packagePath = context.get("packagePath").toString();
        String className = context.get("ClassName").toString();
        String module = context.get("module").toString();
        String javaPath = PROJECT_PATH + "/" + StrUtil.replace(packagePath, ".", "/");
        // 处理module为空的情况
        String moduleDir = StrUtil.isEmpty(module) ? "" : "/" + module;
        String fileName = StrUtil.format("{}/controller/{}Controller.java", javaPath + moduleDir, className);
        convertTpl(context, "template/controller.java.vm", fileName);
    }

    private static void convertTpl(VelocityContext context, String template, String filePath) {
        StringWriter sw = new StringWriter();
        Template tpl = Velocity.getTemplate(template, UTF8);
        tpl.merge(context, sw);
        String baseUrl = new File("").getAbsolutePath();
        String newFile = baseUrl + File.separator + "src" + File.separator + filePath;
        //删除文件
        FileUtil.del(newFile);
        // 创建并写入文件
        File file = FileUtil.newFile(newFile);
        FileUtil.writeString(sw.toString(), file, UTF8);
        try {
            sw.close();
        } catch (IOException e) {
            log.error("", e);
        }
    }

    public static void initVelocity() {
        Properties p = new Properties();
        try {
            // 加载classpath目录下的vm文件
            p.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            // 定义字符集
            p.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
//            p.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
            // 初始化Velocity引擎，指定配置Properties
            Velocity.init(p);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void genCode(CodeInfo info) {
        DbUtil.init();
        initVelocity();
        VelocityContext context = new VelocityContext();
        context.put("packagePath", info.getPackagePath());
        context.put("module", info.getModule());
        context.put("entityName", info.getEntityName());
        context.put("tableName", info.getTableName());
        context.put("author", info.getAuthor());
        context.put("datetime", DateUtil.format(new Date(), "yyyy-MM-dd"));
        context.put("ClassName", info.getClassName());
        context.put("className", StrUtil.lowerFirst(info.getClassName()));
        TableMeta tabMeta = TabMetaUtil.tableMeta(info.getSchema(), info.getTableName());
        tabMeta.addSearch(info.getSearchs());
        context.put("table", tabMeta);
        context.put("columns", tabMeta.getColumns());
        context.put("entityImports", tabMeta.getEntityImport());
        context.put("voImports", tabMeta.getVoImport());
        context.put("searchImports", tabMeta.getSearchImport());
        context.put("excel", info.isExcel());
        context.put("EntityBaseName", info.getEntityBaseName());
        createEntityFile(context);
        createVoFile(context);
        createSearchVoFile(context);
        createMapperJavaFile(context);
        createMapperXmlFile(context);
        createServiceJavaFile(context);
        if (info.isController()) {
            createControllerJavaFile(context);
        }
    }


}
