<#assign base=request.contextPath />
<@screen id="ywMenuInsert" title="系统字典增加页面" places=[{"name":"系统菜单","href":"${base}/system/ywMenu.htm"},{"name":"添加/修改","href":"#"}]>
<form id="ywMenuForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type!''}"/>
    <input id="mu_id" name="mu_id" type="hidden" class="dfinput" <#if op_type == "2">readonly="readonly"</#if> value="${ywMenu.mu_id!''}"/>
    <div class="formtitle">
		<span class="formspan">系统菜单</span>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>菜单名称</label>
		<input id="mu_name" name="mu_name" valid="NotBlank" placeholder="请输入菜单名称" type="text" class="dfinput" value="${ywMenu.mu_name!''}"/>
	
		<label for="level" class="label">菜单图片</label>
		<input id="mu_icon" name="mu_icon" placeholder="请输入菜单图片" type="text" class="dfinput" value="${ywMenu.mu_icon!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label">父菜单</label>
		<select id="mu_parentid" name="mu_parentid" placeholder="请选择父菜单" class="dfinput">
		        <option value="">请选择父菜单</option>
		   <#if menus?? && menus?size gt 0>
		      <#list menus as menu>
		         <option value="${menu.mu_id}" <#if menu.mu_id == ywMenu.mu_parentid>selected="seleted"</#if>>${menu.mu_name}</option>
		      </#list>
		   </#if>
		</select>
	
		<label for="level" class="label">菜单url</label>
		<input id="mu_url" name="mu_url" placeholder="请输入菜单url" type="text" class="dfinput" value="${ywMenu.mu_url!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>菜单级别</label>
		<@select valid="NotBlank" placeholder="请选择菜单级别" name = "mu_level" dic_key="1002" value="${ywMenu.mu_level!''}"/>
	
		<label for="level" class="label"><em style="color:red;">*</em>是否有子菜单</label>
		<@select valid="NotBlank" placeholder="请选择是否有子菜单" name = "mu_issub_node" dic_key="1003" value="${ywMenu.mu_issub_node!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label">排序</label>
		<input id="mu_sort" name="mu_sort" placeholder="请输入排序" type="text" class="dfinput" value="${ywMenu.mu_sort!''}"/>
	
		<label for="level" class="label">备注</label>
		<input id="mu_remark" name="mu_remark" placeholder="请输入备注" type="text" class="dfinput" value="${ywMenu.mu_remark!''}"/>
	</div>
	<div class="button-content">
	    <input type="button" class="btn btn_submit_text btn-confirm" value="确认" id="btn_ywMenu_submit"/>
		<input type="button" class="btn btn_submit_text btn-cancel" value="取消" id="btn_ywMenu_back"/>
	</div>
</form>	
<@script>
$(function(){
  // 确认
  $("#btn_ywMenu_submit").click(function(){
      // 表单校验
      if (!formValidate("ywMenuForm")) {
		 return false;
	  }
      var formData = $("#ywMenuForm").serializeArray();
      $.post($("#ywMenuForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
                devActAfterShowDialog(data.error_info,function(){
                 location.href = "${base}/system/ywMenu.htm";
                },"suc");
            } else {
                isTimeOutdevShowDialog(data.error_info,data.infos);
            };
        }).error(function(){
            devShowDialog("系统异常");
        });
  })
  
  // 取消
  $("#btn_ywMenu_back").click(function(){
      location.href = "${base}/system/ywMenu.htm";
  })
  
})
        
</@script>
</@screen>