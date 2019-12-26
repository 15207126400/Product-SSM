<@screen id="ywXcxCarouselInsert" title="小程序轮播图页面" places=[{"name":"小程序轮播图","href":"${base}/xcx/ywXcxCarousel.htm"},{"name":"添加/修改","href":"#"}]>
<form id="ywXcxCarouselForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type}"/>
    <input id="id" name="id" type="hidden" value="${ywXcxCarousel.id!''}"/>
	<div class="formtitle">
		<span class="formspan">小程序轮播图</span>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>轮播图类型</label>
		<@select valid="NotBlank" placeholder="请选择轮播图类型" name = "carousel_type" dic_key="1070" value="${ywXcxCarousel.carousel_type!''}"/>
      
        <label for="level" class="label">轮播图名称</label>
		<input id="carousel_name" name="carousel_name" type="text" class="dfinput" placeholder="请输入轮播图名称" maxbytelength="50" value="${ywXcxCarousel.carousel_name!''}"/>
	</div>
	<div class="form-group">
		<@fileSyncUpload isMust=true title="轮播图路径" imageUrl = ywXcxCarousel.carousel_url/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>跳转导航</label>
		<@select class="tplus" name="navigation" datalist=ywXcxNavigations placeholder="请选择跳转导航" value="${ywXcxCarousel.navigation!''}" optionData={"value":"id","option":"nav_name"}/>
	
		<label for="level" class="label"><em style="color:red;">*</em>排序</label>
		<input id="sort" name="sort" type="text" valid="NotBlank" class="dfinput" placeholder="请输入排序" maxbytelength="10" value="${ywXcxCarousel.sort!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>状态</label>
		<@select valid="NotBlank" placeholder="请选择状态" name = "status" dic_key="1000" value="${ywXcxCarousel.status!''}"/>
	</div>
	<div class="button-content">
	    <input type="button" class="btn btn-success btn_text" value="确认" id="btn_ywXcxCarousel_submit"/>
		<input type="button" class="btn btn-success btn_text" value="取消" id="btn_ywXcxCarousel_back"/>
	</div>
</form>	
<@script>
$(function(){

  // 确认
  $("#btn_ywXcxCarousel_submit").click(function(){
      // 表单校验
      if (!formValidate("ywXcxCarouselForm")) {
		 return false;
	  }
      var formData = new FormData($("#ywXcxCarouselForm")[0]);
      $.ajax({  
          url: $("#ywXcxCarouselForm").attr("action") ,  
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
	                 location.href = "${base}/xcx/ywXcxCarousel.htm";
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
  $("#btn_ywXcxCarousel_back").click(function(){
      location.href = "${base}/xcx/ywXcxCarousel.htm";
  })
  
})
</@script>
</@screen>