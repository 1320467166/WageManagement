<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
    <title>${siteName!""}|工龄管理-编辑工龄补贴</title>
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
                            <div class="card-header"><h4>编辑工龄补贴</h4></div>
                            <div class="card-body">
                                <form  id="work-years-edit-form" class="row">
                                    <input  type="hidden" name="id" id="id" value="${workYears.id}"/>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">新工龄</span>
                                        <input type="number" class="form-control required" id="edit-years" name="years" value="${workYears.years!"0"}" placeholder="请输入新工龄" tips="请填写新工龄"  onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)" minlength="1" maxlength="7"
                                               onblur="this.v();" min="0"/>
                                    </div>

                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">补贴</span>
                                        <input type="number" id="edit-subsidy" name="subsidy" value="${workYears.subsidy!"0"}"
                                               class="form-control required" oninput="if(value>10000)value=10000;if (value<0)value=0" min="0"
                                               onkeyup="number(this)"  placeholder="请输入补贴" tips="请填写补贴"/>
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
            if(!checkForm("work-years-edit-form")){
                return;
            }
            var data = $("#work-years-edit-form").serialize();
            $.ajax({
                url:'edit',
                type:'POST',
                data:data,
                dataType:'json',
                success:function(data){
                    if(data.code == 0){
                        showSuccessMsg('工龄补贴编辑成功!',function(){
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