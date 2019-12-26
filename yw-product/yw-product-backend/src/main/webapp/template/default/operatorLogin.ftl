<#assign base=request.contextPath />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>启恒智互联科技有限公司</title>
<link rel="shortcut icon" href="https://www.qhzhlkj.com/files/image/qhz/qhz_icon.png">
<!-- 本地js,css文件引入 -->
<link rel="stylesheet" href="${base}/css/bootstrap-3.3.7/bootstrap.min.css">
<#--
<link rel="stylesheet" href="${base}/css/style.css" type="text/css">-->
<link rel="stylesheet" href="${base}/css/main-1.1.css" type="text/css">
<@script src="${base}/js/jquery-3.2.1.min.js"/>
<@script src="${base}/js/bootstrap-3.3.7/bootstrap.min.js"/>
<#-- 表单校验 -->
<@script src="${base}/js/comm.js"/>
<@script src="${base}/js/yunw-dev-utils.js"/>
<@script src="${base}/js/yunw-dev-validate.js"/>
<@script src="${base}/js/formValidate.js"/>
<style type="text/css">
#register{
	background: #ED3939;
	width:200px;
	margin-left: 20px;
	border-radius:3px;
}
#register:HOVER{
	background: #F06C6C;
}
#imageCode{
    padding-bottom: 4;
    margin-left: 8%;
}
<#-- 去掉左右轮播图阴影 -->
.carousel-control.left {  
  background-image:none;  
  background-repeat: repeat-x;  
  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#80000000', endColorstr='#00000000', GradientType=1);  
}  
.carousel-control.right {  
  left: auto;  
  right: 0;  
  background-image:none;  
  background-repeat: repeat-x;  
  filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#00000000', endColorstr='#80000000', GradientType=1);  
}  

</style>

<@script>
function userLogin(){
	var user_name = $("#user_name").val();
	var password = $("#password").val();
	// 校验随机验证码
	
	if(user_name=="" || password==""){
	    devShowDialog("用户名或密码不能为空");
		return false;
	}
	var verifyCode = $("#verifyCode").val();
	if(!checkVerifycode(verifyCode)){
	   devShowDialog("验证码不一致");
	   $("#imageCode").click();
	   return false;
    }
    
    $("#formid").submit();
}

// 校验图形验证码
function checkVerifycode(verifyCode){
   var flag = false;
   $.ajax({
       url:"${base}/checkVerifycode.json",
       data:{"verifyCode":verifyCode},
       xhrFields: {
	      withCredentials: true
	   },
       dataType:"json",
       type:"get",
       async: false,
       success:function(data){
           console.info(data);
           if(data.error_no == 0){
           
              flag = true;
           }else{
              flag = false;
           } 
       }
   })
   
   return flag;
}

$(function(){
    
    // 轮播
    $(".login-body-left").carousel({interval: 2000});
    
    // 图形随机验证码获取
    $("#imageCode").click(function(){
		$("#imageCode").attr("src","${base}/imageCode.img?d="+new Date().getTime());
	});

});  
</@script>

</head>
<#--<body style="background-color:#1c77ac; background-image:url(${base}/image/home/images/light.png;) background-repeat:no-repeat; background-position:center top; overflow:hidden;">-->
<body class="login">
	
	<div class="login-top">
		<#--<span>云维小程序内容管理平台</span>
		<ul>
			<li><a href="#">帮助</a></li>
			<li><a href="#">关于</a></li>
		</ul>-->
	</div>
    <#-- 页面变化的内容 -->
    ${screen_content}
    <#--
	<div class="login-bottom">云维时代信息技术有限公司</div>
	-->
</body>
</html>