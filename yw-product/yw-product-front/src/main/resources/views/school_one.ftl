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
<input id="op_type" type="hidden" value="${op_type}" />
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
<#--顶部部分-->
<div style="width:100%;margin:20px 0px 50px 0px;" class="app-column-center-layout">
	<#if op_type=="lesson1">
		<div style="width:60%;" class="app-row-center-layout">
			<image style="width:100%;" src="${base}/image/school/top_teacher_bg.jpg" />
		</div>
		<div style="width:59.8%;border-left:3px solid #0D6BCB;margin-top:10px;" class="app-column-start-left">
			<h3>&nbsp;总裁经营之道</h3>
			<h3>&nbsp;赵朋 启恒智互联科技创办人</h3>
		</div>
		<div style="width:59.8%;margin-top:30px;display:flex;">
			<div style="width:80px;height:20px;border-left:3px solid #0D6BCB;">
				<h4>&nbsp;课程介绍</h4>
			</div>
			<div style="color:#676767;font-size:12px;line-height:22px;" class="app-column-start-left">
				<span>&nbsp;&nbsp;&nbsp;&nbsp;帮助企业家及其核心高层彻底打通经营命脉</span>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;事业+家庭+孩子+个人</span>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;平衡式发展</span>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;实现物质与精神的双重幸福</span>
				<image style="width:100%;" src="${base}/image/school/school_content_img2.jpg" />
			</div>
		</div>
	<#elseif op_type=="lesson2">	
		<div style="width:60%;" class="app-row-center-layout">
			<image style="width:100%;" src="${base}/image/school/top_teacher_bg.jpg" />
		</div>
		<div style="width:59.8%;border-left:3px solid #0D6BCB;margin-top:10px;" class="app-column-start-left">
			<h3>&nbsp;薪酬十五大误区</h3>
			<h3>&nbsp;赵朋 启恒智互联科技创办人</h3>
		</div>
		<div style="width:59.8%;margin-top:30px;display:flex;">
			<div style="width:80px;height:20px;border-left:3px solid #0D6BCB;">
				<h4>&nbsp;课程介绍</h4>
			</div>
			<div style="color:#676767;font-size:12px;line-height:22px;" class="app-column-start-left">
				<span>&nbsp;&nbsp;&nbsp;&nbsp;2019年, 员工工资怎么发?</span>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;老板必须知道!</span>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;逃离薪酬设计的十五大误区</span>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;老板必须懂, 逃离薪酬设计的深坑! 如何合理设计薪酬既能激发员工动力, 又能让企业健康发展?</span>
				<image style="width:100%;" src="${base}/image/school/school_content_img1.jpg" />
			</div>
		</div>
	<#elseif op_type=="lesson3">	
		<div style="width:60%;" class="app-row-center-layout">
			<image style="width:100%;" src="${base}/image/school/top_teacher_bg.jpg" />
		</div>
		<div style="width:59.8%;border-left:3px solid #0D6BCB;margin-top:10px;" class="app-column-start-left">
			<h3>&nbsp;企业的激励机制</h3>
			<h3>&nbsp;赵朋 启恒智互联科技创办人</h3>
		</div>
		<div style="width:59.8%;margin-top:30px;display:flex;">
			<div style="width:80px;height:20px;border-left:3px solid #0D6BCB;">
				<h4>&nbsp;课程介绍</h4>
			</div>
			<div style="color:#676767;font-size:12px;line-height:22px;" class="app-column-start-left">
				<span>&nbsp;&nbsp;&nbsp;&nbsp;如何解决企业优秀人才“进不来、留不住”？</span>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;如何让员工与企业“同心、同向、同德、同行”？</span>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;如何让员工发挥主人翁意识，与平台共创业？</span>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;如何找到员工真正需求，激发员工战斗力？</span>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;如何构建长期激励方案，让员工持续保持战斗力？</span>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;赵朋老师逐条梳理不同成长阶段的人性特征，</span>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;带领老板一起探底员工灵魂深处的需求，</span>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;懂他，才能留住他！懂他，才能激发他！</span>
				<image style="width:100%;" src="${base}/image/school/school_content_img3.jpg" />
			</div>
		</div>
	<#elseif op_type=="lesson4">	
		<div style="width:60%;" class="app-row-center-layout">
			<image style="width:100%;" src="${base}/image/school/top_teacher_bg2.jpg" />
		</div>
		<div style="width:59.8%;border-left:3px solid #0D6BCB;margin-top:10px;" class="app-column-start-left">
			<h3>&nbsp;销售攻心36计</h3>
			<h3>&nbsp;廖冲 启恒智互联科技营销副总</h3>
		</div>
		<div style="width:59.8%;margin-top:30px;display:flex;">
			<div style="width:80px;height:20px;border-left:3px solid #0D6BCB;">
				<h4>&nbsp;课程介绍</h4>
			</div>
			<div style="color:#676767;font-size:12px;line-height:22px;" class="app-column-start-left">
				<span>&nbsp;&nbsp;&nbsp;&nbsp;销售干不好？</span>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;老板不赚钱！员工收入低！企业无发展！如何打破死循环？</span>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;突破恐惧、坚定信念、塑造产品、逼单成交、沟通话术、谈判技巧从卖产品到销售自我价值。</span>
				<image style="width:100%;" src="${base}/image/school/school_content_img4.jpg" />
			</div>
		</div>
	<#elseif op_type=="lesson5">	
		<div style="width:60%;" class="app-row-center-layout">
			<image style="width:100%;" src="${base}/image/school/top_teacher_bg3.jpg" />
		</div>
		<div style="width:59.8%;border-left:3px solid #0D6BCB;margin-top:10px;" class="app-column-start-left">
			<h3>&nbsp;六大分红方案</h3>
			<h3>&nbsp;佘成 企业运营实战讲师</h3>
		</div>
		<div style="width:59.8%;margin-top:30px;display:flex;">
			<div style="width:80px;height:20px;border-left:3px solid #0D6BCB;">
				<h4>&nbsp;课程介绍</h4>
			</div>
			<div style="color:#676767;font-size:12px;line-height:22px;" class="app-column-start-left">
				<image style="width:100%;" src="${base}/image/school/school_content_img5.jpg" />
			</div>
		</div>
	<#elseif op_type=="lesson6">	
		<div style="width:60%;" class="app-row-center-layout">
			<image style="width:100%;" src="${base}/image/school/top_teacher_bg3.jpg" />
		</div>
		<div style="width:59.8%;border-left:3px solid #0D6BCB;margin-top:10px;" class="app-column-start-left">
			<h3>&nbsp;企业招聘方案</h3>
			<h3>&nbsp;佘成 企业运营实战讲师</h3>
		</div>
		<div style="width:59.8%;margin-top:30px;display:flex;">
			<div style="width:80px;height:20px;border-left:3px solid #0D6BCB;">
				<h4>&nbsp;课程介绍</h4>
			</div>
			<div style="color:#676767;font-size:12px;line-height:22px;" class="app-column-start-left">
				<image style="width:100%;" src="${base}/image/school/school_content_img6.jpg" />
			</div>
		</div>
	<#elseif op_type=="lesson7">	
		<div style="width:60%;" class="app-row-center-layout">
			<image style="width:100%;" src="${base}/image/school/top_teacher_bg4.jpg" />
		</div>
		<div style="width:59.8%;border-left:3px solid #0D6BCB;margin-top:10px;" class="app-column-start-left">
			<h3>&nbsp;社群变现</h3>
			<h3>&nbsp;王奕程 中国资深零售专家</h3>
		</div>
		<div style="width:59.8%;margin-top:30px;display:flex;">
			<div style="width:80px;height:20px;border-left:3px solid #0D6BCB;">
				<h4>&nbsp;课程介绍</h4>
			</div>
			<div style="color:#676767;font-size:12px;line-height:22px;" class="app-column-start-left">
				<span>&nbsp;&nbsp;&nbsp;&nbsp;社群变现，</span>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;互联网时代，如何低成本运营，构建流量池，将粉丝流量变现，引爆现金流？</span>
				<image style="width:100%;" src="${base}/image/school/school_content_img7.jpg" />
			</div>
		</div>
	<#elseif op_type=="lesson8">	
		<div style="width:60%;" class="app-row-center-layout">
			<image style="width:100%;" src="${base}/image/school/top_teacher_bg5.jpg" />
		</div>
		<div style="width:59.8%;border-left:3px solid #0D6BCB;margin-top:10px;" class="app-column-start-left">
			<h3>&nbsp;36计教出好孩子</h3>
			<h3>&nbsp;袁晖 启恒智特聘讲师</h3>
		</div>
		<div style="width:59.8%;margin-top:30px;display:flex;">
			<div style="width:80px;height:20px;border-left:3px solid #0D6BCB;">
				<h4>&nbsp;课程介绍</h4>
			</div>
			<div style="color:#676767;font-size:12px;line-height:22px;" class="app-column-start-left">
				<image style="width:100%;" src="${base}/image/school/school_content_img8.jpg" />
			</div>
		</div>
	</#if>
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