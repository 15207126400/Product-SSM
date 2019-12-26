<@screen id="ywCouponCollectiondetails" title="优惠券领取明细页面" places=[{"name":"优惠券领取明细","href":"#"}]>
<@ajaxQueryTable id="ywCouponCollectiondetails_list" auto=true isMultiple=true multipleCondition=["id"]
	action="${base}/sales/ywCouponCollectiondetails/getList.json" 
	paginatorUrl="${base}/sales/ywCouponCollectiondetails/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
	       {"id":"id", "name":"编号"},
	       {"id":"openid", "name":"用户昵称"},
	       {"id":"coupon_id", "name":"优惠券编号"},
	       {"id":"coupon_price", "name":"优惠券面额","provider":"formatCouponPrice"},
	       {"id":"coupon_function_type", "name":"作用类型","dic_key":"1018"},
	       {"id":"coupon_use_condition", "name":"使用条件","provider":"formatCouponUseCondition"},
	       {"id":"coupon_use_scope_type", "name":"使用范围","dic_key":"1086"},
	       {"id":"coupon_starttime", "name":"优惠券有效时间"},
	       {"id":"coupon_endtime", "name":"优惠券失效时间"},
	       {"id":"starttime", "name":"领取时间"},
	       {"id":"endtime", "name":"使用时间"},
	       {"id":"details_status", "name":"使用状态","dic_key":"1090"},
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
  				<span class="dl-title">使用状态：</span>
    			<label class="w-170">
      			 <@select id="details_status" name="details_status" class="yw-input" dic_key="1090" value="${details_status!''}" />
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<@innerOfQueryTable>
	<div class="right-btn-box">
		<#--后台暂时不支持直接发放优惠券到指定用户
	    <span class="input-group-btn" style="float: left;">
			<a href="${base}/sales/ywCouponCollectiondetails/edit.htm?op_type=1">
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
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(id){
 	    devActAfterConfirmAndClose("您确定要删除吗",function(){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/sales/ywCouponCollectiondetails/delete.json",
 		     data:{"id":id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/sales/ywCouponCollectiondetails.htm";
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
	  var ywCouponCollectiondetailsForm = []
	   var id = []
	   ywCouponCollectiondetailsForm = getAllCheckboxs().split(",");
	   for(var i=0;i<ywCouponCollectiondetailsForm.length;i++){
	   		id.push(ywCouponCollectiondetailsForm[i].split("-")[0])
	   }
	  devActAfterConfirmAndClose("您确定要批量删除吗",function(){
		   $.ajax({
				 type:"post",
				 url:"${base}/sales/ywCouponCollectiondetails/deleteBatch.json",
				 data:{"id":id.toString()},
				 dataType:"json",
				 success:function(data){
					if(data.error_no == "0"){
					   devActAfterShowDialog(data.error_info,function(){
						  location.href = "${base}/sales/ywCouponCollectiondetails.htm";
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