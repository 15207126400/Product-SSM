<#assign base=request.contextPath />
<@screen id="ywTicketList" title="抽奖配置" places=[{"name":"抽奖配置","href":"#"}]>
    <@ajaxQueryTable id="ywTicket_list" auto=true isMultiple=true multipleCondition=["ticket_id"]
	action="${base}/system/ywTicket/getList.json" 
	paginatorUrl="${base}/system/ywTicket/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
		{"id":"ticket_id", "name":"抽奖人员编号"},
		{"id":"ticket_name", "name":"抽奖人员姓名"},
		{"id":"school_name", "name":"抽奖人员学校"},
		{"id":"ticket_flag", "name":"设置是否中奖"},
		{"id":"caozuo", "name":"操作", "provider":"dealStatus"}
	]>
	
	<#-- 查询条件 -->
	<@innerOfQueryCondition>
        <dl class="search-dl">
  			<dd>
  				<span class="dl-title">编号：</span>
    			<label class="w-170">
      				<input id="ticket_id" name="ticket_id" type="text" class="yw-input" value="${ticket_id!''}" placeholder="请输入编号进行查找"/ >
    			</label>
  			</dd>
		</dl>	
     </@innerOfQueryCondition>
     
     <#-- 其他 -->
	<@innerOfQueryTable>
	<div class="right-btn-box">
	    <span class="input-group-btn">
			<a href="${base}/system/ywTicket/edit.htm?op_type=1">
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
        html.push("<a href='${base}/system/ywTicket/edit.htm?ticket_id="+ row["ticket_id"] +"&op_type=2' class='tablelink'>修改</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["ticket_id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(ticket_id,dic_subkey){
 	    devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/system/ywTicket/delete.json",
 		     data:{"ticket_id":ticket_id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/system/ywTicket.htm";
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
	
	   var ywTicketForm = []
	   var ticket_ids = []
	   ywTicketForm = getAllCheckboxs().split(",");
	   for(var i=0;i<ywTicketForm.length;i++){
	   		ticket_ids.push(ywTicketForm[i].split("-")[0])
	   }
	   devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
	   $.ajax({
			 type:"post",
			 url:"${base}/system/ywTicket/deleteBatch.json",
			 data:{"ticket_ids":ticket_ids.toString()},
			 dataType:"json",
			 success:function(data){
				if(data.error_no == "0"){
				   devActAfterShowDialog(data.error_info,function(){
					  location.href = "${base}/system/ywTicket.htm";
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