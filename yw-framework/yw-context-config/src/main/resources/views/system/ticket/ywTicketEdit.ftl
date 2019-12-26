<#assign base=request.contextPath />
<@screen id="ywTicketInsert" title="抽奖配置" places=[{"name":"抽奖配置","href":"${base}/system/ywTicket.htm"},{"name":"添加/修改","href":"#"}]>
<form id="ywTicketForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type!''}"/>
    <input id="ticket_id" name="ticket_id" type="hidden" class="dfinput" value="${ywTicket.ticket_id!''}"/>
	<div class="formtitle">
		<span class="formspan">抽奖配置</span>
	</div>
	<div class="form-group">
		<label for="level" class="label">抽奖人员姓名</label>
		<input id="ticket_name" name="ticket_name" type="text" class="dfinput" value="${ywTicket.ticket_name!''}"/>
	
		<label for="level" class="label">抽奖人员学校</label>
		<input id="school_name" name="school_name" type="text" class="dfinput" value="${ywTicket.school_name!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label">设置是否中奖</label>
		<input id="ticket_flag" name="ticket_flag" type="text" class="dfinput" value="${ywTicket.ticket_flag!''}"/>
	</div>
	<div class="button-content">
	    <input type="button" class="btn btn_submit_text btn-confirm" value="确认" id="btn_ywTicket_submit"/>
		<input type="button" class="btn btn_submit_text btn-cancel" value="取消" id="btn_ywTicket_back"/>
	</div>
</form>	
<@script>
$(function(){
  // 确认
  $("#btn_ywTicket_submit").click(function(){
      var formData = $("#ywTicketForm").serializeArray();
      $.post($("#ywTicketForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
                devActAfterShowDialog(data.error_info,function(){
                 location.href = "${base}/system/ywTicket.htm";
                },"suc");
            } else {
                isTimeOutdevShowDialog(data.error_info,data.infos);
            };
        }).error(function(){
            devShowDialog("系统异常");
        });
  })
  
  // 取消
  $("#btn_ywTicket_back").click(function(){
      location.href = "${base}/system/ywTicket.htm";
  })
  
})
        
</@script>
</@screen>