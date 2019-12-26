<#assign base=request.contextPath />
<@screen id="ywOrderInsert" title="物流信息页面" places=[{"name":"订单信息","href":"${base}/product/ywOrder.htm"},{"name":"发货","href":"#"}]>
	<form id="ywOrderForm" action="insertOrUpdate.json" method="post" class="form">
		<input type="hidden" id="op_type" name="op_type" value="${op_type!''}"/>
		<input name="order_sn" type="hidden" class="dfinput" value="${ywOrder.order_sn!'' }"/>
		<input name="order_status" type="hidden" class="dfinput" value="3"/>
		<div class="formtitle">
			<span class="formspan">物流信息</span>
		</div>
		<div class="form-group">
			<label for="level" class="label"><em style="color:red;">*</em>请输入物流单号</label>
			<input id="transport_sn" valid="NotBlank" name="transport_sn" type="text" placeholder="请输入物流单号" class="dfinput" />
		</div>
		<div class="button-content">
		    <input type="button" class="btn btn-success btn_text" value="确认发货" id="btn_ywOrder_submit"/>
		    <input type="button" class="btn btn-success btn_text" value="取消" id="btn_ywOrder_back"/>
		</div>
	</form>
<script type="text/javascript">
    $(function(){
      // 确认
	  $("#btn_ywOrder_submit").click(function(){
		  // 表单校验
	      if (!formValidate("ywOrderForm")) {
			 return false;
		  }
	      var formData = $("#ywOrderForm").serializeArray();
	      $.post($("#ywOrderForm").attr("action"), formData, function(data){
	            var openType=${op_type};
	            var operateType={"0":"添加","1":"修改"};
	            if (data.error_no == 0) {
	                devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/product/ywOrder.htm";
	                },"suc");
	            } else {
	                isTimeOutdevShowDialog(data.error_info,data.infos);
	            };
	        })
	  })
	  
	  // 取消
	  $("#btn_ywOrder_back").click(function(){
	      location.href = "${base}/product/ywOrder.htm";
	  })

	})
</script>	
</@screen>