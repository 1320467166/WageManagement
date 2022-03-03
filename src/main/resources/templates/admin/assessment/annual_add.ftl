<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
    <title>${siteName!""}|考核管理-年度考核</title>
    <#include "../common/header.ftl"/>
    <link href="/admin/select2/select2.min.css" rel="stylesheet">
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
                            <div class="card-header"><h4>年度考核</h4></div>
                            <div class="card-body">
                                <form  id="assess-target-add-form" class="row">
                                    <input type="hidden" name="staffId" value="${staff.id}"/>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">员工名称</span>
                                        <input type="text" class="form-control required" value="${staff.name}" readonly />
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">年份</span>
                                        <input type="text" class="form-control required" value="${year}" readonly />
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">绩效分数</span>
                                        <input type="text" class="form-control required" value="${performanceScore}" readonly />
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">请假天数</span>
                                        <input type="text" class="form-control required" value="${leaveDays}" readonly />
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">缺勤天数</span>
                                        <input type="text" class="form-control required" value="${absenceDays}" readonly />
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">加班小时</span>
                                        <input type="text" class="form-control required" value="${overTimeHours}" readonly />
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">评分</span>
                                        <input type="number" min="0" max="100" class="form-control required" id="grade" name="grade" value="" tips="请填写评分"/>
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
<script type="text/javascript" src="/admin/js/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="/admin/js/main.min.js"></script>
<script type="text/javascript">


    $(document).ready(function(){
        //提交按钮监听事件
        $("#add-form-submit-btn").click(function(){
            if(!checkForm("assess-target-add-form")){
                return;
            }

            var data = $("#assess-target-add-form").serialize();
            $.ajax({
                url:'/admin/annual_assessment/add',
                type:'POST',
                data:data,
                dataType:'json',
                success:function(data){
                    if(data.code == 0){
                        showSuccessMsg('评分成功!',function(){
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