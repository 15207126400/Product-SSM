<#assign base=request.contextPath />
<@screen id="ywSeckillTime" title="秒杀时间配置页面" places=[{"name":"秒杀时间配置","href":"#"}]>
<@ajaxQueryTable id="ywSeckillTime_list" auto=true isMultiple=true multipleCondition=["time_id"]
	action="${base}/sales/ywSeckillTime/getList.json" 
	paginatorUrl="${base}/sales/ywSeckillTime/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
		{"id":"time_id", "name":"编号"},
		{"id":"seckill_date", "name":"开始日期"},
		{"id":"seckill_starttime", "name":"开始时间点"},
		{"id":"seckill_endtime", "name":"结束时间(单位:小时)"},
		{"id":"time_title_url", "name":"时间主题图片","type":"img"},
		{"id":"time_status", "name":"状态","dic_key":"1000"},
		{"id":"caozuo", "name":"操作", "provider":"dealStatus"}
	]>
	
	<#-- 查询条件 -->
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">编号：</span>
    			<label class="w-170">
    			<@autocomplete size="10" maxHeight="250" noCache="false" inputId="time_id" tableName="yw_system.yw_seckill_time" column="time_id">
      			 <input id="time_id" name="time_id" type="text" class="yw-input" value="" placeholder="请输入编号"/ >
      			 </@autocomplete>
    			</label>
  			</dd>
		</dl>
	    
	</@innerOfQueryCondition>
	
	<#-- 其他 -->
	<@innerOfQueryTable>
	<div class="right-btn-box">
	    <span class="input-group-btn" style="float: left;">
			<a href="${base}/sales/ywSeckillTime/edit.htm?op_type=1">
				<button class="btn btn-success btn_text" type="button">添加</button>
			</a>
		</span>
		<span class="input-group-btn">
			<a href="javascript:void(0)" >
				<button class="btn btn-success btn_text" type="button" onclick="operationCondition()">批量删除</button>
			</a>
		</span>
	</div>
    </@innerOfQueryTable>
    
</@ajaxQueryTable>
<@script>
    function dealStatus(row, head){
        var html = [];
        html.push("<a href='${base}/sales/ywSeckillTime/edit.htm?time_id="+ row["time_id"] +"&op_type=2' class='tablelink'>修改</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["time_id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(time_id){
 	   devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/sales/ywSeckillTime/delete.json",
 		     data:{"time_id":time_id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/sales/ywSeckillTime.htm";
	                },"suc");
 		        } else {
 		           isTimeOutdevShowDialog(data.error_info,data.infos);
 		        }
 		     }
 		  })
 	   });
 	}
 	
 	// 批量删除
	function operationCondition(){
	   var ywSeckillTimeForm = []
	   var time_id = []
	   ywSeckillTimeForm = getAllCheckboxs().split(",");
	   for(var i=0;i<ywSeckillTimeForm.length;i++){
	   		time_id.push(ywSeckillTimeForm[i].split("-")[0])
	   }
	  devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
		   $.ajax({
				 type:"post",
				 url:"${base}/sales/ywSeckillTime/deleteBatch.json",
				 data:{"time_id":time_id.toString()},
				 dataType:"json",
				 success:function(data){
					if(data.error_no == "0"){
					   devActAfterShowDialog(data.error_info,function(){
						  location.href = "${base}/sales/ywSeckillTime.htm";
						},"suc");
					} else {
					   isTimeOutdevShowDialog(data.error_info,data.infos);
					}
				 }
			  })
		   })	  
		 }
 	
</@script>
</@screen>