<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
    <title>${siteName!""}|职称管理-编辑职称</title>
    <#include "../common/header.ftl"/>

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
                            <div class="card-header"><h4>编辑职称</h4></div>
                            <div class="card-body">
                                <form action="add" id="department-edit-form" method="post" class="row">
                                    <input type="hidden" name="id" value="${jobTitle.id}">
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">职称名称</span>
                                        <input type="text" class="form-control required" id="name" name="name" value="${jobTitle.name}" placeholder="请填写职称名称" tips="请填写职称名称" />
                                    </div>

                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">奖金</span>
                                        <input type="number" id="bonus" name="bonus" value="${jobTitle.bonus}"
                                               class="form-control required" oninput="if(value>100000)value=100000;if (value<0)value=0"
                                               onkeyup="number(this)"  placeholder="请填写奖金" tips="请填写奖金"/>
                                    </div>
                                    <div class="form-group col-md-12">
                                        <button type="button" class="btn btn-primary ajax-post" id="edit-form-submit-btn">确 定</button>
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
        $("#edit-form-submit-btn").click(function(){
            if(!checkForm("department-edit-form")){
                return;
            }
            var data = $("#department-edit-form").serialize();
            $.ajax({
                url:'edit',
                type:'POST',
                data:data,
                dataType:'json',
                success:function(data){
                    if(data.code == 0){
                        showSuccessMsg('编辑成功!',function(){
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