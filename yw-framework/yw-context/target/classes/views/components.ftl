<#-- 修改freemarker默认显示数字格式，避免‘,’影响脚本程序执行 -->
<#setting number_format="#">
<#assign _version = "1.0.0.0" />
<#assign systemNo= "1" />
<#assign base=request.contextPath />
<#--
    @name script
    @param src 脚本文件，相对根路径
    @description js脚本
-->
<#macro script
        src="" >
    <#if src?has_content>
        <script type="text/javascript" src="${src}?t=${_version}"></script>
    <#else>
        <script type="text/javascript">
            <#nested />
        </script>
    </#if>
</#macro>

<#--
    @name image
    @param src 文件，images内部相对路径
    @param id {String} 唯一标识
    @param name {String} 名称
    @param paras {Json} 其他参数，包括：hidden、alt、class、height、width
    @description 图片文件
-->
<#macro image src 
        name="" id=name paras={} >
    <#if !src?starts_with("/")>
        <#local src = "/template/default/images/" + src />
    </#if>
    <img <#if name?has_content>name="${name}" </#if><#t/>
        <#if id?has_content>id="${id}" </#if><#t/>
        <#if paras.hidden?? && paras.hidden==true>style="visibility:hidden;" </#if><#t/>
        <#if paras.alt?has_content>alt="${paras.alt}" </#if><#t/>
        <#if paras.class?has_content>class="${paras.class}" </#if><#t/>
        <#if paras.height?has_content>height="${paras.height}" </#if><#t/>
        <#if paras.width?has_content>width="${paras.width}" </#if><#t/>
        src="${src}?t=${_version}&d=${.now?string('yyyyMMddHHmmssSS')}" />
</#macro>

<#--
    @name select 用于只有字典项的单选下拉框
    @param name {String} 唯一标识
    @param placeholder {String} 选择提示
    @param dic_key {String} 数据字典
    @param valid {String} 校验规则 NotBlank非空
    @param datalist {List<Map>} 取值范围 [{"option":"show1","value":"val1"},{"option":"show2","value":"val2"}]，优先级高于dic_key
    @param value {String} 选中值
    @param defaultValue {String} 默认值
    @param extraData {List<Map>} 额外取值范围，格式和datalist相同，与其他取值范围并存，起始位置为第二个选项
    @param excludeValue {List<String>} 要排除的选项的值的数组["value1", "value2"]
    @param ignorePlaceholder{String} ignorePlaceholder==1表示隐藏提示框信息，不传或传其他值则不隐藏
    @param showValue {boolean} 默认false，为true时选项前展示实际值，如"0 身份证"
    @param bystatus 是否过滤数据字典的状态 默认false 为true时，只显示状态为正常的字典项
    @description select选择框
-->
<#macro select name placeholder 
        id=name dic_key="" valid="" datalist=[] extraData=[] value="" defaultValue="" disabled="false" ignorePlaceholder=""
        optionData={} showValue=false class="">
    <#if datalist?size==0 && dic_key?has_content && SpringContext.containsBean("ywDictionaryCache") >
        <#assign ywDictionaryCache = SpringContext.getBean("ywDictionaryCache") />
        <#local datalist = ywDictionaryCache.getDictionaryList(dic_key)![] />
    </#if>
    <#if !value?has_content>
        <#local value = defaultValue />
    </#if>
    <select id="${id!''}" name="${name!''}" valid="${valid}" class="dfinput ${class!''}"
                          <#if disabled == "true">disabled="disabled"</#if>
                          placeholder=${placeholder!"请选择"} ignorePlaceholder="${ignorePlaceholder}">
          <#if ignorePlaceholder?has_content && ignorePlaceholder=="1">
            <option value='' ignorePlaceholder="1" >${placeholder!"请选择"}</option>
          <#else> 
            <option value='' >${placeholder!"请选择"}</option>
          </#if>
          <#if extraData?size gt 0>
            <#list extraData as data>
                <#if optionData??>
                   <option value="${data[optionData.value]!}" <#if value==data[optionData.value]!>selected="selected"</#if>><#if showValue!false>${data[optionData.value]!}-</#if>${data[optionData.option]!}<#if optionData.option1!false>-${data[optionData.option1]!}</#if></option>
                <#else>
                   <option value="${data.value!}" <#if value==data.value!>selected="selected"</#if>><#if showValue!false>${data.value!}-</#if>${data.option!}</option>
                </#if>
            </#list>
          </#if>
	      <#list datalist as data>
	          <#if optionData??>
	          	 <option value="${data[optionData.value]!data.dic_subkey}" <#if value == data[optionData.value]!data.dic_subkey>selected="selected"</#if>><#if showValue!false>${data[optionData.value]!data.dic_subvalue!}-</#if>${data[optionData.option]!data.dic_subvalue}<#if optionData.option1!false>-${data[optionData.option1]!}</#if></option>
	          <#else>
	             <option value="${data.value!data.dic_subkey}" <#if value == data.value!data.dic_subkey>selected="selected"</#if>><#if showValue!false>${data.value!data.dic_subvalue!}-</#if>${data.option!data.dic_subvalue}</option>
	          </#if>
	      </#list>
	</select>
    <@script>
        
    </@script>
</#macro>

<#--
    @name select2 
    @param name {String} 唯一标识
    @param placeholder {String} 选择提示
    @param dic_key {String} 数据字典
    @param valid {String} 校验规则 NotBlank非空
    @param datalist {List<Map>} 取值范围 [{"option":"show1","value":"val1"},{"option":"show2","value":"val2"}]，优先级高于dic_key
    @param value {String} 选中值
    @param defaultValue {String} 默认值
    @param extraData {List<Map>} 额外取值范围，格式和datalist相同，与其他取值范围并存，起始位置为第二个选项
    @param optionData {Map} value和option显示在select option中取值{"value":"field1","option":"field2"}
    @param excludeValue {List<String>} 要排除的选项的值的数组["value1", "value2"]
    @param ignorePlaceholder{String} ignorePlaceholder==1表示隐藏提示框信息，不传或传其他值则不隐藏
    @param showValue {boolean} 默认false，为true时选项前展示实际值，如"0 身份证"
    @param multiple {boolean} 默认false，为true时可以进行多选
    @param bystatus 是否过滤数据字典的状态 默认false 为true时，只显示状态为正常的字典项
    @description select2选择框(用于下拉搜索或多选的下拉框)
-->
<#macro select2 name placeholder 
        id=name dic_key="" valid="" datalist=[] extraData=[] value="" defaultValue="" disabled="false" ignorePlaceholder=""
        optionData={} showValue=false multiple=false class="">
<link rel="stylesheet" href="${base}/css/select2/select2.min.css" type="text/css">
<#-- select选择框 -->
<@script src="${base}/js/select2/select2.min.js"/>
<@script src="${base}/js/select2/i18n/zh-CN.js"/>        
    <#if datalist?size==0 && dic_key?has_content && SpringContext.containsBean("ywDictionaryCache") >
        <#assign ywDictionaryCache = SpringContext.getBean("ywDictionaryCache") />
        <#local datalist = ywDictionaryCache.getDictionaryList(dic_key)![] />
    </#if>
    <#if !value?has_content>
        <#local value = defaultValue />
    </#if>
    <select id="${id!''}" name="${name!''}" valid="${valid}" class="dfinput ${class!''}"
                          <#if disabled == "true">disabled="disabled"</#if>
                          placeholder=${placeholder!"请选择"} ignorePlaceholder="${ignorePlaceholder}">
          <#if ignorePlaceholder?has_content && ignorePlaceholder=="1">
            <#if !multiple>
            <option value=' ' ignorePlaceholder="1" >${placeholder!"请选择"}</option>
            </#if>
          <#else> 
            <#if !multiple>
            <option value=' ' >${placeholder!"请选择"}</option>
            </#if>
          </#if>
          <#if extraData?size gt 0>
            <#list extraData as data>
                <#if optionData??>
                   <option value="${data[optionData.value]!}" <#if value==data[optionData.value]!>selected="selected"</#if>><#if showValue!false>${data[optionData.value]!}-</#if>${data[optionData.option]!}<#if optionData.option1!false>-${data[optionData.option1]!}</#if></option>
                <#else>
                   <option value="${data.value!}" <#if value==data.value!>selected="selected"</#if>><#if showValue!false>${data.value!}-</#if>${data.option!}</option>
                </#if>
            </#list>
          </#if>
	      <#list datalist as data>
	          <#if optionData??>
	          	 <option value="${data[optionData.value]!data.dic_subkey}" <#if value == data[optionData.value]!data.dic_subkey>selected="selected"</#if>><#if showValue!false>${data[optionData.value]!data.dic_subvalue!}-</#if>${data[optionData.option]!data.dic_subvalue}<#if optionData.option1!false>-${data[optionData.option1]!}</#if></option>
	          <#else>
	             <option value="${data.value!data.dic_subkey}" <#if value == data.value!data.dic_subkey>selected="selected"</#if>><#if showValue!false>${data.value!data.dic_subvalue!}-</#if>${data.option!data.dic_subvalue}</option>
	          </#if>
	      </#list>
	</select>
    <@script>
      //下拉框搜索或多选
	  $("#${name}").select2({
	      placeholder:'--${placeholder!"请选择"}--',
	      language : 'zh-CN',
	      <#if multiple>
	      multiple:true,
	      </#if>
	      width:'260px'
	  });
	  // 多选设置默认选中
	  <#if multiple>
	     $("#branch_id").val(${FastJsonUtil.toJSONString(value?split(","))}).trigger("change");
	  </#if>
    </@script>
</#macro>

<#--
    @name checkbox
    @param name {String} 唯一标识
    @param dic_key {String} 数据字典
    @param valid {String} 校验规则 NotBlank非空
    @param datalist {List<Map>} 取值范围 [{"option":"show1","value":"val1"},{"option":"show2","value":"val2"}]，优先级高于dic_key
    @param extraData {List<Map>} 额外取值范围，格式和datalist相同，与其他取值范围并存，起始位置为第二个选项
    @param optionData {Map} value和option显示在select option中取值{"value":"field1","option":"field2"}
    @param value {List} 选中值
    @param defaultValue {List} 默认值
    @description checkbox多选框
-->
<#macro checkbox name
        id=name dic_key="" valid="" placeholder="请勾选" datalist=[] extraData=[] value=[] defaultValue=[] 
        optionData={}>
        <#if datalist?size==0 && dic_key?has_content && SpringContext.containsBean("ywDictionaryCache") >
	        <#local ywDictionaryCache = SpringContext.getBean("ywDictionaryCache") />
	        <#local datalist = ywDictionaryCache.getDictionaryList(dic_key)![] />
	    </#if>
    <#if value?size==0>
        <#local value = defaultValue />
    </#if>
    <#list datalist as data>
        <#if optionData??>
			<input id="${id}" name="${name}" valid="${valid}" placeholder=${placeholder} type="checkbox" value="${data[optionData.value]!data.dic_subkey}" <#if value?seq_contains(data[optionData.value]!data.dic_subkey)>checked="checked"</#if>/>
			${data[optionData.option]!data.dic_subvalue}<#if optionData.option1!false>-${data[optionData.option1]!}</#if>
	    <#else>
	        <input id="${id}" name="${name}" valid="${valid}" placeholder=${placeholder} type="checkbox" value="${data.dic_subkey!''}" <#if value?seq_contains(data.dic_subkey)>checked="checked"</#if>/>
			${data.dic_subvalue!''}
	    </#if>		
    </#list>
	<@script>
        
    </@script>
</#macro>

<#--
    @name checkboxSearch
    @param name {String} 唯一标识
    @param dic_key {String} 数据字典
    @param valid {String} 校验规则 NotBlank非空
    @param datalist {List<Map>} 取值范围 [{"option":"show1","value":"val1"},{"option":"show2","value":"val2"}]，优先级高于dic_key
    @param optionData {Map} value和option显示在checkboxSearch option中取值{"value":"field1","option":"field2"} 解决数据格式不一致的问题
    @param value {List} 选中值
    @param defaultValue {List} 默认值
    @description checkboxSearch带过滤的多选框
-->
<#--example
    <#if (operatorinfo.en_branch_no!"")?trim?length!=0 > <@checkboxSearch name="en_branch_no" title="允许营业部" value=(operatorinfo.en_branch_no)?split(",") dic_key="branch_name"  />
    <#else>
    <@checkboxSearch name="en_branch_no" title="允许营业部" dic_key="branch_name"  />
    </#if>
-->
<#macro checkboxSearch name title="" placeholder="请勾选"
        id=name dic_key="" valid="" datalist=[] value=[] defaultValue=[] callback="" class="" height=""
        optionData={}>
    <#if datalist?size==0 && dic_key?has_content && SpringContext.containsBean("ywDictionaryCache") >
        <#local ywDictionaryCache = SpringContext.getBean("ywDictionaryCache") />
        <#local datalist = ywDictionaryCache.getDictionaryList(dic_key)![] />
    </#if>
    <#if value?size==0>
        <#local value = defaultValue />
    </#if>
    <style>
       .ui-option .alllistname .content-name .attr-panl{
          max-height: ${height!'60'}
       }
    </style>
    <div class="ui-option">
        <div class="alllistname edit" id="${id}">
            <h3>${title!""}</h3>
            <div class="search-box" style="display:none;">
                <lable class="search-label" for="">
                    <input class="search-input dfinput" type="text" value="" name="">
                </lable>
            </div>
            <div class="content-name" style="height:${height!'60'}">
                <div style="display:none" id="checkbox-list">
                    <#list datalist as data>
                       <#if optionData??>
                         <input type="checkbox" id="${id}" name="${name}" valid="${valid}" placeholder=${placeholder} value="${data[optionData.value]!data.dic_subkey}" <#if value?seq_contains(data[optionData.value]!data.dic_subkey)>checked="checked"</#if>/>
                       <#else>
                         <input type="checkbox" name="${name}" valid="${valid}" placeholder=${placeholder} value="${data.value!data.dic_subkey}" <#if value?seq_contains(data.value!data.dic_subkey)> checked="checked"</#if> />
                       </#if>
                    </#list>
                </div>
                <div class="attr-panl" style="height:${height!'60'}" id="attr-panl">
                    <a class="select-all department <#if (value?size)=(datalist?size)>current</#if>" href="javascript:void(0)"><span class="param-checkbox"></span>全部</a>
                    <#list datalist as data>
                       <#if optionData??>
                         <a class="department <#if value?seq_contains(data[optionData.value]!data.dic_subkey)>current</#if>" href="javascript:void(0)" name="${data[optionData.value]!data.dic_subkey}" >
                            <span class="param-checkbox"></span>${data[optionData.option]!data.dic_subvalue}
                         </a>
                       <#else> 
                         <a class="department <#if value?seq_contains(data.value!data.dic_subkey)>current</#if>" href="javascript:void(0)" name="${data.value!data.dic_subkey}" >
                            <span class="param-checkbox"></span>${data.option!data.dic_subvalue}
                         </a>
                       </#if> 
                    </#list>
                </div>
            </div>
            <div class="option-name">
                <a class="more" href="javascript:void(0)">更多<span class=""></span></a>
            </div>
        </div>
    </div>
    <@script>
        // 全局变量，checkbox变量
        checkboxSize = ${datalist?size};
        $(function(){
            $('#${id}').on('click', '.more', function(event) {//多选更多和收起的切换
                event.preventDefault();
                /* Act on the event */
                var flag = $(this).find("span").hasClass("ico-arrow");
                if(!flag) {
                  $(this).html('收起 ').append('<span class="ico-arrow"></span>');
                  $('#${id}').addClass('active');
                  $('#${id}').find('.search-box').show();
                  //查询重置
                  $('#${id} .search-input').val("");
                  $('#${id} .search-input').on('keyup',function(event){
                  //搜索条件过滤
                      var value = $(this).val().trim();
                      if(value.length!=0){
                      $("#${id} .department ").hide();// 先全部隐藏，然后再次显示
                      var patten = new RegExp(value);
                      //如果有搜索条件，只显示匹配选项，过滤查询不显示全部按钮。
                          $("#${id} .department ").each(function(){
                              //if($(this).text().indexOf(value)==-1){
                                // $(this).hide();
                              //}
                              if(patten.test($(this).text())){
                                 $(this).show();// 包含查询条件checkbox显示
                              }
                          });
                          $("#${id} .select-all").hide();
                      }else{
                      //空查询，显示所有选项
                         $("#${id} .department ").show();
                      }
                  });
                  flag = !flag;
                } else {
                  $(this).html('更多 ').append('<span class=""></span>');
                  $('#${id}').removeClass('active');
                  $('#${id}').find('.search-box').hide();
                  //如果在搜索中，收起时，要还原显示
                  $("#${id} .department ").show();
                  flag = !flag;
                }
             });
             
            $("#${id}").on("click",".department",function(){
                //debugger;
                if($(this).hasClass('current')) {
                    if($(this).hasClass('select-all')){
                        <#--全选已选-->
                        $("#${id} .select-all").siblings().removeClass('current');
                    }
                    $(this).removeClass('current');
                } else {
                    if($(this).hasClass('select-all')){
                        <#--全选已选-->
                        $("#${id} .select-all").siblings().addClass('current');
                    }
                    $(this).addClass('current');
                }
                var selectValues_${id} = getSelectedValues_${id}();
                $("input[name='${name}']").val(selectValues_${id});
                if(selectValues_${id}.length==checkboxSize){
                   $("#${id} .select-all").addClass('current');
                }else{
                   $("#${id} .select-all").removeClass('current');
                }
                //if (${callback}) {
                  //  ${callback}.apply(this);
               // }
                //getBranchsByRole();
            });
            
            function getSelectedValues_${id}(){
                var selectValues_${id} = [];
                $("#${id} .department.current").not(function(index) {
                  return $(this).hasClass('select-all'); 
                }).each(function(){
                    selectValues_${id}.push($(this).attr("name"));
                });
                return selectValues_${id};
            }
        });
    </@script>
</#macro>

<#--
    @name radio
    @param name {String} 唯一标识
    @param dic_key {String} 数据字典
    @param valid {String} 校验规则 NotBlank非空
    @param datalist {List<Map>} 取值范围 [{"option":"show1","value":"val1"},{"option":"show2","value":"val2"}]，优先级高于dic_key
    @param extraData {List<Map>} 额外取值范围，格式和datalist相同，与其他取值范围并存，起始位置为第二个选项
    @param optionData {Map} value和option显示在select option中取值{"value":"field1","option":"field2"}
    @param value "" 选中值
    @param defaultValue "" 默认值
    @description radio单选框
-->
<#macro radio name
        id=name dic_key="" valid="" placeholder="请选择" datalist=[] extraData=[] value="" defaultValue="" 
        optionData={}>
        <#if datalist?size==0 && dic_key?has_content && SpringContext.containsBean("ywDictionaryCache") >
	        <#local ywDictionaryCache = SpringContext.getBean("ywDictionaryCache") />
	        <#local datalist = ywDictionaryCache.getDictionaryList(dic_key)![] />
	    </#if>
    <#if value == "">
        <#local value = defaultValue />
    </#if>
    <#list datalist as data>
	    <#if optionData??>
			<input id="${id}" name="${name}" class="${id}" valid="${valid}" type="radio" placeholder=${placeholder} value="${data[optionData.value]!data.dic_subkey}" <#if value == data[optionData.value]!data.dic_subkey>checked="checked"</#if>/>
				${data[optionData.option]!data.dic_subvalue}<#if optionData.option1!false>-${data[optionData.option1]!}</#if>
	    <#else>
	       <input id="${id}" name="${name}" class="${id}" valid="${valid}" type="radio" placeholder=${placeholder} value="${data.dic_subkey!''}" <#if value == data.dic_subkey>checked="checked"</#if>/>
				${data.dic_subvalue!''}
	    </#if>			
    </#list>
	<@script>
        
    </@script>
</#macro>

<#--
    @name switch 开关控件
    @param name {String} 唯一标识
    @param valid {String} 校验规则 NotBlank非空
    @param on {String}开关开启值 默认1
    @param on {String}开关关闭值 默认0
    @param value "" 选中值
    @param defaultValue "2" 默认值
    @description radio单选框
-->
<#macro switch name
        id=name valid="" on="1" off="0" value="" defaultValue="0">
    <#if value == "">
        <#local value = defaultValue />
    </#if>
    <link href="${base}/css/bootstrap-switch.min.css" rel="stylesheet">
    <input id="${id}" type="checkbox" value="${value!'0'}"/>
    <input name="${name}" type="hidden" value="${value!'0'}"/>
    <@script src="${base}/js/bootstrap-switch.min.js"/>
	<@script>
	$(function(){
	    $('#${id}').bootstrapSwitch({
			onText:"开",
			offText:"关",
			onColor:"success",
			offColor:"info",
			size:"small",
			animate:false,
			onSwitchChange:function(event,state){
				if(state==true){
					$('[name="${name}"]').val(${on!''});
				}else{
					$('[name="${name}"]').val(${off!''});
				}
			}
		<#if value == "1">
		}).bootstrapSwitch('state',true);
		<#else>
		}).bootstrapSwitch('state',false);
		</#if>
    })		
    </@script>
</#macro>
<#--
    @name screen
    @param id {String} 唯一标识
    @param title {String} 显示标题
    @param places {List<{}>} 页面显示位置
    @description 屏幕主程序
-->
<#macro screen 
        id="" title="" places=[] beforeUnload="">
    <#if id?has_content >
        ${LayoutModel.addAttribute("id",id)}
    </#if>
    <#if title?has_content >
        ${LayoutModel.addAttribute("title",title)}
    </#if>
    <#if places?size gt 0>
        ${LayoutModel.addAttribute("places",places)}
    </#if>
    <#nested />
    <@script>
        var screen_of_page = {"id":"${id!""}","title":"${title!""}"};
        <#if beforeUnload?has_content>
        if(typeof(${beforeUnload})=="function"){
            var screen_before_unload = ${beforeUnload};
        }else{
            var screen_before_unload = null;
        }
        <#else>
            var screen_before_unload = null;
        </#if>
    </@script>
</#macro>

<#--
    @name multipartFileUpload
    @param id {String} 表单唯一标识
    @param formId {String} 表单唯一标识
    @param previewImageList [] 图片预览列表
    @param uploadUrl 图片上传url
    @param deleteUrl 图片删除url
    @description 屏幕主程序
-->
<#macro multipartFileUpload formId
        id = formId previewImageList=[] uploadUrl="${base}/system/ywImages/img_upload.json" deleteUrl="${base}/system/ywImages/delete.json">
<link href="${base}/css/fileinput/fileinput.css" media="all" rel="stylesheet" type="text/css"/>
<link href="${base}/css/fileinput/font-awesome.min.css" media="all" rel="stylesheet" type="text/css"/>
<link href="${base}/themes/explorer-fa/theme.css" media="all" rel="stylesheet" type="text/css"/>
<style>
  .file-drop-zone{
     overflow:auto;
  }
</style>    
<@script src="${base}/js/fileinput/plugins/sortable.js" />
<@script src="${base}/js/fileinput/fileinput.js"/>
<@script src="${base}/js/fileinput/locales/zh.js"/>
<@script src="${base}/themes/explorer-fa/theme.js"/>
<@script src="${base}/themes/fa/theme.js"/>
<@script src="${base}/js/fileinput/popper.min.js"/>     
   <div style="width:80%;height:300px;margin-bottom:300px;">
		<form enctype="multipart/form-data">
		    <div class="form-group">
		        <div class="file-loading">
		            <input id="file-${id}" name="file" type="file" multiple class="file" data-overwrite-initial="false" data-min-file-count="2">
		        </div>
		    </div>
		</form>
	</div>
<@script>
// 扩展数组按照下标删除
Array.prototype.del=function(index){
	if(isNaN(index)||index>this.length){
	   return false;
	} 
	this.splice(index,1); 
}
// 预览图片数组
var imagePreviewArray = [];
// 预览图片配置数组
var imagePreviewArrayConfig = [];

// 将预览图片加入图片预览数组和配置数组
 <#if previewImageList?? && previewImageList?size gt 0>
	<#list previewImageList as img>
		imagePreviewArray.push("<img style='width: 100%;height: 100%;max-width:100%;max-height:100%' src='${img.image_url!''}' class='file-preview-image' alt='商品图片' title='商品图片'>");
	    var obj${img_index} = new Object();
	    obj${img_index}.caption="${img.image_remark!''}";
        obj${img_index}.size="12312"; 
        obj${img_index}.width='120px';
        obj${img_index}.url='${deleteUrl}';
        obj${img_index}.key='${img.iamge_id}';
        obj${img_index}.extra={
		       image_id: '${img.image_id!''}'
		        } ;
	    imagePreviewArrayConfig.push(obj${img_index});
	</#list>
</#if>
// 批量图片上传
  imagesList = [];
  $("#file-${id}").fileinput({
        language: 'zh', //设置语言 
        theme: 'fa',
        uploadUrl: '${uploadUrl}', // 文件上传路径
        uploadExtraData:{
        	//product_id : '${detail.id!'' }',
        	//op_type : '1'
        }, // 上传文件传额外的参数
        allowedFileExtensions: ['jpg', 'png', 'gif'],
        overwriteInitial: false,
        maxFileSize: 1024,// 图片控制到1M
        maxFileCount: 5,
        enctype: 'multipart/form-data', 
        //allowedFileTypes: ['image', 'video', 'flash'],
        msgFilesTooMany: "您目前选择上传的文件数量为({n}) 超过允许的最大文件上传数量{m}！", 
        // 初始化预览图片
        initialPreview: imagePreviewArray,
		// 初始化预览图片配置
		initialPreviewConfig:imagePreviewArrayConfig,
        slugCallback: function (filename) { // 选择文件回调函数
            return filename.replace('(', '_').replace(']', '_');
        }
    }).on('filepreupload', function(event, data, previewId, index) {     //上传中  
             var form = data.form, files = data.files, extra = data.extra,  
             response = data.response, reader = data.reader;  
             console.log('文件正在上传');  
    }).on("fileuploaded", function (event, data, previewId, index) {    //一个文件上传成功  
    		console.log(data.response.image_url)
    		$(".kv-upload-progress").hide();
            var image_url = data.response.image_url;  //文件上传成功返回的文件名，可返回自定义文件名
            imagesList.push({ "image_url": image_url, "key_id": previewId })
             
  
    }).on('filesuccessremove', function(event, data, previewId, index) {// 文件上传成功后移除回调函数
	    for (var i = 0; i < imagesList.length; i++) { 
	      if (imagesList[i].key_id== data) { 
	          imagesList.del(i); 
	      } 
	   }
	}).on('fileerror', function(event, data, msg) {  //一个文件上传失败 回调函数 
	      $(".file-error-message").html(msg);
                console.log('文件上传失败！'+msg);  
    }).on('filebatchuploaderror', function(event, data, msg) {
          $(".file-error-message").html(msg);
                console.log('同步文件上传失败！'+msg)
    }).on('filepredelete', function(event, key) { // 删除初始化预览图片前回调
           if(!confirm("确定删除原文件？删除后不可恢复")){  
                return false;  
            }  
	}).on('filedeleted', function(event, key) { // 删除初始化预览图片成功之后回调函数
			
           //alert("删除成功");
	}).on('filedeleteerror', function(event, key) { // 删除初始化预览图片失败之后回调函数
           alert("删除失败");
	});
	
	// 添加批量图片
	function addBatchImage(){
	   var count = imagesList.length;
	   if(count > 0){
		   for(var i=0;i<count;i++){
		    var image =  imagesList[i];
			$("#${formId}").append("<input name='image_url' type='hidden' value='"+image.image_url+"' />")
		  }
		}
	}
</@script>	
</#macro>

<#--
    @name innerOfQueryTable
    @description 内部插入inner部分
-->
<#macro innerOfQueryTable >
    <#assign ajaxQueryTable_inner>
    <#nested />
    </#assign>
</#macro>

<#--
    @name innerOfQueryCondition
    @description 内部插入inner查询条件部分
-->
<#macro innerOfQueryCondition >
    <#assign ajaxQueryCondition_inner>
    <#nested />
    </#assign>
</#macro>
 
<#--
    @name ajaxQueryTable 
    @param id {String} 唯一编号
    @param action {String} 主查询数据url
    @param paginatorUrl {String} 分页查询url
    @param updateCellUrl {String} 单元格编辑url
    @param headItems {List<Map>} 表头，{id、name、class/class_style、}
    @param auto {Boolean} 是否自动执行查询
    @param isMultiple {Boolean} 是否批量操作
    @param multipleCondition {List<？>} 执行批量操作的条件
    @param updateCellCondition {List<？>} 单元格编辑条件
    @param hidden {Boolean} 是否隐藏
    @param queryCondition 按钮查询或回车查询时需要满足的条件
    @param buttons {Map} 按钮及样式
    @param querySub {Map{querySubCondition:查询子内容的条件(默认通过主键查询),subCallback:子内容显示的回调函数,updateSubCellUrl:子内容单元格进行编辑的url,updateSubCellCondition:子内容单元格更新的条件}} 行的子项查询（例如字表查询或子项查询）
    @description ajax查询分页表格
    @rtQryPage 返回列表第几页
-->
<#macro ajaxQueryTable 
        id action paginatorUrl updateCellUrl headItems auto=false isMultiple=false multipleCondition=[] updateCellCondition=[] rtQryPage="1" queryCondition="undefined" querySub = {} buttons={"查询#btn_qry":"btn btn-search"}>
        <#-- 获取字典缓存 -->
		<#if SpringContext.containsBean("ywDictionaryCache") >
			<#local ywDictionaryCache = SpringContext.getBean("ywDictionaryCache") />
		</#if>
		<style>
          
           .cell-edit{
              color: #3a5e77;
              cursor: pointer;
              margin-left: 10;
              border-bottom: 1px solid #3a5e77;
           }
           .cell-edit-row{
              color: #1991da;
              cursor: pointer;
              margin-left: 5;
              border-bottom: 1px solid #1991da;
           }
           .table-sub{
              color: #1991da;
		      cursor: pointer;
           }
           
           
		</style>
	<div class="rightinfo">
	   <div>
        <form name="${id}" id="${id}" class="form-main" action="${action}" method="post">
          <div class="form-search">
			<div class="tools">
				<div class="search-box">
				    <#nested />
				    <#-- 添加查询条件 -->
				    <#if ajaxQueryCondition_inner??>
				       ${ajaxQueryCondition_inner}
				    </#if>
					<#--<input id="level" name="level" type="text" class="input-group" value="${level}" placeholder="请输入会员级别查找"/ >-->
					<input type="hidden" class="txt" name="rtQryPage" value="${rtQryPage}" id="rtQryPage">
				</div>
			</div>
			<div class="zoom-btn">
             <a href="javascript:void(0)">展开<i></i></a>
            </div>
           </div> 
			<#-- 查询按钮部分 -->
			<div class="form-button">
				<span class="input-group-btn">
					<#list buttons?keys as key>
	                    <#if key?contains("#")>
	                        <#if key?split("#")[1] == "export_excel">
                                <button type="button" id="${key?split("#")[1]}" class="${buttons[key]}" >${key?split("#")[0]}</button>
	                        <#else>
	                            <button type="button" id="${key?split("#")[1]}" class="${buttons[key]} app-row-around-layout" ><span class="glyphicon glyphicon-search"></span><span>${key?split("#")[0]}</span></button>
	                        </#if>
	                    <#else>
	                        <#if key == "export_excel">
                                <button type="button" class="${buttons[key]}" >${key}</button>
	                        <#else>
	                            <button type="button" class="${buttons[key]}" ><span class="glyphicon glyphicon-search"></span><span>${key}</span></button>
	                        </#if>
	                    </#if>
	                </#list>
				</span>
				<#-- 添加按钮部分 -->
				<#if ajaxQueryTable_inner?has_content>
				    ${ajaxQueryTable_inner}
				</#if>
			</div>
		</form>
		<div style="display: flex;flex-direction:row;">
		  <div style="overflow-x:auto;width:100%">
			<table class="tablelist" id="ajaxQueryTable_${id}">
				<!-- 表单标题栏 -->
				<thead>
					<tr>
					    <#local usedDicts = {} />
					    <#-- 判断是否有子内容或单元格是否可编辑 -->
					    <#if querySub.querySubCondition?? || td.edit??>
					       <th></th>
					    </#if>
					    <#-- 增加复选框(isMultiple==true控制是否显示) -->
					    <#if isMultiple>
					   		<th><input type="checkbox" id="allCheckboxs"></th>
					    </#if>
			      		<#list headItems! as td>
			      		
				        	<th <#if td.width?has_content>width="${td.width}"</#if><#if td.hidden??|| td.hidden?has_content>style="display:none;"</#if>class="<#if td_index % 2 ==1><#if isMultiple == false>even</#if><#else><#if isMultiple>even</#if></#if> <#if td.class?has_content>${td.class}</#if>">${td.name!""}<#if td.edit??><span class='glyphicon glyphicon-pencil cell-edit cell-edit-pencil' title='批量列修改'></span></#if></th>
				        	<#-- 存储字典信息 -->
							<#if ywDictionaryCache?exists && td.dic_key?? && ywDictionaryCache.getDictionaryList(td.dic_key)??>
				        		<#local subentrys = {} />
				        		<#list ywDictionaryCache.getDictionaryList(td.dic_key) as dict>
				        			<#local subentrys = subentrys + {dict.dic_subkey:dict.dic_subvalue} />
				        		</#list>
				        		<#local usedDicts = usedDicts + {td.dic_key:subentrys} />
				        	</#if>
				        	<#-- 判断是否配置单元格修改 -->
				        	<#if td.edit??>
				        	   <#local cellEdit = true/>
				        	</#if>
				        </#list>
					</tr>
				</thead>
				<!-- 表单数据部分 -->
				<tbody>
				</tbody>
			</table>
		</div>
		</div>
		</div>	
		<div class="bottom app-row-between-layout" id="pagenav_${id}" style="display:none">
			<ul id='drawPaginator_${id}' class='bottom-d'></ul>
		</div>
		
	</div>
<@script>
    // 表单查询对象  
    var QueryTable_${id} = {
		headItems : ${FastJsonUtil.toJSONString(headItems)},
		dicts : ${FastJsonUtil.toJSONString(usedDicts)},
		multipleCondition:${FastJsonUtil.toJSONString(multipleCondition)},// 多选条件(转换为js对象)
		updateCellCondition:${FastJsonUtil.toJSONString(multipleCondition)},// 单元格编辑条件(转换为js对象)
		querySub:${FastJsonUtil.toJSONString(querySub)},// 子内容条件转换为js对象
		paginator : {},
		dataList:[],
		query : function (page) {
			if ( page < 0 || page > QueryTable_${id}.paginator.totalPages) {
				return false;
			}
			removeTableBody();
			addToTableBody("正在查询...");
			var	formData = $("#${id}").serializeArray();
			if (page == 0) {
				$("#pagenav_${id}").hide();
				$.post("${paginatorUrl}", formData, function (data) {
				    var error_no = data.error_no;
				    var error_info = data.error_info;
					if(error_no != "0"){
					    removeTableBody();
						addToTableBody(error_info);
						return;
					}
					QueryTable_${id}.paginator = data;
					if (data.totalPages > 0) {
						var rtQryPage = $("#rtQryPage").val();
						if(rtQryPage == ""){
						    rtQryPage = "1";
						}
						$("#rtQryPage").val(1);
						// 判断是否需要保留当前页面状态
						var page = sessionStorage.getItem(window.location.pathname);
						if(page){
						  // 跳转到当前页面之前保存的状态
						  QueryTable_${id}.query(parseInt(page));
						}else{
						  QueryTable_${id}.query(parseInt(rtQryPage));
						}
						//QueryTable_${id}.query(1);
					} else {
						removeTableBody();
						addToTableBody("查询结果为空");
					}
				});
			} else {
				var pagedata = new Array({"name":"page", "value":page});
		  		$.post("${action}", pagedata.concat(formData), function (data) {
		  			if (data.error_no == "0") {
		  				QueryTable_${id}.drawTableBody(data.resultList);
						QueryTable_${id}.jumpTo(page);
						
						// 将数据集合赋值到当前对象
						QueryTable_${id}.dataList=data.resultList;
		  			} else {
		  				removeTableBody();
		  				addToTableBody(data.error_info);
		  			}
				});
			}
		},
		lastPage : function () {
			var pn = eval($("#pagenav_${id} .thisPage").text());
			if(pn <= 1) {
				return false;
			}
	  		queryPage(pn - 1);
		},
		nextPage : function () {
			var pn = eval($("#pagenav_${id} .thisPage").text());
			if(pn >= QueryTable_${id}.paginator.totalPages) {
				return false;
			}
	  		queryPage(pn + 1);
		},
		goPage : function () {
			if ($("#pagenav_${id} .page-go").val() == "") {
				return false;
			}
		  	var gp = eval($("#pagenav_${id} .page-go").val());
		  	queryPage(gp);
		},
		jumpTo : function (page) {
	  		$("#pagenav_${id}").show();
	  		QueryTable_${id}.drawPaginator(page);
	  		$("#page" + page + "_${id}").find("a").addClass("thisPage");
	  	},
	  	drawTableBody : function (data) {
	  		removeTableBody();
	  		<#-- 行 -->
		  	for (var i = 0; i < data.length; i++) {
		  		var _tr;
		  		<#-- 奇偶行设置不同背景色 -->
		  		if (i % 2 == 1) {
		  			_tr = $("<tr></tr>");
		  		} else {
		  			_tr = $("<tr class='even'></tr>");
		  		}
		  		<#-- 判断是否有子内容或单元格是否可编辑 -->
		  		<#if querySub.querySubCondition?? || cellEdit>
		  		    var item = data[i];
		  		    _tr.append("<td><#if querySub.querySubCondition??><span class='glyphicon glyphicon-plus table-sub sub-content' data-id='"+ item["${querySub.querySubCondition}"] +"'></span></#if><#if cellEdit><span class='glyphicon glyphicon-pencil cell-edit-row cell-edit-pencil' title='批量行修改'></span></#if></td>");
		  		</#if>
		  		
		  		<#-- 增加复选框 (isMultiple==true控制是否显示)-->
		  		<#if isMultiple>
		  		        _tr.append("<td><input type='checkbox' class='tdCheckbox'></td>");
			    </#if>
			    
		  		<#-- 列 -->
		  		for (var h = 0; h < QueryTable_${id}.headItems.length - 1; h++) {
		  			var tmp_head = QueryTable_${id}.headItems[h];
		  			var _td;
		  			if (tmp_head["class"]) {
				  		_td = $("<td title='"+ QueryTable_${id}.drawItem(data[i], tmp_head) +"' name_id='"+ tmp_head["id"] +"' class='" + tmp_head["class"] + "'></td>");
		  			} else {
				  		_td = $("<td title='"+ QueryTable_${id}.drawItem(data[i], tmp_head) +"' name_id='"+ tmp_head["id"] +"''></td>");
		  			}
		  			<#-- 是否能双击编辑修改 -->
		  			if(tmp_head["edit"]){
		  			   _td.addClass("edit-dbclick");
		  			   _td.attr("title","双击可编辑");
		  			}
		  			
		  			<#-- 奇偶行设置不同背景色 -->
		  			if(!_tr.hasClass("even")){
				  		if (h % 2 == 1) {
				  		    <#if isMultiple == false>
				  				_td.addClass("class","even");
				  			</#if>
				  		} else {
				  		   <#if isMultiple>
				  				_td.addClass("class","even");
				  			</#if>
				  		} 
				    }
			  		
		  			// 判断是否需要显示图片
		  			if(tmp_head["type"] == "img"){
		  				if(QueryTable_${id}.drawItem(data[i], tmp_head) != ""){
		  					_td.append("<img class='detail-img' src='"+ QueryTable_${id}.drawItem(data[i], tmp_head) +"' />");
		  				} else {
		  					_td.append("<img class='detail-img' src='https://www.qhzhlkj.com/files/image/qhz/no_img.png' />");
		  				}
		  			   
		  			} else {
		  			    // 超过多少50个字符加省略号
		  			    if((QueryTable_${id}.drawItem(data[i], tmp_head)) && (QueryTable_${id}.drawItem(data[i], tmp_head)).length > 50){
		  			        _td.removeAttr("title");
		  			        _td.html("<div class='app-row-between-layout'><div class='table-ellipsis'>"+QueryTable_${id}.drawItem(data[i], tmp_head)+"</div><div class='table-ellipsis-show'><img src='${base}/image/home/right/right-table-ellipsis-show.png' title='展开'></div><div class='table-ellipsis-content'>"+QueryTable_${id}.drawItem(data[i], tmp_head)+"</div></div>");
		  			    } else {
		  			        _td.html(QueryTable_${id}.drawItem(data[i], tmp_head));
		  			    }
		  			}
		  			_tr.append(_td);
		  		}
		  		<#-- 最后一列特殊处理 -->
				var tmp_head = QueryTable_${id}.headItems[QueryTable_${id}.headItems.length - 1];
	  			var _td;
	  			if (tmp_head["class"]) {
	  				_td = $("<td class=" + tmp_head["class"] + "></td>");
	  			} else {
	  				_td = $("<td></td>");
	  			}
	  			
	  			_td.html(QueryTable_${id}.drawItem(data[i], tmp_head));
	  			_tr.append(_td);
				
		  		$("#ajaxQueryTable_${id} tbody").append(_tr);
		  	}
		  	
		  	var th_height = $("#ajaxQueryTable_${id} thead tr").height();
		  	var td_height = $("#ajaxQueryTable_${id} tbody tr").height();
	  		$("#left_table thead tr th").css("height",th_height);
	  		$("#left_table tbody tr td").css("height",td_height - 1);
	  	},
	  	drawHref : function (btnObj, classcontent) {
	  	    var rtnATag = $("<a>"+ $(btnObj).html() +"</a>"); 
	  	    $.each(btnObj.attributes, function() {
				if (this.specified) {
					$(rtnATag).attr(this.name,this.value);
				}
			});
	  	    $(rtnATag).attr("class",classcontent);
	  	    if(!$(btnObj).attr("onclick") && '${openInNewTab!''}'=='1'){
	  	    	$(rtnATag).attr("target","_blank");
	  	    	var newHref = "/blank.htm?redirect_url="+$(btnObj).attr("href");
	  	   		$(rtnATag).attr("href",newHref);
	  	    }
	  		return $(rtnATag)[0].outerHTML;
	  	},
	  	drawItem : function (row, head) {
	  		
	  		var _value = "";
	  		if (typeof head["id"] === "string") {
	  			
	  			var array = head["id"].split(".");
	  			var object = row;
	  			for (var i = 0; i < array.length; i++) {
	  				
	  				try {
	  					if (i == (array.length - 1)) {
		  					_value = object[array[i]];
		  					break;
		  				}
		  				
		  				object = object[array[i]];
	  				} catch (err) {
						console.log(err);	  	
						_value = "";
						break;
	  				}
	  			}
	  		} else {
	  			_value = row[head["id"]];
	  		}
	  		
	  		var provider = head["provider"];
	  		if (provider) {
	  			try {
	  				return eval(provider).apply(this, arguments);
	  			} catch (e) {}
	  		}
	  		var dic_key = head["dic_key"];
	  		<#-- 转换字典，可处理逗号分隔的情况 -->
	  		if (_value && dic_key && QueryTable_${id}.dicts[dic_key]) {
	  			var valueCaption = "";
	  			var dictCaps = QueryTable_${id}.dicts[dic_key];
	  			var valArr = _value.toString().split(",");
	  			
	  			for (var di = 0; di < valArr.length; di++) {
	  				if (dictCaps[valArr[di]] == null) {
	  					valueCaption = valueCaption + (di != 0 ? ", " : "") + valArr[di];
	  				} else {
	  					valueCaption = valueCaption + (di != 0 ? ", " : "") + dictCaps[valArr[di]];
	  				}
	  			}
	  			return valueCaption;
	  		}
	  		return typeof _value == 'string' ? (_value ? _value.replace(/,/g,", ") : _value) : _value;
	  	},
	  	drawPaginator : function (page) {
	  		var data = QueryTable_${id}.paginator;
	  		var avg = 2;
	  		var pageUL = $("<ul id='drawPaginator_${id}' class='bottom-d'></ul>");
	  		
	  		pageUL.append("<li class='bottom-right'><a href='javascript:void(0)' style='width: 50px' onclick='QueryTable_${id}.lastPage()'><span>上一页</span></a></li>");
		  	pageUL.append("<li class='bottom-right' id='page1_${id}'><a href='javascript:void(0)' class='bottom-number' onclick='queryPage(1)'>1</a></li>");
		  	if (page > avg + 2) {
		  		pageUL.append("<li class='bottom-right'>. . .</li>");
		  	}
	  		for (var _page = (page - avg); _page < (page + avg); _page++) {
		  		if (_page > 1 && _page <data.totalPages) {
		  			pageUL.append("<li id='page"+_page+"_${id}' class='bottom-right'><a href='javascript:void(0)' class='bottom-number' onclick='queryPage("+_page+")'>"+_page+"</a></li>");
		  		}
		  	}
		  	if (page + avg < data.totalPages) {
		  		pageUL.append("<li class='bottom-right'>. . .</li>");
		  	}
		  	if (data.totalPages > 1) {
		  		pageUL.append("<li id='page"+data.totalPages+"_${id}' class='bottom-right'><a href='javascript:void(0)' class='bottom-number' onclick='queryPage("+data.totalPages+")'>"+data.totalPages+"</a></li>");
		  	}
		  	pageUL.append("<li class='bottom-right'><a href='javascript:void(0)' style='width: 50px' onclick='QueryTable_${id}.nextPage()'><span>下一页</span></a></li>");
		  	pageUL.append("<li class='bottom-right'>到</li>");
		    pageUL.append("<li class='bottom-right'><input class='bottom-input page-go' type='text'><span class='bottom-right'>页</span></li>");
		    pageUL.append("<li class='bottom-right'><a href='javascript:void(0)' class='bottom-go' onclick='QueryTable_${id}.goPage()'>确定</a></li>");
		    pageUL.append("<li class='bottom-right'><span>总  <span>"+data.totalPages+"<span>  页</span></li>");
		    $("#pagenav_${id}").html(pageUL);
		    $("#drawPaginator_${id}").before("<ul><li class='bottom-left'><span>共<i class='buttom-i'>"+ data.totalItems +"</i>条记录 ，当前显示第<i class='buttom-i'>"+ page +"</i>页</span></ul>");
	  	}
	}
	
	/**
	 * 移除Table内容
	 */
	function removeTableBody () {
		$("#ajaxQueryTable_${id} tbody tr").remove();
		$("#ajaxQueryTable_${id} tbody tr").remove();
	}
	
	/**
	 * 像Table内添加内容
	 */
	function addToTableBody (content) {
	    <#if isMultiple>
			$("#ajaxQueryTable_${id} tbody").append("<tr><td colspan=${headItems?size + 1}>" + content + "</td></tr>");
		<#else>
		    $("#ajaxQueryTable_${id} tbody").append("<tr><td colspan=${headItems?size}>" + content + "</td></tr>");	
		</#if>
	}
	// 查询按钮
	function queryPage(page){
		var result=true;
		if (typeof ${queryCondition} =="function") {
			result= ${queryCondition}.apply(this);
		}
		if(result){
			QueryTable_${id}.query(page);
			if(page > 0){
			  // 当前窗口缓存当前页面
	  		  sessionStorage.setItem(window.location.pathname,page);
			}
		}
	}
	
	// 获取所有选中值
	function getAllCheckboxs(){
	    // 判断多选条件是否为空数组
	    var conditionArray = QueryTable_${id}.multipleCondition;
	    var count = conditionArray.length;
	    var dataArray = [];
	    if(count > 0){
	        $(".tdCheckbox").each(function(index,ele){
	           if($(this).prop("checked")){
	               // 获取当前元素所在checkbox中的索引
	               var index = $('.tdCheckbox').index(this);
	               var tr_element =  $(this).parent().parent();
		           var data = "";
		           for(var i = 0 ; i < count; i ++){
		               if(i == count - 1){
		                  // 判断显示的表格中是否存在相应数据
		                  if(tr_element.find("[name_id='"+ conditionArray[i] +"']").text().length > 0){
		                     data = data + tr_element.find("[name_id='"+ conditionArray[i] +"']").text();
		                  } else {
		                     var temp_id = conditionArray[i];
		                     data = data + (QueryTable_${id}.dataList[index])[temp_id];
		                  }
		                  
		               } else {
		                  // 判断显示的表格中是否存在相应数据
		                  if(tr_element.find("[name_id='"+ conditionArray[i] +"']").text().length > 0){
		                     data = data + tr_element.find("[name_id='"+ conditionArray[i] +"']").text()+"-";
		                  } else {
		                     var temp_id = conditionArray[i];
		                     data = data + (QueryTable_${id}.dataList[index])[temp_id] + "-";
		                  }
		                  
		               }
		           }
		           dataArray.push(data);
	           }
            });
	        
	    } else {
	       devShowDialog("请配置复选框操作条件");
	       return;
	    }
	    if(dataArray.length == 0){
	       devShowDialog("请批量选择后再操作");
	       return;
	    }
	    return dataArray.toString();
	}
	$(function(){
       <#if auto>
          QueryTable_${id}.query(0);
	   </#if>
	   <#-- 绑定查询按钮事件 -->
		<#if buttons?? && (buttons?size>0)>
			<#-- 有按钮属性，为第一个绑定查询事件 -->
			<#list buttons?keys as key>
				$("button#${key?split("#")[1]}").click(function () {
				    // 清除当前页面缓存
				    sessionStorage.removeItem(window.location.pathname);
					queryPage(0);
				
				});
				<#break />
			</#list>
		<#else>
			<#-- 按钮属性buttons为空时为id="btn_qry_${id}"绑定查询事件 (适应自己定义按钮的情况) -->
			$("button#btn_qry_${id}").click(function () {
				queryPage(0);
			});
		</#if>
		
		// 选择所有checkbox
		$("#allCheckboxs").click(function(){
		    if($(this).prop('checked')){
		       $(".tdCheckbox").prop("checked",true);
		    } else {
		       $(".tdCheckbox").prop("checked",false);
	        }
		})
		
		// 点击添加按钮时删除当前页面缓存
		$(".input-group-btn").click(function(){
		   if($(this).find("a").length > 0 && $(this).find("a").attr("href").indexOf("op_type=1") != -1){
		      // 清除当前页面缓存
		      sessionStorage.removeItem(window.location.pathname);
		   }
		})
		
		// 判断查询条件是否超过3个
		if($(".search-dl").length > 3){
		   $(".zoom-btn").show();
		   $(".search-dl").each(function(index,element){
		       if(index >= 3){
		          $(this).addClass("aql-display");
		       }
		   })
		   $(".search-box").find(".aql-display").hide();
		}
		
		//展开  收起功能
	    $(".zoom-btn").on("click",function(event) {
	      event.preventDefault();
	      var _this = $(this);
	      var a_tag = _this.find("a")
	      if (a_tag.attr("class") == undefined || a_tag.attr("class").indexOf("open") < 0) {
	        _this.find('a').html('收起').append('<i></i>').addClass('open');
	        a_tag.find('i').addClass('open');
	        $(".search-box").find(".aql-display").show();
	      } else {
	          _this.find('a').html('展开').append('<i></i>').removeClass('open');
	          a_tag.find('i').removeClass('open');
	          $(".search-box").find(".aql-display").hide();
	      }
	    });
	    
	    // 默认隐藏单元格编辑按钮
	    $(".toast-tip").hide();
	    
	    // td内容展开后的索引
	    var showIndex;
	    
	    // 表格事件
	    $("table").on("mouseover","thead th,tbody td",function(){
	        // 鼠标滑过表格增加编辑图标
	        // $(this).find(".cell-edit-pencil").show();
	    }).on("mouseout","thead th,tbody td",function(){
	       // 鼠标移出隐藏编辑图标
	       // $(this).find('.cell-edit-pencil').hide(); 
	    }).on("click","thead .cell-edit-pencil,thead .cell-edit-pencil-sub",function(event){
	       // 批量列修改添加点击事件
	       // 批量编辑输入框添加
	       var isClassName = $(event.target).hasClass("cell-edit-pencil-sub");
	       var th_index = $(this).parent().index();
	       $(this).parent().parent().parent().next("tbody").children("tr").not(".tr-sub").each(function(index,ele){
	          var tempValue = $(this).find("td:eq("+ th_index +")").text();
	          if($(this).find("td:eq("+ th_index +")").find("input").length > 0){
	            tempValue = $(this).find("td:eq("+ th_index +")").find("input").val();
	          }
	          if(isClassName){
	             $(this).find("td:eq("+ th_index +")").html("<input type='text' class='cell-input cell-input-sub' value='"+ tempValue +"' />");
	          } else {
                $(this).find("td:eq("+ th_index +")").html("<input type='text' class='cell-input' value='"+ tempValue +"' />");
	          }
	       })
	       // 修改编辑图标
	       var parenfSelf = $(this).parent(); 
	       $(this).remove();
	       if(isClassName){
	          parenfSelf.append("<span class='glyphicon glyphicon-ok cell-edit cell-edit-ok-sub' title='批量列保存'></span>");
	       } else {
              parenfSelf.append("<span class='glyphicon glyphicon-ok cell-edit cell-edit-ok' title='批量列保存'></span>");
	       }
	       parenfSelf.find(".cell-edit-ok").show();
	    }).on("click","thead .cell-edit-ok,thead .cell-edit-ok-sub",function(event){
	        var request_param = {};// 请求参数条件
	        var isClassName = $(event.target).hasClass("cell-edit-ok-sub");// 获取单前对象
	        // 单元格格待编辑名称/值
	        $(this).parent().parent().find(".edit-dbclick").each(function(index,ele){
	           var cell_name = $(this).attr("name_id");
	           var cell_value = $(this).find(".cell-input").val();
	           request_param[cell_name] = cell_value;
	        })
	        
	        var self = $(this);
	        
	        // 单元格编辑条件
	        var conditionArray = QueryTable_${id}.updateCellCondition;
	        if(isClassName){
		        conditionArray = QueryTable_${id}.querySub.updateSubCellCondition;
	        } 
	        var count = conditionArray.length;
	        
	        if(count > 0){
	           for(var i = 0 ; i < count; i ++){
	                  var name = self.parent().parent().find("[name_id='"+ conditionArray[i] +"']").attr("name_id");
	                  var value = self.parent().parent().find("[name_id='"+ conditionArray[i] +"']").text();
	                  request_param[name] = value;
	           }
		    } else {
		       devShowDialog("请选择单元格编辑条件");
		       return;
		    }
		    
	           var th_index = $(this).parent().index();
		       $(this).parent().parent().parent().next("tbody").find("tr").each(function(index,ele){
		          $(this).find("td:eq("+ th_index +")").html($(this).find("td:eq("+ th_index +")").find(".cell-input").val());
		       })
		       var parenfSelf = $(self).parent();
			   $(self).remove();
			   if(isClassName){
			      parenfSelf.append("<span class='glyphicon glyphicon-pencil cell-edit cell-edit-pencil-sub' title='批量列编辑'></span>");
		       } else {
		          parenfSelf.append("<span class='glyphicon glyphicon-pencil cell-edit cell-edit-pencil' title='批量列编辑'></span>");
		       }
			   parenfSelf.find(".cell-edit-pencil").show();
		    
	    }).on("click","tbody .cell-edit-pencil,tbody .cell-edit-pencil-sub",function(event){
	       // 批量行修改添加点击事件
	       // 批量编辑输入框添加
	       var isClassName = $(event.target).hasClass("cell-edit-pencil-sub");// 获取单前对象
	       $(this).parent().parent().find(".edit-dbclick").each(function(index,ele){
	          var tempValue = $(this).text();
	          if($(this).find("input").length > 0){
	            tempValue = $(this).find("input").val();
	          }
	          if(isClassName){
	             $(this).html("<input type='text' class='cell-input cell-input-sub' value='"+ tempValue +"' />");
	          } else {
	             $(this).html("<input type='text' class='cell-input' value='"+ tempValue +"' />");
	          }
	         
	       })
	       // 修改编辑图标
	       var parenfSelf = $(this).parent();
	       $(this).remove();
	       if(isClassName){
             parenfSelf.append("<span class='glyphicon glyphicon-ok cell-edit-row cell-edit-ok-sub' title='批量行保存'></span>");
           } else {
             parenfSelf.append("<span class='glyphicon glyphicon-ok cell-edit-row cell-edit-ok' title='批量行保存'></span>");
           }
	       parenfSelf.find(".cell-edit-ok").show();
	       
	    }).on("click","tbody .cell-edit-ok,tbody .cell-edit-ok-sub",function(event){
	        var request_param = {};// 请求参数条件
	        // 单元格格待编辑名称/值
	        var isClassName = $(event.target).hasClass("cell-edit-ok-sub");// 获取单前对象
	        $(this).parent().parent().find(".edit-dbclick").each(function(index,ele){
	           var cell_name = $(this).attr("name_id");
	           var cell_value = $(this).find(".cell-input").val();
	           request_param[cell_name] = cell_value;
	        })
	        
	        var self = $(this);
	        
	        // 单元格编辑条件
	        var conditionArray = QueryTable_${id}.updateCellCondition;
	        // 判断是否是子项单元格内容修改
	        if(isClassName){
		        conditionArray = QueryTable_${id}.querySub.updateSubCellCondition;
	        } 
	        var count = conditionArray.length;
	        
	        if(count > 0){
	           for(var i = 0 ; i < count; i ++){
	                  var name = self.parent().parent().find("[name_id='"+ conditionArray[i] +"']").attr("name_id");
	                  var value = self.parent().parent().find("[name_id='"+ conditionArray[i] +"']").text();
	                  request_param[name] = value;
	           }
		    } else {
		       devShowDialog("请选择单元格编辑条件");
		       return;
		    }
		    
	           $(self).parent().parent().find(".edit-dbclick").each(function(index,ele){
		           $(this).html($(this).find(".cell-input").val());
		       })
		       var parenfSelf = $(self).parent();
			     $(self).remove();
			     if(isClassName){
		             parenfSelf.append("<span class='glyphicon glyphicon-pencil cell-edit-row cell-edit-pencil-sub' title='批量行编辑'></span>");
		         } else {
		            parenfSelf.append("<span class='glyphicon glyphicon-pencil cell-edit-row cell-edit-pencil' title='批量行编辑'></span>");
		         }
			     parenfSelf.find(".cell-edit-pencil").show();
		    // 保存编辑的值
		   // $.ajaxPost("${updateCellUrl}",request_param,function(data){
		    //     var error_info = data.error_info || "修改成功";
		    //     var parenfSelf = $(self).parent();
			//     $(self).remove();
			//     parenfSelf.append("<span class='glyphicon glyphicon-pencil cell-edit-row cell-edit-pencil' title='批量行编辑'></span>");
			//     parenfSelf.find(".cell-edit-pencil").show();
		    //     toastTipShow();
		    //});
		    
	    }).on("dblclick","tbody .edit-dbclick",function(event){
	        // 双击添加编辑输入框
	        var className = $(event.target);
	        // 判断是否是输入框状态(不进行操作)
	        if($(this).find("input").hasClass("cell-input")){
	           return false;
	        }
	        var value = $(this).text() || " ";
	        if(className.hasClass("edit-dbclick-sub")){
	            $(this).html("<input type='text' class='cell-input cell-input-sub' value='"+ value +"' />");
	        } else {
		        $(this).html("<input type='text' class='cell-input' value='"+ value +"' />");
	        }
	        $(this).find(".cell-input").focus();
	        $(this).find(".cell-input").val("");
	        $(this).find(".cell-input").val(value);
	        
	    }).on("blur","tbody .cell-input",function(event){
	        // 阻止事件冒泡
	        event.stopPropagation();
	        // 单元格格待编辑名称
	        var cell_name = $(this).parent().attr("name_id");
	        // 单元格格待编辑值
	        var cell_value = $(this).val() || " ";
	        var self = $(this);
	        var className = $(event.target);
	        // 单元格编辑条件
	        var conditionArray = QueryTable_${id}.updateCellCondition;
	        var reuqest_url = "${updateCellUrl}";
	        // 判断是否是子项单元格内容修改
	        if(className.hasClass("cell-input-sub")){
		        conditionArray = QueryTable_${id}.querySub.updateSubCellCondition;
		        reuqest_url = QueryTable_${id}.querySub.updateSubCellUrl;
	        } 
	        var count = conditionArray.length;
	        var request_param = {};// 请求参数条件
	        request_param[cell_name] = cell_value;
	        if(count > 0){
	           for(var i = 0 ; i < count; i ++){
	                  var name = self.parent().parent().find("[name_id='"+ conditionArray[i] +"']").attr("name_id");
	                  var value = self.parent().parent().find("[name_id='"+ conditionArray[i] +"']").text();
	                  request_param[name] = value;
	           }
		    } else {
		       devShowDialog("请选择单元格编辑条件");
		       return;
		    }
		    // 保存编辑的值
		    $.ajaxPost(reuqest_url,request_param,function(data){
		         var error_info = data.error_info || "修改成功";
		         $(self).parent().html(cell_value);
		         // 消息提示
		         $.toast(error_info);
		    });
	       
	    }).on("click","tbody .sub-content",function(){
	        // 行的子内容查询
	        var colLength =  $(this).parent().parent().find('td').length;
			var id = $(this).data("id");// 查询条件
			if(!$(this).parent().parent().next().hasClass('tr-sub')){
				var trArr = [];
				trArr.push("<tr class='tr-sub'>");
				trArr.push("<td class='td-sub' style='padding:10px;padding-left: 54;' colspan='"+ colLength +"'>");
				trArr.push("</td>");
				trArr.push("</tr>");
				$(this).parent().parent().after(trArr.join(" "));
				
				// 判断是否为回调函数
				var subCallback = eval(QueryTable_${id}.querySub.subCallback);
		        var self = this;
		        if(typeof subCallback == "function"){
		           // 内容变化个性化回调函数
		           subCallback(id,$(this).parent().parent().next().find(".td-sub"));
		        }
		        $(this).removeClass("glyphicon-plus");
		        $(this).addClass("glyphicon-minus");
			} else {
				$(this).parent().parent().next().remove();
				$(this).removeClass("glyphicon-minus");
		        $(this).addClass("glyphicon-plus");
			}
	        	       
	    }).on("click","tbody .table-ellipsis-show",function(e){
	        // 根据点击的展开的元素的位置动态显示弹出框的高度
	        e = e || window.event;
	        var contentTop = e.clientY + 21 + $(document).scrollTop();
	        if(showIndex != $(".table-ellipsis-show").index($(this))){
	           // 隐藏其他的显示内容的元素
	           console.info($(".table-ellipsis-show").length);
	           console.info($(".table-ellipsis-show").index($(this)));
	           $(".table-ellipsis-content").hide();
	           $(".table-ellipsis-show").children().attr("title","展开").removeClass("active").addClass("restore");
	        }
	        // 点击元素的索引
	        showIndex = $(".table-ellipsis-show").index($(this));
	        
	        // 当前点击元素判断
	        if($(this).children().hasClass("active")){
	           $(this).children().attr("title","展开").removeClass("active").addClass("restore");
	           $(this).next().hide();
	        } else {
	           $(this).children().attr("title","收起").removeClass("restore").addClass("active");
	           $(this).next().css({"top":contentTop}).show();
	        }
	        
	    })
   })
      
      // 键盘控制开始和结束(回车键【Enter】控制搜索) 
	  $(document).keypress(function (e) {
	   var keyCode = e.keyCode || e.which || e.charCode;
	   var shiftKey = e.shiftKey || e.metaKey;
	   var ctrlKey = e.ctrlKey || e.metaKey;
	    // 空格控制(开始和结束)
	    if (keyCode == 13){
	       queryPage(0);
	       // 阻止浏览器默认行为
	       e.preventDefault();
	    }
	    
	})
</@script>
</#macro>


<#--
    @name dateTimePicker	时间控件宏
    @name 传入的id值
    @timeValue 表单的value值
-->
<#macro dateTimePicker
		id name timeValue placeholder valid  disabled="false">
<link rel="stylesheet" href="${base}/css/bootstrap-datetimepicker.min.css" type="text/css">
<#-- 时间控件 -->
<@script src="${base}/js/bootstrap-datetimepicker.js" />
<@script src="${base}/js/bootstrap-datetimepicker.zh-CN.js" />		
	<input id="${name}" name="${name}" type="text" readonly="readonly" valid="${valid!''}" class="dfinput" placeholder="${placeholder!''}" <#if disabled=="true">disabled="disabled"</#if>  value="<#if timeValue??>${timeValue?string('yyyy-MM-dd HH:mm:ss')}</#if>" /> 
<@script>	
      $("#${name}").datetimepicker({
		  format: 'yyyy-mm-dd hh:ii:ss',
		  language: 'zh-CN',
		  startDate:new Date(),
		  showSecond: true, //显示秒
		  stepHour: 1,//设置步长
		  stepMinute: 1,
		  stepSecond: 1,
		  autoclose: true,//选中自动关闭
	      todayBtn: true,//显示今日按钮
	  });  
</@script>
</#macro>

<#--
    @name dateTimePickerByDay	时间控件宏(年月日)
    @name 传入的id值
    @timeValue 表单的value值
-->
<#macro dateTimePickerByDay
		id name timeValue placeholder valid  disabled="false">
<link rel="stylesheet" href="${base}/css/bootstrap-datetimepicker.min.css" type="text/css">
<#-- 时间控件 -->
<@script src="${base}/js/bootstrap-datetimepicker.js" />
<@script src="${base}/js/bootstrap-datetimepicker.zh-CN.js" />			
	<input id="${name}" name="${name}" type="text" readonly="readonly" valid="${valid!''}" class="dfinput" placeholder="${placeholder!''}" <#if disabled=="true">disabled="disabled"</#if>  value="<#if timeValue??>${timeValue?string('yyyy-MM-dd')}</#if>" /> 
<@script>	
      $("#${name}").datetimepicker({
      	  minView: "month",//设置只显示到月份
		  format: 'yyyy-mm-dd',
		  language: 'zh-CN',
		  stepHour: 1,//设置步长
		  stepMinute: 1,
		  stepSecond: 1,
		  autoclose: true,//选中自动关闭
	      todayBtn: true,//显示今日按钮
	  });  
</@script>
</#macro>

<#--
    @name dateTimePickerByDay	时间控件宏(小时)
    @name 传入的id值
    @timeValue 表单的value值
-->
<#macro dateTimePickerByHour
		id name timeValue placeholder valid  disabled="false">
<link rel="stylesheet" href="${base}/css/bootstrap-datetimepicker.min.css" type="text/css">
<#-- 时间控件 -->
<@script src="${base}/js/bootstrap-datetimepicker.js" />
<@script src="${base}/js/bootstrap-datetimepicker.zh-CN.js" />			
	<input id="${name}" name="${name}" type="text" readonly="readonly" valid="${valid!''}" class="dfinput" placeholder="${placeholder!''}" <#if disabled=="true">disabled="disabled"</#if>  value="<#if timeValue??>${timeValue}</#if>" /> 
<@script>	
      $("#${name}").datetimepicker({
		  language: 'zh-CN', // 中文显示
		  format: 'hh:00',// 只显示（时间：分钟）
		  weekStart: 1, // 从星期一开始
          todayBtn:  0, // 不显示当天按钮
          autoclose: 1,// 选择完成之后自动关闭
          todayHighlight: false, // 当天按钮显示高亮
          startView: 1, // 从当天开始
          minView: 2, // 从当天显示小时开始
          maxView: 1, // 最大显示当天
          forceParse: 0 // 是否强制解析输入框中的值 并将解析后的正确值按照给定的格式format设置到输入框中
	  }).on("hide",function(){
	      $(".table-condensed").find(".switch").html("");  
	  }).on("show",function(){
	      $(".table-condensed").find(".switch").html(""); 
	  });
</@script>
</#macro>

<#--
    @name messageAlert 消息提示框
-->
<#macro messageAlert>
    <div class="modal small fade" id="tipModal" tabindex="-1" role="dialog" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h4 class="modal-title">提示</h4>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
	      </div>
	      <div class="modal-body">规格最多设置5项</div>
	    </div>
	  </div>
	</div>
<@script>

     function messageAlert(message){
         $("#tipModal").find(".modal-body").html(message);
         $("#tipModal").modal('show');
     }
      	
</@script>
</#macro>

<#-- 
    @name fileSyncUpload 文件同步上传
    @name {String} 唯一标识
    @title 图片上传标题
    @imageUrl 图片显示路径
    @previewTitle 预览标题
    @isMust 是否显示文件必须上传
 -->
<#macro fileSyncUpload name title imageUrl previewTitle isMust>
 <style>
.fileinput{
	width: 250px;
	height:32px;
	padding-top:4px;
	padding-left:10px;
	float: left;
	border: 1px solid #A7B5BC;
}
#f{
	height: 32px;
	line-height: 32px;
}
#preview {
    width: 250px;
    height: 170px;
    border-radius: 5px;
}

</style>
	<label for="level" class="label" id="image-label"><#if isMust><em style="color:red;">*</em></#if><#if title??>${title!''}<#else>图片上传</#if></label>
	<div class="app-column-center-layout">
		<img id= "preview" class="preview" src="" style="display:none"/>
	</div>
	<div style="height:170px;display:flex;flex-direction:column;justify-content:flex-end;align-items:center;">
		<div class="app-row-start-left">
			<input style="margin-left:20px;" id="imageUpload" class="btn btn-success" type="button" value="<#if imageUrl??>重新上传<#else>上传图片</#if>">
			<i id="f">&nbsp;请上传图片格式文件 : jpg,png,bmp等</i>
		</div>
		<input id="file" name="<#if name??>${name!''}<#else>file</#if>" type="file" class="fileinput" style="display:none"/>
	</div>
 <@script>
 
 $(function(){
  // 显示图片
   var imageUrl = "${imageUrl!''}";
  // 选择图片 
  $("#imageUpload").click(function(){
      $("#file").click();// 触发文件上传功能
  })
  $("#file").change(function (e) {
        var fileMaxSize=1024;// 图片最大为1M
	    var filepath = $("#file").val();
	    var extStart = filepath.lastIndexOf(".");
	    var ext = filepath.substring(extStart, filepath.length).toUpperCase();
	    if(filepath==""){
	    	$("#f").text("请上传图片格式文件 : jpg,png,bmp等");
	        $("#f").css("color","#7F7F7F");
	    }else if (ext != ".BMP" && ext != ".PNG" && ext != ".JPG" && ext != ".JPEG") {
	        $("#f").text("图片限于jpg,png,bmp,jpeg格式");
	        $("#f").css("color","red");
	    } else {
	    
	        var fileData = document.getElementById('file').files[0];// 获取图片资源
	        var fileSize = fileData.size; // 获取图片大小
	        if(fileSize >fileMaxSize * 1024 ){
	           $("#f").text("图片大小不能超过1M");
	           $("#f").css("color","red");
	          // devShowDialog("图片大小不能超过1M");
	        } else {
	           $("#f").text("文件格式正确");
	           $("#f").css("color","green");
	        
	           var file = e.target.files[0]
	           preview(file);
	          
	          <#--$.ajaxFileUpload({
                fileElementId: 'file',    //需要上传的文件域的ID，即<input type="file">的ID。
                url: '/system/ywImages/img_upload.json', //后台方法的路径
                type: 'post',   //当要提交自定义参数时，这个参数要设置成post
                dataType: 'json',   //服务器返回的数据类型。可以为xml,script,json,html。如果不填写，jQuery会自动判断。
                secureuri: false,   //是否启用安全提交，默认为false。
                async : true,   //是否是异步
                success: function(data) {   //提交成功后自动执行的处理函数，参数data就是服务器返回的数据。
                console.log(data);
                    $("#yulan").show();
				    $("#f").text("文件格式正确");
				    $("#f").css("color","green");
				    $('#preview').empty().append("<img src='"+ data.image_url +"'/>");
                }
               
              });-->
	        }
	    	
	    }
    })
   if(imageUrl != ""){
	    $("#preview").show();
	    $("#f").text("文件格式正确");
	    $("#f").css("color","green");
	    $('#preview').attr("src",imageUrl);
	}
   
})

//图片上传预览
function preview(file) {
	$("#preview").show();
    var img = new Image();
    url = img.src = URL.createObjectURL(file);
    img.id="preview";
    img.class="preview"
    var $img = $(img)
    img.onload = function() {
        URL.revokeObjectURL(url)
        $("#image-label").next().remove();
        $('#image-label').after($img);
        $("#imageUpload").val("重新上传");
    }
}
</@script>	 
 </#macro>
 
 <#-- 
    @name fileAsynUpload 文件异步上传
    @name {String} 唯一标识
    @title {String} 标题
    @uploadUrl {String} 文件上传url
    @showUrl {String} 文件显示url
 -->
<#macro fileAsynUpload name 
        id=name title="" uploadUrl="${base}/system/ywImages/ueditor_file_upload.json" showUrl="${base}/system/ywImages/listimage.json" >
	<li>
		<script type="text/plain" id="${id}"></script>
	</li>
 <@script>
 // 文本编辑器自定义文件上传和显示
UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;  
UE.Editor.prototype.getActionUrl = function(action) {  
    if (action == 'uploadimage' || action == 'uploadfile') {
        // 修改文件上传路径  
        var id = $('#carInfoId').val();  
        return "${uploadUrl}";  
    }else if (action == 'listimage' || action == 'listfile'){
        // 修改文件显示路径
        return "${showUrl}";  
    }else {
        // 文件处理默认路径  
        return this._bkGetActionUrl.call(this, action);  
    }  
}; 
var _editor;
var $image_button; // 图片按钮对象
var $image_func; // 图片按钮回调函数
 $(function(){                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
 //重新实例化一个编辑器，防止在上面的editor编辑器中显示上传的图片或者文件
 _editor = UE.getEditor('${id}');
	_editor.ready(function () {
	    //设置编辑器不可用
	    //_editor.setDisabled();
	    //隐藏编辑器，因为不会用到这个编辑器实例，所以要隐藏
	    _editor.hide();
	    //侦听图片上传
	    
	    _editor.addListener('beforeinsertimage', function (t, arg) {
	        // 图片预览回调
	        if(typeof $image_func == 'function'){
	           $image_func($image_button,t,arg);
	        }
	    })
	    //侦听文件上传，取上传文件列表中第一个上传的文件的路径
	    _editor.addListener('afterUpfile', function (t, arg) {
	        $("#file").attr("value", _editor.options.filePath + arg[0].url);
	    })
	    //侦听文件上传，取上传文件列表中第一个上传的文件的路径
	    _editor.addListener('aftergetcontent', function (t, arg) {
	        console.log(_editor);
	        console.log("sdfds");
	    })
	    
	});
 })

var fileAsynUpload = {
     //弹出图片上传的对话框
     upImage : function(ele,func){
        var myImage = _editor.getDialog("insertimage");
	    myImage.open();
	    $image_button = ele;
	    // 判断变量是否为函数
	    if(typeof func == 'function'){
	       $image_func = func;
	    }
     },
     //弹出文件上传的对话框
     upFiles : function(ele,func){
        var myFiles = _editor.getDialog("attachment");
	    myFiles.open();
	    $image_button = ele;
	    // 判断变量是否为函数
	    if(typeof func == 'function'){
	       $image_func = func;
	    }
     }
}
</@script>	 
 </#macro>
 
 <#-- 
    @name editor 富文本编辑器
    @name {String} 唯一标识
    @title 图片详情标题
    @content 富文本编辑器内容
 -->
<#macro editor name 
        id=name title="" content="">
<#-- 富文本编辑器 -->
<@script src="${base}/js/ueditor/ueditor.config.js"/>
<@script src="${base}/js/ueditor/ueditor.all.min.js"/>
<@script src="${base}/js/ueditor/lang/zh-cn/zh-cn.js"/>        
		<label for="level" class="label">${title}</label>
		<script id="${id}" type="text/plain" name="${name}" style="width:800px;height:300px;">${content}</script>
<@script>
 // 文本编辑器
var ue = UE.getEditor('${id}');
function clearContent(){
   ue.execCommand('cleardoc');
}

</@script>	 
 </#macro>
 
 <#--
    @name areaselect
    @param name {String} 唯一标识
    @param placeholder {String} 选择提示
    @param valid {String} 校验规则 NotBlank非空
    @param datalist {List<Map>} 取值范围 [{"option":"show1","value":"val1"},{"option":"show2","value":"val2"}]，优先级高于dic_key
    @param value {String} 选中值
    @param defaultValue {String} 默认值
    @description 省市区/县联动选择框
-->
<#macro provinceCityAreaSelect name="" id=name province="" city="" area="" placeholder="请选择" valid="" value="" defaultValue="" >
    <#if SpringContext.containsBean("ywProvincesCache") >
		<#local ywProvincesCache = SpringContext.getBean("ywProvincesCache") />
		<#local provinces = ywProvincesCache.getProvincesList()![] />
	</#if>
	<#if SpringContext.containsBean("ywCitysCache") >
		<#local ywCitysCache = SpringContext.getBean("ywCitysCache") />
		<#if provinces?size gt 0>
		    <#list provinces as p>
		       <#if p.province == province>
		         <#local province_id = p.province_id />
		         <#break/>   
		       </#if>
		    </#list>
		</#if>
		  
		<#local citys = ywCitysCache.getCitysByProvinceId(province_id)![] />
	</#if>
	<#if SpringContext.containsBean("ywAreasCache") >
		<#local ywAreasCache = SpringContext.getBean("ywAreasCache") />
		<#if citys?size gt 0>
		    <#list citys as c>
		       <#if c.city == city>
		         <#local city_id = c.city_id />
		         <#break/>   
		       </#if>
		    </#list>
		</#if>
		<#local areas = ywAreasCache.getAreasByCityId(city_id)![] />
	</#if>
    <select id="province_id" name="province" style="width:150px;height:30px;border:1px solid #A7B5BC">
	    <option value="">${placeholder!''}</option>
	    <#if provinces?size gt 0>
		    <#list provinces as p>
	           <option value="${p.province!''}" data-id="${p.province_id!''}" <#if p.province == province>selected</#if>>${p.province!''}</option>
		    </#list>
	    </#if>
	</select>
	<select id="city_id" name="city" style="width:150px;height:30px;border:1px solid #A7B5BC">
	    <option value="">${placeholder}</option>
	    <#if citys?size gt 0>
		    <#list citys as c>
	           <option value="${c.city!''}" data-id="${c.city_id!''}" <#if c.city == city>selected</#if>>${c.city!''}</option>
		    </#list>
	    </#if>
	</select>
	<select id="area_id" name="area" style="width:150px;height:30px;border:1px solid #A7B5BC">
	    <option value="">${placeholder!''}</option>
	    <#if areas?size gt 0>
		    <#list areas as a>
	           <option value="${a.area!''}" data-id="${a.area_id!''}" <#if a.area == area>selected</#if>>${a.area!''}</option>
		    </#list>
	    </#if>
	</select>
	<input id="${id}" name="${name}" type="hidden" class="dfinput" value="${value!''}">
    <@script>
    var placeholder = "${placeholder!''}";// 选择提示
    var provinceCityArea = "${value!''}";// 已经选择的省级，城市，地区信息
    var province_name = "";
    var city_name = "";
    var area_name = "";
    $(function(){
        // 省级改变事件
        $("#province_id").change(function(){
            // 清空省级，城市和县区
            $("#city_id").html("<option value=''>"+ placeholder +"</option>");
            $("#area_id").html("<option value=''>"+ placeholder +"</option>");
           // 显示城市信息
           provincesCitysAreas.getCitys($("#province_id").find("option:selected").attr("data-id"));
           
           // 省级信息拼接
           $("#${id}").val("");
           $("#${id}").val($("#province_id").find("option:selected").text());
        })
        
        // 城市信息改变
        $("#city_id").change(function(){
            // 清空城市和县区
            $("#areas").html("<option value=''>"+ placeholder +"</option>");
           // 显示省级信息
           provincesCitysAreas.getAreas($("#city_id").find("option:selected").attr("data-id"));
           
           // 省级信息拼接
           $("#${id}").val("");
           $("#${id}").val($("#province_id").find("option:selected").text()+"-"+$("#city_id").find("option:selected").text());
        })
        
        // 地区信息改变
        $("#area_id").change(function(){
           // 省级信息拼接
           $("#${id}").val("");
           $("#${id}").val($("#province_id").find("option:selected").text()+"-"+$("#city_id").find("option:selected").text()+"-"+$("#area_id").find("option:selected").text());
        })
        
    });
    // 省市区对象
    var provincesCitysAreas = {
		/**
		 * 得到省级信息
		 * province_name : 选中的省级信息
		 */
		getProvinces:function(){
			
		},
		// 得到城市信息
		getCitys:function(province_id){
			$.ajax({
			   url:"${base}/getCitysList.json",
			   type:"post",
			   data:{province_id:province_id},
			   dataType:"json",
			   success:function(data){
			        if (data.error_no == 0) {
		              var resultList = data.resultList;
		              var html = [];
		              html.push("<option value=''>${placeholder!''}</option>");
		              for(var i = 0 ; i < resultList.length ; i++){
		                 html.push("<option value='"+resultList[i].city+"' data-id='"+ resultList[i].city_id +"'>"+resultList[i].city+"</option>");
		              }
		              $("#city_id").html(html.join(" "));
		            }else{
		                isTimeOutdevShowDialog(data.error_info,data.infos);
		            }
			   }
			})
		},
		// 得到区县信息
		getAreas:function(city_id){
			$.ajax({
			   url:"${base}/getAreasList.json",
			   type:"post",
			   data:{city_id:city_id},
			   dataType:"json",
			   success:function(data){
			        if (data.error_no == 0) {
		              var resultList = data.resultList;
		              var html = [];
		              html.push("<option value=''>${placeholder!''}</option>");
		              for(var i = 0 ; i < resultList.length ; i++){
		                 html.push("<option value='"+resultList[i].area+"' data-id='"+ resultList[i].area_id +"'>"+resultList[i].area+"</option>");
		              }
		              $("#area_id").html(html.join(" "));
		            }else{
		                isTimeOutdevShowDialog(data.error_info,data.infos);
		            }
			   }
			})
	    }
    }
    </@script>
</#macro>

<#--
    @name dialog 对话框
    @id 对话框编号
    @name 传入的id值
    @action 表单提交url
    @title 对话框标题
    @callback 回调函数
-->
<#macro dialog
		id name action title formInputArray dialogMessage backUrl>
	<div id="${id}" class="easyui-dialog" style="width: 400px; height: auto; padding: 10px 20px"
			title="<#if title??>对话框<#else>${title}</#if>" iconCls="icon-ok"
			toolbar="#dlg-toolbar" data-options="closed:true,buttons:'#dlg-buttons'">
			<#if formInputArray?? && formInputArray?size gt 0>
				<form id="yw_submitForm" method="post" action="${action}">
				    <#list formInputArray as input>
				    <div>
						<label for="name">${input.name}</label>
						<input class="easyui-validatebox" id="${input.id}" type="text" name="${input.id}" <#if input.required == "true">data-options="required:true"</#if> />
				    </div>
				    </#list>
				</form>
			<#else>
				<div>
					<label for="name">${dialogMessage}</label>
			    </div>
			</#if>
			<#--<table id="datagrid" class="easyui-datagrid" style="width:600px;height:150px">  
	            <thead>  
	                <tr>  
	                    <th field="ck" checkbox="true"></th>
	                    <th data-options="field:'Id'" align="center" width="100" sortable="true">  
	                                                             编号  
	                    </th>  
	                    <th field="UserName" align="center" width="120" sortable="true">  
	                                                           用户名  
	                    </th>  
	                    <th field="Name" align="center" width="80" sortable="true">  
	                                                            姓名  
	                    </th>  
	                    <th field="Age" align="center" width="80" sortable="true">  
	                                                           年龄  
	                    </th>  
	                    <th field="Email" align="center" width="80" sortable="true">  
	                                                           邮箱  
	                    </th>  
	  
	                </tr>  
	            </thead>  
	        </table> -->
	</div>
	<#--
	<div id="dlg-toolbar">
		<table cellpadding="0" cellspacing="0" style="width:100%">
			<tr>
				<td>
					<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-help" plain="true">帮助</a>
				</td>
				<td style="text-align:right">
					<input></input><a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true"></a>
				</td>
			</tr>
		</table>
	</div> -->
	<div id="dlg-buttons">
		<table cellpadding="0" cellspacing="0" style="width:100%">
			<tr>
				<td>
					
				</td>
				<td style="text-align:right">
					<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="<#if formInputArray?? && formInputArray?size gt 0>submitForm()<#else>javascript:$('#dd').dialog('close')</#if>">确定</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#${id}').dialog('close')">取消</a>
				</td>
			</tr>
		</table>
	</div>
<@script>
      // 表单提交	
      function submitForm(){
       var flag = true;
        $("#yw_submitForm input").each(function(){
            if($(this).hasClass("easyui-validatebox") && $(this).val() == "" ){
               alert("必须填入名称");
               flag = false;
            }
        })
        if(!flag){
           return false;
        }
        
        var formData = $("#yw_submitForm").serializeArray();
         $.ajax({
 		     type:$("#yw_submitForm").attr("method"),
 		     url:$("#yw_submitForm").attr("action"),
 		     data:formData,
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           alert(data.error_info);
 		           // 回调函数
 		           callBack();
 		           $('#${id}').dialog('close');
 		        } else {
 		           alert(data.error_info);
 		        }
 		     },
 		     error:function(){
 		        alert(data.error_info); 
 		     }
 		  })
      }
      
      // 刷新分类
      <#if backUrl??>
		function callBack(){
		   $.ajax({
		     type:"post",
		     url:"${backUrl}",
		     data:{},
		     dataType:"json",
		     success:function(data){
		        if(data.error_no == "0"){
		           var html=[];
		           var dataList = data.resultList;
		           debugger;
		           if(dataList != undefined && dataList.length > 0){
		             for(var i = 0 ; i < dataList.length ;i ++){
		                html.push("<option value='"+ dataList[i].classify_id +"'>"+ dataList[i].classify_name  +"</option>");
		             }
		             $("#shopType").html(html.join(" "));
		           }
		           
		        } else {
		           alert(data.error_info);
		        }
		     },
		     error:function(){
		        alert("提交错误"); 
		     }
		  })
      }	  
      </#if>
</@script>
</#macro>

<#--
    @name datetimeCondition
    @param begin_datetime_id {String} 开始日期的id
    @param begin_datetime_value {String} 开始日期的初始值
    @param end_datetime_id {String} 结束日期的id
    @param end_datetime_value {String} 结束日期的初始值
    @description 日期查询的组件，今日，昨日，最近7日，最近30日，自定义时间。如果时间初始值为空，组件默认选择是今日
    @example:<@datetimeCondition></@datetimeCondition>
-->
<#macro datetimeCondition begin_datetime_id="begin_datetime" begin_datetime_value="" end_datetime_id="end_datetime" end_datetime_value="">
    <div class="ui-search">
        <span class="search-inner">
        <a class="" href="javascript:void(0)" id="today" onclick="datetimeClick('today')">今日</a>
        <a class="" href="javascript:void(0)" id="yesterday" onclick="datetimeClick('yesterday')">昨日</a>
        <a class="" href="javascript:void(0)" id="less7" onclick="datetimeClick('less7')">最近7日</a>
        <a class="" href="javascript:void(0)" id="less30" onclick="datetimeClick('less30')">最近30日</a>
        </span>
        <label>
            <input class="txt begin-time glyphicon glyphicon-calendar" type="text"  readonly="readonly" id="${begin_datetime_id}" name="${begin_datetime_id}" <#if begin_datetime_value!="">value="${begin_datetime_value}"<#else>placeholder="请选择开始时间"</#if>>
        </label>
        <span class="pre">至</span>
        <label>
            <input class="txt end-time" type="text"   readonly="readonly" id="${end_datetime_id}" name="${end_datetime_id}" <#if end_datetime_value!="">value="${end_datetime_value}"<#else>placeholder="请选择结束时间"</#if>>
        </label>
       <link rel="stylesheet" href="${base}/css/bootstrap-datetimepicker.min.css" type="text/css">
<#-- 时间控件 -->
<@script src="${base}/js/bootstrap-datetimepicker.js" />
<@script src="${base}/js/bootstrap-datetimepicker.zh-CN.js" />	
        <@script>
            // 扩展Date增加天数方法
			Date.prototype.addDays = function(number)
			{
			    return new Date(this.getTime() + 24*60*60*1000*number);
			}
            var today = getSmpFormatDate(new Date(),false);
            var pattern = "yyyy-MM-dd";  
            $(function(){
                $('.begin-time').datetimepicker({
                    minView: "month",//设置只显示到月份
					format: 'yyyy-mm-dd',
					language: 'zh-CN',
					endDate:today,
					autoclose: true//选中自动关闭
                }).on("hide",function(){
                    $('.end-time').datetimepicker( "setStartDate", $('.begin-time').val());
                    removeDateActiveClass();
                });
                $('.end-time').datetimepicker({
                    minView: "month",//设置只显示到月份
					format: 'yyyy-mm-dd',
					language: 'zh-CN',
					endDate:today,
					autoclose: true//选中自动关闭
                }).on("hide",function(){
                   $('.begin-time').datetimepicker( "setEndDate",$('.end-time').val());
                   removeDateActiveClass();
                });
                <#if !(begin_datetime_value?exists)||!(end_datetime_value?exists)>
                    initDateTimeCondition()
                </#if>
            });
            function initDateTimeCondition(){
                $( ".begin-time" ).datetimepicker( "setDate",today );
                $( ".end-time" ).datetimepicker( "setDate",today );
                $("#today").addClass("active");
            }
            function removeDateActiveClass(){
                $("#today").removeClass("active");
                $("#yesterday").removeClass("active");
                $("#less7").removeClass("active");
                $("#less30").removeClass("active");
            }
            function datetimeClick(type){
                var begin_time = new Date();
                var end_time = new Date();
                removeDateActiveClass();
                if(type=="yesterday"){
                    begin_time = begin_time.addDays(-1);
                    end_time = end_time.addDays(-1);
                    $("#yesterday").addClass("active");
                }else if(type=="less7"){
                    begin_time = begin_time.addDays(-6);
                    $("#less7").addClass("active");
                }else if(type=="less30"){
                    begin_time = begin_time.addDays(-29);
                    $("#less30").addClass("active");
                }else{
                    $("#today").addClass("active");
                }
               // $( ".begin-time" ).datetimepicker( "setStartDate",begin_time.format(pattern) );
               // $( ".end-time" ).datetimepicker( "setStartDate",end_time.format(pattern) );
                $( ".begin-time" ).val(begin_time.format(pattern));
                $( ".end-time" ).val(end_time.format(pattern));
            }
        </@script>
    </div>
</#macro>

<#--
    类似于百度搜索框自动填充动能，默认情况是查询指定表的指定列，如果需要多表关联查询，则需要开发serviceUrl
    @name autocomplete
    @param size 结果条数 可为空 默认10条  当serviceUrl为空时有效
    @param tableName 表名 当serviceUrl为空时必填
    @param conditionName 查询条件参数名，默认query
    @param column 列名 当serviceUrl为空时必填
    @param minChars 触发自动提示的最小字符数
    @param width 宽度
    @param maxHeight 最大像素高度. Default: 300.
    @param deferRequestBy keyUp之后发起请求的间隔时间
    @param noCache 设置是否缓存ajax结果的布尔值. 默认 true
    @param inputId 文本框id属性值 不能为空
    @param serviceUrl 提供数据查询action地址 不为空时，返回的数据为{suggestions:[{value:""},{value:""},{value:""}]}，如果数据格式不为以上格式，则参数transformResult不能为空
    @param transformResult 解析返回数据的函数，达到autocomplete所要求的数据格式 例子：function test(response){var obj = $.parseJSON(response);return {suggestions:$.map(obj.resultList,function(dataItem){return {value:dataItem.value};})};}
    @description 根据指定的条件查询指定表的指定列数据，模糊查询
-->
<#macro autocomplete  size="10" tableName="" conditionName="" column="" minChars="" width="" maxHeight="" deferRequestBy="" noCache="" inputId="" serviceUrl="" transformResult="">
    <#nested />
    <style>
       <#-- 搜索框自动填充样式 -->  
	  .autocomplete-suggestions {
		  border: 1px solid #999;
		  background: #FFF;
		  overflow: auto; 
	  }
	  .autocomplete-suggestions strong {
	    font-weight: normal;
	    color: #3399FF;
	 }
	
	.autocomplete-suggestion {
	  padding: 2px 5px;
	  white-space: nowrap;
	  overflow: hidden;
	 }
	
	.autocomplete-selected {
	  background: #F0F0F0; 
	 }
    </style>
    <@script src="/js/jquery.autocomplete.js" />
    <@script>
    $('#${inputId}').autocomplete({
        <#if serviceUrl?has_content>
            serviceUrl:'${serviceUrl}',
        <#else>
            serviceUrl:'${base}/autocomplete.json?size=${size}&tableName=${tableName}&column=${column}',
        </#if>
        <#if minChars?has_content>
            minChars:${minChars},
        </#if>
        <#if width?has_content>
            width:${width},
        </#if>
        <#if maxHeight?has_content>
            maxHeight:${maxHeight},
        </#if>
        <#if autoSelectFirst?has_content>
            autoSelectFirst:${autoSelectFirst},
        </#if>
        <#if deferRequestBy?has_content>
            deferRequestBy:${deferRequestBy},
        </#if>
        <#if noCache?has_content>
            noCache:${noCache},
        </#if>
        <#if transformResult?has_content>
            transformResult:function(response){
                //解析服务器传过来的json字符串
                return ${transformResult}(response);
            },
        </#if>
        <#if conditionName?has_content>
            extraParams:{${conditionName}:$("#${inputId}").val()},
            paramName:"${conditionName}"
        <#else>
            extraParams:{query:$("#${inputId}").val()},
            paramName:"query"
        </#if>
    });
    </@script>
</#macro>
