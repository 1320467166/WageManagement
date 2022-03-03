package com.yuanlrc.base.controller.admin;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yuanlrc.base.bean.*;
import com.yuanlrc.base.entity.admin.AssessTarget;
import com.yuanlrc.base.entity.admin.Department;
import com.yuanlrc.base.entity.admin.JobTitle;
import com.yuanlrc.base.entity.admin.WageItem;
import com.yuanlrc.base.service.admin.AssessTargetService;
import com.yuanlrc.base.service.admin.DepartmentService;
import com.yuanlrc.base.service.admin.OperaterLogService;
import com.yuanlrc.base.service.admin.WageItemService;
import com.yuanlrc.base.util.ValidateEntityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 后台指标管理Controller
 */
@Controller
@RequestMapping("/admin/assess_target")
public class AssessTargetController {


    @Autowired
    private OperaterLogService operaterLogService;

    @Autowired
    private AssessTargetService assessTargetService;

    @Autowired
    private DepartmentService departmentService;

    /**
     * 指标列表
     * @param model
     * @return
     */
    @RequestMapping("/list")
    public String list(Model model, PageBean<AssessTarget> pageBean, AssessTarget assessTarget){
        model.addAttribute("title","指标列表");
        model.addAttribute("name",assessTarget.getName());
        model.addAttribute("pageBean",assessTargetService.findList(assessTarget,pageBean));
        return "admin/assess_target/list";
    }
    /**
     * 新增指标页面
     * @param model
     * @return
     */
    @RequestMapping(value="/add",method=RequestMethod.GET)
    public String add(Model model){
        model.addAttribute("departmentList",departmentService.findAll());
        model.addAttribute("targetClassifyList",TargetClassify.values());
        return "admin/assess_target/add";
    }
    /**
     * 指标添加提交处理
     * @param assessTarget
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> add(AssessTarget assessTarget){
        //用统一验证实体方法验证是否合法
        CodeMsg validate = ValidateEntityUtil.validate(assessTarget);
        if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }
        if(assessTarget.getTargetClassify().getCode()==-1){
            return Result.error(CodeMsg.ADMIN_ASSESS_TARGET_CATEGORY_ERROR);
        }
        if(assessTargetService.isExistName(assessTarget.getName(),0l)){
            return Result.error(CodeMsg.ADMIN_ASSESS_TARGET_NAME_EXIST_ERROR);
        }
        if(assessTargetService.save(assessTarget) == null){
            return Result.error(CodeMsg.ADMIN_ASSESS_TARGET_ADD_ERROR);
        }
        operaterLogService.add("添加新指标，指标名称：" + assessTarget.getName());
        return Result.success(true);
    }
    /**
     * 指标编辑页面
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Model model,@RequestParam(name="id",required=true)Long id){
        AssessTarget assessTarget = assessTargetService.find(id);
        model.addAttribute("assessTarget",assessTarget);
        model.addAttribute("targetClassifyList",TargetClassify.values());
        model.addAttribute("departmentList",departmentService.findAll());
        List<Department> departments = assessTarget.getDepartments();
        List<Long> departmentIds = departments.stream().map(Department::getId).collect(Collectors.toList());
        String departmentIdList = JSONArray.toJSONString(departmentIds);
        model.addAttribute("departments",departmentIdList);
        model.addAttribute("departmentIds",departmentIds);
        return "/admin/assess_target/edit";
    }

    /**
     * 指标编辑表单提交处理
     * @param assessTarget
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> edit(AssessTarget assessTarget){
        //用统一验证实体方法验证是否合法
        CodeMsg validate = ValidateEntityUtil.validate(assessTarget);
        if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }
        if(assessTarget.getTargetClassify().getCode()==-1){
            return Result.error(CodeMsg.ADMIN_ASSESS_TARGET_CATEGORY_ERROR);
        }
        if(assessTargetService.isExistName(assessTarget.getName(),assessTarget.getId())){
            return Result.error(CodeMsg.ADMIN_ASSESS_TARGET_NAME_EXIST_ERROR);
        }
        //到这说明一切符合条件，进行数据库保存
        AssessTarget findById = assessTargetService.find(assessTarget.getId());
        //讲提交的用户信息指定字段复制到已存在的department对象中,该方法会覆盖新字段内容
        BeanUtils.copyProperties(assessTarget, findById, "id","createTime","updateTime");
        if(assessTargetService.save(findById) == null){
            return Result.error(CodeMsg.ADMIN_ASSESS_TARGET_EDIT_ERROR);
        }
        operaterLogService.add("编辑指标，指标名：" + assessTarget.getName());
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
            assessTargetService.delete(id);
        }catch (Exception e){
            return Result.error(CodeMsg.ADMIN_ASSESS_TARGET_DELETE_ERROR);
        }
        operaterLogService.add("删除指标，指标ID：" + id);
        return Result.success(true);
    }
}
