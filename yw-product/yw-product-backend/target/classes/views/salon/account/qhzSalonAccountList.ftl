<@screen id="qhzSalonAccount" title="沙龙注册用户信息表页面" places=[{"name":"沙龙注册用户信息表","href":"#"}]>
<@ajaxQueryTable id="qhzSalonAccount_list" auto=true isMultiple=true multipleCondition=["id"]
	action="${base}/qhz_salon_account/qhzSalonAccount/getList.json" 
	paginatorUrl="${base}/qhz_salon_account/qhzSalonAccount/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
	       {"id":"id", "name":"编号"},
	       {"id":"openid", "name":"openid"},
	       {"id":"name", "name":"姓名"},
	       {"id":"tel", "name":"手机号"},
	       {"id":"card", "name":"身份证"},
	       {"id":"company", "name":"公司名"},
	       {"id":"adviser", "name":"学习顾问"},
	       {"id":"create_datetime", "name":"创建时间"},
	       {"id":"update_datetime", "name":"更新时间"},
	       {"id":"caozuo", "name":"操作", "provider":"dealStatus"}
		
	]>

	
	<@innerOfQueryCondition>
	    <dl class="search-dl">
	    	<dd>
  				<span class="dl-title">姓名：</span>
    			<label class="w-170">
      			 <input id="name" name="name" type="text" class="yw-input" value="" placeholder="请输入姓名"/ >
    			</label>
  			</dd>
  			<dd>
  				<span class="dl-title">手机号：</span>
    			<label class="w-170">
      			 <input id="tel" name="tel" type="text" class="yw-input" value="" placeholder="请输入手机号"/ >
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<@innerOfQueryTable>
	<div class="right-btn-box">
		<#--
	    <span class="input-group-btn" style="float: left;">
			<a href="${base}/qhz_salon_account/qhzSalonAccount/edit.htm?op_type=1">
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
        html.push("<a href='${base}/qhz_salon_account/qhzSalonAccount/edit.htm?id="+ row["id"]+"&op_type=2' class='tablelink'>查看</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(id){
 	    devActAfterConfirmAndClose("您确定要删除吗",function(){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/qhz_salon_account/qhzSalonAccount/delete.json",
 		     data:{"id":id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/qhz_salon_account/qhzSalonAccount.htm";
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
	  var qhzSalonAccountForm = []
	   var id = []
	   qhzSalonAccountForm = getAllCheckboxs().split(",");
	   for(var i=0;i<qhzSalonAccountForm.length;i++){
	   		id.push(qhzSalonAccountForm[i].split("-")[0])
	   }
	  devActAfterConfirmAndClose("您确定要批量删除吗",function(){
		   $.ajax({
				 type:"post",
				 url:"${base}/qhz_salon_account/qhzSalonAccount/deleteBatch.json",
				 data:{"id":id.toString()},
				 dataType:"json",
				 success:function(data){
					if(data.error_no == "0"){
					   devActAfterShowDialog(data.error_info,function(){
						  location.href = "${base}/qhz_salon_account/qhzSalonAccount.htm";
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