<@screen id="ywCouponCollectiondetailsInsert" title="优惠券领取明细页面" places=[{"name":"优惠券领取明细","href":"${base}/sales/ywCouponCollectiondetails.htm"},{"name":"添加/修改","href":"#"}]>
<form id="ywCouponCollectiondetailsForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type}"/>
    <input id="id" name="id" type="hidden" value="${ywCouponCollectiondetails.id!''}"/>
	<div class="formtitle">
		<span class="formspan">优惠券领取明细</span>
	</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>用户openid</label>
					<input id="openid" name="openid" type="text" valid="NotBlank" class="dfinput" placeholder="请输入用户openid" maxbytelength="32" value="${ywCouponCollectiondetails.openid!''}"/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>优惠券编号</label>
					<input id="coupon_id" name="coupon_id" type="text" valid="NotBlank" class="dfinput" placeholder="请输入优惠券编号" maxbytelength="11" value="${ywCouponCollectiondetails.coupon_id!''}"/>
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>优惠券面额</label>
					<input id="coupon_price" name="coupon_price" type="text" valid="NotBlank" class="dfinput" placeholder="请输入优惠券面额" maxbytelength="11" value="${ywCouponCollectiondetails.coupon_price!''}"/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>作用类型</label>
					<input id="coupon_function_type" name="coupon_function_type" type="text" valid="NotBlank" class="dfinput" placeholder="请输入优惠券作用类型(1满减卷,2折扣劵,3抵扣劵,4免邮券)" maxbytelength="2" value="${ywCouponCollectiondetails.coupon_function_type!''}"/>
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>使用条件类型</label>
					<input id="coupon_use_condition_type" name="coupon_use_condition_type" type="text" valid="NotBlank" class="dfinput" placeholder="请输入优惠券使用条件类型（0、不限制，1、限制）" maxbytelength="1" value="${ywCouponCollectiondetails.coupon_use_condition_type!''}"/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>使用条件</label>
					<input id="coupon_use_condition" name="coupon_use_condition" type="text" valid="NotBlank" class="dfinput" placeholder="请输入优惠券使用条件（满n元可用）" maxbytelength="11" value="${ywCouponCollectiondetails.coupon_use_condition!''}"/>
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>使用范围类型</label>
					<input id="coupon_use_scope_type" name="coupon_use_scope_type" type="text" valid="NotBlank" class="dfinput" placeholder="请输入优惠券使用范围类型（0、全场,1、单商品，2、全品类）" maxbytelength="2" value="${ywCouponCollectiondetails.coupon_use_scope_type!''}"/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>使用范围</label>
					<input id="coupon_use_scope" name="coupon_use_scope" type="text" valid="NotBlank" class="dfinput" placeholder="请输入优惠券使用范围" maxbytelength="11" value="${ywCouponCollectiondetails.coupon_use_scope!''}"/>
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>有效时间</label>
					<input id="coupon_starttime" name="coupon_starttime" type="text" valid="NotBlank" class="dfinput" placeholder="请输入优惠券有效时间" maxbytelength="19" value="${ywCouponCollectiondetails.coupon_starttime!''}"/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>失效时间</label>
					<input id="coupon_endtime" name="coupon_endtime" type="text" valid="NotBlank" class="dfinput" placeholder="请输入优惠券失效时间" maxbytelength="19" value="${ywCouponCollectiondetails.coupon_endtime!''}"/>
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>领取时间</label>
					<input id="starttime" name="starttime" type="text" valid="NotBlank" class="dfinput" placeholder="请输入领取时间" maxbytelength="19" value="${ywCouponCollectiondetails.starttime!''}"/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>使用时间</label>
					<input id="endtime" name="endtime" type="text" valid="NotBlank" class="dfinput" placeholder="请输入使用时间" maxbytelength="19" value="${ywCouponCollectiondetails.endtime!''}"/>
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>状态</label>
					<input id="details_status" name="details_status" type="text" valid="NotBlank" class="dfinput" placeholder="请输入优惠券明细状态（0：未使用，1、已使用，2、已过期）" maxbytelength="1" value="${ywCouponCollectiondetails.details_status!''}"/>
			</div>
	<div class="button-content">
	    <input type="button" class="btn btn-success btn_text" value="确认" id="btn_ywCouponCollectiondetails_submit"/>
		<input type="button" class="btn btn-success btn_text" value="取消" id="btn_ywCouponCollectiondetails_back"/>
	</div>
</form>	
<@script>
$(function(){
  // 确认
  $("#btn_ywCouponCollectiondetails_submit").click(function(){
      // 表单校验
      if (!formValidate("ywCouponCollectiondetailsForm")) {
		 return false;
	  }
      var formData = $("#ywCouponCollectiondetailsForm").serializeArray();
      $.post($("#ywCouponCollectiondetailsForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
               devActAfterShowDialog(data.error_info,function(){
                 location.href = "${base}/sales/ywCouponCollectiondetails.htm";
                },"suc");
            } else if(data.error_no == "-1"){
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }else{
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }
        })
  })
  
  // 取消
  $("#btn_ywCouponCollectiondetails_back").click(function(){
      location.href = "${base}/sales/ywCouponCollectiondetails.htm";
  })
  
})
</@script>
</@screen>