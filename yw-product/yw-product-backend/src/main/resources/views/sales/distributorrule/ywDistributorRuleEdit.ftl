<#assign base=request.contextPath />
<@screen id="ywDistributorRuleInsert" title="商家设置分销规则" places=[{"name":"分销规则设置","href":"${base}/sales/ywDistributorRule.htm"},{"name":"添加/修改","href":"#"}]>
<form id="ywDistributorRuleForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type!''}"/>
    <input id="rule_id" name="rule_id" type="hidden" class="dfinput" <#if op_type == "2">readonly="readonly"</#if> value="${ywDistributorRule.rule_id!''}"/>
	<div class="formtitle">
		<span class="formspan">分销规则设置</span>
	</div>
	
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>分销等级</label>
		<@select valid="NotBlank" placeholder="请选择分销等级" name="rule_level_type" dic_key="1008" disabled="true" value="${ywDistributorRule.rule_level_type!''}"/>
	</div>
	<div class="form-group" id="one">
		<label for="level" class="label"><em style="color:red;">*</em>一级分销商佣金比例</label>
		<input id="rule_scale_one" name="rule_scale_one" valid="NotBlank,Digital" placeholder="请输入一级分销比例（单位：%）" type="text" class="dfinput" maxlength="2" value="${ywDistributorRule.rule_scale_one!''}"/>
		<i id="dis_name">%</i>
	</div>
	<div class="form-group" id="two">
		<label for="level" class="label"><em style="color:red;">*</em>二级分销商佣金比例</label>
		<input id="rule_scale_two" name="rule_scale_two" valid="Digital" placeholder="请输入二级分销比例（单位：%）" type="text" class="dfinput" maxlength="2" value="${ywDistributorRule.rule_scale_two!''}"/>
		<i id="dis_name">%</i>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>门槛类型</label>
		<@select valid="NotBlank" placeholder="请输入门槛类型" name="rule_door_type" dic_key="1012" value="${ywDistributorRule.rule_door_type!''}"/>
	</div>
	<div class="form-group" id="door_price">
		<label for="level" class="label">门槛条件</label>
		<input id="rule_door_price" name="rule_door_price" valid="Digital" placeholder="请输入门槛条件（单位：元）" type="text" class="dfinput" maxlength="18" value="${ywDistributorRule.rule_door_price!''}"/>
		<i id="dis_name">元</i>
	</div>
	<div class="form-group">
		<label for="level" class="label">提现转账类型</label>
		<@select placeholder="请选择提现转账类型" name="rule_pay_type" dic_key="1013" value="${ywDistributorRule.rule_pay_type!''}"/>
	
		<label for="level" class="label">优惠是否有佣金</label>
		<@select valid="NotBlank" placeholder="请选择优惠是否产生佣金" name="rule_discounts" dic_key="1014" value="${ywDistributorRule.rule_discounts!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label">每月可提现次数</label>
		<input id="rule_cash_count" name="rule_cash_count" valid="Digital" placeholder="请输入每月可提现次数（单位：数字）" type="text" class="dfinput" value="${ywDistributorRule.rule_cash_count!''}"/>
	
		<label for="level" class="label">多少金额可提现</label>
		<input id="rule_cash_money" name="rule_cash_money" valid="Digital" placeholder="请输入提现金额（单位：元）" type="text" class="dfinput" value="${ywDistributorRule.rule_cash_money!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label">关系锁定天数</label>
		<input id="rule_levellock_day" name="rule_levellock_day" valid="Digital" placeholder="请输入关系锁定天数（单位：天）" type="text" class="dfinput" value="${ywDistributorRule.rule_levellock_day!''}"/>
	
		<label for="level" class="label">推广二维码</label>
		<input id="rule_QRcode_url" name="rule_QRcode_url" placeholder="请输入推广二维码" type="text" class="dfinput"  value="${ywDistributorRule.rule_QRcode_url!''}"/>
	</div>
	<div class="button-content">
	    <input type="button" class="btn btn_submit_text btn-confirm" value="确认" id="btn_ywDistributorRule_submit"/>
	    <input type="button" class="btn btn_submit_text btn-cancel" value="取消" id="btn_ywDistributorRule_back"/>
	</div>
</form>	
<@script>
$(document).ready(function(){
	var openType=${op_type};
	var rule_level_type = $("#rule_level_type").val();
	var rule_door_type = $("#rule_door_type").val();
	if(openType == 2){
		if(rule_level_type == 1){
			$("#one").show();
			$("#two").hide();
			$("#three").hide();
		}else if(rule_level_type == 2){
			$("#one").show();
			$("#two").show();
			$("#three").hide();
		}else if(rule_level_type == 3){
			$("#one").show();
			$("#two").show();
			$("#three").show();
		}
		if(rule_door_type == 0){
			$("#door_price").hide();
		}else if(rule_door_type == 1){
			$("#door_price").show();
		}
	}else{
		$("#door_price").hide();
		$("#one").hide();
		$("#two").hide();
		$("#three").hide();
	}
	
	

  // 确认
  $("#btn_ywDistributorRule_submit").click(function(){
  	  $("#rule_level_type").removeAttr("disabled");
      // 表单校验
      if (!formValidate("ywDistributorRuleForm")) {
		 return false;
	  }
      var formData = $("#ywDistributorRuleForm").serializeArray();
      $.post($("#ywDistributorRuleForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
                devActAfterShowDialog(data.error_info,function(){
                 location.href = "${base}/sales/ywDistributorRule.htm";
                },"suc");
            } else {
                isTimeOutdevShowDialog(data.error_info,data.infos);
            };
        }).error(function(){
            devShowDialog("系统异常");
        });
  })
  
  // 取消
  $("#btn_ywDistributorRule_back").click(function(){
      location.href = "${base}/sales/ywDistributorRule.htm";
  })
  
  	$("#rule_level_type").change(function(){
		var rule_level_type = $("#rule_level_type").val();
		if(rule_level_type == 1){
			$("#one").show();
			$("#two").hide();
			$("#three").hide();
		}else if(rule_level_type == 2){
			$("#one").show();
			$("#two").show();
			$("#three").hide();
		}else if(rule_level_type == 3){
			$("#one").show();
			$("#two").show();
			$("#three").show();
		}else{
			$("#one").hide();
			$("#two").hide();
			$("#three").hide();
		}
	})
  
  	$("#rule_door_type").change(function(){
  		var rule_door_type = $("#rule_door_type").val();
  		var door_price = $("#door_price");
  		if(rule_door_type == 1){
  			door_price.show();
  		}else{
  			$("#rule_door_price").val("0");
  			door_price.hide();
  		}
	})

})
        
</@script>
</@screen>