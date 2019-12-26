<#assign base=request.contextPath />
<@screen id="ywOrderInsert" title="订单信息页面" places=[{"name":"订单信息","href":"${base}/product/ywOrder.htm"},{"name":"详情","href":"#"}]>
<style>

#formTable3{
   width:800px;
   margin: 0 auto;
}
.top-box{
	height:120px;
	width:100%;
}
#formTable3 p{
	font-size:30px;
	font-family:sans-serif;
}
.top-item{
	width:100%;
	font-size:15px;
}

.table-text-style{
	font-size:15px;
	text-align:center;
	border:1px solid;
	font-family:sans-serif;
}
.text-style{
	font-size:15px;
	font-family:sans-serif;
}

</style>
<form id="ywOrderForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type!''}"/>
    <input id="order_id" name="order_id" type="hidden" class="dfinput" <#if op_type == "2">readonly="readonly"</#if> maxlength="18" value="${ywOrder.order_id!''}"/>
	<div class="app-row-start-layout">
		<div id="formspan1" class="formtitle tab-switch app-row-center-layout active">
			<span class="tab-span">订单基本信息</span>
		</div>
		<div id="formspan2" class="formtitle tab-switch app-row-center-layout">
			<span class="tab-span">订单其他信息</span>
		</div>
		<div id="formspan3" class="formtitle tab-switch app-row-center-layout">
			<span class="tab-span">订单发货信息</span>
		</div>
	</div>
	<div id="formTable1">
		<div class="form-group">
			<label for="level" class="label">订单类型</label>
			<@select valid="NotBlank" name="order_type" disabled="true" dic_key="1016" value="${ywOrder.order_type!''}" />
		
			<label for="level" class="label">订单号</label>
			<input id="order_sn" name="order_sn" type="text" class="dfinput" <#if op_type == "2">readonly="readonly"</#if> value="${ywOrder.order_sn!''}"/>
		</div>
		<div class="form-group">
			<label for="level" class="label">购买人</label>
			<input id="nickname" name="nickname" type="text" class="dfinput" <#if op_type == "2">readonly="readonly"</#if> value="${nickname!''}"/>
		
			<label for="level" class="label">分享人</label>
			<input id="share_nickname" name="share_nickname" type="text" class="dfinput" <#if op_type == "2">readonly="readonly"</#if> value="${share_nickname!''}"/>
		</div>
		<div class="form-group">
			<label for="level" class="label">收货人</label>
			<input id="order_name" name="order_name" type="text" class="dfinput" <#if op_type == "2">readonly="readonly"</#if> value="${ywOrder.order_name!''}"/>
		
			<label for="level" class="label">收货人电话</label>
			<input id="order_tel" name="order_tel" type="text" class="dfinput" <#if op_type == "2">readonly="readonly"</#if> value="${ywOrder.order_tel!''}"/>
		</div>
		<div class="form-group">
			<label for="level" class="label">收货人地址</label>
			<input id="order_address" name="order_address" type="text" class="dfinput" <#if op_type == "2">readonly="readonly"</#if> value="${ywOrder.order_address!''}"/>
		    
		    <label for="level" class="label">订单状态</label>
			<@select valid="NotBlank" name="order_status" disabled="true" dic_key="1017" value="${ywOrder.order_status!''}" />
		</div>
		<div class="form-group">
		    <label for="level" class="label">获取积分</label>
			<input id="order_points" name="order_points" type="text" class="dfinput" <#if op_type == "2">readonly="readonly"</#if> value="${ywOrder.order_points!''}"/>
			
			<label for="level" class="label">订单金额（元）</label>
			<input id="order_totalprice" name="order_totalprice" type="text" class="dfinput" <#if op_type == "2">readonly="readonly"</#if> value="${ywOrder.order_totalprice!''}"/>
		</div>
		
		<div class="form-group">
			<label for="level" class="label">订单创建时间</label>
			<@dateTimePicker name="order_createtime" disabled="true" timeValue=ywOrder.order_createtime />
		
			<label for="level" class="label">订单更新时间</label>
			<@dateTimePicker name="order_updatetime" disabled="true" timeValue=ywOrder.order_updatetime />
		</div>
		<div class="form-group">
			<label for="level" class="label">订单创建时间</label>
			<@dateTimePicker name="order_createtime" disabled="true" timeValue=ywOrder.order_createtime />
		
			<label for="level" class="label">订单更新时间</label>
			<@dateTimePicker name="order_updatetime" disabled="true" timeValue=ywOrder.order_updatetime />
		</div>
		<div class="form-group">
			<label for="level" class="label">订单支付时间</label>
			<@dateTimePicker name="order_paytime" disabled="true" timeValue=ywOrder.order_paytime />
		
			<label for="level" class="label">订单发货时间</label>
			<@dateTimePicker name="order_sendtime" disabled="true" timeValue=ywOrder.order_sendtime />
		</div>
		<div class="form-group">
			<label for="level" class="label">订单完成时间</label>
			<@dateTimePicker name="order_successtime" disabled="true" timeValue=ywOrder.order_successtime />
		
		    <label for="level" class="label">物流单号</label>
			<input id="transport_sn" name="transport_sn" type="text" class="dfinput" <#if op_type == "2">readonly="readonly"</#if> value="${ywOrder.transport_sn!''}"/>
		</div>
		<div class="form-group">
		    <label for="level" class="label">配送方式</label>
			<@select valid="NotBlank" name="order_delivery_method" disabled="true" dic_key="1076" value="${ywOrder.order_delivery_method!''}" />
			
			<label for="level" class="label">配送费</label>
			<input id="order_delivery_fee" name="order_delivery_fee" type="text" class="dfinput" <#if op_type == "2">readonly="readonly"</#if> value="${ywOrder.order_delivery_fee!''}"/>
		</div>
		<div class="form-group">
			<label for="level" class="label">订单备注</label>
			<textarea id="order_remark" name="order_remark" readonly="readonly" class="textareainput" placeholder="请输入备注" cols="100" rows="5">${ywOrder.order_remark!''}</textarea> 
		</div>
		<div class="button-content">
			<#if op_type == "1">
			    <input type="button" class="btn btn_submit_text btn-confirm" value="确认" id="btn_ywOrder_submit"/>
				<input type="button" class="btn btn_submit_text btn-cancel" value="取消" id="btn_ywOrder_back"/>
			</#if>
			<#if op_type == "2">
				<input type="button" class="btn btn_submit_text btn-cancel" value="返回" id="btn_ywOrder_back"/>
			</#if>
		</div>
	</div>
	<div id="formTable2">
		<text>其他信息</text>
	</div>
	<!--startprint-->
	<div id="formTable3" style="width:90%;display: flex;flex-direction: column;align-items: center;justify-content: center">
		<div id="printToSendTable" style="width:80%" class="formtitle app-row-end-right">
			<button type="button" class="btn btn_submit_text btn-confirm">打印发货单</button>
		</div>
		<div style="width:100%;font-size:15px;display: flex;flex-direction: column;align-items: center;justify-content: center">
			<p style="font-size:30px;font-family:sans-serif">${user.company_name}销货单</p>
			<div style="width:100%;font-size:15px;display: flex;flex-direction: row;justify-content: space-between;align-items: center">
				<text style="font-size:15px;font-family:sans-serif">日期 : <text style="font-size:15px;font-family:sans-serif">${ywOrder.order_createtime?string('yyyy-MM-dd HH:mm:ss')}</text></text>
				<text style="font-size:15px;font-family:sans-serif">订单编号 : <text style="font-size:15px;font-family:sans-serif">${ywOrder.order_sn}</text></text>
				<text style="font-size:15px;font-family:sans-serif">客户姓名 : <text style="font-size:15px;font-family:sans-serif">${ywOrder.order_name}</text></text>
			</div>
			<div style="width:100%;font-size:15px;display: flex;flex-direction: row;justify-content: space-between;align-items: center">
				<text style="font-size:15px;font-family:sans-serif">客户地址 : <text style="font-size:15px;font-family:sans-serif">${ywOrder.order_address}</text></text>
				<text style="font-size:15px;font-family:sans-serif">客户手机号 : <text style="font-size:15px;font-family:sans-serif">${ywOrder.order_tel}</text></text>
			</div>
		</div>
		
		<table style="width:100%;">
	        <tr>
	        	<th style="font-size:15px;text-align:center;border:1px solid;font-family:sans-serif">编号</th>
	            <th style="font-size:15px;text-align:center;border:1px solid;font-family:sans-serif">商品名称</th>
	            <th style="font-size:15px;text-align:center;border:1px solid;font-family:sans-serif">规格型号</th>
	            <th style="font-size:15px;text-align:center;border:1px solid;font-family:sans-serif">数量</th>
	            <th style="font-size:15px;text-align:center;border:1px solid;font-family:sans-serif">单价</th>
	            <th style="font-size:15px;text-align:center;border:1px solid;font-family:sans-serif">金额</th>
	        </tr>
	        <#list orderItem as data>
	          <tr>
	        	<td style="font-size:15px;text-align:center;border:1px solid;font-family:sans-serif">${data.product_id}</td>
	            <td style="font-size:15px;text-align:center;border:1px solid;font-family:sans-serif">${data.order_ite_name}</td>
	            <td style="font-size:15px;text-align:center;border:1px solid;font-family:sans-serif">${data.order_ite_sku}</td>
	            <td style="font-size:15px;text-align:center;border:1px solid;font-family:sans-serif">${data.order_ite_count}</td>
	            <td style="font-size:15px;text-align:center;border:1px solid;font-family:sans-serif">${data.order_ite_price}</td>
	            <td style="font-size:15px;text-align:center;border:1px solid;font-family:sans-serif">${data.order_ite_totalPrice}</td>
	          </tr>
	      	</#list>
	        
	    </table>
	    <div style="width:100%;display: flex;flex-direction: column;align-items: center;justify-content: center">
	    	<div style="width:100%;font-size:15px;display: flex;flex-direction: row;justify-content: space-between;align-items: center">
    			<text style="font-size:15px;font-family:sans-serif">使用优惠券 : <text style="font-size:15px;font-family:sans-serif">${couponName!'无'}</text></text>
	    		<text style="font-size:15px;font-family:sans-serif">优惠金额 : <text style="font-size:15px;font-family:sans-serif">${couponPrice!'0.00'}</text></text>
				<text style="font-size:15px;font-family:sans-serif">合计 : <text style="font-size:15px;font-family:sans-serif">${ywOrder.order_totalprice}</text></text>
			</div>
	    	<div style="width:100%;display: flex;flex-direction: row;justify-content: space-between;align-items: center">
	    		<text style="font-size:15px;font-family:sans-serif">备注 : <text style="font-size:15px;font-family:sans-serif">${ywOrder.order_remark}</text></text>
	    	</div>
	    	<div style="width:100%;display: flex;flex-direction: row;justify-content: space-between;align-items: center">
	    		<text style="font-size:15px;font-family:sans-serif">操作人 : <text style="font-size:15px;font-family:sans-serif">${user.cust_name}</text></text>
	    		<text style="font-size:15px;font-family:sans-serif">公司名称 : <text style="font-size:15px;font-family:sans-serif">${user.company_name}</text></text>
	    	</div>
	    </div>
	</div>
	<!--endprint-->
</form>	
<@script>


$(function(){
	
	$("#formTable2").hide();
	$("#formTable3").hide();
	
	$(".tab-switch").click(function(){
	    $(this).addClass("active").siblings().removeClass("active");
	    if($(this).attr("id") == "formspan1"){
	       $("#formTable1").show();
		   $("#formTable2").hide();
		   $("#formTable3").hide();
	    } else if($(this).attr("id") == "formspan2") {
	       $("#formTable2").show();
		   $("#formTable1").hide();
		   $("#formTable3").hide();
	    } else if($(this).attr("id") == "formspan3"){
	      $("#formTable3").show();
		  $("#formTable1").hide();
		  $("#formTable2").hide(); 
	    }
	})

  // 确认
  $("#btn_ywOrder_submit").click(function(){
      var formData = $("#ywOrderForm").serializeArray();
      $.post($("#ywOrderForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
                devActAfterShowDialog(data.error_info,function(){
                  location.href = "${base}/product/ywOrder.htm";
                },"suc");
            } else {
                isTimeOutdevShowDialog(data.error_info,data.infos);
            };
        }).error(function(){
            devShowDialog("系统异常");
        });
  })
  
  // 取消
  $("#btn_ywOrder_back").click(function(){
      location.href = "${base}/product/ywOrder.htm";
  })
  
})

	$("#printToSendTable").click(function(){
		// 执行打印
		doPrint();
	})
	
	// 打印指定部分内容
	function doPrint() {
        bdhtml=window.document.body.innerHTML;
        sprnstr="<!--startprint-->"; //开始打印标识字符串有17个字符
        eprnstr="<!--endprint-->"; //结束打印标识字符串
        prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17); //从开始打印标识之后的内容
        prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr)); //截取开始标识和结束标识之间的内容
        window.document.body.innerHTML=prnhtml; //把需要打印的指定内容赋给body.innerHTML
        window.print(); //调用浏览器的打印功能打印指定区域
        window.document.body.innerHTML=bdhtml; // 最后还原页面
        window.location.reload();
    }
        
</@script>
</@screen>