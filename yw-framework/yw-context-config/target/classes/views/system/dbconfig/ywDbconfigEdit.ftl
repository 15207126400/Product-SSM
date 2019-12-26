<#assign base=request.contextPath />
<@screen id="ywDbconfigInsert" title="系统数据库配置页面" places=[{"name":"数据库配置","href":"${base}/system/ywDbconfig.htm"},{"name":"添加/修改","href":"#"}]>
<form id="ywDbconfigForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type!''}"/>
    <input id="db_id" name="db_id" type="hidden" class="dfinput" <#if op_type == "2">readonly="readonly"</#if> maxlength="18" value="${ywDbconfig.db_id!''}"/>
    <div class="formtitle">
		<span class="formspan">系统数据库配置</span>
	</div>
	<div class="form-group">
	    <label for="level" class="label"><em style="color:red;">*</em>数据库类型</label>
		<@select valid="NotBlank" placeholder="请数据库类型" name = "db_type" dic_key="1001" value="${ywDbconfig.db_type!'1'}"/>
		
		<label for="level" class="label"><em style="color:red;">*</em>名称</label>
		<input id="db_name" name="db_name" valid="NotBlank" placeholder="请输入名称" type="text" class="dfinput" maxlength="50" value="${ywDbconfig.db_name!''}"/>
	</div>
	<div class="form-group">
		<input id="db_driver" name="db_driver" valid="NotBlank" placeholder="请输入驱动类" readonly="readonly" type="hidden" class="dfinput"  value="${ywDbconfig.db_driver!''}"/>
	
		<label for="level" class="label"><em style="color:red;">*</em>数据库主机/ip</label>
		<input id="db_protocol" name="db_protocol" valid="NotBlank" placeholder="请输入数据库主机/ip" type="text" class="dfinput"  value="${ywDbconfig.db_protocol!''}"/>
		
		<label for="level" class="label"><em style="color:red;">*</em>数据库端口号</label>
		<input id="db_port" name="db_port" valid="NotBlank" placeholder="请输入数据库端口号" type="text" class="dfinput"  value="${ywDbconfig.db_port!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>登录名称</label>
		<input id="db_username" name="db_username" valid="NotBlank" placeholder="请输入登录名称" type="text" class="dfinput"  value="${ywDbconfig.db_username!''}"/>
	
		<label for="level" class="label"><em style="color:red;">*</em>登录密码</label>
		<input id="db_password" name="db_password" valid="NotBlank" placeholder="请输入登录密码" type="text" class="dfinput"  value="${ywDbconfig.db_password!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>作用类型</label>
		<@select valid="NotBlank" placeholder="请选择作用类型" name = "db_function_type" dic_key="1062" value="${ywDbconfig.db_function_type!''}"/>
	
		<label for="level" class="label">请选择导入版本</label>
		<@select placeholder="请选择导入版本" name = "db_sqlimport_versiontype" dic_key="1079" value="${ywDbconfig.db_sqlimport_versiontype!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label">用户</label>
		<@select valid="NotBlank" placeholder="请选择用户" name="user_id" datalist=ywUsers value="${ywDbconfig.user_id!''}" optionData={"value":"user_id","option":"cust_name"}/>
	
		<label for="level" class="label">小程序编号</label>
		<input id="wx_appid" name="wx_appid" placeholder="请输入小程序编号" type="text" class="dfinput"  value="${ywDbconfig.wx_appid!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>状态</label>
		<@select valid="NotBlank" placeholder="请选择状态" name = "db_status" dic_key="1000" value="${ywDbconfig.db_status!'1'}"/>
	</div>
	<div class="button-content">
	    <input type="button" class="btn btn_submit_text btn-confirm" value="确认" id="btn_ywDbconfig_submit"/>
		<input type="button" class="btn btn_submit_text btn-cancel" value="取消" id="btn_ywDbconfig_back"/>
	</div>
</form>	
<@script>
$(function(){
  // 选择数据库类型
  $("#db_type").change(function(){
     if($(this).val() == "1"){
        $("#db_driver").val("com.mysql.jdbc.Driver");
        $("#db_port").val("3306");
     } else if($(this).val() == "2"){
        $("#db_driver").val("oracle.jdbc.driver.OracleDriver");
        $("#db_port").val("1521");
     } else if($(this).val() == "3"){
        $("#db_driver").val("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        $("#db_port").val("1433");
     } else {
        $("#db_driver").val("");
        $("#db_port").val("");
     }
  })
  // 确认
  $("#btn_ywDbconfig_submit").click(function(){
      // 表单校验
      if (!formValidate("ywDbconfigForm")) {
		 return false;
	  }
      var formData = $("#ywDbconfigForm").serializeArray();
      $.post($("#ywDbconfigForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
                devActAfterShowDialog(data.error_info,function(){
                 location.href = "${base}/system/ywDbconfig.htm";
                },"suc");
            } else {
                isTimeOutdevShowDialog(data.error_info,data.infos);
            };
        }).error(function(){
             devShowDialog("系统异常");
        });
  })
  
  // 取消
  $("#btn_ywDbconfig_back").click(function(){
      location.href = "${base}/system/ywDbconfig.htm";
  })
  
})
        
</@script>
</@screen>