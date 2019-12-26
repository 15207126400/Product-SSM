<#assign base=request.contextPath />
<@screen id="ywDistributorWithdrawalrecordInsert" title="拒接提现申请备注" places=[{"name":"拒接提现申请","href":"${base}/sales/ywDistributorWithdrawalrecord.htm"},{"name":"添加/修改","href":"#"}]>
	<form id="ywDistributorWithdrawalrecordForm" action="insertOrUpdate.json" method="post" class="form">
		<input type="hidden" id="op_type" name="op_type" value="${op_type!''}"/>
		<input name="dis_wid_id" type="hidden" class="dfinput" value="${ywDistributorWithdrawalrecord.dis_wid_id!'' }"/>	
		<input id="dis_wid_status" name="dis_wid_status" type="hidden" class="dfinput" value="3"/>
		<div class="formtitle">
			<span class="formspan">不同意提现</span>
		</div>
		<div class="form-group">
			<label for="level" class="label">输入原因</label>
			<input id="dis_wid_remark" name="dis_wid_remark" placeholder="请输入原因" type="text" class="dfinput" value="${ywDistributorWithdrawalrecord.dis_wid_remark!'' }"/>
		</div>
		<div class="button-content">
		    <input type="button" class="btn btn-success btn_text" value="确定" id="btn_ywDistributorWithdrawalrecord_submit"/>
		    <input type="button" class="btn btn-success btn_text" value="取消" id="btn_ywDistributorWithdrawalrecord_back"/>
		</div>	
	</form>
<script type="text/javascript">
    $(function(){
      // 确认
	  $("#btn_ywDistributorWithdrawalrecord_submit").click(function(){
	      var formData = $("#ywDistributorWithdrawalrecordForm").serializeArray();
	      $.post($("#ywDistributorWithdrawalrecordForm").attr("action"), formData, function(data){
	            var openType=${op_type};
	            var operateType={"0":"添加","1":"修改"};
	            if (data.error_no == 0) {
	                devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/sales/ywDistributorWithdrawalrecord.htm";
	                },"suc");
	            } else {
	                isTimeOutdevShowDialog(data.error_info,data.infos);
	            };
	        })
	  })
	  
	  // 取消
	  $("#btn_ywDistributorWithdrawalrecord_back").click(function(){
	      location.href = "${base}/product/ywDistributorWithdrawalrecord.htm";
	  })

	})
</script>	
</@screen>