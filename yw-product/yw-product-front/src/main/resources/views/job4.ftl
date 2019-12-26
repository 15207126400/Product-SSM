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
<style>
/**顶部部分**/
.job-top-img{
	width:100%;
}
.job-top-nav-box{
	width:40%;
	height:150px;
}
.job-top-nav-img{
	width:60px;
	height:60px;
}
.job-top-nav-span{
	color:#0066C8;
	font-size:16px;
	padding:10px;
}
/**中部招聘部分**/
.job-center-big-box{
	width:100%;
	background:#F6F6F6;
	padding-bottom:50px;
}
.job-center-box{
	width:100%;
	height:40px;
	border-bottom:1px solid #EEEEEE;
}
.job-active{
	height:20px;
	background: #0164C9;
	color: #fff;
}
/**职位介绍部分**/
.job-center-top-span{
	font-size:12px;
	padding:5px 10px 5px 10px;
	cursor:pointer;
}
#content{
	color:#949494;
}
</style>
</head>
<body onscroll="scroll()">
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
   				<div class="top-center-nav-item app-row-center-layout">
   					<span>名师简介</span>
   				</div>
   			</a>
   			<a href="${base}/about.htm">
   				<div class="top-center-nav-item app-row-center-layout">
   					<span>关于我们</span>
   				</div>
   			</a>
   			<a href="${base}/job.htm">
   				<div class="active top-center-nav-item app-row-center-layout">
   					<span>诚聘英才</span>
   				</div>
   			</a>
   		</div>
   		<div class="top-right-nav app-row-end-right">
   			<img style="width:150px;" src="${base}/image/front/phone.png">
   		</div>
	</div>
</div>
<#--数据部分-->
<div style="width:100%;" class="app-column-center-layout">
	<#--顶部横幅-->
	<image class="job-top-img" src="${base}/image/job/top_job_img.png" />
	<#--特色导航-->
	<div class="job-top-nav-box app-row-between-layout">
		<a href="${base}/job.htm">
			<div class="app-column-center-layout">
				<image class="job-top-nav-img" src="${base}/image/job/nav_icon1.png" />
				<span class="job-top-nav-span">销售</span>
			</div>
		</a>
		<a href="${base}/job2.htm">
			<div class="app-column-center-layout">
				<image class="job-top-nav-img" src="${base}/image/job/nav_icon2.png" />
				<span class="job-top-nav-span">策划</span>
			</div>
		</a>
		<a href="${base}/job3.htm">
			<div class="app-column-center-layout">
				<image class="job-top-nav-img" src="${base}/image/job/nav_icon3.png" />
				<span class="job-top-nav-span">开发</span>
			</div>
		</a>
		<a href="${base}/job4.htm">
			<div class="app-column-center-layout">
				<image class="job-top-nav-img" src="${base}/image/job/nav_select_icon4.png" />
				<span class="job-top-nav-span">设计</span>
			</div>
		</a>
	</div>
	<#--招聘详细-->
	<div class="job-center-big-box app-column-start-layout">
		<div style="width:66%;" class="app-column-start-left">
			<#--职位选择部分-->
			<div class="job-center-box app-row-start-left">
				<span style="margin-right:20px;">职位:</span>
				<div id="post-nav1" class="job-active job-center-top-span app-row-center-layout">
					<span>UI设计师</span>
				</div>
			</div>
			<#--职位介绍部分-->
			<div id="content" class="app-column-start-left">
				<div id="post-nav1-con" class="app-column-start-left">
					<div class="app-column-start-left">
						<h3 style="color:#0066C9;padding:20px;">UI设计 5-6k/月</h3>
					</div>
					<div style="padding:10px 0px 0px 20px;" class="app-column-start-left">
						<span>岗位职责: </span>
						<span style="padding-left:20px;">1. 能根据客户要求设计出网页原型图；</span>
						<span style="padding-left:20px;">2. 至少要熟练使用两种以上处理图片软件；</span>
						<span style="padding-left:20px;">3. 至少一年网页设计相关工作经验；</span>
						<span style="padding-left:20px;">4. 会前端代码优先；</span>
						<span style="padding-left:20px;">5. 特别优秀且有项目经验应届生也可以接受；</span>
					</div>
					<div style="padding:10px 0px 0px 20px;" class="app-column-start-left">
						<span style="padding-left:20px;">工作时间: 8:30-18:00 单休 午休2小时</span>
						<span style="padding-left:20px;">工作地点: 武昌区楚河汉街环球国际4栋3A层&nbsp;&nbsp;&nbsp;工作性质: 全职&nbsp;&nbsp;&nbsp;发布时间: 2019-08-17</span>
					</div>
				</div>
			</div>
		</div>
	</div>
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
	//职位选择切换
	$('.job-center-box div').click(function(){
		$('.job-center-box div').removeClass('job-active');
		$(this).addClass('job-active');
		$("#content div").hide();
        var name=$(this).attr("id");
        $("#"+name+"-con").show();
        $("#"+name+"-con div").show();
	})

    $('.flexslider').flexslider({
        directionNav: false,
        pauseOnAction: false
    });
});
</@script>