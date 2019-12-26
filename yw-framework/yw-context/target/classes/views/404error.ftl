<#assign base=request.contextPath />
<@screen id="404" title="404页面">
<style>
   body{
      background: #edf6fa;
   }
</style>
<script language="javascript">
	$(function(){
    $('.error').css({'position':'absolute','left':($(window).width()-490)/2});
	$(window).resize(function(){  
    $('.error').css({'position':'absolute','left':($(window).width()-490)/2});
    })  
});  
</script> 
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<li><a href="javascript:void(0)" onclick="top.location.href='${base}/login.htm'">返回首页</a></li>
		</ul>
	</div>

	<div class="error">
		<h2>非常遗憾，您访问的页面不存在！</h2>
		<p>看到这个提示，就自认倒霉吧!</p>
		<div class="reindex">
			<a href="javascript:void(0)" target="_parent" onclick="top.location.href='${base}/login.htm'">返回首页</a>
		</div>
	</div>
</@screen>