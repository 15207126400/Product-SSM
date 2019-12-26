<@screen id="ywCoupon" title="优惠券配置信息页面" places=[{"name":"优惠券配置信息","href":"#"}]>
<@ajaxQueryTable id="ywCoupon_list" auto=true isMultiple=true multipleCondition=["id"]
	action="${base}/sales/ywCoupon/getList.json" 
	paginatorUrl="${base}/sales/ywCoupon/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
	       {"id":"id", "name":"编号"},
	       {"id":"coupon_name", "name":"优惠券名称"},
	       {"id":"coupon_scene_type", "name":"场景类型","dic_key":"1085"},
	       {"id":"coupon_function_type", "name":"作用类型","dic_key":"1018"},
	       {"id":"coupon_count", "name":"发放数量"},
	       {"id":"coupon_price", "name":"优惠券面额","provider":"formatCouponPrice"},
	       {"id":"coupon_use_condition", "name":"使用条件","provider":"formatCouponUseCondition"},
	       {"id":"coupon_collection_condition_type", "name":"领取条件","dic_key":"1019"},
	       {"id":"coupon_use_scope_type", "name":"使用范围","dic_key":"1086"},
	       {"id":"coupon_received_count", "name":"已领（单位：次）"},
	       {"id":"coupon_status", "name":"状态","dic_key":"1088"},
	       {"id":"caozuo", "name":"操作", "provider":"dealStatus"}
		
	]>

	
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">编号：</span>
    			<label class="w-170">
      			 <input id="id" name="id" type="text" class="yw-input" value="" placeholder="请输入编号"/ >
    			</label>
  			</dd>
		</dl>
		<dl class="search-dl">
  			<dd>
  				<span class="dl-title">优惠券作用类型：</span>
    			<label class="w-170">
      			 <@select id="coupon_function_type" name="coupon_function_type" class="yw-input" dic_key="1018" value="${coupon_function_type!''}" />
    			</label>
  			</dd>
		</dl>
		<dl class="search-dl">
  			<dd>
  				<span class="dl-title">优惠券场景类型：</span>
    			<label class="w-170">
      			 <@select id="coupon_scene_type" name="coupon_scene_type" class="yw-input" dic_key="1085" value="${coupon_scene_type!''}" />
    			</label>
  			</dd>
		</dl>
		<dl class="search-dl">
  			<dd>
  				<span class="dl-title">状态：</span>
    			<label class="w-170">
      			 <@select id="coupon_status" name="coupon_status" class="yw-input" dic_key="1088" value="${coupon_status!''}" />
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<@innerOfQueryTable>
	<div class="right-btn-box">
	    <span class="input-group-btn" style="float: left;">
			<a href="${base}/sales/ywCoupon/edit.htm?op_type=1">
				<button class="btn btn-success btn_text" type="button">添加</button>
			</a>
		</span>
		<span class="input-group-btn">
			<a href="javascript:void(0)" >
				<button class="btn btn-success btn_text" type="button" onclick="deleteBatch()">批量删除</button>
			</a>
		</span>
	</div>
   </@innerOfQueryTable>
</@ajaxQueryTable>
<@script>
    // 格式化优惠券面额
    function formatCouponPrice(row, head){
       var coupon_function_type =row["coupon_function_type"];
       var coupon_price =row["coupon_price"];
       if(coupon_function_type == "1"){
         return coupon_price +"元"; 
       } else if(coupon_function_type == "2"){
         return coupon_price +"折"; 
       } else if(coupon_function_type == "3"){
         return coupon_price +"元"; 
       } else if(coupon_function_type == "4"){
         return "通用"; 
       }
       
    }
    // 格式化优惠券使用条件
    function formatCouponUseCondition(row, head){
        var coupon_use_condition_type = row["coupon_use_condition_type"];
        var coupon_use_condition = row["coupon_use_condition"];
        if(coupon_use_condition_type == "0"){
           return "不限制";
        } else if(coupon_use_condition_type == "1"){
            return "满"+coupon_use_condition+"使用";
        }
    }
    function dealStatus(row, head){
        var html = [];
        html.push("<a href='${base}/sales/ywCoupon/edit.htm?id="+ row["id"]+"&op_type=2' class='tablelink'>修改</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(id){
 	    devActAfterConfirmAndClose("您确定要删除吗",function(){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/sales/ywCoupon/delete.json",
 		     data:{"id":id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/sales/ywCoupon.htm";
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
	  var ywCouponForm = []
	   var id = []
	   ywCouponForm = getAllCheckboxs().split(",");
	   for(var i=0;i<ywCouponForm.length;i++){
	   		id.push(ywCouponForm[i].split("-")[0])
	   }
	  devActAfterConfirmAndClose("您确定要批量删除吗",function(){
		   $.ajax({
				 type:"post",
				 url:"${base}/sales/ywCoupon/deleteBatch.json",
				 data:{"id":id.toString()},
				 dataType:"json",
				 success:function(data){
					if(data.error_no == "0"){
					   devActAfterShowDialog(data.error_info,function(){
						  location.href = "${base}/sales/ywCoupon.htm";
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