package com.yuanlrc.base.bean;

/**
 * 学历枚举
 */
public enum EducationEnum {

    PRIMARY(1,"小学"),
    JUNIOR(2,"初中"),
    HIGH(3,"高中"),
    JUNIOR_COLLEGE(4,"专科"),
    REGULAR_COLLEGE(5,"本科"),
    MASTER(6,"硕士"),
    DOCTOR(7,"博士");


    private int code;
    private String value;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    EducationEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
