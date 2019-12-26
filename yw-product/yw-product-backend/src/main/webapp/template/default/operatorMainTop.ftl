<#assign base=request.contextPath />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>启恒智互联科技有限公司</title>
<link rel="shortcut icon" href="https://www.qhzhlkj.com/files/image/qhz/qhz_icon.png">
<!-- 本地js,css文件引入 -->
<link rel="stylesheet" href="${base}/css/bootstrap.css" type="text/css">
<link rel="stylesheet" href="${base}/css/main-1.1.css" type="text/css">
<style type="text/css">
.helpimg{
	float: right;
}
</style>
<@script src="${base}/js/jquery-3.2.1.min.js"/>
<@script src="${base}/js/websocket.js"/>
<@script>
	function exit(){
		window.top.location.href = "${base}/operatorLoginOut.htm";
	}

	//顶部导航切换
	$(".nav li a").click(function(){
		$(".nav li a.selected").removeClass("selected")
		$(this).addClass("selected");
		
	})	
	
	//if(window.CrhWebsocket){
    //	window.CrhWebsocket.openWebsocket("${user.user_id!''}","");
    //}
</@script>
</head>
<body class="top-bg">
    ${screen_content}
</body>
</html>