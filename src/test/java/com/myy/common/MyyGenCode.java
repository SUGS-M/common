package com.myy.common;

import com.myy.common.common.gen.CodeInfo;
import com.myy.common.common.gen.GenUtil;

public class MyyGenCode {
    public static void main(String[] args) {
        genDicHlxm();
    }
    private static void genDicHlxm() {
        CodeInfo codeInfo = new CodeInfo();
        codeInfo.setPackagePath("com.myy.common.usercenter"); //包路径
        codeInfo.setModule("user");//模块
        codeInfo.setAuthor("myy");//作者
        codeInfo.setTableName("sys_user"); //表名
        codeInfo.setClassName("user"); //java类名
        codeInfo.setEntityName("用户表"); //实体中文名
        codeInfo.setSearchs("");//搜索字段，多个字段逗号隔开
        codeInfo.setExcel(false); //是否需要Excel导入导出
        codeInfo.setController(true); //是否需要Controller
        GenUtil.genCode(codeInfo);
    }
}
