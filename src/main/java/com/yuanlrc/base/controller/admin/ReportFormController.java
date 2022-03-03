package com.yuanlrc.base.controller.admin;

import com.sun.javafx.collections.MappingChange;
import com.yuanlrc.base.bean.CodeMsg;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.Result;
import com.yuanlrc.base.bean.StaffStatus;
import com.yuanlrc.base.entity.admin.Department;
import com.yuanlrc.base.service.admin.DepartmentService;
import com.yuanlrc.base.service.admin.OperaterLogService;
import com.yuanlrc.base.service.admin.SalaryService;
import com.yuanlrc.base.service.admin.StaffService;
import com.yuanlrc.base.util.DateUtil;
import com.yuanlrc.base.util.ValidateEntityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 后台报表管理Controller
 */
@Controller
@RequestMapping("/admin/report_form")
public class ReportFormController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private OperaterLogService operaterLogService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private SalaryService salaryService;



    /**
     * 报表页面
     * @return
     */
    @RequestMapping("/list")
    public String list(Model model,@RequestParam(name = "years",defaultValue = "",required = false)Integer years,@RequestParam(name = "months",defaultValue = "",required = false)Integer months){
        model.addAttribute("title","报表列表");
        List<Object> countDepartment = staffService.findCountDepartment(StaffStatus.ON_THE_JOB.getCode());
        List<Object> avgDepartment = staffService.findAvgDepartment(StaffStatus.ON_THE_JOB.getCode());
        int currentYear = DateUtil.getCurrentYear();
        int currentMonth = DateUtil.getCurrentMonth();
        if(years==null){
            years=currentYear;
        }
        if(months==null){
            months=currentMonth;
        }
        List<Object> salary = salaryService.sumByDepartmentByYearAndMonth(years,months);
        List<Object> countPayRoll = salaryService.countPayRollDepartmentByYear(years);
        Map<String, Object> ret = new HashMap<>();
        ret.put("months",months);
        ret.put("years",years);
        ret.put("salaryList",salary);
        ret.put("avgDepartments",avgDepartment);
        ret.put("countDepartments",countDepartment);
        ret.put("countPayRoll",countPayRoll);
        model.addAllAttributes(ret);
        return "/admin/report_form/list";
    }

}
