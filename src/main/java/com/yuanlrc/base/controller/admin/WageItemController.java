package com.yuanlrc.base.controller.admin;

import com.yuanlrc.base.bean.CodeMsg;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.Result;
import com.yuanlrc.base.entity.admin.Department;
import com.yuanlrc.base.entity.admin.WageItem;
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

import java.util.List;

/**
 * 后台工资项管理Controller
 */
@Controller
@RequestMapping("/admin/wage_item")
public class WageItemController {


    @Autowired
    private OperaterLogService operaterLogService;

    @Autowired
    private WageItemService wageItemService;

    /**
     * 工资项列表
     * @param model
     * @return
     */
    @RequestMapping("/list")
    public String list(Model model){
        model.addAttribute("title","工资项列表");
        WageItem wageItem = wageItemService.findFirstByOrderByCreateTimeDesc();
        if(wageItem==null){
            //表示还没有添加
           return "/admin/wage_item/list";
        }else{
            //表示存在 编辑
            model.addAttribute("wageItem",wageItem);
            return "/admin/wage_item/edit";
        }
    }
    
    /**
     * 工资项添加提交处理
     * @param
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> add(WageItem wageItem){
        //用统一验证实体方法验证是否合法
        CodeMsg validate = ValidateEntityUtil.validate(wageItem);
        if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }
        if(wageItemService.save(wageItem) == null){
            return Result.error(CodeMsg.ADMIN_WAGE_ITEM_ADD_ERROR);
        }
        operaterLogService.add("添加工资项，工资项Id：" + wageItem.getId());
        return Result.success(true);
    }


    /**
     * 工资项编辑
     * @param wageItem
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> edit(WageItem wageItem){
        //用统一验证实体方法验证是否合法
        CodeMsg validate = ValidateEntityUtil.validate(wageItem);
        if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }
        //到这说明一切符合条件，进行数据库保存
        WageItem findById = wageItemService.find(wageItem.getId());
        BeanUtils.copyProperties(wageItem, findById, "id","createTime","updateTime");
        if(wageItemService.save(findById) == null){
            return Result.error(CodeMsg.ADMIN_WAGE_ITEM_EDIT_ERROR);
        }
        operaterLogService.add("编辑工资项，工资项Id：" + wageItem.getId());
        return Result.success(true);
    }
}
