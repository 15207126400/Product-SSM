<@screen id="qhzArticle" title="文章信息模块页面" places=[{"name":"文章信息模块","href":"#"}]>
<@ajaxQueryTable id="qhzArticle_list" auto=true isMultiple=true multipleCondition=["id"]
	action="${base}/qhz_article/qhzArticle/getList.json" 
	paginatorUrl="${base}/qhz_article/qhzArticle/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
	       {"id":"id", "name":"文章编号"},
	       {"id":"type", "name":"类型","dic_key":"1031"},
	       {"id":"scene_type", "name":"应用场景","dic_key":"1096"},
	       {"id":"title", "name":"标题"},
	       {"id":"img", "name":"图片","type":"img"},
	       {"id":"create_name", "name":"发布人"},
	       {"id":"create_time", "name":"发布时间"},
	       {"id":"update_name", "name":"更新人"},
	       {"id":"update_time", "name":"更新时间"},
	       {"id":"read_num", "name":"阅读量"},
	       {"id":"status", "name":"状态","dic_key":"1000"},
	       {"id":"caozuo", "name":"操作", "provider":"dealStatus"}
		
	]>

	
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">文章类型：</span>
    			<label class="w-170">
      			 <@select id="type" name="type" class="yw-input" dic_key="1031" value="${type!''}" />
    			</label>
  			</dd>
  			<dd>
  				<span class="dl-title">应用场景：</span>
    			<label class="w-170">
      			 <@select id="scene_type" name="scene_type" class="yw-input" dic_key="1096" value="${scene_type!''}" />
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<@innerOfQueryTable>
	<div class="right-btn-box">
	    <span class="input-group-btn" style="float: left;">
			<a href="${base}/qhz_article/qhzArticle/edit.htm?op_type=1">
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
        html.push("<a href='${base}/qhz_article/qhzArticle/edit.htm?id="+ row["id"]+"&op_type=2' class='tablelink'>修改</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(id){
 	    devActAfterConfirmAndClose("您确定要删除吗",function(){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/qhz_article/qhzArticle/delete.json",
 		     data:{"id":id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/qhz_article/qhzArticle.htm";
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
	  var qhzArticleForm = []
	   var id = []
	   qhzArticleForm = getAllCheckboxs().split(",");
	   for(var i=0;i<qhzArticleForm.length;i++){
	   		id.push(qhzArticleForm[i].split("-")[0])
	   }
	  devActAfterConfirmAndClose("您确定要批量删除吗",function(){
		   $.ajax({
				 type:"post",
				 url:"${base}/qhz_article/qhzArticle/deleteBatch.json",
				 data:{"id":id.toString()},
				 dataType:"json",
				 success:function(data){
					if(data.error_no == "0"){
					   devActAfterShowDialog(data.error_info,function(){
						  location.href = "${base}/qhz_article/qhzArticle.htm";
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