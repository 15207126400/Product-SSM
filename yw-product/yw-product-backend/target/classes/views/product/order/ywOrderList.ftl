<#assign base=request.contextPath />
<#assign ywRoleCache = SpringContext.getBean("ywRoleCache") />
<#assign tableData1 = [
		{"id":"order_id", "name":"编号"},
		{"id":"order_type", "name":"类型","dic_key":"1016"},
		{"id":"order_sn", "name":"订单编号"},
		{"id":"nickname", "name":"购买人"},
		{"id":"order_delivery_method", "name":"配送方式","dic_key":"1076"},
		{"id":"order_delivery_fee", "name":"配送费"},
		{"id":"share_nickname", "name":"分享人"},
		{"id":"order_name", "name":"收货人"},
		{"id":"order_tel", "name":"收货电话"},
		{"id":"order_address", "name":"收货地址"},
		{"id":"order_totalprice", "name":"金额(单位:元)"},
		{"id":"couponid", "name":"优惠券编号"},
		{"id":"transport_sn", "name":"物流编号"},
		{"id":"order_points", "name":"获取积分"},
		{"id":"order_createtime", "name":"创建时间"},
		{"id":"order_updatetime", "name":"更新时间"},
		{"id":"order_paytime", "name":"支付时间"},
		{"id":"order_sendtime", "name":"发货时间"},
		{"id":"order_successtime", "name":"成交时间"},
		{"id":"order_remark", "name":"订单备注"},
		{"id":"order_status", "name":"状态","dic_key":"1017"},
		{"id":"order_import_status", "name":"导入状态","dic_key":"1061"},
		{"id":"caozuo", "name":"操作", "provider":"dealStatus"}
	] />

 <#assign tableData2 = [
		{"id":"order_id", "name":"编号"},
		{"id":"order_type", "name":"类型","dic_key":"1016"},
		{"id":"order_sn", "name":"订单编号"},
		{"id":"nickname", "name":"购买人"},
		{"id":"order_delivery_method", "name":"配送方式","dic_key":"1076"},
		{"id":"order_delivery_fee", "name":"配送费"},
		{"id":"share_nickname", "name":"分享人"},
		{"id":"order_name", "name":"收货人"},
		{"id":"order_tel", "name":"收货电话"},
		{"id":"order_address", "name":"收货地址"},
		{"id":"order_totalprice", "name":"金额(单位:元)"},
		{"id":"couponid", "name":"优惠券编号"},
		{"id":"transport_sn", "name":"物流编号"},
		{"id":"order_points", "name":"获取积分"},
		{"id":"order_createtime", "name":"创建时间"},
		{"id":"order_updatetime", "name":"更新时间"},
		{"id":"order_paytime", "name":"支付时间"},
		{"id":"order_sendtime", "name":"发货时间"},
		{"id":"order_successtime", "name":"成交时间"},
		{"id":"order_remark", "name":"订单备注"},
		{"id":"order_status", "name":"状态","dic_key":"1017"},
		{"id":"caozuo", "name":"操作", "provider":"dealStatus"}
	] />
	<#if ywRoleCache.isHasRole("${ywUser.ro_ids!''}","18")>
	  <#assign tableData = tableData1/>
	<#else>
	  <#assign tableData = tableData2/>
	</#if>
<@screen id="ywOrderList" title="订单列表页面" places=[{"name":"订单信息","href":"#"}]>
    <@ajaxQueryTable id="ywOrder_list" auto=true isMultiple=true multipleCondition=["order_sn"]
	action="${base}/product/ywOrder/getList.json" 
	paginatorUrl="${base}/product/ywOrder/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=tableData>
	
	<#-- 查询条件 -->
	<@innerOfQueryCondition>
        <dl class="search-dl">
  			<dd>
  				<span class="dl-title">订单状态：</span>
    			<label class="w-170">
      			<@select id="order_status" name="order_status" class="yw-input" dic_key="1017" value="${order_status!''}" />
    			</label>
  			</dd>
  		</dl>
  		<dl class="search-dl">
  			<dd>
  				<span class="dl-title">订单号：</span>
    			<label class="w-170">
      			<input id="order_sn" name="order_sn" type="text" class="yw-input" value="${order_sn!''}" placeholder="请输入订单号查找"/>
    			</label>
  			</dd>
  		</dl>
  		<dl class="search-dl">
  			<dd>
  				<span class="dl-title">备注信息：</span>
    			<label class="w-170">
      			<input id="order_remark" name="order_remark" type="text" class="yw-input" value="${order_remark!''}" placeholder="请输入备注信息查找"/>
    			</label>
  			</dd>
  		</dl>
  		<dl class="search-dl">
  			<dd>
  				<#---->
    			<@datetimeCondition />
    			
  			</dd> 
		</dl>	
     </@innerOfQueryCondition>
	
	<#-- 其他 -->
	<@innerOfQueryTable>
	<div class="right-btn-box">
		<span class="input-group-btn">
			<a href="javascript:void(0)" >
				<button class="btn btn-success btn_text" type="button" onclick="operationCondition()"><span class="glyphicon glyphicon-trash"></span>批量删除</button>
			</a>
		</span>
		<span class="input-group-btn">
	    	<#--生成发货单停止使用
	    	<button id="btnSendOutGoods" class="btn btn-success btn_text" type="button">生成发货单</button>
	    	-->
	    	<a href="javascript:void(0)" >
				<button id="btnExport" class="btn btn-success" type="button"><span class="glyphicon glyphicon-export"></span>导出Excel</button>
			</a>
		</span>
	</div>
    </@innerOfQueryTable>
    
</@ajaxQueryTable>
<script type="text/javascript">
    var timeData;
	// 批量导出
    $("#btnExport").click(function(){
	    
		var dialogoptions = {
			width: '450',
			height:'250',
			title: "导出商品数据",
			content: '<div><text id="product_exportExcel_rowCount">是否开始导出数据 ?</text></div>'
					+'<div><text style="margin-top:50px;color:green" class="app-column-center-layout" id="product_exportExcel_msg"></text></div>'
				   ,
			hasBtn: true,
			afterHide:function(){
				location.reload();
			},
			confirm: function (dialog) {
				$(".js-dialog-close").hide();
				$(".btn-confirm").attr("disabled","true");
				$(".btn-deny").attr("disabled","true");
				var formData = $("#ywOrder_list").serializeArray();
	    		location.href = "${base}/product/ywOrder/exportExcel.htm"+formDateConvertUrlParam(formData);
	 		    $("#product_exportExcel_rowCount").text("正在查询数据库...请耐心等候...");
			},
			needDestroy:true
		
		}
		var dialog=new Dialog(null,dialogoptions);
	    
	});	
	
	// 设置按钮恢复可点击
	function changeDisabled(){
		//$(".btn-confirm").removeAttr("disabled");
		$(".btn-deny").text('关闭');
		$(".btn-deny").removeAttr("disabled");
	}
    
    // 间隔获取数据
    function setTimeData(){
    	$.ajax({
 		     type:"post",
 		     url:"${base}/product/ywProduct/getRowCount.json",
 		     data:{},
 		     dataType:"json",
 		     async:true, 
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           rowCount = data.rowCount;
 		           timeMillis = data.timeMillis;
 		           console.info("数据:" + data);
 		           $("#product_exportExcel_rowCount").text("成功导出的数据条数 : " + rowCount + "条,耗时:" + timeMillis + "秒");
 		           if(data.resultCode == 0){
	 		           $("#product_exportExcel_msg").text("导入中...请耐心等候...");
	 		           $("#product_exportExcel_msg").css("color","green");
 		           } else if(data.resultCode == 1){
	 		           $("#product_exportExcel_msg").text("导入完成!");
	 		           $("#product_exportExcel_msg").css("color","green");
	 		           //clearInterval(timeData);
 		           }
 		        } 
 		     }
 		  })
    }

  $(function(){
     $("#btnSendOutGoods").click(function(){
        var formData = $("#ywOrder_list").serializeArray();
        location.href = "${base}/product/ywOrder/btnSendOutGoods.htm"+formDateConvertUrlParam(formData);
    });	
	

  
     // 批量导入
	  $("#importBatch").click(function(){
	    if(confirm("您确定要批量导入吗?")){
	      var formData = $("#ywOrder_list").serializeArray();
	      $.ajax({
			 type:"post",
			 url:"${base}/product/ywOrder/importBatch.json",
			 data:formData,
			 dataType:"json",
			 success:function(data){
				if(data.error_no == "0"){
				    alert(data.error_info);
					location.href = "${base}/product/ywOrder.htm";
				} else {
				   alert(data.error_info);
				}
			 }
		  })
		 }
	   })
     
  })
   
   function dealStatus(row, head){
        var html = [];
        if(row["order_status"] == "2"){
           html.push("<a href='${base}/product/ywOrder/edit.htm?order_sn="+ row["order_sn"] +"&op_type=2' class='tablelink'>查看</a>");
           html.push("<a href='${base}/product/ywOrder/edit.htm?order_sn="+ row["order_sn"] +"&op_type=3' class='tablelink'>发货</a>");
        }else if(row["order_status"] == "8"){
           html.push("<a href='${base}/product/ywOrder/edit.htm?order_sn="+ row["order_sn"] +"&op_type=2' class='tablelink'>查看</a>");
           html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["order_sn"]+"')\" class='tablelink'>删除</a>");
        } else if(row["order_status"] == "12"){// 退款申请
           html.push("<a href='javascript:void(0)' onclick=\"updateOne('"+row["order_sn"]+"','5')\" class='tablelink'>同意</a>");
           html.push("<a href='javascript:void(0)' onclick=\"updateOne('"+row["order_sn"]+"','2')\" class='tablelink'>不同意</a>");
        } else {
            html.push("<a href='${base}/product/ywOrder/edit.htm?order_sn="+ row["order_sn"] +"&op_type=2' class='tablelink'>查看</a>");
            // 发货成功后增加导入到sqlsever数据的功能
           <#if ywRoleCache.isHasRole("${ywUser.ro_ids!''}","18")>
           if(row["order_status"] == "3" && row["order_import_status"] == 0){
              html.push("<a href='${base}/order/sqlserverT3/match.htm?order_sn="+ row["order_sn"] +"' class='tablelink'>导入</a>");
           } 
           </#if>
        }
        
   		return html.join(" ");
    }
	// 删除
 	function deleteOne(order_sn){
 	    if(confirm("您确定要删除吗?")){
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/product/ywOrder/delete.json",
 		     data:{"order_sn":order_sn},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           alert(data.error_info);
				   location.href = "${base}/product/ywOrder.htm";
		        } else {
		           alert(data.error_info);
		        }
 		     }
 		  })
 	   }
 	}
 	
 	// 批量删除
	function operationCondition(){
	   //debugger;
	   var ywOrderForm = []
	   var order_sns = []
	   ywOrderForm = getAllCheckboxs().split(",");
	   for(var i=0;i<ywOrderForm.length;i++){
	   		order_sns.push(ywOrderForm[i].split("-")[0])
	   }
	   if(confirm("您确定要删除吗?")){
	   $.ajax({
			 type:"post",
			 url:"${base}/product/ywOrder/deleteBatch.json",
			 data:{"order_sns":order_sns.toString()},
			 dataType:"json",
			 success:function(data){
				if(data.error_no == "0"){
					alert(data.error_info);
					location.href = "${base}/product/ywOrder.htm";
				   
				} else {
				   alert(data.error_info,data.infos);
				}
			 }
		  })
	  }
	}
 	
 	// 修改
 	function updateOne(order_sn,order_status){
 	    var show_info = "";
        if(order_status == "3"){
          show_info = "您确定要发货吗?";
        } else if(order_status == "2"){
          // 不同意退款
          show_info = "您确定不同意退款?";
        } else if(order_status == "5"){
          // 同意退款
          show_info = "您确定同意退款?";
        }
	      if(confirm(show_info)){
	       $.ajax({
		     type:"post",
		     url:"${base}/product/ywOrder/insertOrUpdate.json",
		     data:{"order_sn":order_sn,"order_status":order_status,"op_type":"2"},
		     dataType:"json",
		     success:function(data){
		        if(data.error_no == "0"){
		           alert(data.error_info);
				  location.href = "${base}/product/ywOrder.htm";
		        } else {
		           isTimeOutdevShowDialog(data.error_info,data.infos);
		        }
		     }
		   })
	     }
 	   }
 	   
 	
 	
</script>
</@screen>