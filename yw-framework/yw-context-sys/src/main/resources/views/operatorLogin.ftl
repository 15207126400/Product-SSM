<#assign base=request.contextPath />
<@screen id="login" title="登录页面">
<form id="formid" action="${base}/operatorLogin.htm" method="post">
	<div class="login-body">
		 <#--
		 <div class="carousel slide srcoll-img login-body-left">
			<ol class="carousel-indicators" style="font-size: 40;bottom: 0;">
				<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
				<li data-target="#myCarousel" data-slide-to="1"></li>
				<li data-target="#myCarousel" data-slide-to="2"></li>
			</ol>   
			<div class="carousel-inner">
				<div class="item active">
					<img src="${base}/image/home/login/scroll-01.png" alt="First slide">
				</div>
				<div class="item">
					<img src="${base}/image/home/login/scroll-02.png" alt="Second slide">
				</div>
				<div class="item">
					<img src="${base}/image/home/login/scroll-03.png" alt="Third slide">
				</div>
			</div>
		</div>
		-->
		<div class="login-body-right">
		    <div class="app-row-center-layout login-body-right-top">后台登录</div>
			<ul>
				<li>
					<lable style="font-size:14px;font-weight:bold;">账号</lable>
					<input id="user_name" name="user_name" type="text" placeholder="请输入用户名" class="loginuser" maxlength="18"/>
				</li>
				<li>
					<input id="subsys_id" name="subsys_id" type="hidden" value="1"/>
				</li>
				<li>
					<lable style="font-size:14px;font-weight:bold;">密码</lable>
					<input id="password" name="password" type="password" placeholder="请输入密码" class="loginuser" maxlength="18"/>
				</li>
				<li>
					<lable style="font-size:14px;font-weight:bold;">验证码</lable>
					<input style="width:37%;margin-left:15px;" id="verifyCode" name="verifyCode" placeholder="请输入验证码" type="text" class="loginuser" maxlength="4"/>
					<@image id="imageCode" src="${base}/imageCode.img" paras={"width":"30%","height":"40px"}/>
				</li>
				<li><span style="color:red">${msg!''}</span></li>
				<div class="forget-psd-box">
					<a class="forget-psd" href="${base}/operatorForgetPassword.htm">忘记密码？</a>
				</div>
				<li class="btn-box">
					<input name="login" type="button" class="loginbtn" onclick="userLogin()" value="登  录" />
					<!-- 申请体验账号
					<a style="width:40%" href="${base}/operatorTryInfo.htm">
						<input name="register" type="button" class="regbtn" value="申请体验"/>
					</a>
					-->
				</li>
			</ul>
		</div>
	</div>
</form>
<@script>
//设置回车键触发按钮登录事件
$(document).keyup(function(event){
  if(event.keyCode ==13){
    $("input").trigger("click");
  }
});
</@script>
</@screen>
