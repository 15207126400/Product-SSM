<#assign base=request.contextPath />
<@screen id="ywDistributorCommissionrecord" title="佣金记录页面" places=[{"name":"佣金记录","href":"#"}]>
<@ajaxQueryTable id="ywDistributorCommissionrecord_list" auto=true isMultiple=false multipleCondition=["dis_com_id"]
	action="${base}/sales/ywDistributorCommissionrecord/getList.json" 
	paginatorUrl="${base}/sales/ywDistributorCommissionrecord/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
	    {"id":"dis_com_id", "name":"编号"},
	    {"id":"dis_com_type", "name":"佣金类型","dic_key":"1037"},
		{"id":"nickname", "name":"用户昵称"},
		{"id":"order_id", "name":"订单编号"},
		{"id":"dis_com_money", "name":"佣金金额(单位:元)"},
		{"id":"dis_com_createtime", "name":"创建时间"},
		{"id":"dis_com_updatetime", "name":"更新时间"},
		{"id":"dis_com_remark", "name":"佣金备注"},
		{"id":"dis_com_status", "name":"状态","dic_key":"1038"}
		<#--{"id":"caozuo", "name":"操作", "provider":"dealStatus"}-->
	]>
	
	<#-- 查询条件 -->
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">编号：</span>
    			<label class="w-170">
      			 <input id="dis_com_id" name="dis_com_id" type="text" class="yw-input" value="" placeholder="请输入编号"/ >
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	
	<@innerOfQueryTable>
	<div class="right-btn-box">
		<#-- 其他 
	    <span class="input-group-btn">
			<a href="${base}/sales/ywDistributorCommissionrecord/edit.htm?op_type=1">
				<button class="btn btn-success btn_text" type="button">添加</button>
			</a>
		</span>
		<span class="input-group-btn">
			<a href="javascript:void(0)" >
				<button class="btn btn-success btn_text" type="button" onclick="operationCondition()">批量删除</button>
			</a>
		</span>
		-->
	</div>
    </@innerOfQueryTable>
    
</@ajaxQueryTable>
<@script>
    function dealStatus(row, head){
        var html = [];
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["dis_com_id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(pro_id,user_id){
 	    if(confirm("您确定要删除吗?")){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/sales/ywDistributorCommissionrecord/delete.json",
 		     data:{"dis_com_id":dis_com_id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/sales/ywDistributorCommissionrecord.htm";
	                },"suc");
 		        } else {
 		           isTimeOutdevShowDialog(data.error_info,data.infos);
 		        }
 		     }
 		  })
 	   }
 	}
 	
 	// 批量删除
	function operationCondition(){
	   $.ajax({
			 type:"post",
			 url:"${base}/sales/ywDistributorCommissionrecord/deleteBatch.json",
			 data:{"dis_com_ids":getAllCheckboxs()},
			 dataType:"json",
			 success:function(data){
				if(data.error_no == "0"){
				   devActAfterShowDialog(data.error_info,function(){
					  location.href = "${base}/sales/ywDistributorCommissionrecord.htm";
					},"suc");
				} else {
				   isTimeOutdevShowDialog(data.error_info,data.infos);
				}
			 }
		  })
		}
 	
</@script>
</@screen>