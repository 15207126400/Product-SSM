<#assign base=request.contextPath />
<@screen id="ywMember" title="会员信息" places=[{"name":"会员信息","href":"${base}/member/ywMember.htm"},{"name":"会员信息详情","href":"#"}]>
	<form id="ywMemberForm" action="insertOrUpdate.json" method="post" class="form">
	   <input type="hidden" id="op_type" name="op_type" value="${op_type!''}"/>
	   <input type="hidden" id="auditing" name="auditing" value="${auditing!''}"/>
		<input name="id" type="hidden" class="dfinput" value="${member.id }"/>
		<div class="formtitle">
			<span class="formspan">基本信息</span>
		</div>
		<div class="form-group">
			<label for="level" class="label">openid</label>
			<input id="openid" name="openid" type="text" class="dfinput" <#if op_type=="2">readonly="readonly"</#if> value="${member.openid!'' }"/>
		
			<label for="level" class="label">微信昵称</label>
			<input id="nickname" name="nickname" type="text" class="dfinput" <#if op_type=="2">readonly="readonly"</#if> value="${member.nickname!'' }"/>
		</div>
		<div class="form-group">
			<label for="level" class="label">微信头像</label>
			<image style="width:100px;height:100px" src="${member.avatarUrl }" />
		</div>
		<div class="form-group">
			<label for="level" class="label">真实姓名</label>
			<input id="realname" name="realname" type="text" class="dfinput" <#if op_type=="2">readonly="readonly"</#if> value="${member.realname!'' }"/>
		
			<label for="level" class="label">联系电话</label>
			<input id="phone" name="phone" type="text" class="dfinput" <#if op_type=="2">readonly="readonly"</#if> value="${member.phone!'' }"/>
		</div>
		<div class="form-group">
			<label for="level" class="label">地址</label>
			<input id="address" name="address" type="text" class="dfinput" <#if op_type=="2">readonly="readonly"</#if> value="${member.address!'' }"/>
		
			<label for="level" class="label">会员级别</label>
			<select id="level" name="level" class="dfinput">
		      <#list ywMemberRoles as item>
		       	<option value="${item.id!''}" <#if member.level == item.id>selected="selected"</#if>>${item.levelname}</option>
		      </#list>
			</select>
		</div>
		<div class="form-group">
			<label for="level" class="label">店铺名称</label>
			<input id="shopname" name="shopname" type="text" class="dfinput" <#if op_type=="2">readonly="readonly"</#if> value="${member.shopname!'' }"/>
			
			<label for="level" class="label">享受折扣</label>
			<input id="discount" name="discount" type="text" class="dfinput" <#if op_type=="2">readonly="readonly"</#if> value="${member.discount!'' }"/>
			<i>(单位:折)</i>
		</div>
		<div class="form-group">
			<label for="level" class="label">审核状态</label>
			<@select valid="NotBlank" name="is_auditing" disabled="true" dic_key="1078" value="${member.is_auditing!''}" />
		
			<label for="level" class="label">累积消费金额</label>
			<input id="ac_price" name="ac_price" type="text" class="dfinput" <#if op_type=="2">readonly="readonly"</#if> value="${member.ac_price!'' }"/>
			<i>(单位:元)</i>
		</div>
		<input type="hidden" id="auditing_falg" name="auditing_falg" value="${auditing_falg!''}"/>
		<div class="button-content">
			<#if op_type == 3>
				<input type="button" class="btn btn_submit_text btn-cancel" value="通过" id="btn_ywMember_submit"/>
				<input type="button" class="btn btn_submit_text btn-cancel" value="拒绝通过" id="btn_ywMember_no_submit"/>
		    <#else>
		        <input type="button" class="btn btn_submit_text btn-cancel" value="确认" id="btn_ywMember_update"/>
				<input type="button" class="btn btn_submit_text btn-cancel" value="返回" id="btn_ywMember_back"/>
			</#if>
		</div>	
				
	</form>
<@script>
$(function(){	
	
  // 通过审核
  $("#btn_ywMember_submit").click(function(){
  	  $("#createTime").removeAttr("disabled");
  	  $("#level").removeAttr("disabled");
  	  $("#is_auditing").removeAttr("disabled");
  	  $("#auditing_falg").val("2");
  	  
      // 表单校验
      if (!formValidate("ywMemberForm")) {
		 return false;
	  }
	  
      var formData = $("#ywMemberForm").serializeArray();
      $.post($("#ywMemberForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            
            if (data.error_no == 0) {
                devActAfterShowDialog(data.error_info,function(){
                  location.href = "${base}/member/ywMember.htm";
                },"suc");
            } else {
               isTimeOutdevShowDialog(data.error_info,data.infos);
            };
        }).error(function(){
            devShowDialog("系统异常");
        });
  })
  
  // 拒绝通过审核
  $("#btn_ywMember_no_submit").click(function(){
  	  $("#createTime").removeAttr("disabled");
  	  $("#level").removeAttr("disabled");
  	  $("#is_auditing").removeAttr("disabled");
  	  $("#auditing_falg").val("3");
  	  
      // 表单校验
      if (!formValidate("ywMemberForm")) {
		 return false;
	  }
	  
      var formData = $("#ywMemberForm").serializeArray();
      $.post($("#ywMemberForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            
            if (data.error_no == 0) {
                devActAfterShowDialog(data.error_info,function(){
                  location.href = "${base}/member/ywMember.htm";
                },"suc");
            } else {
               isTimeOutdevShowDialog(data.error_info,data.infos);
            };
        }).error(function(){
            devShowDialog("系统异常");
        });
  })
  
  // 确认修改
  $("#btn_ywMember_update").click(function(){
        // 表单校验
      if (!formValidate("ywMemberForm")) {
		 return false;
	  }
	  
      var formData = $("#ywMemberForm").serializeArray();
      $.post($("#ywMemberForm").attr("action"), formData, function(data){
            if (data.error_no == 0) {
                devActAfterShowDialog(data.error_info,function(){
                  location.href = "${base}/member/ywMember.htm";
                },"suc");
            } else {
               isTimeOutdevShowDialog(data.error_info,data.infos);
            };
        }).error(function(){
            devShowDialog("系统异常");
        });
  })

 // 取消
  $("#btn_ywMember_back").click(function(){
      location.href = "${base}/member/ywMember.htm";
  })
});
</@script>	
</@screen>