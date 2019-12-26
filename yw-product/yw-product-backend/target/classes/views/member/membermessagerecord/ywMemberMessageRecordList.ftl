<@screen id="ywMemberMessageRecord" title="会员模板消息发送记录信息页面" places=[{"name":"会员模板消息发送记录信息","href":"#"}]>
<@ajaxQueryTable id="ywMemberMessageRecord_list" auto=true isMultiple=true multipleCondition=["id"]
	action="${base}/member/ywMemberMessageRecord/getList.json" 
	paginatorUrl="${base}/member/ywMemberMessageRecord/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
	       {"id":"id", "name":"编号"},
	       {"id":"openid", "name":"消息用户"},
	       {"id":"formid", "name":"消息编号"},
	       {"id":"prepay_id", "name":"微信预订单编号"},
	       {"id":"message_type", "name":"消息类型"},
	       {"id":"message_content", "name":"消息内容"},
	       {"id":"order_sn", "name":"订单编号"},
	       {"id":"branch_id", "name":"机构编号"},
	       {"id":"createtime", "name":"创建时间"},
	       {"id":"updatetime", "name":"更新时间"},
	       {"id":"message_status", "name":"消费发送状态"},
	       {"id":"message_errorinfo", "name":"消息发送错误信息"},
	       {"id":"caozuo", "name":"操作", "provider":"dealStatus"}
		
	]>

	
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">编号：</span>
    			<label class="w-170">
      			 <input id="id" name="id" type="text" class="yw-input" value="" placeholder="请输入编号"/ >
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<@innerOfQueryTable>
	<div class="right-btn-box">
	    <span class="input-group-btn" style="float: left;">
			<a href="${base}/member/ywMemberMessageRecord/edit.htm?op_type=1">
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
        html.push("<a href='${base}/member/ywMemberMessageRecord/edit.htm?id="+ row["id"]+"&op_type=2' class='tablelink'>修改</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(id){
 	    devActAfterConfirmAndClose("您确定要删除吗",function(){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/member/ywMemberMessageRecord/delete.json",
 		     data:{"id":id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/member/ywMemberMessageRecord.htm";
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
	  var ywMemberMessageRecordForm = []
	   var id = []
	   ywMemberMessageRecordForm = getAllCheckboxs().split(",");
	   for(var i=0;i<ywMemberMessageRecordForm.length;i++){
	   		id.push(ywMemberMessageRecordForm[i].split("-")[0])
	   }
	  devActAfterConfirmAndClose("您确定要批量删除吗",function(){
		   $.ajax({
				 type:"post",
				 url:"${base}/member/ywMemberMessageRecord/deleteBatch.json",
				 data:{"id":id.toString()},
				 dataType:"json",
				 success:function(data){
					if(data.error_no == "0"){
					   devActAfterShowDialog(data.error_info,function(){
						  location.href = "${base}/member/ywMemberMessageRecord.htm";
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