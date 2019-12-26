<#assign base=request.contextPath />
<#assign ywRoleCache = SpringContext.getBean("ywRoleCache") />
<#-- 地址选择器js及json文件 -->
<@script src="${base}/js/address.js"/>
<@screen id="ywUserCustomerInsert" title="用户信息修改页面" places=[{"name":"用户信息管理","href":"${base}/system/ywUserCustomer.htm"},{"name":"添加/修改","href":"#"}]>
<form id="ywUserCustomerForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type!''}"/>
    <input id="user_id" name="user_id" type="hidden" class="dfinput" value="${ywUserCustomer.user_id!''}"/>
    <input id="user_type" name="user_type" type="hidden" class="dfinput" value="${ywUser.user_type!'1'}"/>
    <div class="formtitle">
		<span class="formspan">用户信息</span>
	</div>
    <div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>登录名</label>
		<input id="user_name" name="user_name" valid="NotBlank,Name" placeholder="请输入用户名" type="text" class="dfinput" value="${ywUser.user_name!''}"/>
	
		<label for="level" class="label"><em style="color:red;">*</em>登录密码</label>
		<input id="password" name="password" valid="NotBlank" placeholder="请输入密码" type="text" class="dfinput" value="${ywUser.password!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>真实姓名</label>
		<input id="cust_name" name="cust_name" valid="NotBlank,Name" placeholder="请输入真实姓名" type="text" class="dfinput" value="${ywUserCustomer.cust_name!''}"/>
	
		<label for="level" class="label"><em style="color:red;">*</em>手机号码</label>
		<input id="mobile_tel" name="mobile_tel" valid="NotBlank,Mobile" placeholder="请输入手机号码" type="text" class="dfinput"  value="${ywUserCustomer.mobile_tel!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label">座机号码</label>
		<input id="phone_tel" name="phone_tel" placeholder="请输入座机号码" type="text" class="dfinput"  value="${ywUserCustomer.phone_tel!''}" />
	
		<label for="level" class="label">微信号</label>
		<input id="wx_code" name="wx_code" placeholder="请输入微信号" type="text" class="dfinput"  value="${ywUserCustomer.wx_code!''}" />
	</div>
	<div class="form-group">
		<label for="level" class="label">QQ</label>
		<input id="qq_code" name="qq_code" placeholder="请输入QQ" type="text" class="dfinput"  value="${ywUserCustomer.qq_code!''}" />
	
		<label for="level" class="label">邮箱</label>
		<input id="e_mail" name="e_mail" valid="Email" placeholder="请输入邮箱" type="text" class="dfinput"  value="${ywUserCustomer.e_mail!''}" />
	</div>
	<div class="form-group">
	    <label for="level" class="label"><em style="color:red;">*</em>所在城市地区</label>
		<input id="city" name="city" valid="NotBlank" type="text" placeholder="请输入所在城市地区" class="dfinput" value="${ywUserCustomer.city!''}" /> 
		
		<label for="level" class="label"><em style="color:red;">*</em>详细地址</label>
		<input id="address" name="address" valid="NotBlank,Address" placeholder="请输入地址" type="text" class="dfinput" value="${ywUserCustomer.address!''}" />
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>性别</label>
		<@select valid="NotBlank" placeholder="请输入性别" name="sex" dic_key="1011" value="${ywUserCustomer.sex!''}" />
	
		<label for="level" class="label">生日</label>
		<input id="birthday" name="birthday" placeholder="请输入生日（格式：1997-08-15）" type="text" class="dfinput" value="${ywUserCustomer.birthday!''}" />
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
		<#--
		<label for="level" class="label">邀请码</label>
		<input id="invite_code" name="invite_code" placeholder="没有可不填" type="text" class="dfinput"  value="${ywUserCustomer.invite_code!''}" />
		-->
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>密码提示问题</label>
		<@select valid="NotBlank" placeholder="请选择密码提示问题" name="password_question" dic_key="1049" value="${ywUser.password_question!''}" />
		
		<label for="level" class="label"><em style="color:red;">*</em>密码提示答案</label>
		<input id="password_answer" name="password_answer" valid="NotBlank" placeholder="请输入密码提示答案" type="text" class="dfinput" value="${ywUser.password_answer!''}" />
	</div>
	<div class="form-group">
		<label for="level" class="label">付费时间</label>
	    <@dateTimePicker name="pay_datetime" valid="NotBlank" placeholder="请输入付费时间" timeValue=ywUser.pay_datetime />
	    
		<label for="level" class="label">到期时间</label>
		<@dateTimePicker name="expire_datetime" valid="NotBlank" placeholder="请输入到期时间" timeValue=ywUser.expire_datetime />
	</div>
	<#--
	<div class="form-group">
		<label for="level" class="label">商品购买区域</label>
		<@provinceCityAreaSelect name="wx_buy_area" placeholder="不限" value="${ywUserCustomer.wx_buy_area!''}"/>
	</div>
	-->
	<div class="form-group">
		<label for="level" class="label">角色字符串</label>
		      <input type="checkbox" id="checkedAll">全选
		<#if ywRoles?? && ywRoles?size gt 0>
		   <#list ywRoles as ywRole>
		   	  <input style="margin-left:10px" class="ro_ids" id="${ywRole.ro_id}" type="checkbox" <#if ywRoleCache.isHasRole("${ywUserCustomer.ro_ids}",ywRole.ro_id)>checked="checked"</#if>/>${ywRole.ro_name}
		   </#list>
		</#if>
		<input type="hidden" id="ro_ids" name="ro_ids">
	</div>
	<div class="button-content">
	    <input type="button" class="btn btn_submit_text btn-confirm" value="确认" id="btn_ywUserCustomer_submit"/>
		<input type="button" class="btn btn_submit_text btn-cancel" value="取消" id="btn_ywUserCustomer_back"/>
	</div>
</form>	
<@script>
$(function(){
 
	// checkbox全部选择
	$("#checkedAll").click(function(){
		if($(this).prop("checked")){
	      $(".ro_ids").prop("checked",true);
	   } else {
	      $(".ro_ids").prop("checked",false);
	   }
    })	

  // 确认
  $("#btn_ywUserCustomer_submit").click(function(){
  	  $("#pay_datetime").removeAttr("disabled");	
  	  $("#expire_datetime").removeAttr("disabled");	
  	  
       // 表单校验
      if (!formValidate("ywUserCustomerForm")) {
		 return false;
	  }
  	   // 获取用户角色
	    var roArray = [];
	    $(".ro_ids").each(function(index,item){

	        if($(this).prop("checked")){
	           roArray.push($(this).attr("id"));
	        }
	    })
	    $("#ro_ids").val(roArray.toString());
      	var formData = $("#ywUserCustomerForm").serializeArray();
      	
      $.post($("#ywUserCustomerForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
                devActAfterShowDialog(data.error_info,function(){
                 location.href = "${base}/system/ywUserCustomer.htm";
                },"suc");
            } else {
               isTimeOutdevShowDialog(data.error_info,data.infos);
            };
        }).error(function(){
            devShowDialog("系统异常");
        });
  })
  
  // 取消
  $("#btn_ywUserCustomer_back").click(function(){
      location.href = "${base}/system/ywUserCustomer.htm";
  })

})
        
</@script>
</@screen>