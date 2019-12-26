<#assign base=request.contextPath />
<@screen id="qhzSalonSignInsert" title="沙龙注册签到信息模块页面" places=[{"name":"沙龙注册签到信息模块","href":"${base}/qhz_salon_sign/qhzSalonSign.htm"},{"name":"添加/修改","href":"#"}]>
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
.data-box{
	width:30%;
	padding: 20px;
	font-size:15px;
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	/**
	background-image: url('https://www.qhzhlkj.com/files/image/qhz/salon/pay/pay_top_bg.png');
  	background-size: 100% 100%;
  	**/
}
</style>
<form id="qhzSalonSignForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type}"/>
    <input id="id" name="id" type="hidden" value="${qhzSalonSign.id!''}"/>
	<div class="app-row-start-layout">
		<div id="formspan1" class="formtitle tab-switch app-row-center-layout active">
			<span class="tab-span">沙龙注册签到信息</span>
		</div>
		<div id="formspan2" class="formtitle tab-switch app-row-center-layout">
			<span class="tab-span">打印签到信息</span>
		</div>
	</div>
	<div id="formTable1">
		<div class="form-group">
			<label for="level" class="label">姓名</label>
			<input id="name" name="name" type="text" class="dfinput" disabled="disabled" placeholder="请输入签到人姓名" maxbytelength="20" value="${qhzSalonSign.name!''}"/>
	      
	        <label for="level" class="label">联系电话</label>
			<input id="phone" name="phone" type="text" class="dfinput" disabled="disabled" placeholder="请输入签到人联系电话" maxbytelength="15" value="${qhzSalonSign.phone!''}"/>
		</div>
		<div class="form-group">
			<label for="level" class="label">身份证</label>
			<input id="card" name="card" type="text" class="dfinput" disabled="disabled" placeholder="请输入签到人身份证" maxbytelength="50" value="${qhzSalonSign.card!''}"/>
	      
	      	<label for="level" class="label">学习顾问</label>
			<input id="adviser" name="adviser" type="text" class="dfinput" disabled="disabled" placeholder="请输入签到人学习顾问" maxbytelength="100" value="${qhzSalonSign.adviser!''}"/>
		</div>
		<div class="form-group">
			<label for="level" class="label">行业</label>
			<input id="industry" name="industry" type="text" class="dfinput" disabled="disabled" placeholder="请输入行业" maxbytelength="20" value="${qhzSalonSign.industry!''}"/>
		
			<label for="level" class="label">年营业额</label>
			<input id="turnover" name="turnover" type="text" class="dfinput" disabled="disabled" placeholder="请输入年营业额" maxbytelength="50" value="${qhzSalonSign.turnover!'0'}"/>
			<i>单位: 万</i>
		</div>
		<div class="form-group">
			<label for="level" class="label">职位</label>
			<input id="position" name="position" type="text" class="dfinput" disabled="disabled" placeholder="请输入职位" maxbytelength="20" value="${qhzSalonSign.position!''}"/>
		
			<label for="level" class="label">票务编号</label>
			<input id="ticket_number" name="ticket_number" type="text" class="dfinput" disabled="disabled" placeholder="请输入票务编号" maxbytelength="50" value="${qhzSalonSign.ticket_number!''}"/>
		</div>
		<div class="form-group">
			<label for="level" class="label">签到会议</label>
			<input id="meeting_name" name="meeting_name" type="text" class="dfinput" disabled="disabled" placeholder="请输入签到会议编号" maxlength="10" value="${qhzSalonSign.meeting_name!''}"/>
	      
	        <label for="level" class="label">签到入场号</label>
			<input id="sgin_code" name="sgin_code" type="text" class="dfinput" disabled="disabled" placeholder="请输入签到入场号" maxbytelength="10" value="${qhzSalonSign.sgin_code!''}"/>
		</div>
		<div class="form-group">
			<label for="level" class="label">签到时间</label>
	        <@dateTimePickerByDay disabled="true" placeholder="请选择签到时间" name="sgin_time" timeValue=qhzSalonSign.sgin_time />
		</div>
		<div class="form-group">
			<label for="level" class="label">入场费支付状态</label>
			<@select placeholder="请选择入场费支付状态" disabled="true" name="pay_status" dic_key="1094" value=qhzSalonSign.pay_status!'1'/>
		</div>
		<div class="form-group">
			<label for="level" class="label">备注</label>
			<input style="width:50%;" id="remark" name="remark" type="text" class="dfinput" placeholder="请输入备注信息" maxlength="200" value="${qhzSalonSign.remark!''}"/>
		</div>
		<div class="button-content">
			<#---->
		    <input type="button" class="btn btn-success btn_text" value="确认" id="btn_qhzSalonSign_submit"/>
			<input type="button" class="btn btn-success btn_text" value="返回" id="btn_qhzSalonSign_back"/>
		</div>
	</div>
	<!--startprint-->
	<div id="formTable2" style="width:90%;display: flex;flex-direction: column;align-items: center;justify-content: center">
		<div id="printToSendTable" style="width:80%" class="formtitle app-row-end-right">
			<button type="button" style="width:120px;" class="btn btn_submit_text btn-confirm">打印签到信息</button>
		</div>
		<div class="data-box">
			<p style="font-size:24px;font-family:sans-serif">${qhzSalonSign.meeting_name!''}</p>
			<div style="width:100%;font-size:15px;display: flex;flex-direction:column;justify-content:flex-start;">
				<text style="font-size:15px;font-family:sans-serif">姓名: <text style="font-size:15px;font-family:sans-serif">${qhzSalonSign.name!''}</text></text>
				<text style="font-size:15px;font-family:sans-serif">联系电话: <text style="font-size:15px;font-family:sans-serif">${qhzSalonSign.phone!''}</text></text>
				<text style="font-size:15px;font-family:sans-serif">身份证: <text style="font-size:15px;font-family:sans-serif">${qhzSalonSign.card!''}</text></text>
				<text style="font-size:15px;font-family:sans-serif">签到会议: <text style="font-size:15px;font-family:sans-serif">${qhzSalonSign.meeting_name!''}</text></text>
				<text style="font-size:15px;font-family:sans-serif">签到入场码: <text style="font-size:15px;font-family:sans-serif">${qhzSalonSign.sgin_code!''}</text></text>
				<text style="font-size:15px;font-family:sans-serif">备注: <text style="font-size:15px;font-family:sans-serif">${qhzSalonSign.remark!''}</text></text>
			</div>
		</div>
	</div>
	<!--endprint-->
</form>	
<@script>
$(function(){
    $("#formTable2").hide();
	
	$(".tab-switch").click(function(){
	    $(this).addClass("active").siblings().removeClass("active");
	    if($(this).attr("id") == "formspan1"){
	       $("#formTable1").show();
		   $("#formTable2").hide();
	    } else if($(this).attr("id") == "formspan2") {
	       $("#formTable2").show();
		   $("#formTable1").hide();
	    }
	})

  // 确认
  $("#btn_qhzSalonSign_submit").click(function(){
      // 表单校验
      if (!formValidate("qhzSalonSignForm")) {
		 return false;
	  }
      var formData = $("#qhzSalonSignForm").serializeArray();
      $.post($("#qhzSalonSignForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
               devActAfterShowDialog(data.error_info,function(){
                 location.href = "${base}/qhz_salon_sign/qhzSalonSign.htm";
                },"suc");
            } else if(data.error_no == "-1"){
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }else{
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }
        })
  })
  
  // 取消
  $("#btn_qhzSalonSign_back").click(function(){
      location.href = "${base}/qhz_salon_sign/qhzSalonSign.htm";
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