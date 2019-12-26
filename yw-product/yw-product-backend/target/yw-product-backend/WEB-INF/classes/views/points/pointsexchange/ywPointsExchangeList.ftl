<@screen id="ywPointsExchange" title="积分兑换页面" places=[{"name":"积分兑换","href":"#"}]>
<@ajaxQueryTable id="ywPointsExchange_list" auto=true isMultiple=true multipleCondition=["exchange_id"]
	action="${base}/points/ywPointsExchange/getList.json" 
	paginatorUrl="${base}/points/ywPointsExchange/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
	       {"id":"exchange_id", "name":"积分兑换编号"},
	       {"id":"user_id", "name":"用户编号"},
	       {"id":"nickname", "name":"用户昵称"},
	       {"id":"exchange_type", "name":"商品类型","dic_key":"1059"},
	       {"id":"points", "name":"消费积分"},
	       {"id":"price", "name":"消费金额"},
	       {"id":"product_id", "name":" 商品编号"},
	       {"id":"create_datetime", "name":"兑换时间"},
	       {"id":"status", "name":"兑换状态","dic_key":"1060"},
	       {"id":"caozuo", "name":"操作", "provider":"dealStatus"}
		
	]>

	
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">积分兑换编号：</span>
    			<label class="w-170">
      			 <input id="exchange_id" name="exchange_id" type="text" class="yw-input" value="" placeholder="请输入积分兑换编号"/ >
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<@innerOfQueryTable>
	<div class="right-btn-box">
		<#--
	    <span class="input-group-btn" style="float: left;">
			<a href="${base}/points/ywPointsExchange/edit.htm?op_type=1">
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
        html.push("<a href='${base}/points/ywPointsExchange/edit.htm?exchange_id="+ row["exchange_id"]+"&op_type=2' class='tablelink'>查看</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["exchange_id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(exchange_id){
 	    devActAfterConfirmAndClose("您确定要删除吗",function(){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/points/ywPointsExchange/delete.json",
 		     data:{"exchange_id":exchange_id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/points/ywPointsExchange.htm";
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
	  var ywPointsExchangeForm = []
	   var exchange_id = []
	   ywPointsExchangeForm = getAllCheckboxs().split(",");
	   for(var i=0;i<ywPointsExchangeForm.length;i++){
	   		exchange_id.push(ywPointsExchangeForm[i].split("-")[0])
	   }
	  devActAfterConfirmAndClose("您确定要批量删除吗",function(){
		   $.ajax({
				 type:"post",
				 url:"${base}/points/ywPointsExchange/deleteBatch.json",
				 data:{"exchange_id":exchange_id.toString()},
				 dataType:"json",
				 success:function(data){
					if(data.error_no == "0"){
					   devActAfterShowDialog(data.error_info,function(){
						  location.href = "${base}/points/ywPointsExchange.htm";
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