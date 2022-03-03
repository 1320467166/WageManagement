package com.yuanlrc.base.bean;

/**
 * 员工状态枚举
 */
public enum StaffStatus {

    ON_THE_JOB(1,"在职"),
    QUIT(2,"离职");
    public Integer code;
    public String value;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    StaffStatus(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}