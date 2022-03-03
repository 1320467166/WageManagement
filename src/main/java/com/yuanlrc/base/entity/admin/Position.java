package com.yuanlrc.base.entity.admin;

import com.yuanlrc.base.annotion.ValidateEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 岗位
 */
@Entity
@Table(name = "ylrc_position")
@EntityListeners(AuditingEntityListener.class)
public class Position extends BaseEntity{

    @ValidateEntity(required=true,requiredLeng=true,minLength=2,maxLength=18,errorRequiredMsg="岗位名称不能为空!",errorMinLengthMsg="岗位名称长度不能小于2!",errorMaxLengthMsg="岗位名称长度不能大于18!")
    @Column(name="name",nullable=false,length=18,unique=true)
    private String name;//名称

    @Column(name="note")
    private String note;//描述

    @ValidateEntity(required = true,requiredMinValue = true,requiredMaxValue = true,minValue = 0,maxValue = 100000
            ,errorRequiredMsg="补贴不能为空!",errorMinValueMsg="补贴最小为0!",errorMaxValueMsg="补贴最大为100000!")
    @Column(name = "subsidy")
    private BigDecimal subsidy;//补贴

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public BigDecimal getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(BigDecimal subsidy) {
        this.subsidy = subsidy;
    }

    @Override
    public String toString() {
        return "Position{" +
                "name='" + name + '\'' +
                ", note='" + note + '\'' +
                ", subsidy=" + subsidy +
                '}';
    }
}
