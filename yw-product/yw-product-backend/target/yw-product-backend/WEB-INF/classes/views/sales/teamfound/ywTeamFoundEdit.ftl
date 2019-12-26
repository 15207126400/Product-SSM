<#assign base=request.contextPath />
<@screen id="ywTeamFoundInsert" title="开团信息页面" places=[{"name":"开团信息","href":"${base}/sales/ywTeamFound.htm"},{"name":"添加/修改","href":"#"}]>
<form id="ywTeamFoundForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type!''}"/>
    <input id="found_id" name="found_id" type="hidden" class="dfinput" <#if op_type == "2">readonly="readonly"</#if> value="${ywTeamFound.found_id!''}"/>
	<div class="formtitle">
		<span class="formspan">开团信息</span>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>开团时间</label>
		<@dateTimePicker valid="NotBlank" placeholder="请输入团时间" name="found_time" timeValue=ywTeamFound.found_time />
	
		<label for="level" class="label"><em style="color:red;">*</em>成团截止时间</label>
		<@dateTimePicker valid="NotBlank" placeholder="请输入成团截止时间" name="found_end_time" timeValue=ywTeamFound.found_end_time />
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>团长编号</label>
		<input id="user_id" name="user_id" valid="NotBlank" placeholder="请输入团长编号" type="text" class="dfinput" maxlength="18" value="${ywTeamFound.user_id!''}"/>
		<i id="user_id"></i>
	
		<label for="level" class="label"><em style="color:red;">*</em>拼团活动编号</label>
		<input id="team_id" name="team_id" valid="NotBlank" placeholder="请输入拼团活动编号" type="text" class="dfinput" maxlength="18" value="${ywTeamFound.team_id!''}"/>
		<i id="team_id"></i>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>团长用户昵称</label>
		<input id="nickname" name="nickname" valid="NotBlank" placeholder="请输入团长用户昵称" type="text" class="dfinput" maxlength="18" value="${ywTeamFound.nickname!''}"/>
		<i id="nickname"></i>
	
		<#-- 同步文件上传 -->
	    <@fileSyncUpload  isMust=true title="团长头像" imageUrl=ywTeamFound.head_pic/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>团长订单编号</label>
		<input id="order_id" name="order_id" valid="NotBlank" placeholder="请输入团长订单编号" type="text" class="dfinput" maxlength="18" value="${ywTeamFound.order_id!''}"/>
		<i id="order_id"></i>
	
		<label for="level" class="label"><em style="color:red;">*</em>已参团人数</label>
		<input id="found_join" name="found_join" valid="NotBlank,Digital" placeholder="请输入已参团人数" type="text" class="dfinput" maxlength="18" value="${ywTeamFound.found_join!''}"/>
		<i id="found_join"></i>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>需多少人成团</label>
		<input id="need" name="need" valid="NotBlank,Digital" placeholder="请输入需多少人成团" type="text" class="dfinput" maxlength="18" value="${ywTeamFound.need!''}"/>
		<i id="need"></i>
	
		<label for="level" class="label"><em style="color:red;">*</em>拼团价格</label>
		<input id="price" name="price" valid="NotBlank,Digital" placeholder="请输入拼团价格（单位：元）" type="text" class="dfinput" maxlength="18" value="${ywTeamFound.price!''}"/>
		<i></i>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>商品原价</label>
		<input id="product_price" name="product_price" valid="NotBlank,Digital" placeholder="请输入商品原价（单位：元）" type="text" class="dfinput" maxlength="18" value="${ywTeamFound.product_price!''}"/>
		<i id="product_price"></i>
	
		<label for="level" class="label"><em style="color:red;">*</em>状态</label>
		<@select valid="NotBlank" placeholder="请选择状态" name = "status" dic_key="1025" value="${ywTeamFound.status!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label">团长佣金领取状态</label>
		<@select valid="NotBlank" placeholder="请选择团长佣金领取状态" name = "bonus_status" dic_key="1033" value="${ywTeamFound.bonus_status!''}"/>
	</div>
	<div class="button-content">
	    <input type="button" class="btn btn_submit_text btn-confirm" value="确认" id="btn_ywTeamFound_submit"/>
		<input type="button" class="btn btn_submit_text btn-cancel" value="取消" id="btn_ywTeamFound_back"/>
	</div>
</form>	
<@script>
$(function(){
  // 审核通过
  $("#btn_ywTeamFound_submit").click(function(){
      // 表单校验
      if (!formValidate("ywTeamFoundForm")) {
		 return false;
	  }
        var formData = new FormData($("#ywTeamFoundForm")[0]);
      //var formData = $("#ywTeamFoundForm").serializeArray();
      $.ajax({  
          url: $("#ywTeamFoundForm").attr("action") ,  
          type: 'POST',  
          data: formData,  
          async: false,  
          cache: false,  
          contentType: false,  
          processData: false,  
          success: function (data) {  
            if (data.error_no == 0) {
                devActAfterShowDialog(data.error_info,function(){
                 location.href = "${base}/sales/ywTeamFound.htm";
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
  $("#btn_ywTeamFound_back").click(function(){
      location.href = "${base}/sales/ywTeamFound.htm";
  })
  
})
        
</@script>
</@screen>