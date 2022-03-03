<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
    <title>${siteName!""}|年度考勤管理-${title!""}</title>
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
                                                年份
                                            </button>
                                            <ul class="dropdown-menu">
                                                <li> <a tabindex="-1" href="javascript:void(0)" data-field="title">年份</a> </li>
                                            </ul>
                                        </div>
                                        <input type="text"  class="form-control js-datetimepicker"
                                               value="${year!""}"
                                               name="year" placeholder="年份"
                                               data-side-by-side="true" data-locale="zh-cn" data-format="YYYY">
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
                                            <th style="text-align: center;vertical-align: middle">年份</th>
                                            <th style="text-align: center;vertical-align: middle">姓名</th>
                                            <th style="text-align: center;">工号</th>
                                            <th style="text-align: center;">请假天数</th>
                                            <th style="text-align: center;">缺勤天数</th>
                                            <th style="text-align: center;">加班小时</th>
                                            <th style="text-align: center;">绩效分数</th>
                                            <th style="text-align: center;">总评分</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <#if pageBean.content?size gt 0>
                                            <#list pageBean.content as annualAssessment>
                                                <tr>
                                                    <td style="vertical-align:middle;text-align: center;">${annualAssessment.year}</td>
                                                    <td style="vertical-align:middle;text-align: center;">${annualAssessment.staff.name}</td>
                                                    <td style="vertical-align:middle;text-align: center;">${annualAssessment.staff.jobNumber}</td>
                                                    <td style="vertical-align:middle;text-align: center;">${annualAssessment.leaveDays}</td>
                                                    <td style="vertical-align:middle;text-align: center;">${annualAssessment.absenceDays}</td>
                                                    <td style="vertical-align:middle;text-align: center;">${annualAssessment.overtimeHours}</td>
                                                    <td style="vertical-align:middle;text-align: center;">${annualAssessment.performanceScore}</td>
                                                    <td style="vertical-align:middle;text-align: center;">${annualAssessment.grade}</td>
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
                                                <a href="list?jobNumber=${jobNumber!""}&year=${year!""}&currentPage=1">«</a>
                                            </li>
                                        </#if>
                                        <#list pageBean.currentShowPage as showPage>
                                            <#if pageBean.currentPage == showPage>
                                                <li class="active"><span>${showPage}</span></li>
                                            <#else>
                                                <li>
                                                    <a href="list?jobNumber=${jobNumber!""}&year=${year!""}&currentPage=${showPage}">${showPage}</a>
                                                </li>
                                            </#if>
                                        </#list>
                                        <#if pageBean.currentPage == pageBean.totalPage>
                                            <li class="disabled"><span>»</span></li>
                                        <#else>
                                            <li>
                                                <a href="list?jobNumber=${jobNumber!""}&year=${year!""}&currentPage=${pageBean.totalPage}">»</a>
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
</script>
</body>
</html>