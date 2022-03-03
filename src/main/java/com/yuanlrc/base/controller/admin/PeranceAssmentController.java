package com.yuanlrc.base.controller.admin;

import com.yuanlrc.base.bean.CodeMsg;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.Result;
import com.yuanlrc.base.entity.admin.Department;
import com.yuanlrc.base.entity.admin.PerformanceAssessment;
import com.yuanlrc.base.service.admin.DepartmentService;
import com.yuanlrc.base.service.admin.OperaterLogService;
import com.yuanlrc.base.service.admin.PeranceAssmentService;
import com.yuanlrc.base.util.ValidateEntityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 后台绩效考核管理Controller
 */
@Controller
@RequestMapping("/admin/perce_app")
public class PeranceAssmentController {


    @Autowired
    private PeranceAssmentService peranceAssmentService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private OperaterLogService operaterLogService;

    /**
     * 分页查询绩效考核列表
     * @param model
     * @param pageBean
     * @param performanceAssessment
     * @return
     */
    @RequestMapping("/list")
    public String list(Model model, PageBean<PerformanceAssessment> pageBean, PerformanceAssessment performanceAssessment){
        pageBean.setPageSize(2);
        model.addAttribute("title","绩效考核列表");
        model.addAttribute("year",performanceAssessment.getYear()==null?"":performanceAssessment.getYear());
        model.addAttribute("month",performanceAssessment.getMonth()==null?"":performanceAssessment.getMonth());
        model.addAttribute("jobNumber",performanceAssessment.getStaff()==null?"":performanceAssessment.getStaff().getJobNumber());
        model.addAttribute("pageBean",peranceAssmentService.findList(performanceAssessment, pageBean));
        return "/admin/perance_app/list";
    }

}
