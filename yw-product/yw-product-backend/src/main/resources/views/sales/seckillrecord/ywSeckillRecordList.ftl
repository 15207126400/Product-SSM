<#assign base=request.contextPath />
<@screen id="ywSeckillRecord" title="秒杀记录" places=[{"name":"秒杀记录","href":"#"}]>
    <@ajaxQueryTable id="ywSeckillRecord_list" auto=true isMultiple=false multipleCondition=["record_id"]
	action="${base}/sales/ywSeckillRecord/getList.json" 
	paginatorUrl="${base}/sales/ywSeckillRecord/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
		{"id":"record_id", "name":"编号"},
		{"id":"user_nickname", "name":"用户昵称"},
		{"id":"user_headurl", "name":"用户头像" , "type":"img"},
		{"id":"order_sn", "name":"订单号"},
		{"id":"record_totalprice", "name":"总价（单位:元）"},
		{"id":"seckill_id", "name":"活动编号"},
		{"id":"seckill_num", "name":"数量（单位:件）"},
		{"id":"seckill_price", "name":"秒杀价（单位:元）"},
		{"id":"product_price", "name":"商品原价（单位:元）"},
		{"id":"user_palce", "name":"秒杀地点"},
		{"id":"record_createtime", "name":"创建时间"},
		{"id":"record_updatetime", "name":"更新时间"},
		{"id":"record_status", "name":"状态","dic_key":"1051"},
		{"id":"caozuo", "name":"操作", "provider":"dealStatus"}
	]>
	
	<#-- 查询条件 -->
	<@innerOfQueryCondition>
	   <dl class="search-dl">
  			<dd>
  				<span class="dl-title">编号：</span>
    			<label class="w-170">
      			 <input id="record_id" name="record_id" type="text" class="yw-input" value="" placeholder="请输入编号"/ >
    			</label>
  			</dd>
		</dl>
        	
     </@innerOfQueryCondition>
	
	<#-- 其他 -->
	<@innerOfQueryTable>
    <#--
    <div class="right-btn-box">
	    <span class="input-group-btn">
			<a href="${base}/sales/ywSeckillRecord/edit.htm?op_type=1">
				<button class="btn btn-success btn_text" type="button">新增</button>
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
<script type="text/javascript">

    function dealStatus(row, head){
	    var html = [];
        html.push("<a href='${base}/sales/ywSeckillRecord/edit.htm?record_id="+ row["record_id"] +"&op_type=2' class='tablelink'>查看</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["record_id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
        
    }
	// 删除
 	function deleteOne(record_id){
 	    devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/sales/ywSeckillRecord/delete.json",
 		     data:{"record_id":record_id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/sales/ywSeckillRecord.htm";
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
	   var ywSeckillRecordForm = []
	   var record_record_ids = []
	   ywSeckillRecordForm = getAllCheckboxs().split(",");
	   for(var i=0;i<ywSeckillRecordForm.length;i++){
	   		record_ids.push(ywSeckillRecordForm[i].split("-")[0])
	   }
	   devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
	   $.ajax({
			 type:"post",
			 url:"${base}/sales/ywSeckillRecord/deleteBatch.json",
			 data:{"record_ids":record_ids.toString()},
			 dataType:"json",
			 success:function(data){
				if(data.error_no == "0"){
				   devActAfterShowDialog(data.error_info,function(){
					  location.href = "${base}/sales/ywSeckillRecord.htm";
					},"suc");
				} else {
				   isTimeOutdevShowDialog(data.error_info,data.infos);
				}
			 }
		  })
	  })
	}
</script>
</@screen>