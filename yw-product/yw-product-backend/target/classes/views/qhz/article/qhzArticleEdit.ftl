<#assign base=request.contextPath />
<@screen id="qhzArticleInsert" title="文章信息模块页面" places=[{"name":"文章信息模块","href":"${base}/qhz_article/qhzArticle.htm"},{"name":"添加/修改","href":"#"}]>
<form id="qhzArticleForm" action="insertOrUpdate.json" method="post" enctype="multipart/form-data" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type}"/>
    <input id="id" name="id" type="hidden" value="${qhzArticle.id!''}"/>
	<div class="formtitle">
		<span class="formspan">文章信息模块</span>
	</div>
	<div class="form-group">
		 <label for="level" class="label"><em style="color:red;">*</em>标题</label>
		<input id="title" name="title" type="text" valid="NotBlank" class="dfinput" placeholder="请输入标题" maxlength="100" value="${qhzArticle.title!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>类型</label>
		<@select valid="NotBlank" placeholder="请选择类型" name="type" dic_key="1031" value="${qhzArticle.type }"/>
		
		<label for="level" class="label"><em style="color:red;">*</em>应用场景</label>
		<@select valid="NotBlank" placeholder="请选择应用场景" name="scene_type" dic_key="1096" value="${qhzArticle.scene_type }" />
	</div>
	<div class="form-group">
		<@fileSyncUpload isMust=true title="图片" imageUrl=qhzArticle.img />
	</div>
	<div class="form-group">
		<label for="level" class="label">发布人</label>
		<input id="create_name" name="create_name" disabled="true" type="text" valid="NotBlank" class="dfinput" placeholder="" value="${qhzArticle.create_name!''}"/>
	
		<label for="level" class="label">发布时间</label>
        <@dateTimePicker name="create_time" disabled="true" timeValue=qhzArticle.create_time />
	</div>
	<div class="form-group">
		<label for="level" class="label">更新人</label>
		<input id="update_name" name="update_name" disabled="true" type="text" valid="NotBlank" class="dfinput" placeholder="" value="${qhzArticle.update_name!''}"/>
        
        <label for="level" class="label">更新时间</label>
		<@dateTimePicker name="update_time" disabled="true" timeValue=qhzArticle.update_time />
	</div>
	<div class="form-group">
		<label for="level" class="label">阅读量</label>
		<input id="read_num" name="read_num" type="text" valid="NotBlank" class="dfinput" placeholder="请输入阅读数" maxlength="50" value="${qhzArticle.read_num!'0'}"/>
	
        <label for="level" class="label"><em style="color:red;">*</em>启用状态</label>
        <@select placeholder="请选择状态" name="status" dic_key="1000" value=qhzArticle.status!'1'/>
	</div>
	<div class="form-group">
		<@editor name="content" title="文章详情" content=qhzArticle.content />
	</div>
	<div class="button-content">
	    <input type="button" class="btn btn-success btn_text" value="确认" id="btn_qhzArticle_submit"/>
		<input type="button" class="btn btn-success btn_text" value="取消" id="btn_qhzArticle_back"/>
	</div>
</form>	
<@script>
$(function(){

  $("#btn_qhzArticle_submit").click(function(){
      // 表单校验
      if (!formValidate("qhzArticleForm")) {
		 return false;
	  }
	  	
      var formData = new FormData($("#qhzArticleForm")[0]);
      //var formData = $("#qhzArticleForm").serializeArray();
      $.ajax({  
          url: $("#qhzArticleForm").attr("action") ,  
          type: 'POST',  
          data: formData,  
          async: false,  
          cache: false,  
          contentType: false,  
          processData: false,  
          success: function (data) {  
            if (data.error_no == 0) {
                devActAfterShowDialog(data.error_info,function(){
                  location.href = "${base}/qhz_article/qhzArticle.htm";
                },"suc");
            } else {
                isTimeOutdevShowDialog(data.error_info,data.infos);
            };
          },  
          error: function (returndata) {  
             devShowDialog("系统异常");
          }  
     });  
  })
  
  // 取消
  $("#btn_qhzArticle_back").click(function(){
      location.href = "${base}/qhz_article/qhzArticle.htm";
  })
  
})
</@script>
</@screen>
<@fileAsynUpload name="fileAsynUpload"/>