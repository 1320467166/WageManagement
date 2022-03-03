package com.yuanlrc.base.entity.admin;

import com.yuanlrc.base.annotion.ValidateEntity;
import com.yuanlrc.base.bean.IsStatus;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 考勤
 */
@Entity
@Table(name="ylrc_attendance")
@EntityListeners(AuditingEntityListener.class)
public class Attendance extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;//员工

    @JoinColumn(name = "staff_name")
    private String name;//员工

    @Transient
    @DateTimeFormat(pattern = "yyyy-MM")
    private Date month;//日期

    @Column(name = "year")
    private Integer year;//年份

    @Column(name = "month_of_day")
    private Integer monthOfDay;//月份

    @Column(name = "job_number")
    private String jobNumber;//工号

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;//部门

    @ValidateEntity(required = true,requiredMinValue = true,minValue = 0,requiredMaxValue = true,
    maxValue = 31,errorRequiredMsg = "病假天数不能为空",errorMinValueMsg = "病假天数最小为0",errorMaxValueMsg = "病假天数最大为31")
    @Column(name = "days_sick")
    private Double daysSick;//病假天数

    @ValidateEntity(required = true,requiredMinValue = true,minValue = 0,requiredMaxValue = true,
            maxValue = 31,errorRequiredMsg = "事假天数不能为空",errorMinValueMsg = "事假天数最小为0",errorMaxValueMsg = "事假天数最大为31")
    @Column(name = "personal_leave_days")
    private Double personalLeaveDays;//事假天数

    @ValidateEntity(required = true,requiredMinValue = true,minValue = 0,requiredMaxValue = true,
            maxValue = 800,errorRequiredMsg = "平时加班不能为空",errorMinValueMsg = "平时加班最小为0",errorMaxValueMsg = "平时加班最大为800")
    @Column(name = "overtime_hours")
    private Double overtimeHours;//平时加班

    @ValidateEntity(required = true,requiredMinValue = true,minValue = 0,requiredMaxValue = true,
            maxValue = 800,errorRequiredMsg = "周末加班不能为空",errorMinValueMsg = "周末加班最小为0",errorMaxValueMsg = "周末加班最大为800")
    @Column(name = "overtime_on_weekends")
    private Double overtimeOnWeekends;//周末加班

    @ValidateEntity(required = true,requiredMinValue = true,minValue = 0,requiredMaxValue = true,
            maxValue = 800,errorRequiredMsg = "节假日加班不能为空",errorMinValueMsg = "节假日加班最小为0",errorMaxValueMsg = "节假日加班最大为800")
    @Column(name = "holiday_overtime_hours")
    private Double holidayOvertimeHours;//节假日加班

    @ValidateEntity(required = true,requiredMinValue = true,minValue = 0,requiredMaxValue = true,
            maxValue = 31,errorRequiredMsg = "迟到次数不能为空",errorMinValueMsg = "迟到次数最小为0",errorMaxValueMsg = "迟到次数最大为31")
    @Column(name = "late_number")
    private Integer lateNumber;//迟到次数

    @ValidateEntity(required = true,requiredMinValue = true,minValue = 0,requiredMaxValue = true,
            maxValue = 31,errorRequiredMsg = "早退次数不能为空",errorMinValueMsg = "早退次数最小为0",errorMaxValueMsg = "早退次数最大为31")
    @Column(name = "leave_early_times")
    private Integer leaveEarlyTimes;//早退次数

    @ValidateEntity(required = true,requiredMinValue = true,minValue = 0,requiredMaxValue = true,
            maxValue = 31,errorRequiredMsg = "缺勤天数不能为空",errorMinValueMsg = "缺勤天数最小为0",errorMaxValueMsg = "缺勤天数最大为31")
    @Column(name = "absence_days")
    private Double absenceDays;//缺勤天数

    @ValidateEntity(required = true,requiredMinValue = true,minValue = 0,requiredMaxValue = true,
            maxValue = 31,errorRequiredMsg = "出差天数不能为空",errorMinValueMsg = "出差天数最小为0",errorMaxValueMsg = "出差天数最大为31")
    @Column(name = "travel_days")
    private Double travelDays;//出差天数

    @Enumerated
    @Column(name = "is_status")
    private IsStatus isStatus = IsStatus.NOT_ATTENDANCE;//是否全勤

    @Column(name = "leave_days")
    private Double leaveDays;//请假天数

    public Staff getStaff() {
        return staff;
    }

    public String getName() {
        return this.staff.getName();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Double getDaysSick() {
        return daysSick;
    }

    public void setDaysSick(Double daysSick) {
        this.daysSick = daysSick;
    }

    public Double getPersonalLeaveDays() {
        return personalLeaveDays;
    }

    public void setPersonalLeaveDays(Double personalLeaveDays) {
        this.personalLeaveDays = personalLeaveDays;
    }

    public Double getOvertimeHours() {
        return overtimeHours;
    }

    public void setOvertimeHours(Double overtimeHours) {
        this.overtimeHours = overtimeHours;
    }

    public Double getOvertimeOnWeekends() {
        return overtimeOnWeekends;
    }

    public void setOvertimeOnWeekends(Double overtimeOnWeekends) {
        this.overtimeOnWeekends = overtimeOnWeekends;
    }

    public Double getHolidayOvertimeHours() {
        return holidayOvertimeHours;
    }

    public void setHolidayOvertimeHours(Double holidayOvertimeHours) {
        this.holidayOvertimeHours = holidayOvertimeHours;
    }

    public Integer getLateNumber() {
        return lateNumber;
    }

    public void setLateNumber(Integer lateNumber) {
        this.lateNumber = lateNumber;
    }

    public Integer getLeaveEarlyTimes() {
        return leaveEarlyTimes;
    }

    public void setLeaveEarlyTimes(Integer leaveEarlyTimes) {
        this.leaveEarlyTimes = leaveEarlyTimes;
    }

    public Double getAbsenceDays() {
        return absenceDays;
    }

    public void setAbsenceDays(Double absenceDays) {
        this.absenceDays = absenceDays;
    }

    public Double getTravelDays() {
        return travelDays;
    }

    public void setTravelDays(Double travelDays) {
        this.travelDays = travelDays;
    }

    public IsStatus getIsStatus() {
        return isStatus;
    }

    public void setIsStatus(IsStatus isStatus) {
        this.isStatus = isStatus;
    }

    public Double getLeaveDays() {
        return leaveDays;
    }

    public void setLeaveDays(Double leaveDays) {
        this.leaveDays = leaveDays;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonthOfDay() {
        return monthOfDay;
    }

    public void setMonthOfDay(Integer monthOfDay) {
        this.monthOfDay = monthOfDay;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "staff=" + staff +
                ", month=" + month +
                ", year=" + year +
                ", monthOfDay=" + monthOfDay +
                ", jobNumber='" + jobNumber + '\'' +
                ", department=" + department +
                ", daysSick=" + daysSick +
                ", personalLeaveDays=" + personalLeaveDays +
                ", overtimeHours=" + overtimeHours +
                ", overtimeOnWeekends=" + overtimeOnWeekends +
                ", holidayOvertimeHours=" + holidayOvertimeHours +
                ", lateNumber=" + lateNumber +
                ", leaveEarlyTimes=" + leaveEarlyTimes +
                ", absenceDays=" + absenceDays +
                ", travelDays=" + travelDays +
                ", isStatus=" + isStatus +
                ", leaveDays=" + leaveDays +
                '}';
    }
}
