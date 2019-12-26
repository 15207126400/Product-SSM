<#assign base=request.contextPath />
<@screen id="ywMenu" title="系统菜单页面" places=[{"name":"系统菜单","href":"#"}]>
<@ajaxQueryTable id="ywMenu_list" auto=true isMultiple=true multipleCondition=["mu_id"]
	action="${base}/system/ywMenu/getList.json" 
	paginatorUrl="${base}/system/ywMenu/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
		{"id":"mu_id", "name":"菜单编号"},
		{"id":"mu_name", "name":"名称"},
		{"id":"mu_icon", "name":"菜单图片"},
		{"id":"mu_parentid", "name":"父菜单编号"},
		{"id":"mu_url", "name":"菜单url"},
		{"id":"mu_level", "name":"菜单级别","dic_key":"1002"},
		{"id":"mu_issub_node", "name":"是否有子菜单","dic_key":"1003"},
		{"id":"mu_sort", "name":"排序"},
		{"id":"mu_remark", "name":"备注"},
		{"id":"caozuo", "name":"操作", "provider":"dealStatus"}
	]>
	
	<#-- 查询条件 -->
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">父菜单编号：</span>
    			<label class="w-170">
					<@autocomplete size="10" maxHeight="250" noCache="false" inputId="mu_id" tableName="yw_system.yw_menu" column="mu_id">
		  			 	<input id="mu_id" name="mu_id" type="text" class="yw-input" value="" placeholder="请输入父菜单编号"/ >
		  			</@autocomplete>
    			</label>
  			</dd>
		</dl>
		<dl class="search-dl">
  			<dt>菜单名称：</dt>
  			<dd>
    			<label class="w-170">
    			<@autocomplete size="10" maxHeight="250" noCache="false" inputId="mu_name" tableName="yw_system.yw_menu" column="mu_name">
      			 <input id="mu_name" name="mu_name" type="text" class="yw-input" value="" placeholder="请输入菜单名称"/ >
      			 </@autocomplete>
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<#-- 其他 -->
	<@innerOfQueryTable>
	<div class="right-btn-box">
	    <span class="input-group-btn">
			<a href="${base}/system/ywMenu/edit.htm?op_type=1">
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
        html.push("<a href='${base}/system/ywMenu/edit.htm?mu_id="+ row["mu_id"] +"&mu_subid="+ row["mu_subid"] +"&op_type=2' class='tablelink'>修改</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["mu_id"]+"','"+row["mu_subid"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(mu_id,mu_subid){
 	    devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/system/ywMenu/delete.json",
 		     data:{"mu_id":mu_id,"mu_subid":mu_subid},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/system/ywMenu.htm";
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
 	   var ywMenuForm = []
	   var mu_ids = []
	   ywMenuForm = getAllCheckboxs().split(",");
	   for(var i=0;i<ywMenuForm.length;i++){
	   		mu_ids.push(ywMenuForm[i].split("-")[0])
	   }
 	   devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
 	   $.ajax({
 		     type:"post",
 		     url:"${base}/system/ywMenu/deleteBatch.json",
 		     data:{"mu_ids":mu_ids.toString()},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/system/ywMenu.htm";
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