<#assign base=request.contextPath />
<@screen id="ywSeckillActivity" title="秒杀活动页面" places=[{"name":"秒杀活动","href":"#"}]>
<@ajaxQueryTable id="ywSeckillActivity_list" auto=true isMultiple=true multipleCondition=["seckill_id"]
	action="${base}/sales/ywSeckillActivity/getList.json" 
	paginatorUrl="${base}/sales/ywSeckillActivity/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
		{"id":"seckill_id", "name":"编号"},
		{"id":"seckill_name", "name":"标题"},
		{"id":"seckill_url", "name":"秒杀图片","type":"img"},
		{"id":"seckill_price", "name":"秒杀价格(单位:元)"},
		{"id":"sales_sum", "name":"已抢购（单位：件）"},
		{"id":"sales_percent", "name":"已抢购（单位：%）"},
		{"id":"start_datetime", "name":"开始时间"},
		{"id":"seckill_endtime", "name":"结束时间"},
		{"id":"title", "name":"商品名称"},
		{"id":"seckill_istop", "name":"是否置顶","dic_key":"1052"},
		{"id":"seckill_status", "name":"状态","dic_key":"1000"},
		{"id":"caozuo", "name":"操作", "provider":"dealStatus"}
	]>
	
	<#-- 查询条件 -->
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">编号：</span>
    			<label class="w-170">
	    			<@autocomplete size="10" maxHeight="250" noCache="false" inputId="seckill_id" tableName="yw_system.yw_seckill_activity" column="seckill_id">
	      			 	<input id="seckill_id" name="seckill_id" type="text" class="yw-input" value="" placeholder="请输入编号"/ >
	      			</@autocomplete>
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<#-- 其他 -->
	<@innerOfQueryTable>
	<div class="right-btn-box">
	    <span class="input-group-btn" style="float: left;">
			<a href="${base}/sales/ywSeckillActivity/edit.htm?op_type=1">
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
        html.push("<a href='${base}/sales/ywSeckillActivity/edit.htm?seckill_id="+ row["seckill_id"] +"&op_type=2' class='tablelink'>修改</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["seckill_id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(seckill_id){
 	    devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/sales/ywSeckillActivity/delete.json",
 		     data:{"seckill_id":seckill_id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/sales/ywSeckillActivity.htm";
	                },"suc");
 		        } else {
 		           isTimeOutdevShowDialog(data.error_info,data.infos);
 		        }
 		     }
 		  })
 	   });
 	}
 	
 	// 批量删除
	function operationCondition(){
	   var ywSeckillActivityForm = []
	   var seckill_id = []
	   ywSeckillActivityForm = getAllCheckboxs().split(",");
	   for(var i=0;i<ywSeckillActivityForm.length;i++){
	   		seckill_id.push(ywSeckillActivityForm[i].split("-")[0])
	   }
	   devActAfterConfirmAndClose("您确定要批量删除吗 ?",function(){
		   $.ajax({
				 type:"post",
				 url:"${base}/sales/ywSeckillActivity/deleteBatch.json",
				 data:{"seckill_id":seckill_id.toString()},
				 dataType:"json",
				 success:function(data){
					if(data.error_no == "0"){
					   devActAfterShowDialog(data.error_info,function(){
						  location.href = "${base}/sales/ywSeckillActivity.htm";
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