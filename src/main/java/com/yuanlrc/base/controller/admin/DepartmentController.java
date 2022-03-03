package com.yuanlrc.base.controller.admin;

import com.yuanlrc.base.bean.CodeMsg;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.Result;
import com.yuanlrc.base.entity.admin.Department;
import com.yuanlrc.base.service.admin.DepartmentService;
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

import javax.xml.ws.Action;

/**
 * 后台部门管理Controller
 */
@Controller
@RequestMapping("/admin/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private OperaterLogService operaterLogService;

    /**
     * 分页查询部门列表
     * @param model
     * @param pageBean
     * @param department
     * @return
     */
    @RequestMapping("/list")
    public String list(Model model, PageBean<Department> pageBean,Department department){
        model.addAttribute("title","部门列表");
        model.addAttribute("name",department.getName());
        model.addAttribute("pageBean",departmentService.findList(department, pageBean));
        return "/admin/department/list";
    }

    /**
     * 添加页面
     * @return
     */
    @RequestMapping("/add")
    public String add(){
        return "/admin/department/add";
    }

    /**
     * 部门添加提交处理
     * @param department
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> add(Department department){

        //用统一验证实体方法验证是否合法
        CodeMsg validate = ValidateEntityUtil.validate(department);
        if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }

        if(department.getNote().length() > 50){
            return Result.error(CodeMsg.DEPARTMENT_LENGTH_EXIST);
        }

        if(departmentService.isExistName(department.getName(),0l)){
            return Result.error(CodeMsg.ADMIN_DEPARTMENT_NAME_EXIST);
        }

        if(departmentService.save(department) == null){
            return Result.error(CodeMsg.ADMIN_DEPARTMENT_SAVE_ERROR);
        }
        operaterLogService.add("添加部门，部门名：" + department.getName());

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
        model.addAttribute("department",departmentService.find(id));
        return "/admin/department/edit";
    }

    /**
     * 编辑表单提交处理
     * @param department
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> edit(Department department){
        //用统一验证实体方法验证是否合法
        CodeMsg validate = ValidateEntityUtil.validate(department);
        if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }

        if(department.getId() == null || department.getId().longValue() <= 0){
            return Result.error(CodeMsg.ADMIN_DEPARTMENT_NO_EXIST);
        }

        if(department.getNote().length() > 50){
            return Result.error(CodeMsg.DEPARTMENT_LENGTH_EXIST);
        }

        if(departmentService.isExistName(department.getName(),department.getId())){
            return Result.error(CodeMsg.ADMIN_DEPARTMENT_NAME_EXIST);
        }
        //到这说明一切符合条件，进行数据库保存
        Department findById = departmentService.find(department.getId());
        //讲提交的用户信息指定字段复制到已存在的department对象中,该方法会覆盖新字段内容
        BeanUtils.copyProperties(department, findById, "id","createTime","updateTime");

        if(departmentService.save(findById) == null){
            return Result.error(CodeMsg.ADMIN_DEPARTMENT_EDIT_ERROR);
        }

        operaterLogService.add("编辑部门，部门名：" + department.getName());
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
            departmentService.delete(id);
        }catch (Exception e){
            return Result.error(CodeMsg.ADMIN_DEPARTMENT_DELETE_ERROR);
        }
        operaterLogService.add("删除部门，部门ID：" + id);
        return Result.success(true);
    }
}
