<#assign base=request.contextPath />
<#-- 地址选择器js及json文件 -->
<@script src="${base}/js/address.js"/>
<@screen id="ywUserInsert" title="申请体验账号" places=[{"name":"返回登录","href":"${base}/login.htm"},{"name":"申请体验账号","href":"#"}]>
<form id="ywUserForm" action="experience_account_user.json" method="post" class="form">
	<input id="user_id" name="user_id" type="hidden" class="dfinput" value="${ywUser.user_id!''}"/>
	<div class="formtitle">
		<span class="formspan">用户信息</span>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>用户名</label>
		<input id="username" name="username" valid="NotBlank,Name" placeholder="请输入用户名" type="text" class="dfinput" value="${ywUser.username!''}"/>
	
		<label for="level" class="label"><em style="color:red;">*</em>密码</label>
		<input id="password" name="password" valid="NotBlank" placeholder="请输入密码" type="text" class="dfinput" value="${ywUser.password!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>真实姓名</label>
		<input id="realname" name="realname" valid="NotBlank,Name" placeholder="请输入真实姓名" type="text" class="dfinput" value="${ywUser.realname!''}"/>
	
		<label for="level" class="label"><em style="color:red;">*</em>手机号码</label>
		<input id="mobile_tel" name="mobile_tel" valid="NotBlank,Mobile" placeholder="请输入手机号码" type="text" class="dfinput"  value="${ywUser.mobile_tel!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label">座机号码</label>
		<input id="phone" name="phone" placeholder="请输入座机号码" type="text" class="dfinput"  value="${ywUser.phone!''}" />
	
		<label for="level" class="label">微信号</label>
		<input id="wx_code" name="wx_code" placeholder="请输入微信号" type="text" class="dfinput"  value="${ywUser.wx_code!''}" />
	</div>
	<div class="form-group">
		<label for="level" class="label">QQ</label>
		<input id="qq_code" name="qq_code" placeholder="请输入QQ" type="text" class="dfinput"  value="${ywUser.qq_code!''}" />
	
		<label for="level" class="label"><em style="color:red;">*</em>邮箱</label>
		<input id="email" name="email" valid="Email" placeholder="请输入邮箱" type="text" class="dfinput"  value="${ywUser.email!''}" />
	</div>
	<div class="form-group">
	    <label for="level" class="label"><em style="color:red;">*</em>所在城市地区</label>
		<input id="city" name="city" valid="NotBlank" type="text" placeholder="请输入所在城市地区" class="dfinput" value="${ywUser.city!''}" /> 
		
		<label for="level" class="label"><em style="color:red;">*</em>详细地址</label>
		<input id="address" name="address" valid="NotBlank,Address" placeholder="请输入地址" type="text" class="dfinput" value="${ywUser.address!''}" />
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>性别</label>
		<@select valid="NotBlank" placeholder="请输入性别" name="sex" dic_key="1011" value="${ywUser.sex!''}" />
	
		<label for="level" class="label">生日</label>
		<input id="birthday" name="birthday" placeholder="请输入生日（格式：1997-08-15）" type="text" class="dfinput" value="${ywUser.birthday!''}" />
	</div>
	<div class="form-group">
	    <label for="level" class="label"><em style="color:red;">*</em>公司名称</label>
		<input id="corporate_name" name="corporate_name" valid="NotBlank" placeholder="请输入公司名称" type="text" class="dfinput" value="${ywUser.corporate_name!''}" />
		
		<label for="level" class="label"><em style="color:red;">*</em>行业</label>
		<@select valid="NotBlank" placeholder="请选择行业" name="industry" dic_key="1048" value="${ywUser.industry!''}" />
	</div>
	<div class="form-group">
		<label for="level" class="label">公司网站</label>
		<input id="corporate_url" name="corporate_url" placeholder="请输入公司网站" type="text" class="dfinput" value="${ywUser.corporate_url!''}" />
	
		<label for="level" class="label">邀请码</label>
		<input id="invite_code" name="invite_code" placeholder="没有可不填" type="text" class="dfinput"  value="${ywUser.invite_code!''}" />
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>密码提示问题</label>
		<@select valid="NotBlank" placeholder="请选择密码提示问题" name="password_question" dic_key="1049" value="${ywUser.password_question!''}" />
		
		<label for="level" class="label"><em style="color:red;">*</em>密码提示答案</label>
		<input id="password_prompt_answer" name="password_prompt_answer" valid="NotBlank" placeholder="请输入密码提示答案" type="text" class="dfinput" value="${ywUser.password_prompt_answer!''}" />
	</div>
	<div class="button-content">
	    <input type="button" class="btn btn_submit_text btn-confirm" value="确认" id="btn_ywUser_submit"/>
		<input type="button" class="btn btn_submit_text btn-cancel" value="取消" id="btn_ywUser_back"/>
	</div>
</form>	
<@script>
$(function(){
  

  // 确认
  $("#btn_ywUser_submit").click(function(){
  		
      // 表单校验
      if (!formValidate("ywUserForm")) {
		 return false;
	  }
	  
      var formData = $("#ywUserForm").serializeArray();
      	
      $.post($("#ywUserForm").attr("action"), formData, function(data){
            if (data.error_no == 0) {
                devActAfterShowDialog(data.error_info,function(){
                 location.href = "${base}/login.htm";
                },"suc");
            } else {
               isTimeOutdevShowDialog(data.error_info,data.infos);
            };
        }).error(function(){
            devShowDialog("系统异常");
        });
  })
  
  // 取消
  $("#btn_ywUser_back").click(function(){
      location.href = "${base}/login.htm";
  })

})
        
</@script>
</@screen>