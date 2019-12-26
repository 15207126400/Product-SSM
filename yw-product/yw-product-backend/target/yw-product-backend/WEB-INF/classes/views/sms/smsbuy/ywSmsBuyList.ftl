<#assign base=request.contextPath />
<@screen id="ywSmsBuy" title="短信购买记录页面" places=[{"name":"短信购买记录","href":"#"}]>
	
<@ajaxQueryTable id="ywSmsBuy_list" auto=true isMultiple=true multipleCondition=["buy_id"]
	action="${base}/sms/ywSmsBuy/getList.json" 
	paginatorUrl="${base}/sms/ywSmsBuy/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
		{"id":"buy_id", "name":"编号"},
		{"id":"user_id", "name":"购买人"},
		{"id":"combo_id", "name":"套餐编号"},
		{"id":"buy_number", "name":"购买条数（单位：条）"},
		{"id":"buy_usenumber", "name":"已使用条数（单位：条）"},
		{"id":"buy_createtime", "name":"购买时间"},
		{"id":"buy_status", "name":"状态","dic_key":"1043"},
		{"id":"caozuo", "name":"操作", "provider":"dealStatus"}
	]>
	
	<#-- 查询条件 -->
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">编号：</span>
    			<label class="w-170">
      			 <input id="buy_id" name="buy_id" type="text" class="yw-input" value="" placeholder="请输入编号"/ >
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<#-- 其他 -->
	<@innerOfQueryTable>
	<div class="right-btn-box">
   	   <#-- 
	   <span class="input-group-btn" style="float: left;">
			<a href="${base}/sms/ywSmsBuy/edit.htm?op_type=1">
				<button class="btn btn-success btn_text" type="button">购买</button>
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
        html.push("<a href='${base}/sms/ywSmsBuy/edit.htm?buy_id="+ row["buy_id"] +"&op_type=2' class='tablelink'>查看</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["buy_id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(buy_id){
 	    devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/sms/ywSmsBuy/delete.json",
 		     data:{"buy_id":buy_id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/sms/ywSmsBuy.htm";
	                },"suc");
 		        } else {
 		           isTimeOutdevShowDialog(data.error_info,data.infos);
 		        }
 		     }
 		  })
 	   })
 	}
 	
 	function operationCondition(){
 	   var ywSmsBuyForm = []
	   var buy_ids = []
	   ywSmsBuyForm = getAllCheckboxs().split(",");
	   for(var i=0;i<ywSmsBuyForm.length;i++){
	   		buy_ids.push(ywSmsBuyForm[i].split("-")[0])
	   }
 	   devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
 	   $.ajax({
 		     type:"post",
 		     url:"${base}/business/ywSmsBuy/deleteBatch.json",
 		     data:{"buy_ids":buy_ids.toString()},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/business/ywSmsBuy.htm";
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