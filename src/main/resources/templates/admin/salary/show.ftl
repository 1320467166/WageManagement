<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title>${siteName!""}|工资管理-工资详情</title>
    <#include "../common/header.ftl"/>
    <link href="/admin/select2/select2.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/admin/bootstrap-select/dist/css/bootstrap-select.min.css">

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
                            <div class="card-header"><h4>工资详情</h4></div>
                            <div class="card-body">
                                <div class="input-group m-b-10">
                                    <span class="input-group-addon">基本工资</span>
                                    <input type="text" class="form-control required"
                                           value="${salary.basicWage}" readonly/>
                                </div>
                                <div class="input-group m-b-10">
                                    <span class="input-group-addon">应发工资</span>
                                    <input type="text" class="form-control required"
                                           value="${salary.shouldSalary}" readonly/>
                                </div>
                                <div class="input-group m-b-10">
                                    <span class="input-group-addon">实发工资</span>
                                    <input type="text" class="form-control required"
                                           value="${salary.netPayroll}" readonly/>
                                </div>
                                <div class="input-group m-b-10">
                                    <span class="input-group-addon">餐饮补贴</span>
                                    <input type="text" class="form-control required"
                                           value="${salary.foodSubsidy}" readonly/>
                                </div>
                                <div class="input-group m-b-10">
                                    <span class="input-group-addon">岗位补贴</span>
                                    <input type="text" class="form-control required"
                                           value="${salary.positionSubsidy}" readonly/>
                                </div>
                                <div class="input-group m-b-10">
                                    <span class="input-group-addon">交通补贴</span>
                                    <input type="text" class="form-control required"
                                           value="${salary.travelAllowance}" readonly/>
                                </div>
                                <div class="input-group m-b-10">
                                    <span class="input-group-addon">出差补贴</span>
                                    <input type="text" class="form-control required"
                                           value="${salary.missionAllowance}" readonly/>
                                </div>
                                <div class="input-group m-b-10">
                                    <span class="input-group-addon">工龄奖金</span>
                                    <input type="text" class="form-control required"
                                           value="${salary.yearsBonus}" readonly/>
                                </div>
                                <div class="input-group m-b-10">
                                    <span class="input-group-addon">职称奖金</span>
                                    <input type="text" class="form-control required"
                                           value="${salary.jobTitleBonus}" readonly/>
                                </div>
                                <div class="input-group m-b-10">
                                    <span class="input-group-addon">加班奖金</span>
                                    <input type="text" class="form-control required"
                                           value="${salary.overTimeBonus}" readonly/>
                                </div>
                                <div class="input-group m-b-10">
                                    <span class="input-group-addon">全勤奖金</span>
                                    <input type="text" class="form-control required"
                                           value="${salary.attendanceBonus}" readonly/>
                                </div>
                                <div class="input-group m-b-10">
                                    <span class="input-group-addon">养老</span>
                                    <input type="text" class="form-control required"
                                           value="${salary.annuity}" readonly/>
                                </div>
                                <div class="input-group m-b-10">
                                    <span class="input-group-addon">医疗</span>
                                    <input type="text" class="form-control required"
                                           value="${salary.medical}" readonly/>
                                </div>
                                <div class="input-group m-b-10">
                                    <span class="input-group-addon">失业</span>
                                    <input type="text" class="form-control required"
                                           value="${salary.unemployment}" readonly/>
                                </div>
                                <div class="input-group m-b-10">
                                    <span class="input-group-addon">工伤</span>
                                    <input type="text" class="form-control required"
                                           value="${salary.occupationalInjury}" readonly/>
                                </div>
                                <div class="input-group m-b-10">
                                    <span class="input-group-addon">生育</span>
                                    <input type="text" class="form-control required"
                                           value="${salary.childbirth}" readonly/>
                                </div>
                                <div class="input-group m-b-10">
                                    <span class="input-group-addon">住房公积金</span>
                                    <input type="text" class="form-control required"
                                           value="${salary.housingFund}" readonly/>
                                </div>
                                <div class="input-group m-b-10">
                                    <span class="input-group-addon">迟到</span>
                                    <input type="text" class="form-control required"
                                           value="${salary.latePenalty}" readonly/>
                                </div>
                                <div class="input-group m-b-10">
                                    <span class="input-group-addon">早退</span>
                                    <input type="text" class="form-control required"
                                           value="${salary.leaveEarlyFine}" readonly/>
                                </div>
                                <div class="input-group m-b-10">
                                    <span class="input-group-addon">病假</span>
                                    <input type="text" class="form-control required"
                                           value="${salary.sick}" readonly/>
                                </div>
                                <div class="input-group m-b-10">
                                    <span class="input-group-addon">事假</span>
                                    <input type="text" class="form-control required"
                                           value="${salary.personalLeave}" readonly/>
                                </div>
                                <div class="input-group m-b-10">
                                    <span class="input-group-addon">缺勤</span>
                                    <input type="text" class="form-control required"
                                           value="${salary.absence}" readonly/>
                                </div>
                                <div class="input-group m-b-10">
                                    <span class="input-group-addon">个人所得税</span>
                                    <input type="text" class="form-control required"
                                           value="${salary.individualIncomeTax}" readonly/>
                                </div>
                                <div class="input-group m-b-10">
                                    <span class="input-group-addon">绩效奖金</span>
                                    <input type="text" class="form-control required"
                                           value="${salary.performance}" readonly/>
                                </div>
                                <div class="input-group m-b-10">
                                    <span class="input-group-addon">年度考核奖金</span>
                                    <input type="text" class="form-control required"
                                           value="${salary.annualAssessmentBonus}" readonly/>
                                </div>
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
<script type="text/javascript">

</script>
</body>
</html>