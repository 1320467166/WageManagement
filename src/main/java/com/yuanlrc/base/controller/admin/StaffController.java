package com.yuanlrc.base.controller.admin;

import com.yuanlrc.base.bean.*;
import com.yuanlrc.base.constant.SessionConstant;
import com.yuanlrc.base.dao.admin.JobTitleDao;
import com.yuanlrc.base.entity.admin.Role;
import com.yuanlrc.base.entity.admin.Staff;
import com.yuanlrc.base.entity.admin.User;
import com.yuanlrc.base.service.admin.*;
import com.yuanlrc.base.util.DateUtil;
import com.yuanlrc.base.util.SessionUtil;
import com.yuanlrc.base.util.StringUtil;
import com.yuanlrc.base.util.ValidateEntityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 后台员工管理控制器
 * @author Administrator
 *
 */
@RequestMapping("/admin/staff")
@Controller
public class StaffController {

	@Autowired
	private StaffService staffService;
	@Autowired
	private JobTitleService jobTitleService;
	@Autowired
	private RoleService roleService;

	@Autowired
	private PositionService positionService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private OperaterLogService operaterLogService;

	@Autowired
	private AttendanceService attendanceService;


	/**
	 * 员工列表页面
	 * @param model
	 * @param staff
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value="/list")
	public String list(Model model, Staff staff, PageBean<Staff> pageBean){
        model.addAttribute("title", "员工列表");
		model.addAttribute("jobNumber", staff.getJobNumber()==null?"":staff.getJobNumber());
		model.addAttribute("pageBean", staffService.findList(staff, pageBean));
		return "admin/staff/list";
	}
	
	/**
	 * 新增员工页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model){
		model.addAttribute("roles", roleService.findAll());
		model.addAttribute("educationEnum",EducationEnum.values());
		model.addAttribute("jobTitleList",jobTitleService.findAll());
		model.addAttribute("positionList",positionService.findAll());
		model.addAttribute("departmentList",departmentService.findAll());
		return "admin/staff/add";
	}
	
	/**
	 * 员工添加表单提交处理
	 * @param staff
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> add(Staff staff){
		//用统一验证实体方法验证是否合法
		CodeMsg validate = ValidateEntityUtil.validate(staff);
		if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
			return Result.error(validate);
		}
		if(staff.getRole() == null || staff.getRole().getId() == null){
			return Result.error(CodeMsg.ADMIN_STAFF_ROLE_ERROR);
		}
		if(!StringUtil.isMobile(staff.getMobile())){
			return Result.error(CodeMsg.ADMIN_STAFF_MOBILE_ERROR);
		}
		if(!StringUtil.isMobile(staff.getEmergencyMobile())){
			return Result.error(CodeMsg.ADMIN_STAFF_EMERGENCY_MOBILE_ERROR);
		}
		//自动生成工号
        int maxId = staffService.findMaxId()+1;
        String jobNumber = DateUtil.getCurrentDateTime("yyyyMMdd");
		if(maxId<10){
            jobNumber=jobNumber+"0"+maxId;
        }else{
            jobNumber=jobNumber+maxId;
        }
        staff.setJobNumber(jobNumber);
        //到这说明一切符合条件，进行数据库新增
		if(staffService.save(staff) == null){
			return Result.error(CodeMsg.ADMIN_STAFF_ADD_ERROR);
		}
		operaterLogService.add("添加员工，员工名：" + staff.getName());
		return Result.success(true);
	}

	@RequestMapping(value="/edit_self",method=RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> edit_self(Staff staff, HttpServletRequest request){
		//用统一验证实体方法验证是否合法
		CodeMsg validate = ValidateEntityUtil.validate(staff);
		if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
			return Result.error(validate);
		}
		if(!StringUtil.isMobile(staff.getMobile())){
			return Result.error(CodeMsg.ADMIN_STAFF_MOBILE_ERROR);
		}
		if(!StringUtil.isMobile(staff.getEmergencyMobile())){
			return Result.error(CodeMsg.ADMIN_STAFF_EMERGENCY_MOBILE_ERROR);
		}
		if(staff.getId() == null || staff.getId().longValue() <= 0){
			return Result.error(CodeMsg.ADMIN_STAFF_NOT_EXIST_ERROR);
		}
		//到这说明一切符合条件，进行数据库保存
		Staff findById = staffService.find(staff.getId());
		//讲提交的员工信息指定字段复制到已存在的staff对象中,该方法会覆盖新字段内容
		BeanUtils.copyProperties(staff, findById, "id","createTime","updateTime","jobNumber",
				"role","educationEnum","jobTitle",
				"position","department","entryTime");
		Staff saveStaff = staffService.save(findById);
		if(saveStaff == null){
			return Result.error(CodeMsg.ADMIN_STAFF_EDIT_ERROR);
		}
		Staff loginedStaff = SessionUtil.getLoginedStaff();
		if(loginedStaff != null){
			if(loginedStaff.getId().longValue() == findById.getId().longValue()){
				loginedStaff.setHeadPic(saveStaff.getHeadPic());
				loginedStaff.setName(saveStaff.getName());
				loginedStaff.setMobile(saveStaff.getMobile());
				loginedStaff.setEmergencyContact(saveStaff.getEmergencyContact());
				loginedStaff.setEmergencyMobile(saveStaff.getEmergencyMobile());
				loginedStaff.setAge(saveStaff.getAge());
				loginedStaff.setSex(saveStaff.getSex());
				SessionUtil.set(SessionConstant.SESSION_STAFF_LOGIN_KEY,loginedStaff);
			}
		}

		operaterLogService.add("编辑员工，员工名：" + staff.getName());
		return Result.success(true);
	}

	/**
	 * 员工编辑页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/edit",method=RequestMethod.GET)
	public String edit(Model model,@RequestParam(name="id",required=true)Long id){
		model.addAttribute("staff", staffService.find(id));
		model.addAttribute("roles", roleService.findAll());
		model.addAttribute("educationEnum",EducationEnum.values());
		model.addAttribute("jobTitleList",jobTitleService.findAll());
		model.addAttribute("positionList",positionService.findAll());
		model.addAttribute("departmentList",departmentService.findAll());
		return "admin/staff/edit";
	}
	
	/**
	 * 编辑员工信息表单提交处理
	 * @param staff
	 * @return
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> edit(Staff staff, HttpServletRequest request){
		//用统一验证实体方法验证是否合法
		CodeMsg validate = ValidateEntityUtil.validate(staff);
		if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
			return Result.error(validate);
		}


		if(staff.getRole() == null || staff.getRole().getId() == null){
			return Result.error(CodeMsg.ADMIN_STAFF_ROLE_ERROR);
		}
		if(!StringUtil.isMobile(staff.getMobile())){
			return Result.error(CodeMsg.ADMIN_STAFF_MOBILE_ERROR);
		}
		if(!StringUtil.isMobile(staff.getEmergencyMobile())){
			return Result.error(CodeMsg.ADMIN_STAFF_EMERGENCY_MOBILE_ERROR);
		}
		if(staff.getId() == null || staff.getId().longValue() <= 0){
			return Result.error(CodeMsg.ADMIN_STAFF_NOT_EXIST_ERROR);
		}
		//到这说明一切符合条件，进行数据库保存
		Staff findById = staffService.find(staff.getId());
		//讲提交的员工信息指定字段复制到已存在的staff对象中,该方法会覆盖新字段内容
		BeanUtils.copyProperties(staff, findById, "id","createTime","updateTime","jobNumber");
		Staff saveStaff = staffService.save(findById);
		if(saveStaff == null){
			return Result.error(CodeMsg.ADMIN_STAFF_EDIT_ERROR);
		}
		Staff loginedStaff = SessionUtil.getLoginedStaff();
		if(loginedStaff != null){
			if(loginedStaff.getId().longValue() == findById.getId().longValue()){
				loginedStaff.setHeadPic(saveStaff.getHeadPic());
				loginedStaff.setName(saveStaff.getName());
				loginedStaff.setMobile(saveStaff.getMobile());
				loginedStaff.setEmergencyContact(saveStaff.getEmergencyContact());
				loginedStaff.setEmergencyMobile(saveStaff.getEmergencyMobile());
				loginedStaff.setAge(saveStaff.getAge());
				loginedStaff.setSex(saveStaff.getSex());
				SessionUtil.set(SessionConstant.SESSION_STAFF_LOGIN_KEY,loginedStaff);
			}
		}

		operaterLogService.add("编辑员工，员工名：" + staff.getName());
		return Result.success(true);
	}
	
	/**
	 * 离职员工
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> delete(@RequestParam(name="id",required=true)Long id){
		if(staffService.updateStatus(StaffStatus.QUIT.getCode(), id)<=0){
			return Result.error(CodeMsg.ADMIN_STAFF_STATUS_ERROR);
		}
		operaterLogService.add("员工离职，员工ID：" + id);
		return Result.success(true);
	}

	/**
	 * 修改个人信息
	 * @param model
	 * @return
	 */
	@RequestMapping("/self")
	public String self(Model model){
		Staff loginedStaff = SessionUtil.getLoginedStaff();
		Staff staff = staffService.find(loginedStaff.getId());
		model.addAttribute("roles", roleService.findAll());
		model.addAttribute("educationEnum",EducationEnum.values());
		model.addAttribute("jobTitleList",jobTitleService.findAll());
		model.addAttribute("positionList",positionService.findAll());
		model.addAttribute("departmentList",departmentService.findAll());
		model.addAttribute("staff",staff);
		return "admin/staff/self";
	}

}
