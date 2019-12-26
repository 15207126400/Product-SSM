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
				<image class="job-top-nav-img" src="${base}/image/job/nav_select_icon2.png" />
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
				<image class="job-top-nav-img" src="${base}/image/job/nav_icon4.png" />
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
					<span>策划专员</span>
				</div>
				<div id="post-nav2" class="job-center-top-span app-row-center-layout">
					<span>资深文案</span>
				</div>
			</div>
			<#--职位介绍部分-->
			<div id="content" class="app-column-start-left">
				<div id="post-nav1-con" class="app-column-start-left">
					<div class="app-column-start-left">
						<h3 style="color:#0066C9;padding:20px;">策划专员 5k-7k/月</h3>
					</div>
					<div style="padding:10px 0px 0px 20px;" class="app-column-start-left">
						<span>岗位职责: </span>
						<span style="padding-left:20px;">1. 与客户进行有效沟通，并根据客户需求，为客户制定并撰写网络营销、投放策划方案</span>
						<span style="padding-left:20px;">2. 负责整合内外部资源，指导并推进完成项目营销传播计划、广告创意</span>
						<span style="padding-left:20px;">3. 进行广告投放的效果评估与分析，并为后续活动及策划提供决策参考</span>
						<span style="padding-left:20px;">4. 根据市场需求，对部门营销产品进行优化升级，并制定销售策略</span>
					</div>
					<div style="padding:10px 0px 0px 20px;" class="app-column-start-left">
						<span>任职要求: </span>
						<span style="padding-left:20px;">1. 1年以上媒体策划工作经验或4A公司工作经验，年龄35岁以内，条件优秀者适当放宽年龄限制</span>
						<span style="padding-left:20px;">2. 具备媒介整合策划经验，能独立撰写媒介策划，善于发现、开拓和利用各类媒体资源关系</span>
						<span style="padding-left:20px;">3. 具备良好的沟通谈判能力、团队精神、工作热情、能承受工作压力</span>
					</div>
					<div style="padding:10px 0px 0px 20px;" class="app-column-start-left">
						<span style="padding-left:20px;">工作时间: 8:30-18:00 单休 午休2小时</span>
						<span style="padding-left:20px;">工作地点: 武昌区楚河汉街环球国际4栋3A层&nbsp;&nbsp;&nbsp;工作性质: 全职&nbsp;&nbsp;&nbsp;发布时间: 2019-08-17</span>
					</div>
				</div>
				<div id="post-nav2-con" style="display:none;">
					<div class="app-column-start-left">
						<h3 style="color:#0066C9;padding:20px;">资深文案 6-10k/月</h3>
					</div>
					<div style="padding:10px 0px 0px 20px;" class="app-column-start-left">
						<span>岗位职责: </span>
						<span style="padding-left:20px;">1. 负责移动互联网自媒体平台（微信、微博、手机终端为主）的日常运营及推广工作；</span>
						<span style="padding-left:20px;">2. 负责能够独立运营微信公众号，为粉丝策划与提供优质、有高度传播性的内容；</span>
						<span style="padding-left:20px;">3. 负责策划并执行微信营销线日常活动及跟踪维护，根据项目发送各种微信内容；</span>
						<span style="padding-left:20px;">4. 负责增加粉丝数，提高关注度和粉丝的活跃度，并及时与粉丝互动；</span>
						<span style="padding-left:20px;">5. 挖掘和分析网友使用习惯、情感及体验感受，及时掌握新闻热点，有效完成专题策划活动；</span>
						<span style="padding-left:20px;">6. 紧跟微信发展趋势，广泛关注标杆性公众号，积极探索微信运营模式；</span>
						<span style="padding-left:20px;">7. 充分了解用户需求，收集用户反馈，分析用户行为及需求。</span>
					</div>
					<div style="padding:10px 0px 0px 20px;" class="app-column-start-left">
						<span>任职要求: </span>
						<span style="padding-left:20px;">1. 广告传播、计算机、新闻或文学相关专业;</span>
						<span style="padding-left:20px;">2. 热爱社交媒体,如微博、微信等；</span>
						<span style="padding-left:20px;">3. 有3到5年文案、策划、媒体工作经历者优先;</span>
						<span style="padding-left:20px;">4. 对新媒体营销和传播方式的了解；</span>
						<span style="padding-left:20px;">5. 责任心强、亲和力,有良好的沟通、实施能力及团队合精神;</span>
						<span style="padding-left:20px;">6. 有文字功底,阅读能力；</span>
						<span style="padding-left:20px;">7. 懂点平面设计如PS、摄影剪辑；</span>
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