<#assign base=request.contextPath />
<@screen id="ywNoticeInsert" title="公告栏信息页面" places=[{"name":"公告栏信息","href":"${base}/product/ywNotice.htm"},{"name":"添加/修改","href":"#"}]>
<form id="ywNoticeForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type!''}"/>
	<input id="noc_id" name="noc_id" type="hidden" class="dfinput" value="${ywNotice.noc_id!''}"/>
	<div class="formtitle">
		<span class="formspan">公告栏信息</span>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>应用场景</label>
		<@select valid="NotBlank" placeholder="请选择应用场景" name="scene_type" dic_key="1096" value="${ywNotice.scene_type }" />
	</div>
	<div class="form-group">
		<label for="level" class="label">发布人</label>
		<input id="create_name" name="create_name" disabled="true" type="text" class="dfinput" placeholder="" value="${ywNotice.create_name!''}"/>
	
		<label for="level" class="label">发布时间</label>
        <@dateTimePicker name="create_time" disabled="true" timeValue=ywNotice.create_time />
	</div>
	<div class="form-group">
		<label for="level" class="label">更新人</label>
		<input id="update_name" name="update_name" disabled="true" type="text" class="dfinput" placeholder="" value="${ywNotice.update_name!''}"/>
        
        <label for="level" class="label">更新时间</label>
		<@dateTimePicker name="update_time" disabled="true" timeValue=ywNotice.update_time />
	</div>
	<div class="form-group">
        <label for="level" class="label"><em style="color:red;">*</em>启用状态</label>
        <@select placeholder="请选择状态" name="status" dic_key="1000" value=ywNotice.status!'1'/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>内容</label>
		<textarea style="border:1px solid #ADADAD;padding:10px;border-radius:5px;" rows="5" cols="100" id="noc_content" name="noc_content" valid="NotBlank" placeholder="请输入公告内容">${ywNotice.noc_content!''}</textarea>
	</div>
	<div class="button-content">
	    <input type="button" class="btn btn_submit_text btn-confirm" value="确认" id="btn_ywNotice_submit"/>
		<input type="button" class="btn btn_submit_text btn-cancel" value="取消" id="btn_ywNotice_back"/>
	</div>
</form>	
<@script>
$(function(){
  
    $("#btn_ywNotice_submit").click(function(){
      // 表单校验
      if (!formValidate("ywNoticeForm")) {
		 return false;
	  }
	  	
      var formData = new FormData($("#ywNoticeForm")[0]);
      //var formData = $("#ywNoticeForm").serializeArray();
      $.ajax({  
          url: $("#ywNoticeForm").attr("action") ,  
          type: 'POST',  
          data: formData,  
          async: false,  
          cache: false,  
          contentType: false,  
          processData: false,  
          success: function (data) {  
            if (data.error_no == 0) {
                devActAfterShowDialog(data.error_info,function(){
                  location.href = "${base}/product/ywNotice.htm";
                },"suc");
            } else {
                isTimeOutdevShowDialog(data.error_info,data.infos);
            };
          },  
          error: function (returndata) {  
             devShowDialog("系统异常");
          }  
     });  
  })
  
  // 取消
  $("#btn_ywNotice_back").click(function(){
      location.href = "${base}/product/ywNotice.htm";
  })
  
})
        
</@script>
</@screen>