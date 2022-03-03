package com.yuanlrc.base.bean;

/**
 * 性别枚举类
 */
public enum SexEnum {

    MALE(1, "男性"),
    FEMALE(2, "女性"),
    UNKNOWN(0,"未知");
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


    SexEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValueByCode(Integer code){
        for (SexEnum sex : values()) {
            if(sex.getCode().equals(code)){
                return sex.getValue();
            }
        }
        return null;
    }

    public static SexEnum getSex(String sex)
    {
        if(sex.equals(SexEnum.FEMALE.value))
            return SexEnum.FEMALE;
        else if(sex.equals(SexEnum.MALE.value))
            return SexEnum.MALE;
        else
            return null;
    }

}
