<#assign base=request.contextPath />
<@screen id="ywDistributorInsert" title="系统数据库配置页面" places=[{"name":"分销商信息","href":"${base}/sales/ywDistributor.htm"},{"name":"添加/修改","href":"#"}]>
<form id="ywDistributorForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type!''}"/>
    <input id="dis_id" name="dis_id" hidden="true" valid="NotBlank" placeholder="请输入编号" type="text" class="dfinput" <#if op_type == "2">readonly="readonly"</#if> value="${ywDistributor.dis_id!''}"/>
    <div class="formtitle">
		<span class="formspan">分销人详情信息</span>
	</div>
    <div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>昵称</label>
		<input id="nickname" type="text" class="dfinput" <#if op_type == "2">readonly="readonly"</#if> value="${nickname!''}"/>
		<i id="dis_name"></i>
	
		<label for="level" class="label"><em style="color:red;">*</em>真实姓名</label>
		<input id="dis_name" name="dis_name" type="text" class="dfinput" <#if op_type == "2">readonly="readonly"</#if> value="${ywDistributor.dis_name!''}"/>
		<i id="dis_name"></i>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>类型</label>
		<@select valid="NotBlank" placeholder="请输入类型" name="dis_type" dic_key="1006" value="${ywDistributor.dis_type!''}" />
	
		<label for="level" class="label"><em style="color:red;">*</em>性别</label>
		<@select valid="NotBlank" placeholder="请输入性别" name="dis_sex" dic_key="1011" value="${ywDistributor.dis_sex!''}" />
	</div>
	<div class="form-group">
		<label for="level" class="label">推荐码</label>
		<input id="dis_parentid" name="dis_parentid" type="text" class="dfinput" <#if op_type == "2">readonly="readonly"</#if> value="${ywDistributor.dis_parentid!''}"/>
	
		<label for="level" class="label">推荐人昵称</label>
		<input id="pid_nickname" name="pid_nickname" type="text" class="dfinput" <#if op_type == "2">readonly="readonly"</#if> value="${pid_nickname!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>分销等级</label>
		<input id="dis_level" name="dis_level" type="text" class="dfinput" dic_key="1008" <#if op_type == "2">readonly="readonly"</#if> value="${ywDistributor.dis_level!''}"/>
	
		<label for="level" class="label">收款类型</label>
		<@select placeholder="请输入收款类型" name="dis_receipt_type" dic_key="1009" value="${ywDistributor.dis_receipt_type!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label">收款方式</label>
		<@select placeholder="请输入收款方式" name="dis_receipt_method" dic_key="1010" value="${ywDistributor.dis_receipt_method!''}"/>
	
		<label for="level" class="label">收款账号</label>
		<input id="dis_receipt_account" name="dis_receipt_account" placeholder="请输入收款账号" type="text" class="dfinput"  value="${ywDistributor.dis_receipt_account!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>手机号码</label>
		<input id="dis_mobile" name="dis_mobile" type="text" class="dfinput"  <#if op_type=="2">readonly="readonly"</#if> value="${ywDistributor.dis_mobile!''}"/>
	
		<label for="level" class="label">qq</label>
		<input id="dis_qq" name="dis_qq" placeholder="请输入qq" type="text" class="dfinput" value="${ywDistributor.dis_qq!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label">微信</label>
		<input id="dis_wxcode" name="dis_wxcode" placeholder="请输入微信" type="text" class="dfinput" value="${ywDistributor.dis_wxcode!''}"/>
	
		<label for="level" class="label">联系地址</label>
		<input id="dis_address" name="dis_address" placeholder="请输入联系地址" type="text" class="dfinput"  value="${ywDistributor.dis_address!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>申请时间</label>
		<@dateTimePicker valid="NotBlank" placeholder="请输入申请时间" name="dis_apply_time" timeValue=ywDistributor.dis_apply_time/>
	
		<label for="level" class="label">申请原因</label>
		<input id="dis_apply_reason" name="dis_apply_reason" placeholder="请输入申请原因" type="text" class="dfinput"  value="${ywDistributor.dis_apply_reason!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label">备注</label>
		<textarea name="dis_audit_remark" class="textareainput" placeholder="请输入备注" id="dis_audit_remark" cols="100" rows="5">${ywDistributor.dis_audit_remark!''}</textarea> 
	</div>
	<input type="hidden" id="dis_status" name="dis_status" value="">
	<div class="button-content">
	    <input type="button" class="btn btn_submit_text btn-confirm" value="审核通过" id="btn_ywDistributor_submit"/>
		<input type="button" class="btn btn_submit_text btn-cancel" value="审核不通过" id="btn_ywDistributor_back"/>
	</div>
</form>	
<@script>
$(function(){
  // 审核通过
  $("#btn_ywDistributor_submit").click(function(){
      // 表单校验
      if (!formValidate("ywDistributorForm")) {
		 return false;
	  }
      $("#dis_status").val("2");
      var formData = $("#ywDistributorForm").serializeArray();
      $.post($("#ywDistributorForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
                devActAfterShowDialog(data.error_info,function(){
                 location.href = "${base}/sales/ywDistributor.htm";
                },"suc");
            } else {
                isTimeOutdevShowDialog(data.error_info,data.infos);
            };
        }).error(function(){
            devShowDialog("系统异常");
        });
  })
  
  // 审核不通过
  $("#btn_ywDistributor_back").click(function(){
      // 表单校验
      if (!formValidate("ywDistributorForm")) {
		 return false;
	  }
      $("#dis_status").val("3");
      var formData = $("#ywDistributorForm").serializeArray();
      $.post($("#ywDistributorForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
                alert(data.error_info);
                location.href = "${base}/sales/ywDistributor.htm";
            } else {
                alert("审核失败");
            };
        }).error(function(){
            alert("审核失败");
        });
  })
  
   $("#dis_apply_time").datetimepicker({
	    format: 'yyyy-mm-dd hh:ii:ss',
	    language: 'zh-CN',
	    startDate:new Date(),
	    showSecond: true, //显示秒
	    stepHour: 1,//设置步长
		stepMinute: 1,
		stepSecond: 1,
	    autoclose: true,//选中自动关闭
        todayBtn: true,//显示今日按钮
	  });
  
})
        
</@script>
</@screen>