<#assign base=request.contextPath />
<@screen id="ywTeamActivityInsert" title="团购活动页面" places=[{"name":"团购活动","href":"${base}/sales/ywTeamActivity.htm"},{"name":"添加/修改","href":"#"}]>
<form id="ywTeamActivityForm" action="insertOrUpdate.json" method="post" enctype="multipart/form-data" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type!''}"/>
	<input id="team_id" name="team_id" type="hidden" class="dfinput"  value="${ywTeamActivity.team_id!''}"/>
	
	<div class="formtitle">
		<span class="formspan">团购活动信息</span>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>拼团活动标题</label>
		<input id="act_name" name="act_name" valid="NotBlank" placeholder="请输入拼团活动标题" type="text" class="dfinput" maxlength="18" value="${ywTeamActivity.act_name!''}"/>
		<i id="act_name"></i>
		
		<label for="level" class="label"><em style="color:red;">*</em>拼团商品</label>
		<@select2 placeholder="请选择拼团商品" name = "product_id" valid="NotBlank" value=ywTeamActivity.product_id datalist=products optionData={"value":"id","option":"title"}/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>拼团活动类型</label>
		<@select valid="NotBlank" placeholder="请选择拼团活动类型" name="team_type" dic_key="1022" value="${ywTeamActivity.team_type!''}" />
		
		<label for="level" class="label"><em style="color:red;">*</em>拼团价</label>
		<input id="team_price" name="team_price" valid="NotBlank,Digital" placeholder="请输入拼团价（单位：元）" type="text" class="dfinput" value="${ywTeamActivity.team_price!''}"/>
		<i></i>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>需要成团人数</label>
		<input id="needer" name="needer" valid="NotBlank,Digital" placeholder="请输入需要成团人数(单位：数字)" type="text" class="dfinput"   value="${ywTeamActivity.needer!''}"/>
		<i></i>
		
		<label for="level" class="label"><em style="color:red;">*</em>成团有效期</label>
		<input id="time_limit" name="time_limit" valid="NotBlank,Digital" placeholder="成团有效期（单位：秒）" type="text" class="dfinput"   value="${ywTeamActivity.time_limit!''}"/>
		<i></i>
	</div>
	<div class="form-group">
		<label for="level" class="label">单次团购买限制数</label>
		<input id="buy_limit" name="buy_limit" valid="Digital" placeholder="请输入单次团购买限制数(单位：数字)" type="text" class="dfinput"   value="${ywTeamActivity.buy_limit!''}"/>
		<i></i>
		
		<label for="level" class="label">虚拟已拼多少件</label>
		<input id="sales_sum" name="sales_sum" valid="Digital" placeholder="请输入虚拟已拼多少件(单位:数字)" type="text" class="dfinput"   value="${ywTeamActivity.sales_sum!''}"/>
		<i></i>
	</div>
	<div class="form-group">
		<label for="level" class="label">虚拟销售基数</label>
		<input id="virtual_num" name="virtual_num" valid="Digital" placeholder="请输入虚拟销售基数(单位:数字)" type="text" class="dfinput"   value="${ywTeamActivity.virtual_num!''}"/>
		<i></i>
		
		<label for="level" class="label"><em style="color:red;">*</em>团长佣金</label>
		<input id="bonus" name="bonus" placeholder="请输入团长佣金" type="text" class="dfinput" value="${ywTeamActivity.bonus!''}"/>
	</div>
	<#-- 
	<div class="form-group">
		<label for="level" class="label">抽奖限量</label>
		<input id="stock_limit" name="stock_limit" placeholder="请输入抽奖限量" type="text" class="dfinput" value="${ywTeamActivity.stock_limit!''}"/>
		
		<label for="level" class="label">是否已经抽奖</label>
		<@select placeholder="请输入是否已经抽奖" name="is_lottery" dic_key="1028" value="${ywTeamActivity.is_lottery!''}"/>
	</div>-->
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>分享标题</label>
		<input id="share_title" name="share_title" placeholder="请输入分享标题" type="text" class="dfinput"   value="${ywTeamActivity.share_title!''}"/>
		
		<label for="level" class="label"><em style="color:red;">*</em>分享描述</label>
		<input id="share_desc" name="share_desc" placeholder="请输入分享描述" type="text" class="dfinput"   value="${ywTeamActivity.share_desc!''}"/>
	</div>
	<div class="form-group team_type1" style="display:none">
		<@fileSyncUpload isMust=true title="分享图片" imageUrl = ywTeamActivity.share_img/>
	</div>
	<div class="form-group">
		<label for="level" class="label">排序</label>
		<input id="sort" name="sort" placeholder="请输入排序" type="text" class="dfinput" value="${ywTeamActivity.sort!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>是否推荐</label>
		<@select valid="NotBlank" placeholder="请选择是否推荐" name="is_recommend" dic_key="1027" value="${ywTeamActivity.is_recommend!''}"/>
		
		<label for="level" class="label"><em style="color:red;">*</em>状态</label>
		<@select valid="NotBlank" placeholder="请选择状态" name="status" dic_key="1023" value="${ywTeamActivity.status!''}"/>
	</div>
	<div class="button-content">
	    <input type="button" class="btn btn_submit_text btn-confirm" value="确认" id="btn_ywTeamActivity_submit"/>
		<input type="button" class="btn btn_submit_text btn-cancel" value="取消" id="btn_ywTeamActivity_back"/>
	</div>
	
</form>	
<@script>
$(function(){
  // 提交确认
  $("#btn_ywTeamActivity_submit").click(function(){
      // 表单校验
      if (!formValidate("ywTeamActivityForm")) {
		 return false;
	  }
       var formData = new FormData($("#ywTeamActivityForm")[0]);
      //var formData = $("#ywTeamActivityForm").serializeArray();
      $.ajax({  
          url: $("#ywTeamActivityForm").attr("action") ,  
          type: 'POST',  
          data: formData,  
          async: false,  
          cache: false,  
          contentType: false,  
          processData: false,  
          success: function (data) {  
              var openType=${op_type};
	            var operateType={"0":"添加","1":"修改"};
	            if (data.error_no == 0) {
	                devActAfterShowDialog(data.error_info,function(){
	                 location.href = "${base}/sales/ywTeamActivity.htm";
	                },"suc");
	            } else {
	                isTimeOutdevShowDialog(data.error_info,data.infos);
	            };
          },  
          error: function (data) {  
             devShowDialog("系统异常");
          }  
     });
  })
  
  // 取消
  $("#btn_ywTeamActivity_back").click(function(){
      location.href = "${base}/sales/ywTeamActivity.htm";
  })
  
  // 修改页面联动显示
  var team_type_flag = "${ywTeamActivity.team_type!''}";
  var share_img = "${ywTeamActivity.share_img!''}";
  if(team_type_flag == "1"){
     $(".form .team_type1").show();
     // 判断是否显示图片
     if(share_img != ""){
        $("#yulan").show();
        $("#f").text("文件格式正确");
        $("#f").css("color","green");
        $('#preview').empty().append("<img src='"+ share_img +"'/>");
     }
  } else if(team_type_flag == "2"){
     $(".form .team_type2").show();
  } else if(team_type_flag == "3"){
     $(".form .team_type3").show();
  }
  
  //拼团活动类型联动选择
  $("#team_type").change(function(){
     if($(this).val() == "1"){
        $(".form .team_type1").show();
        $(".form .team_type2").hide().find("input").val("");
        $(".form .team_type3").hide().find("input").val("");
     } else if ($(this).val() == "2"){
        //$(".form .team_type1").hide().find("input").val("");
        $(".form .team_type2").show();
        $(".form .team_type3").hide().find("input").val("");
        $("#yulan").hide();
     } else if($(this).val() == "3"){
        //$(".form .team_type1").hide().find("input").val("");
        $(".form .team_type2").hide().find("input").val("");
        $(".form .team_type3").show();
        $("#yulan").hide();
     }
  
  })
})


</@script>
</@screen>