<#assign base=request.contextPath />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>启恒智互联科技有限公司</title>
<link rel="shortcut icon" href="https://www.qhzhlkj.com/files/image/qhz/qhz_icon.png">
<!-- 本地js,css文件引入 -->
<link rel="stylesheet" href="${base}/css/fileinput/fileinput.min.css">
<link rel="stylesheet" href="${base}/css/fileinput/fileinput-rtl.min.css">
<@script src="${base}/js/fileinput/fileinput.min.js"/>
<@script src="${base}/js/fileinput/locales/zh.js"/>
</head>
<@screen id="qhzCourseInsert" title="课程详细信息页面" places=[{"name":"课程详细信息","href":"${base}/qhz_course/qhzCourse.htm"},{"name":"添加/修改","href":"#"}]>
<form id="qhzCourseForm" action="insertOrUpdate.json" method="post" enctype="multipart/form-data" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type}"/>
    <input id="id" name="id" type="hidden" value="${qhzCourse.id!''}"/>
	<div class="formtitle">
		<span class="formspan">课程详细信息</span>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>课程标题</label>
		<input id="title" name="title" type="text" valid="NotBlank" class="dfinput" placeholder="请输入课程标题" maxbytelength="100" value="${qhzCourse.title!''}"/>
      
        <label for="level" class="label"><em style="color:red;">*</em>课程所属集数</label>
		<input id="epis" name="epis" type="text" valid="Digital" class="dfinput" placeholder="请输入课程所属集数" maxbytelength="10" value="${qhzCourse.epis!''}"/>
	</div>
	<div class="form-group">
		<@fileSyncUpload isMust=true title="课程图片" imageUrl=qhzCourse.img />
	</div>
	<div class="form-group">
        <label for="level" class="label"><em style="color:red;">*</em>课程所属</label>
        <select id="curriculum_id" name="curriculum_id" class="dfinput">
	      <#list qhzCurriculums as item>
	       	<option value="${item.id!''}" <#if qhzCourse.curriculum_id == item.id>selected="selected"</#if>>${item.title}</option>
	      </#list>
		</select>
		
		<label for="level" class="label"><em style="color:red;">*</em>主讲老师</label>
		<select id="teacher_id" name="teacher_id" class="dfinput">
	      <#list qhzTeachers as item>
	       	<option value="${item.id!''}" <#if qhzCourse.teacher_id == item.id>selected="selected"</#if>>${item.name}</option>
	      </#list>
		</select>
	</div>
	<div style="display:flex;flex-direction:row;align-items:center;" class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>课程视频</label>
		<input id="videoFile" name="videoFile" type="file" multiple class="file-loading" />
		
		<input id="video" name="video" type="hidden" value="${qhzCourse.video!''}"/>
	</div>
	<div class="form-group">
        <label for="level" class="label"><em style="color:red;">*</em>现价</label>
		<input id="unit_price" name="unit_price" type="text" valid="Digital" class="dfinput" placeholder=""请输入现价" maxbytelength="20" value="${qhzCourse.unit_price!'1'}"/>
	
		<label for="level" class="label">原价</label>
		<input id="fic_price" name="fic_price" type="text" valid="Digital" class="dfinput" maxbytelength="20" value="${qhzCourse.fic_price!'0'}"/>
	</div>
	<div class="form-group">
        <label for="level" class="label">总点击量</label>
		<input id="click_num" name="click_num" type="text" valid="Digital" class="dfinput" maxbytelength="100" value="${qhzCourse.click_num!'0'}"/>
	
		<label for="level" class="label">虚拟点击量</label>
		<input id="fic_click_num" name="fic_click_num" type="text" valid="Digital" class="dfinput" maxbytelength="100" value="${qhzCourse.fic_click_num!'0'}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label">课程简述</label>
		<input id="resume" name="resume" type="text" class="dfinput" placeholder="请输入课程简述" maxbytelength="100" value="${qhzCourse.resume!''}"/>
	
        <label for="level" class="label">是否可以试听</label>
        <@select placeholder="请选择是否可以试听" name="audition" dic_key="1097" value=qhzCourse.audition!'1'/>
	</div>
	<div class="form-group">
        <label for="level" class="label">创建人</label>
		<input id="create_name" name="create_name" type="text" readonly="readonly" class="dfinput" maxbytelength="30" value="${qhzCourse.create_name!''}"/>
	
		<label for="level" class="label">创建时间</label>
		<@dateTimePicker name="create_time" disabled="true" timeValue=qhzCourse.create_time />
	</div>
	<div class="form-group">
        <label for="level" class="label">更新人</label>
		<input id="update_name" name="update_name" type="text" readonly="readonly" class="dfinput" maxbytelength="30" value="${qhzCourse.update_name!''}"/>
	
		<label for="level" class="label">更新时间</label>
		<@dateTimePicker name="update_time" disabled="true" timeValue=qhzCourse.update_time />
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>启用状态</label>
		<@select placeholder="请选择启用状态" name="status" dic_key="1000" value=qhzCourse.status!'1'/>
	</div>
	<div class="form-group">
		<@editor name="content" title="课程详情" content=qhzCourse.content />
	</div>
	<div class="button-content">
	    <input type="button" class="btn btn-success btn_text" value="确认" id="btn_qhzCourse_submit"/>
		<input type="button" class="btn btn-success btn_text" value="取消" id="btn_qhzCourse_back"/>
	</div>
</form>	
<@script>
$(function(){

  //上传视频
  $("#videoFile").fileinput({
	    dropZoneTitle : "请上传150M以内的MP4格式文件！",
	    uploadUrl : "${base}/qhz_course/qhzCourse/uploadVideo.json",
	    uploadAsync: false,					//是否为异步上传
	    language : "zh",					//设置中文
	    enctype: 'multipart/form-data', 	//设置文件传输模式
	    autoReplace : true,					//是否自动替换当前图片, 设置为true时, 再次选择文件, 会将当前的文件替换掉
	    showCaption : true,					//是否显示被选文件的简介
	    showBrowse: true,					//是否显示浏览按钮
	    showUpload : true,					//是否显示上传按钮
	    showCancel: false,					//是否显示取消按钮
	    showRemove : false,					//是否显示删除按钮
	    showClose : false,					//是否显示关闭按钮
	    showPreview:true,                   //是否显示预览区域
	    allowedFileTypes: ['video'],		//文件类型
	    allowedFileExtensions : [ "mp4" ],	//接收的文件后缀
	    browseClass: "btn btn-primary",		//按钮样式
	    maxFileCount: 1,
	    minFileCount: 1,
	    maxFileSize : 153600,				//文件最大153600kb=150M 
	    initialPreviewShowDelete : false,
	    deleteUrl: '${base}/qhz_course/qhzCourse/deleteVideo.json',						//删除视频的请求路径
	    deleteExtraData: {																//删除视频时额外传入的参数
	    	id: $("#id").val()
	    },				
	    
	    layoutTemplates :{
			actionUpload:'',				//去除上传预览缩略图中的上传按钮
			//actionDelete:'',				//去除上传预览缩略图中的删除按钮
		},
		
		initialPreviewAsData: true,			 //是否开启回显
		initialPreviewFileType: 'video',
		initialPreview: [
			$("#video").val()
		],
		//initialPreviewDownloadUrl: $("#video").val(),
		initialPreviewConfig: [
			{ 
				caption: $("#video").val().slice(58), 
				type:"video",
				filetype:"video/mp4", 
				width: '', 
				key: $("#id").val() 
			},
		],
       
	}).on("filebatchuploadsuccess", function(event, data) {		//上传成功后的处理
	    console.log('文件上传成功 : '+ data.response.url);
	    //视频文件服务器访问路径赋值
	    $("#video").val(data.response.url);
	}).on('fileerror', function(event, data, msg) {  			//上传失败的处理
	    console.log('文件上传失败！'+ msg);
	}).on('filedeleted', function(event, key) {  				//删除成功后的处理
	    $("#video").val("");
	}); 


  $("#btn_qhzCourse_submit").click(function(){
      // 表单校验
      if (!formValidate("qhzCourseForm")) {
		 return false;
	  }
	  	
      var formData = new FormData($("#qhzCourseForm")[0]);
      //var formData = $("#qhzCourseForm").serializeArray();
      $.ajax({  
          url: $("#qhzCourseForm").attr("action") ,  
          type: 'POST',  
          data: formData,  
          async: false,  
          cache: false,  
          contentType: false,  
          processData: false,  
          success: function (data) {  
            if (data.error_no == 0) {
                devActAfterShowDialog(data.error_info,function(){
                  location.href = "${base}/qhz_course/qhzCourse.htm";
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
  $("#btn_qhzCourse_back").click(function(){
      location.href = "${base}/qhz_course/qhzCourse.htm";
  })
})
</@script>
</@screen>
<@fileAsynUpload name="fileAsynUpload"/>