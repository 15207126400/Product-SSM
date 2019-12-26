<#assign base=request.contextPath />
<@screen id="ywSeckillRecordInsert" title="秒杀记录" places=[{"name":"秒杀记录","href":"${base}/sales/ywSeckillRecord.htm"},{"name":"添加/修改","href":"#"}]>
	<form id="ywSeckillRecordForm" action="insertOrUpdate.json" method="post" class="form">
	    <input type="hidden" id="op_type" name="op_type" value="${op_type!''}"/>
	    <input id="record_id" name="record_id" type="hidden" class="dfinput" value="${ywSeckillRecord.record_id!''}"/>
		<div class="formtitle">
			<span class="formspan">基本信息</span>
		</div>
		<div class="form-group">
			<label for="level" class="label">活动编号</label>
			<input id="seckill_id" name="seckill_id" type="text" <#if op_type == "2">readonly="readonly"</#if> value="${ywSeckillRecord.seckill_id!''}" class="dfinput" />
		
			<label for="level" class="label">总价</label>
			<input id="record_totalprice" name="record_totalprice" type="text" <#if op_type == "2">readonly="readonly"</#if> value="${ywSeckillRecord.record_totalprice!''}" class="dfinput" />
		</div>
		<div class="form-group">
			<label for="level" class="label">数量</label>
			<input id="seckill_num" name="seckill_num" type="text" <#if op_type == "2">readonly="readonly"</#if> value="${ywSeckillRecord.seckill_num!''}" class="dfinput" />
		
			<label for="level" class="label">秒杀价</label>
			<input id="seckill_price" name="seckill_price" type="text" <#if op_type == "2">readonly="readonly"</#if> value="${ywSeckillRecord.seckill_price!''}" class="dfinput" />
		</div>
		<div class="form-group">
			<label for="level" class="label">商品原价</label>
			<input id="product_price" name="product_price" type="text" <#if op_type == "2">readonly="readonly"</#if> value="${ywSeckillRecord.product_price!''}" class="dfinput" />
		
			<label for="level" class="label">订单编号</label>
			<input id="order_sn" name="order_sn" type="text" <#if op_type == "2">readonly="readonly"</#if> value="${ywSeckillRecord.order_sn!''}" class="dfinput" />
		</div>
		<div class="form-group">
			<label for="level" class="label">用户昵称</label>
			<input id="user_nickname" name="user_nickname" type="text" <#if op_type == "2">readonly="readonly"</#if> value="${ywSeckillRecord.user_nickname!''}" class="dfinput" />
		
			<label for="level" class="label">用户头像</label>
			<image name="user_headurl" style="width:100px;height:100px" src="${ywSeckillRecord.user_headurl }" />
		</div>
		<div class="form-group">
			<label for="level" class="label">秒杀地点</label>
			<input id="user_palce" name="user_palce" type="text" <#if op_type == "2">readonly="readonly"</#if> value="${ywSeckillRecord.user_palce!''}" class="dfinput" />
		
			<label for="level" class="label">创建时间</label>
			<@dateTimePicker name="record_createtime" disabled="true" placeholder="请输入创建时间" timeValue=ywSeckillRecord.record_createtime />
		</div>
		<div class="form-group">
			<label for="level" class="label">更新时间</label>
			<@dateTimePicker name="record_updatetime" disabled="true" placeholder="请输入更新时间" timeValue=ywSeckillRecord.record_updatetime />
		
			<label for="level" class="label">状态</label>
			<@select disabled="true" name="record_status" dic_key="1051" value="${ywSeckillRecord.record_status!''}" />
		</div>
		<div class="button-content">
		    <input type="button" class="btn btn_submit_text btn-confirm" value="返回" id="btn_ywSeckillRecord_back"/>
		</div>
	</form>
<script type="text/javascript">

$(document).ready(function(){
		
	  // 确认
	  $("#btn_ywSeckillRecord_submit").click(function(){
	  	  $("#record_createtime").removeAttr("disabled");	
		  $("#record_updatetime").removeAttr("disabled");	
		  $("#record_status").removeAttr("disabled");	
		  
		  
	      // 表单校验
	      if (!formValidate("ywSeckillRecordForm")) {
			 return false;
		  }
			
		 
			var formData = $("#ywSeckillRecordForm").serializeArray();
	        $.post($("#ywSeckillRecordForm").attr("action"), formData, function(data){
	            var openType=${op_type};
	            var operateType={"0":"添加","1":"修改"};
	            if (data.error_no == 0) {
	                devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/sales/ywSeckillRecord.htm";
	                },"suc");
	            } else {
	                isTimeOutdevShowDialog(data.error_info,data.infos);
	            };
	        }).error(function(){
	           devShowDialog("系统异常");
	        });
	  })
	  
	  // 取消
	  $("#btn_ywSeckillRecord_back").click(function(){
	      location.href = "${base}/sales/ywSeckillRecord.htm";
	  })
})
</script>	
</@screen>