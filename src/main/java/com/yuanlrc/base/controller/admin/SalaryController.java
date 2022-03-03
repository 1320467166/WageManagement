package com.yuanlrc.base.controller.admin;

import com.yuanlrc.base.bean.*;
import com.yuanlrc.base.entity.admin.*;
import com.yuanlrc.base.service.admin.*;
import com.yuanlrc.base.util.BigDecimalUtil;
import com.yuanlrc.base.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 工资Controller
 */
@Controller
@RequestMapping("/admin/salary")
public class SalaryController {
    
    @Autowired
    private SalaryService salaryService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private WageItemService wageItemService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private WorkingYearsService workingYearsService;

    @Autowired
    private PeranceAssmentService peranceAssmentService;

    @Autowired
    private AnnualAssessmentService annualAssessmentService;

    /**
     * 分页查询列表
     * @param model
     * @param salary
     * @param pageBean
     * @return
     */
    @RequestMapping("/list")
    public String list(Model model, Salary salary, PageBean<Salary> pageBean,
                       String month,String jobNumber){
        model.addAttribute("title","工资列表");
        model.addAttribute("jobNumber",jobNumber);
        model.addAttribute("month",month);
        model.addAttribute("pageBean",salaryService.findList(salary,pageBean,jobNumber));
        return "admin/salary/list";
    }

    /**
     * 个人工资列表
     * @param model
     * @param pageBean
     * @param salary
     * @param month
     * @return
     */
    @RequestMapping("/personage_list")
    public String personageList(Model model, PageBean<Salary> pageBean, Salary salary,
                                String month){
        model.addAttribute("title","个人工资列表");
        model.addAttribute("month",month);
        model.addAttribute("pageBean",salaryService.findPersonageList(salary,pageBean));
        return "admin/salary/personage_list";
    }

    /**
     * 查看详情
     * @param id
     * @return
     */
    @RequestMapping("/show")
    public String show(@RequestParam(name="id",required=true)Long id,Model model){
        Salary salary = salaryService.find(id);
        model.addAttribute("salary",salary);
        return "admin/salary/show";
    }

    /**
     * 工资结算页面
     * @return
     */
    @RequestMapping("/calculation")
    public String calculation(Model model){
        model.addAttribute("staffList",staffService.findByIsStatus(StaffStatus.ON_THE_JOB.getCode()));
        return "admin/salary/calculation";
    }

    /**
     * 结算全体员工奖金
     * @param salary
     * @return
     */
    @RequestMapping(value = "/calculation_all",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> calculateAll(Salary salary){
        if(salary.getMonth() == null){
            return Result.error(CodeMsg.ADMIN_ATTENDANCE_MONTH_EMPTY);
        }
        if(salary.getMonth().getTime() > new Date().getTime()){
            return Result.error(CodeMsg.ADMIN_DATE_ERROR);
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(salary.getMonth());
        int monthOfDay = cal.get(Calendar.MONTH)+1;
        int year = cal.get(Calendar.YEAR);
        //在职员工列表
        List<Staff> staffList = staffService.findByIsStatus(StaffStatus.ON_THE_JOB.getCode());

        List<Salary> salaryList = salaryService.findByYearAndMonthOfDay(year, monthOfDay);
        List<Long> collect = salaryList.stream().map(Salary -> Salary.getStaff().getId()).collect(Collectors.toList());

        for (int index = 0; index < staffList.size(); index++) {
            for (Long staffId : collect) {
                if(staffList.get(index).getId().longValue() == staffId.longValue()){
                    staffList.remove(index);
                }
            }
        }

        if(staffList == null || staffList.size() == 0){
            return Result.error(CodeMsg.ADMIN_STAFF_SEND_ALL);
        }

        List<Salary> salaries = new ArrayList<>();

        for (Staff staff : staffList) {
            Salary newSalary = new Salary();
            newSalary.setMonthOfDay(monthOfDay);
            newSalary.setYear(year);
            newSalary.setStaff(staff);
            newSalary.setDepartment(staff.getDepartment());
            setSalary(newSalary);
            salaries.add(newSalary);
        }

        try{
            salaryService.saveAll(salaries);
        }catch (Exception e){
            return Result.error(CodeMsg.ADMIN_SALARY_SAVE_ERROR);
        }

        return Result.success(true);
    }

    /**
     * 结算单个员工工资
     * @param salary
     * @return
     */
    @RequestMapping(value = "/calculation_person",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> calculatePerson(Salary salary){

        if(salary.getMonth() == null){
            return Result.error(CodeMsg.ADMIN_ATTENDANCE_MONTH_EMPTY);
        }

        if(salary.getStaff() == null || salary.getStaff().getId() == null){
            return Result.error(CodeMsg.ADMIN_SALARY_NO_STAFF_ERROR);
        }

        Staff staff = staffService.findByIdAndIsStatus(salary.getStaff().getId(), StaffStatus.ON_THE_JOB.getCode());
        if(staff == null){
            return Result.error(CodeMsg.ADMIN_STAFF_NOT_EXIST_ERROR);
        }

        if(salary.getMonth().getTime() > new Date().getTime()){
            return Result.error(CodeMsg.ADMIN_DATE_ERROR);
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(salary.getMonth());
        int monthOfDay = cal.get(Calendar.MONTH)+1;
        int year = cal.get(Calendar.YEAR);

        Salary byStaffIdAndYearAndMonthOfDay = salaryService.findByStaffIdAndYearAndMonthOfDay(salary.getStaff().getId(), year, monthOfDay);
        if(byStaffIdAndYearAndMonthOfDay != null){
            return Result.error(CodeMsg.ADMIN_SALARY_MONTH_EXIST);
        }

        salary.setMonthOfDay(monthOfDay);
        salary.setYear(year);
        salary.setStaff(staff);
        salary.setDepartment(staff.getDepartment());

        setSalary(salary);

        if(salaryService.save(salary) == null){
            return Result.error(CodeMsg.ADMIN_SALARY_SAVE_ERROR);
        }

        return Result.success(true);
    }

    public Salary setSalary(Salary salary){

        BigDecimal basicWage = salary.getStaff().getSalary();

        int daysByYearMonth = DateUtil.getDaysByYearMonth(salary.getYear(), salary.getMonthOfDay());

        //该月平均每日工资
        BigDecimal dayBasicWage = basicWage.divide(BigDecimal.valueOf(daysByYearMonth),4,BigDecimal.ROUND_HALF_UP);

        //查询工资项
        WageItem wageItem = wageItemService.findFirstByOrderByCreateTimeDesc();
        //餐饮补贴
        BigDecimal foodSubsidy = BigDecimal.ZERO;
        //交通补贴
        BigDecimal travelAllowance = BigDecimal.ZERO;
        //出差补贴
        BigDecimal missionAllowance = BigDecimal.ZERO;
        //加班奖金
        BigDecimal overTimeBonus = BigDecimal.ZERO;
        //全勤奖金
        BigDecimal attendanceBonus = BigDecimal.ZERO;
        //迟到罚金
        BigDecimal latePenalty = BigDecimal.ZERO;
        //早退罚金
        BigDecimal leaveEarlyFine = BigDecimal.ZERO;
        //病假罚金
        BigDecimal sick = BigDecimal.ZERO;
        //事假罚金
        BigDecimal personalLeave = BigDecimal.ZERO;
        //缺勤罚金
        BigDecimal absence = BigDecimal.ZERO;
        //绩效奖金
        BigDecimal performance = BigDecimal.ZERO;
        //年度考核奖金
        BigDecimal annualAssessmentBonus = BigDecimal.ZERO;

        if(wageItem != null){
            foodSubsidy = wageItem.getFoodSubsidy();
            travelAllowance = wageItem.getTravelAllowance();
            missionAllowance = wageItem.getMissionAllowance();
            overTimeBonus = wageItem.getOverTimeAllowance();
            attendanceBonus = wageItem.getAttendanceBonus();
            latePenalty = wageItem.getLatePenalty().negate();//转为负数
            leaveEarlyFine = wageItem.getLeaveEarlyFine().negate();//转为负数
            performance = wageItem.getPerformance();
            annualAssessmentBonus = wageItem.getAnnualAssessmentBonus();
        }

        //查询该月员工考勤情况
        Attendance attendance = attendanceService.findByJobNumberAndYearAndMonthOfDay(salary.getStaff().getJobNumber(), salary.getYear(), salary.getMonthOfDay());
        if(attendance != null){
            overTimeBonus = BigDecimalUtil.multiply(overTimeBonus,(attendance.getOvertimeHours() + attendance.getOvertimeOnWeekends() + attendance.getHolidayOvertimeHours()));
            if(attendance.getIsStatus() == IsStatus.NOT_ATTENDANCE){
                attendanceBonus = BigDecimal.ZERO;
            }
            latePenalty = BigDecimalUtil.multiply(latePenalty,attendance.getLateNumber());
            leaveEarlyFine = BigDecimalUtil.multiply(leaveEarlyFine,attendance.getLeaveEarlyTimes());
            sick = BigDecimalUtil.multiply(dayBasicWage,attendance.getDaysSick()).negate();
            personalLeave = BigDecimalUtil.multiply(dayBasicWage,attendance.getPersonalLeaveDays()).negate();
            absence = BigDecimalUtil.multiply(dayBasicWage,attendance.getAbsenceDays()).negate();
            if(attendance.getTravelDays() == 0){
                missionAllowance = BigDecimal.ZERO;
            }
        }else{
            overTimeBonus = BigDecimal.ZERO;
            attendanceBonus = BigDecimal.ZERO;
            latePenalty = BigDecimal.ZERO;
            leaveEarlyFine = BigDecimal.ZERO;
            missionAllowance = BigDecimal.ZERO;
        }


        //基本工资
        salary.setBasicWage(basicWage.setScale(2,BigDecimal.ROUND_HALF_UP));
        //餐饮补贴
        salary.setFoodSubsidy(foodSubsidy.setScale(2,BigDecimal.ROUND_HALF_UP));
        //岗位补贴
        salary.setPositionSubsidy((salary.getStaff().getPosition().getSubsidy()).setScale(2,BigDecimal.ROUND_HALF_UP));
        //交通补贴
        salary.setTravelAllowance(travelAllowance.setScale(2,BigDecimal.ROUND_HALF_UP));
        //出差补贴
        salary.setMissionAllowance(missionAllowance.setScale(2,BigDecimal.ROUND_HALF_UP));

        //工龄奖金
        BigDecimal yearsBonus = BigDecimal.ZERO;

        //获取工龄
        int year = DateUtil.getYear(salary.getStaff().getEntryTime());
        WorkingYears workingYears = workingYearsService.findByYears(year);
        if(workingYears != null){
            yearsBonus = workingYears.getSubsidy();
        }
        //工龄奖金
        salary.setYearsBonus(yearsBonus.setScale(2,BigDecimal.ROUND_HALF_UP));

        //职称奖金
        salary.setJobTitleBonus((salary.getStaff().getJobTitle().getBonus()).setScale(2,BigDecimal.ROUND_HALF_UP));
        //加班奖金
        salary.setOverTimeBonus(overTimeBonus.setScale(2,BigDecimal.ROUND_HALF_UP));
        //全勤奖金
        salary.setAttendanceBonus(attendanceBonus.setScale(2,BigDecimal.ROUND_HALF_UP));
        //迟到罚金
        salary.setLatePenalty(latePenalty.setScale(2,BigDecimal.ROUND_HALF_UP));
        //早退罚金
        salary.setLeaveEarlyFine(leaveEarlyFine.setScale(2,BigDecimal.ROUND_HALF_UP));
        //病假
        salary.setSick(sick.setScale(2,BigDecimal.ROUND_HALF_UP));
        //事假
        salary.setPersonalLeave(personalLeave.setScale(2,BigDecimal.ROUND_HALF_UP));
        //缺勤
        salary.setAbsence(absence.setScale(2,BigDecimal.ROUND_HALF_UP));

        //查询绩效
        PerformanceAssessment performanceAssessment = peranceAssmentService.findByYearAndMonthAndStaffId(salary.getYear(), salary.getMonthOfDay(), salary.getStaff().getId());
        if(performanceAssessment != null){
            Double rate = performanceAssessment.getGrade().getRate();
            performance = BigDecimalUtil.multiply(performance,rate);
        }else{
            performance = BigDecimal.ZERO;
        }
        //绩效奖金
        salary.setPerformance(performance.setScale(2,BigDecimal.ROUND_HALF_UP));

        if(salary.getMonthOfDay() == 12){
            AnnualAssessment annualAssessment = annualAssessmentService.findByStaffIdAndYear(salary.getStaff().getId(), salary.getYear());
            if(annualAssessment != null){
                Double rate = annualAssessment.getGradeEnum().getRate();
                annualAssessmentBonus = BigDecimalUtil.multiply(annualAssessmentBonus,rate);
            }else{
                annualAssessmentBonus = BigDecimal.ZERO;
            }
        }
        else{
            annualAssessmentBonus = BigDecimal.ZERO;
        }
        //年度考核奖金
        salary.setAnnualAssessmentBonus(annualAssessmentBonus.setScale(2,BigDecimal.ROUND_HALF_UP));

        //应发工资
        BigDecimal shouldSalary = salary.getBasicWage().add(salary.getFoodSubsidy())
                .add(salary.getPositionSubsidy()).add(salary.getTravelAllowance())
                .add(salary.getMissionAllowance()).add(salary.getYearsBonus())
                .add(salary.getJobTitleBonus()).add(salary.getOverTimeBonus())
                .add(salary.getAttendanceBonus()).add(salary.getLatePenalty())
                .add(salary.getLeaveEarlyFine()).add(salary.getSick())
                .add(salary.getPersonalLeave()).add(salary.getAbsence())
                .add(salary.getAnnualAssessmentBonus()).add(salary.getPerformance());
        //应发工资
        salary.setShouldSalary(shouldSalary.setScale(2,BigDecimal.ROUND_HALF_UP));
        //养老
        BigDecimal annuity = BigDecimalUtil.multiply(shouldSalary,PercentageEnum.ANNUITY.getRate()).setScale(2,BigDecimal.ROUND_HALF_UP);
        salary.setAnnuity(annuity.negate());
        //医疗
        BigDecimal medical = BigDecimalUtil.multiply(shouldSalary,PercentageEnum.MEDICAL.getRate()).setScale(2,BigDecimal.ROUND_HALF_UP);
        salary.setMedical(medical.negate());
        //失业
        BigDecimal unemployment = BigDecimalUtil.multiply(shouldSalary,PercentageEnum.UNEMPLOYMENT.getRate()).setScale(2,BigDecimal.ROUND_HALF_UP);
        salary.setUnemployment(unemployment.negate());
        //工伤
        BigDecimal occupationalInjury = BigDecimalUtil.multiply(shouldSalary,PercentageEnum.OCCUPATIONAL_INJURY.getRate()).setScale(2,BigDecimal.ROUND_HALF_UP);
        salary.setOccupationalInjury(occupationalInjury.negate());
        //生育
        BigDecimal childbirth = BigDecimalUtil.multiply(shouldSalary,PercentageEnum.CHILDBIRTH.getRate()).setScale(2,BigDecimal.ROUND_HALF_UP);
        salary.setChildbirth(childbirth.negate());
        //住房公积金
        BigDecimal housingFund = BigDecimalUtil.multiply(shouldSalary,PercentageEnum.HOUSING_FUND.getRate()).setScale(2,BigDecimal.ROUND_HALF_UP);
        salary.setHousingFund(housingFund.negate());
        //实发工资
        BigDecimal netPayroll = shouldSalary.add(salary.getAnnuity()).add(salary.getMedical()).
                add(salary.getUnemployment()).add(salary.getOccupationalInjury()).add(salary.getChildbirth()).add(salary.getHousingFund());
        //个人所得税
        BigDecimal individualIncomeTax = TaxRateEnum.countTaxRate(netPayroll).setScale(2,BigDecimal.ROUND_HALF_UP);
        salary.setIndividualIncomeTax(individualIncomeTax);
        //实发工资
        salary.setNetPayroll(netPayroll.add(individualIncomeTax));


        return salary;
    }

   /**
     * 发放工资
     * @param id
     * @return
     */
    @RequestMapping(value = "/send",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> send(@RequestParam(name="id",required=true)Long id){
        Salary salary = salaryService.find(id);
        if(salary == null){
            return Result.error(CodeMsg.ADMIN_SALARY_EMPRY);
        }
        if(salary.getIsStatus() == IsStatus.IS_ATTENDANCE){
            return Result.error(CodeMsg.ADMIN_SALRY_STATUS_SEND);
        }
        if(salaryService.updateStatus(IsStatus.IS_ATTENDANCE,id) <= 0){
            return Result.error(CodeMsg.ADMIN_SALARY_SEND_ERROR);
        }
        return Result.success(true);
    }
    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delete(@RequestParam(name="id",required=true)Long id){
        Salary salary = salaryService.find(id);
        if(salary == null){
            return Result.error(CodeMsg.ADMIN_SALARY_EMPRY);
        }
        if(salary.getIsStatus() == IsStatus.IS_ATTENDANCE){
            return Result.error(CodeMsg.ADMIN_SALARY_HAS_SEND);
        }
        try{
            salaryService.delete(id);
        }catch (Exception e){
            return Result.error(CodeMsg.ADMIN_SALARY_DELETE_ERROR);
        }
        return Result.success(true);
    }
}
