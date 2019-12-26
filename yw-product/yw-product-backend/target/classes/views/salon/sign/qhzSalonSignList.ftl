<@screen id="qhzSalonSign" title="沙龙注册签到信息模块页面" places=[{"name":"沙龙注册签到信息模块","href":"#"}]>
<@ajaxQueryTable id="qhzSalonSign_list" auto=true isMultiple=true multipleCondition=["id"]
	action="${base}/qhz_salon_sign/qhzSalonSign/getList.json" 
	paginatorUrl="${base}/qhz_salon_sign/qhzSalonSign/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
	       {"id":"id", "name":"签到编号"},
	       {"id":"name", "name":"姓名"},
	       {"id":"phone", "name":"联系电话"},
	       {"id":"card", "name":"身份证"},
	       {"id":"adviser", "name":"学习顾问"},
	       {"id":"industry", "name":"行业"},
	       {"id":"turnover", "name":"年营业额(单位: 万)"},
	       {"id":"position", "name":"职位"},
	       {"id":"meeting_name", "name":"签到会议"},
	       {"id":"sgin_code", "name":"入场码"},
	       {"id":"sgin_time", "name":"签到时间"},
	       {"id":"pay_status", "name":"入场费支付状态","dic_key":"1094"},
	       {"id":"ticket_number", "name":"票务编号"},
	       {"id":"remark", "name":"备注"},
	       {"id":"caozuo", "name":"操作", "provider":"dealStatus"}
		
	]>

	
	<@innerOfQueryCondition>
	    <dl class="search-dl">
	    	<dd>
  				<span class="dl-title">签到会议：</span>
    			<label class="w-170">
      			<select id="meeting_id" name="meeting_id" class="yw-input">
					<option value="">请选择会议</option>
					<#list qhzSalonMeetings as qhzSalonMeeting>
						<option value="${qhzSalonMeeting.id}">${qhzSalonMeeting.name}</option>
          			</#list>
		    	</select>
    			</label>
  			</dd>
  			<dd>
  				<span class="dl-title">支付状态：</span>
    			<label class="w-170">
      			 <@select id="pay_status" name="pay_status" class="yw-input" dic_key="1094" value="${type!''}" />
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<@innerOfQueryTable>
	<div class="right-btn-box">
		<span class="input-group-btn">
			<button id="btnExport" class="btn btn-success" type="button"><span class="glyphicon glyphicon-export"></span><span>导出Excel</span></button>
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
        html.push("<a href='${base}/qhz_salon_sign/qhzSalonSign/edit.htm?id="+ row["id"]+"&op_type=2' class='tablelink'>修改</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
	//导出excel
	$("#btnExport").click(function(){
		var formData = $("#ywProduct_list").serializeArray();
		location.href = "${base}/qhz_salon_sign/qhzSalonSign/exportExcel.htm"+formDateConvertUrlParam(formData);
	});	
	
    // 删除
 	function deleteOne(id){
 	    devActAfterConfirmAndClose("您确定要删除吗",function(){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/qhz_salon_sign/qhzSalonSign/delete.json",
 		     data:{"id":id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/qhz_salon_sign/qhzSalonSign.htm";
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
	  var qhzSalonSignForm = []
	   var id = []
	   qhzSalonSignForm = getAllCheckboxs().split(",");
	   for(var i=0;i<qhzSalonSignForm.length;i++){
	   		id.push(qhzSalonSignForm[i].split("-")[0])
	   }
	  devActAfterConfirmAndClose("您确定要批量删除吗",function(){
		   $.ajax({
				 type:"post",
				 url:"${base}/qhz_salon_sign/qhzSalonSign/deleteBatch.json",
				 data:{"id":id.toString()},
				 dataType:"json",
				 success:function(data){
					if(data.error_no == "0"){
					   devActAfterShowDialog(data.error_info,function(){
						  location.href = "${base}/qhz_salon_sign/qhzSalonSign.htm";
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