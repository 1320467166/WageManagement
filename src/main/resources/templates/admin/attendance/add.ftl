<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
    <title>${siteName!""}|考勤管理-添加考勤</title>
    <#include "../common/header.ftl"/>
    <!--时间选择插件-->
    <link rel="stylesheet" href="/admin/js/bootstrap-datetimepicker/bootstrap-datetimepicker.min.css">
    <!--日期选择插件-->
    <link rel="stylesheet" href="/admin/js/bootstrap-datepicker/bootstrap-datepicker3.min.css">
    <link href="/admin/css/style.min.css" rel="stylesheet">

    <link rel="stylesheet" href="/admin/bootstrap-select/dist/css/bootstrap-select.min.css">

</head>

<body>
<div class="lyear-layout-web">
    <div class="lyear-layout-container">
        <!--左侧导航-->
        <aside class="lyear-layout-sidebar">

            <!-- logo -->
            <div id="logo" class="sidebar-header">
                <a href="/system/index"><img src="/admin/images/logo-sidebar.png" title="${siteName!""}" alt="${siteName!""}" /></a>
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
                            <div class="card-header"><h4>添加考勤</h4></div>
                            <div class="card-body">
                                <form action="add" id="department-add-form" method="post" class="row">
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">选择员工信息</span>
                                        <select name="jobNumber" class="form-control selectpicker" id="jobNumber"
                                                data-live-search="true" data-max-options="1">
                                            <option value="">--请选择--</option>
                                            <#list staffList as staff>
                                                <option value="${staff.jobNumber}">${staff.jobNumber}-${staff.name}-${staff.department.name}</option>
                                            </#list>
                                        </select>
                                    </div>

                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">月份</span>
                                        <input class="form-control js-datetimepicker" type="text" id="month" name="month"
                                               placeholder="请选择月份" value="" data-side-by-side="true" data-locale="zh-cn" data-format="YYYY-MM" />
                                    </div>

                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">病假天数</span>
                                        <input type="number" id="daysSick" name="daysSick" value="0"
                                               class="form-control required" oninput="if (value<0)value=0"
                                               onkeyup="aDecimal(this)"  placeholder="请填写病假天数" tips="请填写病假天数"/>
                                    </div>

                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">事假天数</span>
                                        <input type="number" id="personalLeaveDays" name="personalLeaveDays" value="0"
                                               class="form-control required" oninput="if (value<0)value=0"
                                               onkeyup="aDecimal(this)"  placeholder="请填写事假天数" tips="请填写事假天数"/>
                                    </div>

                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">平时加班(小时)</span>
                                        <input type="number" id="overtimeHours" name="overtimeHours" value="0"
                                               class="form-control required" oninput="if (value<0)value=0"
                                               onkeyup="aDecimal(this)"  placeholder="请填写平时加班时间" tips="请填写平时加班时间"/>
                                    </div>

                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">周末加班(小时)</span>
                                        <input type="number" id="overtimeOnWeekends" name="overtimeOnWeekends" value="0"
                                               class="form-control required" oninput="if (value<0)value=0"
                                               onkeyup="aDecimal(this)"  placeholder="请填写周末加班时间" tips="请填写周末加班时间"/>
                                    </div>

                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">节假日加班(小时)</span>
                                        <input type="number" id="holidayOvertimeHours" name="holidayOvertimeHours" value="0"
                                               class="form-control required" oninput="if (value<0)value=0"
                                               onkeyup="aDecimal(this)"  placeholder="请填写节假日加班" tips="请填写节假日加班"/>
                                    </div>

                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">迟到次数</span>
                                        <input type="number" id="lateNumber" name="lateNumber" value="0"
                                               class="form-control required" oninput="if (value<0)value=0"
                                               onkeyup="integer(this)"  placeholder="请填写迟到次数" tips="请填写迟到次数"/>
                                    </div>

                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">早退次数</span>
                                        <input type="number" id="leaveEarlyTimes" name="leaveEarlyTimes" value="0"
                                               class="form-control required" oninput="if (value<0)value=0"
                                               onkeyup="integer(this)"  placeholder="请填写早退次数" tips="请填写早退次数"/>
                                    </div>

                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">缺勤天数</span>
                                        <input type="number" id="absenceDays" name="absenceDays" value="0"
                                               class="form-control required" oninput="if (value<0)value=0"
                                               onkeyup="aDecimal(this)"  placeholder="请填写缺勤天数" tips="请填写缺勤天数"/>
                                    </div>

                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">出差天数</span>
                                        <input type="number" id="travelDays" name="travelDays" value="0"
                                               class="form-control required" oninput="if (value<0)value=0"
                                               onkeyup="aDecimal(this)"  placeholder="请填写事假天数" tips="请填写事假天数"/>
                                    </div>

                                    <div class="form-group col-md-12">
                                        <button type="button" class="btn btn-primary ajax-post" id="add-form-submit-btn">确 定</button>
                                        <button type="button" class="btn btn-default" onclick="javascript:history.back(-1);return false;">返 回</button>
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

<script type="text/javascript" src="/admin/bootstrap-select/js/bootstrap-select.js"></script>
<script type="text/javascript" src="/admin/bootstrap-select/js/i18n/defaults-zh_CN.js"></script>

<script type="text/javascript" src="/admin/js/perfect-scrollbar.min.js"></script>

<!--时间选择插件-->
<script src="/admin/js/bootstrap-datetimepicker/moment.min.js"></script>
<script src="/admin/js/bootstrap-datetimepicker/bootstrap-datetimepicker.min.js"></script>
<script src="/admin/js/bootstrap-datetimepicker/locale/zh-cn.js"></script>
<!--日期选择插件-->
<script src="/admin/js/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
<script src="/admin/js/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>

<script type="text/javascript" src="/admin/js/main.min.js"></script>
<script type="text/javascript">

    $(document).ready(function(){
        //提交按钮监听事件
        $("#add-form-submit-btn").click(function(){
            if(!checkForm("department-add-form")){
                return;
            }
            var data = $("#department-add-form").serialize();
            $.ajax({
                url:'add',
                type:'POST',
                data:data,
                dataType:'json',
                success:function(data){
                    if(data.code == 0){
                        showSuccessMsg('添加成功!',function(){
                            window.location.href = 'list';
                        })
                    }else{
                        showErrorMsg(data.msg);
                    }
                },
                error:function(data){
                    alert('网络错误!');
                }
            });
        });
    });

</script>
</body>
</html>