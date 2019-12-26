<#assign base=request.contextPath />
<@screen id="ywSystemNotice" title="系统公告" places=[{"name":"系统公告","href":"#"}]>
<@ajaxQueryTable id="ywSystemNotice_list" auto=true isMultiple=true multipleCondition=["notice_id"] 
	action="${base}/system/ywSystemNotice/getList.json" 
	paginatorUrl="${base}/system/ywSystemNotice/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
		{"id":"notice_id", "name":"编号"},
		{"id":"notice_type", "name":"公告类型","dic_key":"1047"},
		{"id":"user_id", "name":"通告对象"},
		{"id":"notice_content", "name":"公告内容"},
		{"id":"notice_create_time", "name":"发布时间"},
		{"id":"notice_status", "name":"状态","dic_key":"1000"},
		{"id":"caozuo", "name":"操作", "provider":"dealStatus"}
	]>
	
	<#-- 查询条件 -->
	<@innerOfQueryCondition>
	    <dl class="search-dl">
	    	<dd>
  				<span class="dl-title">公告类型：</span>
    			<label class="w-170">
      				<@select id="notice_type" name="notice_type" class="yw-input" dic_key="1047" value="${notice_type!''}" />
    			</label>
  			</dd>
  			<dd>
  				<span class="dl-title">公告内容：</span>
    			<label class="w-170">
      				<input id="notice_content" name="notice_content" type="text" class="yw-input" value="" placeholder="请输入公告内容"/ >
    			</label>
  			</dd>
  			<dd>
  				<span class="dl-title">公告状态：</span>
    			<label class="w-170">
      				<@select id="notice_status" name="notice_status" class="yw-input" dic_key="1000" value="${notice_status!''}" />
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<#-- 其他 -->
	<@innerOfQueryTable>
	<div class="right-btn-box">
	    <span class="input-group-btn">
			<a href="${base}/system/ywSystemNotice/edit.htm?op_type=1">
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
        html.push("<a href='${base}/system/ywSystemNotice/edit.htm?notice_id="+ row["notice_id"] +"&op_type=2' class='tablelink'>修改</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["notice_id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(notice_id){
 	    devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/system/ywSystemNotice/delete.json",
 		     data:{"notice_id":notice_id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/system/ywSystemNotice.htm";
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
	   var ywNoticeForm = []
	   var notice_ids = []
	   ywNoticeForm = getAllCheckboxs().split(",");
	   for(var i=0;i<ywNoticeForm.length;i++){
	   		notice_ids.push(ywNoticeForm[i].split("-")[0])
	   }
	   devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
	   $.ajax({
			 type:"post",
			 url:"${base}/system/ywSystemNotice/deleteBatch.json",
			 data:{"notice_ids":notice_ids.toString()},
			 dataType:"json",
			 success:function(data){
				if(data.error_no == "0"){
				   devActAfterShowDialog(data.error_info,function(){
					  location.href = "${base}/system/ywSystemNotice.htm";
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