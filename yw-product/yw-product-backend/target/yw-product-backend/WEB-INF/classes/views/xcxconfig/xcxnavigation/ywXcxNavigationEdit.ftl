<@screen id="ywXcxNavigationInsert" title="小程序跳转导航配置表页面" places=[{"name":"小程序跳转导航配置表","href":"${base}/xcxconfig/ywXcxNavigation.htm"},{"name":"添加/修改","href":"#"}]>
<form id="ywXcxNavigationForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type}"/>
    <input id="id" name="id" type="hidden" value="${ywXcxNavigation.id!''}"/>
	<div class="formtitle">
		<span class="formspan">小程序跳转导航配置表</span>
	</div>
		<div class="form-group">
			<label for="level" class="label">小程序用户</label>
			<@select class="tplus" name="wx_appid" datalist=ywUserCustomerXcx placeholder="请选择小程序用户" value="${ywXcxNavigation.wx_appid!''}" optionData={"value":"app_id","option":"xcx_order_body"}/>
		</div>
		<div class="form-group">
			<label for="level" class="label"><em style="color:red;">*</em>应用场景</label>
			<@select valid="NotBlank" placeholder="请选择应用场景" name="scene_type" dic_key="1096" value="${ywXcxNavigation.scene_type }" />
		</div>
		<div class="form-group">
			<label for="level" class="label"><em style="color:red;">*</em>导航跳转名称</label>
			<input id="nav_name" name="nav_name" type="text" valid="NotBlank" class="dfinput" placeholder="请输入导航跳转名称" maxbytelength="100" value="${ywXcxNavigation.nav_name!''}"/>
	      
	        <label for="level" class="label"><em style="color:red;">*</em>导航跳转路径</label>
			<input id="nav_path" name="nav_path" type="text" valid="NotBlank" class="dfinput" placeholder="请输入导航跳转路径" maxbytelength="500" value="${ywXcxNavigation.nav_path!''}"/>
		</div>
		<div class="form-group">
			<label for="level" class="label"><em style="color:red;">*</em>状态</label>
			<@select valid="NotBlank" placeholder="请选择状态" name="status" dic_key="1000" value="${ywXcxNavigation.status!'1'}"/>
		</div>
	<div class="button-content">
	    <input type="button" class="btn btn-success btn_text" value="确认" id="btn_ywXcxNavigation_submit"/>
		<input type="button" class="btn btn-success btn_text" value="取消" id="btn_ywXcxNavigation_back"/>
	</div>
</form>	
<@script>
$(function(){
  // 确认
  $("#btn_ywXcxNavigation_submit").click(function(){
      // 表单校验
      if (!formValidate("ywXcxNavigationForm")) {
		 return false;
	  }
      var formData = $("#ywXcxNavigationForm").serializeArray();
      $.post($("#ywXcxNavigationForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
               devActAfterShowDialog(data.error_info,function(){
                 location.href = "${base}/xcxconfig/ywXcxNavigation.htm";
                },"suc");
            } else if(data.error_no == "-1"){
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }else{
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }
        })
  })
  
  // 取消
  $("#btn_ywXcxNavigation_back").click(function(){
      location.href = "${base}/xcxconfig/ywXcxNavigation.htm";
  })
  
})
</@script>
</@screen>