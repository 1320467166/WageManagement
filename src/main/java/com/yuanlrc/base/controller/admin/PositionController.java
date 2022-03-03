package com.yuanlrc.base.controller.admin;

import com.yuanlrc.base.bean.CodeMsg;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.Result;
import com.yuanlrc.base.entity.admin.Position;
import com.yuanlrc.base.service.admin.OperaterLogService;
import com.yuanlrc.base.service.admin.PositionService;
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
 * 后台岗位管理Controller
 */
@RequestMapping("/admin/position")
@Controller
public class PositionController {

    @Autowired
    private PositionService positionService;

    @Autowired
    private OperaterLogService operaterLogService;

    /**
     * 分页查询岗位列表
     * @param model
     * @param pageBean
     * @param position
     * @return
     */
    @RequestMapping("/list")
    public String list(Model model, PageBean<Position> pageBean,Position position){
        model.addAttribute("title","岗位列表");
        model.addAttribute("name",position.getName());
        model.addAttribute("pageBean",positionService.findList(position,pageBean));
        return "admin/position/list";
    }

    /**
     * 添加页面
     * @return
     */
    @RequestMapping("/add")
    public String add(){
        return "admin/position/add";
    }

    /**
     * 添加表单提交
     * @param position
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> add(Position position){
        //统一验证法验证
        CodeMsg validate = ValidateEntityUtil.validate(position);
        if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }
        if(position.getNote().length() > 50){
            return Result.error(CodeMsg.POSITION_LENGTH_EXIST);
        }
        if(positionService.isExistName(position.getName(),0l)){
            return Result.error(CodeMsg.ADMIN_POSITION_NAME_EXIST);
        }
        if(positionService.save(position) == null){
            return Result.error(CodeMsg.ADMIN_POSITION_SAVE_ERROR);
        }
        operaterLogService.add("添加岗位，岗位名：" + position.getName());
        return Result.success(true);
    }

    /**
     * 编辑页面
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Model model, @RequestParam("id")Long id){
        model.addAttribute("position",positionService.find(id));
        return "admin/position/edit";
    }

    /**
     * 编辑提交处理
     * @param position
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> edit(Position position){

        //统一验证法验证
        CodeMsg validate = ValidateEntityUtil.validate(position);
        if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }

        if(position.getId() == null || position.getId().longValue() <= 0){
            return Result.error(CodeMsg.ADMIN_POSITION_NO_EXIST);
        }

        if(position.getNote().length() > 50){
            return Result.error(CodeMsg.POSITION_LENGTH_EXIST);
        }
        if(positionService.isExistName(position.getName(),position.getId())){
            return Result.error(CodeMsg.ADMIN_POSITION_NAME_EXIST);
        }

        Position findById = positionService.find(position.getId());
        BeanUtils.copyProperties(position,findById,"id","createTime","updateTime");
        if(positionService.save(findById) == null){
            return Result.error(CodeMsg.ADMIN_POSITION_EDIT_ERROR);
        }
        operaterLogService.add("编辑岗位，岗位名：" + position.getName());
        return Result.success(true);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delete(@RequestParam("id")Long id){
        try{
            positionService.delete(id);
        }catch (Exception e){
            return Result.error(CodeMsg.ADMIN_POSITION_DELETE_ERROR);
        }
        operaterLogService.add("删除岗位，岗位ID：" + id);
        return Result.success(true);
    }

}
