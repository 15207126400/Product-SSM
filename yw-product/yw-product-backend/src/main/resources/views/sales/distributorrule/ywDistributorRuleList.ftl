<#assign base=request.contextPath />
<@screen id="ywDistributorRule" title="分销规则设置页面" places=[{"name":"分销规则设置","href":"#"}]>
<@ajaxQueryTable id="ywDistributorRule_list" auto=true isMultiple=false multipleCondition=["rule_id"]
	action="${base}/sales/ywDistributorRule/getList.json" 
	paginatorUrl="${base}/sales/ywDistributorRule/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
		{"id":"rule_id", "name":"编号"},
		{"id":"rule_level_type", "name":"分销等级","dic_key":"1008"},
		{"id":"rule_scale_one", "name":"一级分销比例(单位:%)"},
		{"id":"rule_scale_two", "name":"二级分销比例(单位:%)"},
		{"id":"rule_door_type", "name":"门槛类型","dic_key":"1012"},
		{"id":"rule_door_price", "name":"门槛条件"},
		{"id":"rule_pay_type", "name":"转账类型","dic_key":"1013"},
		{"id":"rule_discounts", "name":"优惠产生佣金","dic_key":"1014"},
		{"id":"caozuo", "name":"操作", "provider":"dealStatus"}
	]>
	
	<#-- 查询条件 -->
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">规则编号：</span>
    			<label class="w-170">
      			 <input id="rule_id" name="rule_id" type="text" class="yw-input" value="" placeholder="请输入规则编号"/ >
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<#-- 其他 
	<@innerOfQueryTable>
	<div class="right-btn-box">
	    <span class="input-group-btn">
			<a href="${base}/sales/ywDistributorRule/edit.htm?op_type=1">
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
    -->
</@ajaxQueryTable>
<@script>
    function dealStatus(row, head){
    	if(row["rule_id"] == "1" || row["rule_id"] == "2"){
	    	var html = [];
	        html.push("<a href='${base}/sales/ywDistributorRule/edit.htm?rule_id="+ row["rule_id"] +"&op_type=2' class='tablelink'>修改</a>");
	        html.push("<a href='javascript:void(0)' style='color:gray' class='tablelink'>删除</a>");
	   		return html.join(" ");
	    }else{
		    var html = [];
	        html.push("<a href='${base}/sales/ywDistributorRule/edit.htm?rule_id="+ row["rule_id"] +"&op_type=2' class='tablelink'>修改</a>");
	        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["rule_id"]+"')\" class='tablelink'>删除</a>");
	   		return html.join(" ");
	    }
        
    }
	
    // 删除
 	function deleteOne(rule_id){
 	    devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/sales/ywDistributorRule/delete.json",
 		     data:{"rule_id":rule_id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/sales/ywDistributorRule.htm";
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
	   var ywDistributorRuleForm = []
	   var rule_ids = []
	   ywDistributorRuleForm = getAllCheckboxs().split(",");
	   for(var i=0;i<ywDistributorRuleForm.length;i++){
	   		rule_ids.push(ywDistributorRuleForm[i].split("-")[0])
	   }
	   devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
	   $.ajax({
			 type:"post",
			 url:"${base}/sales/ywDistributorRule/deleteBatch.json",
			 data:{"rule_ids":rule_ids.toString()},
			 dataType:"json",
			 success:function(data){
				if(data.error_no == "0"){
				   devActAfterShowDialog(data.error_info,function(){
					  location.href = "${base}/sales/ywDistributorRule.htm";
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