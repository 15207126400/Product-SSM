<#assign base=request.contextPath />
<@screen id="ywSmsComboInsert" title="短信套餐页面" places=[{"name":"短信套餐","href":"${base}/sms/ywSmsCombo.htm"},{"name":"添加/修改","href":"#"}]>
<form id="ywSmsComboForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type!''}"/>
	<input id="combo_id" name="combo_id" type="hidden" value="${ywSmsCombo.combo_id!''}"/>
	<div class="formtitle">
		<span class="formspan">短信套餐</span>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>套餐类型</label>
		<@select valid="NotBlank" placeholder="请选择套餐类型" name="combo_type" dic_key="1045" value=ywSmsCombo.combo_type!''/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>短信条数</label>
		<input id="combo_number" name="combo_number" valid="NotBlank,Digital" placeholder="请输入短信条数（单位：千）" type="text" class="dfinput" maxbytelength="10" value="${ywSmsCombo.combo_number!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>短信单价</label>
		<input id="combo_unitprice" name="combo_unitprice" valid="NotBlank,decimal" placeholder="请输入短信条数（单位：元）" type="text" class="dfinput" maxbytelength="100" value="${ywSmsCombo.combo_unitprice!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label">备注</label>
		<textarea id="combo_remark" name="combo_remark" placeholder="请输入备注" class="textareainput" cols="100" rows="3" value="${ywSmsCombo.combo_remark!''}"></textarea>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>状态</label>
		<@select valid="NotBlank" placeholder="请选择状态" name="combo_status" dic_key="1000" value=ywSmsCombo.combo_status!''/>
	</div>
	<div class="button-content">
	    <input type="button" class="btn btn_submit_text btn-confirm" value="确认" id="btn_ywSmsCombo_submit"/>
		<input type="button" class="btn btn_submit_text btn-cancel" value="取消" id="btn_ywSmsCombo_back"/>
	</div>
</form>	
<@script>
$(function(){
  // 确认
  $("#btn_ywSmsCombo_submit").click(function(){
      // 表单校验
      if (!formValidate("ywSmsComboForm")) {
		 return false;
	  }
      var formData = $("#ywSmsComboForm").serializeArray();
      $.post($("#ywSmsComboForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
               devActAfterShowDialog(data.error_info,function(){
                 location.href = "${base}/sms/ywSmsCombo.htm";
                },"suc");
            } else if(data.error_no == "-1"){
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }else{
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }
        })
  })
  
  // 取消
  $("#btn_ywSmsCombo_back").click(function(){
      location.href = "${base}/sms/ywSmsCombo.htm";
  })
  
})
        
</@script>
</@screen>