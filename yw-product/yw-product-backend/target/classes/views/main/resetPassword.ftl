<#assign base=request.contextPath />
<@screen id="ywUserInsert" title="重置密码页面" places=[{"name":"返回登录","href":"${base}/login.htm"},{"name":"找回密码","href":"${base}/forget_psd_path.htm"},{"name":"重置密码","href":"#"}]>
<form id="ywUserForm" action="${base}/resetPassword.json" method="post">
	<div class="formbody">
		<div class="formtitle">
			<span class="formspan">重置密码</span>
		</div>

		<ul class="forminfo">
			<li>
				<label>用户名</label>
				<input id="username" name="username" type="text" class="dfinput" readonly="readonly" maxlength="18" value="${ywUser.username!'' }" />
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
		             location.href = "${base}/login.htm";
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
      location.href = "${base}/login.htm";
  })

})

</@script>
</@screen>