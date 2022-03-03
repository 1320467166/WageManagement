<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title>${siteName!""}|考核管理-${title!""}</title>
<#include "../common/header.ftl"/>
    <style>
        td {
            vertical-align: middle;
        }
        .table-th{
            text-align: center;
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
                            <div class="card-toolbar clearfix">
                                <form class="pull-right search-bar" method="get" action="list" role="form">
                                    <div class="input-group">
                                        <div class="input-group-btn">
                                            <button class="btn btn-default dropdown-toggle" id="search-btn"
                                                    data-toggle="dropdown" type="button" aria-haspopup="true"
                                                    aria-expanded="false">
                                                员工工号 <span class="caret"></span>
                                            </button>
                                            <ul class="dropdown-menu">
                                                <li><a tabindex="-1" href="javascript:void(0)"
                                                       data-field="title">员工工号</a></li>
                                            </ul>
                                        </div>
                                        <input type="text" class="form-control" value="${jobNumber!""}" name="jobNumber"
                                               placeholder="请输入员工工号">
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
                                            <th>
                                                <label class="lyear-checkbox checkbox-primary">
                                                    <input type="checkbox" id="check-all" name="checkAll"><span></span>
                                                </label>
                                            </th>
                                            <th class="table-th">头像</th>
                                            <th class="table-th">工号</th>
                                            <th class="table-th">姓名</th>
                                            <th class="table-th">性别</th>
                                            <th class="table-th">角色</th>
                                            <th class="table-th">学历</th>
                                            <th class="table-th">职称</th>
                                            <th class="table-th">岗位</th>
                                            <th class="table-th">部门</th>
                                            <th class="table-th">手机号码</th>
                                            <th class="table-th">紧急联系人</th>
                                            <th class="table-th">基本工资</th>
                                            <th class="table-th">入职时间</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                      <#if pageBean.content?size gt 0>
                    <#list pageBean.content as staff>
                        <tr>
                          <td style="vertical-align:middle;">
                            <label class="lyear-checkbox checkbox-primary">
                              <input type="checkbox" name="ids[]" value="${staff.id}"><span></span>
                            </label>
                          </td>
                          <td style="vertical-align:middle;">
                              <#if staff.headPic??>
                                  <#if staff.headPic?length gt 0>
                                  <img src="/photo/view?filename=${staff.headPic}" width="60px" height="60px">
                                  <#else>
                                  <img src="/admin/images/default-head.jpg" width="60px" height="60px">
                                  </#if>
                              </#if>
                          </td>
                          <td style="vertical-align:middle;">${staff.jobNumber}</td>
                          <td style="vertical-align:middle;">${staff.name}</td>
                          <td style="vertical-align:middle;">
                              <#if staff.sex == 1>
                              <font class="text-success">男</font>
                              <#elseif staff.sex == 2>
                              <font class="text-info">女</font>
                              <#else>
                              <font class="text-warning">未知</font>
                              </#if>
                          </td>
                            <td style="vertical-align:middle;" align="center">${staff.role.name}</td>
                          <td style="vertical-align:middle;" align="center">${staff.educationEnum.getValue()}</td>
                          <td style="vertical-align:middle;" align="center">${staff.jobTitle.name}</td>
                            <td style="vertical-align:middle;" align="center">${staff.position.name}</td>
                            <td style="vertical-align:middle;" align="center">${staff.department.name}</td>
                            <td style="vertical-align:middle;" align="center">${staff.mobile}</td>
                            <td style="vertical-align:middle;" align="center">${staff.emergencyContact}</td>
                            <td style="vertical-align:middle;" align="center">${staff.salary}</td>
                            <td style="vertical-align:middle;" style="vertical-align:middle;"><font class="text-success">${staff.entryTime}</font></td>
                        </tr>
                      </#list>
                      <#else>
                    <tr align="center">
                        <td colspan="14">这里空空如也！</td>
                    </tr>
                      </#if>
                                        </tbody>
                                    </table>
                                </div>
                <#if pageBean.total gt 0>
                <ul class="pagination ">
                  <#if pageBean.currentPage == 1>
                  <li class="disabled"><span>«</span></li>
                  <#else>
                  <li><a href="list?jobNumber=${jobNumber!""}&currentPage=1">«</a></li>
                  </#if>
                  <#list pageBean.currentShowPage as showPage>
                      <#if pageBean.currentPage == showPage>
                  <li class="active"><span>${showPage}</span></li>
                      <#else>
                  <li><a href="list?jobNumber=${jobNumber!""}&currentPage=${showPage}">${showPage}</a></li>
                      </#if>
                  </#list>
                  <#if pageBean.currentPage == pageBean.totalPage>
                  <li class="disabled"><span>»</span></li>
                  <#else>
                  <li><a href="list?jobNumber=${jobNumber!""}&currentPage=${pageBean.totalPage}">»</a></li>
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
<#include "../common/footer.ftl"/>
<script type="text/javascript" src="/admin/js/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="/admin/js/main.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {

    });

    //绩效考核
    function assessment(url){
        if ($("input[type='checkbox']:checked").length != 1) {
            showWarningMsg('请选择一条数据进行绩效考核！');
            return;
        }
        if ($("input[type='checkbox'][name='checkAll']:checked").length!=0) {
            showWarningMsg('请选择一条数据进行绩效考核！');
            return;
        }
        window.location.href = url + '?id=' + $("input[type='checkbox']:checked").val();
    }

    //绩效考核
    function annual(url){
        if ($("input[type='checkbox']:checked").length != 1) {
            showWarningMsg('请选择一条数据进行年度考核！');
            return;
        }
        if ($("input[type='checkbox'][name='checkAll']:checked").length!=0) {
            showWarningMsg('请选择一条数据进行年度考核！');
            return;
        }
        $.ajax({
            url:url,
            type:'POST',
            success: function (data) {
                if (data.code == 0) {
                    window.location.href = '/admin/assessment/annual_add' + '?id=' + $("input[type='checkbox']:checked").val();
                } else {
                    showErrorMsg(data.msg);
                }
            },
            error: function (data) {
                alert('网络错误!');
            }
        });
    }

    function del(url) {
        if ($("input[type='checkbox']:checked").length != 1) {
            showWarningMsg('请选择一条数据进行员工离职！');
            return;
        }
        if ($("input[type='checkbox'][name='checkAll']:checked").length!=0) {
            showWarningMsg('请选择一条数据进行员工离职！');
            return;
        }
        var id = $("input[type='checkbox']:checked").val();
        $.confirm({
            title: '确定离职？',
            content: '离职后数据不可恢复，请慎重！',
            buttons: {
                confirm: {
                    text: '确认',
                    action: function () {
                        deleteReq(id, url);
                    }
                },
                cancel: {
                    text: '关闭',
                    action: function () {

                    }
                }
            }
        });
    }

    //打开编辑页面
    function edit(url) {
        if ($("input[type='checkbox']:checked").length != 1) {
            showWarningMsg('请选择一条数据进行员工离职！');
            return;
        }
        if ($("input[type='checkbox'][name='checkAll']:checked").length != 0) {
            showWarningMsg('请选择一条数据进行员工离职！');
            return;
        }
        window.location.href = url + '?id=' + $("input[type='checkbox']:checked").val();
    }

    //调用删除方法
    function deleteReq(id, url) {
        $.ajax({
            url: url,
            type: 'POST',
            data: {id: id},
            dataType: 'json',
            success: function (data) {
                if (data.code == 0) {
                    showSuccessMsg('员工离职成功!', function () {
                        $("input[type='checkbox']:checked").parents("tr").remove();
                    })
                } else {
                    showErrorMsg(data.msg);
                }
            },
            error: function (data) {
                alert('网络错误!');
            }
        });
    }
</script>
</body>
</html>