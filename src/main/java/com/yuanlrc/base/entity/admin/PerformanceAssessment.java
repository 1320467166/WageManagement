package com.yuanlrc.base.entity.admin;

import com.yuanlrc.base.annotion.ValidateEntity;
import com.yuanlrc.base.bean.GradeEnum;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 绩效考核
 */
@Entity
@Table(name="ylrc_performance_assessment")
@EntityListeners(AuditingEntityListener.class)
public class PerformanceAssessment extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;//员工

    @Column(name = "percentage")
    private BigDecimal percentage;//百分比

    @DateTimeFormat(pattern = "yyyy-MM")
    @Transient
    private Date years;//日期月份

    @Column(name = "year")
    private Integer year; //年份

    @Column(name = "month")
    private Integer month;//月份

    @Column(name = "grade")
    private GradeEnum grade;//等级

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }


    public GradeEnum getGrade() {
        return grade;
    }

    public void setGrade(GradeEnum grade) {
        this.grade = grade;
    }

    public Date getYears() {
        return years;
    }

    public void setYears(Date years) {
        this.years = years;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return "PerformanceAssessment{" +
                "staff=" + staff +
                ", percentage=" + percentage +
                ", years=" + years +
                ", year=" + year +
                ", month=" + month +
                ", grade=" + grade +
                '}';
    }
}
