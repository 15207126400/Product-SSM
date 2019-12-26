<@screen id="ywSignInRuleInsert" title="签到规则页面" places=[{"name":"签到规则","href":"${base}/system/ywSignInRule.htm"},{"name":"添加/修改","href":"#"}]>
<form id="ywSignInRuleForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type}"/>
    <input id="rule_id" name="rule_id" type="hidden" valid="NotBlank" class="dfinput" placeholder="请输入签到规则编号" maxbytelength="10" value="${ywSignInRule.rule_id}"/>
	<div class="formtitle">
		<span class="formspan">签到规则</span>
	</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>连续签到天数</label>
					<input id="rule_continue_days" name="rule_continue_days" type="text" valid="NotBlank" class="dfinput" placeholder="请输入连续签到天数" maxbytelength="50" value="${ywSignInRule.rule_continue_days}"/>
			</div>
			<div class="form-group">
			        <label for="level" class="label"><em style="color:red;">*</em>签到规则奖励类型</label>
					<@select valid="NotBlank" placeholder="请选择签到规则奖励类型" id="rule_type" name="rule_type" dic_key="1055" value="${ywSignInRule.rule_type!''}"/>
			</div>
			<div id="points" class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>奖励积分</label>
					<input name="rule_reward_points" id="rule_reward_points" type="text" class="dfinput" placeholder="请输入奖励积分" maxbytelength="50" value="${ywSignInRule.rule_reward_points!''}"/>
			</div>
			<div id="coupon" class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>奖励优惠券</label>
					<@select placeholder="请选择优惠券" name="rule_reward_coupon" value=ywSignInRule.rule_reward_coupon datalist=coupons optionData={"value":"id","option":"name"}/>
			</div>
			<div class="form-group">
			        <label for="level" class="label"><em style="color:red;">*</em>状态</label>
			        <@select valid="NotBlank" placeholder="请选择状态" name="rule_status" dic_key="1000" value="${ywSignInRule.rule_status!''}"/>
			</div>
	<div class="button-content">
	    <input type="button" class="btn btn-success btn_text" value="确认" id="btn_ywSignInRule_submit"/>
		<input type="button" class="btn btn-success btn_text" value="取消" id="btn_ywSignInRule_back"/>
	</div>
</form>	
<@script>
$(function(){
	if($("#rule_type").val() == 2){
		$("#points").hide();
  		$("#coupon").show();
  	}else{
  		$("#points").show();
  		$("#coupon").hide();
  	}
  
  $("#rule_type").change(function(){
  	if($(this).val() == 1){
  		$("#points").show();
  		$("#coupon").hide();
  	}else{
  		$("#points").hide();
  		$("#coupon").show();
  	}
  })

  // 确认
  $("#btn_ywSignInRule_submit").click(function(){
      // 表单校验
      if (!formValidate("ywSignInRuleForm")) {
		 return false;
	  }
      var formData = $("#ywSignInRuleForm").serializeArray();
      $.post($("#ywSignInRuleForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
               devActAfterShowDialog(data.error_info,function(){
                 location.href = "${base}/system/ywSignInRule.htm";
                },"suc");
            } else if(data.error_no == "-1"){
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }else{
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }
        })
  })
  
  // 取消
  $("#btn_ywSignInRule_back").click(function(){
      location.href = "${base}/system/ywSignInRule.htm";
  })
  
})
</@script>
</@screen>