<#assign base=request.contextPath />
<@screen id="ywQuartzjob" title="系统定时任务页面" places=[{"name":"定时任务","href":"#"}]>
<@ajaxQueryTable id="ywQuartzjob_list" auto=true isMultiple=true multipleCondition=["job_name","description"]
	action="${base}/system/ywQuartzjob/getList.json" 
	paginatorUrl="${base}/system/ywQuartzjob/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
		{"id":"job_name", "name":"任务名称"},
		{"id":"job_group", "name":"任务分组"},
		{"id":"description", "name":"详细描述"},
		{"id":"job_class", "name":"任务事件"},
		{"id":"trigger_time", "name":"触发时间"},
		{"id":"update_datetime", "name":"更新日期时间"}
		{"id":"valid_flag", "name":"有效标志","dic_key":"1050"}
		{"id":"caozuo", "name":"操作", "provider":"dealStatus"}
	]>
	
	<#-- 查询条件 -->
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">任务名称：</span>
    			<label class="w-170">
    				<@autocomplete size="10" maxHeight="250" noCache="false" inputId="job_name" tableName="yw_system.yw_Quartzjob" column="job_name">
      			 		<input id="job_name" name="job_name" type="text" class="yw-input" value="" placeholder="请输入任务名称"/ >
      			 	</@autocomplete>
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<#-- 其他 -->
	<@innerOfQueryTable>
	<div class="right-btn-box">
	    <span class="input-group-btn" style="float: left;">
			<a href="${base}/system/ywQuartzjob/edit.htm?op_type=1">
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
        html.push("<a href='${base}/system/ywQuartzjob/edit.htm?job_name="+ row["job_name"] +"&description="+ row["description"] +"&op_type=2' class='tablelink'>修改</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["job_name"]+"','"+row["description"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(job_name,description){
 	    devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/system/ywQuartzjob/delete.json",
 		     data:{"job_name":job_name,"description":description},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/system/ywQuartzjob.htm";
	                },"suc");
 		        } else {
 		           isTimeOutdevShowDialog(data.error_info,data.infos);
 		        }
 		     }
 		  })
 	   })
 	}
 	
 	// 批量删除
	function operationCondition(){
	   var ywQuartzjobForm = []
	   var job_name = []
	   var description = []
	   ywQuartzjobForm = getAllCheckboxs().split(",");
	   for(var i=0;i<ywQuartzjobForm.length;i++){
	   		job_name.push(ywQuartzjobForm[i].split("-")[0])
	   		description.push(ywQuartzjobForm[i].split("-")[1])
	   }
	
	   devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
	   $.ajax({
			 type:"post",
			 url:"${base}/system/ywQuartzjob/deleteBatch.json",
			 data:{"job_name":job_name.toString() , "description":description.toString()},
			 dataType:"json",
			 success:function(data){
				if(data.error_no == "0"){
				   devActAfterShowDialog(data.error_info,function(){
					  location.href = "${base}/system/ywQuartzjob.htm";
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