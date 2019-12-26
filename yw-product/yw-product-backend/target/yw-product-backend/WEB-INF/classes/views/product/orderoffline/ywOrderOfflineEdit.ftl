<@screen id="ywOrderOfflineInsert" title="线下订单页面" places=[{"name":"线下订单","href":"${base}/product/ywOrderOffline.htm"},{"name":"添加/修改","href":"#"}]>
<form id="ywOrderOfflineForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type}"/>
    <input id="id" name="id" type="hidden" value="${ywOrderOffline.id!''}"/>
	<div class="formtitle">
		<span class="formspan">线下订单</span>
	</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>订单编号</label>
					<input id="order_sn" name="order_sn" type="text" valid="NotBlank" class="dfinput" placeholder="请输入订单编号" maxbytelength="32" value="${ywOrderOffline.order_sn!''}"/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>线下订单类型</label>
			        <@select valid="NotBlank" name="offline_type" disabled="true" dic_key="1084" value="${ywOrderOffline.offline_type!''}" />
			</div>
			<div class="form-group">
			        <label for="level" class="label"><em style="color:red;">*</em>机构名称</label>
					<input id="branch_name" name="branch_name" type="text" valid="NotBlank" class="dfinput" placeholder="请输入机构名称" maxbytelength="255" value="${ywOrderOffline.branch_name!''}"/>
			
			        <label for="level" class="label"><em style="color:red;">*</em>用户昵称</label>
					<input id="nickname" name="nickname" type="text" valid="NotBlank" class="dfinput" placeholder="请输入用户昵称" maxbytelength="255" value="${ywOrderOffline.nickname!''}"/>
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>消费金额</label>
					<input id="offline_totalprice" name="offline_totalprice" type="text" valid="NotBlank" class="dfinput" placeholder="请输入消费金额" maxbytelength="20" value="${ywOrderOffline.offline_totalprice!''}"/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>支付金额</label>
					<input id="order_totalprice" name="order_totalprice" type="text" valid="NotBlank" class="dfinput" placeholder="请输入支付金额" maxbytelength="20" value="${ywOrderOffline.order_totalprice!''}"/>
			</div>
			<div class="form-group">
			        <label for="level" class="label"><em style="color:red;">*</em>优惠金额</label>
					<input id="order_discount_price" name="order_discount_price" type="text" valid="NotBlank" class="dfinput" placeholder="请输入优惠金额" maxbytelength="20" value="${ywOrderOffline.order_discount_price!''}"/>
					
					<label for="level" class="label"><em style="color:red;">*</em>不参与优惠金额</label>
					<input id="order_nodiscount_price" name="order_nodiscount_price" type="text" valid="NotBlank" class="dfinput" placeholder="请输入不参与优惠金额" maxbytelength="20" value="${ywOrderOffline.order_nodiscount_price!''}"/>
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>创建时间</label>
					<input id="create_datetime" name="create_datetime" type="text" valid="NotBlank" class="dfinput" placeholder="请输入创建时间" maxbytelength="19" value="${ywOrderOffline.create_datetime?string("yyyy-MM-dd HH:mm:ss")!''}"/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>更新时间</label>
					<input id="update_datetime" name="update_datetime" type="text" valid="NotBlank" class="dfinput" placeholder="请输入更新时间" maxbytelength="19" value="<#if ywOrderOffline.update_datetime??>${ywOrderOffline.update_datetime?string("yyyy-MM-dd HH:mm:ss")!''}</#if>"/>
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>线下订单备注</label>
					<input id="offline_remark" name="offline_remark" type="text" valid="NotBlank" class="dfinput" placeholder="请输入线下订单备注" maxbytelength="255" value="${ywOrderOffline.offline_remark!''}"/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>线下订单状态</label>
			        <@select valid="NotBlank" name="offline_status" disabled="true" dic_key="1034" value="${ywOrderOffline.offline_status!''}" />
			</div>
	<div class="button-content">
	    <input type="button" class="btn btn-success btn_text" value="确认" id="btn_ywOrderOffline_submit"/>
		<input type="button" class="btn btn-success btn_text" value="取消" id="btn_ywOrderOffline_back"/>
	</div>
</form>	
<@script>
$(function(){
  // 确认
  $("#btn_ywOrderOffline_submit").click(function(){
      // 表单校验
      if (!formValidate("ywOrderOfflineForm")) {
		 return false;
	  }
      var formData = $("#ywOrderOfflineForm").serializeArray();
      $.post($("#ywOrderOfflineForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
               devActAfterShowDialog(data.error_info,function(){
                 location.href = "${base}/product/ywOrderOffline.htm";
                },"suc");
            } else if(data.error_no == "-1"){
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }else{
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }
        })
  })
  
  // 取消
  $("#btn_ywOrderOffline_back").click(function(){
      location.href = "${base}/product/ywOrderOffline.htm";
  })
  
})
</@script>
</@screen>