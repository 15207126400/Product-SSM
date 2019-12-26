<#assign base=request.contextPath />
<@screen id="qhzCurriculumInsert" title="沙龙注册课程信息表页面" places=[{"name":"沙龙注册课程信息表","href":"${base}/qhz_curriculum/qhzCurriculum.htm"},{"name":"添加/修改","href":"#"}]>
<form id="qhzCurriculumForm" action="insertOrUpdate.json" method="post" enctype="multipart/form-data" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type}"/>
    <input id="id" name="id" type="hidden" value="${qhzCurriculum.id!''}"/>
	<div class="formtitle">
		<span class="formspan">沙龙注册课程信息表</span>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>课程类型</label>
		<@select placeholder="请选择课程类型" name="type" dic_key="1092" value=qhzCurriculum.type!'1'/>
		
		<label for="level" class="label"><em style="color:red;">*</em>应用场景</label>
		<@select placeholder="请选择应用场景" name="scene_type" dic_key="1096" value=qhzCurriculum.scene_type!'1'/>
	</div>
	<div class="form-group">
		<@fileSyncUpload isMust=true title="图片" imageUrl=qhzCurriculum.img />
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>课程名称</label>
		<input id="title" name="title" type="text" valid="NotBlank" class="dfinput" placeholder="请输入课程名称" maxbytelength="40" value="${qhzCurriculum.title!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>课程价格</label>
		<input id="price" name="price" type="text" valid="Digital" class="dfinput" placeholder="请输入课程价格" maxbytelength="20" value="${qhzCurriculum.price!''}"/>
	
		<label for="level" class="label">课程原价</label>
		<input id="old_price" name="old_price" type="text" valid="Digital" class="dfinput" placeholder="请输入课程原价" maxbytelength="20" value="${qhzCurriculum.old_price!'0'}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>主讲老师</label>
		<select id="teacher_id" name="teacher_id" class="dfinput">
	      <#list qhzTeachers as item>
	       	<option value="${item.id!''}" <#if qhzCurriculum.teacher_id == item.id>selected="selected"</#if>>${item.name}</option>
	      </#list>
		</select>
	
		<label for="level" class="label">订阅量</label>
		<input id="buy_num" name="buy_num" type="text" valid="Digital" class="dfinput" placeholder="请输入订阅量" maxbytelength="20" value="${qhzCurriculum.buy_num!'0'}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label">创建人</label>
		<input id="create_name" name="create_name" type="text" readonly="readonly" class="dfinput" maxbytelength="20" value="${qhzCurriculum.create_name!''}"/>
	
        <label for="level" class="label">创建时间</label>
        <@dateTimePicker name="create_time" disabled="true" timeValue=qhzCurriculum.create_time />
    </div>
    <div class="form-group">  
    	<label for="level" class="label">更新人</label>
		<input id="update_name" name="update_name" type="text" readonly="readonly" class="dfinput" maxbytelength="20" value="${qhzCurriculum.update_name!''}"/>
    
        <label for="level" class="label">更新时间</label>
		<@dateTimePicker name="update_time" disabled="true" timeValue=qhzCurriculum.update_time />
	</div>
	<div class="form-group">
        <label for="level" class="label"><em style="color:red;">*</em>启用状态</label>
        <@select placeholder="请选择课程类型" name="status" dic_key="1000" value=qhzCurriculum.status!'1'/>
	</div>
	<div class="form-group">
		<@editor name="content" title="课程介绍" content=qhzCurriculum.content />
	</div>
	<div class="button-content">
	    <input type="button" class="btn btn-success btn_text" value="确认" id="btn_qhzCurriculum_submit"/>
		<input type="button" class="btn btn-success btn_text" value="取消" id="btn_qhzCurriculum_back"/>
	</div>
</form>	
<@script>
$(function(){

  $("#btn_qhzCurriculum_submit").click(function(){
      // 表单校验
      if (!formValidate("qhzCurriculumForm")) {
		 return false;
	  }
	  	
      var formData = new FormData($("#qhzCurriculumForm")[0]);
      //var formData = $("#qhzCurriculumForm").serializeArray();
      $.ajax({  
          url: $("#qhzCurriculumForm").attr("action") ,  
          type: 'POST',  
          data: formData,  
          async: false,  
          cache: false,  
          contentType: false,  
          processData: false,  
          success: function (data) {  
            if (data.error_no == 0) {
                devActAfterShowDialog(data.error_info,function(){
                  location.href = "${base}/qhz_curriculum/qhzCurriculum.htm";
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
  $("#btn_qhzCurriculum_back").click(function(){
      location.href = "${base}/qhz_curriculum/qhzCurriculum.htm";
  })
  
})
</@script>
</@screen>
<@fileAsynUpload name="fileAsynUpload"/>