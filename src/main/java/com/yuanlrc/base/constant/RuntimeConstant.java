package com.yuanlrc.base.constant;

import java.util.Arrays;
import java.util.List;

/**
 * 系统运行时的常量
 * @author Administrator
 *
 */
public class RuntimeConstant {

	//后台登录拦截器无需拦截的url
	public static List<String> loginExcludePathPatterns = Arrays.asList(
			"/home/**",
			"/system/login",
			"/system/auth_order",
			"/admin/css/**",
			"/admin/fonts/**",
			"/admin/js/**",
			"/admin/images/**",
			"/error",
			"/upload/upload_photo",
			"/photo/view",
			"/cpacha/generate_cpacha",
			"/admin/select2/**",
			"/admin/bootstrap-select/**",
			"/admin/bootstrap-select2/**"
	);
	//后台权限拦截器无需拦截的url
	public static List<String> authorityExcludePathPatterns = Arrays.asList(
			"/home/**",
			"/system/login",
			"/system/auth_order",
			"/system/index",
			"/system/no_right",
			"/admin/css/**",
			"/admin/fonts/**",
			"/admin/js/**",
			"/admin/images/**",
			"/error",
			"/cpacha/generate_cpacha",
			"/system/logout",
			"/system/update_userinfo",
			"/system/update_pwd",
			"/upload/upload_photo",
			"/photo/view",
			"/admin/select2/**",
			"/admin/bootstrap-select/**",
			"/admin/bootstrap-select2/**"

	);

	//前台登录拦截器无需拦截的url
	public static List<String> homeLoginExcludePathPatterns = Arrays.asList(
			"/admin/**",
			"/home/index/**",
			"/home/css/**",
			"/home/fonts/**",
			"/home/js/**",
			"/home/images/**",
			"/system/**",
			"/error",
			"/photo/view",
			"/cpacha/generate_cpacha",
			"/upload/upload_photo",
			"/home/picture/**",
			"/admin/**",
			"/menu/**"
	);
	//前台全局拦截器无需拦截的url
	public static List<String> homeGlobalExcludePathPatterns = Arrays.asList(
			"/admin/**",
			"/home/index/**",
			"/home/css/**",
			"/home/fonts/**",
			"/home/js/**",
			"/home/images/**",
			"/system/**",
			"/error",
			"/photo/view",
			"/upload/upload_photo",
			"/cpacha/generate_cpacha",
			"/home/picture/**"
	);
}
