<#assign base=request.contextPath />
<@screen id="ywDictionaryInsert" title="系统字典增加页面" places=[{"name":"数据字典","href":"${base}/system/ywDictionary.htm"},{"name":"添加/修改","href":"#"}]>
<form id="ywDictionaryForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type!''}"/>
    <div class="formtitle">
		<span class="formspan">数据字典</span>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>字典项</label>
		<input id="dic_key" name="dic_key" type="text" class="dfinput" valid="NotBlank" placeholder="请输入字典项" <#if op_type == "2">readonly="readonly"</#if> maxbytelength="10" value="${ywDictionary.dic_key!''}"/>
	
		<label for="level" class="label"><em style="color:red;">*</em>字典值</label>
		<input id="dic_value" name="dic_value" type="text" class="dfinput" valid="NotBlank" placeholder="请输入字典值"  maxbytelength="100" value="${ywDictionary.dic_value!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>字典子项</label>
		<input id="dic_subkey" name="dic_subkey" type="text" class="dfinput" valid="NotBlank" placeholder="请输入字典子项" maxbytelength="10" value="${ywDictionary.dic_subkey!''}"/>
	
		<label for="level" class="label"><em style="color:red;">*</em>字典子项值</label>
		<input id="dic_subvalue" name="dic_subvalue" type="text" class="dfinput" valid="NotBlank" placeholder="请输入字典子项值" maxbytelength="100" value="${ywDictionary.dic_subvalue!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>状态</label>
		<@select valid="NotBlank" placeholder="请选择字典状态" name = "dic_status" dic_key="1000" value=ywDictionary.dic_status!'1'/>
	
		<label for="level" class="label">备注</label>
		<textarea name="dic_remark" class="textareainput" placeholder="请输入备注" style="" id="dic_remark" cols="100" rows="5">${ywDictionary.dic_remark!''}</textarea> 
	</div>
	<div class="button-content">
	    <input type="button" class="btn btn_submit_text btn-confirm" value="确认" id="btn_ywDictionary_submit"/>
		<input type="button" class="btn btn_submit_text btn-cancel" value="取消" id="btn_ywDictionary_back"/>
	</div>
</form>	
<@script>
$(function(){
  // 确认
  $("#btn_ywDictionary_submit").click(function(){
      // 表单校验
      if (!formValidate("ywDictionaryForm")) {
		 return false;
	  }
      var formData = $("#ywDictionaryForm").serializeArray();
      $.post($("#ywDictionaryForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
               devActAfterShowDialog(data.error_info,function(){
                 location.href = "${base}/system/ywDictionary.htm";
                },"suc");
            } else if(data.error_no == "-1"){
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }else{
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }
        })
  })
  
  // 取消
  $("#btn_ywDictionary_back").click(function(){
      location.href = "${base}/system/ywDictionary.htm";
  })
  
})
        
</@script>
</@screen>