package com.yuanlrc.base.bean;

/**
 * 指标分类
 */
public enum TargetClassify {

    PERFORMANCE(1,"业绩考核"),
    CAPACITY(2,"能力考核"),
    ATTITUDE(3,"态度考核"),
    MANAGE(4,"经营考核"),
    OTHER(5,"其他");

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

    TargetClassify(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}
