package com.yuanlrc.base.bean;
/**
 * 错误码统一处理类，所有的错误码统一定义在这里
 * @author Administrator
 *
 */
public class CodeMsg {

	private int code;//错误码
	
	private String msg;//错误信息
	
	/**
	 * 构造函数私有化即单例模式
	 * @param code
	 * @param msg
	 */
	private CodeMsg(int code,String msg){
		this.code = code;
		this.msg = msg;
	}
	
	public int getCode() {
		return code;
	}



	public void setCode(int code) {
		this.code = code;
	}



	public String getMsg() {
		return msg;
	}



	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	//通用错误码定义
	//处理成功消息码
	public static CodeMsg SUCCESS = new CodeMsg(0, "success");
	//非法数据错误码
	public static CodeMsg DATA_ERROR = new CodeMsg(-1, "非法数据！");
	public static CodeMsg CPACHA_EMPTY = new CodeMsg(-2, "验证码不能为空！");
	public static CodeMsg VALIDATE_ENTITY_ERROR = new CodeMsg(-3, "");
	public static CodeMsg SESSION_EXPIRED = new CodeMsg(-4, "会话已失效，请刷新页面重试！");
	public static CodeMsg CPACHA_ERROR = new CodeMsg(-5, "验证码错误！");
	public static CodeMsg USER_SESSION_EXPIRED = new CodeMsg(-6, "还未登录或会话失效，请重新登录！");
	public static CodeMsg UPLOAD_PHOTO_SUFFIX_ERROR = new CodeMsg(-7, "图片格式不正确！");
	public static CodeMsg UPLOAD_PHOTO_ERROR = new CodeMsg(-8, "图片上传错误！");
	public static CodeMsg ORDER_SN_ERROR = new CodeMsg(-12, "订单编号错误！");
	public static CodeMsg PHONE_ERROR = new CodeMsg(-13, "手机号错误！");
	public static CodeMsg ORDER_AUTH_ERROR = new CodeMsg(-14, "\u8ba2\u5355\u9a8c\u8bc1\u5931\u8d25\uff0c\u8ba2\u5355\u7f16\u53f7\u6216\u624b\u673a\u53f7\u8f93\u5165\u6709\u8bef\u6216\u8005\u53ef\u80fd\u4f60\u8d2d\u4e70\u7684\u662f\u76d7\u7248\uff0c\u8bf7\u8054\u7cfb\u3010\u733f\u6765\u5165\u6b64\u3011\u5ba2\u670d\uff01");
	
	//后台管理类错误码
	//用户管理类错误
	public static CodeMsg ADMIN_USERNAME_EMPTY = new CodeMsg(-2000, "用户名不能为空！");
	public static CodeMsg ADMIN_PASSWORD_EMPTY = new CodeMsg(-2001, "密码不能为空！");
	public static CodeMsg ADMIN_NO_RIGHT = new CodeMsg(-2002, "您所属的角色没有该权限！");
	
	//登录类错误码
	public static CodeMsg ADMIN_USERNAME_NO_EXIST = new CodeMsg(-3000, "该账号不存在！");
	public static CodeMsg ADMIN_PASSWORD_ERROR = new CodeMsg(-3001, "密码错误！");
	public static CodeMsg ADMIN_USER_UNABLE = new CodeMsg(-3002, "该用户已被冻结，请联系管理员！");
	public static CodeMsg ADMIN_USER_ROLE_UNABLE = new CodeMsg(-3003, "该用户所属角色状态不可用，请联系管理员！");
	public static CodeMsg ADMIN_USER_ROLE_AUTHORITES_EMPTY = new CodeMsg(-3004, "该用户所属角色无可用权限，请联系管理员！");
	
	//后台菜单管理类错误码
	public static CodeMsg ADMIN_MENU_ADD_ERROR = new CodeMsg(-4000, "菜单添加失败，请联系管理员！");
	public static CodeMsg ADMIN_MENU_EDIT_ERROR = new CodeMsg(-4001, "菜单编辑失败，请联系管理员！");
	public static CodeMsg ADMIN_MENU_ID_EMPTY = new CodeMsg(-4002, "菜单ID不能为空！");
	public static CodeMsg ADMIN_MENU_ID_ERROR = new CodeMsg(-4003, "菜单ID错误！");
	public static CodeMsg ADMIN_MENU_DELETE_ERROR = new CodeMsg(-4004, "改菜单下有子菜单，不允许删除！");
	//后台角色管理类错误码
	public static CodeMsg ADMIN_ROLE_ADD_ERROR = new CodeMsg(-5000, "角色添加失败，请联系管理员！");
	public static CodeMsg ADMIN_ROLE_NO_EXIST = new CodeMsg(-5001, "该角色不存在！");
	public static CodeMsg ADMIN_ROLE_EDIT_ERROR = new CodeMsg(-5002, "角色编辑失败，请联系管理员！");
	public static CodeMsg ADMIN_ROLE_DELETE_ERROR = new CodeMsg(-5003, "该角色下存在用户信息，不可删除！");
	//后台用户管理类错误码
	public static CodeMsg ADMIN_USER_ROLE_EMPTY = new CodeMsg(-6000, "请选择用户所属角色！");
	public static CodeMsg ADMIN_USERNAME_EXIST = new CodeMsg(-6001, "该用户名已存在，请换一个试试！");
	public static CodeMsg ADMIN_USE_ADD_ERROR = new CodeMsg(-6002, "用户添加失败，请联系管理员！");
	public static CodeMsg ADMIN_USE_NO_EXIST = new CodeMsg(-6003, "用户不存在！");
	public static CodeMsg ADMIN_USE_EDIT_ERROR = new CodeMsg(-6004, "用户编辑失败，请联系管理员！");
	public static CodeMsg ADMIN_USE_DELETE_ERROR = new CodeMsg(-6005, "该用户存在关联数据，不允许删除！");
	
	//后台用户修改密码类错误码
	public static CodeMsg ADMIN_USER_UPDATE_PWD_ERROR = new CodeMsg(-7000, "旧密码错误！");
	public static CodeMsg ADMIN_USER_UPDATE_PWD_EMPTY = new CodeMsg(-7001, "新密码不能为空！");
	public static CodeMsg ADMIN_USER_PWD_LENGTH_ERROR=new CodeMsg(-7002,"新密码长度需大于4位小于32位");
	
	//后台用户修改密码类错误码
	public static CodeMsg ADMIN_DATABASE_BACKUP_NO_EXIST = new CodeMsg(-8000, "备份记录不存在！");

	//后台员工管理错误码
	public static CodeMsg ADMIN_STAFF_ROLE_ERROR=new CodeMsg(-9000,"该员工没有选择角色");
	public static CodeMsg ADMIN_STAFF_MOBILE_ERROR=new CodeMsg(-9001,"请填写正确的手机号");
	public static CodeMsg ADMIN_STAFF_EMERGENCY_MOBILE_ERROR=new CodeMsg(-9002,"请填写正确的紧急联系人手机号");
	public static CodeMsg ADMIN_STAFF_NOT_EXIST_ERROR=new CodeMsg(-9003,"员工不存在");
	public static CodeMsg ADMIN_STAFF_EDIT_ERROR=new CodeMsg(-9004,"员工编辑失败,请联系管理员!");
	public static CodeMsg ADMIN_STAFF_ADD_ERROR=new CodeMsg(-9005,"员工添加失败,请联系管理员!");
	public static CodeMsg ADMIN_STAFF_DELETE_ERROR=new CodeMsg(-9006,"该员工下存在关联数据不可删除!");
	public static CodeMsg ADMIN_STAFF_STATUS_ERROR=new CodeMsg(-9007,"离职状态修改失败,请联系管理员!");

	//后台部门管理错误码
	public static CodeMsg DEPARTMENT_LENGTH_EXIST = new CodeMsg(-10000,"部门备注长度不能超过50");
	public static CodeMsg ADMIN_DEPARTMENT_NAME_EXIST = new CodeMsg(-10001, "该部门名称已存在，请换一个试试！");
	public static CodeMsg ADMIN_DEPARTMENT_SAVE_ERROR = new CodeMsg(-10002, "部门添加失败！");
	public static CodeMsg ADMIN_DEPARTMENT_EDIT_ERROR = new CodeMsg(-10003, "部门编辑失败！");
	public static CodeMsg ADMIN_DEPARTMENT_DELETE_ERROR = new CodeMsg(-10004, "该部门存在关联数据，不允许删除！");
	public static CodeMsg ADMIN_DEPARTMENT_NO_EXIST = new CodeMsg(-10005, "部门不存在！");

	//后台职称管理错误码
	public static CodeMsg ADMIN_JOB_TITLE_NAME_EXIST = new CodeMsg(-11000,"该职称名称已存在，请换一个试试！");
	public static CodeMsg ADMIN_JOB_TITLE_SAVE_ERROR = new CodeMsg(-11001,"职称添加失败！");
	public static CodeMsg ADMIN_JOB_TITLE_EDIT_ERROR = new CodeMsg(-11002,"职称编辑失败！");
	public static CodeMsg ADMIN_JOB_TITLE_NO_EXIST = new CodeMsg(-11003,"职称不存在！");

	//后台工龄管理错误码
	public static CodeMsg ADMIN_WORKING_YEARS_ADD_ERROR=new CodeMsg(-20000,"工龄补贴添加失败,请联系管理员!");
	public static CodeMsg ADMIN_WORKING_YEARS_EDIT_ERROR=new CodeMsg(-20001,"工龄补贴编辑失败,请联系管理员!");
	public static CodeMsg ADMIN_WORKING_YEARS_DELETE_ERROR=new CodeMsg(-20002,"工龄补贴删除失败,请联系管理员!");
	public static CodeMsg ADMIN_WORKING_YEARS_EXIST_ERROR=new CodeMsg(-20003,"该工龄补贴已存在,请重新填写");

	//后台岗位管理错误码
	public static CodeMsg POSITION_LENGTH_EXIST = new CodeMsg(-12000,"岗位描述长度不能超过50");
	public static CodeMsg ADMIN_POSITION_NAME_EXIST = new CodeMsg(-12001,"该岗位名称已存在，请换一个试试！");
	public static CodeMsg ADMIN_POSITION_SAVE_ERROR = new CodeMsg(-12002, "岗位添加失败！");
	public static CodeMsg ADMIN_POSITION_EDIT_ERROR = new CodeMsg(-12003, "岗位编辑失败！");
	public static CodeMsg ADMIN_POSITION_DELETE_ERROR = new CodeMsg(-12004, "该岗位存在关联数据，不允许删除！");
	public static CodeMsg ADMIN_POSITION_NO_EXIST = new CodeMsg(-12005, "岗位不存在！");


	//考勤管理错误码
	public static CodeMsg ADMIN_ATTENDANCE_MONTH_EMPTY = new CodeMsg(-13000,"请选择月份");
	public static CodeMsg ADMIN_JOB_NUMBER_EMPTY = new CodeMsg(-13001,"该工号不存在");
	public static CodeMsg ADMIN_ATTENDANCE_SAVE_ERROR = new CodeMsg(-13002,"考勤信息添加失败");
	public static CodeMsg ADMIN_ATTENDANCE_DELETE_ERROR = new CodeMsg(-13003,"该考勤信息存在关联数据，不允许删除！");
	public static CodeMsg ADMIN_DAYS_ERROR = new CodeMsg(-13004,"天数有误，请重新核对天数！");
	public static CodeMsg ADMIN_DATE_ERROR = new CodeMsg(-13005,"日期不能超过当前月份！");
	public static CodeMsg ADMIN_ATTENDANCE_JOB_NUMBER_EMPTY = new CodeMsg(-13006,"请选择员工信息！");
	public static CodeMsg ADMIN_ATTENDANCE_EDIT_ERROR = new CodeMsg(-13007,"编辑失败！");
	public static CodeMsg ADMIN_ATTENDANCE_NO_EXIST = new CodeMsg(-13008,"考勤不存在！");
	public static CodeMsg ADMIN_ATTENDANCE_MONTH_EXIST = new CodeMsg(-13009,"该员工该月考勤已存在！");
	public static CodeMsg ADMIN_ATTENDANCE_IMPORT_ADD_ERROR = new CodeMsg(-13010,"请选择Excel文件！");
	public static CodeMsg ADMIN_ATTENDANCE_IMPORT_EXCEL_ERROR = new CodeMsg(-13011,"请正确导入数据信息！");
	public static CodeMsg ADMIN_IMPORT_EXCEL_NULL_ERROR = new CodeMsg(-13012, "单元格内容不能为空");
	public static CodeMsg ADMIN_IMPORT_MONTH_ERROR = new CodeMsg(-13013, "请将月份以 yyyy-MM 形式填写");
	public static CodeMsg ADMIN_ATTENDANCE_LIST_ADD_ERROR = new CodeMsg(-13014, "导入失败");
	public static CodeMsg ADMIN_IMPORT_JOB_NUMBER_EMPTY = new CodeMsg(-13015,"员工该月考勤已存在！");
	public static CodeMsg ADMIN_IMPORT_DATE_ERROR = new CodeMsg(-13016,"日期超过当前月份！");
	public static CodeMsg NUMBER_TYPE_ERROR = new CodeMsg(-13017,"数据格式错误！");

	//年度考勤管理错误码
	public static CodeMsg ADMIN_MONTH_ERROR = new CodeMsg(-14000,"当前不是一月份，无法进行年度考核");
	public static CodeMsg ADMIN_ANNUAL_ASSESSMENT_ERROR = new CodeMsg(-14001,"年度考核添加失败");
	public static CodeMsg ANNUAL_YEAR_EXIST = new CodeMsg(-14002,"员工该年份的年度考核已存在");

	//工资管理错误码
	public static CodeMsg ADMIN_SALARY_NO_STAFF_ERROR = new CodeMsg(-15000,"请选择员工");
	public static CodeMsg ADMIN_SALARY_MONTH_EXIST = new CodeMsg(-15001,"该员工该月工资已结算");
	public static CodeMsg ADMIN_SALARY_SAVE_ERROR = new CodeMsg(-15002,"结算失败");
	public static CodeMsg ADMIN_SALARY_SEND_ERROR = new CodeMsg(-15003,"发放失败");
	public static CodeMsg ADMIN_SALARY_DELETE_ERROR = new CodeMsg(-15004,"删除失败");
	public static CodeMsg ADMIN_SALARY_EMPRY = new CodeMsg(-15005,"工资不存在");
	public static CodeMsg ADMIN_SALARY_HAS_SEND = new CodeMsg(-15006,"工资已发放，不能再删除");
	public static CodeMsg ADMIN_SALRY_STATUS_SEND = new CodeMsg(-15007,"工资已发放，不能再发放");
	public static CodeMsg ADMIN_STAFF_SEND_ALL = new CodeMsg(-15008,"该月员工工资已全部结算，不能再结算");

	//后台工资项管理错误码
	public static CodeMsg ADMIN_WAGE_ITEM_ADD_ERROR=new CodeMsg(-21000,"工资项添加失败,请联系管理员!");
	public static CodeMsg ADMIN_WAGE_ITEM_EDIT_ERROR=new CodeMsg(-21001,"工资项编辑失败,请联系管理员!");

	//后台指标管理错误码
	public static CodeMsg ADMIN_ASSESS_TARGET_ADD_ERROR=new CodeMsg(-22001,"指标添加失败,请联系管理员!");
	public static CodeMsg ADMIN_ASSESS_TARGET_EDIT_ERROR=new CodeMsg(-22002,"指标编辑失败,请联系管理员!");
	public static CodeMsg ADMIN_ASSESS_TARGET_DELETE_ERROR=new CodeMsg(-22003,"指标删除失败,请联系管理员!");
	public static CodeMsg ADMIN_ASSESS_TARGET_CATEGORY_ERROR=new CodeMsg(-22004,"请选择指标分类!");
	public static CodeMsg ADMIN_ASSESS_TARGET_NAME_EXIST_ERROR=new CodeMsg(-22005,"指标名称已存在,请重新填写!");

	//后台绩效考核错误码
	public static CodeMsg ADMIN_PERFORMANCE_YEAR_ERROR=new CodeMsg(-23001,"请填写有效的年份!");
	public static CodeMsg ADMIN_PERFORMANCE_MONTH_ERROR=new CodeMsg(-23002,"请填写有效的月份!");
	public static CodeMsg ADMIN_PERFORMANCE_EXIST_ERROR=new CodeMsg(-23003,"该员工的本年份月份已考核,不可重复考核");
	public static CodeMsg ADMIN_PERFORMANCE_ADD_ERROR=new CodeMsg(-23004,"绩效考核失败,请联系管理员!");
}
