<@screen id="ywBaseconfigInsert" title="基本配置信息页面" places=[{"name":"基本配置信息","href":"${base}/system/ywBaseconfig.htm"},{"name":"添加/修改","href":"#"}]>
<form id="ywBaseconfigForm" action="${base}/system/ywBaseconfig/insertOrUpdate.json" method="post" class="form">
	<div class="formtitle">
		<span class="formspan">基本配置信息</span>
	</div>
	<#--
	<input type="button" class="btn btn-success btn_text" value="开始" id="btn_speak"/>
	<input type="button" class="btn btn-success btn_text" value="继续" id="btn_resume"/>
	<input type="button" class="btn btn-success btn_text" value="暂停" id="btn_pause"/>
	<text id="Result">云维时代提醒您,您有新的订单,请注意查收!</text>-->
	
	       <#if ywBaseconfigs?? && ywBaseconfigs?size gt 0>
	          <#list ywBaseconfigs as ywBaseconfig>
				<div class="form-group">
					<label for="level" class="label">${ywBaseconfig.config_name!''}</label>
					<#if ywBaseconfig.config_type == "1">
					   <#-- 输入框 -->
					   <div style="line-height:35px;">
					   		<input id="${ywBaseconfig.config_id!''}" name="${ywBaseconfig.config_id!''}" type="text" class="dfinput" placeholder="请输入配置编号" maxbytelength="255" value="${ywBaseconfig.config_value!ywBaseconfig.config_default_value}"/>
					   		<i>${ywBaseconfig.config_remark!''}</i>
					   </div>
					<#elseif ywBaseconfig.config_type == "2">
					   <#-- 单选框 -->
					   <div style="line-height:35px;">
					   		<@radio name="${ywBaseconfig.config_id!''}" valid="NotBlank" datalist=ywBaseconfig.ywBaseConfigContents optionData={"value":"value","option":"option"} value=ywBaseconfig.config_value/>
					   </div>
					<#elseif ywBaseconfig.config_type == "3">
					   <#-- 复选框-->
					   <div style="line-height:35px;">
					   		<@checkbox name="${ywBaseconfig.config_id!''}" valid="NotBlank" datalist=ywBaseconfig.ywBaseConfigContents optionData={"value":"value","option":"option"} value=ywBaseconfig.config_value?split(",")/>
					   </div>
					<#elseif ywBaseconfig.config_type == "4">
					   <#-- 下拉框-->
					   <div style="line-height:35px;">
					   		<select name="${ywBaseconfig.config_id!''}" class="dfinput yw-input">
								<#list ywBaseconfig.ywBaseConfigContents as item>
									<option value="${item.value}" <#if item.value==ywBaseconfig.config_value>selected="selected"</#if>>${item.option}</option>
		          				</#list>
				    		</select>
				    		<i>${ywBaseconfig.config_remark!''}</i>
					   </div>
					</#if>
				</div>
			  </#list>	
			</#if>
	<div class="button-content">
	    <input type="button" class="btn btn-success btn_text" value="保存" id="btn_ywBaseconfig_submit"/>
	</div>
</form>	
<@script>
$(function(){
  (function($){
    // 将表单对象装换为json数组对象(Array)
    $.fn.serializeJsonArray=function(){
        var serializeJsonArray=[];
        var serializeJson = $(this).serializeJson();
        // 遍历json对象
        $.each(serializeJson,function(key,val){
           var serializeObj = {};
           serializeObj[key] = serializeJson[key];
           serializeJsonArray.push(serializeObj);
        })
        return serializeJsonArray;
    };
    
    // 将表单数据对象转换为json对象
    $.fn.serializeJson=function(){
        var array=this.serializeArray();
        var str=this.serialize();
        var serializeObj={};
        $(array).each(function(){
            if(serializeObj[this.name]){
                if($.isArray(serializeObj[this.name])){
                    serializeObj[this.name].push(this.value);
                }else{
                    serializeObj[this.name]=[serializeObj[this.name],this.value];
                }
            }else{
                serializeObj[this.name]=this.value; 
            }
        });
        return serializeObj;
    };
})(jQuery);
  // 确认
  $("#btn_ywBaseconfig_submit").click(function(){
      // 表单校验
      if (!formValidate("ywBaseconfigForm")) {
		 return false;
	  }
      var formData = $("#ywBaseconfigForm").serializeJsonArray();
      // 遍历json数组对象转换成需要的json对象数组
      var formDataArray = []
      $.each(formData,function(i,item){
          var jsonObject = {};
          $.each(item,function(key,val){
             jsonObject.config_id = key;
             if($.isArray(item[key])){
               jsonObject.config_value = item[key].toString();
             } else{
               jsonObject.config_value = item[key]; 
             }
          })
          formDataArray.push(jsonObject);
      })
      console.info(formDataArray);
      // 传递json字符串参数(修改默认形式,内容类型为json)
      $.ajax({
	        type: "POST",
	        url: $("#ywBaseconfigForm").attr("action"),
	        contentType: "application/json; charset=utf-8",
	        data: JSON.stringify(formDataArray),
	        dataType: "json",
	        success: function (data) {
	            if (data.error_no == 0) {
	               devActAfterShowDialog(data.error_info,function(){
	                 location.href = "${base}/system/ywBaseconfig.htm";
	                },"suc");
	            } else if(data.error_no == "-1"){
	                isTimeOutdevShowDialog(data.error_info,data.infos);
	            }else{
	                isTimeOutdevShowDialog(data.error_info,data.infos);
	            }
	        }
	    });
  })
  
  // 取消
  $("#btn_ywBaseconfig_back").click(function(){
      location.href = "${base}/system/ywBaseconfig.htm";
  })
  
  // 语音播放测试文本
  var Result = $("#Result").text();
  var msg = new SpeechSynthesisUtterance(Result);
  
  // 开始播放
  $("#btn_speak").click(function(){
    msg.pitch = 0.5;			// 音高,范围：0.1-10之间
    msg.rate = 0.7;				// 播放速率,范围：0.1-10之间
    window.speechSynthesis.speak(msg);
  })
  
  // 暂停播放
  $("#btn_pause").click(function(){
    speechSynthesis.pause(msg);
  })
  
  // 继续播放
  $("#btn_resume").click(function(){
    speechSynthesis.resume(msg);
  })
  
})

</@script>
</@screen>