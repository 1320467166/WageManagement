<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title>${siteName!""}|员工管理-添加员工</title>
<#include "../common/header.ftl"/>
    <link rel="stylesheet" href="/admin/js/bootstrap-datepicker/bootstrap-datepicker3.min.css">
</head>

<body>
<div class="lyear-layout-web">
    <div class="lyear-layout-container">
        <!--左侧导航-->
        <aside class="lyear-layout-sidebar">

            <!-- logo -->
            <div id="logo" class="sidebar-header">
                <a href="/system/index"><img src="/admin/images/logo-sidebar.png" title="${siteName!""}"
                                             alt="${siteName!""}"/></a>
            </div>
            <div class="lyear-layout-sidebar-scroll">
        <#include "../common/left-menu.ftl"/>
            </div>

        </aside>
        <!--End 左侧导航-->
    
    <#include "../common/header-menu.ftl"/>

        <!--页面主要内容-->
        <main class="lyear-layout-content">

            <div class="container-fluid">

                <div class="row">
                    <div class="col-lg-12">
                        <div class="card">
                            <div class="card-header"><h4>添加员工</h4></div>
                            <div class="card-body">
                                <form id="staff-add-form"  class="row">
                                    <div class="form-group col-md-12">
                                        <label>头像上传</label>
                                        <div class="form-controls">
                                            <ul class="list-inline clearfix lyear-uploads-pic">
                                                <li class="col-xs-4 col-sm-3 col-md-2">
                                                    <figure>
                                                        <img src="/admin/images/default-head.jpg" id="show-picture-img"
                                                             alt="默认头像">
                                                    </figure>
                                                </li>
                                                <input type="hidden" name="headPic" id="headPic">
                                                <input type="file" id="select-file" style="display:none;"
                                                       onchange="upload('show-picture-img','headPic')">
                                                <li class="col-xs-4 col-sm-3 col-md-2">
                                                    <a class="pic-add" id="add-pic-btn" href="javascript:void(0)"
                                                       title="点击上传"></a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">员工姓名</span>
                                        <input type="text" class="form-control required" id="name"
                                               name="name" value="" placeholder="请输入员工姓名" minlength="1" maxlength="10" tips="请填写员工姓名"/>
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">登录密码</span>
                                        <input type="password" class="form-control required" id="add-password"
                                               name="password" value="" placeholder="请输入登录密码" minlength="4" maxlength="30"  tips="请填写登录密码"/>
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">员工学历</span>
                                        <select name="educationEnum" class="form-control selected" id="add-educationEnum" tips="请选择学历">
                                            <option value="-1">--请选择学历--</option>
                    	<#list educationEnum as education>
                            <option value="${education}">${education.value}</option>
                        </#list>
                                        </select>
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">所属角色</span>
                                        <select name="role.id" class="form-control selected" id="add-role" tips="请选择角色">
                    	<#list roles as role>
                            <option value="${role.id}">${role.name}</option>
                        </#list>
                                        </select>
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">所属职称</span>
                                        <select name="jobTitle.id" class="form-control selected" id="add-jobTitle" tips="请选择职称">
                                            <option value="-1">--请选择职称--</option>
                    	<#list jobTitleList as jobTitle>
                            <option value="${jobTitle.id}">${jobTitle.name}</option>
                        </#list>
                                        </select>
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">所属岗位</span>
                                        <select name="position.id" class="form-control selected" id="add-position" tips="请选择岗位">
                                            <option value="-1">--请选择岗位--</option>
                    	<#list positionList as position>
                            <option value="${position.id}">${position.name}</option>
                        </#list>
                                        </select>
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">所属部门</span>
                                        <select name="department.id" class="form-control selected" id="add-department" tips="请选择部门">
                                            <option value="-1">--请选择部门--</option>
                    	<#list departmentList as department>
                            <option value="${department.id}">${department.name}</option>
                        </#list>
                                        </select>
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">手机号码</span>
                                        <input type="tel" class="form-control required" id="add-mobile" minlength="5" maxlength="11" name="mobile" value="" tips="请输入手机号码"/>
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">紧急联系人</span>
                                        <input type="text" class="form-control required" id="add-emergencyContact" tips="请填写紧急联系人"
                                               name="emergencyContact" value=""/>
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">紧急联系人电话</span>
                                        <input type="text" class="form-control required" id="add-emergencyMobile" tips="请填写紧急联系人电话"
                                               name="emergencyMobile" value="" minlength="5" maxlength="11"/>
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">员工年龄</span>
                                        <input type="number" class="form-control required" id="add-age"
                                               name="age" value="" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)"
                                               onblur="this.v();" tips="请填写员工年龄"/>
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">基本工资</span>
                                        <input type="number" class="form-control required" id="add-salary"
                                               name="salary" value="" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)" tips="请填写基本工资"
                                               onblur="this.v();" tips="请填写基本工资"/>
                                    </div>

                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">入职时间</span>
                                        <input class="form-control required js-datepicker " tips="请选择入职时间" type="text" id="entryTime-datepicker" name="entryTime" placeholder="请选择入职时间" value="" data-date-format="yyyy-mm-dd"  readonly/>
                                    </div>

                                    <div class="input-group"
                                         style="margin-top:15px;margin-bottom:15px;padding-left:25px;">
                                        性别：
                                        <label class="lyear-radio radio-inline radio-primary" style="margin-left:30px;">
                                            <input type="radio" name="sex" value="1" checked="">
                                            <span>男</span>
                                            <label class="lyear-radio radio-inline radio-primary">
                                                <input type="radio" name="sex" value="2">
                                                <span>女</span>
                                                <label class="lyear-radio radio-inline radio-primary">
                                                    <input type="radio" name="sex" value="0">
                                                    <span>未知</span>
                                                </label>
                                    </div>
                                    <div class="form-group col-md-12">
                                        <button type="button" class="btn btn-primary ajax-post"
                                                id="add-form-submit-btn">确 定
                                        </button>
                                        <button type="button" class="btn btn-default"
                                                onclick="javascript:history.back(-1);return false;">返 回
                                        </button>
                                    </div>
                                </form>

                            </div>
                        </div>
                    </div>

                </div>

            </div>

        </main>
        <!--End 页面主要内容-->
    </div>
</div>
<#include "../common/footer.ftl"/>
<script type="text/javascript" src="/admin/js/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="/admin/js/main.min.js"></script>
<script src="/admin/js/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
<script src="/admin/js/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        //提交按钮监听事件
        $("#add-form-submit-btn").click(function () {
            if (!checkForm("staff-add-form")) {
                return;
            }
            if(!checkSelectForm("form-control.selected")){
                return;
            }
            if($("#add-password").val().length<4 ||$("#add-password").val().length>32){
                showWarningMsg("密码不能小于4位,不能大于32位");
                return;
            }
            var regPhone = /^1[0-9]{10}$/;
            var mobile=$("#add-mobile").val();
            if(!regPhone.test(mobile)){
                showWarningMsg("请填写正确的手机号");
                return ;
            }
            var emergencyMobile=$("#add-emergencyMobile").val();
            if(!regPhone.test(emergencyMobile)){
                showWarningMsg("请填写正确的紧急联系人手机号");
                return ;
            }
            var data = $("#staff-add-form").serialize();
            $.ajax({
                url: 'add',
                type: 'POST',
                data: data,
                dataType: 'json',
                success: function (data) {
                    if (data.code == 0) {
                        showSuccessMsg('员工添加成功!', function () {
                            window.location.href = 'list';
                        })
                    } else {
                        showErrorMsg(data.msg);
                    }
                },
                error: function (data) {
                    alert('网络错误!');
                }
            });
        });
        //监听上传图片按钮
        $("#add-pic-btn").click(function () {
            $("#select-file").click();
        });
    });

</script>
</body>
</html>