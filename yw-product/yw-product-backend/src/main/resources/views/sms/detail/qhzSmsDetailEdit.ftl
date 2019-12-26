<@screen id="qhzSmsDetailInsert" title="沙龙注册短信发送信息页面" places=[{"name":"沙龙注册短信发送信息","href":"${base}/qhz_sms_detail/qhzSmsDetail.htm"},{"name":"添加/修改","href":"#"}]>
<form id="qhzSmsDetailForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type}"/>
    <input id="id" name="id" type="hidden" value="${ywSmsDetail.id!''}"/>
	<div class="formtitle">
		<span class="formspan">沙龙注册短信发送信息</span>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>短信通道类型</label>
		<@select valid="NotBlank" placeholder="请选择短信通道类型" name="type" dic_key="1095" value=qhzSmsConfig.type!'1'/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>全部被叫号码</label>
		<input id="mobile" name="mobile" type="text" valid="NotBlank" class="dfinput" placeholder="例如: 15207126400" maxbytelength="2,147,483,647" value="${qhzSmsDetail.mobile!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>发送内容</label>
		<textarea style="width:500px;height:150px;" id="content" name="content" valid="NotBlank" class="textareainput" placeholder="例如: 今天是中秋节,预祝各位节日快乐!" cols="300" rows="5">${qhzSmsDetail.content!''}</textarea> 
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>定时发送时间</label>
		<@dateTimePicker placeholder="例如: 2017-07-04 15:07:12" name="sendTime" timeValue=qhzSmsDetail.sendTime/>
		<i>若为空,表示立即发送</i>
	</div>
	<div class="button-content">
	    <input type="button" class="btn btn-success btn_text" value="确认" id="btn_qhzSmsDetail_submit"/>
		<input type="button" class="btn btn-success btn_text" value="取消" id="btn_qhzSmsDetail_back"/>
	</div>
</form>	
<@script>
$(function(){
  // 确认
  $("#btn_qhzSmsDetail_submit").click(function(){
      // 表单校验
      if (!formValidate("qhzSmsDetailForm")) {
		 return false;
	  }
      var formData = $("#qhzSmsDetailForm").serializeArray();
      $.post($("#qhzSmsDetailForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
               devActAfterShowDialog(data.error_info,function(){
                 location.href = "${base}/qhz_sms_detail/qhzSmsDetail.htm";
                },"suc");
            } else if(data.error_no == "-1"){
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }else{
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }
        })
  })
  
  // 取消
  $("#btn_qhzSmsDetail_back").click(function(){
      location.href = "${base}/qhz_sms_detail/qhzSmsDetail.htm";
  })
  
})
</@script>
</@screen>