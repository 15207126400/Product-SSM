<#assign base=request.contextPath />
<@screen id="ywNoticeList" title="公告栏信息" places=[{"name":"公告栏信息","href":"#"}]>
    <@ajaxQueryTable id="ywNotice_list" auto=true isMultiple=true multipleCondition=["noc_id"]
	action="${base}/product/ywNotice/getList.json" 
	paginatorUrl="${base}/product/ywNotice/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
		{"id":"noc_id", "name":"编号", "width":"100px"},
		{"id":"scene_type", "name":"应用场景", "dic_key":"1096"},
		{"id":"noc_content", "name":"内容"},
		{"id":"create_name", "name":"创建人"},
		{"id":"create_time", "name":"创建时间"},
		{"id":"update_name", "name":"更新人"},
		{"id":"update_time", "name":"更新时间"},
		{"id":"status", "name":"状态", "dic_key":"1000"},
		{"id":"caozuo", "name":"操作", "provider":"dealStatus"}
	]>
	
	<#-- 查询条件 -->
	<@innerOfQueryCondition>
        <dl class="search-dl">
  			<dd>
  				<span class="dl-title">公告内容：</span>
    			<label class="w-170">
      				<input id="noc_content" name="noc_content" type="text" class="yw-input" value="${noc_content}" placeholder="请输入公告内容进行查找"/ >
    			</label>
  			</dd>
		</dl>	
     </@innerOfQueryCondition>
     
     <#-- 其他 -->
	<@innerOfQueryTable>
	<div class="right-btn-box">
	    <span class="input-group-btn">
			<a href="${base}/product/ywNotice/edit.htm?op_type=1">
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
        html.push("<a href='${base}/product/ywNotice/edit.htm?noc_id="+ row["noc_id"] +"&op_type=2' class='tablelink'>修改</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["noc_id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(noc_id,dic_subkey){
 	    devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/product/ywNotice/delete.json",
 		     data:{"noc_id":noc_id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/product/ywNotice.htm";
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
	   var ywNoticeForm = []
	   var noc_ids = []
	   ywNoticeForm = getAllCheckboxs().split(",");
	   for(var i=0;i<ywNoticeForm.length;i++){
	   		noc_ids.push(ywNoticeForm[i].split("-")[0])
	   }
	   devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
	   $.ajax({
			 type:"post",
			 url:"${base}/product/ywNotice/deleteBatch.json",
			 data:{"noc_ids":noc_ids.toString()},
			 dataType:"json",
			 success:function(data){
				if(data.error_no == "0"){
				   devActAfterShowDialog(data.error_info,function(){
					  location.href = "${base}/product/ywNotice.htm";
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