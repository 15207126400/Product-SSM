<@screen id="ywSignIn" title="签到页面" places=[{"name":"签到","href":"#"}]>
<@ajaxQueryTable id="ywSignIn_list" auto=true isMultiple=true multipleCondition=["signIn_id"] 
	action="${base}/signin/ywSignIn/getList.json" 
	paginatorUrl="${base}/signin/ywSignIn/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
	       {"id":"signIn_id", "name":"签到编号"},
	       {"id":"nickname", "name":"用户微信昵称"},
	       {"id":"signIn_last_date", "name":"上次签到日期"},
	       {"id":"signIn_continue_day", "name":"连续签到天数"},
	       {"id":"signIn_status", "name":"签到状态","dic_key":"1054"},
	       {"id":"signIn_updatetime", "name":"修改时间"},
	       {"id":"caozuo", "name":"操作", "provider":"dealStatus"}
		
	]>

	
	<@innerOfQueryCondition>
		<dl class="search-dl">
  			<dd>
  				<span class="dl-title">签到编号：</span>
    			<label class="w-170">
      			<input id="signIn_id" name="signIn_id" type="text" class="yw-input" value="" placeholder="请输入签到编号"/ >
    			</label>
  			</dd>
		</dl>
	    
	</@innerOfQueryCondition>
	
	<@innerOfQueryTable>
	<div class="right-btn-box">
		<#--
	    <span class="input-group-btn" style="float: left;">
			<a href="${base}/signin/ywSignIn/edit.htm?op_type=1">
				<button class="btn btn-success btn_text" type="button">添加</button>
			</a>
		</span>
		-->
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
        html.push("<a href='${base}/signin/ywSignIn/edit.htm?signIn_id="+ row["signIn_id"]+"&op_type=2' class='tablelink'>查看</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["signIn_id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(signIn_id){
 	    devActAfterConfirmAndClose("您确定要删除吗",function(){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/signin/ywSignIn/delete.json",
 		     data:{"signIn_id":signIn_id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/signin/ywSignIn.htm";
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
	  var ywSignInForm = []
	   var signIn_id = []
	   ywSignInForm = getAllCheckboxs().split(",");
	   for(var i=0;i<ywSignInForm.length;i++){
	   		signIn_id.push(ywSignInForm[i].split("-")[0])
	   }
	  devActAfterConfirmAndClose("您确定要批量删除吗",function(){
		   $.ajax({
				 type:"post",
				 url:"${base}/signin/ywSignIn/deleteBatch.json",
				 data:{"signIn_id":signIn_id.toString()},
				 dataType:"json",
				 success:function(data){
					if(data.error_no == "0"){
					   devActAfterShowDialog(data.error_info,function(){
						  location.href = "${base}/signin/ywSignIn.htm";
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