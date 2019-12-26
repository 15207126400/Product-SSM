<#assign base=request.contextPath />
<@screen id="ywMember" title="会员信息页面" places=[{"name":"会员信息","href":"#"}]>
	
<@ajaxQueryTable id="ywMember_list" auto=true isMultiple=false multipleCondition=["id"]
	action="${base}/member/ywMember/getList.json" 
	paginatorUrl="${base}/member/ywMember/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-search"}
	headItems=[
		{"id":"id", "name":"编号"},
		{"id":"nickname", "name":"微信昵称"},
		{"id":"avatarUrl", "name":"微信头像","type":"img"},
		{"id":"realname", "name":"真实姓名"},
		{"id":"phone", "name":"联系方式"},
		{"id":"address", "name":"地址"},
		{"id":"shopname", "name":"店铺名称"},
		{"id":"levelname", "name":"会员级别"},
		{"id":"discount", "name":"享受折扣(单位:小数)"},
		{"id":"ac_price", "name":"累计消费金额(单位:元)"},
		{"id":"createTime", "name":"最近登录时间"},
		{"id":"is_auditing", "name":"审核状态","dic_key":"1078"},
		{"id":"caozuo", "name":"操作", "provider":"dealStatus"}
	]>
	
	<#-- 查询条件 -->
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">会员编号：</span>
    			<label class="w-170">
      			 <input id="id" name="id" type="text" class="yw-input" value="" placeholder="请输入会员编号查找"/ >
    			</label>
  			</dd>
		</dl>
		<dl class="search-dl">
  			<dd>
  				<span class="dl-title">审核状态：</span>
    			<label class="w-170">
      			<@select id="is_auditing" name="is_auditing" class="yw-input" dic_key="1078" value="${is_auditing!''}" />
    			</label>
  			</dd>
  		</dl>
  		<dl class="search-dl">
  			<dd>
  				<span class="dl-title">会员级别：</span>
    			<label class="w-170">
      			<@select id="level" name="level" class="yw-input" datalist=ywMemberRoles value="${level!''}" optionData={"value":"id","option":"levelname"} />
    			</label>
  			</dd>
  		</dl>
	</@innerOfQueryCondition>
	
	<@innerOfQueryTable>
	<div class="right-btn-box">
		<#-- 其他 
	    <span class="input-group-btn">
			<a href="${base}/member/ywMember/edit.htm?op_type=1">
				<button class="btn btn-success btn_text" type="button">会员权限管理</button>
			</a>
		</span>
	     
	    
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
        if(row["is_auditing"] == "1"){
        	html.push("<a href='${base}/member/ywMember/edit.htm?openid="+ row["openid"] +"&op_type=3' class='tablelink'>审核</a>");
        }else{
        	html.push("<a href='${base}/member/ywMember/edit.htm?openid="+ row["openid"] +"&op_type=2' class='tablelink'>修改</a>");
        	html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["id"]+"')\" class='tablelink'>删除</a>");
        }
        
        
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(id){
 	    devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/member/ywMember/delete.json",
 		     data:{"id":id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/member/ywMember.htm";
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
	   var ywMemberForm = []
	   var ids = []
	   ywMemberForm = getAllCheckboxs().split(",");
	   for(var i=0;i<ywMemberForm.length;i++){
	   		ids.push(ywMemberForm[i].split("-")[0])
	   }
	  devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
		   $.ajax({
				 type:"post",
				 url:"${base}/member/ywMember/deleteBatch.json",
				 data:{"ids":ids.toString()},
				 dataType:"json",
				 success:function(data){
					if(data.error_no == "0"){
					   devActAfterShowDialog(data.error_info,function(){
						  location.href = "${base}/member/ywMember.htm";
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