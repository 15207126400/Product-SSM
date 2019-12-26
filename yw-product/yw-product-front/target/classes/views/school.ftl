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
.school-top-bg{
	width:100%;
}
.school-top-title-box{
	width:100%;
	height:80px;
	color:#0164C9;
}
.school-top-title-h{
	line-height:40px;
	border-bottom:3px solid #0164C9;
}
.school-teacher-box{
	width:100%;
	margin-bottom:30px;
}
.teacher-item-box{
	width:24.5%;
	margin-bottom:30px;
	cursor:pointer;
}
/**鼠标悬停荧光界面**/
.teacher-item-box:hover{
	filter:alpha(Opacity=80);
	-moz-opacity:0.8;
	opacity: 0.8;
}
.teacher-item-img{
	width:100%;
}
.teacher-item-icon{
	width:12px;
	height:12px;
}
.teacher-item-title-box{
	width:100%;
	height:40px;
}
.teacher-item-name-box{
	width:100%;
	height:30px;
	color:#AAAAAA;
}
.teacher-item-bottom-box{
	width:100%;
	height:20px;
	font-size:12px;
}
</style>
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
				<div class="active top-center-nav-item app-row-center-layout">
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
<#--顶部-->
<div style="width:100%;" class="app-column-center-layout">
	<div style="width:66%;" class="app-column-center-layout">
		<image class="school-top-bg" src="${base}/image/school/school_top_bg.jpg" />
		<div class="school-top-title-box app-row-start-left">
			<h2 class="school-top-title-h">云学堂</h2>
		</div>
		<#--老师专辑-->
		<div class="school-teacher-box app-row-between-layout">
			<div id="lesson1" class="teacher-item-box">
				<div class="app-row-center-layout">
					<image class="teacher-item-img" src="${base}/image/school/teacher1.png" />
				</div>
				<div class="teacher-item-title-box app-row-start-left">
					<h4>总裁经营之道</h4>
				</div>
				<div class="teacher-item-name-box app-row-start-layout">
					<span>赵朋</span>
					<span style="margin-left:20px;">启恒智互联科技创办人</span>
				</div>
				<div class="teacher-item-bottom-box app-row-start-layout">
					<div class="app-row-start-left">
						<image class="teacher-item-icon" src="${base}/image/school/icon1.png" />
						<span>4.6万</span>
					</div>
					<div style="margin-left:20px;" class="app-row-start-left">
						<image class="teacher-item-icon" src="${base}/image/school/icon2.png" />
						<span>2019-08-17</span>
					</div>
				</div>
			</div>
			<div id="lesson2" class="teacher-item-box">
				<div class="app-row-center-layout">
					<image class="teacher-item-img" src="${base}/image/school/teacher1.png" />
				</div>
				<div class="teacher-item-title-box app-row-start-left">
					<h4>薪酬十五大误区</h4>
				</div>
				<div class="teacher-item-name-box app-row-start-layout">
					<span>赵朋</span>
					<span style="margin-left:20px;">启恒智互联科技创办人</span>
				</div>
				<div class="teacher-item-bottom-box app-row-start-layout">
					<div class="app-row-start-left">
						<image class="teacher-item-icon" src="${base}/image/school/icon1.png" />
						<span>4.6万</span>
					</div>
					<div style="margin-left:20px;" class="app-row-start-left">
						<image class="teacher-item-icon" src="${base}/image/school/icon2.png" />
						<span>2019-08-17</span>
					</div>
				</div>
			</div>
			<div id="lesson3" class="teacher-item-box">
				<div class="app-row-center-layout">
					<image class="teacher-item-img" src="${base}/image/school/teacher1.png" />
				</div>
				<div class="teacher-item-title-box app-row-start-left">
					<h4>企业的激励机制</h4>
				</div>
				<div class="teacher-item-name-box app-row-start-layout">
					<span>赵朋</span>
					<span style="margin-left:20px;">启恒智互联科技创办人</span>
				</div>
				<div class="teacher-item-bottom-box app-row-start-layout">
					<div class="app-row-start-left">
						<image class="teacher-item-icon" src="${base}/image/school/icon1.png" />
						<span>4.6万</span>
					</div>
					<div style="margin-left:20px;" class="app-row-start-left">
						<image class="teacher-item-icon" src="${base}/image/school/icon2.png" />
						<span>2019-08-17</span>
					</div>
				</div>
			</div>
			<div id="lesson4" class="teacher-item-box">
				<div class="app-row-center-layout">
					<image class="teacher-item-img" src="${base}/image/school/teacher2.png" />
				</div>
				<div class="teacher-item-title-box app-row-start-left">
					<h4>销售攻心36计</h4>
				</div>
				<div class="teacher-item-name-box app-row-start-layout">
					<span>廖冲</span>
					<span style="margin-left:20px;">启恒智互联科技营销副总</span>
				</div>
				<div class="teacher-item-bottom-box app-row-start-layout">
					<div class="app-row-start-left">
						<image class="teacher-item-icon" src="${base}/image/school/icon1.png" />
						<span>4.6万</span>
					</div>
					<div style="margin-left:20px;" class="app-row-start-left">
						<image class="teacher-item-icon" src="${base}/image/school/icon2.png" />
						<span>2019-08-17</span>
					</div>
				</div>
			</div>
		</div>
		<div class="school-teacher-box app-row-between-layout">
			<div id="lesson5" class="teacher-item-box">
				<div class="app-row-center-layout">
					<image class="teacher-item-img" src="${base}/image/school/teacher3.png" />
				</div>
				<div class="teacher-item-title-box app-row-start-left">
					<h4>六大分红方案</h4>
				</div>
				<div class="teacher-item-name-box app-row-start-layout">
					<span>佘成</span>
					<span style="margin-left:20px;">企业运营实战讲师</span>
				</div>
				<div class="teacher-item-bottom-box app-row-start-layout">
					<div class="app-row-start-left">
						<image class="teacher-item-icon" src="${base}/image/school/icon1.png" />
						<span>4.6万</span>
					</div>
					<div style="margin-left:20px;" class="app-row-start-left">
						<image class="teacher-item-icon" src="${base}/image/school/icon2.png" />
						<span>2019-08-17</span>
					</div>
				</div>
			</div>
			<div id="lesson6" class="teacher-item-box">
				<div class="app-row-center-layout">
					<image class="teacher-item-img" src="${base}/image/school/teacher3.png" />
				</div>
				<div class="teacher-item-title-box app-row-start-left">
					<h4>企业招聘方案</h4>
				</div>
				<div class="teacher-item-name-box app-row-start-layout">
					<span>佘成</span>
					<span style="margin-left:20px;">企业运营实战讲师</span>
				</div>
				<div class="teacher-item-bottom-box app-row-start-layout">
					<div class="app-row-start-left">
						<image class="teacher-item-icon" src="${base}/image/school/icon1.png" />
						<span>4.6万</span>
					</div>
					<div style="margin-left:20px;" class="app-row-start-left">
						<image class="teacher-item-icon" src="${base}/image/school/icon2.png" />
						<span>2019-08-17</span>
					</div>
				</div>
			</div>
			<div id="lesson7" class="teacher-item-box">
				<div class="app-row-center-layout">
					<image class="teacher-item-img" src="${base}/image/school/teacher4.png" />
				</div>
				<div class="teacher-item-title-box app-row-start-left">
					<h4>社群变现</h4>
				</div>
				<div class="teacher-item-name-box app-row-start-layout">
					<span>王奕程</span>
					<span style="margin-left:20px;">中国资深零售专家</span>
				</div>
				<div class="teacher-item-bottom-box app-row-start-layout">
					<div class="app-row-start-left">
						<image class="teacher-item-icon" src="${base}/image/school/icon1.png" />
						<span>4.6万</span>
					</div>
					<div style="margin-left:20px;" class="app-row-start-left">
						<image class="teacher-item-icon" src="${base}/image/school/icon2.png" />
						<span>2019-08-17</span>
					</div>
				</div>
			</div>
			<div id="lesson8" class="teacher-item-box">
				<div class="app-row-center-layout">
					<image class="teacher-item-img" src="${base}/image/school/teacher5.png" />
				</div>
				<div class="teacher-item-title-box app-row-start-left">
					<h4>36计教出好孩子</h4>
				</div>
				<div class="teacher-item-name-box app-row-start-layout">
					<span>袁晖</span>
					<span style="margin-left:20px;">启恒智特聘讲师</span>
				</div>
				<div class="teacher-item-bottom-box app-row-start-layout">
					<div class="app-row-start-left">
						<image class="teacher-item-icon" src="${base}/image/school/icon1.png" />
						<span>4.6万</span>
					</div>
					<div style="margin-left:20px;" class="app-row-start-left">
						<image class="teacher-item-icon" src="${base}/image/school/icon2.png" />
						<span>2019-08-17</span>
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
    $('.flexslider').flexslider({
        directionNav: false,
        pauseOnAction: false
    });
    
    //请求课程详情页
    $('.school-teacher-box div').click(function(){
    	var op_type = $(this).attr("id");
    	window.location.href="${base}/school_one.htm?op_type=" + op_type;
    });
});
</@script>