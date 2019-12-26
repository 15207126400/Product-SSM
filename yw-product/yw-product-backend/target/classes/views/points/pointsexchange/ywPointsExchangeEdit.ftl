<@screen id="ywPointsExchangeInsert" title="积分兑换页面" places=[{"name":"积分兑换","href":"${base}/points/ywPointsExchange.htm"},{"name":"添加/修改","href":"#"}]>
<form id="ywPointsExchangeForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type}"/>
    <input id="exchange_id" name="exchange_id" type="hidden" value="${ywPointsExchange.exchange_id}"/>
    <input id="user_id" name="user_id" type="hidden" value="${ywPointsExchange.user_id}"/>
	<div class="formtitle">
		<span class="formspan">积分兑换</span>
	</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>用户昵称</label>
					<input id="nickname" name="nickname" type="text" valid="NotBlank" class="dfinput" disabled="true" placeholder="请输入用户编号" value="${nickname}"/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>商品类型</label>
			        <@select valid="NotBlank" placeholder="请选择积分兑换商品类型" name="exchange_type" dic_key="1059" disabled="true" value="${ywPointsExchange.exchange_type!''}"/>
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>消费积分</label>
					<input id="points" name="points" type="text" valid="NotBlank" class="dfinput" disabled="true" placeholder="请输入消费积分" maxbytelength="100" value="${ywPointsExchange.points}"/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>消费金额</label>
					<input id="price" name="price" type="text" class="dfinput" disabled="true" maxbytelength="100" value="${ywPointsExchange.price}"/>
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em> 商品编号</label>
					<@select2 placeholder="请选择积分兑换商品" name="product_id" valid="NotBlank" disabled="true" value=ywPointsExchange.product_id datalist=products optionData={"value":"id","option":"title"}/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>兑换时间</label>
			        <@dateTimePicker valid="NotBlank" placeholder="请选择兑换时间" disabled="true" name="create_datetime" timeValue=ywPointsExchange.create_datetime/>
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>兑换状态</label>
					<@select valid="NotBlank" placeholder="请选择兑换状态" name="status" dic_key="1060" disabled="true" value="${ywPointsExchange.status!''}"/>
			</div>
	<div class="button-content">
		<#--
	    <input type="button" class="btn btn-success btn_text" value="确认" id="btn_ywPointsExchange_submit"/>
		-->
		<input type="button" class="btn btn-success btn_text" value="返回" id="btn_ywPointsExchange_back"/>
	</div>
</form>	
<@script>
$(function(){
  // 确认
  $("#btn_ywPointsExchange_submit").click(function(){
      // 表单校验
      if (!formValidate("ywPointsExchangeForm")) {
		 return false;
	  }
      var formData = $("#ywPointsExchangeForm").serializeArray();
      $.post($("#ywPointsExchangeForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
               devActAfterShowDialog(data.error_info,function(){
                 location.href = "${base}/points/ywPointsExchange.htm";
                },"suc");
            } else if(data.error_no == "-1"){
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }else{
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }
        })
  })
  
  // 取消
  $("#btn_ywPointsExchange_back").click(function(){
      location.href = "${base}/points/ywPointsExchange.htm";
  })
  
})
</@script>
</@screen>