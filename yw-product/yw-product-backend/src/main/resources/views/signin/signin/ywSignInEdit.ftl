<@screen id="ywSignInInsert" title="签到页面" places=[{"name":"签到","href":"${base}/signin/ywSignIn.htm"},{"name":"添加/修改","href":"#"}]>
<form id="ywSignInForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type}"/>
    <input id="signIn_id" name="signIn_id" type="hidden" class="dfinput" value="${ywSignIn.signIn_id}"/>
    <input id="user_id" name="user_id" type="hidden" class="dfinput" value="${ywSignIn.user_id}"/>
	<div class="formtitle">
		<span class="formspan">签到</span>
	</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>用户微信昵称</label>
					<input id="nickname" name="nickname" type="text" valid="NotBlank" class="dfinput" disabled="true" placeholder="请输入用户昵称" maxbytelength="32" value="${nickname}"/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>上次签到日期</label>
					<@dateTimePickerByDay valid="NotBlank" placeholder="请选择上次签到日期" disabled="true" name="signIn_last_date" timeValue=ywSignIn.signIn_last_date />
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>连续签到天数</label>
					<input id="signIn_continue_day" name="signIn_continue_day" type="text" valid="NotBlank" disabled="true" class="dfinput" placeholder="请输入连续签到天数" maxbytelength="50" value="${ywSignIn.signIn_continue_day}"/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>签到状态(0,未签 ; 1,已签) </label>
			        <@select valid="NotBlank" placeholder="请选择签到状态" name="signIn_status" dic_key="1054" disabled="true" value="${ywSignIn.signIn_status!''}"/>
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>修改时间</label>
					<@dateTimePicker valid="NotBlank" placeholder="请选择修改时间" disabled="true" name="signIn_updatetime" timeValue=ywSignIn.signIn_updatetime/>
			</div>
	<div class="button-content">
		<#--
	    <input type="button" class="btn btn-success btn_text" value="确认" id="btn_ywSignIn_submit"/>
	    -->
		<input type="button" class="btn btn-success btn_text" value="返回" id="btn_ywSignIn_back"/>
	</div>
</form>	
<@script>
$(function(){
  // 确认
  $("#btn_ywSignIn_submit").click(function(){
      // 表单校验
      if (!formValidate("ywSignInForm")) {
		 return false;
	  }
      var formData = $("#ywSignInForm").serializeArray();
      $.post($("#ywSignInForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
               devActAfterShowDialog(data.error_info,function(){
                 location.href = "${base}/signin/ywSignIn.htm";
                },"suc");
            } else if(data.error_no == "-1"){
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }else{
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }
        })
  })
  
  // 取消
  $("#btn_ywSignIn_back").click(function(){
      location.href = "${base}/signin/ywSignIn.htm";
  })
  
})
</@script>
</@screen>