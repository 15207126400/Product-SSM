<#assign base=request.contextPath />
<@screen id="parameter" title="系统参数">
<@script src="${base}/js/jquery-validation/jquery.validate.js"/>
<@script src="${base}/js/jquery-validation/messages_zh.js"/>
	<style>
	.rte-zone { clear: none; margin-left: 10px; }
	</style>
<div class="parameter-content">
<a class="back-btn" href="${base}/system/sysconfig/sysarg.htm">&lt;返回</a>
	<form id="modifyConfigure" action="${base}/system/sysconfig/sysargEditSave.json" method="POST">
		  	${html_data?default("")}
		  <input type="hidden" name="catalogId" id="catalogId" value="${catalogId?default("")}">
		  
	</form>
		  <div class="button-content">
		    <a class="btn btn-cancel" href="${base}/system/sysconfig/sysarg.htm">取消</a>
		    <a class="btn btn-confirm" href="javascript:void(0);" onClick="submit_modify()">保存</a>
		  </div>
</div>
<@script>
 (function($){  
	    $.fn.serializeJson=function(){
	        var serializeObj={};
	        var array=this.serializeArray();
	        $(array).each(function(){
	            if(serializeObj[this.name]){
	                if($.isArray(serializeObj[this.name])){
	                    serializeObj[this.name].push(this.value);
	                }else{
	                    serializeObj[this.name]=[serializeObj[this.name],this.value];
	                }
	            }else{
	                serializeObj[this.name]=this.value;
	            } 
	        });  
	        return serializeObj;
	    };
	})(jQuery);

 $(function(){
    $('.parameter-inner').on('click', '.status', function(event) {
      event.preventDefault();
      /* Act on the event */
      if ($(this).hasClass('close')) {
        $(this).removeClass('close').addClass('open');
      } else if ($(this).hasClass('open')) {
        $(this).removeClass('open').addClass('close');
      }
    });
  });
  
  
  $(function(){
  	$('.relatedTrigger').each(function(){
  		if($(this).hasClass("checked")){
  			$(this).removeClass('close').addClass('open');
  		}
  	});
  });
  
  $(function() {
	    $('select').diySelect({
	        width: 276,
	        style: 'margin:0 10px 0 0'
	    });
	    
	    $('.relatedTrigger').change();
	});
  
	
  function onCheckboxSwitch(ctnEle) {
        var eleObj = $(ctnEle);
        eleObj.toggleClass('checked');
        var optionCtnson = eleObj.find('.blockon');
        var optionCtnsoff = eleObj.find('.blockoff');
        
        if(optionCtnson.find(':radio').is(":checked")){
        	optionCtnson.find(':radio').removeAttr("checked");
        	optionCtnsoff.find(':radio').prop("checked","checked")
        }else{
        	optionCtnsoff.find(':radio').removeAttr("checked");
        	optionCtnson.find(':radio').prop("checked","checked")
        }
    }
    
  function onOptionChanged(optionsCtn, optionSelect) {
        var optionsCtnObj = $(optionsCtn);
        var allOptions = optionsCtnObj.find(optionSelect);
        var selectedOptions, unselectedOptions;
        if ('option' == optionSelect) {
            selectedOptions = allOptions.filter(':selected');
            unselectedOptions = allOptions.not(':selected');
        } else if (':radio' == optionSelect) {
            selectedOptions = allOptions.filter(':checked');
            unselectedOptions = allOptions.not(':checked');
        } else {
            return;
        }
        
    unselectedOptions.each(function() {
        var related = $(this).attr('related');
        if (related) {
            var relatedArr = related.split(',');
            $.each(relatedArr, function(i, n) {
                var relatedUnitObj = $('#' + n);
                relatedUnitObj.hide();
                relatedUnitObj.find('input,select').prop('disabled', 'disabled');
            });
        }
    });
        
    selectedOptions.each(function() {
        var related = $(this).attr('related');
        if (related) {
            var relatedArr = related.split(',');
            $.each(relatedArr, function(i, n) {
                var relatedUnitObj = $('#' + n);
                relatedUnitObj.show();
                relatedUnitObj.find('input,select').removeAttr('disabled');
            });
        }
    });
 }		
  
  function submit_modify() {
  		if(validate()){
  			$('#modifyConfigure').submit();
  		}
    }
  
  function validate(){
  	 var result=true;
  	 if($("#task_interval").length >0){
  		 result=validateUserFollow();
  	 }
  	 return result;
  }
  
  function validateUserFollow(){
  		var result=true;
  		var userFollowCheck = /^(\d+)\,(\d+)\,(\d+)\,(\d+),(\d+),(\d+)$/;
  		if(!userFollowCheck.test($("#task_interval input").val())){
  			var freeze_screen=new Dialog(undefined, {
				title: '提示',
				tipType: 'error' ,
				message: '用户回访任务格式设置不正确',
				needDestroy: true,
				hasBtn: true,
				btnText: ['确定'],
				btnRole: ['confirm'],
			    confirm: function() {
      		      freeze_screen.hide();
     		    }
			});
			result=false;
  		}
  		return result;
  }
  
   $("#modifyConfigure").validate({
        errorClass : 'validate-fail',
        validClass : 'validate-pass',
        errorElement : 'label',
        submitHandler : function(form) {
            modifySubmit(form);
        }
    });
  
   function modifySubmit(form) {
        var formObj = $(form);
        var data = formObj.serializeJson();
        //console.log(data);
        var catalogId = $("#catalogId").val();
        $.ajax({
            type : "post",
            data : data,
            dataType : "json",
            url : "${base}/system/sysconfig/sysargEditSave.json",
            success : function(data) {
                if (data.error_no == 0) {
                    devActAfterShowDialog("修改成功", function() {
                		location.href = "${base}/system/sysconfig/sysarg.htm";
                	});
                } else {
                	if(data.error_info){
                	    if(data.error_info.indexOf('未登录') >= 0){
	                		devActAfterShowDialog("修改配置失败:"+data.error_info, function() {
			                	location.href="${base}/system/sysconfig/sysarg.htm";
			                });	 
	                	}
	                	else{
	                	    devShowDialog("修改配置失败:"+data.error_info);
	                	}
                	}
                	else{
                	  	devShowDialog("修改配置失败:"+data.error_no);
                	}
                }
            },
            error : function(jqXHR, textStatus, errorThrown) {
                devShowDialog("修改配置失败"+textStatus);
            }
        });
    }
</@script>
</@screen>