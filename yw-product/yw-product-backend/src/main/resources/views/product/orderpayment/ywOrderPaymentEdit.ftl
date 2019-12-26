<#assign base=request.contextPath />
<@screen id="ywOrderPaymentInsert" title="支付信息页面" places=[{"name":"支付信息","href":"${base}/product/ywOrderPayment.htm"},{"name":"详情","href":"#"}]>
<form id="ywOrderPaymentForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type!''}"/>
    <input id="order_pay_id" name="order_pay_id" type="hidden" class="dfinput" value="${ywOrderPayment.order_pay_id!''}"/>
	<div class="formtitle">
		<span class="formspan">支付信息</span>
	</div>
	<div class="form-group">
		<label for="level" class="label">支付金额</label>
		<input id="order_pay_price" name="order_pay_price" type="text" placeholder="请输入支付金额（单位：元）" class="dfinput" <#if op_type == "2">readonly="readonly"</#if> value="${ywOrderPayment.order_pay_price!''}"/>
		<i id="order_pay_price"></i>
	
		<label for="level" class="label">支付状态</label>
		<@select valid="NotBlank" name="order_pay_status" placeholder="请选择支付状态" disabled="true" dic_key="1034" value="${ywOrderPayment.order_pay_status!''}" />
		<i id="order_pay_status"></i>
	</div>
	<div class="form-group">
		<label for="level" class="label">用户openid</label>
		<input id="open_id" name="open_id" type="text" class="dfinput" placeholder="请输入用户openid" <#if op_type == "2">readonly="readonly"</#if> value="${ywOrderPayment.open_id!''}"/>
	
		<label for="level" class="label">用户昵称</label>
		<input id="nickname" name="nickname" type="text" class="dfinput" placeholder="请输入用户昵称" <#if op_type == "2">readonly="readonly"</#if> value="${nickname!''}"/>
	</div>
	<div class="form-group">
	    <label for="level" class="label">订单号</label>
		<input id="order_sn" name="order_sn" type="text" class="dfinput" <#if op_type == "2">readonly="readonly"</#if> value="${ywOrderPayment.order_sn!''}"/>
		
		<label for="level" class="label">支付单号</label>
		<input id="order_pay_code" name="order_pay_code" type="text" class="dfinput" <#if op_type == "2">readonly="readonly"</#if> value="${ywOrderPayment.order_pay_code!''}"/>
	</div>
	<div class="form-group">
	    <label for="level" class="label">支付类型</label>
		<@select valid="NotBlank" name="order_pay_type" disabled="true" dic_key="1083" value="${ywOrderPayment.order_pay_type!''}" />
		
		<label for="level" class="label">支付方式</label>
		<@select valid="NotBlank" name="order_pay_method" disabled="true" dic_key="1010" value="${ywOrderPayment.order_pay_method!''}" />
	</div>
	<div class="form-group">
		<label for="level" class="label">支付创建时间</label>
		<@dateTimePicker name="order_pay_createtime" disabled="true" timeValue=ywOrderPayment.order_pay_createtime />
	
		<label for="level" class="label">支付更新时间</label>
		<@dateTimePicker name="order_pay_updatetime" disabled="true" timeValue=ywOrderPayment.order_pay_updatetime />
	</div>
	
	<div class="button-content">
	    <#if op_type == "1">
			<input type="button" class="btn btn_submit_text btn-confirm" value="确认" id="btn_ywOrderPayment_submit"/>
			<input type="button" class="btn btn_submit_text btn-cancel" value="取消" id="btn_ywOrderPayment_back"/>
		</#if>
		<#if op_type == "2">
			<input type="button" class="btn btn_submit_text btn-cancel" value="返回" id="btn_ywOrderPayment_back"/>
		</#if>
	</div>
</form>	
<@script>
$(function(){
  // 确认
  $("#btn_ywOrderPayment_submit").click(function(){
      var formData = $("#ywOrderPaymentForm").serializeArray();
      $.post($("#ywOrderPaymentForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
                devActAfterShowDialog(data.error_info,function(){
                  location.href = "${base}/product/ywOrderPayment.htm";
                },"suc");
            } else {
                isTimeOutdevShowDialog(data.error_info,data.infos);
            };
        }).error(function(){
            devShowDialog("系统异常");
        });
  })
  
  // 取消
  $("#btn_ywOrderPayment_back").click(function(){
      location.href = "${base}/product/ywOrderPayment.htm";
  })
  
})
        
</@script>
</@screen>