package com.yuanlrc.base.controller.admin;

import com.yuanlrc.base.bean.CodeMsg;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.Result;
import com.yuanlrc.base.bean.StaffStatus;
import com.yuanlrc.base.entity.admin.AnnualAssessment;
import com.yuanlrc.base.entity.admin.Staff;
import com.yuanlrc.base.service.admin.AnnualAssessmentService;
import com.yuanlrc.base.service.admin.AttendanceService;
import com.yuanlrc.base.service.admin.PeranceAssmentService;
import com.yuanlrc.base.service.admin.StaffService;
import com.yuanlrc.base.util.ValidateEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * 年度考核Controller
 */
@Controller
@RequestMapping("/admin/annual_assessment")
public class AnnualAssessmentController {

    @Autowired
    private AnnualAssessmentService annualAssessmentService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private PeranceAssmentService peranceAssmentService;

    /**
     * 部门年度考核列表
     * @param model
     * @param pageBean
     * @param jobNumber
     * @param year
     * @return
     */
    @RequestMapping("/list")
    public String list(Model model, PageBean<AnnualAssessment> pageBean,String jobNumber,String year){
        model.addAttribute("title","年度考核列表");
        model.addAttribute("jobNumber",jobNumber);
        model.addAttribute("year",year);
        model.addAttribute("pageBean",annualAssessmentService.findList(pageBean,jobNumber,year));
        return "admin/annual_assessment/list";
    }

    /**
     * 个人年度考核列表
     * @param model
     * @param pageBean
     * @param year
     * @return
     */
    @RequestMapping("/personage_list")
    public String personageList(Model model, PageBean<AnnualAssessment> pageBean,String year){
        model.addAttribute("title","个人年度考核列表");
        model.addAttribute("year",year);
        model.addAttribute("pageBean",annualAssessmentService.findPersonageList(pageBean,year));
        return "admin/annual_assessment/personage_list";
    }

    /**
     * 判断是否是一月
     * @return
     */
    @RequestMapping(value = "/is_jan",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> isJan(){
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int monthOfDay = cal.get(Calendar.MONTH)+1;

        /*if(monthOfDay != 1){
            return Result.error(CodeMsg.ADMIN_MONTH_ERROR);
        }*/
        return Result.success(true);
    }

    /**
     * 添加年度考核
     * @param annualAssessment
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> add(AnnualAssessment annualAssessment,Long staffId){
        if(staffId == null || staffId.longValue() <= 0){
            return Result.error(CodeMsg.ADMIN_STAFF_NOT_EXIST_ERROR);
        }
        Staff staff = staffService.findByIdAndIsStatus(staffId, StaffStatus.ON_THE_JOB.getCode());
        if(staff == null){
            return Result.error(CodeMsg.ADMIN_STAFF_NOT_EXIST_ERROR);
        }

        CodeMsg validate = ValidateEntityUtil.validate(annualAssessment);
        if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //获取去年的年份
        int year = cal.get(Calendar.YEAR) - 1;

        if(annualAssessmentService.findByStaffIdAndYear(staffId,year) != null){
            return Result.error(CodeMsg.ANNUAL_YEAR_EXIST);
        }

        if(annualAssessmentService.save(annualAssessment,staff,year) == null){
            return Result.error(CodeMsg.ADMIN_ANNUAL_ASSESSMENT_ERROR);
        }

        return Result.success(true);
    }

}
