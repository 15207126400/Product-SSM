<#assign base=request.contextPath />
<@screen id="ywRightIndex" title="首页" places=[{"name":"首页","href":"#"}]>
<style>
.big-box{
   display: flex;
   flex-direction: row;
   justify-content: space-around;
   align-items: center;
}
.index-bg{
	display: flex;
    flex-direction: column;
    justify-content: flex-end;
	width: 100%;
	height: 80%;
	background: url('${base}/img/qhz/index.jpg');
	background-size: 100% 100%;
	color: #fff;
}
.index-text{
	margin: 30px;
	font-size: 25px;
	font-weight: bold;
	font-family: '楷体';
}
</style>
	<div class="index-bg">
		<text class="index-text">启恒智互联科技有限公司</text>
	</div>
	
</@screen>

