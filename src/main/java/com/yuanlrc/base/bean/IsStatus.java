package com.yuanlrc.base.bean;

/**
 * 是否全勤、发放、在职
 */
public enum IsStatus {
    IS_ATTENDANCE(0,"是"),
    NOT_ATTENDANCE(1,"否");
    private Integer code;
    private String value;

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

    IsStatus(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}
