<#assign base=request.contextPath />
<@screen id="ywUserRegist" title="找回密码页面" places=[{"name":"返回登录","href":"${base}/login.htm"},{"name":"找回密码","href":"#"}]>
<form id="formid" action="${base}/forget_psd.htm" method="post">
	<input id="op_type" hidden=true value="${op_type}" />
	
	<div class="formbody">
		<div class="formtitle">
			<span class="formspan">找回密码</span>
		</div>

		<ul class="forminfo">
			<input id="user_id" name="user_id" type="hidden" />
			<li>
				<label>用户名</label>
				<input id="user_name" name="user_name" placeholder="请输入用户名" type="text" class="dfinput" maxlength="18" />
			</li>
			<li id="question">
				<label>密码提示问题</label>
				<input id="password_question" name="password_question" type="text" class="dfinput" readonly="readonly" />
			</li>
			<li id="answer">
				<label>密码提示答案</label>
				<input id="password_answer" name="password_answer" valid="NotBlank" placeholder="请输入密码提示答案" type="text" class="dfinput" />
			</li>
			<li>
				<label>&nbsp;</label>
				<input type="button" class="btn-success btn_text sddd" value="确认" id="btn_username_submit"/>
				<input type="button" class="btn-success btn_text sddd" value="确认" id="btn_ywUser_submit"/>
				<input type="button" class="btn-success btn_text sddd" value="取消" id="btn_ywUser_back"/>
			</li>
		</ul>
	</div>
</form>
<@script>
$(document).ready(function(){
	$("#question").hide();
	$("#answer").hide();
	$("#btn_ywUser_submit").hide();
	
	
	// 输入并确认账号
	$("#btn_username_submit").click(function(){
		
		var user_name = $("#user_name").val();
		$.ajax({
			 type: "post",
			 url: "${base}/operatorForgetPasswordSubmit.json",
			 data: {
				"user_name":user_name
			 },
			 dataType: "json",
			 success: function(data){
				console.log(data)
				if (data.password_question == "1") {
					devShowDialog("您没有设置密码找回!");
	            } else if(data.password_question == "2"){
	               devShowDialog("没有该账号,请重新输入!");
	            }else {
	                $("#password_question").val(data.password_question)
	                $("#user_id").val(data.user_id);
	                $("#question").show();
					$("#answer").show();
					$("#btn_ywUser_submit").show();
					$("#btn_username_submit").hide();
	            }
			 }
		 });
  	  })
  	
  	// 提交问题答案
	$("#btn_ywUser_submit").click(function(){
	  //var user_name = $("#user_name").val();
	  // 表单校验
      if (!formValidate("ywUserForm")) {
		 return false;
	  }
		var formData = new FormData($("#formid")[0]);
		//var formData = $("#formid").serializeArray();
		$.ajax({  
          url: $("#formid").attr("action") ,  
          type: 'POST',  
          data: formData,  
          async: false,  
          cache: false,  
          contentType: false,  
          processData: false,  
          success: function (data) {  
            if (data.username == -1) {
                devShowDialog("密码找回答案不正确!");
            } else {
                location.href = "${base}/operatorResetPassword2.htm?user_name="+data.username;
            };
          },  
          error: function (returndata) {  
             devShowDialog("系统异常");
          }  
     }); 
  	})
  	
  	
  	
  	
  	// 取消
  	$("#btn_ywUser_back").click(function(){
      	location.href = "${base}/login.htm";
  	})
  	
})
	
</@script>
</@screen>