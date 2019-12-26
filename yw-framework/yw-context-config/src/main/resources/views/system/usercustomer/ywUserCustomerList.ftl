<#assign base=request.contextPath />
<@screen id="ywUserCustomer" title="用户信息页面" places=[{"name":"用户信息管理","href":"#"}]>
<@ajaxQueryTable id="ywUserCustomer_list" auto=true isMultiple=true multipleCondition=["user_id"]
	action="${base}/system/ywUserCustomer/getList.json" 
	paginatorUrl="${base}/system/ywUserCustomer/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
		{"id":"user_id", "name":"用户编号"},
		{"id":"cust_name", "name":"真实姓名"},
		{"id":"mobile_tel", "name":"手机号码"},
		{"id":"e_mail", "name":"邮箱"},
		{"id":"sex", "name":"性别","dic_key":"1011"},
		{"id":"birthday", "name":"生日"},
		{"id":"city", "name":"所在区域","dic_key":"1049"},
		{"id":"address", "name":"详细地址"},
		{"id":"industry_type", "name":"行业","dic_key":"1048"},
		{"id":"company_name", "name":"公司名称"},
		{"id":"create_datetime", "name":"创建时间"},
		{"id":"update_datetime", "name":"修改时间"},
		{"id":"pay_datetime", "name":"付费时间"},
		{"id":"expire_datetime", "name":"到期时间"},
		{"id":"ro_names", "name":"用户角色"},
		{"id":"caozuo", "name":"操作", "provider":"dealStatus"}
	]>
	
	<#-- 查询条件 -->
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">用户名：</span>
    			<label class="w-170">
    				<@autocomplete size="10" maxHeight="250" noCache="false" inputId="username" tableName="yw_system.yw_user" column="username">
      					<input id="username" name="username" type="text" class="yw-input" value="" placeholder="请输入用户名"/ >
      				</@autocomplete>
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<#-- 其他 -->
	<@innerOfQueryTable>
	<div class="right-btn-box">
	    <span class="input-group-btn">
			<a href="${base}/system/ywUserCustomer/edit.htm?op_type=1">
				<button class="btn btn-success btn_text" type="button">添加用户</button>
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
        html.push("<a href='${base}/system/ywUserCustomer/edit.htm?user_id="+ row["user_id"]+"&op_type=2' class='tablelink'>授权</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["user_id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
    
	// 删除
 	function deleteOne(user_id){
 	    devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/system/ywUserCustomer/delete.json",
 		     data:{
 		     	"user_id":user_id
 		     },
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/system/ywUserCustomer.htm";
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
	   var ywUserCustomerForm = []
	   var user_ids = []
	   ywUserCustomerForm = getAllCheckboxs().split(",");
	   for(var i=0;i<ywUserCustomerForm.length;i++){
	   		user_ids.push(ywUserCustomerForm[i].split("-")[0])
	   }
	   devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
	   $.ajax({
			 type:"post",
			 url:"${base}/system/ywUserCustomer/deleteBatch.json",
			 data:{"user_ids":user_ids.toString()},
			 dataType:"json",
			 success:function(data){
				if(data.error_no == "0"){
				   devActAfterShowDialog(data.error_info,function(){
					  location.href = "${base}/system/ywUserCustomer.htm";
					},"suc");
				} else {
				   isTimeOutdevShowDialog(data.error_info,data.infos);
				}
			 }
		  })
	  })
	}
 	
 	$(function(){
 	    $('.tablelist tbody tr:odd').addClass('odd');
 	})
    
</@script>
</@screen>