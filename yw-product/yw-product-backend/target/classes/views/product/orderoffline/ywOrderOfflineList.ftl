<@screen id="ywOrderOffline" title="线下订单页面" places=[{"name":"线下订单","href":"#"}]>
<@ajaxQueryTable id="ywOrderOffline_list" auto=true isMultiple=true multipleCondition=["id"]
	action="${base}/product/ywOrderOffline/getList.json" 
	paginatorUrl="${base}/product/ywOrderOffline/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
	       {"id":"id", "name":"主键"},
	       {"id":"order_sn", "name":"订单编号"},
	       {"id":"offline_type", "name":"线下订单类型","dic_key":"1084"},
	       {"id":"branch_name", "name":"机构名称"},
	       {"id":"nickname", "name":"客户"},
	       {"id":"offline_totalprice", "name":"消费金额（单位：元）"},
	       {"id":"order_totalprice", "name":"支付金额（单位：元）"},
	       {"id":"order_discount_price", "name":"优惠金额（单位：元）"},
	       {"id":"order_nodiscount_price", "name":"不参与优惠金额（单位：元）"},
	       {"id":"create_datetime", "name":"创建时间"},
	       {"id":"update_datetime", "name":"更新时间"},
	       {"id":"offline_status", "name":"线下订单状态","dic_key":"1034"},
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
		<dl class="search-dl">
  			<dd>
  				<span class="dl-title">线下订单状态：</span>
    			<label class="w-170">
      			<@select id="offline_status" name="offline_status" class="yw-input" dic_key="1034" value="${offline_status!''}" />
    			</label>
  			</dd>
  		</dl>
	</@innerOfQueryCondition>
	
	<@innerOfQueryTable>
	<div class="right-btn-box">
	  <#-- 
	    <span class="input-group-btn" style="float: left;">
			<a href="${base}/product/ywOrderOffline/edit.htm?op_type=1">
				<button class="btn btn-success btn_text" type="button">添加</button>
			</a>
		</span>-->
		<span class="input-group-btn">
			<a href="javascript:void(0)" >
				<button class="btn btn-success btn_text" type="button" onclick="deleteBatch()"><span class="glyphicon glyphicon-trash"></span>批量删除</button>
			</a>
		</span>
	</div>
   </@innerOfQueryTable>
</@ajaxQueryTable>
<@script>
    function dealStatus(row, head){
        var html = [];
        html.push("<a href='${base}/product/ywOrderOffline/edit.htm?id="+ row["id"]+"&op_type=2' class='tablelink'>修改</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(id){
 	    devActAfterConfirmAndClose("您确定要删除吗",function(){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/product/ywOrderOffline/delete.json",
 		     data:{"id":id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/product/ywOrderOffline.htm";
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
	  var ywOrderOfflineForm = []
	   var id = []
	   ywOrderOfflineForm = getAllCheckboxs().split(",");
	   for(var i=0;i<ywOrderOfflineForm.length;i++){
	   		id.push(ywOrderOfflineForm[i].split("-")[0])
	   }
	  devActAfterConfirmAndClose("您确定要批量删除吗",function(){
		   $.ajax({
				 type:"post",
				 url:"${base}/product/ywOrderOffline/deleteBatch.json",
				 data:{"id":id.toString()},
				 dataType:"json",
				 success:function(data){
					if(data.error_no == "0"){
					   devActAfterShowDialog(data.error_info,function(){
						  location.href = "${base}/product/ywOrderOffline.htm";
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