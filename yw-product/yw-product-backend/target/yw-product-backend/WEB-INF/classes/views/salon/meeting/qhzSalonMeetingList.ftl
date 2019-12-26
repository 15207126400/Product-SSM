<link rel="stylesheet" href="${base}/css/bootstrap-datetimepicker.min.css" type="text/css">
<#-- 时间控件 -->
<@script src="${base}/js/bootstrap-datetimepicker.js" />
<@script src="${base}/js/bootstrap-datetimepicker.zh-CN.js" />	
<@screen id="qhzSalonMeeting" title="沙龙注册会议信息模块页面" places=[{"name":"沙龙注册会议信息模块","href":"#"}]>
<@ajaxQueryTable id="qhzSalonMeeting_list" auto=true isMultiple=true multipleCondition=["id"]
	action="${base}/qhz_salon_meeting/qhzSalonMeeting/getList.json" 
	paginatorUrl="${base}/qhz_salon_meeting/qhzSalonMeeting/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
	       {"id":"id", "name":"会议编号"},
	       {"id":"name", "name":"会议名称"},
	       {"id":"address", "name":"会议地址"},
	       {"id":"people_num", "name":"会议人数"},
	       {"id":"people_surplus_num", "name":"已签人数"},
	       {"id":"price", "name":"会议价格"},
	       {"id":"start_day", "name":"开始日期"},
	       {"id":"end_day", "name":"结束日期"},
	       {"id":"is_ticket", "name":"是否需要票务编码","dic_key":"1098"},
	       {"id":"status", "name":"状态","dic_key":"1000"},
	       {"id":"caozuo", "name":"操作", "provider":"dealStatus"}
		
	]>

	
	<@innerOfQueryCondition>
	    <dl class="search-dl">
	    	<dd>
  				<span class="dl-title">会议名称：</span>
    			<label class="w-170">
      			 <input id="name" name="name" type="text" class="yw-input" value="" placeholder="请输入会议名称关键字"/ >
    			</label>
  			</dd>
  			<dd>
  				<span class="dl-title">会议地址：</span>
    			<label class="w-170">
      			 <input id="address" name="address" type="text" class="yw-input" value="" placeholder="请输入会议地址关键字"/ >
    			</label>
  			</dd>
  			<#--
  			<dd>
  				<span class="dl-title data">开始时间：</span>
    			<label class="w-170">
	    			<div class='input-group date' id='startDay'>
			            <input id="start_day" name="start_day" readonly="readonly" type="text" class="yw-input" value="" placeholder="请选择开始时间"/ >
			            <span class="input-group-addon">
			                <span style="top:-21px;left:140px;" class="glyphicon glyphicon-calendar"></span>
			            </span>
			        </div>
    			</label>
  			</dd>
  			<dd>
  				<span class="dl-title data">结束时间：</span>
    			<label class="w-170">
	    			<div class='input-group date' id='endDay'>
			            <input id="end_day" name="end_day" readonly="readonly" type="text" class="yw-input" value="" placeholder="请选择结束时间"/ >
			            <span class="input-group-addon">
			                <span style="top:-21px;left:140px;" class="glyphicon glyphicon-calendar"></span>
			            </span>
			        </div>
    			</label>
  			</dd>
  			-->
		</dl>
	</@innerOfQueryCondition>
	
	<@innerOfQueryTable>
	<div class="right-btn-box">
	    <span class="input-group-btn" style="float: left;">
			<a href="${base}/qhz_salon_meeting/qhzSalonMeeting/edit.htm?op_type=1">
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
        html.push("<a href='${base}/qhz_salon_meeting/qhzSalonMeeting/edit.htm?id="+ row["id"]+"&op_type=2' class='tablelink'>查看</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(id){
 	    devActAfterConfirmAndClose("您确定要删除吗",function(){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/qhz_salon_meeting/qhzSalonMeeting/delete.json",
 		     data:{"id":id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/qhz_salon_meeting/qhzSalonMeeting.htm";
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
	  var qhzSalonMeetingForm = []
	   var id = []
	   qhzSalonMeetingForm = getAllCheckboxs().split(",");
	   for(var i=0;i<qhzSalonMeetingForm.length;i++){
	   		id.push(qhzSalonMeetingForm[i].split("-")[0])
	   }
	  devActAfterConfirmAndClose("您确定要批量删除吗",function(){
		   $.ajax({
				 type:"post",
				 url:"${base}/qhz_salon_meeting/qhzSalonMeeting/deleteBatch.json",
				 data:{"id":id.toString()},
				 dataType:"json",
				 success:function(data){
					if(data.error_no == "0"){
					   devActAfterShowDialog(data.error_info,function(){
						  location.href = "${base}/qhz_salon_meeting/qhzSalonMeeting.htm";
						},"suc");
					} else {
					   isTimeOutdevShowDialog(data.error_info,data.infos);
					}
				 }
			  })
		});  
	 }
	 
	 //选取开始时间
	 $('#startDay').datetimepicker({
		  minView: "month",//设置只显示到月份
		  format: 'yyyy-mm-dd',
		  language: 'zh-CN',
		  stepHour: 1,//设置步长
		  stepMinute: 1,
		  stepSecond: 1,
		  autoclose: true,//选中自动关闭
	      todayBtn: true,//显示今日按钮
	  });
	  
	 //选取结束时间
	 $('#endDay').datetimepicker({
		  minView: "month",//设置只显示到月份
		  format: 'yyyy-mm-dd',
		  language: 'zh-CN',
		  stepHour: 1,//设置步长
		  stepMinute: 1,
		  stepSecond: 1,
		  autoclose: true,//选中自动关闭
	      todayBtn: true,//显示今日按钮
	  });
 	
</@script>
</@screen>