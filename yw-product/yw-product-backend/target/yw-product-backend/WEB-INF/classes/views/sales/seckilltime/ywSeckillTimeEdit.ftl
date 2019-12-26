<#assign base=request.contextPath />
<@screen id="ywSeckillTimeInsert" title="秒杀时间" places=[{"name":"秒杀时间配置","href":"${base}/sales/ywSeckillTime.htm"},{"name":"添加/修改","href":"#"}]>
<form id="ywSeckillTimeForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type!''}"/>
	<input id="time_id" name="time_id" type="hidden" class="dfinput" valid="NotBlank" value="${ywSeckillTime.time_id!''}"/>
	<div class="formtitle">
		<span class="formspan">秒杀时间</span>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>开始日期</label>
		<@dateTimePickerByDay valid="NotBlank" placeholder="请选择开始日期" name="seckill_date" timeValue=ywSeckillTime.seckill_date />
		<i id="seckill_date_tip"></i>
	
		<label for="level" class="label"><em style="color:red;">*</em>开始时间点</label>
		<@dateTimePickerByHour valid="NotBlank" placeholder="请选择开始时间点" name="seckill_starttime" timeValue=ywSeckillTime.seckill_starttime />
		<i id="seckill_starttime_tip"></i>
	</div>
	<div class="form-group">
		<@fileSyncUpload isMust=true title="时间主题图片" imageUrl = ywSeckillTime.time_title_url/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>结束时间</label>
		<input id="seckill_endtime" name="seckill_endtime" type="text" class="dfinput" valid="NotBlank,Digital" placeholder="请输入结束时间(输入数字或小数)[单位:小时]" maxbytelength="100" value="${ywSeckillTime.seckill_endtime!''}"/>
		<i id="seckill_endtime_tip"></i>
	
		<label for="level" class="label"><em style="color:red;">*</em>状态</label>
		<@select valid="NotBlank" placeholder="请选择状态" name = "time_status" dic_key="1000" value=ywSeckillTime.time_status!''/>
	</div>
	<div class="button-content">
	    <input type="button" class="btn btn_submit_text btn-confirm" value="确认" id="btn_ywSeckillTime_submit"/>
		<input type="button" class="btn btn_submit_text btn-cancel" value="取消" id="btn_ywSeckillTime_back"/>
	</div>
</form>	
<@script>
$(function(){
  // 确认
  $("#btn_ywSeckillTime_submit").click(function(){
      // 表单校验
      if (!formValidate("ywSeckillTimeForm")) {
		 return false;
	  }
      var formData = new FormData($("#ywSeckillTimeForm")[0]);
      $.ajax({  
          url: $("#ywSeckillTimeForm").attr("action") ,  
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
	                 location.href = "${base}/sales/ywSeckillTime.htm";
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
  $("#btn_ywSeckillTime_back").click(function(){
      location.href = "${base}/sales/ywSeckillTime.htm";
  })
  
})
        
</@script>
</@screen>