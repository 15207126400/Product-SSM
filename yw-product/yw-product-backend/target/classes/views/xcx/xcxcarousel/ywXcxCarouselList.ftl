<@screen id="ywXcxCarousel" title="小程序轮播图页面" places=[{"name":"小程序轮播图","href":"#"}]>
<@ajaxQueryTable id="ywXcxCarousel_list" auto=true isMultiple=true multipleCondition=["id"]
	action="${base}/xcx/ywXcxCarousel/getList.json" 
	paginatorUrl="${base}/xcx/ywXcxCarousel/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
	       {"id":"id", "name":"编号"},
	       {"id":"carousel_type", "name":"轮播图类型","dic_key":"1070"},
	       {"id":"carousel_name", "name":"轮播图名称"},
	       {"id":"carousel_url", "name":"轮播图路径","type":"img"},
	       {"id":"create_datetime", "name":"创建时间"},
	       {"id":"update_datetime", "name":"更新时间"},
	       {"id":"sort", "name":"排序"},
	       {"id":"nav_name", "name":"跳转导航"},
	       {"id":"status", "name":"状态","dic_key":"1000"},
	       {"id":"caozuo", "name":"操作", "provider":"dealStatus"}
		
	]>

	
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">编号：</span>
    			<label class="w-170">
      			 <input id="id" name="id" type="text" class="yw-input" value="" placeholder="请输入编号"/ >
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<@innerOfQueryTable>
	<div class="right-btn-box">
	    <span class="input-group-btn" style="float: left;">
			<a href="${base}/xcx/ywXcxCarousel/edit.htm?op_type=1">
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
        html.push("<a href='${base}/xcx/ywXcxCarousel/edit.htm?id="+ row["id"]+"&op_type=2' class='tablelink'>修改</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(id){
 	    devActAfterConfirmAndClose("您确定要删除吗",function(){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/xcx/ywXcxCarousel/delete.json",
 		     data:{"id":id},
 		     dataType:"json",
 		     success:function(data){
 		        console.info(data);
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/xcx/ywXcxCarousel.htm";
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
	  var ywXcxCarouselForm = []
	   var id = []
	   ywXcxCarouselForm = getAllCheckboxs().split(",");
	   for(var i=0;i<ywXcxCarouselForm.length;i++){
	   		id.push(ywXcxCarouselForm[i].split("-")[0])
	   }
	  devActAfterConfirmAndClose("您确定要批量删除吗",function(){
		   $.ajax({
				 type:"post",
				 url:"${base}/xcx/ywXcxCarousel/deleteBatch.json",
				 data:{"id":id.toString()},
				 dataType:"json",
				 success:function(data){
					if(data.error_no == "0"){
					   devActAfterShowDialog(data.error_info,function(){
						  location.href = "${base}/xcx/ywXcxCarousel.htm";
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