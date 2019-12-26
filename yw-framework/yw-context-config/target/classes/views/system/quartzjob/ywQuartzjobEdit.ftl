<#assign base=request.contextPath />
<@screen id="ywQuartzjobInsert" title="系统定时任务页面" places=[{"name":"定时任务","href":"${base}/system/ywQuartzjob.htm"},{"name":"添加/修改","href":"#"}]>
<form id="ywQuartzjobForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type!''}"/>
    <div class="formtitle">
		<span class="formspan">定时任务</span>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>任务名称</label>
		<input id="job_name" name="job_name" type="text" class="dfinput" valid="NotBlank" placeholder="请输入任务名称" <#if op_type == "2">readonly="readonly"</#if> maxbytelength="100" value="${ywQuartzjob.job_name!''}"/>
	
		<label for="level" class="label">任务分组</label>
		<input id="job_group" name="job_group" type="text" class="dfinput" valid="NotBlank" placeholder="请输入任务分组"  maxbytelength="100" value="${ywQuartzjob.job_group!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label">任务事件</label>
		<input id="job_class" name="job_class" type="text" class="dfinput" placeholder="请输入任务事件" maxbytelength="100" value="${ywQuartzjob.job_class!''}"/>
	
		<label for="level" class="label"><em style="color:red;">*</em>触发时间</label>
		<input id="trigger_time" name="trigger_time" type="text" class="dfinput" valid="NotBlank" placeholder="请输入触发时间cron表达式：* * * * * *（*/30 * * * * *表示每分钟的第30秒执行）  " maxbytelength="100" value="${ywQuartzjob.trigger_time!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label">详细描述</label>
		<input id="description" name="description" type="text" class="dfinput" placeholder="请输入详细描述" maxbytelength="255" value="${ywQuartzjob.description!''}"/>
	
		<label for="level" class="label"><em style="color:red;">*</em>有效标志</label>
		<@select valid="NotBlank" placeholder="请选择有效标志" name = "valid_flag" dic_key="1050" value=ywQuartzjob.valid_flag!'1'/>
	</div>
	<div class="button-content">
	    <input type="button" class="btn btn_submit_text btn-confirm" value="确认" id="btn_ywQuartzjob_submit"/>
		<input type="button" class="btn btn_submit_text btn-cancel" value="取消" id="btn_ywQuartzjob_back"/>
	</div>
</form>	
<@script>
$(function(){
  // 确认
  $("#btn_ywQuartzjob_submit").click(function(){
      // 表单校验
      if (!formValidate("ywQuartzjobForm")) {
		 return false;
	  }
      var formData = $("#ywQuartzjobForm").serializeArray();
      $.post($("#ywQuartzjobForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
               devActAfterShowDialog(data.error_info,function(){
                 location.href = "${base}/system/ywQuartzjob.htm";
                },"suc");
            } else if(data.error_no == "-1"){
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }else{
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }
        })
  })
  
  // 取消
  $("#btn_ywQuartzjob_back").click(function(){
      location.href = "${base}/system/ywQuartzjob.htm";
  })
  
})
        
</@script>
</@screen>