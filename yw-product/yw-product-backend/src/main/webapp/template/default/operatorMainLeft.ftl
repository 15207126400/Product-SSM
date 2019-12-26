<#assign base=request.contextPath />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>左边菜单导航</title>
<!-- 本地js,css文件引入 -->
<link rel="stylesheet" href="${base}/css/bootstrap.css" type="text/css">
<link rel="stylesheet" href="${base}/css/main-1.1.css" type="text/css">
<@script src="${base}/js/jquery-3.2.1.min.js"/>

</head>
<body style="background:#fff;">
		<#--<div class="lefttop">
			<span></span>后台管理平台
		</div>-->
	${screen_content}
</body>
</html>