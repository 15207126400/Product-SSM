<@screen id="qhzSmsDetail" title="沙龙注册短信信息管理页面" places=[{"name":"沙龙注册短信信息管理","href":"#"}]>
<@ajaxQueryTable id="qhzSmsDetail_list" auto=true isMultiple=true multipleCondition=["id"]
	action="${base}/qhz_sms_detail/qhzSmsDetail/getList.json" 
	paginatorUrl="${base}/qhz_sms_detail/qhzSmsDetail/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
	       {"id":"id", "name":"编号"},
	       {"id":"type", "name":"短信通道类型","dic_key":"1095"},
	       {"id":"mobile", "name":"被叫号码"},
	       {"id":"content", "name":"发送内容","width":"30%"},
	       {"id":"sendTime", "name":"定时发送时间"},
	       {"id":"message", "name":"发送返回状态"},
	       {"id":"createTime", "name":"创建时间"},
	       {"id":"caozuo", "name":"操作", "provider":"dealStatus"}
		
	]>

	
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">被叫号码：</span>
    			<label class="w-170">
      			 <input id="mobile" name="mobile" type="text" class="yw-input" value="" placeholder="请输入被叫号码"/ >
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<@innerOfQueryTable>
	<div class="right-btn-box">
	    <span class="input-group-btn" style="float: left;">
			<a href="${base}/qhz_sms_detail/qhzSmsDetail/edit.htm?op_type=1">
				<button class="btn btn-success btn_text" type="button">发送短信</button>
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
        <#--
        html.push("<a href='${base}/qhz_sms_detail/qhzSmsDetail/edit.htm?id="+ row["id"]+"&op_type=2' class='tablelink'>修改</a>");
        -->
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(id){
 	    devActAfterConfirmAndClose("您确定要删除吗",function(){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/qhz_sms_detail/qhzSmsDetail/delete.json",
 		     data:{"id":id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/qhz_sms_detail/qhzSmsDetail.htm";
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
	  var qhzSmsDetailForm = []
	   var id = []
	   qhzSmsDetailForm = getAllCheckboxs().split(",");
	   for(var i=0;i<qhzSmsDetailForm.length;i++){
	   		id.push(qhzSmsDetailForm[i].split("-")[0])
	   }
	  devActAfterConfirmAndClose("您确定要批量删除吗",function(){
		   $.ajax({
				 type:"post",
				 url:"${base}/qhz_sms_detail/qhzSmsDetail/deleteBatch.json",
				 data:{"id":id.toString()},
				 dataType:"json",
				 success:function(data){
					if(data.error_no == "0"){
					   devActAfterShowDialog(data.error_info,function(){
						  location.href = "${base}/qhz_sms_detail/qhzSmsDetail.htm";
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