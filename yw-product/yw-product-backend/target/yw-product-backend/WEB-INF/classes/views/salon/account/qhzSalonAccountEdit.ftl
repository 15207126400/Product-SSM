<@screen id="qhzSalonAccountInsert" title="沙龙注册用户信息表页面" places=[{"name":"沙龙注册用户信息表","href":"${base}/qhz_salon_account/qhzSalonAccount.htm"},{"name":"添加/修改","href":"#"}]>
<form id="qhzSalonAccountForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type}"/>
    <input id="id" name="id" type="hidden" value="${qhzSalonAccount.id!''}"/>
	<div class="formtitle">
		<span class="formspan">沙龙注册用户信息表</span>
	</div>
			<div class="form-group">
					<label for="level" class="label">openid</label>
					<input readonly="readonly" id="openid" name="openid" type="text" class="dfinput" placeholder="请输入openid" maxbytelength="50" value="${qhzSalonAccount.openid!''}"/>
			      
			        <label for="level" class="label">姓名</label>
					<input readonly="readonly" id="name" name="name" type="text" class="dfinput" placeholder="请输入姓名" maxbytelength="20" value="${qhzSalonAccount.name!''}"/>
			</div>
			<div class="form-group">
					<label for="level" class="label">手机号</label>
					<input readonly="readonly" id="tel" name="tel" type="text" class="dfinput" placeholder="请输入手机号" maxbytelength="20" value="${qhzSalonAccount.tel!''}"/>
			      
			        <label for="level" class="label">身份证</label>
					<input readonly="readonly" id="card" name="card" type="text" class="dfinput" placeholder="请输入身份证" maxbytelength="30" value="${qhzSalonAccount.card!''}"/>
			</div>
			<div class="form-group">
					<label for="level" class="label">公司名</label>
					<input readonly="readonly" id="company" name="company" type="text" class="dfinput" placeholder="请输入公司名" maxbytelength="30" value="${qhzSalonAccount.company!''}"/>
			      
			        <label for="level" class="label">学习顾问</label>
					<input readonly="readonly" id="adviser" name="adviser" type="text" class="dfinput" placeholder="请输入学习顾问" maxbytelength="20" value="${qhzSalonAccount.adviser!''}"/>
			</div>
			<div class="form-group">
					<label for="level" class="label">创建时间</label>
					<@dateTimePicker name="create_time" disabled="true" timeValue=qhzSalonAccount.create_time />
			      
			        <label for="level" class="label">更新时间</label>
			        <@dateTimePicker name="update_time" disabled="true" timeValue=qhzSalonAccount.update_time />
			</div>
	<div class="button-content">
		<#--
	    <input type="button" class="btn btn-success btn_text" value="确认" id="btn_qhzSalonAccount_submit"/>
	    -->
		<input type="button" class="btn btn-success btn_text" value="返回" id="btn_qhzSalonAccount_back"/>
	</div>
</form>	
<@script>
$(function(){
  // 确认
  $("#btn_qhzSalonAccount_submit").click(function(){
      // 表单校验
      if (!formValidate("qhzSalonAccountForm")) {
		 return false;
	  }
      var formData = $("#qhzSalonAccountForm").serializeArray();
      $.post($("#qhzSalonAccountForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
               devActAfterShowDialog(data.error_info,function(){
                 location.href = "${base}/qhz_salon_account/qhzSalonAccount.htm";
                },"suc");
            } else if(data.error_no == "-1"){
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }else{
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }
        })
  })
  
  // 取消
  $("#btn_qhzSalonAccount_back").click(function(){
      location.href = "${base}/qhz_salon_account/qhzSalonAccount.htm";
  })
  
})
</@script>
</@screen>