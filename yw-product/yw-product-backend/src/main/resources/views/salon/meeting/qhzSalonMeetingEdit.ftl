<@screen id="qhzSalonMeetingInsert" title="沙龙注册会议信息模块页面" places=[{"name":"沙龙注册会议信息模块","href":"${base}/qhz_salon_meeting/qhzSalonMeeting.htm"},{"name":"添加/修改","href":"#"}]>
<form id="qhzSalonMeetingForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type}"/>
    <input id="id" name="id" type="hidden" value="${qhzSalonMeeting.id!''}"/>
	<div class="formtitle">
		<span class="formspan">沙龙注册会议信息模块</span>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>会议名称</label>
		<input id="name" name="name" type="text" valid="NotBlank" class="dfinput" <#if op_type!=1>disabled="disabled"</#if> placeholder="请输入会议名称" maxbytelength="50" value="${qhzSalonMeeting.name!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>开始日期</label>
		<#if op_type!=1>
			<@dateTimePickerByDay valid="NotBlank" placeholder="请选择开始日期" name="start_day" disabled="true" timeValue=qhzSalonMeeting.start_day />
		<#else>
			<@dateTimePickerByDay valid="NotBlank" placeholder="请选择开始日期" name="start_day" timeValue=qhzSalonMeeting.start_day />
		</#if>
        
        <label for="level" class="label"><em style="color:red;">*</em>结束日期</label>
        <#if op_type!=1>
        	<@dateTimePickerByDay valid="NotBlank" placeholder="请选择结束日期" name="end_day" disabled="true" timeValue=qhzSalonMeeting.end_day />
        <#else>
        	<@dateTimePickerByDay valid="NotBlank" placeholder="请选择结束日期" name="end_day" timeValue=qhzSalonMeeting.end_day />
        </#if>
	</div>
	<div class="form-group">
        <label for="level" class="label"><em style="color:red;">*</em>会议人数</label>
		<input id="people_num" name="people_num" type="text" valid="Digital" class="dfinput" <#if op_type!=1>disabled="disabled"</#if> placeholder="请输入会议人数" maxbytelength="50" value="${qhzSalonMeeting.people_num!'0'}"/>
	
		<label for="level" class="label"><em style="color:red;">*</em>已签人数</label>
		<input id="people_surplus_num" name="people_surplus_num" type="text" valid="Digital" class="dfinput" <#if op_type!=1>disabled="disabled"</#if> placeholder="请输入剩余人数" maxbytelength="50" value="${qhzSalonMeeting.people_surplus_num!'0'}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>会议地址</label>
		<input id="address" name="address" type="text" valid="NotBlank" class="dfinput" <#if op_type!=1>disabled="disabled"</#if> placeholder="请输入会议地址" maxbytelength="200" value="${qhzSalonMeeting.address!''}"/>
	
		<label for="level" class="label"><em style="color:red;">*</em>会议价格</label>
		<input id="price" name="price" type="text" valid="float" class="dfinput" <#if op_type!=1>disabled="disabled"</#if> placeholder="请输入会议价格" maxbytelength="50" value="${qhzSalonMeeting.price!'0'}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>是否需要票务编码</label>
		<#if op_type!=1>
			<@select placeholder="请选择是否需要票务编码" name="is_ticket" disabled="true" dic_key="1098" value=qhzSalonMeeting.is_ticket!'0'/>
		<#else>
			<@select placeholder="请选择是否需要票务编码" name="is_ticket" dic_key="1098" value=qhzSalonMeeting.is_ticket!'0'/>
		</#if>
        
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>启用状态</label>
        <@select placeholder="请选择状态" name="status" dic_key="1000" value=qhzSalonMeeting.status!'1'/>
	</div>
	<div class="button-content">
	    <input type="button" class="btn btn-success btn_text" value="确认" id="btn_qhzSalonMeeting_submit"/>
		<input type="button" class="btn btn-success btn_text" value="返回" id="btn_qhzSalonMeeting_back"/>
	</div>
</form>	
<@script>
$(function(){
  // 确认
  $("#btn_qhzSalonMeeting_submit").click(function(){
      // 表单校验
      if (!formValidate("qhzSalonMeetingForm")) {
		 return false;
	  }
      var formData = $("#qhzSalonMeetingForm").serializeArray();
      $.post($("#qhzSalonMeetingForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
               devActAfterShowDialog(data.error_info,function(){
                 location.href = "${base}/qhz_salon_meeting/qhzSalonMeeting.htm";
                },"suc");
            } else if(data.error_no == "-1"){
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }else{
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }
        })
  })
  
  // 取消
  $("#btn_qhzSalonMeeting_back").click(function(){
      location.href = "${base}/qhz_salon_meeting/qhzSalonMeeting.htm";
  })
  
})
</@script>
</@screen>