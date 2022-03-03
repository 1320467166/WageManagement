<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
    <title>${siteName!""}|指标管理-编辑指标</title>
    <#include "../common/header.ftl"/>
    <link href="/admin/select2/select2.min.css" rel="stylesheet">
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
                            <div class="card-header"><h4>编辑指标</h4></div>
                            <div class="card-body">
                                <form  id="assess-target-edit-form" class="row">
                                    <input type="hidden" name="id" id="id" value="${assessTarget.id}"/>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">指标名称</span>
                                        <input type="text" class="form-control required" id="edit-name" name="name" value="${assessTarget.name}" placeholder="请输入指标名称" tips="请填写指标名称" minlength="1" maxlength="15"/>
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">指标分类</span>
                                        <select name="targetClassify" class="form-control selected" id="edit-targetClassify" tips="请选择指标分类">
                                            <option value="-1">--请选择指标分类--</option>
                    	<#list targetClassifyList as targetClassify>
                            <option value="${targetClassify}" <#if targetClassify.code==assessTarget.targetClassify.code>selected</#if>>${targetClassify.value}</option>
                        </#list>
                                        </select>
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">指定部门</span>
                                        <select class="form-control selectRequired selectpicker select2-multiple"
                                                data-none-selected-text="请选择部门" multiple id="edit-departments" name="departments"
                                                tips="请选择部门">
                                            <#list departmentList as department>
                                                <option value="${department.id}">${department.name}</option>
                                            </#list>
                                        </select>
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">等级A</span>
                                        <textarea class="form-control required" id="edit-optionA" name="optionA" rows="6" placeholder="请输入指标说明.." tips="请输入等级A指标说明" >${assessTarget.optionA}</textarea>
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">等级B</span>
                                        <textarea class="form-control required" id="edit-optionB" name="optionB" rows="6" placeholder="请输入指标说明.." tips="请输入等级B指标说明">${assessTarget.optionB}</textarea>
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">等级C</span>
                                        <textarea class="form-control required" id="edit-optionC" name="optionC" rows="6" placeholder="请输入指标说明.." tips="请输入等级C指标说明">${assessTarget.optionC}</textarea>
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">等级D</span>
                                        <textarea class="form-control required" id="edit-optionD" name="optionD" rows="6" placeholder="请输入指标说明.." tips="请输入等级D指标说明">${assessTarget.optionD}</textarea>
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">权重分数</span>
                                        <input type="number" min="0" class="form-control required" id="score" name="score" value="${assessTarget.score}" placeholder="请输入权重分数" tips="请输入权重分数" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)"
                                               onblur="this.v();"/>
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
<script type="text/javascript" src="/admin/select2/select2.min.js"></script>
<script type="text/javascript" src="/admin/bootstrap-select/js/bootstrap-select.js"></script>
<script type="text/javascript" src="/admin/bootstrap-select/js/i18n/defaults-zh_CN.js"></script>
<script type="text/javascript">



    $(document).ready(function(){
        var departmentId=${departments!"[]"};
            $('#edit-departments').selectpicker('val',departmentId);
        //提交按钮监听事件
        $("#edit-form-submit-btn").click(function(){
            if(!checkForm("assess-target-edit-form")){
                return;
            }
            if($("#edit-targetClassify").val()==-1){
                showWarningMsg("请选择指标分类!");
                return;
            }
            if($("#edit-departments").val()==null){
                showWarningMsg("请选择指定部门!");
                return;
            }
            var data = $("#assess-target-edit-form").serialize();
         $.ajax({
                url:'edit',
                type:'POST',
                data:data,
                dataType:'json',
                success:function(data){
                    if(data.code == 0){
                        showSuccessMsg('指标编辑成功!',function(){
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