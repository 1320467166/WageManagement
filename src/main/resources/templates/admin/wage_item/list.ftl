<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
    <title>${siteName!""}|工资项管理-管理工资项</title>
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
                            <div class="card-header"><h4>工资项</h4></div>
                            <div class="card-body">
                                <form id="wage-item-add-form"  class="row">
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">迟到罚金</span>
                                        <input type="number" min="0" class="form-control required" id="latePenalty" name="latePenalty" value="0" placeholder="请输入迟到罚金" tips="请输入迟到罚金" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)"
                                               onblur="this.v();" />
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">早退罚金</span>
                                        <input type="number" min="0" class="form-control required" id="leaveEarlyFine" name="leaveEarlyFine" value="0" placeholder="请输入早退罚金" tips="请输入早退罚金" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)"
                                               onblur="this.v();" />
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">出差补贴</span>
                                        <input type="number" min="0" class="form-control required" id="missionAllowance" name="missionAllowance" value="0" placeholder="请输入出差补贴" tips="请输入出差补贴" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)"
                                               onblur="this.v();"/>
                                    </div>
                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">全勤奖金</span>
                                        <input type="number" min="0" class="form-control required" id="attendanceBonus" name="attendanceBonus" value="0" placeholder="请输入全勤奖金" tips="请输入全勤奖金" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)"
                                               onblur="this.v();"/>
                                    </div>

                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">餐饮补贴</span>
                                        <input type="number" min="0" class="form-control required" id="foodSubsidy" name="foodSubsidy" value="0" placeholder="请输入餐饮补贴" tips="请输入餐饮补贴" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)"
                                               onblur="this.v();"/>
                                    </div>

                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">交通补贴</span>
                                        <input type="number" min="0" class="form-control required" id="travelAllowance" name="travelAllowance" value="0" placeholder="请输入交通补贴" tips="请输入交通补贴"onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)"
                                               onblur="this.v();" />
                                    </div>

                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">加班补贴</span>
                                        <input type="number" min="0" class="form-control required" id="overTimeAllowance" name="overTimeAllowance" value="0" placeholder="请输入加班补贴" tips="请输入加班补贴" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)"
                                               onblur="this.v();"/>
                                    </div>

                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">绩效奖金</span>
                                        <input type="number" min="0" class="form-control required" id="performance" name="performance" value="0" placeholder="请输入绩效奖金" tips="请输入绩效奖金" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)"
                                               onblur="this.v();"/>
                                    </div>

                                    <div class="input-group m-b-10">
                                        <span class="input-group-addon">年度考核奖金</span>
                                        <input type="number" min="0" class="form-control required" id="AnnualAssessmentBonus" name="AnnualAssessmentBonus" value="0" placeholder="请输入年度考核奖金" tips="请输入年度考核奖金" onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)"
                                               onblur="this.v();"/>
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
            if(!checkForm("wage-item-add-form")){
                return;
            }
            var data = $("#wage-item-add-form").serialize();
            $.ajax({
                url:'add',
                type:'POST',
                data:data,
                dataType:'json',
                success:function(data){
                    if(data.code == 0){

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