<#assign base=request.contextPath />
<@screen id="ywRole" title="系统用户角色页面" places=[{"name":"用户角色","href":"#"}]>
<@ajaxQueryTable id="ywRole_list" auto=true isMultiple=true multipleCondition=["ro_id"]
	action="${base}/system/ywRole/getList.json" 
	paginatorUrl="${base}/system/ywRole/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-search"}
	headItems=[
		{"id":"ro_id", "name":"角色编号"},
		{"id":"ro_name", "name":"角色名称"},
		{"id":"mu_names", "name":"菜单字符串"},
		{"id":"ro_remark", "name":"角色备注"},
		{"id":"ro_status", "name":"角色状态","dic_key":"1004"},
		{"id":"caozuo", "name":"操作", "provider":"dealStatus"}
	]>
	
	<#-- 查询条件 -->
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">角色编号：</span>
    			<label class="w-170">
      			 <input id="ro_id" name="ro_id" type="text" class="yw-input" value="" placeholder="请输入角色编号"/ >
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<#-- 其他 -->
	<@innerOfQueryTable>
	<div class="right-btn-box">
	    <span class="input-group-btn">
			<a href="${base}/system/ywRole/edit.htm?op_type=1">
				<button class="btn btn-success" type="button"><span class="glyphicon glyphicon-plus"></span>添加</button>
			</a>
		</span>
		<span class="input-group-btn">
			<a href="javascript:void(0)" >
				<button class="btn btn-success" type="button" onclick="operationCondition()"><span class="glyphicon glyphicon-trash"></span>批量删除</button>
			</a>
		</span>
	</div>
    </@innerOfQueryTable>
    
</@ajaxQueryTable>
<@script>
    function dealStatus(row, head){
        var html = [];
        html.push("<a href='${base}/system/ywRole/edit.htm?ro_id="+ row["ro_id"] +"&op_type=2' class='tablelink'>修改</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["ro_id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(ro_id,dic_subkey){
 	    devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/system/ywRole/delete.json",
 		     data:{"ro_id":ro_id,"dic_subkey":dic_subkey},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/system/ywRole.htm";
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
	   var ywRoleForm = []
	   var ro_ids = []
	   ywRoleForm = getAllCheckboxs().split(",");
	   for(var i=0;i<ywRoleForm.length;i++){
	   		ro_ids.push(ywRoleForm[i].split("-")[0])
	   }
	   devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
	   $.ajax({
			 type:"post",
			 url:"${base}/system/ywRole/deleteBatch.json",
			 data:{"ro_ids":ro_ids.toString()},
			 dataType:"json",
			 success:function(data){
				if(data.error_no == "0"){
				   devActAfterShowDialog(data.error_info,function(){
					  location.href = "${base}/system/ywRole.htm";
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