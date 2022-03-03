<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title>${siteName!""}|考核管理-绩效考核</title>
    <#include "../common/header.ftl"/>
    <link href="/admin/select2/select2.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/admin/bootstrap-select/dist/css/bootstrap-select.min.css">
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
                            <div class="card-header"><h4>绩效考核</h4></div>
                            <div class="card-body">
                                <form id="assess-target-add-form" class="row">
                                    <input type="hidden" name="staff.id" value="${staff.id}"/>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">员工名称</span>
                                        <input type="text" class="form-control required" id="add-name"
                                               value="${staff.name}" readonly/>
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">年份</span>
                                        <input class="form-control js-datetimepicker" type="number" id="add-year"
                                               name="year"
                                               placeholder="请选择年份" value="" data-side-by-side="true" data-locale="zh-cn"
                                               data-format="YYYY"/>
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">月份</span>
                                        <input class="form-control js-datetimepicker" type="number" id="add-month"
                                               name="month"
                                               placeholder="请选择月份" value="" data-side-by-side="true" data-locale="zh-cn"
                                               data-format="MM"/>
                                    </div>
                                    <#if assessTargetList??>
                                        <#list assessTargetList as target>
                                        <div data-targetId="${target.id}" class="ass-target">
                                            <div class="card-header"><h4>${target.name}</h4></div>
                                            <div class="input-group m-b-10">
                                                <span class="input-group-addon">等级A(85~100)</span>
                                                <input type="text" class="form-control required" id="add-name"
                                                       value="${target.optionA}" readonly/>
                                            </div>
                                            <div class="input-group m-b-10">
                                                <span class="input-group-addon">等级B(70~84)</span>
                                                <input type="text" class="form-control required" id="add-name"
                                                       value="${target.optionB}" readonly/>
                                            </div>
                                            <div class="input-group m-b-10">
                                                <span class="input-group-addon">等级C(50~69)</span>
                                                <input type="text" class="form-control required" id="add-name"
                                                       value="${target.optionC}" readonly/>
                                            </div>
                                            <div class="input-group m-b-10">
                                                <span class="input-group-addon">等级D(0~49)</span>
                                                <input type="text" class="form-control required" id="add-name"
                                                       value="${target.optionD}" readonly/>
                                            </div>
                                            <div class="input-group m-b-10">
                                                <span class="input-group-addon">指标打分</span>
                                                <input type="number" min="0" max="100" class="form-control required"
                                                       id="add-score" value="0"/>
                                            </div>
                                        </div>
                                        </#list>
                                    </#if>
                                    <div class="form-group col-md-12">
                                        <button type="button" class="btn btn-primary ajax-post"
                                                id="target-form-submit-btn">确 定
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
<script type="text/javascript" src="/admin/select2/select2.min.js"></script>
<script type="text/javascript" src="/admin/bootstrap-select/js/bootstrap-select.js"></script>
<script type="text/javascript" src="/admin/bootstrap-select/js/i18n/defaults-zh_CN.js"></script>
<!--时间选择插件-->
<script src="/admin/js/bootstrap-datetimepicker/moment.min.js"></script>
<script src="/admin/js/bootstrap-datetimepicker/bootstrap-datetimepicker.min.js"></script>
<script src="/admin/js/bootstrap-datetimepicker/locale/zh-cn.js"></script>
<!--日期选择插件-->
<script src="/admin/js/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
<script src="/admin/js/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script type="text/javascript">

    $(document).ready(function () {
        //提交按钮监听事件
        $("#target-form-submit-btn").click(function () {
            var year = $("#add-year").val();
            if (year == null || year == "") {
                showWarningMsg("年份不能为空");
                return;
            }
            var month=$("#add-month").val();
            if(month==null || month==""){
                showWarningMsg("月份不能为空");
                return;
            }
           var arr=new Array();
            var data = $("#assess-target-add-form").serialize();
            $(".ass-target").each(function(i,e){
                var targetId=$(e).attr("data-targetId");
              var score=$(e).find("input[type='number']").val();
              var option={};
              option.targetId=targetId;
              option.score=score;
              arr.push(option);
            });
            var assTarget=JSON.stringify(arr);
            var datas=data+"&assTarget="+assTarget;
             $.ajax({
                     url:'achieve_add',
                     type:'POST',
                     data:datas,
                     dataType:'json',
                     success:function(data){
                         if(data.code == 0){
                             showSuccessMsg('绩效考核成功!',function(){
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