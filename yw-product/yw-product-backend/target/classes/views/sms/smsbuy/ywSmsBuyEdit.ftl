<#assign base=request.contextPath />
<@screen id="ywSmsBuyInsert" title="短信购买记录" places=[{"name":"短信购买记录","href":"${base}/sms/ywSmsBuy.htm"},{"name":"详情","href":"#"}]>
<form id="ywSmsBuyForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type!''}"/>
	<input id="buy_id" name="buy_id" type="hidden" value="${ywSmsBuy.buy_id!''}"/>
	<div class="formtitle">
		<span class="formspan">短信购买记录</span>
	</div>
	<div class="form-group">
		<label for="level" class="label">短信购买者编号</label>
		<input id="user_id" name="user_id" type="text" class="dfinput" placeholder="请输入短信购买者编号" readonly="readonly" value="${ywSmsBuy.user_id!''}"/>
		
		<label for="level" class="label">短信模板编号</label>
		<input id="combo_id" name="combo_id" type="text" class="dfinput" placeholder="请输入短信模板编号" readonly="readonly" value="${ywSmsBuy.combo_id!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label">短信条数</label>
		<input id="buy_number" name="buy_number" type="text" class="dfinput" placeholder="请输入短信条数（单位：条）" readonly="readonly" value="${ywSmsBuy.buy_number!''}"/>
		
		<label for="level" class="label">短信已使用</label>
		<input id="buy_usenumber" name="buy_usenumber" type="text" class="dfinput" placeholder="请输入短信已使用（单位：条）" readonly="readonly" value="${ywSmsBuy.buy_usenumber!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label">短信价格</label>
		<input id="buy_price" name="buy_price" type="text" class="dfinput" placeholder="请输入短信价格（单位：元）" readonly="readonly" value="${ywSmsBuy.buy_price!''}"/>
		
		<label for="level" class="label">订单号</label>
		<input id="order_sn" name="order_sn" type="text" class="dfinput" placeholder="请输入订单号" readonly="readonly" value="${ywSmsBuy.order_sn!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label">创建时间</label>
		<@dateTimePicker valid="NotBlank" placeholder="请输入创建时间" disabled="true" name="buy_createtime" timeValue=ywSmsBuy.buy_createtime/>
		
		<label for="level" class="label">更新时间</label>
		<@dateTimePicker valid="NotBlank" placeholder="请输入更新时间" disabled="true" name="buy_updatetime" timeValue=ywSmsBuy.buy_updatetime/>
	</div>
	<div class="form-group">
		<label for="level" class="label">备注</label>
		<textarea id="buy_remark" name="buy_remark" placeholder="请输入备注" class="textareainput" cols="100" rows="3" readonly="readonly" value="${ywSmsBuy.buy_remark!''}"></textarea>
	</div>
	<div class="form-group">
		<label for="level" class="label">状态</label>
		<@select valid="NotBlank" placeholder="请选择状态" disabled="true" name="buy_status" dic_key="1043" value=ywSmsBuy.buy_status!''/>
	</div>
	<div class="button-content">
		<input type="button" class="btn btn_submit_text btn-cancel" value="返回" id="btn_ywSmsBuy_back"/>
	</div>
</form>	
<@script>
$(function(){
  // 确认
  $("#btn_ywSmsBuy_submit").click(function(){
  	  $("#buy_createtime").removeAttr("disabled");
  	  $("#buy_updatetime").removeAttr("disabled");
  	  $("#buy_status").removeAttr("disabled");
      // 表单校验
      if (!formValidate("ywSmsBuyForm")) {
		 return false;
	  }
      var formData = $("#ywSmsBuyForm").serializeArray();
      $.post($("#ywSmsBuyForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
               devActAfterShowDialog(data.error_info,function(){
                 location.href = "${base}/sms/ywSmsBuy.htm";
                },"suc");
            } else if(data.error_no == "-1"){
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }else{
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }
        })
  })
  
  // 取消
  $("#btn_ywSmsBuy_back").click(function(){
      location.href = "${base}/sms/ywSmsBuy.htm";
  })
  
})
        
</@script>
</@screen>