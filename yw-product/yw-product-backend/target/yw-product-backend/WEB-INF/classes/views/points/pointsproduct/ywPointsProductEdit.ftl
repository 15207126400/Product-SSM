<@screen id="ywPointsProductInsert" title="积分商品页面" places=[{"name":"积分商品","href":"${base}/points/ywPointsProduct.htm"},{"name":"添加/修改","href":"#"}]>
<form id="ywPointsProductForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type}"/>
    <input id="points_pro_id" name="points_pro_id" type="hidden" value="${ywPointsProduct.points_pro_id}"/>
	<div class="formtitle">
		<span class="formspan">积分商品</span>
	</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>积分商品名称</label>
					<input id="points_pro_title" name="points_pro_title" type="text" valid="NotBlank" class="dfinput" placeholder="请输入积分商品名称" maxbytelength="100" value="${ywPointsProduct.points_pro_title}"/>
			</div>
			<div class="form-group team_type1">
				<@fileSyncUpload isMust=true title="积分商品图片" imageUrl=ywPointsProduct.points_pro_img/>
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>积分商品类型</label>
					<@select placeholder="请选择积分商品类型" name="points_pro_type" dic_key="1058" value="${ywPointsProduct.points_pro_type!''}"/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>兑换所需积分</label>
					<input id="points_pro_needs" name="points_pro_needs" type="text" valid="NotBlank" class="dfinput" placeholder="请输入兑换所需积分" maxbytelength="100" value="${ywPointsProduct.points_pro_needs}"/>
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>目前为虚拟库存</label>
					<input id="points_pro_stock" name="points_pro_stock" type="text" valid="NotBlank" class="dfinput" placeholder="请输入目前为虚拟库存(只针对商品类型)" maxbytelength="50" value="${ywPointsProduct.points_pro_stock!'0'}"/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>已兑换数量</label>
					<input id="points_pro_sale_num" name="points_pro_sale_num" type="text" valid="NotBlank" class="dfinput" placeholder="请输入已兑换数量" maxbytelength="100" value="${ywPointsProduct.points_pro_sale_num!'0'}"/>
			</div>
			<div id="product" class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>选择商品</label>
					<@select2 placeholder="请选择积分商品" name="product_id" valid="NotBlank" value=ywPointsProduct.product_id datalist=products optionData={"value":"id","option":"title"}/>
			</div>
			<div id="coupon" class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>选择优惠券</label>
					<@select2 placeholder="请选择积分优惠券" name="coupon_id" valid="NotBlank" value=ywPointsProduct.coupon_id datalist=coupons optionData={"value":"id","option":"coupon_name"}/>
			</div>
					
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>首页展示状态</label>
			        <@select valid="NotBlank" placeholder="请选择状态" name="showpage_status" dic_key="1069" value="${ywPointsProduct.showpage_status!'0'}"/>
			        
			        <label for="level" class="label"><em style="color:red;">*</em>状态</label>
			        <@select valid="NotBlank" placeholder="请选择状态" name="status" dic_key="1000" value="${ywPointsProduct.status!'1'}"/>
			</div>
	<div class="button-content">
	    <input type="button" class="btn btn-success btn_text" value="确认" id="btn_ywPointsProduct_submit"/>
		<input type="button" class="btn btn-success btn_text" value="取消" id="btn_ywPointsProduct_back"/>
	</div>
</form>	
<@script>
$(function(){
	if($("#points_pro_type").val() == 2){
		$("#product").hide();
  		$("#coupon").show();
	}else{
		$("#product").show();
  		$("#coupon").hide();
	}
	
	$("#points_pro_type").change(function(){
		if($(this).val() == 1){
			$("#product").show();
  			$("#coupon").hide();
		}else if($(this).val() == 2){
			$("#product").hide();
  			$("#coupon").show();
		}
	})
  

  // 提交确认
  $("#btn_ywPointsProduct_submit").click(function(){
      // 表单校验
      if (!formValidate("ywPointsProductForm")) {
		 return false;
	  }
       var formData = new FormData($("#ywPointsProductForm")[0]);
      //var formData = $("#ywPointsProductForm").serializeArray();
      $.ajax({  
          url: $("#ywPointsProductForm").attr("action") ,  
          type: 'POST',  
          data: formData,  
          async: false,  
          cache: false,  
          contentType: false,  
          processData: false,  
          success: function (data) {  
              var openType=${op_type};
	            var operateType={"0":"添加","1":"修改"};
	            if (data.error_no == 0) {
	                devActAfterShowDialog(data.error_info,function(){
	                 location.href = "${base}/points/ywPointsProduct.htm";
	                },"suc");
	            } else {
	                isTimeOutdevShowDialog(data.error_info,data.infos);
	            };
          },  
          error: function (data) {  
             devShowDialog("系统异常");
          }  
     });
  })
  
  // 取消
  $("#btn_ywPointsProduct_back").click(function(){
      location.href = "${base}/points/ywPointsProduct.htm";
  })
  
})
</@script>
</@screen>