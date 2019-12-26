<#assign base=request.contextPath />
<@screen id="ywDistributionProductInsert" title="分销商品编辑" places=[{"name":"特殊分销商品","href":"${base}/sales/ywDistributionProduct.htm"},{"name":"添加/修改","href":"#"}]>
<form id="ywDistributionProductForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type!''}"/>
	<input id="dis_pro_id" name="dis_pro_id" type="hidden" class="dfinput" <#if op_type == "2">readonly="readonly"</#if> maxlength="18" value="${ywDistributionProduct.dis_pro_id!''}"/>
	<div class="formtitle">
		<span class="formspan">分销商品编辑</span>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>分销商品</label>
		<@select2 placeholder="请选择分销商品" name = "product_id" valid="NotBlank" value=ywDistributionProduct.product_id datalist=products optionData={"value":"id","option":"title"}/>
	
		<label for="level" class="label"><em style="color:red;">*</em>一级佣金比例</label>
		<input id="dis_pro_onescale" name="dis_pro_onescale" valid="NotBlank,Digital" placeholder="请输入一级佣金比例（单位:%）" type="text" class="dfinput" maxlength="2" value="${ywDistributionProduct.dis_pro_onescale!''}"/>
		<i></i>
		<i id="dis_pro_onescale"></i>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>二级佣金比例</label>
		<input id="dis_pro_twoscale" name="dis_pro_twoscale" valid="NotBlank,Digital" placeholder="请输入二级佣金比例（单位:%）" type="text" class="dfinput" maxlength="2" value="${ywDistributionProduct.dis_pro_twoscale!''}"/>
		<i></i>
		<i id="dis_pro_twoscale"></i>
		
		<#-- 同步文件上传 -->
		<@fileSyncUpload  isMust=true title="分销商品logo" imageUrl=ywDistributionProduct.dis_pro_showlogo />
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>发布时间</label>
		<@dateTimePicker valid="NotBlank" placeholder="请输入发布时间" name="dis_pro_createtime" timeValue=ywDistributionProduct.dis_pro_createtime />
	
		<label for="level" class="label"><em style="color:red;">*</em>到期时间</label>
		<@dateTimePicker valid="NotBlank" placeholder="请输入到期时间" name="dis_pro_endtime" timeValue=ywDistributionProduct.dis_pro_endtime />
	</div>
	<div class="form-group">
		<label for="level" class="label">销售总量</label>
		<input id="dis_pro_saleatotal" name="dis_pro_saleatotal" valid="NotBlank,Digital" placeholder="请输入销售总量（单位:件）" type="text" class="dfinput" maxlength="8" value="${ywDistributionProduct.dis_pro_saleatotal!''}"/>
		<i></i>
	
		<label for="level" class="label"><em style="color:red;">*</em>状态</label>
		<@select name = "dis_pro_status" dic_key="1000" valid="NotBlank" placeholder="请选择状态" value="${ywDistributionProduct.dis_pro_status!''}"/>
	</div>
	<div class="button-content">
	    <input type="button" class="btn btn_submit_text btn-confirm" value="确认" id="btn_ywDistributionProduct_submit"/>
		<input type="button" class="btn btn_submit_text btn-cancel" value="取消" id="btn_ywDistributionProduct_back"/>
	</div>
</form>	
<@script>
$(function(){
  // 确认
  $("#btn_ywDistributionProduct_submit").click(function(){
      // 表单校验
      if (!formValidate("ywDistributionProductForm")) {
		 return false;
	  }
      var formData = new FormData($("#ywDistributionProductForm")[0]);
      //var formData = $("#ywDistributionProductForm").serializeArray();
      $.ajax({  
          url: $("#ywDistributionProductForm").attr("action") ,  
          type: 'POST',  
          data: formData,  
          async: false,  
          cache: false,  
          contentType: false,  
          processData: false,  
          success: function (data) {  
            if (data.error_no == 0) {
                devActAfterShowDialog(data.error_info,function(){
                 location.href = "${base}/sales/ywDistributionProduct.htm";
                },"suc");
            } else {
                isTimeOutdevShowDialog(data.error_info,data.infos);
            };
          },  
          error: function (returndata) {  
              devShowDialog("系统异常");
          }  
     });  
  })
  
  // 取消
  $("#btn_ywDistributionProduct_back").click(function(){
      location.href = "${base}/sales/ywDistributionProduct.htm";
  })
  
})
        
</@script>
</@screen>