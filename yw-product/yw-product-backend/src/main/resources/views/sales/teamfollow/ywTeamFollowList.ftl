<#assign base=request.contextPath />
<@screen id="ywTeamFollow" title="参团信息页面" places=[{"name":"参团信息","href":"#"}]>
<@ajaxQueryTable id="ywTeamFollow_list" auto=true isMultiple=false multipleCondition=["follow_id"]
	action="${base}/sales/ywTeamFollow/getList.json" 
	paginatorUrl="${base}/sales/ywTeamFollow/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
		{"id":"follow_id", "name":"编号"},
		{"id":"follow_user_nickname", "name":"参团会员昵称"},
		{"id":"follow_user_head_pic", "name":"会员头像","type":"img"},
		{"id":"follow_time", "name":"参团时间"},
		{"id":"order_id", "name":"订单编号"},
		{"id":"found_id", "name":"开团编号"},
		{"id":"team_id", "name":"拼团活动编号"},
		{"id":"status", "name":"状态","dic_key":"1024"}
		<#--
		{"id":"caozuo", "name":"操作", "provider":"dealStatus"}
		-->
	]>
	
	<#-- 查询条件 -->
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">编号：</span>
    			<label class="w-170">
			    <input id="follow_id" name="follow_id" type="text" class="yw-input" value="" placeholder="请输入编号"/ >
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<#-- 其他 -->
	<@innerOfQueryTable>
	<div class="right-btn-box">
	    <span class="input-group-btn">
			<a href="${base}/sales/ywTeamFollow/edit.htm?op_type=1">
				<button class="btn btn-success btn_text" type="button">添加</button>
			</a>
		</span>
		<#--
		<span class="input-group-btn">
			<a href="javascript:void(0)" >
				<button class="btn btn-success btn_text" type="button" onclick="operationCondition()">批量删除</button>
			</a>
		</span>
		-->
	</div>
    </@innerOfQueryTable>
    
</@ajaxQueryTable>
<@script>
    function dealStatus(row, head){
        var html = [];
            //html.push("<a href='${base}/sales/ywTeamFollow/edit.htm?follow_id="+ row["follow_id"] +"&op_type=2' class='tablelink'>修改</a>");
            html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["follow_id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(follow_id){
 		devActAfterConfirmAndClose("删除该数据会导致拼团活动异常,确认要删除吗?",function(){
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/sales/ywTeamFollow/delete.json",
 		     data:{"follow_id":follow_id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/sales/ywTeamFollow.htm";
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
	   var ywTeamFollowForm = []
	   var follow_ids = []
	   ywTeamFollowForm = getAllCheckboxs().split(",");
	   for(var i=0;i<ywTeamFollowForm.length;i++){
	   		follow_ids.push(ywTeamFollowForm[i].split("-")[0])
	   }
	   devActAfterConfirmAndClose("删除该数据会导致拼团活动异常,确认要删除吗?",function(){
	   $.ajax({
			 type:"post",
			 url:"${base}/sales/ywTeamFollow/deleteBatch.json",
			 data:{"follow_ids":follow_ids.toString()},
			 dataType:"json",
			 success:function(data){
				if(data.error_no == "0"){
				   devActAfterShowDialog(data.error_info,function(){
					  location.href = "${base}/sales/ywTeamFollow.htm";
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