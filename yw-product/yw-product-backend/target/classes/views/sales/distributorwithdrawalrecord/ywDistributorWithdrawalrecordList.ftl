<#assign base=request.contextPath />
<@screen id="ywDistributorWithdrawalrecord" title="提现记录页面" places=[{"name":"提现记录","href":"#"}]>
<@ajaxQueryTable id="ywDistributorWithdrawalrecord_list" auto=true isMultiple=false multipleCondition=["dis_wid_id"]
	action="${base}/sales/ywDistributorWithdrawalrecord/getList.json" 
	paginatorUrl="${base}/sales/ywDistributorWithdrawalrecord/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
	    {"id":"dis_wid_id", "name":"编号"},
	    {"id":"dis_wid_type", "name":"提现类型","dic_key":"1035"},
		{"id":"nickname", "name":"用户昵称"},
		{"id":"dis_wid_money", "name":"提现金额(单位:元)"},
		{"id":"dis_wid_createtime", "name":"创建时间"},
		{"id":"dis_wid_updatetime", "name":"修改时间"},
		{"id":"dis_wid_remark", "name":"提现备注"},
		{"id":"dis_wid_status", "name":"状态","dic_key":"1036"},
		{"id":"caozuo", "name":"操作", "provider":"dealStatus"}
	]>
	
	<#-- 查询条件 -->
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">编号：</span>
    			<label class="w-170">
      			 <input id="dis_wid_id" name="dis_wid_id" type="text" class="yw-input" value="" placeholder="请输入编号"/ >
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	
	<@innerOfQueryTable>
	<#-- 其他 
	<div class="right-btn-box">
	    <span class="input-group-btn">
			<a href="${base}/sales/ywDistributorWithdrawalrecord/edit.htm?op_type=1">
				<button class="btn btn-success btn_text" type="button">添加</button>
			</a>
		</span>
		<span class="input-group-btn">
			<a href="javascript:void(0)" >
				<button class="btn btn-success btn_text" type="button" onclick="operationCondition()">批量删除</button>
			</a>
		</span>
	</div>
	-->
    </@innerOfQueryTable>
    
</@ajaxQueryTable>
<@script>
    function dealStatus(row, head){
        var html = [];
        if(row["dis_wid_status"] == "1"){
        	html.push("<a href='javascript:void(0)' onclick=\"updateOne('"+row["dis_wid_id"]+"','2')\" class='tablelink'>同意</a>");
        	html.push("<a href='${base}/sales/ywDistributorWithdrawalrecord/edit.htm?dis_wid_id="+ row["dis_wid_id"] +"&op_type=3' class='tablelink'>不同意</a>");
        }else{
        	html.push("<a href='${base}/sales/ywDistributorWithdrawalrecord/edit.htm?dis_wid_id="+ row["dis_wid_id"] +"&op_type=2' class='tablelink'>查看</a>");
        }
   		return html.join(" ");
    }
    
    // 同意提现
 	function updateOne(dis_wid_id,dis_wid_status){
 		devActAfterConfirmAndClose("确定同意提现吗?",function(){
 			$.ajax({
		     type:"post",
		     url:"${base}/sales/ywDistributorWithdrawalrecord/insertOrUpdate.json",
		     data:{"dis_wid_id":dis_wid_id,"dis_wid_status":dis_wid_status,"dis_wid_remark":"提现成功","op_type":"2"},
		     dataType:"json",
		     success:function(data){
		        if(data.error_no == "0"){
		           devActAfterShowDialog(data.error_info,function(){
					  location.href = "${base}/sales/ywDistributorWithdrawalrecord.htm";
					},"suc");
		        } else {
		           isTimeOutdevShowDialog(data.error_info,data.infos);
		        }
		     }
		   })
 		})
   	}
   	
   	
	
    // 删除
 	function deleteOne(pro_id,user_id){
 	    if(confirm("您确定要删除吗?")){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/sales/ywDistributorWithdrawalrecord/delete.json",
 		     data:{"dis_wid_id":dis_wid_id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           alert(data.error_info);
 		           location.href = "${base}/sales/ywDistributorWithdrawalrecord.htm";
 		        } else {
 		           alert("删除失败");
 		        }
 		     }
 		  })
 	   }
 	}
 	
 	// 批量删除
	function operationCondition(){
	   $.ajax({
			 type:"post",
			 url:"${base}/sales/ywDistributorWithdrawalrecord/deleteBatch.json",
			 data:{"dis_wid_ids":getAllCheckboxs()},
			 dataType:"json",
			 success:function(data){
				if(data.error_no == "0"){
				   devActAfterShowDialog(data.error_info,function(){
					  location.href = "${base}/sales/ywDistributorWithdrawalrecord.htm";
					},"suc");
				} else {
				   isTimeOutdevShowDialog(data.error_info,data.infos);
				}
			 }
		  })
		}
 	
</@script>
</@screen>