<#assign base=request.contextPath />
<@screen id="ywTeamFollowInsert" title="参团信息页面" places=[{"name":"参团信息","href":"${base}/sales/ywTeamFollow.htm"},{"name":"添加/修改","href":"#"}]>
<form id="ywTeamFollowForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type!''}"/>
    <input id="follow_id" name="follow_id" type="hidden" class="dfinput"  value="${ywTeamFollow.follow_id!''}"/>
	<div class="formtitle">
		<span class="formspan">参团信息</span>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>参团会员编号</label>
		<input id="follow_user_id" name="follow_user_id" valid="NotBlank" placeholder="请输入参团会员编号" type="text" class="dfinput" maxlength="18" value="${ywTeamFollow.follow_user_id!''}"/>
		<i id="follow_user_id"></i>
	
		<label for="level" class="label"><em style="color:red;">*</em>参团会员昵称</label>
		<input id="follow_user_nickname" name="follow_user_nickname" valid="NotBlank" placeholder="请输入参团会员昵称" type="text" class="dfinput" maxlength="18" value="${ywTeamFollow.follow_user_nickname!''}"/>
		<i id="follow_user_nickname"></i>
	</div>
	<div class="form-group">
	    <label for="level" class="label"><em style="color:red;">*</em>参团时间</label>
		<@dateTimePicker valid="NotBlank" placeholder="请输入参团时间" name="follow_time" timeValue=ywTeamFollow.follow_time />
		
		<#-- 同步文件上传 -->
		<@fileSyncUpload isMust=true  title="会员头像" imageUrl=ywTeamFollow.follow_user_head_pic />
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>订单编号</label>
		<input id="order_id" name="order_id" valid="NotBlank" placeholder="请输入订单编号" type="text" class="dfinput"   value="${ywTeamFollow.order_id!''}"/>
	
		<label for="level" class="label"><em style="color:red;">*</em>开团编号</label>
		<input id="found_id" name="found_id" valid="NotBlank" placeholder="请输入开团编号" type="text" class="dfinput"   value="${ywTeamFollow.found_id!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>开团人用户编号</label>
		<input id="found_user_id" name="found_user_id" valid="NotBlank" placeholder="请输入开团人用户编号" type="text" class="dfinput"   value="${ywTeamFollow.found_user_id!''}"/>
	
		<label for="level" class="label"><em style="color:red;">*</em>拼团活动编号</label>
		<input id="team_id" name="team_id" valid="NotBlank" placeholder="请输入拼团活动编号" type="text" class="dfinput"   value="${ywTeamFollow.team_id!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>参团状态</label>
		<@select valid="NotBlank" placeholder="请选择参团状态" name="status" dic_key="1024" value="${ywTeamFollow.status!''}"/>
	
		<label for="level" class="label">抽奖团是否中奖</label>
		<@select placeholder="请选择抽奖团是否中奖" name="is_win" dic_key="1032" value="${ywTeamFollow.is_win!''}"/>
	</div>
	<div class="button-content">
	    <input type="button" class="btn btn_submit_text btn-confirm" value="确认" id="btn_ywTeamFollow_submit"/>
		<input type="button" class="btn btn_submit_text btn-cancel" value="取消" id="btn_ywTeamFollow_back"/>
	</div>
</form>	
<@script>
$(function(){
  // 审核通过
  $("#btn_ywTeamFollow_submit").click(function(){
       // 表单校验
      if (!formValidate("ywTeamFollowForm")) {
		 return false;
	  }
       var formData = new FormData($("#ywTeamFollowForm")[0]);
      //var formData = $("#ywTeamFollowForm").serializeArray();
      $.ajax({  
          url: $("#ywTeamFollowForm").attr("action") ,  
          type: 'POST',  
          data: formData,  
          async: false,  
          cache: false,  
          contentType: false,  
          processData: false,  
          success: function (data) {  
            if (data.error_no == 0) {
                devActAfterShowDialog(data.error_info,function(){
                  location.href = "${base}/sales/ywTeamFollow.htm";
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
  $("#btn_ywTeamFollow_back").click(function(){
      location.href = "${base}/sales/ywTeamFollow.htm";
  })
})
        
</@script>
</@screen>