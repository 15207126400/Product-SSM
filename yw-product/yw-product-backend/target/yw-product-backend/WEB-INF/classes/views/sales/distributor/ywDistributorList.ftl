<#assign base=request.contextPath />
<@screen id="ywDistributor" title="分销商信息页面" places=[{"name":"分销商信息","href":"#"}]>
<@ajaxQueryTable id="ywDistributor_list" auto=true isMultiple=true multipleCondition=["dis_id"]
	action="${base}/sales/ywDistributor/getList.json" 
	paginatorUrl="${base}/sales/ywDistributor/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
		{"id":"dis_id", "name":"编号"},
		{"id":"nickname", "name":"昵称"},
		{"id":"dis_name", "name":"真实姓名"},
		{"id":"dis_type", "name":"类型","dic_key":"1006"},
		{"id":"dis_sex", "name":"性别","dic_key":"1011"},
		{"id":"pid_nickname", "name":"推荐人昵称"},
		{"id":"dis_parentid", "name":"推荐码"},
		{"id":"dis_level", "name":"等级","dic_key":"1008"},
		{"id":"dis_apply_time", "name":"申请时间"},
		{"id":"dis_apply_reason", "name":"申请原因"},
		{"id":"dis_status", "name":"状态","dic_key":"1007"},
		{"id":"caozuo", "name":"操作", "provider":"dealStatus"}
	]>
	
	<#-- 查询条件 -->
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">分销商编号：</span>
    			<label class="w-170">
      			<input id="dis_id" name="dis_id" type="text" class="yw-input" value="" placeholder="请输入分销商编号"/ >
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<#-- 其他 -->
	<@innerOfQueryTable>
	<div class="right-btn-box">
		<#--
	    <span class="input-group-btn">
			<a href="${base}/sales/ywDistributor/edit.htm?op_type=1">
				<button class="btn btn-success btn_text" type="button">添加</button>
			</a>
		</span>
		-->
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
        if(row["dis_status"] == "1"){
            html.push("<a href='${base}/sales/ywDistributor/edit.htm?dis_id="+ row["dis_id"] +"&op_type=2' class='tablelink'>审核</a>");
        } else {
        	html.push("<text style='color:gray' class='tablelink'>已审核</text>");
            //html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["dis_id"]+"')\" class='tablelink'>删除</a>");
        }
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(dis_id){
 	    devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/sales/ywDistributor/delete.json",
 		     data:{"dis_id":dis_id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/sales/ywDistributor.htm";
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
	   var ywDistributorForm = []
	   var dis_ids = []
	   ywDistributorForm = getAllCheckboxs().split(",");
	   for(var i=0;i<ywDistributorForm.length;i++){
	   		dis_ids.push(ywDistributorForm[i].split("-")[0])
	   }
	   devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
	   $.ajax({
			 type:"post",
			 url:"${base}/sales/ywDistributor/deleteBatch.json",
			 data:{"dis_ids":dis_ids.toString()},
			 dataType:"json",
			 success:function(data){
				if(data.error_no == "0"){
				   devActAfterShowDialog(data.error_info,function(){
					  location.href = "${base}/sales/ywDistributor.htm";
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