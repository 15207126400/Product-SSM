<@screen id="qhzSalonOrder" title="沙龙注册订单信息表页面" places=[{"name":"沙龙注册订单信息表","href":"#"}]>
<@ajaxQueryTable id="qhzSalonOrder_list" auto=true isMultiple=true multipleCondition=["id"]
	action="${base}/qhz_salon_order/qhzSalonOrder/getList.json" 
	paginatorUrl="${base}/qhz_salon_order/qhzSalonOrder/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
	       {"id":"id", "name":"编号"},
	       {"id":"order_sn", "name":"订单号"},
	       {"id":"order_type", "name":"订单类型","dic_key":"1093"},
	       {"id":"order_total", "name":"订单总价"},
	       {"id":"order_realtotal", "name":"实际支付金额"},
	       {"id":"order_curriculums", "name":"所报课程信息"},
	       {"id":"create_datetime", "name":"创建时间"},
	       {"id":"update_datetime", "name":"更新时间"},
	       {"id":"order_name", "name":"购买人"},
	       {"id":"order_tel", "name":"购买人电话"},
	       {"id":"order_benefit_name", "name":"受益人"},
	       {"id":"order_benefit_card", "name":"受益人身份证"},
	       {"id":"order_address", "name":"订单创建人联系地址"},
	       {"id":"order_remark", "name":"订单备注"},
	       {"id":"status", "name":"支付状态","dic_key":"1094"},
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
  			<dd>
  				<span class="dl-title">订单类型：</span>
    			<label class="w-170">
      			 <@select id="order_type" name="order_type" class="yw-input" dic_key="1093" value="${order_type!''}" />
    			</label>
  			</dd>
  			<dd>
  				<span class="dl-title">支付状态：</span>
    			<label class="w-170">
      			 <@select id="status" name="status" class="yw-input" dic_key="1094" value="${status!''}" />
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<@innerOfQueryTable>
	<div class="right-btn-box">
		<#--
	    <span class="input-group-btn" style="float: left;">
			<a href="${base}/qhz_salon_order/qhzSalonOrder/edit.htm?op_type=1">
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
        html.push("<a href='${base}/qhz_salon_order/qhzSalonOrder/edit.htm?id="+ row["id"]+"&op_type=2' class='tablelink'>查看</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(id){
 	    devActAfterConfirmAndClose("您确定要删除吗",function(){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/qhz_salon_order/qhzSalonOrder/delete.json",
 		     data:{"id":id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/qhz_salon_order/qhzSalonOrder.htm";
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
	  var qhzSalonOrderForm = []
	   var id = []
	   qhzSalonOrderForm = getAllCheckboxs().split(",");
	   for(var i=0;i<qhzSalonOrderForm.length;i++){
	   		id.push(qhzSalonOrderForm[i].split("-")[0])
	   }
	  devActAfterConfirmAndClose("您确定要批量删除吗",function(){
		   $.ajax({
				 type:"post",
				 url:"${base}/qhz_salon_order/qhzSalonOrder/deleteBatch.json",
				 data:{"id":id.toString()},
				 dataType:"json",
				 success:function(data){
					if(data.error_no == "0"){
					   devActAfterShowDialog(data.error_info,function(){
						  location.href = "${base}/qhz_salon_order/qhzSalonOrder.htm";
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