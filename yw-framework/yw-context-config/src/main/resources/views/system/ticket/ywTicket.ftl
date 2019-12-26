<#assign base=request.contextPath />
<@screen id="ywDictionaryInsert" title="抽奖页面" places=[{"name":"抽奖页面","href":"#"}]>
<form id="ywDictionaryForm" action="insertOrUpdate.json" method="post">
    <input type="hidden" id="op_type" name="op_type" value="${op_type!''}"/>
	<#--<div class="container-box">-->
		<div class="formbody">
		    <div class="draw-title">
	                                       
	        </div>
	        <#-- 抽奖区域 -->
	        <div class="draw-container">
	                                            期待中...
	        </div>
	        
	        <div class="draw-box">
				<div class="draw" id="start"> 
					<span class="" >开始抽奖</span>
				</div>
				<div class="draw" id="stop" style="display:none">
					<span class="" >暂停</span>
				</div>
	        </div>
	        
	        <div class="person-box">
		        <div class="person-title">
		          <span>-</span>
		          <span style="font-size: 22px;margin-left: 20px;margin-right: 20px;">中奖动态</span>
		          <span>-</span>
		        </div>
		        <div class="person-body">
		          <table style="height: 50%;overflow: auto;">
		            <thead>
			          <tr style="color: #e53047;">
					    <th>幸运用户</th>
					    <th style="font-size: 14px;width: 0px;">|</th>
					    <th style="text-align: right;">学校名称</th>
					  </tr>
					  </thead>
					  <tbody class="person-table">
					 <#-- <tr style="color: #562122;" class="person-table">
					    <td>张家浩</td>
					    <td>|</td>
					    <td style="text-align: right;">武汉大学</td>
					  </tr>-->
					  </tbody> 
		          </table>
			<#--
				<div class="draw-person">
			       <span>获奖者：</span>
			       <span class="specific">待开奖</span>
			    </div>
			    -->
		    </div>
	        
		</div>
		
   <#-- </div>-->
</form>
<style>
body{
   background-image: url("${base}/image/ticket/bg_1.png");
    background-repeat: no-repeat;
   background-size: 100% 100%;
}

.formbody {
    padding: 10px 18px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}
.container-box{
    display: flex;
    flex-direction: row;
    justify-content: center;
}
.draw-box{
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    margin-top: 10px;
}
.draw{
   background-image: url("${base}/image/ticket/button.png");
    background-repeat: no-repeat;
   background-size: 100% 100%;
       /* font-size: 40px; */
    /* border-radius: 10px; */
    cursor: pointer;
    width: 163px;
    height: 69px;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
}
.draw span {
     font-size: 30px;
    /* margin-left: 20px; */
    /* margin-right: 20px; */
    color: #ffe711;
}
.draw-title{
   background-image: url("${base}/image/ticket/logo_1.png");
    background-repeat: no-repeat;
   background-size: 100% 100%;
       width: 487px;
    height: 126px;
       margin-top: 30px;
}
.draw-container{
    background-image: url(/yw-product-backend/image/ticket/bg_2.png);
    background-repeat: no-repeat;
    background-size: 100% 100%;
    font-size: 45px;
    height: 235px;
    width: 463px;
    /* background: yellow; */
    /* text-align: center; */
    /* vertical-align: middle; */
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    border-radius: 10px;
    margin-top: 40px;
    color: red;
}
.person-box{
   background-image: url("${base}/image/ticket/bg_3 .png");
   b background-repeat: no-repeat;
   background-size: 100% 100%;
   height: 232px;
    width: 457px;
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 20px;
    overflow: auto;
}
.person-title{
      display: flex;
    /* font-size: 35px; */
    color: #;
    color: #e53047;
    font-size: 22px;
    margin-top: 12px;
}
.person-title span{
     font-size: 22px;
}
.person-body {
   margin-top: 7px;
}

.person-body table tr th{
   font-size: 14px;
    /* margin-right: 131px; */
    /* right: 131px; */
    width: 120px;
}
}
.person-body table tr td{
   font-size: 14px;
    /* margin-top: 17px; */
    /* height: 50; */
    height: 40px;
}
.draw-person{
    width: 300px;
    background: chartreuse;
    display: flex;
    margin-top: 20px;
    border-radius: 2px;    
}

.draw-person span{
   font-size: 20px;
   border-radius: 2px;
}
    
</style>	
<@script>
$(function(){
  
  var key_flag = true; // 按键标志（控制开始和结束）
  var model_flag = false;// 模式切换标志
  var drawData = [];
  var defaultDrawData = [];
  <#if ywTickets?? && ywTickets?size gt 0>
    <#list ywTickets as ywTicket>
        drawData.push("${ywTicket.ticket_name}"+"-"+"${ywTicket.school_name}");
        <#if ywTicket.ticket_flag == "1">
          defaultDrawData.push("${ywTicket.ticket_name}"+"-"+"${ywTicket.school_name}");
        </#if>
    </#list>
  </#if>
  //var drawData = ["周震","晏飞","晋灿","任洋","张家浩","张三6","张三7","张三8","周震1","晏飞1","晋灿1","任洋1","张家浩1","张三61","张三71","张三81","周震2","晏飞2","晋灿2","任洋2","张家浩62","张三62","张三72","张三82","周震3","晏飞3","晋灿3","任洋3","张家浩3","张三63","张三73","张三83","周震4","晏飞4","晋灿4","任洋4","张家浩4","张三65","张三75","张三85"]
  //var defaultDrawData = ["周震","晏飞"];
  
  var drawController;
  // 开始
  $("#start").click(function(){
    $(this).hide();
    $("#stop").show();
    key_flag = false;
    drawController = setInterval(function(){
      $(".draw-container").html(drawData[parseInt(Math.random()*(drawData.length))]);
    },30)
  })
  
  // 停止
  $("#stop").click(function(){
    $(this).hide();
    $("#start").show();
    $(".person-table").html("");
    key_flag = true;
    window.clearInterval(drawController);
    if(model_flag){
       // 内定模式
       for(var i = 0 ; i < defaultDrawData.length ; i ++){
            $(".person-table").append("<tr style='color: #562122;'><td>"+ (defaultDrawData[i].split("-"))[0] +"</td><td>|</td><td style='text-align: right;'>"+(defaultDrawData[i].split("-"))[1]+"</td></tr>");
       }
        $(".draw-container").html("抽奖结束！");
      // $(".specific").html("内定");
    } else {
        // 普通模式
       // 生成30个不重复的随机数
       var dataArray = [];
       var obj = {};
       var count = 30
       if(drawData.length < 30){
         count = drawData.length
       }
       for(var i = 0 ; i < count ; i ++){
           var randomnum = parseInt(Math.random()*(drawData.length));
           if(!obj[randomnum]){  
	            obj[randomnum]=true;  
	            dataArray.push(randomnum);  
	        }  
	        else {  
	            i--;  
	        }
       }
       // 显示抽到的30个随机人数
       if(dataArray.length > 0){
          for(var i = 0 ; i < dataArray.length ; i ++){
            $(".person-table").append("<tr style='color: #562122;'><td>"+ (drawData[dataArray[i]].split("-"))[0] +"</td><td>|</td><td style='text-align: right;'>"+(drawData[dataArray[i]].split("-"))[1]+"</td></tr>");
            //$(".person-box").append("<div class='draw-person'><span>获奖者：</span><span class='specific'>"+ drawData[dataArray[i]] +"</span></div>");
          }
       }
       
       $(".draw-container").html("抽奖结束！");
       
    }
  })
  
  // 键盘控制开始和结束 
  $(document).keypress(function (e) {
   var keyCode = e.keyCode || e.which || e.charCode;
   var shiftKey = e.shiftKey || e.metaKey;
   var ctrlKey = e.ctrlKey || e.metaKey;
    // 空格控制(开始和结束)
    if (keyCode == 32){
       if(key_flag){
          $("#start").click();
       } else {
          $("#stop").click();
       }
    } else if(shiftKey && keyCode == 13){
    // 通过按键切换到内定模式(shift+enter)
       model_flag = true;
    } else if(ctrlKey && keyCode == 10){
    // 通过按键切换到普通模式(ctrl+enter)
       model_flag = false;
    }
    
    // 阻止浏览器默认行为
    e.preventDefault();
     
  })
})
        
</@script>
</@screen>