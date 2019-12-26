<#assign base=request.contextPath />
<@screen id="ywOrderPayment" title="系统字典页面" places=[{"name":"支付信息","href":"#"}]>
	
<@ajaxQueryTable id="ywOrderPayment_list" auto=true isMultiple=true multipleCondition=["order_pay_id","order_sn"]
	action="${base}/product/ywOrderPayment/getList.json" 
	paginatorUrl="${base}/product/ywOrderPayment/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
		{"id":"order_pay_id", "name":"编号"},
		{"id":"order_sn", "name":"订单编号"},
		{"id":"order_pay_price", "name":"支付金额(单位:元)"},
		{"id":"order_pay_type", "name":"支付类型","dic_key":"1083"},
		{"id":"order_pay_method", "name":"支付方式","dic_key":"1010"},
		{"id":"nickname", "name":"用户昵称"},
		{"id":"order_pay_createtime", "name":"支付创建时间"},
		{"id":"order_pay_updatetime", "name":"支付更新时间"},
		{"id":"order_pay_code", "name":"支付单号"},
		{"id":"order_pay_status", "name":"状态","dic_key":"1034"},
		{"id":"caozuo", "name":"操作", "provider":"dealStatus"}
	]>
	
	<#-- 查询条件 -->
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">编号：</span>
    			<label class="w-170">
      			<input id="order_pay_id" name="order_pay_id" type="text" class="yw-input" value="" placeholder="请输入编号"/ >
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	
	<@innerOfQueryTable>
	<div class="right-btn-box">
		<#-- 其他 
	    <span class="input-group-btn">
			<a href="${base}/product/ywOrderPayment/edit.htm?op_type=1">
				<button class="btn btn-success btn_text" type="button">添加</button>
			</a>
		</span>
		-->	
		<span class="input-group-btn">
			<a href="javascript:void(0)" >
				<button class="btn btn-success btn_text" type="button" onclick="operationCondition()"><span class="glyphicon glyphicon-trash"></span>批量删除</button>
			</a>
		</span>
	</div>
    </@innerOfQueryTable>
    
    
</@ajaxQueryTable>
<@script>
    function dealStatus(row, head){
        var html = [];
        html.push("<a href='${base}/product/ywOrderPayment/edit.htm?order_pay_id="+ row["order_pay_id"] +"&order_sn="+ row["order_sn"] +"&op_type=2' class='tablelink'>查看</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["order_pay_id"]+"','"+row["order_sn"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(order_pay_id,order_sn){
 	    if(confirm("您确定要删除吗?")){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/product/ywOrderPayment/delete.json",
 		     data:{"order_pay_id":order_pay_id,"order_sn":order_sn},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/product/ywOrderPayment.htm";
	                },"suc");
 		        } else {
 		           isTimeOutdevShowDialog(data.error_info,data.infos);
 		        }
 		     }
 		  })
 	   }
 	}
 	
 	// 批量删除
	function operationCondition(){
		var ywOrderPaymentForm = []
		var order_pay_id = []
		var order_sn = []
		ywOrderPaymentForm = getAllCheckboxs().split(",");
		for(var i=0;i<ywOrderPaymentForm.length;i++){
			order_pay_id.push(ywOrderPaymentForm[i].split("-")[0])
			order_sn.push(ywOrderPaymentForm[i].split("-")[1])
		}
		
		//console.log('order_pay_id:'+order_pay_id + ',order_sn:'+order_sn)
	devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
	   $.ajax({
			 type:"post",
			 url:"${base}/product/ywOrderPayment/deleteBatch.json",
			 data:{"order_pay_id":order_pay_id.toString(),"order_sn":order_sn.toString()},
			 dataType:"json",
			 success:function(data){
				if(data.error_no == "0"){
				   devActAfterShowDialog(data.error_info,function(){
					  location.href = "${base}/product/ywOrderPayment.htm";
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