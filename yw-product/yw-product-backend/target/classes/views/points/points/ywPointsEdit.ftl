<@screen id="ywPointsInsert" title="积分中心页面" places=[{"name":"积分中心","href":"${base}/points/ywPoints.htm"},{"name":"添加/修改","href":"#"}]>
<form id="ywPointsForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type}"/>
    <input id="points_id" name="points_id" type="hidden" valid="NotBlank" class="dfinput" placeholder="请输入积分编号" maxbytelength="10" value="${ywPoints.points_id}"/>
    <input id="user_id" name="user_id" type="hidden" valid="NotBlank" class="dfinput" placeholder="请输入用户编号" maxbytelength="10" value="${ywPoints.user_id}"/>
	<div class="formtitle">
		<span class="formspan">积分中心</span>
	</div>
			<div class="form-group">
			        <label for="level" class="label"><em style="color:red;">*</em>用户昵称</label>
					<input id="nickname" name="nickname" type="text" valid="NotBlank" disabled="true" class="dfinput" placeholder="请输入用户昵称" value="${nickname}"/>
					
					<label for="level" class="label"><em style="color:red;">*</em>积分总数</label>
					<input id="points_totals" name="points_totals" type="text" valid="NotBlank" class="dfinput" placeholder="请输入积分总数" maxbytelength="255" value="${ywPoints.points_totals}"/>
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>积分创建时间</label>
			      	<@dateTimePicker valid="NotBlank" placeholder="请选择积分创建时间" disabled="true" name="points_createtime" timeValue=ywPoints.points_createtime/>
			        
			        <label for="level" class="label"><em style="color:red;">*</em>积分更新时间</label>
					<@dateTimePicker valid="NotBlank" placeholder="请选择积分修改时间" disabled="true" name="points_updatetime" timeValue=ywPoints.points_updatetime/>
			</div>
	<div class="button-content">
	    <input type="button" class="btn btn-success btn_text" value="确认" id="btn_ywPoints_submit"/>
		<input type="button" class="btn btn-success btn_text" value="返回" id="btn_ywPoints_back"/>
	</div>
</form>	
<@script>
$(function(){
  // 确认
  $("#btn_ywPoints_submit").click(function(){
      // 表单校验
      if (!formValidate("ywPointsForm")) {
		 return false;
	  }
      var formData = $("#ywPointsForm").serializeArray();
      $.post($("#ywPointsForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
               devActAfterShowDialog(data.error_info,function(){
                 location.href = "${base}/points/ywPoints.htm";
                },"suc");
            } else if(data.error_no == "-1"){
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }else{
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }
        })
  })
  
  // 取消
  $("#btn_ywPoints_back").click(function(){
      location.href = "${base}/points/ywPoints.htm";
  })
  
})
</@script>
</@screen>