package com.yuanlrc.base.entity.admin;

import com.yuanlrc.base.annotion.ValidateEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 职称
 */
@Entity
@Table(name = "ylrc_job_title")
@EntityListeners(AuditingEntityListener.class)
public class JobTitle extends BaseEntity{

    @ValidateEntity(required=true,requiredLeng=true,minLength=1,maxLength=18,errorRequiredMsg="职称名称不能为空!",errorMinLengthMsg="职称名称不能为空!",errorMaxLengthMsg="职称名称长度不能大于18!")
    @Column(name="name",nullable=false,length=18,unique=true)
    private String name;//名称

    @ValidateEntity(required = true,requiredMinValue = true,requiredMaxValue = true,minValue = 0,maxValue = 100000
            ,errorRequiredMsg="奖金不能为空!",errorMinValueMsg="奖金最小为0!",errorMaxValueMsg="奖金最大为100000!")
    @Column(name = "bonus")
    private BigDecimal bonus;//奖金

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

    @Override
    public String toString() {
        return "JobTitle{" +
                "name='" + name + '\'' +
                ", bonus=" + bonus +
                '}';
    }
}
