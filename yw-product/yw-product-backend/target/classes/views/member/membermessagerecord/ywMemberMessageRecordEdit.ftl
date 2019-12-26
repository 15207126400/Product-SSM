<@screen id="ywMemberMessageRecordInsert" title="会员模板消息发送记录信息页面" places=[{"name":"会员模板消息发送记录信息","href":"${base}/member/ywMemberMessageRecord.htm"},{"name":"添加/修改","href":"#"}]>
<form id="ywMemberMessageRecordForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type}"/>
    <input id="id" name="id" type="hidden" value="${ywMemberMessageRecord.id!''}"/>
	<div class="formtitle">
		<span class="formspan">会员模板消息发送记录信息</span>
	</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>消息用户</label>
					<input id="openid" name="openid" type="text" valid="NotBlank" class="dfinput" placeholder="请输入消息用户" maxbytelength="32" value="${ywMemberMessageRecord.openid!''}"/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>消息编号</label>
					<input id="formid" name="formid" type="text" valid="NotBlank" class="dfinput" placeholder="请输入消息编号" maxbytelength="32" value="${ywMemberMessageRecord.formid!''}"/>
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>微信预订单编号</label>
					<input id="prepay_id" name="prepay_id" type="text" valid="NotBlank" class="dfinput" placeholder="请输入微信预订单编号（可以发三条模板消息）" maxbytelength="32" value="${ywMemberMessageRecord.prepay_id!''}"/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>消息类型</label>
					<input id="message_type" name="message_type" type="text" valid="NotBlank" class="dfinput" placeholder="请输入消息类型" maxbytelength="4" value="${ywMemberMessageRecord.message_type!''}"/>
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>消息内容</label>
					<input id="message_content" name="message_content" type="text" valid="NotBlank" class="dfinput" placeholder="请输入消息内容" maxbytelength="255" value="${ywMemberMessageRecord.message_content!''}"/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>订单编号</label>
					<input id="order_sn" name="order_sn" type="text" valid="NotBlank" class="dfinput" placeholder="请输入订单编号" maxbytelength="32" value="${ywMemberMessageRecord.order_sn!''}"/>
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>机构编号</label>
					<input id="branch_id" name="branch_id" type="text" valid="NotBlank" class="dfinput" placeholder="请输入机构编号" maxbytelength="11" value="${ywMemberMessageRecord.branch_id!''}"/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>创建时间</label>
					<input id="createtime" name="createtime" type="text" valid="NotBlank" class="dfinput" placeholder="请输入创建时间" maxbytelength="19" value="${ywMemberMessageRecord.createtime!''}"/>
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>更新时间</label>
					<input id="updatetime" name="updatetime" type="text" valid="NotBlank" class="dfinput" placeholder="请输入更新时间" maxbytelength="19" value="${ywMemberMessageRecord.updatetime!''}"/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>消费发送状态</label>
					<input id="message_status" name="message_status" type="text" valid="NotBlank" class="dfinput" placeholder="请输入消费发送状态" maxbytelength="1" value="${ywMemberMessageRecord.message_status!''}"/>
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>消息发送错误信息</label>
					<input id="message_errorinfo" name="message_errorinfo" type="text" valid="NotBlank" class="dfinput" placeholder="请输入消息发送错误信息" maxbytelength="255" value="${ywMemberMessageRecord.message_errorinfo!''}"/>
			</div>
	<div class="button-content">
	    <input type="button" class="btn btn-success btn_text" value="确认" id="btn_ywMemberMessageRecord_submit"/>
		<input type="button" class="btn btn-success btn_text" value="取消" id="btn_ywMemberMessageRecord_back"/>
	</div>
</form>	
<@script>
$(function(){
  // 确认
  $("#btn_ywMemberMessageRecord_submit").click(function(){
      // 表单校验
      if (!formValidate("ywMemberMessageRecordForm")) {
		 return false;
	  }
      var formData = $("#ywMemberMessageRecordForm").serializeArray();
      $.post($("#ywMemberMessageRecordForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
               devActAfterShowDialog(data.error_info,function(){
                 location.href = "${base}/member/ywMemberMessageRecord.htm";
                },"suc");
            } else if(data.error_no == "-1"){
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }else{
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }
        })
  })
  
  // 取消
  $("#btn_ywMemberMessageRecord_back").click(function(){
      location.href = "${base}/member/ywMemberMessageRecord.htm";
  })
  
})
</@script>
</@screen>