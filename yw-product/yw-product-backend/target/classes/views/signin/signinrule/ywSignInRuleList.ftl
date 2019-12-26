<@screen id="ywSignInRule" title="签到规则页面" places=[{"name":"签到规则","href":"#"}]>
<@ajaxQueryTable id="ywSignInRule_list" auto=true isMultiple=false multipleCondition=["rule_id"]
	action="${base}/system/ywSignInRule/getList.json" 
	paginatorUrl="${base}/system/ywSignInRule/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
	       {"id":"rule_id", "name":"签到规则编号"},
	       {"id":"rule_continue_days", "name":"连续签到天数"},
	       {"id":"rule_type", "name":"签到规则奖励类型","dic_key":"1055"},
	       {"id":"rule_reward_points", "name":"奖励积分"},
	       {"id":"rule_reward_coupon", "name":"奖励优惠券"},
	       {"id":"rule_status", "name":"状态","dic_key":"1000"},
	       {"id":"caozuo", "name":"操作", "provider":"dealStatus"}
		
	]>

	
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">签到规则编号：</span>
    			<label class="w-170">
      			 <input id="rule_id" name="rule_id" type="text" class="yw-input" value="" placeholder="请输入签到规则编号"/ >
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<@innerOfQueryTable>
	<div class="right-btn-box">
	    <span class="input-group-btn" style="float: left;">
			<a href="${base}/system/ywSignInRule/edit.htm?op_type=1">
				<button class="btn btn-success btn_text" type="button">添加</button>
			</a>
		</span>
		<#--
		<span class="input-group-btn">
			<a href="javascript:void(0)" >
				<button class="btn btn-success btn_text" type="button" onclick="deleteBatch()">批量删除</button>
			</a>
		</span>
		-->
	</div>
   </@innerOfQueryTable>
</@ajaxQueryTable>
<@script>
    function dealStatus(row, head){
        var html = [];
        html.push("<a href='${base}/system/ywSignInRule/edit.htm?rule_id="+ row["rule_id"]+"&op_type=2' class='tablelink'>修改</a>");
        <#--
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["rule_id"]+"')\" class='tablelink'>删除</a>");
   		-->
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(rule_id){
 	    devActAfterConfirmAndClose("您确定要删除吗",function(){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/system/ywSignInRule/delete.json",
 		     data:{"rule_id":rule_id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/system/ywSignInRule.htm";
	                },"suc");
 		        } else {
 		           isTimeOutdevShowDialog(data.error_info,data.infos);
 		        }
 		     }
 		  })
 	   });
 	}
 	
 	// 批量删除
	function deleteBatch(){
	  var ywSignInRuleForm = []
	   var rule_id = []
	   ywSignInRuleForm = getAllCheckboxs().split(",");
	   for(var i=0;i<ywSignInRuleForm.length;i++){
	   		rule_id.push(ywSignInRuleForm[i].split("-")[0])
	   }
	  devActAfterConfirmAndClose("您确定要批量删除吗",function(){
		   $.ajax({
				 type:"post",
				 url:"${base}/system/ywSignInRule/deleteBatch.json",
				 data:{"rule_id":rule_id.toString()},
				 dataType:"json",
				 success:function(data){
					if(data.error_no == "0"){
					   devActAfterShowDialog(data.error_info,function(){
						  location.href = "${base}/system/ywSignInRule.htm";
						},"suc");
					} else {
					   isTimeOutdevShowDialog(data.error_info,data.infos);
					}
				 }
			  })
		});  
	 }
 	
</@script>
</@screen>