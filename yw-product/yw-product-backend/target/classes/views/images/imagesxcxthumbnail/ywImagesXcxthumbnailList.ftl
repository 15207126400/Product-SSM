<@screen id="ywImagesXcxthumbnail" title="小程序缩略图中心页面" places=[{"name":"小程序缩略图中心","href":"#"}]>
<@ajaxQueryTable id="ywImagesXcxthumbnail_list" auto=true isMultiple=true multipleCondition=["id"]
	action="${base}/images/ywImagesXcxthumbnail/getList.json" 
	paginatorUrl="${base}/images/ywImagesXcxthumbnail/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
	       {"id":"id", "name":"主键"},
	       {"id":"thumbnail_name", "name":"缩略图名称"},
	       {"id":"thumbnail_type", "name":"缩略图类型"},
	       {"id":"thumbnail_url", "name":"缩略图路径"},
	       {"id":"image_id", "name":"原图编号"},
	       {"id":"create_datetime", "name":"创建时间"},
	       {"id":"update_datetime", "name":"更新时间"},
	       {"id":"status", "name":"状态（1：启用，2、禁用）"},
	       {"id":"caozuo", "name":"操作", "provider":"dealStatus"}
		
	]>

	
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">主键：</span>
    			<label class="w-170">
      			 <input id="id" name="id" type="text" class="yw-input" value="" placeholder="请输入主键"/ >
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<@innerOfQueryTable>
	<div class="right-btn-box">
	    <span class="input-group-btn" style="float: left;">
			<a href="${base}/images/ywImagesXcxthumbnail/edit.htm?op_type=1">
				<button class="btn btn-success btn_text" type="button">添加</button>
			</a>
		</span>
		<span class="input-group-btn">
			<a href="javascript:void(0)" >
				<button class="btn btn-success btn_text" type="button" onclick="deleteBatch()">批量删除</button>
			</a>
		</span>
	</div>
   </@innerOfQueryTable>
</@ajaxQueryTable>
<@script>
    function dealStatus(row, head){
        var html = [];
        html.push("<a href='${base}/images/ywImagesXcxthumbnail/edit.htm?id="+ row["id"]+"&op_type=2' class='tablelink'>修改</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(id){
 	    devActAfterConfirmAndClose("您确定要删除吗",function(){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/images/ywImagesXcxthumbnail/delete.json",
 		     data:{"id":id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/images/ywImagesXcxthumbnail.htm";
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
	  var ywImagesXcxthumbnailForm = []
	   var id = []
	   ywImagesXcxthumbnailForm = getAllCheckboxs().split(",");
	   for(var i=0;i<ywImagesXcxthumbnailForm.length;i++){
	   		id.push(ywImagesXcxthumbnailForm[i].split("-")[0])
	   }
	  devActAfterConfirmAndClose("您确定要批量删除吗",function(){
		   $.ajax({
				 type:"post",
				 url:"${base}/images/ywImagesXcxthumbnail/deleteBatch.json",
				 data:{"id":id.toString()},
				 dataType:"json",
				 success:function(data){
					if(data.error_no == "0"){
					   devActAfterShowDialog(data.error_info,function(){
						  location.href = "${base}/images/ywImagesXcxthumbnail.htm";
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