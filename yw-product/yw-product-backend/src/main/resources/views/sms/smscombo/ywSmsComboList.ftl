<#assign base=request.contextPath />
<@screen id="ywSmsCombo" title="短信套餐" places=[{"name":"短信套餐","href":"#"}]>
	
<@ajaxQueryTable id="ywSmsCombo_list" auto=true isMultiple=true multipleCondition=["combo_id"]
	action="${base}/sms/ywSmsCombo/getList.json" 
	paginatorUrl="${base}/sms/ywSmsCombo/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
		{"id":"combo_id", "name":"编号"},
		{"id":"combo_type", "name":"套餐类型","dic_key":"1045"},
		{"id":"combo_number", "name":"短信条数（单位：千）"},
		{"id":"combo_unitprice", "name":"单价（单位：元 ）"},
		{"id":"combo_remark", "name":"备注"},
		{"id":"combo_status", "name":"状态","dic_key":"1000"},
		{"id":"caozuo", "name":"操作", "provider":"dealStatus"}
	]>
	
	<#-- 查询条件 -->
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">编号：</span>
    			<label class="w-170">
      			 <input id="combo_id" name="combo_id" type="text" class="yw-input" value="" placeholder="请输入编号"/ >
    			</label>
  			</dd>
		</dl>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">套餐类型：</span>
    			<label class="w-170">
    			<@select placeholder="请选择套餐类型" name = "combo_type" class="yw-input" dic_key="1045" value=""/>
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<#-- 其他 -->
	<@innerOfQueryTable>
	<div class="right-btn-box">
	    <span class="input-group-btn" style="float: left;">
			<a href="${base}/sms/ywSmsCombo/edit.htm?op_type=1">
				<button class="btn btn-success btn_text" type="button">添加</button>
			</a>
		</span>
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
        html.push("<a href='${base}/sms/ywSmsCombo/edit.htm?combo_id="+ row["combo_id"] +"&op_type=2' class='tablelink'>修改</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["combo_id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(combo_id){
 	    devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/sms/ywSmsCombo/delete.json",
 		     data:{"combo_id":combo_id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/sms/ywSmsCombo.htm";
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
	   var ywSmsComboForm = []
	   var combo_ids = []
	   ywSmsComboForm = getAllCheckboxs().split(",");
	   for(var i=0;i<ywSmsComboForm.length;i++){
	   		combo_ids.push(ywSmsComboForm[i].split("-")[0])
	   }
	   devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
	   $.ajax({
			 type:"post",
			 url:"${base}/sms/ywSmsCombo/deleteBatch.json",
			 data:{"combo_ids":combo_ids.toString()},
			 dataType:"json",
			 success:function(data){
				if(data.error_no == "0"){
				   devActAfterShowDialog(data.error_info,function(){
					  location.href = "${base}/sms/ywSmsCombo.htm";
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