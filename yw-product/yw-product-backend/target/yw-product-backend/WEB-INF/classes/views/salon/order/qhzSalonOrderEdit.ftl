<@screen id="qhzSalonOrderInsert" title="沙龙注册订单信息表页面" places=[{"name":"沙龙注册订单信息表","href":"${base}/qhz_salon_order/qhzSalonOrder.htm"},{"name":"添加/修改","href":"#"}]>
<form id="qhzSalonOrderForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type}"/>
    <input id="id" name="id" type="hidden" value="${qhzSalonOrder.id!''}"/>
	<div class="formtitle">
		<span class="formspan">沙龙注册订单信息表</span>
	</div>
	<div class="form-group">
        <label for="level" class="label">订单号</label>
		<input id="order_sn" name="order_sn" type="text" class="dfinput" placeholder="请输入订单号" maxbytelength="50" value="${qhzSalonOrder.order_sn!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label">订单类型</label>
		<@select placeholder="请选择课程类型" name="order_type" dic_key="1093" value=qhzSalonOrder.order_type!'1'/>
	</div>
	<div class="form-group">
        <label for="level" class="label">所报课程信息</label>
		<input style="width:50%;" id="order_curriculums" name="order_curriculums" type="text" class="dfinput" placeholder="请输入所报课程信息" maxbytelength="200" value="${qhzSalonOrder.order_curriculums!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label">订单总价</label>
		<input id="order_total" name="order_total" type="text" class="dfinput" placeholder="请输入订单总价" maxbytelength="50" value="${qhzSalonOrder.order_total!''}"/>
		
		<label for="level" class="label">实际支付金额</label>
		<input id="order_realtotal" name="order_realtotal" type="text" class="dfinput" placeholder="请输入实际支付金额" maxbytelength="50" value="${qhzSalonOrder.order_realtotal!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label">订单创建人</label>
		<input id="order_name" name="order_name" type="text" class="dfinput" placeholder="请输入订单创建人" maxbytelength="20" value="${qhzSalonOrder.order_name!''}"/>
      
        <label for="level" class="label">订单创建人联系电话</label>
		<input id="order_tel" name="order_tel" type="text" class="dfinput" placeholder="请输入订单创建人联系电话" maxbytelength="20" value="${qhzSalonOrder.order_tel!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label">订单受益人</label>
		<input id="order_benefit_name" name="order_benefit_name" type="text" class="dfinput" placeholder="请输入订单受益人" maxbytelength="20" value="${qhzSalonOrder.order_benefit_name!''}"/>
      
        <label for="level" class="label">受益人身份证</label>
		<input id="order_benefit_card" name="order_benefit_card" type="text" class="dfinput" placeholder="请输入受益人身份证" maxbytelength="20" value="${qhzSalonOrder.order_benefit_card!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label">订单创建人联系地址</label>
		<input style="width:50%;" id="order_address" name="order_address" type="text" class="dfinput" placeholder="请输入订单创建人联系地址" maxbytelength="100" value="${qhzSalonOrder.order_address!''}"/>
	</div>
	<div class="form-group">
        <label for="level" class="label">支付状态</label>
		<@select placeholder="请选择课程类型" name="order_type" dic_key="1094" value=qhzSalonOrder.status!'1'/>
	</div>
	<div class="form-group">
		<label for="level" class="label">创建时间</label>
		<@dateTimePicker name="create_datetime" disabled="true" timeValue=qhzSalonOrder.create_datetime />
      
        <label for="level" class="label">更新时间</label>
        <@dateTimePicker name="update_datetime" disabled="true" timeValue=qhzSalonOrder.update_datetime />
	</div>
	<div class="form-group">
		<label for="level" class="label">订单备注</label>
		<input style="width:50%;" id="order_remark" name="order_remark" type="text" class="dfinput" placeholder="请输入订单备注" maxbytelength="200" value="${qhzSalonOrder.order_remark!''}"/>
	</div>
	<div class="button-content">
		<#--
	    <input type="button" class="btn btn-success btn_text" value="确认" id="btn_qhzSalonOrder_submit"/>
	    -->
		<input type="button" class="btn btn-success btn_text" value="返回" id="btn_qhzSalonOrder_back"/>
	</div>
</form>	
<@script>
$(function(){
  // 确认
  $("#btn_qhzSalonOrder_submit").click(function(){
      // 表单校验
      if (!formValidate("qhzSalonOrderForm")) {
		 return false;
	  }
      var formData = $("#qhzSalonOrderForm").serializeArray();
      $.post($("#qhzSalonOrderForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
               devActAfterShowDialog(data.error_info,function(){
                 location.href = "${base}/qhz_salon_order/qhzSalonOrder.htm";
                },"suc");
            } else if(data.error_no == "-1"){
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }else{
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }
        })
  })
  
  // 取消
  $("#btn_qhzSalonOrder_back").click(function(){
      location.href = "${base}/qhz_salon_order/qhzSalonOrder.htm";
  })
  
})
</@script>
</@screen>