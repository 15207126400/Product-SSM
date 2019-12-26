<#assign base=request.contextPath />
<@screen id="ywProductClassifyInsert" title="商品分类页面" places=[{"name":"商品分类管理","href":"${base}/product/ywProductClassify.htm"},{"name":"添加/修改","href":"#"}]>
<form id="ywProductClassifyForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type!''}"/>
	<input id="classify_id" name="classify_id" type="hidden" class="dfinput" <#if op_type == "2">readonly="readonly"</#if> maxlength="18" value="${ywProductClassify.classify_id!''}"/>
	<div class="formtitle">
		<span class="formspan">商品分类</span>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>分类名称</label>
		<input id="classify_name" name="classify_name" type="text" placeholder="请输入分类名称" valid="NotBlank" class="dfinput" maxlength="255" value="${ywProductClassify.classify_name!''}"/>
		<i id="classify_name"></i>
	</div>
	<div id="img-box" class="form-group">
		<@fileSyncUpload isMust=true title="分类图片" imageUrl = ywProductClassify.classify_url/>
	</div>
	<div class="form-group">
		<label for="level" class="label">分类级别</label>
		<@select name="classify_level" valid="NotBlank" placeholder="请选择分类级别" dic_key="1080" value="${ywProductClassify.classify_level!''}"/>
	</div>
	<div id="p-menu" class="form-group">
		<label for="level" class="label">上级菜单</label>
		<@select class="tplus" name="parent_id" datalist=classifyList placeholder="请选择上级菜单" value="${ywProductClassify.parent_id!''}" optionData={"value":"classify_id","option":"classify_name"}/>
	</div>
	<div class="form-group">
		<label for="level" class="label">分类顺序</label>
		<input id="classify_order" name="classify_order" valid="Digital" placeholder="请输入分类顺序（整数：0.1.2...）" type="text" class="dfinput" maxlength="30" value="${ywProductClassify.classify_order!''}"/>
		<i id="classify_order"></i>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>状态</label>
		<@select name="classify_status" valid="NotBlank" placeholder="请选择状态" dic_key="1000" value="${ywProductClassify.classify_status!'1'}"/>
	</div>
	<div class="button-content">
	    <input type="button" class="btn btn_submit_text btn-confirm" value="确认" id="btn_ywProductClassify_submit"/>
		<input type="button" class="btn btn_submit_text btn-cancel" value="取消" id="btn_ywProductClassify_back"/>
	</div>
</form>	
<@script>
$(function(){
  if($("#op_type").val() == "1"){
  	$("#img-box").hide();
    $("#p-menu").hide();
  }else if($("#op_type").val() == "2"){
  	if($("#classify_level").val() == "1"){
  		$("#img-box").hide();
        $("#p-menu").hide();
  	}else if($("#classify_level").val() == "2"){
  		$("#img-box").show();
        $("#p-menu").show();
  	}
  }
  

  // 选择分类菜单级别
  $("#classify_level").change(function(){
  	if($(this).val() == "1"){
  		$("#img-box").hide();
  		$("#p-menu").hide();
  	}else if($(this).val() == "2"){
  		$("#img-box").show();
  		$("#p-menu").show();
  		$.ajax({
          url:"getClassifyLevel.json",
          type:"post",
          data:{"classify_level":parseInt($(this).val()) - 1},
          dataType:"json",
          success:function(data){
              if (data.error_no == 0) {
                getClassifyLevelAfter(data);
	          }else if(data.error_no == "-1"){
	                isTimeOutdevShowDialog(data.error_info,data.infos);
	          }else{
	                isTimeOutdevShowDialog(data.error_info,data.infos);
	          }
          },
          error:function(){
             
          }      
        });
  	  }
  })
  
  // 选择菜单级别处理
  function getClassifyLevelAfter(data){
	var classifyList = data.classifyList;  	
	var classifyListArray = [];
	$.each(classifyList,function(index,item){
      classifyListArray.push("<option value='"+ item.classify_id +"'>"+ item.classify_name +"</option>");
   })
   
   $("#parent_id").html(classifyListArray.join(" "));
  }

  // 确认
  $("#btn_ywProductClassify_submit").click(function(){
  
      // 表单校验
      if (!formValidate("ywProductClassifyForm")) {
		 return false;
	  }
      var formData = new FormData($("#ywProductClassifyForm")[0]);
      $.ajax({  
          url: $("#ywProductClassifyForm").attr("action") ,  
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
	                 location.href = "${base}/product/ywProductClassify.htm";
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
  $("#btn_ywProductClassify_back").click(function(){
      location.href = "${base}/product/ywProductClassify.htm";
  })
  
})
        
</@script>
</@screen>