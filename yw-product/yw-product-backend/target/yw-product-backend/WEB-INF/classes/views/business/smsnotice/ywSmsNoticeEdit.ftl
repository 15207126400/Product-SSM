<#assign base=request.contextPath />
<@screen id="ywSmsNoticeInsert" title="短信消费记录" places=[{"name":"短信消费记录","href":"${base}/sms/ywSmsNotice.htm"},{"name":"短信消费记录详情","href":"#"}]>
<form id="ywSmsNoticeForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type!''}"/>
    <input id="sms_id" name="sms_id" type="hidden" class="dfinput" value="${ywSmsNotice.sms_id!''}"/>
	
	<div class="formtitle">
		<span class="formspan">短信消费记录</span>
	</div>
	<div class="form-group">
		<label for="level" class="label">短信类型</label>
		<@select valid="NotBlank" name="sms_type" dic_key="1046" value="${ywSmsNotice.sms_type!''}" />
	
		<label for="level" class="label">模板</label>
		<input id="sms_template_id" name="sms_template_id" valid="NotBlank" placeholder="请输入模板编号" type="text" class="dfinput" value="${ywSmsNotice.sms_template_id!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label">手机号码</label>
		<input id="sms_mobile" name="sms_mobile" valid="NotBlank" placeholder="请输入手机号码" type="text" class="dfinput" value="${ywSmsNotice.sms_mobile!''}" />
	
		<label for="level" class="label">通知内容</label>
		<input id="sms_content" name="sms_content" valid="NotBlank" placeholder="请输入通知内容" type="text" class="dfinput" value="${ywSmsNotice.sms_content!''}" />
	</div>
	<div class="form-group">
		<label for="level" class="label">是否收费</label>
		<@select valid="NotBlank" name="sms_isfee" dic_key="1053" value="${ywSmsNotice.sms_isfee!''}" />
	</div>
	<div class="form-group">
		<label for="level" class="label">状态</label>
		<@select valid="NotBlank" name="sms_status" dic_key="1044" value="${ywSmsNotice.sms_status!''}" />
	</div>
	<div class="button-content">
		<input type="button" class="btn btn_submit_text btn-cancel" value="返回" id="btn_ywSmsNotice_back"/>
	</div>
</form>	
<@script>
$(function(){
  // 确认
  $("#btn_ywSmsNotice_submit").click(function(){
      // 表单校验
      if (!formValidate("ywSmsNoticeForm")) {
		 return false;
	  }
      var formData = $("#ywSmsNoticeForm").serializeArray();
      $.post($("#ywSmsNoticeForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
               devActAfterShowDialog(data.error_info,function(){
                 location.href = "${base}/sms/ywSmsNotice.htm";
                },"suc");
            } else if(data.error_no == "-1"){
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }else{
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }
        })
  })
  
  // 取消
  $("#btn_ywSmsNotice_back").click(function(){
      location.href = "${base}/sms/ywSmsNotice.htm";
  })
  
})
        
</@script>
</@screen>