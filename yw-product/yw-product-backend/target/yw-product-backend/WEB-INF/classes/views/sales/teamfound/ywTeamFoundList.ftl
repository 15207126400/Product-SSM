<#assign base=request.contextPath />
<@screen id="ywTeamFound" title="开团信息页面" places=[{"name":"开团信息","href":"#"}]>
<@ajaxQueryTable id="ywTeamFound_list" auto=true isMultiple=false multipleCondition=["found_id"]
	action="${base}/sales/ywTeamFound/getList.json" 
	paginatorUrl="${base}/sales/ywTeamFound/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
		{"id":"found_id", "name":"编号"},
		{"id":"found_time", "name":"开团时间"},
		{"id":"found_end_time", "name":"成团截止时间"},
		{"id":"nickname", "name":"团长昵称"},
		{"id":"head_pic", "name":"团长头像","type":"img"},
		{"id":"order_id", "name":"团长订单编号"},
		{"id":"found_join", "name":"已参团人数"},
		{"id":"need", "name":"需多少人参团"},
		{"id":"price", "name":"拼团价格(单位:元)"},
		{"id":"product_price", "name":"商品原价(单位:元)"},
		{"id":"status", "name":"状态","dic_key":"1025"}
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
			    <input id="found_id" name="found_id" type="text" class="yw-input" value="" placeholder="请输入编号"/ >
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<#-- 其他 -->
	<@innerOfQueryTable>
	<div class="right-btn-box">
	    <span class="input-group-btn">
			<a href="${base}/sales/ywTeamFound/edit.htm?op_type=1">
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
            html.push("<a href='${base}/sales/ywTeamFound/edit.htm?found_id="+ row["found_id"] +"&op_type=2' class='tablelink'>修改</a>");
            html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["found_id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(found_id){
 	    devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/sales/ywTeamFound/delete.json",
 		     data:{"found_id":found_id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/sales/ywTeamFound.htm";
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
	   var ywTeamFoundForm = []
	   var found_ids = []
	   ywTeamFoundForm = getAllCheckboxs().split(",");
	   for(var i=0;i<ywTeamFoundForm.length;i++){
	   		found_ids.push(ywTeamFoundForm[i].split("-")[0])
	   }
	   devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
	   $.ajax({
			 type:"post",
			 url:"${base}/sales/ywTeamFound/deleteBatch.json",
			 data:{"found_ids":found_ids.toString()},
			 dataType:"json",
			 success:function(data){
				if(data.error_no == "0"){
				   devActAfterShowDialog(data.error_info,function(){
					  location.href = "${base}/sales/ywTeamFound.htm";
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