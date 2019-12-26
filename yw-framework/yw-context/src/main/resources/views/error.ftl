<#assign base=request.contextPath />
<#if exception?? && exception.class.simpleName=="UserTimeoutException">
${error_info!"用户未登录或已超时"} <a href="javascript:void(0)" style="font-size: 15px;margin-left: 10px;" onclick="top.location.href='${base}/'"><span id="jumpTo" style="display:inline;font-size: 20px;">3</span>秒后系统会自动跳转到登录页，也可点击此处直接进入</a>
<@script>
	function countDown(secs,surl){           
		 var jumpTo = document.getElementById('jumpTo');  
		 jumpTo.innerHTML=secs;    
		 if(--secs>0){       
	     	setTimeout("countDown("+secs+",'"+surl+"')",1000);       
	     } else{         
	     	top.location.href=surl;    
	     }       
	 }
	 
	$(function(){
		$("a").attr("href", "javascript:void(0)");
		$("a").unbind();
		countDown(3,'${base}/');
	});
</@script>
<#else>
系统发生异常：[${error_no!"-1"}]${error_info!"未知错误"}
</#if>