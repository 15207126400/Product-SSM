<#assign base=request.contextPath />
<@screen id="ywTeamActivity" title="团购活动页面" places=[{"name":"团购活动","href":"#"}]>
<@ajaxQueryTable id="ywTeamActivity_list" auto=true isMultiple=true multipleCondition=["team_id"]
	action="${base}/sales/ywTeamActivity/getList.json" 
	paginatorUrl="${base}/sales/ywTeamActivity/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
		{"id":"team_id", "name":"编号"},
		{"id":"act_name", "name":"拼团活动标题"},
		{"id":"team_type", "name":"拼团活动类型","dic_key":"1022"},
		{"id":"time_limit", "name":"成团有效期(单位：秒)"},
		{"id":"team_price", "name":"拼团价(单位：元)"},
		{"id":"needer", "name":"成团需要人数"},
		{"id":"product_name", "name":"商品名称"},
		{"id":"status", "name":"状态","dic_key":"1023"},
		{"id":"caozuo", "name":"操作", "provider":"dealStatus"}
	]>
	
	<#-- 查询条件 -->
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">编号：</span>
    			<label class="w-170">
      			<input id="team_id" name="team_id" type="text" class="yw-input" value="" placeholder="请输入编号"/ >
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<#-- 其他 -->
	<@innerOfQueryTable>
	<div class="right-btn-box">
	    <span class="input-group-btn">
			<a href="${base}/sales/ywTeamActivity/edit.htm?op_type=1">
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
            html.push("<a href='${base}/sales/ywTeamActivity/edit.htm?team_id="+ row["team_id"] +"&op_type=2' class='tablelink'>修改</a>");
            html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["team_id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(team_id){
 	    devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/sales/ywTeamActivity/delete.json",
 		     data:{"team_id":team_id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/sales/ywTeamActivity.htm";
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
	   var ywTeamActivityForm = []
	   var team_ids = []
	   ywTeamActivityForm = getAllCheckboxs().split(",");
	   for(var i=0;i<ywTeamActivityForm.length;i++){
	   		team_ids.push(ywTeamActivityForm[i].split("-")[0])
	   }
	   devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
	   $.ajax({
			 type:"post",
			 url:"${base}/sales/ywTeamActivity/deleteBatch.json",
			 data:{"team_ids":team_ids.toString()},
			 dataType:"json",
			 success:function(data){
				if(data.error_no == "0"){
				   devActAfterShowDialog(data.error_info,function(){
					  location.href = "${base}/sales/ywTeamActivity.htm";
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