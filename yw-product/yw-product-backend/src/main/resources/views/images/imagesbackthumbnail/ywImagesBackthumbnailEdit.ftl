<@screen id="ywImagesBackthumbnailInsert" title="后台缩略图中心中心页面" places=[{"name":"后台缩略图中心中心","href":"${base}/images/ywImagesBackthumbnail.htm"},{"name":"添加/修改","href":"#"}]>
<form id="ywImagesBackthumbnailForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type}"/>
    <input id="id" name="id" type="hidden" value="${ywImagesBackthumbnail.id!''}"/>
	<div class="formtitle">
		<span class="formspan">后台缩略图中心中心</span>
	</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>缩略图名称</label>
					<input id="thumbnail_name" name="thumbnail_name" type="text" valid="NotBlank" class="dfinput" placeholder="请输入缩略图名称" maxbytelength="255" value="${ywImagesBackthumbnail.thumbnail_name!''}"/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>缩略图类型</label>
					<input id="thumbnail_type" name="thumbnail_type" type="text" valid="NotBlank" class="dfinput" placeholder="请输入缩略图类型" maxbytelength="50" value="${ywImagesBackthumbnail.thumbnail_type!''}"/>
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>缩略图路径</label>
					<input id="thumbnail_url" name="thumbnail_url" type="text" valid="NotBlank" class="dfinput" placeholder="请输入缩略图路径" maxbytelength="255" value="${ywImagesBackthumbnail.thumbnail_url!''}"/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>原图编号</label>
					<input id="image_id" name="image_id" type="text" valid="NotBlank" class="dfinput" placeholder="请输入原图编号" maxbytelength="50" value="${ywImagesBackthumbnail.image_id!''}"/>
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>创建时间</label>
					<input id="create_datetime" name="create_datetime" type="text" valid="NotBlank" class="dfinput" placeholder="请输入创建时间" maxbytelength="19" value="${ywImagesBackthumbnail.create_datetime!''}"/>
			      
			        <label for="level" class="label"><em style="color:red;">*</em>更新时间</label>
					<input id="update_datetime" name="update_datetime" type="text" valid="NotBlank" class="dfinput" placeholder="请输入更新时间" maxbytelength="19" value="${ywImagesBackthumbnail.update_datetime!''}"/>
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>状态（1：启用，2、禁用）</label>
					<input id="status" name="status" type="text" valid="NotBlank" class="dfinput" placeholder="请输入状态（1：启用，2、禁用）" maxbytelength="2" value="${ywImagesBackthumbnail.status!''}"/>
			</div>
	<div class="button-content">
	    <input type="button" class="btn btn-success btn_text" value="确认" id="btn_ywImagesBackthumbnail_submit"/>
		<input type="button" class="btn btn-success btn_text" value="取消" id="btn_ywImagesBackthumbnail_back"/>
	</div>
</form>	
<@script>
$(function(){
  // 确认
  $("#btn_ywImagesBackthumbnail_submit").click(function(){
      // 表单校验
      if (!formValidate("ywImagesBackthumbnailForm")) {
		 return false;
	  }
      var formData = $("#ywImagesBackthumbnailForm").serializeArray();
      $.post($("#ywImagesBackthumbnailForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
               devActAfterShowDialog(data.error_info,function(){
                 location.href = "${base}/images/ywImagesBackthumbnail.htm";
                },"suc");
            } else if(data.error_no == "-1"){
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }else{
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }
        })
  })
  
  // 取消
  $("#btn_ywImagesBackthumbnail_back").click(function(){
      location.href = "${base}/images/ywImagesBackthumbnail.htm";
  })
  
})
</@script>
</@screen>