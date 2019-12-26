<@screen id="ywUserCustomerXcxInsert" title="小程序信息页面" places=[{"name":"小程序信息","href":"${base}/system/ywUserCustomerXcx.htm"},{"name":"添加/修改","href":"#"}]>
<form id="ywUserCustomerXcxForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type}"/>
    <input id="id" name="id" type="hidden" value="${ywUserCustomerXcx.id!''}"/>
	<div class="formtitle">
		<span class="formspan">小程序信息</span>
	</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>用户</label>
					<@select valid="NotBlank" placeholder="请选择用户" name="user_id" datalist=ywUserCustomer value="${ywUserCustomerXcx.user_id!''}" optionData={"value":"user_id","option":"cust_name"}/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>小程序类型</label>
			        <@select valid="NotBlank" placeholder="请选择小程序类型" name="xcx_type" dic_key="1063" value="${ywUserCustomerXcx.xcx_type!''}" />
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>小程序ID</label>
					<input id="app_id" name="app_id" type="text" valid="NotBlank" class="dfinput" placeholder="请输入小程序ID" maxbytelength="255" value="${ywUserCustomerXcx.app_id!''}"/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>小程序密钥</label>
					<input id="app_secret" name="app_secret" type="text" valid="NotBlank" class="dfinput" placeholder="请输入小程序密钥" maxbytelength="255" value="${ywUserCustomerXcx.app_secret!''}"/>
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>商户号</label>
					<input id="mch_id" name="mch_id" type="text" valid="NotBlank" class="dfinput" placeholder="请输入商户号(支付相关)" maxbytelength="255" value="${ywUserCustomerXcx.mch_id!''}"/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>支付秘钥</label>
					<input id="xcx_pay_key" name="xcx_pay_key" type="text" valid="NotBlank" class="dfinput" placeholder="请输入支付秘钥（主要用于签名用）" maxbytelength="255" value="${ywUserCustomerXcx.xcx_pay_key!''}"/>
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>参数签名类型</label>
					<@select valid="NotBlank" placeholder="请选择参数签名类型" name="sign_type" dic_key="1064" value="${ywUserCustomerXcx.sign_type!''}" />
			      
			        <label for="level" class="label"><em style="color:red;">*</em>参数类型</label>
			        <@select valid="NotBlank" placeholder="请选择参数类型" name="param_type" dic_key="1065" value="${ywUserCustomerXcx.param_type!''}" />
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>订单IP地址</label>
					<input id="xcx_order_ip" name="xcx_order_ip" type="text" valid="NotBlank" class="dfinput" placeholder="请输入小程序创建订单的IP地址" maxbytelength="255" value="${ywUserCustomerXcx.xcx_order_ip!''}"/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>小程序订单描述</label>
					<input id="xcx_order_body" name="xcx_order_body" type="text" valid="NotBlank" class="dfinput" placeholder="请输入小程序订单描述" maxbytelength="255" value="${ywUserCustomerXcx.xcx_order_body!''}"/>
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>证书路径</label>
					<input id="cert_path" name="cert_path" type="text" valid="NotBlank" class="dfinput" placeholder="请输入证书路径" maxbytelength="255" value="${ywUserCustomerXcx.cert_path!''}"/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>证书类型</label>
			        <@select valid="NotBlank" placeholder="请选择证书类型" name="cert_type" dic_key="1066" value="${ywUserCustomerXcx.cert_type!''}" />
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>小程序状态</label>
					<@select valid="NotBlank" placeholder="请选择小程序状态" name="xcx_status" dic_key="1000" value="${ywUserCustomerXcx.xcx_status!''}" />
			</div>
	<div class="button-content">
	    <input type="button" class="btn btn-success btn_text" value="确认" id="btn_ywUserCustomerXcx_submit"/>
		<input type="button" class="btn btn-success btn_text" value="取消" id="btn_ywUserCustomerXcx_back"/>
	</div>
</form>	
<@script>
$(function(){
  // 确认
  $("#btn_ywUserCustomerXcx_submit").click(function(){
      // 表单校验
      if (!formValidate("ywUserCustomerXcxForm")) {
		 return false;
	  }
      var formData = $("#ywUserCustomerXcxForm").serializeArray();
      $.post($("#ywUserCustomerXcxForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
               devActAfterShowDialog(data.error_info,function(){
                 location.href = "${base}/system/ywUserCustomerXcx.htm";
                },"suc");
            } else if(data.error_no == "-1"){
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }else{
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }
        })
  })
  
  // 取消
  $("#btn_ywUserCustomerXcx_back").click(function(){
      location.href = "${base}/system/ywUserCustomerXcx.htm";
  })
  
})
</@script>
</@screen>