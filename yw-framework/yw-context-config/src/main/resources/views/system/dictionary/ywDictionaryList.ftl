<#assign base=request.contextPath />
<@screen id="ywDictionary" title="系统数据字典页面" places=[{"name":"数据字典","href":"#"}]>
<@ajaxQueryTable id="ywDictionary_list" auto=true isMultiple=true multipleCondition=["dic_key","dic_subkey"]
	action="${base}/system/ywDictionary/getList.json" 
	paginatorUrl="${base}/system/ywDictionary/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
		{"id":"dic_key", "name":"字典项"},
		{"id":"dic_value", "name":"字典值"},
		{"id":"dic_subkey", "name":"字典子项"},
		{"id":"dic_subvalue", "name":"字典子项值"},
		{"id":"dic_remark", "name":"备注"},
		{"id":"dic_status", "name":"状态","dic_key":"1000"},
		{"id":"caozuo", "name":"操作", "provider":"dealStatus"}
	]>
	
	<#-- 查询条件 -->
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">字典项：</span>
    			<label class="w-170">
    			<@autocomplete size="10" maxHeight="250" noCache="false" inputId="dic_key" tableName="yw_system_usercenter.yw_dictionary" column="dic_key">
      			 <input id="dic_key" name="dic_key" type="text" class="yw-input" value="" placeholder="请输入字典项"/ >
      			 </@autocomplete>
    			</label>
  			</dd>
		</dl>
	    <dl class="search-dl">
  			<dt>字典值：</dt>
  			<dd>
    			<label class="w-170">
    			<@autocomplete size="10" maxHeight="250" noCache="false" inputId="dic_value" tableName="yw_system_usercenter.yw_dictionary" column="dic_value">
      			<input id="dic_value" name="dic_value" type="text" class="yw-input" value="" placeholder="请输入字典值"/ >
      			</@autocomplete>
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<#-- 其他 -->
	<@innerOfQueryTable>
	<div class="right-btn-box">
	    <span class="input-group-btn" style="float: left;">
			<a href="${base}/system/ywDictionary/edit.htm?op_type=1">
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
        html.push("<a href='${base}/system/ywDictionary/edit.htm?dic_key="+ row["dic_key"] +"&dic_subkey="+ row["dic_subkey"] +"&op_type=2' class='tablelink'>修改</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["dic_key"]+"','"+row["dic_subkey"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(dic_key,dic_subkey){
 	    devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/system/ywDictionary/delete.json",
 		     data:{"dic_key":dic_key,"dic_subkey":dic_subkey},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/system/ywDictionary.htm";
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
	   var ywDictionaryForm = []
	   var dic_key = []
	   var dic_subkey = []
	   ywDictionaryForm = getAllCheckboxs().split(",");
	   
	   for(var i=0;i<ywDictionaryForm.length;i++){
	   		dic_key.push(ywDictionaryForm[i].split("-")[0])
	   		dic_subkey.push(ywDictionaryForm[i].split("-")[1])
	   }
	   devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
	   $.ajax({
			 type:"post",
			 url:"${base}/system/ywDictionary/deleteBatch.json",
			 data:{"dic_key":dic_key.toString() , "dic_subkey":dic_subkey.toString()},
			 dataType:"json",
			 success:function(data){
				if(data.error_no == "0"){
				   devActAfterShowDialog(data.error_info,function(){
					  location.href = "${base}/system/ywDictionary.htm";
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