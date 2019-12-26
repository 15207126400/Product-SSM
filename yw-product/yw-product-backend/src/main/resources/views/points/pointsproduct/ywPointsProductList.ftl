<@screen id="ywPointsProduct" title="积分商品页面" places=[{"name":"积分商品","href":"#"}]>
<@ajaxQueryTable id="ywPointsProduct_list" auto=true isMultiple=true multipleCondition=["points_pro_id"]
	action="${base}/points/ywPointsProduct/getList.json" 
	paginatorUrl="${base}/points/ywPointsProduct/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
	       {"id":"points_pro_id", "name":"商品编号"},
	       {"id":"points_pro_title", "name":"商品名称"},
	       {"id":"points_pro_img", "name":"商品图片","type":"img"},
	       {"id":"points_pro_type", "name":"商品类型","dic_key":"1058"},
	       {"id":"points_pro_needs", "name":"兑换所需积分"},
	       {"id":"points_pro_stock", "name":"虚拟库存"},
	       {"id":"points_pro_sale_num", "name":"已兑换数量"},
	       {"id":"product_id", "name":"商品编号"},
	       {"id":"coupon_id", "name":"优惠券编号"},
	       {"id":"points_pro_createtime", "name":"创建时间"},
	       {"id":"points_pro_updatetime", "name":"更新时间"},
	       {"id":"showpage_status", "name":"首页展示状态","dic_key":"1069"},
	       {"id":"status", "name":"状态","dic_key":"1000"},
	       {"id":"caozuo", "name":"操作", "provider":"dealStatus"}
		
	]>

	
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">积分商品编号：</span>
    			<label class="w-170">
      			 <input id="points_pro_id" name="points_pro_id" type="text" class="yw-input" value="" placeholder="请输入积分商品编号"/ >
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<@innerOfQueryTable>
	<div class="right-btn-box">
	    <span class="input-group-btn" style="float: left;">
			<a href="${base}/points/ywPointsProduct/edit.htm?op_type=1">
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
        html.push("<a href='${base}/points/ywPointsProduct/edit.htm?points_pro_id="+ row["points_pro_id"] +"&coupon_id="+ row["coupon_id"] +" &op_type=2' class='tablelink'>修改</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["points_pro_id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(points_pro_id){
 	    devActAfterConfirmAndClose("您确定要删除吗",function(){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/points/ywPointsProduct/delete.json",
 		     data:{"points_pro_id":points_pro_id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/points/ywPointsProduct.htm";
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
	  var ywPointsProductForm = []
	   var points_pro_id = []
	   ywPointsProductForm = getAllCheckboxs().split(",");
	   for(var i=0;i<ywPointsProductForm.length;i++){
	   		points_pro_id.push(ywPointsProductForm[i].split("-")[0])
	   }
	  devActAfterConfirmAndClose("您确定要批量删除吗",function(){
		   $.ajax({
				 type:"post",
				 url:"${base}/points/ywPointsProduct/deleteBatch.json",
				 data:{"points_pro_id":points_pro_id.toString()},
				 dataType:"json",
				 success:function(data){
					if(data.error_no == "0"){
					   devActAfterShowDialog(data.error_info,function(){
						  location.href = "${base}/points/ywPointsProduct.htm";
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