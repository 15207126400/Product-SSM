<@screen id="ywWithdrawInsert" title="商家提现申请表页面" places=[{"name":"商家提现申请表","href":"${base}/yw_withdraw/ywWithdraw.htm"},{"name":"添加/修改","href":"#"}]>
<form id="ywWithdrawForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type}"/>
    <input id="id" name="id" type="hidden" value="${ywWithdraw.id!''}"/>
	<div class="formtitle">
		<span class="formspan">商家提现申请表</span>
	</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>提现金额</label>
					<input id="withdraw_price" name="withdraw_price" type="text" valid="NotBlank" class="dfinput" placeholder="请输入提现金额" maxbytelength="50" value="${ywWithdraw.withdraw_price!''}"/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>提现方式  1.微信(线上) 2.微信(线下) 3.支付宝(线下) 4.银行卡(线下)</label>
					<input id="withdraw_type" name="withdraw_type" type="text" valid="NotBlank" class="dfinput" placeholder="请输入提现方式  1.微信(线上) 2.微信(线下) 3.支付宝(线下) 4.银行卡(线下)" maxbytelength="2" value="${ywWithdraw.withdraw_type!''}"/>
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>收款账号</label>
					<input id="withdraw_identity" name="withdraw_identity" type="text" valid="NotBlank" class="dfinput" placeholder="请输入收款账号" maxbytelength="50" value="${ywWithdraw.withdraw_identity!''}"/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>提现状态(1.待处理 2.已处理)</label>
					<input id="withdraw_status" name="withdraw_status" type="text" valid="NotBlank" class="dfinput" placeholder="请输入提现状态(1.待处理 2.已处理)" maxbytelength="2" value="${ywWithdraw.withdraw_status!''}"/>
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>创建时间</label>
					<input id="withdraw_createtime" name="withdraw_createtime" type="text" valid="NotBlank" class="dfinput" placeholder="请输入创建时间" maxbytelength="19" value="${ywWithdraw.withdraw_createtime!''}"/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>更新时间</label>
					<input id="withdraw_updatetime" name="withdraw_updatetime" type="text" valid="NotBlank" class="dfinput" placeholder="请输入更新时间" maxbytelength="19" value="${ywWithdraw.withdraw_updatetime!''}"/>
			</div>
	<div class="button-content">
	    <input type="button" class="btn btn-success btn_text" value="确认" id="btn_ywWithdraw_submit"/>
		<input type="button" class="btn btn-success btn_text" value="取消" id="btn_ywWithdraw_back"/>
	</div>
</form>	
<@script>
$(function(){
  // 确认
  $("#btn_ywWithdraw_submit").click(function(){
      // 表单校验
      if (!formValidate("ywWithdrawForm")) {
		 return false;
	  }
      var formData = $("#ywWithdrawForm").serializeArray();
      $.post($("#ywWithdrawForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
               devActAfterShowDialog(data.error_info,function(){
                 location.href = "${base}/yw_withdraw/ywWithdraw.htm";
                },"suc");
            } else if(data.error_no == "-1"){
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }else{
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }
        })
  })
  
  // 取消
  $("#btn_ywWithdraw_back").click(function(){
      location.href = "${base}/yw_withdraw/ywWithdraw.htm";
  })
  
})
</@script>
</@screen>