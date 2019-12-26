<#assign base=request.contextPath />
<@screen id="ywDictionaryInsert" title="抽奖页面">
<div>
    <input type="hidden" id="op_type" name="op_type" value="${op_type!''}"/>
	<#--<div class="container-box">-->
		<div class="formbody">
			<#--下拉框选择中奖人数-->
			<div class="num-box">
				<lable>中奖人数设置: </lable>
				<select id="num">
	                <option value="1">1</option>
	                <option value="3">3</option>
	                <option value="5">5</option>
	                <option value="10">10</option>
	                <option value="15">15</option>
	                <option value="20">20</option>
	                <option value="25">25</option>
	                <option value="30">30</option>
	            </select>
	            <text id="personNum"></text>
			</div>
			
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
		          <table style="height: 50%;width: 100%;overflow: auto;">
		            <thead>
			          <tr style="color: #e53047;">
					    <th>幸运用户</th>
					    <th>中奖人员部门职位</th>
					  </tr>
					  </thead>
					  <tbody class="person-table">
					  
					  </tbody> 
		          </table>
		    </div>
		</div>
		
</div>
<style>
.right-content{
	background:#eee;
	margin: 0px;
    border-radius: 0px;
    overflow: auto;
}
.formbody {
	background-image: url("${base}/image/ticket/bg_1.png");
    background-repeat: no-repeat;
    background-size: 100% 100%;
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
    background-image: url("${base}/image/ticket/bg_2.png");
    background-repeat: no-repeat;
    background-size: 100% 100%;
    font-size: 28px;
    height: 235px;
    width: 463px;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    border-radius: 10px;
    margin-top: 40px;
    color: red;
}
.person-box{
    background-image: url("${base}/image/ticket/bg_3.png");
    background-repeat: no-repeat;
    background-size: 100% 100%;
    padding-top:20px;
    padding-bottom:80px;
    width: 457px;
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 20px;
    overflow: auto;
}
.person-title{
    display: flex;
    color: #;
    color: #e53047;
    font-size: 22px;
    margin-top: 12px;
}
.person-title span{
    font-size: 22px;
}
.person-body {
    margin-top: 10px;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
}
.person-body table tr th{
    font-size: 14px;
    width: 200px;
    text-align: center;
}
.person-body table tr td{
    font-size: 14px;
    height: 40px;
    width: 200px;
    text-align: center;
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
.num-box{
	position: absolute;
	top: 45%;
	right: 10%;
}
#num{
    width: 150px;
    height: 30px;
    line-height: 30px;
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
        drawData.push("${ywTicket.ticket_name}" + "-" + "${ywTicket.school_name}");
        <#if ywTicket.ticket_flag == "1">
          defaultDrawData.push("${ywTicket.ticket_name}"+"-"+"${ywTicket.school_name}");
        </#if>
    </#list>
  </#if>
  
  console.log(drawData);
  
  var drawController;
  // 开始
  $("#start").click(function(){
    $(this).hide();
    $("#stop").show();
    key_flag = false;
    drawController = setInterval(function(){
      $(".draw-container").html(drawData[parseInt(Math.random()*(drawData.length))]);
    },50)
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
       var dataArray = [];
       var obj = {};
       var num = $("#num").val();		//设置中奖人数
       
       for(var i = 0 ; i < defaultDrawData.length ; i ++){
           var randomnum = parseInt(Math.random()*(defaultDrawData.length));
           if(!obj[randomnum]){  
	            obj[randomnum]=true;  
	            dataArray.push(randomnum);  
	       } else {  
	            i--;  
	       }
	       if(dataArray.length >= num){
	        	break;
	       }
       }
       
       for(var i = 0 ; i < dataArray.length ; i ++){
       	   $(".person-table").append("<tr style='color: #562122;'><td>"+ (defaultDrawData[dataArray[i]].split("-"))[0] +"</td><td>"+(defaultDrawData[dataArray[i]].split("-"))[1]+"</td></tr>");
       }
       $(".draw-container").html("抽奖结束！");
    } else {
       // 普通模式
       var dataArray = [];
       var obj = {};
       var num = $("#num").val();		//设置中奖人数
       
       //过滤数据及添加数据到随机数组中
       isRand(drawData , dataArray , obj , num);
       
       if(dataArray.length < num){
          dataArray = [];
          $(".person-table").append("<tr style='color: #562122;'><td>无</td><td>无</td></tr>");
          $(".draw-container").html("抽奖人数少于中奖设置人数!");
       }
       
       if(dataArray.length > 0){
          for(var i = 0 ; i < dataArray.length ; i ++){
            $(".person-table").append("<tr style='color: #562122;'><td>"+ (drawData[dataArray[i]].split("-"))[0] +"</td><td>"+(drawData[dataArray[i]].split("-"))[1]+"</td></tr>");
          	delete drawData[dataArray[i]];
          }
          $(".draw-container").html("抽奖结束！");
       }
       
       var personNum = 0;
       for(var i = 0; i<drawData.length; i++){
       		if(typeof(drawData[i]) != "undefined"){
       			personNum++;
       		}
       }
       $("#personNum").text("剩余" + personNum + "人");
     }
  })
  
  //过滤数据及添加数据到随机数组中
  function isRand(drawData , dataArray , obj , num){
  console.log("num :" + num);
  	  var newData = [];
  	  //遍历数据,将已删除的数据排除,剩下的扔到一个新的数组
  	  for(var j = 0 ; j < drawData.length ; j ++){
  	      if(typeof(drawData[j]) != "undefined"){
  	          newData.push(drawData[j]);
  	      }
  	  }
  	  //判断设置人数是否大于剩余人数
  	  if(num > newData.length){
          alert("设置抽奖人数不得大于总人数");
          return false;
      }
      for(var i = 0 ; i < drawData.length ; i ++){
          var randomnum = 1; 
       	  if(drawData.length == 1){
       	      randomnum = randomnum;
       	  } else {
       	   	  randomnum = parseInt(Math.random()*(drawData.length));
       	  }
          if(typeof(drawData[randomnum]) != "undefined"){
           	  if(!obj[randomnum]){  
		          obj[randomnum]=true;  
		          dataArray.push(randomnum); 
		      } else {  
		          i--;  
		      }
		      if(dataArray.length == num){
	              break;
		      }
          }
      }
      if(dataArray.length < num){
      	  isRand(drawData , dataArray , obj , num);
      }
  }
  
    //键盘控制开始和结束 
    $(document).keypress(function (e) {
        var keyCode = e.keyCode || e.which || e.charCode;
        var shiftKey = e.shiftKey || e.metaKey;
        var ctrlKey = e.ctrlKey || e.metaKey;
        //空格控制(开始和结束)
        if (keyCode == 32){
            if(key_flag){
                $("#start").click();
            } else {
                $("#stop").click();
            }
        } else if(shiftKey && keyCode == 13){
            //通过按键切换到内定模式(shift+enter)
            model_flag = true;
        } else if(ctrlKey && keyCode == 10){
            //通过按键切换到普通模式(ctrl+enter)
            model_flag = false;
        }
        // 阻止浏览器默认行为
        e.preventDefault();
    })
    
})
        
</@script>
</@screen>