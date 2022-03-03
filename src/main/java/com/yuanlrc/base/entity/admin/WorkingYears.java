package com.yuanlrc.base.entity.admin;

import com.yuanlrc.base.annotion.ValidateEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 工龄
 */
@Entity
@Table(name = "ylrc_working_years")
@EntityListeners(AuditingEntityListener.class)
public class WorkingYears extends BaseEntity{

    @ValidateEntity(required = true,requiredMinValue = true,requiredMaxValue = true,minValue = 0,maxValue = 50,
    errorMinValueMsg = "工龄最小为0",errorMaxValueMsg = "工龄最大为50",errorRequiredMsg = "请填写工龄")
    @Column(name = "years")
    private Integer years;//工龄

    @ValidateEntity(required = true,requiredMinValue = true,requiredMaxValue = true,minValue = 0,maxValue = 100000
            ,errorRequiredMsg="工龄补贴不能为空!",errorMinValueMsg="工龄补贴最小为0!",errorMaxValueMsg="工龄补贴最大为100000!")
    @Column(name = "subsidy")
    private BigDecimal subsidy;//补贴

    public Integer getYears() {
        return years;
    }

    public void setYears(Integer years) {
        this.years = years;
    }

    public BigDecimal getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(BigDecimal subsidy) {
        this.subsidy = subsidy;
    }

    @Override
    public String toString() {
        return "WorkingYears{" +
                "years=" + years +
                ", subsidy=" + subsidy +
                '}';
    }
}
