<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
    <title>${siteName!""}|考勤管理-${title!""}</title>
    <#include "../common/header.ftl"/>
    <!--时间选择插件-->
    <link rel="stylesheet" href="/admin/js/bootstrap-datetimepicker/bootstrap-datetimepicker.min.css">
    <!--日期选择插件-->
    <link rel="stylesheet" href="/admin/js/bootstrap-datepicker/bootstrap-datepicker3.min.css">
    <link href="/admin/css/style.min.css" rel="stylesheet">


    <style>
        td{
            vertical-align:middle;
        }
    </style>
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
                            <div class="card-toolbar clearfix">
                                <form class="pull-right search-bar" method="get" action="list" role="form">
                                    <div class="input-group">
                                        <div class="input-group-btn">
                                            <button  style="position: absolute;left: -266px;top: 0px;" class="btn btn-default dropdown-toggle" id="search-btn" data-toggle="dropdown" type="button" aria-haspopup="true" aria-expanded="false">
                                                员工号 <span class="caret"></span>
                                            </button>
                                            <ul class="dropdown-menu">
                                                <li> <a tabindex="-1" href="javascript:void(0)" data-field="title">员工号</a> </li>
                                            </ul>
                                        </div>
                                        <input type="text" style="position: absolute;left: -180px;top: 0px; width:166px" class="form-control" value="${jobNumber!""}" name="jobNumber" placeholder="请输入员工号">

                                        <div  class="input-group-btn">
                                            <button class="btn btn-default dropdown-toggle" id="search-btn" data-toggle="dropdown" type="button" aria-haspopup="true" aria-expanded="false">
                                                日期
                                            </button>
                                            <ul class="dropdown-menu">
                                                <li> <a tabindex="-1" href="javascript:void(0)" data-field="title">日期</a> </li>
                                            </ul>
                                        </div>
                                        <input type="text"  class="form-control js-datetimepicker"
                                               value="${month!""}"
                                               name="month" placeholder="日期"
                                               data-side-by-side="true" data-locale="zh-cn" data-format="YYYY-MM">
                                        <span class="input-group-btn">
                                          <button class="btn btn-primary" type="submit">搜索</button>
                                        </span>
                                    </div>
                                </form>
                                <#include "../common/third-menu.ftl"/>
                            </div>
                            <div class="card-body">

                                <div class="table-responsive">
                                    <table class="table table-bordered">
                                        <thead>
                                        <tr>
                                            <th rowspan="2" style="text-align: center;vertical-align: middle">
                                                <label class="lyear-checkbox checkbox-primary">
                                                    <input type="checkbox" id="check-all" name="checkAll"><span></span>
                                                </label>
                                            </th>
                                            <th rowspan="2" style="text-align: center;vertical-align: middle">月份</th>
                                            <th rowspan="2" style="text-align: center;vertical-align: middle">姓名</th>
                                            <th colspan="2" style="text-align: center;">员工信息</th>
                                            <th colspan="2" style="text-align: center;">请假天数</th>
                                            <th colspan="3" style="text-align: center;">加班小时</th>
                                            <th colspan="4" style="text-align: center;">考勤情况</th>
                                        </tr>
                                        <tr>
                                            <th style="text-align: center;">工号</th>
                                            <th style="text-align: center;">部门</th>
                                            <th style="text-align: center;">病假</th>
                                            <th style="text-align: center;">事假</th>
                                            <th style="text-align: center;">平时加班</th>
                                            <th style="text-align: center;">周末加班</th>
                                            <th style="text-align: center;">节假日加班</th>
                                            <th style="text-align: center;">迟到次数</th>
                                            <th style="text-align: center;">早退次数</th>
                                            <th style="text-align: center;">缺勤天数</th>
                                            <th style="text-align: center;">出差天数</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <#if pageBean.content?size gt 0>
                                            <#list pageBean.content as attendance>
                                                <tr>
                                                    <td style="vertical-align:middle;">
                                                        <label class="lyear-checkbox checkbox-primary">
                                                            <input type="checkbox" name="ids[]" value="${attendance.id}"><span></span>
                                                        </label>
                                                    </td>
                                                    <td style="vertical-align:middle;text-align: center;">${attendance.year}-${attendance.monthOfDay}</td>
                                                    <td style="vertical-align:middle;text-align: center;">${attendance.staff.name}</td>
                                                    <td style="vertical-align:middle;text-align: center;">${attendance.jobNumber}</td>
                                                    <td style="vertical-align:middle;text-align: center;">${attendance.staff.department.name}</td>
                                                    <td style="vertical-align:middle;text-align: center;">${attendance.daysSick}</td>
                                                    <td style="vertical-align:middle;text-align: center;">${attendance.personalLeaveDays}</td>
                                                    <td style="vertical-align:middle;text-align: center;">${attendance.overtimeHours}</td>
                                                    <td style="vertical-align:middle;text-align: center;">${attendance.overtimeOnWeekends}</td>
                                                    <td style="vertical-align:middle;text-align: center;">${attendance.holidayOvertimeHours}</td>
                                                    <td style="vertical-align:middle;text-align: center;">${attendance.lateNumber}</td>
                                                    <td style="vertical-align:middle;text-align: center;">${attendance.leaveEarlyTimes}</td>
                                                    <td style="vertical-align:middle;text-align: center;">${attendance.absenceDays}</td>
                                                    <td style="vertical-align:middle;text-align: center;">${attendance.travelDays}</td>
                                                </tr>
                                            </#list>
                                        <#else>
                                            <tr align="center"><td colspan="15">这里空空如也！</td></tr>
                                        </#if>
                                        </tbody>
                                    </table>
                                </div>
                                <#if pageBean.total gt 0>
                                    <ul class="pagination ">
                                        <#if pageBean.currentPage == 1>
                                            <li class="disabled"><span>«</span></li>
                                        <#else>
                                            <li>
                                                <a href="list?jobNumber=${jobNumber!""}&month=${month!""}&currentPage=1">«</a>
                                            </li>
                                        </#if>
                                        <#list pageBean.currentShowPage as showPage>
                                            <#if pageBean.currentPage == showPage>
                                                <li class="active"><span>${showPage}</span></li>
                                            <#else>
                                                <li>
                                                    <a href="list?jobNumber=${jobNumber!""}&month=${month!""}&currentPage=${showPage}">${showPage}</a>
                                                </li>
                                            </#if>
                                        </#list>
                                        <#if pageBean.currentPage == pageBean.totalPage>
                                            <li class="disabled"><span>»</span></li>
                                        <#else>
                                            <li>
                                                <a href="list?jobNumber=${jobNumber!""}&month=${month!""}&currentPage=${pageBean.totalPage}">»</a>
                                            </li>
                                        </#if>
                                        <li><span>共${pageBean.totalPage}页,${pageBean.total}条数据</span></li>
                                    </ul>
                                </#if>
                            </div>
                        </div>
                    </div>

                </div>

            </div>

        </main>
        <!--End 页面主要内容-->
    </div>
</div>
<input type="file" id="file" name="file" onchange="uploadExcel()" style="display:none;"/>
<#include "../common/footer.ftl"/>
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

    });
    function del(url){
        if($("input[type='checkbox']:checked").length != 1){
            showWarningMsg('请选择一条数据进行删除！');
            return;
        }
        if($("input[type='checkbox'][name='checkAll']:checked").length!=0){
            showWarningMsg('请选择一条数据进行删除！');
            return;
        }
        var id = $("input[type='checkbox']:checked").val();
        $.confirm({
            title: '确定删除？',
            content: '删除后数据不可恢复，请慎重！',
            buttons: {
                confirm: {
                    text: '确认',
                    action: function(){
                        deleteReq(id,url);
                    }
                },
                cancel: {
                    text: '关闭',
                    action: function(){

                    }
                }
            }
        });
    }
    //打开编辑页面
    function edit(url){
        if($("input[type='checkbox']:checked").length != 1){
            showWarningMsg('请选择一条数据进行编辑！');
            return;
        }
        if($("input[type='checkbox'][name='checkAll']:checked").length!=0){
            showWarningMsg('请选择一条数据进行编辑！');
            return;
        }
        window.location.href = url + '?id=' + $("input[type='checkbox']:checked").val();
    }
    //调用删除方法
    function deleteReq(id,url){
        $.ajax({
            url:url,
            type:'POST',
            data:{id:id},
            dataType:'json',
            success:function(data){
                if(data.code == 0){
                    showSuccessMsg('删除成功!',function(){
                        $("input[type='checkbox']:checked").parents("tr").remove();
                    })
                }else{
                    showErrorMsg(data.msg);
                }
            },
            error:function(data){
                alert('网络错误!');
            }
        });
    }

    function poiImport(url)
    {
        $("#file").click();
    }

    function uploadExcel()
    {
        var formData = new FormData();
        formData.append('file', document.getElementById("file").files[0]);
        $.ajax({
            url:'import',
            contentType:false,
            processData:false,
            data:formData,
            type:'POST',
            success:function(data){
                if(data.code == 0){
                    showSuccessMsg('导入成功!',function(){
                        location.href = "list";
                    })
                }else{
                    showErrorMsg(data.msg);
                }
            },
            error:function(data){
                alert('网络错误!');
            }
        });
    }
</script>
</body>
</html>