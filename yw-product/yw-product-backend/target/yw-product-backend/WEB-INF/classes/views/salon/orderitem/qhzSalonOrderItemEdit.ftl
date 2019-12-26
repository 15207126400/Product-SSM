<@screen id="qhzSalonOrderItemInsert" title="沙龙注册订单项信息表页面" places=[{"name":"沙龙注册订单项信息表","href":"${base}/qhz_salon_order_item/qhzSalonOrderItem.htm"},{"name":"添加/修改","href":"#"}]>
<form id="qhzSalonOrderItemForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type}"/>
    <input id="id" name="id" type="hidden" value="${qhzSalonOrderItem.id!''}"/>
	<div class="formtitle">
		<span class="formspan">沙龙注册订单项信息表</span>
	</div>
	<div class="form-group">
		<label for="level" class="label">订单号</label>
		<input id="order_sn" name="order_sn" type="text" class="dfinput" placeholder="请输入订单号" maxbytelength="50" value="${qhzSalonOrderItem.order_sn!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label">课程名称</label>
		<input id="order_name" name="order_name" type="text" class="dfinput" placeholder="请输入课程名称" maxbytelength="50" value="${qhzSalonOrderItem.order_name!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label">课程编号</label>
		<input id="curriculum_id" name="curriculum_id" type="text" class="dfinput" placeholder="请输入课程编号" maxbytelength="32" value="${qhzSalonOrderItem.curriculum_id!''}"/>
	
		<label for="level" class="label">课程单价</label>
		<input id="order_price" name="order_price" type="text" class="dfinput" placeholder="请输入课程单价" maxbytelength="100" value="${qhzSalonOrderItem.order_price!''}"/>
	</div>
	<div class="button-content">
		<#--
	    <input type="button" class="btn btn-success btn_text" value="确认" id="btn_qhzSalonOrderItem_submit"/>
	    -->
		<input type="button" class="btn btn-success btn_text" value="返回" id="btn_qhzSalonOrderItem_back"/>
	</div>
</form>	
<@script>
$(function(){
  // 确认
  $("#btn_qhzSalonOrderItem_submit").click(function(){
      // 表单校验
      if (!formValidate("qhzSalonOrderItemForm")) {
		 return false;
	  }
      var formData = $("#qhzSalonOrderItemForm").serializeArray();
      $.post($("#qhzSalonOrderItemForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
               devActAfterShowDialog(data.error_info,function(){
                 location.href = "${base}/qhz_salon_order_item/qhzSalonOrderItem.htm";
                },"suc");
            } else if(data.error_no == "-1"){
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }else{
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }
        })
  })
  
  // 取消
  $("#btn_qhzSalonOrderItem_back").click(function(){
      location.href = "${base}/qhz_salon_order_item/qhzSalonOrderItem.htm";
  })
  
})
</@script>
</@screen>