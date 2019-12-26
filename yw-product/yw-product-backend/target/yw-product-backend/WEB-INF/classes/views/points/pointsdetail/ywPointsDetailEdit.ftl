<@screen id="ywPointsDetailInsert" title="积分明细页面" places=[{"name":"积分明细","href":"${base}/points/ywPointsDetail.htm"},{"name":"添加/修改","href":"#"}]>
<form id="ywPointsDetailForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type}"/>
    <input id="detail_id" name="detail_id" type="hidden" value="${ywPointsDetail.detail_id}"/>
    <input id="user_id" name="user_id" type="hidden" value="${ywPointsDetail.user_id}"/>
	<div class="formtitle">
		<span class="formspan">积分明细</span>
	</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>用户昵称</label>
					<input id="nickname" name="nickname" type="text" valid="NotBlank" disabled="true" class="dfinput" placeholder="请输入用户昵称" value="${nickname}"/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>积分明细类型</label>
					<@select valid="NotBlank" placeholder="请选择积分明细类型" name="detail_type" dic_key="1056" disabled="true" value="${ywPointsDetail.detail_type!''}"/>
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>积分明细状态</label>
			      	<@select valid="NotBlank" placeholder="请选择积分明细状态" name="detail_status" dic_key="1057" disabled="true" value="${ywPointsDetail.detail_status!''}"/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>积分编号</label>
					<input id="points_id" name="points_id" type="text" class="dfinput" disabled="true" placeholder="请输入积分编号" maxbytelength="50" value="${ywPointsDetail.points_id}"/>
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>订单编号</label>
					<input id="order_sn" name="order_sn" type="text" class="dfinput" disabled="true" placeholder="请输入订单编号" maxbytelength="50" value="${ywPointsDetail.order_sn}"/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>变动积分</label>
					<input id="detail_points_change" name="detail_points_change" type="text" valid="NotBlank" class="dfinput" disabled="true" placeholder="请输入变动积分" maxbytelength="50" value="${ywPointsDetail.detail_points_change}"/>
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>积分明细创建时间</label>
			      	<@dateTimePicker valid="NotBlank" placeholder="请选择积分明细创建时间" disabled="true" name="detail_createtime" timeValue=ywPointsDetail.detail_createtime/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>积分明细更新时间</label>
					<@dateTimePicker valid="NotBlank" placeholder="请选择积分明细修改时间" disabled="true" name="detail_updatetime" timeValue=ywPointsDetail.detail_updatetime/>
			</div>
	<div class="button-content">
	<#--
	    <input type="button" class="btn btn-success btn_text" value="确认" id="btn_ywPointsDetail_submit"/>
	  -->  
		<input type="button" class="btn btn-success btn_text" value="返回" id="btn_ywPointsDetail_back"/>
	</div>
</form>	
<@script>
$(function(){
  // 确认
  $("#btn_ywPointsDetail_submit").click(function(){
      // 表单校验
      if (!formValidate("ywPointsDetailForm")) {
		 return false;
	  }
      var formData = $("#ywPointsDetailForm").serializeArray();
      $.post($("#ywPointsDetailForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
               devActAfterShowDialog(data.error_info,function(){
                 location.href = "${base}/points/ywPointsDetail.htm";
                },"suc");
            } else if(data.error_no == "-1"){
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }else{
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }
        })
  })
  
  // 取消
  $("#btn_ywPointsDetail_back").click(function(){
      location.href = "${base}/points/ywPointsDetail.htm";
  })
  
})
</@script>
</@screen>