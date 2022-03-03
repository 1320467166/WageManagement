package com.yuanlrc.base.bean;

public enum PercentageEnum {
    ANNUITY(1,0.08,"养老"),
    MEDICAL(2,0.02,"医疗"),
    UNEMPLOYMENT(3,0.005,"失业"),
    OCCUPATIONAL_INJURY(4,0.00,"工伤"),
    CHILDBIRTH(5,0.00,"生育"),
    HOUSING_FUND(6,0.07,"住房公积金"),;

    private Integer code;

    private Double rate;

    private String value;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    PercentageEnum(Integer code, Double rate, String value) {
        this.code = code;
        this.rate = rate;
        this.value = value;
    }
}
