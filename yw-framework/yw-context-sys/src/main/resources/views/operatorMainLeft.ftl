<#assign base=request.contextPath />
	<!-- 新的管理员菜单(权限后续添加) -->
	<div class="leftmenu">
		<ul class="menuson menu-index active" style="margin-top: 16px;">
			<li style="margin-left: 48;">
			  <a style="font-weight: bold;font-size: 14px;" href="operatorMainRight.htm" target="rightFrame" class="menu-li app-row-start-layout">
			    <img class="menu-icon" src="${base}/image/home/left/format/menu-index.png"/>
			    <span style="font-size: 14px;margin-left: 8;">首页</span>
			  </a>
			  <i style="top:15px"></i>
			</li>
		</ul>
	</div>
	<dl style="margin-top: 1px;">
	   <dd></dd>
	</dl>
 <#if user??>
	<#assign roleResource = SpringContext.getBean("ywRoleCache").getConfigData() />
    <#assign menuResource = SpringContext.getBean("ywMenuCache").getConfigData() />
    <#assign resStr = "," />
    
    <#-- 系统管理员 -->
    <#if user.user_type == "1">
	    <#if roleResource??>
	        <#if customer.ro_ids?trim != "">
	            <#list customer.ro_ids?split(",") as ro_id>
	                <#if roleResource[ro_id]??>
	                    <#assign resStr = resStr + (roleResource[ro_id])["mu_ids"] />
	                </#if>
	            </#list>
	        </#if>
	    </#if>
    </#if>
    <div style="overflow-y:auto;height:550px;overflow-y: auto;overflow-x: hidden;height: 600;width: 187;">
    <#assign resStr = resStr + "," />
	<#if menuResource??>
	   <#assign resArr = SpringContext.getBean("ywMenuCache").getMenuIds(resStr) />
	   <#list menuResource?keys as mu_id>
	        <#assign menu = menuResource[mu_id]>
	         <#if menu.mu_level == "1" && SpringContext.getBean("ywMenuCache").isContainsParentMenu("${menu.mu_id}","${resStr}")>
	            <dl class="leftmenu">
	              <dd>
	                <div class="title">
						<span><#if menu.mu_icon??><img class="menu-icon" src="${base}/image/home/left/${menu.mu_icon}"/><#else><img class="menu-icon" src="${base}/image/home/left/menu-default.png"/></#if></span>${menu.mu_name}
					</div>
					
					<#if menu.mu_issub_node == "1">
	                   <#assign subList = SpringContext.getBean("ywMenuCache").getSubMenuResource(mu_id) />
	                   <#if (subList?size > 0)>
	                   <ul class="menuson" style="display:none">
	                    <#list subList as subMenu>
	                      <#if resArr?seq_contains("${subMenu.mu_id}")>
		                     <li><cite></cite><a href="${base}${subMenu.mu_url}" onclick="removeSessionStorage('${subMenu.mu_url}')"  target="rightFrame" class="menu-li">${subMenu.mu_name}</a><i></i></li>
	                      </#if>
	                   </#list>
	                   </ul>
	                   </#if>
	                </#if>
	              </dd>
	            </dl>
	         </#if>
	   </#list>
	</#if>
 </#if>	
 </div>
 <@script>
 var flag;
$(function(){
	//子菜单切换
	$(".menuson li").click(function(){
	    // 判断是否是首页
	    if($(this).parent().hasClass("menu-index")){
	      // 撤销其他菜单选中状态
	      var imgOld = $(".leftmenu div.active").css({"color":"black"}).removeClass("active").find("img");
	      if(imgOld.attr("src")){
	        imgOld.attr("src",imgOld.attr("src").replace("format/",""));
	      }
	      $(this).children().css({"color":"#fff"});
	      if(!$(this).parent().hasClass("active")){
	         var imgNew = $(this).find("img");
		     var imgNewBefore = imgNew.attr("src").substr(0,imgNew.attr("src").lastIndexOf("/"));
		     var imgNewAfter = imgNew.attr("src").substr(imgNew.attr("src").lastIndexOf("/"));
		     imgNew.attr("src",imgNewBefore+"/format"+imgNewAfter);
	      }
		  $(this).parent().addClass("active");
		  
	      // 撤销其他子菜单选中
	      $(".menuson li.active").removeClass("active");
	    } else {
	      $(".menuson li.active").removeClass("active");
		  $(this).addClass("active");
		  $(this).parent().prev().css({"color":"#fff"});
		  var imgNew = $(this).parent().prev().find("img");
		  if(!$(this).parent().prev().hasClass("active")){
		     var imgNewBefore = imgNew.attr("src").substr(0,imgNew.attr("src").lastIndexOf("/"));
		     var imgNewAfter = imgNew.attr("src").substr(imgNew.attr("src").lastIndexOf("/"));
		     imgNew.attr("src",imgNewBefore+"/format"+imgNewAfter);
		  }
		  $(this).parent().prev().addClass("active");
		  // 删除首页导航背景
	      $(".menu-index").children().children().css({"color":"black"}).parents(".menu-index").removeClass("active");
	      var imgOld = $(".menu-index").children().children().find("img");
	      if(imgOld.attr("src")){
	        imgOld.attr("src",imgOld.attr("src").replace("format/",""));
	      }
	    }
		
	});
	
	// 父菜单切换
	$('.title').click(function(){
	    // 删除首页导航背景
	    $(".menu-index").children().children().css({"color":"black"}).parents(".menu-index").removeClass("active");
	    var imgOldIndex = $(".menu-index").children().children().find("img");
	    if(imgOldIndex.attr("src")){
	       imgOldIndex.attr("src",imgOldIndex.attr("src").replace("format/",""));
	    }
	    var imgOld = $(".leftmenu div.active").css({"color":"black"}).removeClass("active").find("img");
	    if(imgOld.attr("src")){
	       imgOld.attr("src",imgOld.attr("src").replace("format/",""));
	    }
	    
		$(this).addClass("active");
		$(this).css({"color":"#fff"});
		var imgNew = $(this).find("img");
		var imgNewBefore = imgNew.attr("src").substr(0,imgNew.attr("src").lastIndexOf("/"));
		var imgNewAfter = imgNew.attr("src").substr(imgNew.attr("src").lastIndexOf("/"));
		imgNew.attr("src",imgNewBefore+"/format"+imgNewAfter);
		var $ul = $(this).next('ul');
		$('dd').find('ul').slideUp();
		if($ul.is(':visible')){
			$(this).next('ul').slideUp();
		}else{
			$(this).next('ul').slideDown();
		}
		flag = $('.title').index($(this));
	});
})
<#--  
      暴露给外部调用菜单导航接口getMenu
  p_index 父菜单索引(索引从0开始)
  s_index 子菜单索引(索引从0开始)
-->

function getMenu(p_index,s_index){
    // 父菜单切换(判断是否已经点击过)
    if(flag != p_index){
       $('.title').eq(p_index).click();
    }
    flag = p_index;
    
    // 子菜单切换
    $('.title').eq(p_index).next().find("li").eq(s_index).click();
}

// 删除当前页面缓存
 function removeSessionStorage(pathName){
    sessionStorage.removeItem(pathName);
 }	
</@script>

	
