<@screen id="ywProductImageInsert" title="商品图片页面" places=[{"name":"商品图片","href":"${base}/product/ywProductImage.htm"},{"name":"添加/修改","href":"#"}]>
<form id="ywProductImageForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type}"/>
    <input id="id" name="id" type="hidden" value="${ywProductImage.id!''}"/>
	<div class="formtitle">
		<span class="formspan">商品图片</span>
	</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>商品编号</label>
					<input id="product_id" name="product_id" type="text" valid="NotBlank" class="dfinput" placeholder="请输入商品编号" maxbytelength="50" value="${ywProductImage.product_id!''}"/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>原图编号</label>
					<input id="image_id" name="image_id" type="text" valid="NotBlank" class="dfinput" placeholder="请输入原图编号" maxbytelength="50" value="${ywProductImage.image_id!''}"/>
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>创建时间</label>
					<input id="create_datetime" name="create_datetime" type="text" valid="NotBlank" class="dfinput" placeholder="请输入创建时间" maxbytelength="19" value="${ywProductImage.create_datetime!''}"/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>更新时间</label>
					<input id="update_datetime" name="update_datetime" type="text" valid="NotBlank" class="dfinput" placeholder="请输入更新时间" maxbytelength="19" value="${ywProductImage.update_datetime!''}"/>
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>排序</label>
					<input id="sort" name="sort" type="text" valid="NotBlank" class="dfinput" placeholder="请输入排序" maxbytelength="10" value="${ywProductImage.sort!''}"/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>状态（1：启用，2：禁用）</label>
					<input id="status" name="status" type="text" valid="NotBlank" class="dfinput" placeholder="请输入状态（1：启用，2：禁用）" maxbytelength="4" value="${ywProductImage.status!''}"/>
			</div>
	<div class="button-content">
	    <input type="button" class="btn btn-success btn_text" value="确认" id="btn_ywProductImage_submit"/>
		<input type="button" class="btn btn-success btn_text" value="取消" id="btn_ywProductImage_back"/>
	</div>
</form>	
<@script>
$(function(){
  // 确认
  $("#btn_ywProductImage_submit").click(function(){
      // 表单校验
      if (!formValidate("ywProductImageForm")) {
		 return false;
	  }
      var formData = $("#ywProductImageForm").serializeArray();
      $.post($("#ywProductImageForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
               devActAfterShowDialog(data.error_info,function(){
                 location.href = "${base}/product/ywProductImage.htm";
                },"suc");
            } else if(data.error_no == "-1"){
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }else{
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }
        })
  })
  
  // 取消
  $("#btn_ywProductImage_back").click(function(){
      location.href = "${base}/product/ywProductImage.htm";
  })
  
})
</@script>
</@screen>