<#assign base=request.contextPath />
<@screen id="ywSystemNoticeInsert" title="系统公告配置页面" places=[{"name":"系统公告","href":"${base}/system/ywSystemNotice.htm"},{"name":"添加/修改","href":"#"}]>
<form id="ywSystemNoticeForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type!''}"/>
    <input id="notice_id" name="notice_id" type="hidden" class="dfinput" <#if op_type == "2">readonly="readonly"</#if> maxlength="18" value="${ywSystemNotice.notice_id!''}"/>
    <div class="formtitle">
		<span class="formspan">系统公告</span>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>公告类型</label>
		<@select valid="NotBlank" placeholder="请选择类型" id="notice_type" name="notice_type" dic_key="1047" value="${ywSystemNotice.notice_type!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>公告内容</label>
		<textarea style="border:1px solid #ADADAD;padding:10px;border-radius:5px;" rows="5" cols="100" id="notice_content" name="notice_content" valid="NotBlank" placeholder="请输入公告内容">${ywSystemNotice.notice_content!''}</textarea>
	</div>
	<div id="user-box" class="form-group">
		<label for="level" class="label">通知对象</label>
		<select id="user_id" name="user_id" placeholder="请选择通知对象" class="dfinput">
		   <option value="0">请选择通知对象</option>
		   <#if userlist?? && userlist?size gt 0>
		      <#list userlist as user>
		         <option value="${user.user_id}" <#if user.user_id == ywSystemNotice.user_id>selected="seleted"</#if>>${user.cust_name}</option>
		      </#list>
		   </#if>
		</select>
	</div>
	<div id="user-box" class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>状态</label>
		<@select valid="NotBlank" placeholder="请选择状态" name="notice_status" dic_key="1000" value="${ywSystemNotice.notice_status!'1'}"/>
	</div>
	<div class="button-content">
	    <input type="button" class="btn btn-confirm" value="确认" id="btn_ywSystemNotice_submit"/>
		<input type="button" class="btn btn-cancel" value="取消" id="btn_ywSystemNotice_back"/>
	</div>
</form>	
<@script>
$(function(){
    if($("#notice_type").val() == 0){
  		$("#user-box").hide()
  	}else if($("#notice_type").val() == 1){
  		$("#user-box").show()
  	}

  $("#notice_type").change(function(){
  	var notice_type = $("#notice_type").val();
  	$("#user_id").val(0);
  	if(notice_type == 0){
  		$("#user-box").hide();
  	}else{
  		$("#user-box").show();
  	}
  })

  // 确认
  $("#btn_ywSystemNotice_submit").click(function(){
  	  if($("#notice_type").val() == 1){
  	  	console.log($("#user_id").val())
  		if($("#user_id").val() == 0){
  			alert("请选择分类")
  		}else{
  		console.log(111)
  			// 表单校验
	      if (!formValidate("ywSystemNoticeForm")) {
			 return false;
		  }
	      var formData = $("#ywSystemNoticeForm").serializeArray();
	      $.post($("#ywSystemNoticeForm").attr("action"), formData, function(data){
	            var openType=${op_type};
	            var operateType={"0":"添加","1":"修改"};
	            if (data.error_no == 0) {
	                devActAfterShowDialog(data.error_info,function(){
	                 location.href = "${base}/system/ywSystemNotice.htm";
	                },"suc");
	            } else {
	                isTimeOutdevShowDialog(data.error_info,data.infos);
	            };
	        }).error(function(){
	             devShowDialog("系统异常");
	        });
	  	  }
  	  }else{
  	  	  $("#user_id").val(0);
	  	  // 表单校验
	      if (!formValidate("ywSystemNoticeForm")) {
			 return false;
		  }
	      var formData = $("#ywSystemNoticeForm").serializeArray();
	      
	      $.post($("#ywSystemNoticeForm").attr("action"), formData, function(data){
	            var openType=${op_type};
	            var operateType={"0":"添加","1":"修改"};
	            if (data.error_no == 0) {
	                devActAfterShowDialog(data.error_info,function(){
	                 location.href = "${base}/system/ywSystemNotice.htm";
	                },"suc");
	            } else {
	                isTimeOutdevShowDialog(data.error_info,data.infos);
	            };
	        }).error(function(){
	             devShowDialog("系统异常");
	        });
	  	  }
      
  })
  
  // 取消
  $("#btn_ywSystemNotice_back").click(function(){
      location.href = "${base}/system/ywSystemNotice.htm";
  })
})
        
</@script>
</@screen>