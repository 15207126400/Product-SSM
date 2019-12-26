<@screen id="ywPoints" title="积分中心页面" places=[{"name":"积分中心","href":"#"}]>
<@ajaxQueryTable id="ywPoints_list" auto=true isMultiple=true multipleCondition=["points_id"]
	action="${base}/points/ywPoints/getList.json" 
	paginatorUrl="${base}/points/ywPoints/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
	       {"id":"points_id", "name":"编号"},
	       {"id":"nickname", "name":"用户昵称"},
	       {"id":"points_totals", "name":"积分总数"},
	       {"id":"points_createtime", "name":"积分创建时间"},
	       {"id":"points_updatetime", "name":"积分更新时间"},
	       {"id":"caozuo", "name":"操作", "provider":"dealStatus"}
		
	]>

	
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">编号：</span>
    			<label class="w-170">
      			 <input id="points_id" name="points_id" type="text" class="yw-input" value="" placeholder="请输入积分编号"/ >
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<@innerOfQueryTable>
	<div class="right-btn-box">
		<#--
	    <span class="input-group-btn" style="float: left;">
			<a href="${base}/points/ywPoints/edit.htm?op_type=1">
				<button class="btn btn-success btn_text" type="button">添加</button>
			</a>
		</span>
		-->
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
        html.push("<a href='${base}/points/ywPoints/edit.htm?points_id="+ row["points_id"]+"&op_type=2' class='tablelink'>修改</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["points_id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(points_id){
 	    devActAfterConfirmAndClose("您确定要删除吗",function(){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/points/ywPoints/delete.json",
 		     data:{"points_id":points_id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/points/ywPoints.htm";
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
	  var ywPointsForm = []
	   var points_id = []
	   ywPointsForm = getAllCheckboxs().split(",");
	   for(var i=0;i<ywPointsForm.length;i++){
	   		points_id.push(ywPointsForm[i].split("-")[0])
	   }
	  devActAfterConfirmAndClose("您确定要批量删除吗",function(){
		   $.ajax({
				 type:"post",
				 url:"${base}/points/ywPoints/deleteBatch.json",
				 data:{"points_id":points_id.toString()},
				 dataType:"json",
				 success:function(data){
					if(data.error_no == "0"){
					   devActAfterShowDialog(data.error_info,function(){
						  location.href = "${base}/points/ywPoints.htm";
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