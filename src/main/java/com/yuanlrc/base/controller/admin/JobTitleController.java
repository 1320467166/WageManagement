package com.yuanlrc.base.controller.admin;

import com.yuanlrc.base.bean.CodeMsg;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.Result;
import com.yuanlrc.base.entity.admin.JobTitle;
import com.yuanlrc.base.service.admin.JobTitleService;
import com.yuanlrc.base.service.admin.OperaterLogService;
import com.yuanlrc.base.util.ValidateEntityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.awt.ModalityListener;

/**
 * 职称Controller
 */
@Controller
@RequestMapping("/admin/job_title")
public class JobTitleController {

    @Autowired
    private JobTitleService jobTitleService;

    @Autowired
    private OperaterLogService operaterLogService;

    /**
     * 分页查询职称列表
     * @param model
     * @param pageBean
     * @param jobTitle
     * @return
     */
    @RequestMapping("/list")
    public String list(Model model, PageBean<JobTitle> pageBean,JobTitle jobTitle){
        model.addAttribute("title","职称列表");
        model.addAttribute("name",jobTitle.getName());
        model.addAttribute("pageBean",jobTitleService.findList(jobTitle,pageBean));
        return "admin/job_title/list";
    }

    /**
     * 添加页面
     * @return
     */
    @RequestMapping("/add")
    public String add(){
        return "admin/job_title/add";
    }

    /**
     * 添加表单提交处理
     * @param jobTitle
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> add(JobTitle jobTitle){

        //统一验证法验证
        CodeMsg validate = ValidateEntityUtil.validate(jobTitle);
        if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }

        //判断是否已有该名称
        if(jobTitleService.isExistName(jobTitle.getName(),0l)){
            return Result.error(CodeMsg.ADMIN_JOB_TITLE_NAME_EXIST);
        }

        if(jobTitleService.save(jobTitle) == null){
            return Result.error(CodeMsg.ADMIN_JOB_TITLE_SAVE_ERROR);
        }
        operaterLogService.add("添加职称，职称名：" + jobTitle.getName());
        return Result.success(true);
    }

    /**
     * 编辑页面
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Model model, @RequestParam(value = "id",required = true)Long id){
        model.addAttribute("jobTitle",jobTitleService.find(id));
        return "admin/job_title/edit";
    }

    /**
     * 编辑表单提交处理
     * @param jobTitle
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> edit(JobTitle jobTitle){
        //统一验证法验证
        CodeMsg validate = ValidateEntityUtil.validate(jobTitle);
        if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }

        if(jobTitle.getId() == null || jobTitle.getId().longValue() <= 0){
            return Result.error(CodeMsg.ADMIN_JOB_TITLE_NO_EXIST);
        }

        //判断是否已有该名称
        if(jobTitleService.isExistName(jobTitle.getName(),jobTitle.getId())){
            return Result.error(CodeMsg.ADMIN_JOB_TITLE_NAME_EXIST);
        }

        JobTitle findById = jobTitleService.find(jobTitle.getId());

        BeanUtils.copyProperties(jobTitle,findById,"id","createTime","updateTime");

        if(jobTitleService.save(findById) == null){
            return Result.error(CodeMsg.ADMIN_JOB_TITLE_EDIT_ERROR);
        }
        operaterLogService.add("编辑职称，职称名：" + jobTitle.getName());
        return Result.success(true);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delete(@RequestParam(name="id",required=true)Long id){
        try{
            jobTitleService.delete(id);
        }catch (Exception e){
            return Result.error(CodeMsg.ADMIN_DEPARTMENT_DELETE_ERROR);
        }
        operaterLogService.add("删除职称，职称ID：" + id);
        return Result.success(true);
    }

}
