package com.yuanlrc.base.bean;

import java.math.BigDecimal;

/**
 * 个人所得税计算枚举
 */
public enum TaxRateEnum {

    TAX_RATE_0(0,0.03,0),//(0,3000]
    TAX_RATE_1(3000,0.10,210),//(3000,12000]
    TAX_RATE_2(12000,0.20,1410),//(12000,25000]
    TAX_RATE_3(25000,0.25,2660),//(25000,35000]
    TAX_RATE_4(35000,0.30,4410),//(35000,55000]
    TAX_RATE_5(55000,0.35,7160),//(55000,80000]
    TAX_RATE_6(80000,0.40,15160);// 大于80000

    private Integer money;//金额最低
    private Double rate;//税率
    private Integer deduction;//速算扣除数

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Integer getDeduction() {
        return deduction;
    }

    public void setDeduction(Integer deduction) {
        this.deduction = deduction;
    }

    TaxRateEnum(Integer money, Double rate, Integer deduction) {
        this.money = money;
        this.rate = rate;
        this.deduction = deduction;
    }

    public static BigDecimal countTaxRate(BigDecimal a){
        TaxRateEnum[] values = TaxRateEnum.values();
        a = a.subtract(BigDecimal.valueOf(5000));
        for(int i=values.length;i>0;i--){
            if(a.compareTo(BigDecimal.valueOf(values[i-1].getMoney()))>0){
                a = a.multiply(BigDecimal.valueOf(values[i-1].getRate()).setScale(2, BigDecimal.ROUND_HALF_UP)).subtract(BigDecimal.valueOf(values[i-1].getDeduction()));
                return a.negate();
            }
        }
        return BigDecimal.ZERO;
    }
}
