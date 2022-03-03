package com.yuanlrc.base.entity.admin;

import com.yuanlrc.base.bean.IsStatus;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 工资
 */
@Entity
@Table(name="ylrc_salary")
@EntityListeners(AuditingEntityListener.class)
public class Salary extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;//员工

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;//部门

    @Column(name = "basic_wage")
    private BigDecimal basicWage;//基本工资

    @Column(name = "should_salary")
    private BigDecimal shouldSalary;//应发工资

    @Column(name = "net_payroll")
    private BigDecimal netPayroll;//实发工资

    @Column(name = "food_subsidy ")
    private BigDecimal foodSubsidy;//餐饮补贴

    @Column(name = "position_subsidy")
    private BigDecimal positionSubsidy;//岗位补贴

    @Column(name = "travel_allowance")
    private BigDecimal travelAllowance;//交通补贴

    @Column(name = "mission_allowance ")
    private BigDecimal missionAllowance;//出差补贴

    @Column(name = "years_bonus")
    private BigDecimal yearsBonus;//工龄奖金

    @Column(name = "job_title_bonus")
    private BigDecimal jobTitleBonus;//职称奖金

    @Column(name = "overtime_bonus")
    private BigDecimal overTimeBonus;//加班奖金

    @Column(name = "attendance_bonus")
    private BigDecimal attendanceBonus;//全勤奖金

    @Column(name = "annuity")
    private BigDecimal annuity;//养老

    @Column(name = "medical")
    private BigDecimal medical;//医疗

    @Column(name = "unemployment")
    private BigDecimal unemployment;//失业

    @Column(name = "occupational_injury")
    private BigDecimal occupationalInjury;//工伤

    @Column(name = "childbirth")
    private BigDecimal childbirth;//生育

    @Column(name = "housing_fund")
    private BigDecimal housingFund;//住房公积金

    @Column(name = "late_penalty")
    private BigDecimal latePenalty;//迟到

    @Column(name = "leave_early_fine")
    private BigDecimal leaveEarlyFine;//早退

    @Column(name = "sick")
    private BigDecimal sick;//病假

    @Column(name = "personal_leave")
    private BigDecimal personalLeave;//事假

    @Column(name = "absence")
    private BigDecimal absence;//缺勤

    @Column(name = " individual_income_tax")
    private BigDecimal  individualIncomeTax;//个人所得税

    @Transient
    @DateTimeFormat(pattern = "yyyy-MM")
    private Date month;//日期月份

    @Column(name = "performance_bonus ")
    private BigDecimal performance;//绩效奖金

    @Column(name = "annual_assessment_bonus")
    private BigDecimal annualAssessmentBonus;//年度考核奖金

    @Column(name = "is_status")
    private IsStatus isStatus = IsStatus.NOT_ATTENDANCE;//是否发放

    @Column(name = "year")
    private Integer year;//年份

    @Column(name = "month_of_day")
    private Integer monthOfDay;//月份

    public BigDecimal getAbsence() {
        return absence;
    }

    public void setAbsence(BigDecimal absence) {
        this.absence = absence;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public BigDecimal getBasicWage() {
        return basicWage;
    }

    public void setBasicWage(BigDecimal basicWage) {
        this.basicWage = basicWage;
    }

    public BigDecimal getShouldSalary() {
        return shouldSalary;
    }

    public void setShouldSalary(BigDecimal shouldSalary) {
        this.shouldSalary = shouldSalary;
    }

    public BigDecimal getNetPayroll() {
        return netPayroll;
    }

    public void setNetPayroll(BigDecimal netPayroll) {
        this.netPayroll = netPayroll;
    }

    public BigDecimal getFoodSubsidy() {
        return foodSubsidy;
    }

    public void setFoodSubsidy(BigDecimal foodSubsidy) {
        this.foodSubsidy = foodSubsidy;
    }

    public BigDecimal getPositionSubsidy() {
        return positionSubsidy;
    }

    public void setPositionSubsidy(BigDecimal positionSubsidy) {
        this.positionSubsidy = positionSubsidy;
    }

    public BigDecimal getTravelAllowance() {
        return travelAllowance;
    }

    public void setTravelAllowance(BigDecimal travelAllowance) {
        this.travelAllowance = travelAllowance;
    }

    public BigDecimal getMissionAllowance() {
        return missionAllowance;
    }

    public void setMissionAllowance(BigDecimal missionAllowance) {
        this.missionAllowance = missionAllowance;
    }

    public BigDecimal getYearsBonus() {
        return yearsBonus;
    }

    public void setYearsBonus(BigDecimal yearsBonus) {
        this.yearsBonus = yearsBonus;
    }

    public BigDecimal getJobTitleBonus() {
        return jobTitleBonus;
    }

    public void setJobTitleBonus(BigDecimal jobTitleBonus) {
        this.jobTitleBonus = jobTitleBonus;
    }

    public BigDecimal getOverTimeBonus() {
        return overTimeBonus;
    }

    public void setOverTimeBonus(BigDecimal overTimeBonus) {
        this.overTimeBonus = overTimeBonus;
    }

    public BigDecimal getAttendanceBonus() {
        return attendanceBonus;
    }

    public void setAttendanceBonus(BigDecimal attendanceBonus) {
        this.attendanceBonus = attendanceBonus;
    }

    public BigDecimal getAnnuity() {
        return annuity;
    }

    public void setAnnuity(BigDecimal annuity) {
        this.annuity = annuity;
    }

    public BigDecimal getMedical() {
        return medical;
    }

    public void setMedical(BigDecimal medical) {
        this.medical = medical;
    }

    public BigDecimal getUnemployment() {
        return unemployment;
    }

    public void setUnemployment(BigDecimal unemployment) {
        this.unemployment = unemployment;
    }

    public BigDecimal getOccupationalInjury() {
        return occupationalInjury;
    }

    public void setOccupationalInjury(BigDecimal occupationalInjury) {
        this.occupationalInjury = occupationalInjury;
    }

    public BigDecimal getChildbirth() {
        return childbirth;
    }

    public void setChildbirth(BigDecimal childbirth) {
        this.childbirth = childbirth;
    }

    public BigDecimal getHousingFund() {
        return housingFund;
    }

    public void setHousingFund(BigDecimal housingFund) {
        this.housingFund = housingFund;
    }

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

    public BigDecimal getSick() {
        return sick;
    }

    public void setSick(BigDecimal sick) {
        this.sick = sick;
    }

    public BigDecimal getPersonalLeave() {
        return personalLeave;
    }

    public void setPersonalLeave(BigDecimal personalLeave) {
        this.personalLeave = personalLeave;
    }

    public BigDecimal getIndividualIncomeTax() {
        return individualIncomeTax;
    }

    public void setIndividualIncomeTax(BigDecimal individualIncomeTax) {
        this.individualIncomeTax = individualIncomeTax;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
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

    public IsStatus getIsStatus() {
        return isStatus;
    }

    public void setIsStatus(IsStatus isStatus) {
        this.isStatus = isStatus;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Salary{" +
                "staff=" + staff +
                ", department=" + department +
                ", basicWage=" + basicWage +
                ", shouldSalary=" + shouldSalary +
                ", netPayroll=" + netPayroll +
                ", foodSubsidy=" + foodSubsidy +
                ", positionSubsidy=" + positionSubsidy +
                ", travelAllowance=" + travelAllowance +
                ", missionAllowance=" + missionAllowance +
                ", yearsBonus=" + yearsBonus +
                ", jobTitleBonus=" + jobTitleBonus +
                ", overTimeBonus=" + overTimeBonus +
                ", attendanceBonus=" + attendanceBonus +
                ", annuity=" + annuity +
                ", medical=" + medical +
                ", unemployment=" + unemployment +
                ", occupationalInjury=" + occupationalInjury +
                ", childbirth=" + childbirth +
                ", housingFund=" + housingFund +
                ", latePenalty=" + latePenalty +
                ", leaveEarlyFine=" + leaveEarlyFine +
                ", sick=" + sick +
                ", personalLeave=" + personalLeave +
                ", absence=" + absence +
                ", individualIncomeTax=" + individualIncomeTax +
                ", month=" + month +
                ", performance=" + performance +
                ", annualAssessmentBonus=" + annualAssessmentBonus +
                ", isStatus=" + isStatus +
                ", year=" + year +
                ", monthOfDay=" + monthOfDay +
                '}';
    }
}
