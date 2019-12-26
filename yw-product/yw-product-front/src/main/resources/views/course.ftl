<#assign base=request.contextPath />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>启恒智互联科技有限公司</title>
<link rel="shortcut icon" href="https://www.qhzhlkj.com/files/image/qhz/qhz_icon.png">
<!-- 本地js,css文件引入 -->
<link rel="stylesheet" href="${base}/css/front/min.css">
<link rel="stylesheet" href="${base}/css/front/front.css">
<link rel="stylesheet" href="${base}/css/front/pagination.css">
<@script src="${base}/js/front/jquery-1.8.3.js"/>
<@script src="${base}/js/front/banner.js"/>
<@script src="${base}/js/front/jquery.pagination.js"/>
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
	   			<div class="active top-center-nav-item app-row-center-layout">
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
<div style="margin-bottom:50px;">
    <image style="width:100%;" src="${base}/image/course/banner.jpg" />
</div>
<#--课程介绍-->
<div id="hiddenresult" style="display:none;width:100%;margin-top:50px;margin-bottom:80px;" class="app-column-center-layout">
	<#if qhzCurriculums?? && qhzCurriculums?size gt 0>
		<#list qhzCurriculums as qhzCurriculum>
			<div style="height:280px;padding:20px;margin:10px 0px 10px 0px;border:2px solid #F3F3F3;border-radius:5px;" class="result app-row-between-layout">
				<div style="width:45%;height:100%;" class="app-row-center-layout">
					<image style="width:100%;height:100%;" src="${qhzCurriculum.img}" />
				</div>
				<div style="width:50%;height:100%;background:url('${base}/image/course/course_content_bg.png');background-size:99.9% 99.9%" class="app-column-center-layout">
					<span style="font-size:16px;font-weight:bold;">《${qhzCurriculum.title!''}》</span>
					<div style="font-size:12px;margin-top:20px;line-height:25px;">
						${qhzCurriculum.content}
					</div>
				</div>
			</div>
		</#list>
	</#if>	
</div>
<#--分页数据-->
<div style="width:100%;" class="app-column-center-layout">
	<div id="Searchresult" style="width:66%;">后台没有数据</div>
	<div id="Pagination" style="margin:20px 0px 20px 0px;" class="pagination"><!-- 这里显示分页 --></div>
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
    
	//分页函数
	var initPagination = function() {
		var num_entries = $("#hiddenresult div.result").length;
		
		// 创建分页
		$("#Pagination").pagination(num_entries, {
			num_edge_entries: 1,    //边缘页数
			num_display_entries: 4, //主体页数
			items_per_page:2,		//每页显示2项
			callback: pageselectCallback
			
		});
	 }();
	 
	//回调 
	function pageselectCallback(page_index, jq){
		var length = $("#hiddenresult div.result").length;
		var items_per_page = 2;		//每页显示2项
		var max_elem = Math.min((page_index+1) * items_per_page, length);
		$("#Searchresult").html("");
		// 获取加载元素
		for(var i=page_index*items_per_page;i<max_elem;i++){
			$("#Searchresult").append($("#hiddenresult div.result:eq("+i+")").clone());
		}
		
		return false;
	}
});

	



</@script>