package com.myy.common.common.gen;

import lombok.Data;

import java.io.Serializable;

@Data
public class DbColumn implements Serializable {

    private String sname;

    private String dataType;

    private int len;

    private String cname;

    private String required;

    private String key;

}
