package com.yuanlrc.base.entity.admin;

import com.yuanlrc.base.annotion.ValidateEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 工资项
 */
@Entity
@Table(name="ylrc_wage_item")
@EntityListeners(AuditingEntityListener.class)
public class WageItem extends BaseEntity{

    @ValidateEntity(required = true,requiredMinValue = true,requiredMaxValue = true,minValue = 0,maxValue = 100000
            ,errorRequiredMsg="迟到罚金不能为空!",errorMinValueMsg="迟到罚金最小为0!",errorMaxValueMsg="迟到罚金最大为100000!")
    @Column(name = "late_penalty")
    private BigDecimal latePenalty;//迟到罚金

    @ValidateEntity(required = true,requiredMinValue = true,requiredMaxValue = true,minValue = 0,maxValue = 100000
            ,errorRequiredMsg="早退罚金不能为空!",errorMinValueMsg="早退罚金最小为0!",errorMaxValueMsg="早退罚金最大为100000!")
    @Column(name = "leave_early_fine")
    private BigDecimal leaveEarlyFine;//早退罚金

    @ValidateEntity(required = true,requiredMinValue = true,requiredMaxValue = true,minValue = 0,maxValue = 100000
            ,errorRequiredMsg="出差补贴不能为空!",errorMinValueMsg="出差补贴最小为0!",errorMaxValueMsg="出差补贴最大为100000!")
    @Column(name = "mission_allowance ")
    private BigDecimal missionAllowance;//出差补贴

    @ValidateEntity(required = true,requiredMinValue = true,requiredMaxValue = true,minValue = 0,maxValue = 100000
            ,errorRequiredMsg="全勤奖金不能为空!",errorMinValueMsg="全勤奖金最小为0!",errorMaxValueMsg="全勤奖金最大为100000!")
    @Column(name = "attendance_bonus")
    private BigDecimal attendanceBonus;//全勤奖金

    @ValidateEntity(required = true,requiredMinValue = true,requiredMaxValue = true,minValue = 0,maxValue = 100000
            ,errorRequiredMsg="餐饮补贴不能为空!",errorMinValueMsg="餐饮补贴最小为0!",errorMaxValueMsg="餐饮补贴最大为100000!")
    @Column(name = "food_subsidy ")
    private BigDecimal foodSubsidy;//餐饮补贴

    @ValidateEntity(required = true,requiredMinValue = true,requiredMaxValue = true,minValue = 0,maxValue = 100000
            ,errorRequiredMsg="交通补贴不能为空!",errorMinValueMsg="交通补贴最小为0!",errorMaxValueMsg="交通补贴最大为100000!")
    @Column(name = "travel_allowance")
    private BigDecimal travelAllowance;//交通补贴

    @ValidateEntity(required = true,requiredMinValue = true,requiredMaxValue = true,minValue = 0,maxValue = 100000
            ,errorRequiredMsg="加班补贴不能为空!",errorMinValueMsg="加班补贴最小为0!",errorMaxValueMsg="加班补贴最大为100000!")
    @Column(name = "overtime_allowance")
    private BigDecimal overTimeAllowance;//加班补贴

    @ValidateEntity(required = true,requiredMinValue = true,requiredMaxValue = true,minValue = 0,maxValue = 100000
            ,errorRequiredMsg="绩效奖金不能为空!",errorMinValueMsg="绩效奖金最小为0!",errorMaxValueMsg="绩效奖金最大为100000!")
    @Column(name = "performance_bonus ")
    private BigDecimal performance;//绩效奖金

    @ValidateEntity(required = true,requiredMinValue = true,requiredMaxValue = true,minValue = 0,maxValue = 100000
            ,errorRequiredMsg="年度考核奖金不能为空!",errorMinValueMsg="年度考核奖金最小为0!",errorMaxValueMsg="年度考核奖金最大为100000!")
    @Column(name = "annual_assessment_bonus")
    private BigDecimal annualAssessmentBonus;//年度考核奖金

    public BigDecimal getLatePenalty() {
        return latePenalty;
    }

    public void setLatePenalty(BigDecimal latePenalty) {
        this.latePenalty = latePenalty;
    }

    public BigDecimal getLeaveEarlyFine() {
        return leaveEarlyFine;
    }

    public void setLeaveEarlyFine(BigDecimal leaveEarlyFine) {
        this.leaveEarlyFine = leaveEarlyFine;
    }

    public BigDecimal getMissionAllowance() {
        return missionAllowance;
    }

    public void setMissionAllowance(BigDecimal missionAllowance) {
        this.missionAllowance = missionAllowance;
    }

    public BigDecimal getAttendanceBonus() {
        return attendanceBonus;
    }

    public void setAttendanceBonus(BigDecimal attendanceBonus) {
        this.attendanceBonus = attendanceBonus;
    }

    public BigDecimal getFoodSubsidy() {
        return foodSubsidy;
    }

    public void setFoodSubsidy(BigDecimal foodSubsidy) {
        this.foodSubsidy = foodSubsidy;
    }

    public BigDecimal getTravelAllowance() {
        return travelAllowance;
    }

    public void setTravelAllowance(BigDecimal travelAllowance) {
        this.travelAllowance = travelAllowance;
    }

    public BigDecimal getOverTimeAllowance() {
        return overTimeAllowance;
    }

    public void setOverTimeAllowance(BigDecimal overTimeAllowance) {
        this.overTimeAllowance = overTimeAllowance;
    }

    public BigDecimal getPerformance() {
        return performance;
    }

    public void setPerformance(BigDecimal performance) {
        this.performance = performance;
    }

    public BigDecimal getAnnualAssessmentBonus() {
        return annualAssessmentBonus;
    }

    public void setAnnualAssessmentBonus(BigDecimal annualAssessmentBonus) {
        this.annualAssessmentBonus = annualAssessmentBonus;
    }

    @Override
    public String toString() {
        return "WageItem{" +
                "latePenalty=" + latePenalty +
                ", leaveEarlyFine=" + leaveEarlyFine +
                ", missionAllowance=" + missionAllowance +
                ", attendanceBonus=" + attendanceBonus +
                ", foodSubsidy=" + foodSubsidy +
                ", travelAllowance=" + travelAllowance +
                ", overTimeAllowance=" + overTimeAllowance +
                ", performance=" + performance +
                ", annualAssessmentBonus=" + annualAssessmentBonus +
                '}';
    }
}
