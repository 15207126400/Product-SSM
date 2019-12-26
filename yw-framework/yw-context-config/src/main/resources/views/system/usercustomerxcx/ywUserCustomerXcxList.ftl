<@screen id="ywUserCustomerXcx" title="小程序信息页面" places=[{"name":"小程序信息","href":"#"}]>
<@ajaxQueryTable id="ywUserCustomerXcx_list" auto=true isMultiple=true multipleCondition=["id"]
	action="${base}/system/ywUserCustomerXcx/getList.json" 
	paginatorUrl="${base}/system/ywUserCustomerXcx/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
	       {"id":"id", "name":"主键编号"},
	       {"id":"xcx_order_body", "name":"小程序订单描述"},
	       {"id":"xcx_type", "name":"小程序类型","dic_key":"1063"},
	       {"id":"app_id", "name":"小程序ID"},
	       {"id":"app_secret", "name":"小程序密钥"},
	       {"id":"mch_id", "name":"商户号"},
	       {"id":"xcx_pay_key", "name":"支付秘钥"},
	       {"id":"sign_type", "name":"参数签名类型","dic_key":"1064"},
	       {"id":"param_type", "name":"参数类型","dic_key":"1065"},
	       {"id":"xcx_order_ip", "name":"订单IP地址"},
	       
	       {"id":"cert_path", "name":"证书路径"},
	       {"id":"cert_type", "name":"证书类型","dic_key":"1066"},
	       {"id":"xcx_status", "name":"小程序状态","dic_key":"1000"},
	       {"id":"caozuo", "name":"操作", "provider":"dealStatus"}
		
	]>

	
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">主键编号：</span>
    			<label class="w-170">
      			 <input id="id" name="id" type="text" class="yw-input" value="" placeholder="请输入主键编号"/ >
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<@innerOfQueryTable>
	<div class="right-btn-box">
	    <span class="input-group-btn" style="float: left;">
			<a href="${base}/system/ywUserCustomerXcx/edit.htm?op_type=1">
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
        html.push("<a href='${base}/system/ywUserCustomerXcx/edit.htm?id="+ row["id"]+"&op_type=2' class='tablelink'>修改</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(id){
 	    devActAfterConfirmAndClose("您确定要删除吗",function(){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/system/ywUserCustomerXcx/delete.json",
 		     data:{"id":id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/system/ywUserCustomerXcx.htm";
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
	  var ywUserCustomerXcxForm = []
	   var id = []
	   ywUserCustomerXcxForm = getAllCheckboxs().split(",");
	   for(var i=0;i<ywUserCustomerXcxForm.length;i++){
	   		id.push(ywUserCustomerXcxForm[i].split("-")[0])
	   }
	  devActAfterConfirmAndClose("您确定要批量删除吗",function(){
		   $.ajax({
				 type:"post",
				 url:"${base}/system/ywUserCustomerXcx/deleteBatch.json",
				 data:{"id":id.toString()},
				 dataType:"json",
				 success:function(data){
					if(data.error_no == "0"){
					   devActAfterShowDialog(data.error_info,function(){
						  location.href = "${base}/system/ywUserCustomerXcx.htm";
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