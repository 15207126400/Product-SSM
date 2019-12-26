<#assign base=request.contextPath />
<@screen id="ywSeckillActivityInsert" title="秒杀活动" places=[{"name":"秒杀活动","href":"${base}/sales/ywSeckillActivity.htm"},{"name":"添加/修改","href":"#"}]>
<form id="ywSeckillActivityForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type!''}"/>
    <input id="seckill_id" name="seckill_id" type="hidden" class="dfinput" valid="NotBlank" placeholder="请输入编号" <#if op_type == "2">readonly="readonly"</#if> maxbytelength="10" value="${ywSeckillActivity.seckill_id!''}"/>
	<div class="formtitle">
		<span class="formspan">秒杀活动</span>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>秒杀标题</label>
		<input id="seckill_name" name="seckill_name" type="text" class="dfinput" valid="NotBlank" placeholder="请输入秒杀标题" maxbytelength="60" value="${ywSeckillActivity.seckill_name!''}"/>
		<i id="seckill_name"></i>
		
		<@fileSyncUpload isMust=true title="秒杀活动图片" imageUrl = ywSeckillActivity.seckill_url/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>秒杀商品</label>
		<@select2 placeholder="请选择秒杀商品" name = "product_id" valid="NotBlank" value=ywSeckillActivity.product_id datalist=products optionData={"value":"id","option":"title"}/>
	
	    <label for="level" class="label">秒杀商品规格</label>
		<@select2 placeholder="--所有规格参与秒杀--" name = "sku_id" value=ywSeckillActivity.sku_id!' ' datalist=productSkus optionData={"value":"sku_id","option":"sku_attr"} />
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>秒杀价格</label>
		<input id="seckill_price" name="seckill_price" type="text" class="dfinput" valid="NotBlank,float" placeholder="请输入秒杀价格（单位：元）" maxbytelength="100" value="${ywSeckillActivity.seckill_price!''}"/>
	    <i id="seckill_name_tip"></i>
	    
	    <label for="level" class="label"><em style="color:red;">*</em>开始时间</label>
		<select id="seckill_date" valid="NotBlank" placeholder="请选择开始时间" style="width:150px;height:30px;border:1px solid #A7B5BC">
		    <option value="">请选择开始时间</option>
		   <#if seckillTimes?? && seckillTimes?size gt 0>
		      <#list seckillTimes as seckill_date>
		         <option value="${seckill_date}" <#if seckillTime??><#if seckill_date == seckillTime.seckill_date?string('yyyy-MM-dd')>selected="selected"</#if></#if>>${seckill_date!''}</option>
		      </#list>
		   </#if>
		</select>
		<select id="time_id" name="time_id"  placeholder="请选择开始时间" style="width:150px;height:30px;border:1px solid #A7B5BC;<#if seckillTime??><#else>display:none</#if>">
		    <option value="${seckillTime.time_id!''}">${seckillTime.seckill_starttime!''}</option>
		</select>
	</div>
	<#--
	<div class="form-group">
	    
		<label for="level" class="label"><em style="color:red;">*</em>秒杀库存</label>
		<input id="seckill_stock" name="seckill_stock" type="text" class="dfinput" valid="NotBlank,Digital" placeholder="请输入秒杀库存（输入数字：0,2,3...）" maxbytelength="100" value="${ywSeckillActivity.seckill_stock!''}"/>
	</div> -->
	<div class="form-group" id="seckill_endtime_li" style="<#if seckillTime??><#else>display:none</#if>">
		<label for="level" class="label"><em style="color:red;">*</em>结束时间</label>
		<input id="seckill_endtime" name="seckill_endtime" type="text" class="dfinput" placeholder="请输入结束时间" readonly="readonly" maxbytelength="100" value="${seckillTime.seckill_endtime!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>购买限制数量</label>
		<input id="seckill_limit" name="seckill_limit" type="text" class="dfinput" valid="NotBlank,Digital" placeholder="请输入购买限制数量(输入数字：0,2,3...）" maxbytelength="100" value="${ywSeckillActivity.seckill_limit!''}"/>
	
		<label for="level" class="label">秒杀描述</label>
		<input id="seckill_description" name="seckill_description" type="text" class="dfinput" placeholder="请输入秒杀描述" maxbytelength="100" value="${ywSeckillActivity.seckill_description!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label">已抢购多少件</label>
		<input id="sales_sum" name="sales_sum" type="text" class="dfinput" valid="Digital" placeholder="请输入已抢购多少件" maxbytelength="100" value="${ywSeckillActivity.sales_sum!''}"/>
	
		<label for="level" class="label">已抢购百分比</label>
		<input id="sales_percent" name="sales_percent" type="text" class="dfinput" valid="Digital" placeholder="请输入已抢购百分比（例如：20%）[单位：数字]" maxbytelength="3" value="${ywSeckillActivity.sales_percent!''}"/>
	    <i id="sales_percent_tip"></i>
	</div>
	<div class="form-group">
		<label for="level" class="label">是否置顶</label>
		<@select placeholder="请选择是否置顶" name = "seckill_istop" dic_key="1052" value=ywSeckillActivity.seckill_istop!''/>
	
		<label for="level" class="label">状态</label>
		<@select valid="NotBlank" placeholder="请选择状态" name = "seckill_status" dic_key="1000" value=ywSeckillActivity.seckill_status!''/>
	</div>
	<div class="button-content">
	    <input type="button" class="btn btn_submit_text btn-confirm" value="确认" id="btn_ywSeckillActivity_submit"/>
		<input type="button" class="btn btn_submit_text btn-cancel" value="取消" id="btn_ywSeckillActivity_back"/>
	</div>
</form>	
<@script>
$(function(){
  
  
  // 开始时间日期选择改变触发事件
  $("#seckill_date").change(function(){
     var self = $(this);
     if(self.val().length == 0){
        $("#time_id").hide();
        $("#seckill_endtime_li").hide();
     } else {
        $("#time_id").show();
        $("#seckill_endtime_li").show();
        // 查询日期对应下的时间点
        $.ajax({
			type: "post",
			data: {"seckill_date":self.val(),"time_status":"1"},
			dataType: "json",
			url: "${base}/sales/ywSeckillTime/getSeckillTimeList.json?rnd=" + new Date().getTime(),
			success: function (data) {
			    debugger;
			    var error_no = data.error_no;
			    var resultList = data.resultList;
			   	if(error_no == "0"){
			   	   var count = resultList.length;
			   	   var timeIdArray = [];
			   	   if(resultList != undefined && resultList.length > 0){
			   	      for(var i = 0 ; i < count ; i ++){
			   	         timeIdArray.push("<option value='"+ resultList[i].time_id +"' data-seckill_starttime='"+ resultList[i].seckill_starttime +"' data-seckill_endtime='"+ resultList[i].seckill_endtime +"'>"+ resultList[i].seckill_starttime +"</option>");
			   	      }
			   	      $("#time_id").html(timeIdArray.join(""));
			   	      // 提前触发按钮
			   	      $("#time_id").change();
			   	   }
			   	} else {
			   	   isTimeOutdevShowDialog(data.error_info,data.infos);
			   	}
			}
		});
     }
  })
  
  // 开始时间点改变change事件
  $("#time_id").change(function(){
      debugger;
      var self = $(this);
      // 查询开始日期
      var seckill_date = $("#seckill_date").find("option:selected").val();
      // 查询开始时间点
      var seckill_starttime = $("#time_id").find("option:selected").attr("data-seckill_starttime");
      // 查询结束时间点
      var seckill_endtime = $("#time_id").find("option:selected").attr("data-seckill_endtime");
      var timestamp = new Date(seckill_date + " " +seckill_starttime).getTime() + parseInt(seckill_endtime)*60*60*1000;
      $("#seckill_endtime").val(getSetTimeStr(timestamp));
  })
  
  // 选择商品时查询商品sku
  $("#product_id").change(function(){
      var product_id = $(this).val();
      if(product_id.trim().length > 0){
         $.post("getSeckillActivityProductSkuList.json?rnd=" + new Date().getTime(),{"product_id":product_id},function(data){
            debugger;
		    var error_no = data.error_no;
		    var resultList = data.resultList;
		   	if(error_no == "0"){
		   	   var product_sku_array = [];
		   	   product_sku_array.push("<option value=' '>--所有规格参与秒杀--</option>");
		   	   $.each(resultList,function(index,item){
		   	       product_sku_array.push("<option value='"+ item.sku_id +"'>"+ item.sku_attr +"</option>");
		   	   })
		   	   $("#sku_id").html(product_sku_array.join(" "));
		   	} else {
		   	   isTimeOutdevShowDialog(data.error_info,data.infos);
		   	}  
         },"json")
      }
  })
  
  // 确认
  $("#btn_ywSeckillActivity_submit").click(function(){
      // 表单校验
      if (!formValidate("ywSeckillActivityForm")) {
		 return false;
	  }
      var formData = new FormData($("#ywSeckillActivityForm")[0]);
      $.ajaxPost($("#ywSeckillActivityForm").attr("action"),formData,function(data){
       devActAfterShowDialog(data.error_info,function(){
         location.href = "${base}/sales/ywSeckillActivity.htm";
        },"suc");
     },"file");
  })
  
  // 取消
  $("#btn_ywSeckillActivity_back").click(function(){
      location.href = "${base}/sales/ywSeckillActivity.htm";
  })
  
})
        
</@script>
</@screen>