<#assign base=request.contextPath />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>启恒智互联科技有限公司</title>
<link rel="shortcut icon" href="https://www.qhzhlkj.com/files/image/qhz/qhz_icon.png">
<!-- 本地js,css文件引入 -->
<link rel="stylesheet" href="${base}/css/front/min.css">
<link rel="stylesheet" href="${base}/css/front/video-js.min.css">
<link rel="stylesheet" href="${base}/css/front/slick.css">
<link rel="stylesheet" href="${base}/css/front/slick-theme.css">
<link rel="stylesheet" href="${base}/css/front/front.css">
<@script src="${base}/js/front/jquery-1.8.3.js"/>
<@script src="${base}/js/front/banner.js"/>
<@script src="${base}/js/front/jquery.img_silder.js"/>
<@script src="${base}/js/front/UIslide.js"/>
<@script src="${base}/js/front/video.min.js"/>
<@script src="${base}/js/front/slick.js"/>
</head>
<body onscroll="scroll()">
<#--客服-->
<div class="customer-box">
	<img src="${base}/image/new/index/customer_qr_code1.jpg">
</div>
<#--顶部导航栏-->
<div class="top app-row-center-layout">
    <div style="width:66%" class="app-row-center-layout">
   		<div class="top-left-nav app-row-start-layout">
   			<a href="${base}/index.htm">
   				<image style="width:100px;" src="${base}/image/front/logo.png" />
   			</a>
   		</div>
   		<div class="top-center-nav app-row-between-layout">
   			<a href="${base}/index.htm">
	   			<div class="active top-center-nav-item app-row-center-layout">
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
<div class="banner">
    <div class="flexslider">
        <ul class="slides">
            <#if ywXcxCarousels?? && ywXcxCarousels?size gt 0>
				<#list ywXcxCarousels as ywXcxCarousel>
					<li style="background: url(${ywXcxCarousel.carousel_url});background-size:100% 100%;"></li>
        		</#list>
        	</#if>
        </ul>
    </div>
</div>
<#--近期课程部分-->
<div style="margin-top:100px;" class="testimonials-box">
	<div class="title app-column-center-layout">
        <h1 style="font-family:FZYaoti;">近期课程</h1>
        <image style="height:25px;padding:10px;" src="${base}/image/new/index/line_icon.jpg" />
    </div>
    <div class="recent-courses-box">
    	<marquee class="recent-courses" behavior="scroll" direction="up" 
    		scrollamount="10" scrolldelay="1000" onMouseOut=start(); onMouseOver=stop();>
	        <ul>
	        	<#if ywNotices?? && ywNotices?size gt 0>
					<#list ywNotices as ywNotice>
	        			<li>${ywNotice.noc_content}</li>
	        		</#list>
	        		<#else>
	        		<li>近期没有课程</li>
	        	</#if>
	        </ul>
	    </marquee>
    </div>
</div>
<#--顶部特色导航部分-->
<div class="course app-column-center-layout">
    <div style="width:66%;" class="app-column-center-layout">
    	<div class="title app-column-center-layout">
	        <h1 style="font-family:FZYaoti;">精品课程</h1>
	        <image style="height:25px;padding:10px;" src="${base}/image/new/index/line_icon.jpg" />
	    </div>
        <ul class="nav-ul app-row-between-layout">
        	<a href="${base}/course.htm">
	            <li style="height:130px;width:220px;" class="nav-li app-column-between-layout">
	            	<div class="app-column-center-layout">
	            		<img style="width:80px;" src="${base}/image/front/icon_1.jpg">
	            	</div>
	            	<div class="app-column-between-layout">
	            		<h4>少年领袖之道</h4>
		                <span>WAY OF BUSINESS</span>
	            	</div>
	            </li>
            </a>
            <a href="${base}/course.htm">
	            <li style="height:130px;width:220px;" class="nav-li app-column-between-layout">
	            	<div class="app-column-center-layout">
	            		<img style="width:80px;" src="${base}/image/front/icon_2.jpg">
	            	</div>
	            	<div class="app-column-between-layout">
	            		<h4>演说之道</h4>
	                	<span>THE WAY OF SPEECH</span>
	            	</div>
	            </li>
            </a>
            <a href="${base}/course.htm">
	            <li style="height:130px;width:220px;" class="nav-li app-column-between-layout">
	            	<div class="app-column-center-layout">
	            		<img style="width:80px;" src="${base}/image/front/icon_4.jpg">
	            	</div>
	            	<div class="app-column-between-layout">
	            		<h4>合伙人方案</h4>
	                	<span>PARTNER PROGRAM</span>
	            	</div>
	            </li>
            </a>
            <a href="${base}/course.htm">
	            <li style="height:130px;width:220px;" class="nav-li app-column-between-layout">
	            	<div class="app-column-center-layout">
	            		<img style="width:80px;" src="${base}/image/front/icon_3.jpg">
	            	</div>
	            	<div class="app-column-between-layout">
	            		<h4>经营之道</h4>
	                	<span>THE WAY OF THE OPERATING</span>
	            	</div>
	            </li>
            </a>
            <a href="${base}/course.htm">
	            <li style="height:130px;width:220px;" class="nav-li app-column-between-layout">
	            	<div class="app-column-center-layout">
	            		<img style="width:80px;" src="${base}/image/front/icon_5.jpg">
	            	</div>
	            	<div class="app-column-between-layout">
	            		<h4>领袖父母</h4>
	                	<span>THE WAY OF MORE</span>
	            	</div>
	            </li>
            </a>
        </ul>
    </div>
</div>
<#--启恒智案例和客户见证部分-->
<div class="testimonials-box">
	<div class="title app-column-center-layout">
        <h1 style="font-family:FZYaoti;">案例见证 </h1>
        <image style="height:25px;padding:10px;" src="${base}/image/new/index/line_icon.jpg" />
    </div>
    <div class="testimonials-data-box app-column-center-layout">
    	<div class="testimonials-data app-row-start-left">
	    	<div class="testimonials-left-data">
	    		<div class="testimonials-left-data-top-box app-row-between-layout">
	    			<div style="margin-right:10px;" class="testimonials-left-data-top-box-item app-row-center-layout">
	    				<image class="testimonials-left-data-top-box-item-img" src="${base}/image/new/index/index_center_img01.jpg" />
	    			</div>
	    			<div class="testimonials-left-data-top-box-item app-row-center-layout">
	    				<image class="testimonials-left-data-top-box-item-img" src="${base}/image/new/index/index_center_img02.jpg" />
	    			</div>
	    		</div>
	    		<div class="testimonials-left-data-top-box app-row-between-layout">
	    			<div style="margin-right:10px;" class="testimonials-left-data-top-box-item app-row-between-layout">
	    				<image class="testimonials-left-data-top-box-item-img" src="${base}/image/new/index/index_center_img03.jpg" />
	    			</div>
	    			<div class="testimonials-left-data-top-box-item app-row-between-layout">
	    				<image class="testimonials-left-data-top-box-item-img" src="${base}/image/new/index/index_center_img04.jpg" />
	    			</div>
	    		</div>
	    	</div>
	    	<div class="testimonials-right-data app-column-center-layout">
	    		<div class="testimonials-right-data-top-box-item app-column-center-layout">
	    			<image style="width:100%;height:99%;" src="${base}/image/new/index/index_center_img05.jpg" />
	    		</div>
	    		<div class="testimonials-right-data-top-box-item app-column-center-layout">
	    			<image class="testimonials-left-data-top-box-item-img" src="${base}/image/new/index/index_center_img06.jpg" />
	    		</div>
	    		<div class="testimonials-bottom-line-box">
					<image class="testimonials-left-data-top-box-item-img" src="${base}/image/new/index/index_center_line.jpg" />
				</div>
	    	</div>
	    </div>
    </div>
    <#--了解更多-->
    <div class="more-box app-row-center-layout">
    	<a href="${base}/case.htm">
    		<button class="more">了解更多</button>
    	</a>
    </div>
</div>
<#--关于我们部分-->
<div class="about-box app-column-center-layout">
	<div style="width:100%;">
		<image style="width:100%;" src="${base}/image/new/index/about_line_bg.jpg" />
	</div>
	<div class="about-content-box app-column-center-layout">
		<span class="about-content">启恒智教育集团是一家旗下集教育, 培训, 传媒, 互联网, 投资五大核心一体的多元化集团公司,</span>
		<span class="about-content">公司从2013年创办至今, 公司从8人发展到200多 人, 从200人发展到500人, 分公司超过240家以上,</span>
		<span class="about-content">从省单操作到华中, 滑动, 华南同步, 形成以上海, 武汉, 广州为三大轴心的全国运营体系只用了短短不到两年的时间,</span>
		<span class="about-content">创造了行业一个又一个的奇迹! 奇迹还在不断创造中.</span>
	</div>
	
	<#--视频播放-->
	<video id="my-player" class="video-js vjs-big-play-centered" preload="auto" poster="${base}/image/new/top_img.png" data-setup="{}" width="500" height="250"
	 controls="controls" >
	 	<source src="https://www.qhzhlkj.com/files/image/redio/qhz_xc_video.mp4" type="video/mp4"></source>
	 </video>
</div>
<#--名师简介部分-->
<div class="teacher-big-box app-column-center-layout">
	<div style="width:100%;">
		<image style="width:100%;" src="${base}/image/new/index/about_line2_bg.jpg" />
	</div>
	<div class="teacher-box">
		<#if qhzTeachers?? && qhzTeachers?size gt 0>
			<#list qhzTeachers as qhzTeacher>
				<div style="display:flex;" class="teacher-item-box">
					<div class="teacher-item-inside-box">
						<div class="teacher-brief-box app-column-center-layout">
							<image class="teacher-photo" src="${qhzTeacher.photo}" />
							<div class="teacher-brief-bg">
								<div style="width:250px;height:380px;padding-left:30px;padding-right:30px;position:absolute;background:rgba(5,5,5,0.8);display:flex;flex-direction:column;justify-content:center;">
									${qhzTeacher.brief}
								</div>
							</div>
						</div>
						<div class="teacher-name-box app-column-center-layout">
							<h6 class="teacher-post">${qhzTeacher.post}</h6>
						</div>
						<div style="display:flex;flex-direction:row;justify-content:center;" class="teacher-name-box">
							<h3 style="font-family:STXinwei;font-size:25px;">${qhzTeacher.name}</h3>
						</div>
					</div>
				</div>
			</#list>
		</#if>
	</div>
</div>
<#--底部诚聘英才部分-->
<div class="join-box app-column-center-layout">
	<div style="width:100%;">
		<image style="width:100%;" src="${base}/image/new/index/about_line3_bg.jpg" />
	</div>
	<image class="join-img" src="${base}/image/new/index/bottom_bg.jpg" />
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
<#--回到顶部锚点-->
<div class="goTop" id="toolBackTop" style="display:none;">  
     <a title="返回顶部" onclick="window.scrollTo(0,0);return false;" href="#top" class="back-top">
		<image style="width:40px;height:40px;" src="${base}/image/new/index/go_top.png" />
		<span>回到顶部</span>
	 </a>  
</div> 
</body>
<@script>
$(function(){
	 //名师介绍横向滑动
	 $(".teacher-box").slick({
        dots: true,						//true , 显示指示点
        infinite: true,					//true , 滑块无限循环
        slidesToShow: 3,				//每屏显示个数
        slidesToScroll: 1,				//每次滑动个数
        cssEase: 'ease',				//滑动的时候滑块的速度逐渐变慢
        autoplay: false,					//自动播放
        pauseOnHover: true,				//鼠标悬停暂停自动循环
        speed: 1000,					//滑动时间
        swipe: true,					//移动设备滑动事件
        touchMove: true,				//触摸滑动
    });
    
    //禁止tab按键
    document.onkeydown = function() {
        if (event.keyCode == 9) {  //如果是其它键，换上相应在ascii 码即可。
            return false; //非常重要
        }
    }

	//轮播图配置
    $('.flexslider').flexslider({
    	animation: "fade",						//"fade" or "slide"图片变换方式：淡入淡出或者滑动
    	slideDirection: "horizontal",			//"horizontal" or "vertical"图片设置为滑动式时的滑动方向：左右或者上下
    	slideshow: true, 						//载入页面时，是否自动播放
    	slideshowSpeed: 5000,					//自动播放速度毫秒
    	animationDuration: 1000,					//动画淡入淡出效果延时
        directionNav: false,					//是否显示左右控制按钮
        controlNav: true, 						//是否显示控制菜单
        keyboardNav: true,						//键盘左右方向键控制图片滑动
        mousewheel: false,						//鼠标滚轮控制制图片滑动
        start : function() {
        	console.log("轮播图加载成功");
		},
    });

    window.onload = function() {
        var $li = $('#tab li');
        var $ul = $('#content ul');
				
        $li.mouseover(function(){
            var $this = $(this);
            var $t = $this.index();
            $li.removeClass();
            $this.addClass('current');
            $ul.css('display','none');
            $ul.eq($t).css('display','block');
        })
    }
    
	$('#silder').imgSilder({
	    s_width:'537', //容器宽度
	    s_height:334, //容器高度
	    is_showTit:true, // 是否显示图片标题 false :不显示，true :显示
	    s_times:3000, //设置滚动时间
	    css_link:'css/style.css'
	});
	
	
	
	//播放视频
	var player = videojs('my-player');
	videojs('my-player').ready(function() {
	    player.load();
	    //player.play();
	});

});


 //回到顶部
 function scroll(e){
    var scroll = window.scrollY;
	var bt = $('#toolBackTop'); 
    if(scroll > 500){
    	bt.fadeIn();
    } else {
    	bt.fadeOut();  
    }
 }

</@script>
   