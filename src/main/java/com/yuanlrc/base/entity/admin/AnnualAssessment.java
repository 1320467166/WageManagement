package com.yuanlrc.base.entity.admin;

import com.yuanlrc.base.annotion.ValidateEntity;
import com.yuanlrc.base.bean.GradeEnum;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 年度考核
 */
@Entity
@Table(name="ylrc_annual_assessment")
@EntityListeners(AuditingEntityListener.class)
public class AnnualAssessment extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;//员工

    @Column(name = "year")
    private Integer year;//年份

    @Column(name = "performance_score")
    private BigDecimal performanceScore;//绩效分数

    @Column(name = "leave_days")
    private Double leaveDays;//请假天数

    @Column(name = "absence_days")
    private Double absenceDays;//缺勤天数

    @Column(name = "overtime_hours")
    private Double overtimeHours;//加班小时

    @ValidateEntity(required = true,requiredMinValue = true,requiredMaxValue = true,minValue = 0,maxValue = 100
            ,errorRequiredMsg="评分不能为空!",errorMinValueMsg="评分最小为0!",errorMaxValueMsg="评分最大为100!")
    @Column(name = "grade")
    private BigDecimal grade;//评分

    @Column(name = "grade_enum")
    private GradeEnum gradeEnum;//等级

    public GradeEnum getGradeEnum() {
        return gradeEnum;
    }

    public void setGradeEnum(GradeEnum gradeEnum) {
        this.gradeEnum = gradeEnum;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BigDecimal getPerformanceScore() {
        return performanceScore;
    }

    public void setPerformanceScore(BigDecimal performanceScore) {
        this.performanceScore = performanceScore;
    }

    public Double getLeaveDays() {
        return leaveDays;
    }

    public void setLeaveDays(Double leaveDays) {
        this.leaveDays = leaveDays;
    }

    public Double getAbsenceDays() {
        return absenceDays;
    }

    public void setAbsenceDays(Double absenceDays) {
        this.absenceDays = absenceDays;
    }

    public Double getOvertimeHours() {
        return overtimeHours;
    }

    public void setOvertimeHours(Double overtimeHours) {
        this.overtimeHours = overtimeHours;
    }

    public BigDecimal getGrade() {
        return grade;
    }

    public void setGrade(BigDecimal grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "AnnualAssessment{" +
                "staff=" + staff +
                ", year=" + year +
                ", performanceScore=" + performanceScore +
                ", leaveDays=" + leaveDays +
                ", absenceDays=" + absenceDays +
                ", overtimeHours=" + overtimeHours +
                ", grade=" + grade +
                ", gradeEnum=" + gradeEnum +
                '}';
    }
}
