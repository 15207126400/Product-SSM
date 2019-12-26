<#assign base=request.contextPath />
<@screen id="ywDistributorSalestatus" title="系统字典页面" places=[{"name":"分销销售信息","href":"#"}]>
<@ajaxQueryTable id="ywDistributorSalestatus_list" auto=true isMultiple=false multipleCondition=["dis_st_id"]
	action="${base}/sales/ywDistributorSalestatus/getList.json" 
	paginatorUrl="${base}/sales/ywDistributorSalestatus/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
	    {"id":"dis_st_id", "name":"编号"},
		{"id":"product_id", "name":"商品编号"},
		{"id":"share_nickname", "name":"分销人"},
		{"id":"dis_level", "name":"分销人等级","dic_key":"1008"},
		{"id":"dis_st_amont", "name":"销售数量(单位:件)"},
		{"id":"dis_st_moeny", "name":"销售金额(单位:元)"},
		{"id":"dis_st_datetime", "name":"销售时间"},
		{"id":"buy_nickname", "name":"购买者"},
		{"id":"dis_st_palce", "name":"销售地点"}
		<#--
		{"id":"caozuo", "name":"操作", "provider":"dealStatus"}
		-->
	]>
	
	<#-- 查询条件 -->
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">编号：</span>
    			<label class="w-170">
      			 <input id="dis_st_id" name="dis_st_id" type="text" class="yw-input" value="" placeholder="请输入编号"/ >
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	
	<@innerOfQueryTable>
	<#-- 其他 
	<div class="right-btn-box">
	    <span class="input-group-btn">
			<a href="${base}/sales/ywDistributorSalestatus/edit.htm?op_type=1">
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
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["dis_st_id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(pro_id,dis_id){
 	    if(confirm("您确定要删除吗?")){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/sales/ywDistributorSalestatus/delete.json",
 		     data:{"dis_st_id":dis_st_id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/sales/ywDistributorSalestatus.htm";
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
			 url:"${base}/sales/ywDistributorSalestatus/deleteBatch.json",
			 data:{"dis_st_ids":getAllCheckboxs()},
			 dataType:"json",
			 success:function(data){
				if(data.error_no == "0"){
				   devActAfterShowDialog(data.error_info,function(){
					  location.href = "${base}/sales/ywDistributorSalestatus.htm";
					},"suc");
				} else {
				   isTimeOutdevShowDialog(data.error_info,data.infos);
				}
			 }
		  })
		}
 	
</@script>
</@screen>