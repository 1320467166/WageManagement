package com.yuanlrc.base.controller.admin;

import com.yuanlrc.base.bean.*;
import com.yuanlrc.base.dao.admin.AttendanceDao;
import com.yuanlrc.base.entity.admin.Attendance;
import com.yuanlrc.base.entity.admin.Staff;
import com.yuanlrc.base.service.admin.AttendanceService;
import com.yuanlrc.base.service.admin.StaffService;
import com.yuanlrc.base.util.DateUtil;
import com.yuanlrc.base.util.ExportExcelUtil;
import com.yuanlrc.base.util.ExportPOIUtils;
import com.yuanlrc.base.util.ValidateEntityUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.persistence.Column;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 考勤Controller
 */
@Controller
@RequestMapping("/admin/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private StaffService staffService;

    @Resource
    private AttendanceDao attendanceDao;

    /**
     * 分页查询列表
     * @param model
     * @param attendance
     * @param pageBean
     * @return
     */
    @RequestMapping("/list")
    public String list(Model model, Attendance attendance, PageBean<Attendance> pageBean,
                       String month){
        model.addAttribute("title","考勤列表");
        model.addAttribute("jobNumber",attendance.getJobNumber());
        model.addAttribute("month",month);
        model.addAttribute("pageBean",attendanceService.findList(attendance,pageBean));
        return "admin/attendance/list";
    }

    /**
     * 个人考勤列表
     * @param model
     * @param pageBean
     * @param attendance
     * @param month
     * @return
     */
    @RequestMapping("/personage_list")
    public String personageList(Model model, PageBean<Attendance> pageBean,Attendance attendance,
                       String month){
        model.addAttribute("title","个人考勤列表");
        model.addAttribute("month",month);
        model.addAttribute("pageBean",attendanceService.findPersonageList(attendance,pageBean));
        return "admin/attendance/personage_list";
    }

    /**
     * 添加页面
     * @return
     */
    @RequestMapping("/add")
    public String add(Model model){
        model.addAttribute("staffList",staffService.findByIsStatus(StaffStatus.ON_THE_JOB.getCode()));
        return "admin/attendance/add";
    }


    /**
     * 添加表单提交
     * @param attendance
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> add(Attendance attendance){
        CodeMsg validate = ValidateEntityUtil.validate(attendance);
        if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }

        if(attendance.getMonth() == null){
            return Result.error(CodeMsg.ADMIN_ATTENDANCE_MONTH_EMPTY);
        }

        if(StringUtils.isEmpty(attendance.getJobNumber())){
            return Result.error(CodeMsg.ADMIN_ATTENDANCE_JOB_NUMBER_EMPTY);
        }


        Staff byJobNumber = staffService.findByJobNumberAndIsStatus(attendance.getJobNumber());
        if(byJobNumber == null){
            return Result.error(CodeMsg.ADMIN_JOB_NUMBER_EMPTY);
        }

        if(attendance.getMonth().getTime() > new Date().getTime()){
            return Result.error(CodeMsg.ADMIN_DATE_ERROR);
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(attendance.getMonth());
        int month = cal.get(Calendar.MONTH)+1;
        int year = cal.get(Calendar.YEAR);

        if(attendanceService.findByJobNumberAndYearAndMonthOfDay(attendance.getJobNumber(),year,month)!= null){
            return Result.error(CodeMsg.ADMIN_ATTENDANCE_MONTH_EXIST);
        }

        int daysByYearMonth = DateUtil.getDaysByYearMonth(year, month);
        if(daysOver(attendance,daysByYearMonth)){
            return Result.error(CodeMsg.ADMIN_DAYS_ERROR);
        }

        if(attendance.getDaysSick() == 0 && attendance.getPersonalLeaveDays() == 0 && attendance.getLateNumber() == 0
            && attendance.getLeaveEarlyTimes() == 0 && attendance.getAbsenceDays() == 0){
            attendance.setIsStatus(IsStatus.IS_ATTENDANCE);
        }

        attendance.setYear(year);
        attendance.setMonthOfDay(month);
        attendance.setLeaveDays(attendance.getDaysSick() + attendance.getPersonalLeaveDays());
        attendance.setStaff(byJobNumber);
        attendance.setDepartment(byJobNumber.getDepartment());

        if(attendanceService.save(attendance) == null){
            return Result.error(CodeMsg.ADMIN_ATTENDANCE_SAVE_ERROR);
        }

        return Result.success(true);
    }

    /**
     * 编辑页面
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Model model,@RequestParam(value = "id",required = true)Long id){
        Attendance attendance = attendanceService.find(id);
        model.addAttribute("attendance",attendance);
        return "admin/attendance/edit";
    }

    /**
     * 编辑表单提交
     * @param attendance
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> edit(Attendance attendance){
        CodeMsg validate = ValidateEntityUtil.validate(attendance);
        if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }

        if(attendance.getId() == null || attendance.getId().longValue() <= 0){
            return Result.error(CodeMsg.ADMIN_ATTENDANCE_NO_EXIST);
        }

        Attendance findById = attendanceService.find(attendance.getId());
        int daysByYearMonth = DateUtil.getDaysByYearMonth(findById.getYear(), findById.getMonthOfDay());
        if(daysOver(attendance,daysByYearMonth)){
            return Result.error(CodeMsg.ADMIN_DAYS_ERROR);
        }

        if(attendance.getDaysSick() == 0 && attendance.getPersonalLeaveDays() == 0 && attendance.getLateNumber() == 0
                && attendance.getLeaveEarlyTimes() == 0 && attendance.getAbsenceDays() == 0){
            attendance.setIsStatus(IsStatus.IS_ATTENDANCE);
        }else{
            attendance.setIsStatus(IsStatus.NOT_ATTENDANCE);
        }
        attendance.setLeaveDays(attendance.getDaysSick() + attendance.getPersonalLeaveDays());
        BeanUtils.copyProperties(attendance,findById,"id","createTime","updateTime","staff","year","monthOfDay","department","jobNumber");

        if(attendanceService.save(findById) == null){
            return Result.error(CodeMsg.ADMIN_ATTENDANCE_EDIT_ERROR);
        }

        return Result.success(true);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delete(@RequestParam(name="id",required=true)Long id){
        try{
            attendanceService.delete(id);
        }catch (Exception e){
            return Result.error(CodeMsg.ADMIN_ATTENDANCE_DELETE_ERROR);
        }
        return Result.success(true);
    }

    /**
     * 判断天数是否超出
     * @param attendance
     * @param daysByYearMonth
     * @return
     */
    public Boolean daysOver(Attendance attendance,int daysByYearMonth){
        if(attendance.getDaysSick() + attendance.getPersonalLeaveDays() + attendance.getAbsenceDays() +
                attendance.getTravelDays() > daysByYearMonth){
            return true;
        }
        return false;
    }

    /**
     * 导入
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/import",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> importAttendance(@RequestParam("file") MultipartFile file) throws Exception {
        List<Attendance> attendanceList = new ArrayList<>();
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
        InputStream ins = file.getInputStream();

        //判断是否是xlsx文件或xls
        if (!suffix.equals("xlsx")&&!suffix.equals("xls")){
            return Result.error(CodeMsg.ADMIN_ATTENDANCE_IMPORT_ADD_ERROR);
        }

        Workbook workbook = WorkbookFactory.create(ins);
        //获取Excel
        Sheet sheet = workbook.getSheetAt(0);
        if(sheet == null)
            return Result.error(CodeMsg.ADMIN_ATTENDANCE_IMPORT_ADD_ERROR);

        String jobNumber = "";
        String month = "";
        String daysSick = "";
        String personalLeaveDays = "";
        String overtimeHours = "";
        String overtimeOnWeekends = "";
        String holidayOvertimeHours = "";
        String lateNumber = "";
        String leaveEarlyTimes = "";
        String absenceDays = "";
        String travelDays = "";

        for (int line = 1;line<=sheet.getLastRowNum();line++){
            Attendance attendance = new Attendance();
            Row row = sheet.getRow(line);

            //如果没有这一行跳过
            if(row == null)
                continue;

            try
            {
                row.getCell(0).setCellType(CellType.STRING);
                jobNumber = row.getCell(0).getStringCellValue();
                row.getCell(1).setCellType(CellType.STRING);
                month = row.getCell(1).getStringCellValue();
                row.getCell(2).setCellType(CellType.STRING);
                daysSick = row.getCell(2).getStringCellValue();
                row.getCell(3).setCellType(CellType.STRING);
                personalLeaveDays = row.getCell(3).getStringCellValue();
                row.getCell(4).setCellType(CellType.STRING);
                overtimeHours = row.getCell(4).getStringCellValue();
                row.getCell(5).setCellType(CellType.STRING);
                overtimeOnWeekends = row.getCell(5).getStringCellValue();
                row.getCell(6).setCellType(CellType.STRING);
                holidayOvertimeHours = row.getCell(6).getStringCellValue();
                row.getCell(7).setCellType(CellType.STRING);
                lateNumber = row.getCell(7).getStringCellValue();
                row.getCell(8).setCellType(CellType.STRING);
                leaveEarlyTimes = row.getCell(8).getStringCellValue();
                row.getCell(9).setCellType(CellType.STRING);
                absenceDays = row.getCell(9).getStringCellValue();
                row.getCell(10).setCellType(CellType.STRING);
                travelDays = row.getCell(10).getStringCellValue();
            }catch (Exception e)
            {
                return Result.error(CodeMsg.ADMIN_ATTENDANCE_IMPORT_EXCEL_ERROR);
            }

            if(StringUtils.isEmpty(jobNumber) || StringUtils.isEmpty(month)
                    || StringUtils.isEmpty(daysSick) || StringUtils.isEmpty(personalLeaveDays)
                    || StringUtils.isEmpty(overtimeHours)||StringUtils.isEmpty(overtimeOnWeekends)
                    || StringUtils.isEmpty(holidayOvertimeHours)
                    || StringUtils.isEmpty(lateNumber) || StringUtils.isEmpty(leaveEarlyTimes)
                    || StringUtils.isEmpty(absenceDays)|| StringUtils.isEmpty(travelDays))
                return Result.error(CodeMsg.ADMIN_IMPORT_EXCEL_NULL_ERROR);

            Date setupTime = HSSFDateUtil.getJavaDate(Double.valueOf(month));

            Calendar cal = Calendar.getInstance();
            cal.setTime(setupTime);
            int monthOfDay = cal.get(Calendar.MONTH)+1;
            int year = cal.get(Calendar.YEAR);


            Staff byJobNumber = staffService.findByJobNumberAndIsStatus(jobNumber);
            if(byJobNumber == null){
                CodeMsg codeMsg = CodeMsg.ADMIN_JOB_NUMBER_EMPTY;
                codeMsg.setMsg("Excel第" + line + "行"+codeMsg.getMsg());
                return Result.error(codeMsg);
            }

            Date date= new Date();

            Date setDate = DateUtil.setDate(date, year, monthOfDay, 0, 0, 0, 0);

            if(setDate.getTime() > date.getTime()){
                CodeMsg codeMsg = CodeMsg.ADMIN_IMPORT_DATE_ERROR;
                codeMsg.setMsg("Excel第" + line + "行"+codeMsg.getMsg());
                return Result.error(codeMsg);
            }

            if(attendanceService.findByJobNumberAndYearAndMonthOfDay(jobNumber,year, monthOfDay)!= null){
                CodeMsg codeMsg = CodeMsg.ADMIN_ATTENDANCE_MONTH_EXIST;
                codeMsg.setMsg("Excel第" + line + "行"+codeMsg.getMsg());
                return Result.error(codeMsg);
            }
            double sick = 0;
            double personal = 0;
            double overtime = 0;
            double overtimeWeekends = 0;
            double holidayOvertime = 0;
            int later = 0;
            int leaveEarly = 0;
            double absence = 0;
            double travel = 0;

            try{
                sick = Double.parseDouble(daysSick);
                personal = Double.parseDouble(personalLeaveDays);
                overtime = Double.parseDouble(overtimeHours);
                overtimeWeekends = Double.parseDouble(overtimeOnWeekends);
                holidayOvertime = Double.parseDouble(holidayOvertimeHours);
                later = Integer.parseInt(lateNumber);
                leaveEarly = Integer.parseInt(leaveEarlyTimes);
                absence = Double.parseDouble(absenceDays);
                travel = Double.parseDouble(travelDays);
            }catch (Exception e){
                return  Result.error(CodeMsg.NUMBER_TYPE_ERROR);
            }

            attendance.setJobNumber(jobNumber);
            attendance.setYear(year);
            attendance.setMonthOfDay(monthOfDay);
            attendance.setDaysSick(sick);
            attendance.setPersonalLeaveDays(personal);
            attendance.setOvertimeHours(overtime);
            attendance.setOvertimeOnWeekends(overtimeWeekends);
            attendance.setHolidayOvertimeHours(holidayOvertime);
            attendance.setLateNumber(later);
            attendance.setLeaveEarlyTimes(leaveEarly);
            attendance.setAbsenceDays(absence);
            attendance.setTravelDays(travel);
            attendance.setStaff(byJobNumber);
            attendance.setDepartment(byJobNumber.getDepartment());

            CodeMsg validate = ValidateEntityUtil.validate(attendance);
            if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
                return Result.error(validate);
            }

            attendance.setLeaveDays(attendance.getDaysSick() + attendance.getPersonalLeaveDays());

            int daysByYearMonth = DateUtil.getDaysByYearMonth(year, monthOfDay);
            if(daysOver(attendance,daysByYearMonth)){
                CodeMsg codeMsg = CodeMsg.ADMIN_DAYS_ERROR;
                codeMsg.setMsg("Excel第" + line + "行"+codeMsg.getMsg());
                return Result.error(codeMsg);
            }

            if(attendance.getDaysSick() == 0 && attendance.getPersonalLeaveDays() == 0 && attendance.getLateNumber() == 0
                    && attendance.getLeaveEarlyTimes() == 0 && attendance.getAbsenceDays() == 0){
                attendance.setIsStatus(IsStatus.IS_ATTENDANCE);
            }

            attendanceList.add(attendance);
        }

        attendanceList = attendanceList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(
                () -> new TreeSet<>(Comparator.comparing(f -> f.getJobNumber() + f.getYear() + f.getMonthOfDay()))), ArrayList::new));

        try
        {
            attendanceService.saveAll(attendanceList);
        }catch (Exception e)
        {
            return Result.error(CodeMsg.ADMIN_ATTENDANCE_LIST_ADD_ERROR);
        }

        return Result.success(true);
    }

    @RequestMapping(value = "/export",method = RequestMethod.GET)
    public void export(HttpServletResponse response) throws Exception {
        List<Attendance> list = attendanceDao.query();
        String fileName = "考勤表";
        // 列名
        String columnNames[] = {"员工姓名","年份", "月份", "病假天数", "事假天数", "平时加班", "周末加班",
                "节假日加班", "迟到次数", "早退次数","缺勤天数", "出差天数", "请假天数"};
        // map中的key
        String keys[] = {"name","year", "monthOfDay", "daysSick", "personalLeaveDays", "overtimeHours", "overtimeOnWeekends",
                "holidayOvertimeHours", "lateNumber", "leaveEarlyTimes", "absenceDays", "travelDays","leaveDays"};
        try {
            ExportPOIUtils.start_download(response, fileName, list, columnNames, keys);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
