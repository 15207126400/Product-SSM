<#assign base=request.contextPath />
<@screen id="ywSmsNotice" title="短信通知" places=[{"name":"短信通知","href":"#"}]>
	
<@ajaxQueryTable id="ywSmsNotice_list" auto=true isMultiple=true multipleCondition=["sms_id"]
	action="${base}/sms/ywSmsNotice/getList.json" 
	paginatorUrl="${base}/sms/ywSmsNotice/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
		{"id":"sms_id", "name":"编号"},
		{"id":"user_id", "name":"商家编号"},
		{"id":"sms_type", "name":"短信类型","dic_key":"1046"},
		{"id":"sms_template_id", "name":"模板"},
		{"id":"sms_mobile", "name":"手机号码"},
		{"id":"sms_content", "name":"通知内容"},
		{"id":"sms_isfee", "name":"是否收费","dic_key":"1053"},
		{"id":"sms_status", "name":"状态","dic_key":"1044"},
		{"id":"caozuo", "name":"操作", "provider":"dealStatus"}
	]>
	
	<#-- 查询条件 -->
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  			    <span class="dl-title">编号：</span>
    			<label class="w-170">
      			 <input id="sms_id" name="sms_id" type="text" class="yw-input" value="" placeholder="请输入编号"/ >
    			</label>
  			</dd>
		</dl>
		<dl class="search-dl">
  			<dd>
  			    <span class="dl-title">短信类型：</span>
    			<label class="w-170">
    			<@select placeholder="请选择短信类型" name = "sms_type" class="yw-input" dic_key="1046" value=ywDictionary.dic_status!""/>
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<#-- 其他 -->
	<@innerOfQueryTable>
	<div class="right-btn-box">
	    <#-- <span class="input-group-btn" style="float: left;">
			<a href="${base}/sms/ywSmsNotice/edit.htm?op_type=1">
				<button class="btn btn-success btn_text" type="button">添加</button>
			</a>
		</span>-->
		<span class="input-group-btn">
			<a href="javascript:void(0)" >
				<button class="btn btn-success btn_text" type="button" onclick="operationCondition()">批量删除</button>
			</a>
		</span> 
	</div>
    </@innerOfQueryTable>
    
</@ajaxQueryTable>
<@script>
    function dealStatus(row, head){
        var html = [];
        html.push("<a href='${base}/sms/ywSmsNotice/edit.htm?sms_id="+ row["sms_id"] +"&op_type=2' class='tablelink'>查看</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["sms_id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(sms_id,sms_type){
 	    devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/sms/ywSmsNotice/delete.json",
 		     data:{"sms_id":sms_id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/sms/ywSmsNotice.htm";
	                },"suc");
 		        } else {
 		           isTimeOutdevShowDialog(data.error_info,data.infos);
 		        }
 		     }
 		  })
 	   })
 	}
 	
 	// 批量删除
 	function operationCondition(){
 	   var ywSmsNoticeForm = []
	   var sms_ids = []
	   ywSmsNoticeForm = getAllCheckboxs().split(",");
	   for(var i=0;i<ywSmsNoticeForm.length;i++){
	   		sms_ids.push(ywSmsNoticeForm[i].split("-")[0])
	   }
 	   devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
 	   $.ajax({
 		     type:"post",
 		     url:"${base}/sms/ywSmsNotice/deleteBatch.json",
 		     data:{"sms_ids":sms_ids.toString()},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/sms/ywSmsNotice.htm";
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