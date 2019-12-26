<#assign base=request.contextPath />
<@screen id="qhzTeacherInsert" title="老师信息模块页面" places=[{"name":"老师信息模块","href":"${base}/qhz_teacher/qhzTeacher.htm"},{"name":"添加/修改","href":"#"}]>
<form id="qhzTeacherForm" action="insertOrUpdate.json" method="post" enctype="multipart/form-data" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type}"/>
    <input id="id" name="id" type="hidden" value="${qhzTeacher.id!''}"/>
	<div class="formtitle">
		<span class="formspan">老师信息模块</span>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>姓名</label>
		<input id="name" name="name" type="text" valid="NotBlank" class="dfinput" placeholder="请输入姓名" maxlength="20" value="${qhzTeacher.name!''}"/>
	</div>
	<div class="form-group">
		<@fileSyncUpload isMust=true title="照片" imageUrl=qhzTeacher.photo />
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>身份证</label>
		<input id="card" name="card" type="text" valid="CID" class="dfinput" placeholder="请输入身份证" maxlength="50" value="${qhzTeacher.card!''}"/>
      
        <label for="level" class="label"><em style="color:red;">*</em>年龄</label>
		<input id="age" name="age" type="text" valid="Digital" class="dfinput" placeholder="请输入年龄" maxlength="5" value="${qhzTeacher.age!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>性别</label>
		<@select valid="NotBlank" placeholder="请选择性别" name="sex" dic_key="1011" value="${qhzTeacher.sex!''}"/>
      
        <label for="level" class="label"><em style="color:red;">*</em>手机号</label>
		<input id="phone" name="phone" type="text" valid="Mobile" class="dfinput" placeholder="请输入手机号" maxlength="15" value="${qhzTeacher.phone!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>住址</label>
		<input style="width:50%;" id="address" name="address" type="text" valid="NotBlank" class="dfinput" placeholder="请输入住址" maxlength="100" value="${qhzTeacher.address!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>部门</label>
		<input id="dept" name="dept" type="text" valid="NotBlank" class="dfinput" placeholder="请输入部门" maxlength="50" value="${qhzTeacher.dept!''}"/>
		
		<label for="level" class="label"><em style="color:red;">*</em>职位</label>
		<input id="post" name="post" type="text" valid="NotBlank" class="dfinput" placeholder="请输入职位" maxlength="50" value="${qhzTeacher.post!''}"/>
	</div>
	<div class="form-group">
		<@editor name="brief" title="老师简介" content=qhzTeacher.brief />
	</div>
	<div class="button-content">
	    <input type="button" class="btn btn-success btn_text" value="确认" id="btn_qhzTeacher_submit"/>
		<input type="button" class="btn btn-success btn_text" value="取消" id="btn_qhzTeacher_back"/>
	</div>
</form>	
<@script>
$(function(){
  //预览图片宽高调整
  $("#preview").css("width","150px");
  $("#preview").css("height","170px");

  // 确认
  $("#btn_qhzTeacher_submit").click(function(){
      // 表单校验
      if (!formValidate("qhzTeacherForm")) {
		 return false;
	  }
      var formData = new FormData($("#qhzTeacherForm")[0]);
      $.ajax({  
          url: $("#qhzTeacherForm").attr("action") ,  
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
	                 location.href = "${base}/qhz_teacher/qhzTeacher.htm";
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
  $("#btn_qhzTeacher_back").click(function(){
      location.href = "${base}/qhz_teacher/qhzTeacher.htm";
  })
  
})
</@script>
</@screen>
<@fileAsynUpload name="fileAsynUpload"/>