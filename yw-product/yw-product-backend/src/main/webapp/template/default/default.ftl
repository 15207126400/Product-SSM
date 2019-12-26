<#assign base=request.contextPath />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>启恒智互联科技有限公司</title>
<link rel="shortcut icon" href="https://www.qhzhlkj.com/files/image/qhz/qhz_icon.png">
<#--<title>${title?default("")}</title>-->
<!-- 本地js,css文件引入 -->
<link href="${base}/css/bootstrap.css" rel="stylesheet">
<#--
<link rel="stylesheet" href="${base}/css/style.css" type="text/css">
-->
<link rel="stylesheet" href="${base}/css/main-1.1.css" type="text/css">

<style>
   .notice{right:0;bottom:0;cursor:default;position:fixed;background-color:#F0F9FD;color:#1F336B;z-index:999;border:1px #F2F2FB solid;margin:2px;padding:10px;font-weight:bold;line-height:25px;display:none;width:400px;height:200px}
   .notice .cbtn{color:#FF0000;cursor:pointer;padding-right:5px;float:right;position:relative}
   .notice .notice_content{margin:3px;font-weight:normal;border:1px #B9C9EF solid;line-height:20px;margin-bottom:10px;padding:10px;height:135px}
   .message-menu{
     position: fixed;
     right: 201;
     top: 0;
     background: #88d2ff;
     width: 249;
     height: 170;
     box-shadow: 5px 5px 6px #b5b5b5;
   }
   .help-menu
   {
	   display: flex;
	   flex-direction: column;
	   align-items: center;
	   justify-content: center;
	   position: fixed;
       right: 103;
       top: 0;
       background: #fff;
       width: 249;
       box-shadow: 5px 5px 6px #b5b5b5;
   }
   .help-item{
   	    color: #fff;
	    border-bottom: 1px solid #F0F0F0;
	    width: 100%;
	    text-align: center;
	    line-height: 3;
	    cursor: pointer;
	    background: #88d2ff;
	    font-size: 14;
	    font-weight: bold;
   }

   .help-item:hover{
      color: #fb5b1e;
   }
   
   a{ 
       text-decoration:none; 
       color:#333; 
    } 
/**用户头像部分**/
.avg-box{
	display: none;
    position: fixed;
    top: 0px;
    right: 250px;
    width: 280px;
    line-height: 70px;
    background: #fff;
    padding: 30px;
}
.avg{
	width:50px;
	height:50px;
	cursor:pointer;
}
</style>
<@script src="${base}/js/jquery-3.2.1.min.js"/>
<@script src="${base}/js/bootstrap.min.js"/>

<#-- 云维平台工具 -->
<@script src="${base}/js/comm.js"/>
<@script src="${base}/js/yunw-dev-utils.js"/>
<@script src="${base}/js/yunw-dev-validate.js"/>
<@script src="${base}/js/formValidate.js"/>

<#-- webwsocket连接js文件 -->
<#-- 生日选择器js及json文件 
<@script src="${base}/js/birthday.js"/>-->
<#-- excl导出插件
<@script src="${base}/js/tableExport.js"/> -->
<#-- Base64解码 
<@script src="${base}/js/base64.js"/>-->
<@script>

/**
 * 登录超时显示内容
 */
function userTimeoutHtml(){
   var timeOut_html = '用户未登录或已超时<a id="userTimeOut" style="font-size: 15px;margin-left: 10px;" href="javascript:void(0)" onclick="top.location.href=\'${base}/\'"><span id="jumpTo" style="display:inline;font-size: 20px;">3</span>秒后系统会自动跳转到登录页，也可点击此处直接进入</a>';
   return timeOut_html;
}

/**
 * 登录超时
 */
function userTimeout(){
   $("#userTimeOut").attr("href", "javascript:void(0)");
	$("#userTimeOut").unbind();
	countDown(3,'${base}/');
}

 /**
 * 超时自动跳转到登录页
 */ 
 function countDown(secs,surl){           
	 var jumpTo = document.getElementById('jumpTo');  
	 jumpTo.innerHTML=secs;    
	 if(--secs>0){       
     	setTimeout("countDown("+secs+",'"+surl+"')",1000);       
     } else{         
     	top.location.href=surl;    
     }       
 }
 
var time;
var delayTime;
var _topSelf;// top页面中的元素对象
$(function(){


	/*
	*	替换头像
	*/
	$('.avg').click(function(){
		console.log($(this)[0].src);
		var avg_img = $(this)[0].src;
		$.ajax({  
	          url: '${base}/system/ywUserCustomer/changeAvg.json?avg_img=' + avg_img ,  
	          type: 'GET',  
	          data: {},  
	          async: false,  
	          cache: false,  
	          contentType: false,  
	          processData: false,  
	          success: function (data) {  
	            if (data.error_no == 0) {
	                $(window.parent.frames["topFrame"].document).find("#avatar img").attr('src',avg_img);
	            }
	          },  
	          error: function (returndata) {  
	             //devShowDialog("系统异常");
	          }  
	     });  
	     $('#avg-box').fadeOut();
	});
     
    /* 绑定页面右下角弹出窗口事件*/
    $('#notice').hover(function(){
            $(this).stop(true,true).slideDown();
            clearTimeout(time);
        },
        function(){
            time = setTimeout('_notice()',delayTime);
        });
     
    // 绑定关闭事件
    $('.cbtn').bind('click',function(){
        $('#notice').slideUp(2000);//向上滑动
        clearTimeout(time);
    });
    
    // body事件绑定
    $("body").mouseenter(function(e){
        e = e || window.event;
        // 不在下拉菜单区域隐藏下拉菜单
	    if (!$(e.target).closest('.message-menu').length && $(".message-menu").is(':visible')) {
	        $(_topSelf).css({"background":"url(../image/home/top/top-line.png) no-repeat right"});
	        $(".message-menu").hide();
	    }
	
	    if (!$(e.target).closest('.help-menu').length && $(".help-menu").is(':visible')) {
	        $(_topSelf).css({"background":"url(../image/home/top/top-line.png) no-repeat right"});
	        $(".help-menu").hide();
	    }
    })
    
    // 移开下拉菜单时隐藏
    $(".message-menu").mouseleave(function(){
        $(".message-menu").hide();
        $(_topSelf).css({"background":"url(../image/home/top/top-line.png) no-repeat right"});
    }).on("click","li",function(){
        // 点击消息项的触发左边菜单切换
        self.parent.frames['leftFrame'].getMenu(1,1);
    })
    
    // 移开下拉菜单时隐藏
    $(".help-menu").mouseleave(function(){
        $(".help-menu").hide();
        $(_topSelf).css({"background":"url(../image/home/top/top-line.png) no-repeat right"});
    })
});
// 弹出消息扩展为jquery的方法
$.extend({
    lay:function(_width,_height){
        $('#notice').css({width:_width,height:_height});
    },
    show:function(_title,_msg,_time){
         delayTime = _time;
         $('.notice_title').html(_title);
         $('.notice_content').html(_msg);
         $('#notice').slideDown(2000);// 向下滑动
         time = setTimeout('_notice()',delayTime);
    }
});

// 向上滑动函数
function _notice(){
    $('#notice').slideUp(2000);// 向上滑动
}
// 页面右下角消息显示
function initMessage(Msg){
    var returnMsg = "<p>信息1 您有新的订单，请及时处理</p><p>信息2 notice.js</p>";
    $.show('提示信息',Msg != undefined?Msg:returnMsg,5000);
}

// 消息下拉菜单显示或隐藏
function messageMenu(topSelf){
   _topSelf = topSelf;
   $(topSelf).css({"background":"#88d2ff"});
   // 判断是否有滚动条(有滚动条动态增加不同距离)
   $("body").scrollTop(10);//控制滚动条下移10px
   if( $("body").scrollTop()>0 ){
	  $(".message-menu").css({"right": "211"});
   }else{
      $(".message-menu").css({"right": "218"});
   }
   $("body").scrollTop(0);//滚动条返回顶部
   $(".message-menu").show();
}

// 帮助菜单显示或隐藏
function helpMenu(topSelf){
	_topSelf = topSelf;
   $(topSelf).css({"background":"#88d2ff"});
   // 判断是否有滚动条(有滚动条动态增加不同距离)
   $("body").scrollTop(10);//控制滚动条下移10px
   if( $("body").scrollTop()>0 ){
	  $(".help-menu").css({"right": "113"});
   }else{
      $(".help-menu").css({"right": "120"});
   }
   $("body").scrollTop(0);//滚动条返回顶部
   $(".help-menu").show();
}

</@script>
</head>
<body <#if id?has_content>class="page-${id}"</#if> style="background-color: #eee;">
      <div class="right-content">
        <#if places?? && places?size gt 0>
        <div class="place">
			<span>位置：</span>
			<ul class="placeul">
		       <#list places as place>
				 <li><a href="${place.href!''}">${place.name!''}</a></li>
			   </#list>
				<#--
				<li><a href="${base}/product/ywProduct.htm">商品信息</a></li>
				<li><a href="#">添加商品</a></li>-->
			</ul>
		</div>
		</#if>
        <div class="specific">
			${screen_content}
		</div>
	  </div>	
	<#-- 增加模态框消息提示 
	<@messageAlert/>-->
	<#-- 图片异步上传
	<@fileAsynUpload name="upload_ue"/> -->
	<#-- 右下角弹出提示框 -->
	<div id='notice' class='notice' onselectstart='return false' style="display:none">
	   <span class='notice_title'> </span>
	   <span class='cbtn'>[关闭]</span>
	   <div class='notice_content'></div>
	</div>
	<#-- 消息下拉菜单
	<div class="message-menu" style="display:none">
	     <ul class="">
			<li style="border-bottom: solid 1px #fff;">
			  <a href="${base}/product/ywOrder.htm?order_status=2" class="app-row-center-layout asassssss">
			    <span style="font-size: 14">订单消息(待发货)：</span>
			    <span class="label label-default" id="order-count" style="font-size: 14">${user.order_count!'0'}条</span>
			   </a>
			</li>
			<li style="border-bottom: solid 1px #fff;">
			  <a href="${base}/product/ywOrder.htm?order_status=2" class="app-row-center-layout asassssss">
			    <span style="font-size: 14">订单消息(待发货)：</span>
			    <span class="label label-default" id="order-count" style="font-size: 14">${user.order_count!'0'}条</span>
			   </a>
			</li>
			<li style="border-bottom: solid 1px #fff;">
			  <a href="${base}/product/ywOrder.htm?order_status=2" class="app-row-center-layout asassssss">
			    <span style="font-size: 14">订单消息(待发货)：</span>
			    <span class="label label-default" id="order-count" style="font-size: 14">${user.order_count!'0'}条</span>
			   </a>
			</li>
			<li>
			  <a href="${base}/product/ywOrder.htm?order_status=2" class="app-row-center-layout asassssss">
			    <span style="font-size: 14">查看更多</span>
			   </a>
			</li>
		</ul>
	  </div>
	   -->
	  <#--更换头像-->
	  <div id="avg-box" class="avg-box">
	  	<img class="avg" src="https://www.qhzhlkj.com/files/image/qhz/avg/man_avg1.png" />
	    <img class="avg" src="https://www.qhzhlkj.com/files/image/qhz/avg/man_avg2.png" />
	    <img class="avg" src="https://www.qhzhlkj.com/files/image/qhz/avg/man_avg3.png" />
	    <img class="avg" src="https://www.qhzhlkj.com/files/image/qhz/avg/man_avg4.png" />
	    <img class="avg" src="https://www.qhzhlkj.com/files/image/qhz/avg/woman_avg1.png" />
	    <img class="avg" src="https://www.qhzhlkj.com/files/image/qhz/avg/woman_avg2.png" />
	    <img class="avg" src="https://www.qhzhlkj.com/files/image/qhz/avg/woman_avg3.png" />
	    <img class="avg" src="https://www.qhzhlkj.com/files/image/qhz/avg/woman_avg4.png" />
	  </div>
	  <#--帮助菜单-->
	  <div id="help-box" class="help-menu" style="display:none">
	  	<a class="help-item" href="${base}/operatorHelp.htm?help_flag=0" target="_blank">通用帮助文档</a>
	  	<#--暂时隐藏
		<a class="help-item" href="${base}/operatorHelp.htm?help_flag=1" target="_blank">商品管理</a>
		<a class="help-item" href="${base}/operatorHelp.htm?help_flag=2" target="_blank">营销管理</a>
		<a class="help-item" href="${base}/operatorHelp.htm?help_flag=3" target="_blank">短信管理</a>
		<a class="help-item" href="${base}/operatorHelp.htm?help_flag=4" target="_blank">会员管理</a>
		<a class="help-item" href="${base}/operatorHelp.htm?help_flag=5" target="_blank">商家管理</a>
		<a class="help-item" href="${base}/operatorHelp.htm?help_flag=6" target="_blank">积分管理</a>
		<a class="help-item" href="${base}/operatorHelp.htm?help_flag=7" target="_blank">签到管理</a>
		<a class="help-item" href="${base}/operatorHelp.htm?help_flag=8" target="_blank">配置中心管理</a>
		<a class="help-item" href="${base}/operatorHelp.htm?help_flag=9" target="_blank">小程序版本管理</a>
		<a class="help-item" href="${base}/operatorHelp.htm?help_flag=10" target="_blank">导入模板管理</a>
		-->
	  </div>
</body>
</html>