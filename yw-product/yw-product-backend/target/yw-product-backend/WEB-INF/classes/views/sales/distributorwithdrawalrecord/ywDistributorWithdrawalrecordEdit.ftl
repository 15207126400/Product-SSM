<#assign base=request.contextPath />
<@screen id="ywDistributorWithdrawalrecordInsert" title="提现页面" places=[{"name":"提现记录","href":"${base}/sales/ywDistributorWithdrawalrecord.htm"},{"name":"添加/修改","href":"#"}]>
<form id="ywDistributorWithdrawalrecordForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type!''}"/>
	<input id="dis_wid_id" name="dis_wid_id" type="hidden" class="dfinput" <#if op_type == "2">readonly="readonly"</#if> value="${ywDistributorWithdrawalrecord.dis_wid_id!''}"/>
	<div class="formtitle">
		<span class="formspan">提现记录</span>
	</div>
	<div class="form-group">
		<label for="level" class="label">提现类型</label>
		<input id="dis_wid_type" name="dis_wid_type" dic_key="1035" type="text" placeholder="请输入提现类型" class="dfinput" <#if op_type == "2">readonly="readonly"</#if> value="${ywDistributorWithdrawalrecord.dis_wid_type!''}"/>
	
		<label for="level" class="label">用户编号</label>
		<input id="user_id" name="user_id" type="text" placeholder="请输入用户编号" class="dfinput" <#if op_type == "2">readonly="readonly"</#if> value="${ywDistributorWithdrawalrecord.user_id!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label">用户昵称</label>
		<input id="nickname" name="nickname" type="text" placeholder="请输入用户昵称" class="dfinput" <#if op_type == "2">readonly="readonly"</#if> value="${ywMember.nickname!''}"/>
	
		<label for="level" class="label">提现金额</label>
		<input id="dis_wid_money" name="dis_wid_money" type="text" placeholder="请输入提现金额（单位:元）" class="dfinput" <#if op_type == "2">readonly="readonly"</#if> value="${ywDistributorWithdrawalrecord.dis_wid_money!''}"/>
		<i></i>
	</div>
	<div class="form-group">
		<label for="level" class="label">提现申请时间</label>
		<@dateTimePicker disabled="true" name="dis_wid_createtime" placeholder="请输入提现申请时间" timeValue=ywDistributorWithdrawalrecord.dis_wid_createtime />
	
		<label for="level" class="label">提现审核时间</label>
		<@dateTimePicker disabled="true" name="dis_wid_updatetime" placeholder="请输入提现审核时间" timeValue=ywDistributorWithdrawalrecord.dis_wid_updatetime />
	</div>
	<div class="form-group">
		<label for="level" class="label">提现状态</label>
		<input id="dis_wid_status" name="dis_wid_status" placeholder="请输入提现状态"  dic_key="1036" <#if op_type == "2">readonly="readonly"</#if> type="text" class="dfinput" value="${ywDistributorWithdrawalrecord.dis_wid_status!''}"/>
	
		<label for="level" class="label">备注</label>
		<input id="dis_wid_remark" name="dis_wid_remark" placeholder="请输入提现备注" type="text" <#if op_type == "2">readonly="readonly"</#if> class="dfinput" value="${ywDistributorWithdrawalrecord.dis_wid_remark!''}"/>
	</div>
	<div class="button-content">
	    <input type="button" class="btn btn_submit_text btn-cancel" value="返回" id="btn_ywDistributorWithdrawalrecord_back"/>
	</div>
</form>	
<@script>
$(function(){
  // 确认
  $("#btn_ywDistributorWithdrawalrecord_submit").click(function(){
  	  $("#dis_wid_createtime").removeAttr("disabled");
  	  $("#dis_wid_updatetime").removeAttr("disabled");
      // 表单校验
      if (!formValidate("ywDistributorWithdrawalrecordForm")) {
		 return false;
	  }
      var formData = $("#ywDistributorWithdrawalrecordForm").serializeArray();
      $.post($("#ywDistributorWithdrawalrecordForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
                devActAfterShowDialog(data.error_info,function(){
                 location.href = "${base}/sales/ywDistributorWithdrawalrecord.htm";
                },"suc");
            } else {
                isTimeOutdevShowDialog(data.error_info,data.infos);
            };
        }).error(function(){
            devShowDialog("系统异常");
        });
  })
  
  // 取消
  $("#btn_ywDistributorWithdrawalrecord_back").click(function(){
      location.href = "${base}/sales/ywDistributorWithdrawalrecord.htm";
  })

 })       
</@script>
</@screen>