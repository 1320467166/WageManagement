package com.yuanlrc.base.bean;

/**
 * 等级枚举
 */
public enum GradeEnum {

    GRADE_A(85.00,"A",1.00),
    GRADE_B(70.00,"B",0.75),
    GRADE_C(50.00,"C",0.50),
    GRADE_D(0.00,"D",0.00);

    public Double code;

    public String value;

    public Double rate;

    public Double getCode() {
        return code;
    }

    public void setCode(Double code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    GradeEnum(Double code, String value, Double rate) {
        this.code = code;
        this.value = value;
        this.rate = rate;
    }

    public static GradeEnum countGrade(double a){
        GradeEnum[] values = GradeEnum.values();
        for(int i=0;i<values.length;i++){
            Double code = values[i].code;
            if(a>=code){
                return values[i];
            }
        }
        return null;
    }

}