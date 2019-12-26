<#assign base=request.contextPath />
<@screen id="ywProductClassify" title="商品分类管理页面" places=[{"name":"商品分类管理","href":"#"}]>
	
<@ajaxQueryTable id="ywProductClassify_list" auto=true isMultiple=true multipleCondition=["classify_id"]
	action="${base}/product/ywProductClassify/getList.json" 
	paginatorUrl="${base}/product/ywProductClassify/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
		{"id":"classify_id", "name":"分类编号"},
		{"id":"classify_name", "name":"分类名称"},
		{"id":"classify_url", "name":"分类图片","type":"img"},
		{"id":"parent_id", "name":"上级菜单编号"},
		{"id":"classify_level", "name":"分类菜单级别","dic_key":"1080"},
		{"id":"classify_order", "name":"序号"},
		{"id":"classify_status", "name":"分类状态","dic_key":"1000"},
		{"id":"caozuo", "name":"操作", "provider":"dealStatus"}
	]>
	
	<#-- 查询条件 -->
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">分类菜单级别：</span>
    			<label class="w-170">
      			<@select id="classify_level" name="classify_level" class="yw-input" dic_key="1080" value="${classify_level!''}" />
    			</label>
  			</dd>
  		</dl>
	</@innerOfQueryCondition>
	
	<#-- 其他 -->
	<@innerOfQueryTable>
	<div class="right-btn-box">
	    <span class="input-group-btn">
			<a href="${base}/product/ywProductClassify/edit.htm?op_type=1">
				<button class="btn btn-success btn_text" type="button">添加</button>
			</a>
		</span>
		<span class="input-group-btn">
			<a href="${base}/product/ywProduct.htm">
				<button class="btn btn-success btn_text" type="button">商品列表</button>
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
        html.push("<a href='${base}/product/ywProductClassify/edit.htm?classify_id="+ row["classify_id"] +"&op_type=2' class='tablelink'>修改</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["classify_id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(classify_id,dic_subkey){
 	    devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/product/ywProductClassify/delete.json",
 		     data:{"classify_id":classify_id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/product/ywProductClassify.htm";
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
	   var ywProductClassifyForm = []
	   var classify_ids = []
	   ywProductClassifyForm = getAllCheckboxs().split(",");
	   for(var i=0;i<ywProductClassifyForm.length;i++){
	   		classify_ids.push(ywProductClassifyForm[i].split("-")[0])
	   }
	   devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
	   $.ajax({
			 type:"post",
			 url:"${base}/product/ywProductClassify/deleteBatch.json",
			 data:{"classify_ids":classify_ids.toString()},
			 dataType:"json",
			 success:function(data){
				if(data.error_no == "0"){
				   devActAfterShowDialog(data.error_info,function(){
					  location.href = "${base}/product/ywProductClassify.htm";
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