<#assign base=request.contextPath />
<@screen id="ywMemberRoleInsert" title="会员级别信息页面" places=[{"name":"会员权限管理","href":"${base}/member/ywMemberRole.htm"},{"name":"添加/修改","href":"#"}]>
	<form id="ywMemberRoleForm" action="insertOrUpdate.json" method="post" class="form">
	   <input type="hidden" id="op_type" name="op_type" value="${op_type!''}"/>
		<input name="id" type="hidden" class="dfinput" value="${ywMemberRole.id }"/>
		
		<div class="formtitle">
			<span class="formspan">会员级别信息</span>
		</div>
		<div class="form-group">
			<label for="level" class="label"><em style="color:red;">*</em>会员级别</label>
			<input id="level" name="level" valid="NotBlank" placeholder="请输入会员级别" type="text" class="dfinput" maxlength="6" value="${ywMemberRole.level!'1'}"/>
			<i id="level_remind" style="color:#292929">请勿比按照从小到大输入数值,起始值为1</i>
		</div>
		<div class="form-group">
			<label for="level" class="label"><em style="color:red;">*</em>会员名称</label>
			<input id="levelname" name="levelname" valid="NotBlank" placeholder="请输入会员名称" type="text" class="dfinput" maxlength="6" value="${ywMemberRole.levelname!''}"/>
		</div>
		<div class="form-group">
			<label for="level" class="label"><em style="color:red;">*</em>所需消费金额</label>
			<input id="levelpoints" name="levelpoints" valid="NotBlank,Digital" placeholder="请输入所需积分（单位：整数）" type="text" class="dfinput" maxlength="8" value="${ywMemberRole.levelpoints!''}"/>
		</div>
		<div class="form-group">
			<label for="level" class="label"><em style="color:red;">*</em>享受折扣</label>
			<input id="discount" name="discount" valid="NotBlank,decimal" placeholder="请输入享受折扣（单位：小数）" type="text" class="dfinput" maxlength="4" value="${ywMemberRole.discount!''}"/>
		</div>
		<div class="form-group">
			<label for="level" class="label"><em style="color:red;">*</em>积分获取比例</label>
			<input id="proportion" name="proportion" valid="NotBlank,float" placeholder="请选输入积分获取比例（单位：整数或小数）" type="text" class="dfinput" maxlength="4" value="${ywMemberRole.proportion!''}"/>
		</div>
		<div class="form-group">
			<label for="level" class="label"><em style="color:red;">*</em>是否会员身份</label>
			<@select valid="NotBlank" placeholder="请选择是否会员身份" name = "iflevel" dic_key="1021" value="${ywMemberRole.iflevel!''}"/>
		</div>
		<div class="button-content">
		    <input type="button" class="btn btn_submit_text btn-confirm" value="确认" id="btn_ywMemberRole_submit"/>
			<input type="button" class="btn btn_submit_text btn-cancel" value="取消" id="btn_ywMemberRole_back"/>
		</div>
	</form>
<!-- js -->
<@script>
$(function(){
  // 判断输入的会员级别是否符合规定
  $("#level").blur(function(){
  	$.ajaxPost("${base}/member/ywMemberRole/judgeIsLevel.json",{level: $("#level").val()},function(data){
       if(data.level_flag == 0){
         $("#level").val("");
         $("#level_remind").text("该会员级别已存在");
         $("#level_remind").css("color","#F73E1C");
       } else {
       	 $("#level_remind").text("该会员级别可用");
         $("#level_remind").css("color","#085D21");
       }
    })
  })

  // 确认
  $("#btn_ywMemberRole_submit").click(function(){
      // 表单校验
      if (!formValidate("ywMemberRoleForm")) {
		 return false;
	  }
      var formData = $("#ywMemberRoleForm").serializeArray();
      	
      $.post($("#ywMemberRoleForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
                devActAfterShowDialog(data.error_info,function(){
                  location.href = "${base}/member/ywMemberRole.htm";
                },"suc");
            } else {
                isTimeOutdevShowDialog(data.error_info,data.infos);
            };
        }).error(function(){
             devShowDialog("系统异常");
        });
  	})
  
  	// 取消
	$("#btn_ywMemberRole_back").click(function(){
	    location.href = "${base}/member/ywMemberRole.htm";
	})
})  
</@script>
</@screen>