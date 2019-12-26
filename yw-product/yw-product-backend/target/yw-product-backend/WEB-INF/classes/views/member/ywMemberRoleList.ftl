<#assign base=request.contextPath />
<@screen id="ywMemberRoleRole" title="会员等级管理页面" places=[{"name":"会员等级管理","href":"#"}]>
	
<@ajaxQueryTable id="ywMemberRoleRole_list" auto=true isMultiple=true multipleCondition=["id"]
	action="${base}/member/ywMemberRole/getList.json" 
	paginatorUrl="${base}/member/ywMemberRole/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-search"}
	headItems=[
		{"id":"id", "name":"编号"},
		{"id":"level", "name":"会员级别"},
		{"id":"levelname", "name":"会员名称"},
		{"id":"levelpoints", "name":"所需消费金额(单位:元)"},
		{"id":"discount", "name":"享受折扣(单位:小数)"},
		{"id":"proportion", "name":"积分获取比例(单位:小数)"},
		{"id":"iflevel", "name":"是否会员身份","dic_key":"1021"},
		{"id":"caozuo", "name":"操作", "provider":"dealStatus"}
	]>
	
	<#-- 查询条件 -->
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">会员级别：</span>
    			<label class="w-170">
      			 <input id="levelname" name="levelname" type="text" class="yw-input" value="" placeholder="请输入会员级别"/ >
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<#-- 其他 -->
	<@innerOfQueryTable>
	<div class="right-btn-box">
	    <span class="input-group-btn">
			<a href="${base}/member/ywMemberRole/edit.htm?op_type=1">
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
        html.push("<a href='${base}/member/ywMemberRole/edit.htm?id="+ row["id"] +"&op_type=2' class='tablelink'>修改</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
        
    }
	
    // 删除
 	function deleteOne(id){
 	    devActAfterConfirmAndClose("您确定要删除吗",function(){
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/member/ywMemberRole/delete.json",
 		     data:{"id":id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/member/ywMemberRole.htm";
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
	   var ywMemberRoleForm = []
	   var ids = []
	   ywMemberRoleForm = getAllCheckboxs().split(",");
	   for(var i=0;i<ywMemberRoleForm.length;i++){
	   		ids.push(ywMemberRoleForm[i].split("-")[0])
	   }
	  devActAfterConfirmAndClose("您确定要删除吗",function(){
	   $.ajax({
			 type:"post",
			 url:"${base}/member/ywMemberRole/deleteBatch.json",
			 data:{"ids":ids.toString()},
			 dataType:"json",
			 success:function(data){
				if(data.error_no == "0"){
				   devActAfterShowDialog(data.error_info,function(){
					  location.href = "${base}/member/ywMemberRole.htm";
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