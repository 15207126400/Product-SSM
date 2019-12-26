<@screen id="qhzSmsConfigInsert" title="沙龙注册短信系统配置页面" places=[{"name":"沙龙注册短信系统配置","href":"${base}/qhz_sms_config/qhzSmsConfig.htm"},{"name":"添加/修改","href":"#"}]>
<form id="qhzSmsConfigForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type}"/>
    <input id="id" name="id" type="hidden" value="${ywSmsConfig.id!''}"/>
	<div class="formtitle">
		<span class="formspan">沙龙注册短信系统配置</span>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>短信通道类型</label>
		<@select valid="NotBlank" placeholder="请选择短信通道类型" name="type" dic_key="1095" value=qhzSmsConfig.type!''/>
      
        <label for="level" class="label"><em style="color:red;">*</em>请求路径</label>
		<input id="url" name="url" type="text" valid="NotBlank" class="dfinput" placeholder="请输入请求路径" maxbytelength="50" value="${qhzSmsConfig.url!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>企业id</label>
		<input id="userid" name="userid" type="text" valid="NotBlank" class="dfinput" placeholder="请输入企业id" maxbytelength="10" value="${qhzSmsConfig.userid!''}"/>
      
        <label for="level" class="label"><em style="color:red;">*</em>短信前缀公司签名</label>
		<input id="sign" name="sign" type="text" valid="NotBlank" class="dfinput" placeholder="请输入短信前缀公司签名" maxbytelength="20" value="${qhzSmsConfig.sign!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>账号</label>
		<input id="account" name="account" type="text" valid="NotBlank" class="dfinput" placeholder="请输入账号" maxbytelength="20" value="${qhzSmsConfig.account!''}"/>
		
		<label for="level" class="label"><em style="color:red;">*</em>密码</label>
		<input id="password" name="password" type="text" valid="NotBlank" class="dfinput" placeholder="请输入密码" maxbytelength="20" value="${qhzSmsConfig.password!''}"/>
	</div>
	<div class="button-content">
	    <input type="button" class="btn btn-success btn_text" value="确认" id="btn_qhzSmsConfig_submit"/>
		<input type="button" class="btn btn-success btn_text" value="取消" id="btn_qhzSmsConfig_back"/>
	</div>
</form>	
<@script>
$(function(){
  // 确认
  $("#btn_qhzSmsConfig_submit").click(function(){
      // 表单校验
      if (!formValidate("qhzSmsConfigForm")) {
		 return false;
	  }
      var formData = $("#qhzSmsConfigForm").serializeArray();
      $.post($("#qhzSmsConfigForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
               devActAfterShowDialog(data.error_info,function(){
                 location.href = "${base}/qhz_sms_config/qhzSmsConfig.htm";
                },"suc");
            } else if(data.error_no == "-1"){
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }else{
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }
        })
  })
  
  // 取消
  $("#btn_qhzSmsConfig_back").click(function(){
      location.href = "${base}/qhz_sms_config/qhzSmsConfig.htm";
  })
  
})
</@script>
</@screen>