package com.myy.common.gen;

public class XgbCodeGen {

    public static void main(String[] args) {
        genDicYLjg();
    }

    private static void genSignInfo() {
        CodeInfo codeInfo = new CodeInfo();
        codeInfo.setPackagePath("com.shdata.health"); //包路径
        codeInfo.setModule("sign");//模块
        codeInfo.setAuthor("xgb");//作者
        codeInfo.setSchema("cen_sqzg");
        codeInfo.setTableName("T_SQZG_GPQY_QYXX"); //表名
        codeInfo.setClassName("SignInfo"); //java类名
        codeInfo.setEntityName("签约信息"); //实体中文名
        codeInfo.setSearchs("sfzh,sqjgbm,gpId,qystate");//搜索字段
        codeInfo.setExcel(false); //是否需要Excel导入导出
        codeInfo.setController(true); //是否需要Controller
        GenUtil.genCode(codeInfo);
    }

    private static void genDicYLjg() {
        CodeInfo codeInfo = new CodeInfo();
        codeInfo.setPackagePath("com.shdata.health"); //包路径
        codeInfo.setModule("sign");//模块
        codeInfo.setAuthor("xgb");//作者
        codeInfo.setSchema("cen_sqzg");
        codeInfo.setTableName("T_SQZG_DIC_YLJG"); //表名
        codeInfo.setClassName("DicYljg"); //java类名
        codeInfo.setEntityName("签约信息"); //实体中文名
        codeInfo.setSearchs("yljgdm,qxdm");//搜索字段
        codeInfo.setExcel(true); //是否需要Excel导入导出
        codeInfo.setController(true); //是否需要Controller
        GenUtil.genCode(codeInfo);
    }

}
