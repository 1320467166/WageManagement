<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
    <title>${siteName!""}|工资管理-工资结算</title>
    <#include "../common/header.ftl"/>

    <!--时间选择插件-->
    <link rel="stylesheet" href="/admin/js/bootstrap-datetimepicker/bootstrap-datetimepicker.min.css">
    <!--日期选择插件-->
    <link rel="stylesheet" href="/admin/js/bootstrap-datepicker/bootstrap-datepicker3.min.css">

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
                            <div class="card-header"><h4>全体员工工资结算</h4></div>
                            <div class="card-body">
                                <form id="wage-add-all-form"  class="row">
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">月份</span>
                                        <input class="form-control js-datetimepicker" type="text" id="month" name="month"
                                               placeholder="请选择月份" value="" data-side-by-side="true" data-locale="zh-cn" data-format="YYYY-MM" />
                                    </div>
                                    <div class="form-group col-md-12">
                                        <button type="button" class="btn btn-primary ajax-post" id="add-all-btn">结 算</button>
                                        <button type="button" class="btn btn-default" onclick="javascript:history.back(-1);return false;">返 回</button>
                                    </div>
                                </form>
                            </div>

                            <div style="width: 100%;height: 1px;border-top: 1px solid #0C0C0C"></div>

                            <div class="card-header"><h4>单个员工工资结算</h4></div>
                            <div class="card-body">
                                <form id="wage-add-person-form"  class="row">
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">选择员工信息</span>
                                        <select name="staff.id" class="form-control selectpicker" id="staff"
                                                data-live-search="true" data-max-options="1">
                                            <option value="">--请选择--</option>
                                            <#list staffList as staff>
                                                <option value="${staff.id}">${staff.jobNumber}-${staff.name}-${staff.department.name}</option>
                                            </#list>
                                        </select>
                                    </div>

                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">月份</span>
                                        <input class="form-control js-datetimepicker" type="text" id="month" name="month"
                                               placeholder="请选择月份" value="" data-side-by-side="true" data-locale="zh-cn" data-format="YYYY-MM" />
                                    </div>
                                    <div class="form-group col-md-12">
                                        <button type="button" class="btn btn-primary ajax-post" id="add-person-btn">结 算</button>
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

        $("#add-all-btn").click(function(){
            if(!checkForm("wage-add-person-form")){
                return;
            }
            var data = $("#wage-add-all-form").serialize();
            $.ajax({
                url:'calculation_all',
                type:'POST',
                data:data,
                dataType:'json',
                success:function(data){
                    if(data.code == 0){
                        showSuccessMsg('结算成功!',function(){
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

        $("#add-person-btn").click(function(){
            if(!checkForm("wage-add-person-form")){
                return;
            }
            var data = $("#wage-add-person-form").serialize();
            $.ajax({
                url:'calculation_person',
                type:'POST',
                data:data,
                dataType:'json',
                success:function(data){
                    if(data.code == 0){
                        showSuccessMsg('结算成功!',function(){
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