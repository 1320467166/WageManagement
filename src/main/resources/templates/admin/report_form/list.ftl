<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
    <title>${siteName!""}|报表管理-${title!""}</title>
    <#include "../common/header.ftl"/>
    <!--时间选择插件-->
    <link rel="stylesheet" href="/admin/js/bootstrap-datetimepicker/bootstrap-datetimepicker.min.css">
    <!--日期选择插件-->
    <link rel="stylesheet" href="/admin/js/bootstrap-datepicker/bootstrap-datepicker3.min.css">
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

                    <div class="col-lg-6">
                        <div class="card">
                            <div class="card-header"><h4>各部门员工数量统计</h4></div>
                            <div class="card-body">
                                <canvas id="chart-vbar-1" width="400" height="250"></canvas>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-6">
                        <div class="card">
                            <div class="card-header"><h4>各部门基本平均工资</h4></div>
                            <div class="card-body">
                                <canvas id="chart-line-2" width="400" height="250"></canvas>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-6">
                        <div class="card">
                            <div class="card-header">
                                <h4>每年12月份实发总工资</h4>
                                <div class="input-group" style="width: 20%">
                                    <span class="input-group-addon" id="basic-addon3">年份</span>
                                    <input type="number"  class="form-control js-datetimepicker" id="payroll-year"
                                           value="${years!""}"
                                           name="year" placeholder="年份"
                                           data-side-by-side="true" data-locale="zh-cn" data-format="YYYY">
                                </div>
                                <button id="payroll-year-btn" class="btn btn-primary" style="margin: 20px 10px;">搜索</button>
                            </div>
                            <div class="card-body">
                                <canvas id="chart-hbar-1" width="400" height="250"></canvas>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-6">
                        <div class="card">
                            <div class="card-header">
                                <h4>各部门实发平均工资</h4>
                                <div class="input-group" style="width: 20%">
                                    <span class="input-group-addon" id="basic-addon3">年份</span>
                                    <input type="number"  class="form-control js-datetimepicker" id="search-year"
                                           value="${years!""}"
                                           name="year" placeholder="年份"
                                           data-side-by-side="true" data-locale="zh-cn" data-format="YYYY">
                                </div>
                                <div class="input-group" style="width: 20%">
                                    <span class="input-group-addon" id="basic-addon3">月份</span>
                                    <input class="form-control js-datetimepicker" type="number" id="search-month"
                                           name="month"
                                           placeholder="请选择月份" value="${months}" data-side-by-side="true" data-locale="zh-cn"
                                           data-format="MM"/>
                                </div>
                                <button id="search-btn" class="btn btn-primary" style="margin: 20px 10px;">搜索</button>
                            </div>
                            <div class="card-body">
                                <canvas id="chart-doughnut" width="280" height="280"></canvas>
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
<script type="text/javascript" src="/admin/js/Chart.js"></script>
<!--时间选择插件-->
<script src="/admin/js/bootstrap-datetimepicker/moment.min.js"></script>
<script src="/admin/js/bootstrap-datetimepicker/bootstrap-datetimepicker.min.js"></script>
<script src="/admin/js/bootstrap-datetimepicker/locale/zh-cn.js"></script>
<!--日期选择插件-->
<script src="/admin/js/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
<script src="/admin/js/bootstrap-datepicker/locales/bootstrap-datepicker.zh-CN.min.js"></script>
<script type="text/javascript">
    new Chart($("#chart-vbar-1"), {
        type: 'bar',
        data: {
            labels: [ <#if countDepartments?size gt 0>
                <#list countDepartments as count>
                    '${count[1].name}',
                </#list>
            </#if>],
            datasets: [{
                label: "人数",
                backgroundColor: "rgba(51,202,185,0.5)",
                borderColor: "rgba(0,0,0,0)",
                hoverBackgroundColor: "rgba(51,202,185,0.7)",
                hoverBorderColor: "rgba(0,0,0,0)",
                data: [
                     <#if countDepartments?size gt 0>
                         <#list countDepartments as count>
                    '${count[0]}',
                         </#list>
                     </#if>
                ]
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });



    new Chart($("#chart-line-2"), {
        type: 'line',
        data: {
            labels: [
                <#if avgDepartments?size gt 0>
                    <#list avgDepartments as department>
                     '${department[1].name}',
                    </#list>
                </#if>
            ],
            datasets: [{
                label: "平均基本工资",
                fill: false,
                borderWidth: 3,
                pointRadius: 5,
                borderColor: "#9966ff",
                pointBackgroundColor: "#9966ff",
                pointBorderColor: "#9966ff",
                pointHoverBackgroundColor: "#fff",
                pointHoverBorderColor: "#9966ff",
                data: [
                     <#if avgDepartments?size gt 0>
                         <#list avgDepartments as department>
                           '${department[0]}',
                         </#list>
                     </#if>
                ]
            }]
        },
        options: {
            legend: {
                display: false
            },
        }
    });
    new Chart($("#chart-doughnut"), {
        type: 'doughnut',
        data: {
            labels: [
                     <#list salaryList as salary>
                           '${salary[1]}',
                     </#list>
            ],
            datasets: [{
                data: [
                         <#list salaryList as salary>
                        '${salary[0]}',
                         </#list>
                ],
                backgroundColor: ['rgba(255,99,132,1)', 'rgba(54, 162, 235, 1)', 'rgba(255, 206, 86, 1)']
            }]
        },
        options: {
            responsive: false
        }
    });

    new Chart($("#chart-hbar-1"), {
        type: 'horizontalBar',
        data: {
            labels: [
                <#list countPayRoll as payRoll>
                           '${payRoll[0]}月',
                </#list>
            ],
            datasets: [{
                label: "实发总工资",
                backgroundColor: "rgba(51,202,185,0.5)",
                borderColor: "rgba(0,0,0,0)",
                hoverBackgroundColor: "rgba(51,202,185,0.7)",
                hoverBorderColor: "rgba(0,0,0,0)",
                data: [
                     <#list countPayRoll as payRoll>
                           '${payRoll[1]}',
                     </#list>
                ]
            }]
        },
        options: {
            scales: {
                xAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            }
        }
    });


    document.getElementById("search-btn").onclick = searchBtn;
    function searchBtn(){
        var year=document.getElementById("search-year").value;
       var month=document.getElementById("search-month").value;
      window.location.href="list?years="+year+"&months="+month;
    }
    document.getElementById("payroll-year-btn").onclick=searchPayRoll;
    function searchPayRoll(){
       var year=document.getElementById("payroll-year").value;
        window.location.href="list?years="+year;
    }
</script>
</body>
</html>