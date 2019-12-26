<@screen id="qhzSalonOrderItem" title="沙龙注册订单项信息表页面" places=[{"name":"沙龙注册订单项信息表","href":"#"}]>
<@ajaxQueryTable id="qhzSalonOrderItem_list" auto=true isMultiple=true multipleCondition=["id"]
	action="${base}/qhz_salon_order_item/qhzSalonOrderItem/getList.json" 
	paginatorUrl="${base}/qhz_salon_order_item/qhzSalonOrderItem/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
	       {"id":"id", "name":"编号"},
	       {"id":"order_sn", "name":"订单号"},
	       {"id":"order_name", "name":"课程名称"},
	       {"id":"order_price", "name":"课程单价"},
	       {"id":"caozuo", "name":"操作", "provider":"dealStatus"}
		
	]>

	
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">订单编号：</span>
    			<label class="w-170">
      			 <input id="order_sn" name="order_sn" type="text" class="yw-input" value="" placeholder="请输入订单编号"/ >
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<@innerOfQueryTable>
	<div class="right-btn-box">
		<#--
	    <span class="input-group-btn" style="float: left;">
			<a href="${base}/qhz_salon_order_item/qhzSalonOrderItem/edit.htm?op_type=1">
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
        html.push("<a href='${base}/qhz_salon_order_item/qhzSalonOrderItem/edit.htm?id="+ row["id"]+"&op_type=2' class='tablelink'>查看</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(id){
 	    devActAfterConfirmAndClose("您确定要删除吗",function(){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/qhz_salon_order_item/qhzSalonOrderItem/delete.json",
 		     data:{"id":id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/qhz_salon_order_item/qhzSalonOrderItem.htm";
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
	  var qhzSalonOrderItemForm = []
	   var id = []
	   qhzSalonOrderItemForm = getAllCheckboxs().split(",");
	   for(var i=0;i<qhzSalonOrderItemForm.length;i++){
	   		id.push(qhzSalonOrderItemForm[i].split("-")[0])
	   }
	  devActAfterConfirmAndClose("您确定要批量删除吗",function(){
		   $.ajax({
				 type:"post",
				 url:"${base}/qhz_salon_order_item/qhzSalonOrderItem/deleteBatch.json",
				 data:{"id":id.toString()},
				 dataType:"json",
				 success:function(data){
					if(data.error_no == "0"){
					   devActAfterShowDialog(data.error_info,function(){
						  location.href = "${base}/qhz_salon_order_item/qhzSalonOrderItem.htm";
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