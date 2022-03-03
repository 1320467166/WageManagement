package com.yuanlrc.base.controller.admin;

import com.yuanlrc.base.bean.CodeMsg;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.Result;
import com.yuanlrc.base.entity.admin.Department;
import com.yuanlrc.base.entity.admin.WorkingYears;
import com.yuanlrc.base.service.admin.DepartmentService;
import com.yuanlrc.base.service.admin.OperaterLogService;
import com.yuanlrc.base.service.admin.WorkingYearsService;
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
 * 后台工龄管理Controller
 */
@Controller
@RequestMapping("/admin/work_years")
public class WorkingYearsController {

    @Autowired
    private WorkingYearsService workingYearsService;

    @Autowired
    private OperaterLogService operaterLogService;

    /**
     * 分页查询工龄列表
     * @param model
     * @param pageBean
     * @param workingYears
     * @return
     */
    @RequestMapping("/list")
    public String list(Model model, PageBean<WorkingYears> pageBean, WorkingYears workingYears){
        model.addAttribute("title","工龄列表");
        model.addAttribute("years",workingYears.getYears());
        model.addAttribute("pageBean",workingYearsService.findList(workingYears, pageBean));
        return "/admin/working_years/list";
    }

    /**
     * 添加页面
     * @return
     */
    @RequestMapping("/add")
    public String add(){
        return "/admin/working_years/add";
    }

    /**
     * 工龄添加提交处理
     * @param workingYears
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> add(WorkingYears workingYears){
        //用统一验证实体方法验证是否合法
        CodeMsg validate = ValidateEntityUtil.validate(workingYears);
        if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }
        if(workingYearsService.findByYears(workingYears.getYears())!=null){
            return Result.error(CodeMsg.ADMIN_WORKING_YEARS_EXIST_ERROR);
        }
        if(workingYearsService.save(workingYears) == null){
            return Result.error(CodeMsg.ADMIN_WORKING_YEARS_ADD_ERROR);
        }
        operaterLogService.add("添加工龄，工龄补贴为：" + workingYears.getSubsidy());
        return Result.success(true);
    }

    /**
     * 编辑页面
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Model model,@RequestParam(name="id",required=true)Long id){
        model.addAttribute("workYears",workingYearsService.find(id));
        return "/admin/working_years/edit";
    }

    /**
     * 编辑表单提交处理
     * @param workingYears
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> edit(WorkingYears workingYears){
        //用统一验证实体方法验证是否合法
        CodeMsg validate = ValidateEntityUtil.validate(workingYears);
        if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }
        if(workingYearsService.isExistYear(workingYears.getYears(),workingYears.getId())){
            return Result.error(CodeMsg.ADMIN_WORKING_YEARS_EXIST_ERROR);
        }
        //到这说明一切符合条件，进行数据库保存
        WorkingYears findById = workingYearsService.find(workingYears.getId());
        //讲提交的用户信息指定字段复制到已存在的department对象中,该方法会覆盖新字段内容
        BeanUtils.copyProperties(workingYears, findById, "id","createTime","updateTime");
        if(workingYearsService.save(findById) == null){
            return Result.error(CodeMsg.ADMIN_WORKING_YEARS_EDIT_ERROR);
        }
        operaterLogService.add("编辑工龄，工龄补贴为:" + workingYears.getSubsidy());
        return Result.success(true);
    }

    /**
     * 工龄删除操作
     * @param id
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delete(@RequestParam(name="id",required=true)Long id){
        try{
            workingYearsService.delete(id);
        }catch (Exception e){
            return Result.error(CodeMsg.ADMIN_WORKING_YEARS_DELETE_ERROR);
        }
        operaterLogService.add("删除工龄补贴，工龄ID：" + id);
        return Result.success(true);
    }
}
