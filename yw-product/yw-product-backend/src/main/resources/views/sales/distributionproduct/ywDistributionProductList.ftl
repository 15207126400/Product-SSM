<#assign base=request.contextPath />
<@screen id="ywDistributionProduct" title="特殊分销商品页面" places=[{"name":"特殊分销商品","href":"#"}]>
<@ajaxQueryTable id="ywDistributionProduct_list" auto=true isMultiple=true multipleCondition=["dis_pro_id","product_id"]
	action="${base}/sales/ywDistributionProduct/getList.json" 
	paginatorUrl="${base}/sales/ywDistributionProduct/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
		{"id":"dis_pro_id", "name":"编号"},
		{"id":"product_name", "name":"商品名称"},
		{"id":"dis_pro_onescale", "name":"一级佣金比例(单位:%)"},
		{"id":"dis_pro_twoscale", "name":"二级佣金比例(单位:%)"},
		{"id":"product_id", "name":"商品编号"},
		{"id":"dis_pro_showlogo", "name":"分销商品logo","type":"img"},
		{"id":"dis_pro_createtime", "name":"发布时间"},
		{"id":"dis_pro_endtime", "name":"到期时间"},
		{"id":"dis_pro_status", "name":"状态","dic_key":"1000"},
		{"id":"dis_pro_saleatotal", "name":"销售总量(单位:件)"},
		{"id":"caozuo", "name":"操作", "provider":"dealStatus"}
	]>
	
	<#-- 查询条件 -->
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">商品编号：</span>
    			<label class="w-170">
      			<input id="product_id" name="product_id" type="text" class="yw-input" value="" placeholder="请输入商品编号"/ >
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<#-- 其他 -->
	<@innerOfQueryTable>
	<div class="right-btn-box">
	    <span class="input-group-btn">
			<a href="${base}/sales/ywDistributionProduct/edit.htm?op_type=1">
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
        html.push("<a href='${base}/sales/ywDistributionProduct/edit.htm?dis_pro_id="+ row["dis_pro_id"] +"&product_id="+ row["product_id"] +"&op_type=2' class='tablelink'>修改</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["dis_pro_id"]+"','"+row["product_id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(dis_pro_id,product_id){
 	    devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/sales/ywDistributionProduct/delete.json",
 		     data:{"dis_pro_id":dis_pro_id,"product_id":product_id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/sales/ywDistributionProduct.htm";
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
		var ywDistributionProductForm = []
		var dis_pro_id = []
		var product_id = []
		ywDistributionProductForm = getAllCheckboxs().split(",");
		for(var i=0;i<ywDistributionProductForm.length;i++){
			dis_pro_id.push(ywDistributionProductForm[i].split("-")[0])
			product_id.push(ywDistributionProductForm[i].split("-")[1])
		}
		
		console.log('dis_pro_id:'+dis_pro_id + ',product_id:'+product_id)
	   devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
	   $.ajax({
			 type:"post",
			 url:"${base}/sales/ywDistributionProduct/deleteBatch.json",
			 data:{"dis_pro_id":dis_pro_id.toString(),"product_id":product_id.toString()},
			 dataType:"json",
			 success:function(data){
				if(data.error_no == "0"){
				   devActAfterShowDialog(data.error_info,function(){
					  location.href = "${base}/sales/ywDistributionProduct.htm";
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