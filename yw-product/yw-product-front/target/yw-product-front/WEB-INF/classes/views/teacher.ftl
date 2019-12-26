<#assign base=request.contextPath />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>启恒智互联科技有限公司</title>
<link rel="shortcut icon" href="https://www.qhzhlkj.com/files/image/qhz/qhz_icon.png">
<!-- 本地js,css文件引入 -->
<link rel="stylesheet" href="${base}/css/front/min.css">
<link rel="stylesheet" href="${base}/css/front/front.css">
<@script src="${base}/js/front/jquery-1.8.3.js"/>
<@script src="${base}/js/front/banner.js"/>
</head>
<#--顶部导航栏-->
<div class="top app-row-center-layout">
    <div style="width:66%;" class="app-row-center-layout">
   		<div class="top-left-nav app-row-start-layout">
   			<a href="${base}/index.htm">
   				<image style="width:100px;" src="${base}/image/front/logo.png" />
   			</a>
   		</div>
   		<div class="top-center-nav app-row-between-layout">
   			<a href="${base}/index.htm">
	   			<div class="top-center-nav-item app-row-center-layout">
	   				<span>首页</span>
	   			</div>
   			</a>
			<a href="${base}/school.htm">
				<div class="top-center-nav-item app-row-center-layout">
					<span>线上课程</span>
				</div>
			</a>
   			<a href="${base}/course.htm">
	   			<div class="top-center-nav-item app-row-center-layout">
	   				<span>精品课程</span>
	   			</div>
   			</a>
   			<a href="${base}/case.htm">
   				<div class="top-center-nav-item app-row-center-layout">
   					<span>案例见证</span>
   				</div>
   			</a>
   			<a href="${base}/teacher.htm">
   				<div class="active top-center-nav-item app-row-center-layout">
   					<span>名师简介</span>
   				</div>
   			</a>
   			<a href="${base}/about.htm">
   				<div class="top-center-nav-item app-row-center-layout">
   					<span>关于我们</span>
   				</div>
   			</a>
   			<a href="${base}/job.htm">
   				<div class="top-center-nav-item app-row-center-layout">
   					<span>诚聘英才</span>
   				</div>
   			</a>
   		</div>
   		<div class="top-right-nav app-row-end-right">
   			<img style="width:150px;" src="${base}/image/front/phone.png">
   		</div>
	</div>
</div>
<#--banner-->
<div style="margin-bottom:50px;">
    <image style="width:100%;" src="${base}/image/teacher/banner.jpg" />
</div>
<#--名师介绍部分-->
<div style="width:100%;" class="app-column-center-layout">
	<image style="width:100%;" src="${base}/image/teacher/teacher_bg.jpg" />
</div>
<#--底部-->
<div class="bottom-box app-column-center-layout">
	<div style="width:66%;" class="app-row-between-layout">
		<div style="line-height:30px;" class="app-column-start-left">
			<h2>关于启恒智</h2>
			<span>About Qihengzhi</span>
			<span>公司名称: 启恒智互联科技有限公司</span>
			<span>地址: 武汉市武昌区楚河汉街环球国际4栋3A层</span>
		</div>
		<div class="bottom-right-qrcode-box app-row-between-layout">
			<div class="app-column-center-layout">
				<image style="width:100px;" src="${base}/image/new/index/qhz_qr_code.jpg" />
				<span class="bottom-right-qrcode-text">关注启恒智云学堂</span>
			</div>
			<div class="app-column-center-layout">
				<image style="width:100px;" src="${base}/image/new/index/qhz_wx_code.jpg" />
				<span class="bottom-right-qrcode-text">访问启恒智小程序</span>
			</div>
		</div>
	</div>
</div>
<@script>
$(function(){
    $('.flexslider').flexslider({
        directionNav: false,
        pauseOnAction: false
    });
});
</@script>