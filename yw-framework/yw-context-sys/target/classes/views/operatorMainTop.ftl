<#assign base=request.contextPath />
	<#--
    <div class="topleft">
    	<a href="${base}/operatorMain.htm" target="_parent">
    		<img src="${base}/image/home/images/top/logo.png" />
    	</a>
    </div>
    -->
    <#--滚动公告栏-->
    <div style="position:absolute;width:60%;color:#fff;margin:10px;padding:20px;background:rgba(215,106,0,0.1);border-radius:5px;">
    	<marquee style="height:30px;line-height:45px;" behavior="scroll" direction="up" 
    		scrollamount="10" scrolldelay="1000" onMouseOut=start(); onMouseOver=stop();>
	        <ul>
	        	<#if systemNotices?? && systemNotices?size gt 0>
					<#list systemNotices as systemNotice>
						<#if systemNotice.notice_type == 0>
	        				<li>全网通告 : ${systemNotice.notice_content}</li>
	        			<#else>
	        				<li>用户提醒 : ${systemNotice.notice_content}</li>
	        			</#if>
	        		</#list>
	        		<#else>
	        		<li>近期没有通知信息</li>
	        	</#if>
	        </ul>
	    </marquee>
    </div>
    
    <div class="topright">  
    	<div class="app-row-center-layout" style="margin-bottom:12;margin-right: 20;">
    	    <div id="avatar">
    	    	<img src="${ywUser.user_avg!''}" title="用户头像" style="width:62px;height:62px;cursor:pointer"/>
    	    </div>
    	    
    	    <div style="margin-left:15px;">
    	      <div style="font-size:15px;color:#fff;margin-bottom:15px;">${user.cust_name!''} , 您好</div>
    	      <div class="app-row-start-layout" style="color:#fff;cursor:pointer" onclick="exit()">
    	         <img src="${base}/image/home/top/top-exit.png" title="退出" />
    	         <span style="margin-left:4px;font-size:14px;">退出</span>
    	      </div>
    	    </div>
    	</div>   
      	<div style="margin-right: 22;">
    		<ul class="">
    			<li class="help-li app-row-center-layout">
    			  <img src="${base}/image/home/top/top-help.png" title="帮助" />
    			  <a id="openHelp" href="#">帮助</a>
    			</li>
    			<li class="app-row-center-layout">
    			  <img src="${base}/image/home/top/top-resetpassword.png" title="重置密码" />
    			  <a href="${base}/operatorResetPassword.htm" target="rightFrame">重置密码</a>
    			</li>
    		</ul>
     	</div>
    </div>
<@script>
$(function(){
     $(".topright").mousemove(function(e){
        e = e || window.event;
        // 不在下拉菜单区域隐藏下拉菜单
	    if (!($(e.target).hasClass('message-li') || $(e.target).parent().hasClass('message-li') || $(e.target).parent().parent().hasClass('message-li'))) {
	       $(".message-li").css({"background":"url(../image/home/top/top-line.png) no-repeat right"});
	       $(window.parent.frames["rightFrame"].document).find(".message-menu").hide();
	    }
	    // 不在下拉菜单区域隐藏下拉菜单
	    if (!($(e.target).hasClass('help-li') || $(e.target).parent().hasClass('help-li') || $(e.target).parent().parent().hasClass('help-li'))) {
	       $(".help-li").css({"background":"url(../image/home/top/top-line.png) no-repeat right"});
	       $(window.parent.frames["rightFrame"].document).find(".help-menu").hide();
	    }
     }).on("mouseenter",".message-li",function(){
         //消息菜单
         self.parent.frames['rightFrame'].messageMenu(this);
     }).on("mouseenter",".help-li",function(){
        // 帮助菜单
        self.parent.frames['rightFrame'].helpMenu(this);
     })
})
	
	//更换头像
	$('#avatar').click(function(e){
		$(window.parent.frames["rightFrame"].document).find("#avg-box").fadeToggle();
	})
</@script>