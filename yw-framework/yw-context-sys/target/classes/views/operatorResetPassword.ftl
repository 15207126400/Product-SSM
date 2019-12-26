<#assign base=request.contextPath />
<@screen id="ywUserInsert" title="重置密码页面" places=[{"name":"返回首页","href":"${base}/login.htm"},{"name":"找回密码","href":"${base}/operatorForgetPassword.htm"},{"name":"重置密码","href":"#"}]>
<form id="ywUserForm" action="${base}/operatorResetPasswordSubmit.json" method="post">
	<div class="formbody">
		<div class="formtitle">
			<span class="formspan">重置密码</span>
		</div>
		<input id="user_id" name="user_id" type="hidden" maxlength="18" value="${ywUser.user_id!'' }" />
		<ul class="forminfo">
			<li>
				<label>用户名</label>
				<input id="user_name" name="user_name" type="text" class="dfinput" readonly="readonly" maxlength="18" value="${ywUser.user_name!'' }" />
			</li>
			<li>
				<label>输入密码</label>
				<input id="password" name="password" valid="NotBlank" placeholder="请输入密码" type="text" class="dfinput" />
			</li>
			<li>
				<label>再次输入密码</label>
				<input id="again_password" name="again_password" valid="NotBlank" placeholder="请再次输入密码" type="text" class="dfinput" />
			</li>
			<li>
				<label>&nbsp;</label>
				<input type="button" class="btn-success btn_text sddd" value="确认" id="btn_ywUser_submit"/>
				<input type="button" class="btn-success btn_text sddd" value="取消" id="btn_ywUser_back"/>
			</li>
		</ul>
	</div>
</form>
<@script>
$(function(){
	
  // 确认
  $("#btn_ywUser_submit").click(function(){
  	  var username = $("#username").val();
  	  var password = $("#password").val();
  	  var again_password = $("#again_password").val();	
  		
       // 表单校验
      if (!formValidate("ywUserForm")) {
		 return false;
	  }
  	  if(password != again_password){
  	  	devShowDialog("两次输入密码不一致,请重新输入!");
  	  }else{
  	  	var formData = $("#ywUserForm").serializeArray();
      	
		  $.post($("#ywUserForm").attr("action"), formData, function(data){
		        if (data.num == 1) {
		        	 devShowDialog("密码重置成功,请登录!");
		             top.location.href = "${base}/login.htm";
		        } else {
		           devShowDialog("密码重置失败,请稍后再试!");
		        };
		    }).error(function(){
		        devShowDialog("系统异常");
		    });
  	  	}
  	})
  
  // 取消
  $("#btn_ywUser_back").click(function(){
      top.location.href = "${base}/login.htm";
  })

})

</@script>
</@screen>