package com.yuanlrc.base.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.sun.javafx.collections.MappingChange;
import com.yuanlrc.base.bean.*;
import com.yuanlrc.base.entity.admin.AssessTarget;
import com.yuanlrc.base.entity.admin.Department;
import com.yuanlrc.base.entity.admin.PerformanceAssessment;
import com.yuanlrc.base.entity.admin.Staff;
import com.yuanlrc.base.service.admin.*;
import com.yuanlrc.base.util.SessionUtil;
import com.yuanlrc.base.util.ValidateEntityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 后台考核管理Controller
 */
@Controller
@RequestMapping("/admin/assessment")
public class AssessmentController {

    @Autowired
    private DepartmentService departmentService;


    @Autowired
    private StaffService staffService;

    @Autowired
    private PeranceAssmentService peranceAssmentService;

    @Autowired
    private AssessTargetService assessTargetService;

    @Autowired
    private AttendanceService attendanceService;


    /**
     * 分页查询员工列表
     * @param model
     * @param pageBean
     * @param staff
     * @return
     */
    @RequestMapping("/list")
    public String list(Model model, PageBean<Staff> pageBean, Staff staff){
        model.addAttribute("title","考核列表");
        Staff loginedStaff = SessionUtil.getLoginedStaff();
        model.addAttribute("jobNumber",staff.getJobNumber());
        model.addAttribute("pageBean",staffService.findDepartmentList(staff, pageBean,loginedStaff));
        return "/admin/assessment/list";
    }

    /**
     * 绩效考核页面
     * @return
     */
    @RequestMapping("/achieve_add")
    public String add(@RequestParam("id")Long id,Model model){
        Staff staff = staffService.find(id);
        Long departmentId = staff.getDepartment().getId();
        Department department = departmentService.find(departmentId);
        List<AssessTarget> assessTargets = department.getAssessTargets();
        model.addAttribute("staff",staff);
        model.addAttribute("assessTargetList",assessTargets);
        return "/admin/assessment/add";
    }

    /**
     * 绩效考核添加提交处理
     * @param performanceAssessment
     * @return
     */
    @RequestMapping(value = "/achieve_add",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> add(PerformanceAssessment performanceAssessment,@RequestParam("assTarget")String assTarget){
        //判断员工是否存在
        Staff staff = performanceAssessment.getStaff();
        if(staff==null){
           return Result.error(CodeMsg.ADMIN_STAFF_NOT_EXIST_ERROR);
       }
       //年份判断
        Integer year = performanceAssessment.getYear();
        if(year<=0){
           return Result.error(CodeMsg.ADMIN_PERFORMANCE_YEAR_ERROR);
       }
       //月份判断
        Integer month = performanceAssessment.getMonth();
        if(month<=0){
           return Result.error(CodeMsg.ADMIN_PERFORMANCE_MONTH_ERROR);
       }
        PerformanceAssessment assessment = peranceAssmentService.findByYearAndMonthAndStaffId(year, month,staff.getId());
        if(assessment!=null){
            return Result.error(CodeMsg.ADMIN_PERFORMANCE_EXIST_ERROR);
        }
        JSONArray array = JSONObject.parseArray(assTarget);
        double totalScore=0;
        double weight=0;
        for(int i=0;i<array.size();i++){
            Object  assessmentTarget =  array.getJSONObject(i);
            Object targetId = ((com.alibaba.fastjson.JSONObject) assessmentTarget).get("targetId");
            Object currentScore = ((com.alibaba.fastjson.JSONObject) assessmentTarget).get("score");
            String idl = targetId.toString();
            AssessTarget assessTarget = assessTargetService.find(Long.valueOf(idl));
            Integer targetScore = assessTarget.getScore();
            String score = currentScore.toString();
            totalScore += Double.parseDouble(score) *targetScore.doubleValue();
            weight+=targetScore.doubleValue();
        }
        double finalScore = totalScore / weight;
        performanceAssessment.setPercentage(new BigDecimal(finalScore));
        GradeEnum gradeEnum = GradeEnum.countGrade(finalScore);
        performanceAssessment.setGrade(gradeEnum);
        if(peranceAssmentService.save(performanceAssessment)==null){
            return Result.error(CodeMsg.ADMIN_PERFORMANCE_ADD_ERROR);
        }
        return Result.success(true);
    }

    /**
     * 添加年度考核
     * @param id
     * @return
     */
    @RequestMapping("/annual_add")
    public String annualAdd(Model model,@RequestParam(name="id",required=true)Long id){
        Staff staff = staffService.findByIdAndIsStatus(id, StaffStatus.ON_THE_JOB.getCode());
        if(staff == null){
            return "redirect:list";
        }
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //获取去年的年份
        int year = cal.get(Calendar.YEAR) - 1;

        //请假天数
        Double leaveDays = attendanceService.sumLeaveDays(staff.getJobNumber(), year);
        //加班小时
        Double overTimeHours = attendanceService.sumOverTimeHours(staff.getJobNumber(), year);
        //缺勤天数
        Double absenceDays = attendanceService.sumAbsenceDays(staff.getJobNumber(), year);

        //总数量
        Integer count = peranceAssmentService.count(staff.getId(), year);
        BigDecimal performanceScore = BigDecimal.ZERO;

        if(count != 0){
            performanceScore = peranceAssmentService.sumPercentage(id, year);
            performanceScore = performanceScore.divide(new BigDecimal(count));
            performanceScore.setScale(2,BigDecimal.ROUND_HALF_UP);
        }

        model.addAttribute("staff",staff);
        model.addAttribute("year",year);
        model.addAttribute("leaveDays",leaveDays);
        model.addAttribute("overTimeHours",overTimeHours);
        model.addAttribute("absenceDays",absenceDays);
        model.addAttribute("performanceScore",performanceScore);
        return "admin/assessment/annual_add";
    }


}
