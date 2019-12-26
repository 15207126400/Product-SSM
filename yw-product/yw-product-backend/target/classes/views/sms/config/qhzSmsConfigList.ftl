<@screen id="qhzSmsConfig" title="沙龙注册短信系统配置页面" places=[{"name":"沙龙注册短信系统配置","href":"#"}]>
<@ajaxQueryTable id="qhzSmsConfig_list" auto=true isMultiple=true multipleCondition=["id"]
	action="${base}/qhz_sms_config/qhzSmsConfig/getList.json" 
	paginatorUrl="${base}/qhz_sms_config/qhzSmsConfig/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
	       {"id":"id", "name":"编号"},
	       {"id":"type", "name":"短信通道类型","dic_key":"1095"},
	       {"id":"url", "name":"请求路径"},
	       {"id":"userid", "name":"企业id"},
	       {"id":"account", "name":"账号"},
	       {"id":"password", "name":"密码"},
	       {"id":"action", "name":"发送任务命令"},
	       {"id":"sign", "name":"短信前缀公司签名"},
	       {"id":"caozuo", "name":"操作", "provider":"dealStatus"}
		
	]>

	
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">编号：</span>
    			<label class="w-170">
      			 <input id="id" name="id" type="text" class="yw-input" value="" placeholder="请输入"/ >
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<@innerOfQueryTable>
	<div class="right-btn-box">
	    <span class="input-group-btn" style="float: left;">
			<a href="${base}/qhz_sms_config/qhzSmsConfig/edit.htm?op_type=1">
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
        html.push("<a href='${base}/qhz_sms_config/qhzSmsConfig/edit.htm?id="+ row["id"]+"&op_type=2' class='tablelink'>修改</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(id){
 	    devActAfterConfirmAndClose("您确定要删除吗",function(){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/qhz_sms_config/qhzSmsConfig/delete.json",
 		     data:{"id":id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/qhz_sms_config/qhzSmsConfig.htm";
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
	  var qhzSmsConfigForm = []
	   var id = []
	   qhzSmsConfigForm = getAllCheckboxs().split(",");
	   for(var i=0;i<qhzSmsConfigForm.length;i++){
	   		id.push(qhzSmsConfigForm[i].split("-")[0])
	   }
	  devActAfterConfirmAndClose("您确定要批量删除吗",function(){
		   $.ajax({
				 type:"post",
				 url:"${base}/qhz_sms_config/qhzSmsConfig/deleteBatch.json",
				 data:{"id":id.toString()},
				 dataType:"json",
				 success:function(data){
					if(data.error_no == "0"){
					   devActAfterShowDialog(data.error_info,function(){
						  location.href = "${base}/qhz_sms_config/qhzSmsConfig.htm";
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