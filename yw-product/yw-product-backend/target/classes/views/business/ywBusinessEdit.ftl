<#assign base=request.contextPath />
<@screen id="ywBusinessInsert" title="用户个人信息修改页面" places=[{"name":"商家信息","href":"${base}/business/ywBusiness.htm"},{"name":"修改","href":"#"}]>
<style>
.bg{
	background: gray;
}
</style>
<form id="ywBusinessForm" action="${base}/business/ywBusiness/insertBusiness.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type!''}"/>
    <input id="user_id" name="user_id" type="hidden" class="dfinput" value="${ywUser.user_id!''}"/>
	
	<div class="formtitle">
		<span class="formspan">商家信息</span>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>登录名</label>
		<input id="user_name" name="user_name" type="text" class="dfinput" disabled="disabled" value="${ywUser.user_name!''}"/>
	
		<label for="level" class="label"><em style="color:red;">*</em>登录密码</label>
		<input id="password" name="password" type="text" class="dfinput" disabled="disabled" value="${ywUser.password!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>真实姓名</label>
		<input id="cust_name" name="cust_name" type="text" readonly="readonly" class="dfinput" value="${ywUserCustomer.cust_name!''}"/>
	
		<label for="level" class="label"><em style="color:red;">*</em>手机号码</label>
		<input id="mobile_tel" name="mobile_tel" type="text" class="dfinput" readonly="readonly" value="${ywUserCustomer.mobile_tel!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label">座机号码</label>
		<input id="phone_tel" name="phone_tel" placeholder="请输入座机号码" type="text" class="dfinput" value="${ywUserCustomer.phone_tel!''}" />
	
		<label for="level" class="label">微信号</label>
		<input id="wx_code" name="wx_code" placeholder="请输入微信号" type="text" class="dfinput" value="${ywUserCustomer.wx_code!''}" />
	</div>
	<div class="form-group">
		<label for="level" class="label">QQ</label>
		<input id="qq_code" name="qq_code" placeholder="请输入QQ" type="text" class="dfinput" value="${ywUserCustomer.qq_code!''}" />
	
		<label for="level" class="label"><em style="color:red;">*</em>邮箱</label>
		<input id="e_mail" name="e_mail" valid="Email" placeholder="请输入邮箱" type="text" class="dfinput" value="${ywUserCustomer.e_mail!''}" />
	</div>
	<div class="form-group">
	    <label for="level" class="label"><em style="color:red;">*</em>所在城市地区</label>
		<input id="city" name="city" valid="NotBlank" type="text" placeholder="请输入所在城市地区" class="dfinput" value="${ywUserCustomer.city!''}" /> 
		
		<label for="level" class="label"><em style="color:red;">*</em>性别</label>
		<@select valid="NotBlank" placeholder="请输入性别" name="sex" dic_key="1011" value="${ywUserCustomer.sex!''}" />
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>地址</label>
		<input id="address" name="address" valid="NotBlank,Address" placeholder="请输入地址" type="text" class="dfinput" value="${ywUserCustomer.address!''}" />
	
		<label for="level" class="label">生日</label>
		<input id="birthday" name="birthday" placeholder="请输入生日" type="text" class="dfinput" value="${ywUserCustomer.birthday!''}" />
	</div>
	<div class="form-group">
	    <label for="level" class="label"><em style="color:red;">*</em>公司名称</label>
		<input id="company_name" name="company_name" valid="NotBlank" placeholder="请输入公司名称" type="text" class="dfinput" value="${ywUserCustomer.company_name!''}" />
		
		<label for="level" class="label"><em style="color:red;">*</em>行业</label>
		<@select valid="NotBlank" placeholder="请选择行业" name="industry_type" dic_key="1048" value="${ywUserCustomer.industry_type!''}" />
	</div>
	<div class="form-group">
		<label for="level" class="label">公司网站</label>
		<input id="company_site" name="company_site" placeholder="请输入公司网站" type="text" class="dfinput" value="${ywUserCustomer.company_site!''}" />
		
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>密码提示问题</label>
		<@select disabled="true" name="password_question" dic_key="1049" value="${ywUser.password_question!''}" />
		
		<label for="level" class="label"><em style="color:red;">*</em>密码提示答案</label>
		<input id="password_answer" name="password_answer" disabled="true" type="text" class="dfinput" value="${ywUser.password_answer!''}" />
	</div>
	<div class="form-group">
		<label for="level" class="label">小程序appid</label>
		<input id="app_id" name="app_id" disabled="disabled" type="text" class="dfinput" value="${ywUserCustomerXcx.app_id!''}" />
		
		<label for="level" class="label">小程序秘钥</label>
		<input id="app_secret" name="app_secret" disabled="disabled" type="text" class="dfinput"  value="${ywUserCustomerXcx.app_secret!''}" />
	</div>
	<div class="form-group">
		<label for="level" class="label">商户支付号</label>
		<input id="mch_id" name="mch_id" type="text" disabled="disabled" class="dfinput"  value="${ywUserCustomerXcx.mch_id!''}" />
	
		<label for="level" class="label">小程序名称</label>
		<input id="xcx_name" name="xcx_name" disabled="disabled" type="text" class="dfinput"  value="${ywUserCustomerXcx.xcx_name!''}" />
	</div>	
	<div class="form-group">
		<label for="level" class="label">付费时间</label>
	    <@dateTimePicker name="pay_datetime" disabled="true" placeholder="请输入付费时间" timeValue=ywUser.pay_datetime />
	
		<label for="level" class="label">到期时间</label>
		<@dateTimePicker name="expire_datetime" disabled="true" placeholder="请输入到期时间" timeValue=ywUser.expire_datetime />
	</div>
	<input id="ro_ids" name="ro_ids" type="hidden" class="dfinput" value="${ywUser.ro_ids!''}" />
	<div class="button-content">
	    <input type="button" class="btn btn_submit_text btn-confirm" value="保存" id="btn_ywUser_submit"/>
	</div>
</form>	
<@script>
$(function(){

  // 确认
  $("#btn_ywUser_submit").click(function(){
  	  $("#pay_datetime").removeAttr("disabled");	
  	  $("#expire_datetime").removeAttr("disabled");	
  		
          // 表单校验
	      if (!formValidate("ywBusinessForm")) {
			 return false;
		  }
		  
      	var formData = $("#ywBusinessForm").serializeArray();
      	$.post($("#ywBusinessForm").attr("action"), formData, function(data){
            if (data.error_no == 0) {
                devActAfterShowDialog(data.error_info,function(){
                  location.href = "${base}/business/ywBusiness.htm";
                },"suc");
            } else {
                isTimeOutdevShowDialog(data.error_info,data.infos);
            };
        }).error(function(){
            devShowDialog("系统异常");
        });
  })
  
})
        
</@script>
</@screen>