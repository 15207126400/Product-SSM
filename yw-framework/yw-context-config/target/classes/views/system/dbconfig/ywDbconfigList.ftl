<#assign base=request.contextPath />
<@screen id="ywDbconfig" title="系统数据库配置" places=[{"name":"数据库配置","href":"#"}]>
<@ajaxQueryTable id="ywDbconfig_list" auto=true isMultiple=true multipleCondition=["db_id"]
	action="${base}/system/ywDbconfig/getList.json" 
	paginatorUrl="${base}/system/ywDbconfig/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
		{"id":"db_id", "name":"编号"},
		{"id":"db_type", "name":"类型","dic_key":"1001"},
		{"id":"db_protocol", "name":"数据库主机/ip"},
		{"id":"db_port", "name":"数据库端口号"},
		{"id":"db_username", "name":"登录名称"},
		{"id":"db_password", "name":"登录密码"},
		{"id":"db_name", "name":"数据库名称"},
		{"id":"db_function_type", "name":"作用类型","dic_key":"1062"},
		{"id":"db_sqlimport_versiontype", "name":"导入版本","dic_key":"1079"},
		{"id":"db_function_remark", "name":"作用类型备注"},
		{"id":"user_id", "name":"用户编号"},
		{"id":"wx_appid", "name":"小程序编号"},
		{"id":"db_status", "name":"状态","dic_key":"1000"},
		{"id":"caozuo", "name":"操作", "provider":"dealStatus"}
	]>
	
	<#-- 查询条件 -->
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">数据库编号：</span>
    			<label class="w-170">
      			<input id="db_id" name="db_id" type="number" class="yw-input" value="" placeholder="请输入数据库编号"/ >
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<#-- 其他 -->
	<@innerOfQueryTable>
	<div class="right-btn-box">
	    <span class="input-group-btn">
			<a href="${base}/system/ywDbconfig/edit.htm?op_type=1">
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
        html.push("<a href='${base}/system/ywDbconfig/edit.htm?db_id="+ row["db_id"] +"&op_type=2' class='tablelink'>修改</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["db_id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(db_id){
 	    devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/system/ywDbconfig/delete.json",
 		     data:{"db_id":db_id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/system/ywDbconfig.htm";
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
	   var ywDbconfigForm = []
	   var db_ids = []
	   ywDbconfigForm = getAllCheckboxs().split(",");
	   for(var i=0;i<ywDbconfigForm.length;i++){
	   		db_ids.push(ywDbconfigForm[i].split("-")[0])
	   }
	   devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
	   $.ajax({
			 type:"post",
			 url:"${base}/system/ywDbconfig/deleteBatch.json",
			 data:{"db_ids":db_ids.toString()},
			 dataType:"json",
			 success:function(data){
				if(data.error_no == "0"){
				   devActAfterShowDialog(data.error_info,function(){
					  location.href = "${base}/system/ywDbconfig.htm";
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