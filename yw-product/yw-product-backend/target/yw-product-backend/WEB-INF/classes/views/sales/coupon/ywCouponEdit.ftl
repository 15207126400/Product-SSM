<@screen id="ywCouponInsert" title="优惠券配置信息页面" places=[{"name":"优惠券配置信息","href":"${base}/sales/ywCoupon.htm"},{"name":"添加/修改","href":"#"}]>
<form id="ywCouponForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type}"/>
    <input id="id" name="id" type="hidden" value="${ywCoupon.id!''}"/>
	<div class="formtitle">
		<span class="formspan">优惠券配置信息</span>
	</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>优惠券名称</label>
					<input id="coupon_name" name="coupon_name" type="text" valid="NotBlank" class="dfinput" placeholder="请输入优惠券名称" maxbytelength="255" value="${ywCoupon.coupon_name!''}"/>
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>发放数量</label>
					<input id="coupon_count" style="width:150" name="coupon_count" type="text" valid="NotBlank,Digital" class="dfinput" placeholder="请输入发放数量" maxbytelength="10" value="${ywCoupon.coupon_count!''}"/>
			        <i>张</i>
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>场景类型</label>
					<@select valid="NotBlank" placeholder="请选择场景类型" name="coupon_scene_type" dic_key="1085" value="${ywCoupon.coupon_scene_type!''}" />
			</div>
			<div class="form-group">
			        <label for="level" class="label"><em style="color:red;">*</em>作用类型</label>
					<@radio valid="NotBlank" placeholder="请选择作用类型" name="coupon_function_type" dic_key="1018" value="${ywCoupon.coupon_function_type!'1'}" />
					
					<div style="margin-left: 183;<#if ywCoupon.coupon_function_type == "4">display:none</#if>">
						<input id="coupon_price" style="width:150" name="coupon_price" type="text" valid="NotBlank,float" class="dfinput" placeholder="请输入满减金额（元）" maxbytelength="50" value="${ywCoupon.coupon_price!''}"/>
				        <i>￥</i>
			        </div>
			</div>
			<div class="form-group">
			        <label for="level" class="label"><em style="color:red;">*</em>使用条件</label>
			        <@radio valid="NotBlank" placeholder="请选择使用条件类型" name="coupon_use_condition_type" dic_key="1087" value="${ywCoupon.coupon_use_condition_type!'1'}" />
			        
			        <div style="margin-left: 183;">
				        <span>满</span>
						<input id="coupon_use_condition" style="width:150" name="coupon_use_condition" type="text" valid="NotBlank,float" class="dfinput" placeholder="单位：元" maxbytelength="50" value="${ywCoupon.coupon_use_condition!''}"/>
				        <span>元可使用</span>
			        </div>
			</div>
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>使用范围</label>
					<@radio valid="NotBlank" placeholder="请选择使用范围类型" name="coupon_use_scope_type" dic_key="1086" value="${ywCoupon.coupon_use_scope_type!'0'}" />
			        
			        <#if (ywCoupon.coupon_use_scope_type)?default("0") == "0">
				        <div style="margin-left: 183;display:none">
							<@select2 valid="NotBlank" placeholder="请选择商品分类" name="coupon_use_scope" dic_key="1085" value="${ywCoupon.coupon_use_scope!''}" />
				        </div>
			        <#elseif (ywCoupon.coupon_use_scope_type)?default("0") == "1">
			            <div style="margin-left: 183;">
							<@select2 valid="NotBlank" placeholder="请选择商品分类" name="coupon_use_scope" datalist=ywProductClassifys optionData={"value":"classify_id","option":"classify_name"} value="${ywCoupon.coupon_use_scope!''}" />
				        </div>
			        <#elseif (ywCoupon.coupon_use_scope_type)?default("0") == "2">
			            <div style="margin-left: 183;">
							<@select2 valid="NotBlank" placeholder="请选择商品" name="coupon_use_scope" datalist=ywProducts optionData={"value":"id","option":"title"} value="${ywCoupon.coupon_use_scope!''}" />
				        </div>
			        </#if>
			</div>
			
			<div class="form-group">
			        <label for="level" class="label"><em style="color:red;">*</em>领取条件</label>
			        <@radio valid="NotBlank" placeholder="请选择领取条件" name="coupon_collection_condition_type" dic_key="1019" value="${ywCoupon.coupon_collection_condition_type!'1'}" />
			
			        <div style="margin-left: 183;<#if ywCoupon.coupon_collection_condition_type == "0">display:none</#if>">
			        	<@select valid="NotBlank" placeholder="请选择会员等级" name="coupon_collection_condition" datalist=ywMemberRoles value="${ywCoupon.coupon_collection_condition!''}" optionData={"value":"id","option":"levelname"} />
			        </div>
			</div>
			<div class="form-group">
			        <label for="level" class="label"><em style="color:red;">*</em>每人限领</label>
			        <@radio valid="NotBlank" placeholder="请选择限制领取次数类型" name="coupon_limit_count_type" dic_key="1087" value="${ywCoupon.coupon_limit_count_type!'1'}" />
			        
			        <div style="margin-left: 183; <#if ywCoupon.coupon_limit_count_type == "0">display:none</#if>">
			            <input id="coupon_limit_day" style="width:80" name="coupon_limit_day" type="text" valid="NotBlank,Digital" class="dfinput" placeholder="天数（数字）" maxbytelength="10" value="${ywCoupon.coupon_limit_day!''}"/>
			            <span>天内可领取</span>
						<input id="coupon_limit_count" style="width:80" name="coupon_limit_count" type="text" valid="NotBlank,Digital" class="dfinput" placeholder="张数（数字）" maxbytelength="10" value="${ywCoupon.coupon_limit_count!'1'}"/>
			            <i>张</i>
			        </div>
			        
			</div>
			<div class="form-group">
			        <label for="level" class="label"><em style="color:red;">*</em>有效期</label>
			        <span style="display: inline-flex;flex-direction: column;justify-content: center;align-items: start;">
			           <span>
			              <input name="coupon_time_type" class="coupon_time_type" <#if (ywCoupon.coupon_time_type)?default("0") == "0">checked</#if> valid="NotBlank" type="radio" value="0">
			                                          固定日期
			           </span>
			           <span <#if (ywCoupon.coupon_time_type)?default("0") != "0">style="display:none"</#if>>
				         <label style="width:70">生效时间</label>
				         <@dateTimePicker valid="NotBlank" placeholder="请选择有效开始时间" name="coupon_starttime" timeValue=ywCoupon.coupon_starttime />
				       </span>
				       <span <#if (ywCoupon.coupon_time_type)?default("0") != "0">style="display:none"</#if>> 
				         <label style="width:70">过期时间</label>
						 <@dateTimePicker placeholder="请选择失效结束时间" name="coupon_endtime" timeValue=ywCoupon.coupon_endtime />
					   </span>
					   <span>
					      <input name="coupon_time_type" class="coupon_time_type" <#if ywCoupon.coupon_time_type == "1">checked</#if> valid="NotBlank" type="radio" value="1">
			                                          领到券当日开始
			              <input style="width:50" <#if ywCoupon.coupon_time_type == "1">name="coupon_time_day" valid="NotBlank,Digital"</#if> type="text" <#if ywCoupon.coupon_time_type != "1">readonly="readonly" </#if>  class="dfinput coupon_time_day" placeholder="天数（数字）" maxbytelength="10" value="<#if ywCoupon.coupon_time_type == "1">${ywCoupon.coupon_time_day!''}</#if>"/>                      
					                  天内有效
					   </span>
					   <span>
					      <input name="coupon_time_type" class="coupon_time_type" <#if ywCoupon.coupon_time_type == "2">checked</#if> valid="NotBlank" type="radio" value="2">
			                                          领到券次日开始
			              <input style="width:50" <#if ywCoupon.coupon_time_type == "2">name="coupon_time_day" valid="NotBlank,Digital"</#if> type="text" <#if ywCoupon.coupon_time_type != "2">readonly="readonly"</#if> class="dfinput coupon_time_day" placeholder="天数（数字）" maxbytelength="10" value="<#if ywCoupon.coupon_time_type == "2">${ywCoupon.coupon_time_day!''}</#if>"/>                      
					                  天内有效
					   </span>
					</span>
			</div>
			<#--
				<div class="form-group">
				        <label for="level" class="label"><em style="color:red;">*</em>被领取的张数</label>
						<input id="coupon_received_count" style="width:150" name="coupon_received_count" type="text" valid="NotBlank" class="dfinput" placeholder="被领取的张数" maxbytelength="10" value="${ywCoupon.coupon_received_count!''}"/>
				        <i>张</i>
				</div>
			-->
			<div class="form-group">
					<label for="level" class="label"><em style="color:red;">*</em>使用说明</label>
					<textarea name="coupon_introduce" class="textareainput" placeholder="请输入优惠券使用说明" id="coupon_introduce" cols="100" rows="5">${ywCoupon.coupon_introduce!''}</textarea> 
			      
			        <label for="level" class="label"><em style="color:red;">*</em>状态</label>
			        <input id="coupon_status" name="coupon_status" valid="NotBlank" type="radio" <#if ywCoupon.coupon_status == "0">checked</#if> placeholder="请选择发布状态" value="0">不发布
			        <input id="coupon_status" name="coupon_status" valid="NotBlank" type="radio" <#if ywCoupon.coupon_status == "1">checked</#if> placeholder="请选择发布状态" value="1">发布
			</div>
	<div class="button-content">
	    <input type="button" class="btn btn-success btn_text" value="确认" id="btn_ywCoupon_submit"/>
		<input type="button" class="btn btn-success btn_text" value="取消" id="btn_ywCoupon_back"/>
	</div>
</form>	
<@script>
$(function(){
  var devDialog;
  $("#ywCouponForm").on("click","#btn_ywCoupon_submit",function(){
      // 表单确认提交
       // 表单校验
      if (!formValidate("ywCouponForm")) {
		 return false;
	  }
      var formData = $("#ywCouponForm").serializeArray();
      $.post($("#ywCouponForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
               devActAfterShowDialog(data.error_info,function(){
                 location.href = "${base}/sales/ywCoupon.htm";
                },"suc");
            } else if(data.error_no == "-1"){
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }else{
                isTimeOutdevShowDialog(data.error_info,data.infos);
            }
        })
  }).on("click","#btn_ywCoupon_back",function(){
     // 取消返回
     location.href = "${base}/sales/ywCoupon.htm";
  }).on("click",".coupon_function_type",function(){
     // 作用类型添加点击事件
     var self = $(this);
     var coupon_function_type = self.val();
     if(coupon_function_type == "1"){
        // 满减券
        $("#coupon_price").val("").parent().show();
        $("#coupon_price").attr("placeholder","请输入满减金额（元）").attr("valid","NotBlank,float");
        $("#coupon_price").next().html("￥");
     } else if(coupon_function_type == "2"){
        // 折扣劵
        $("#coupon_price").val("").parent().show();
        $("#coupon_price").attr("placeholder","请输入折扣（数字）").attr("valid","NotBlank,float");
        $("#coupon_price").next().html("折");
     } else if(coupon_function_type == "3"){
        // 抵扣劵
        $("#coupon_price").val("").parent().show();
        $("#coupon_price").attr("placeholder","请输入抵扣金额（元）").attr("valid","NotBlank,float");
        $("#coupon_price").next().html("￥");
     } else if(coupon_function_type == "4"){
        // 免邮券
        $("#coupon_price").val("").parent().hide();
       // $("#coupon_price").attr("placeholder","请输入免邮金额（元）").attr("valid","NotBlank,float");
       // $("#coupon_price").next().html("￥");
       
     }
  }).on("click",".coupon_use_condition_type",function(){
     // 使用条件类型添加点击事件
     var self = $(this);
     var coupon_use_condition_type = self.val();
     if(coupon_use_condition_type == "0"){
        // 不限制
        $("#coupon_use_condition").val("").parent().hide();
     } else if(coupon_use_condition_type == "1"){
        // 限制
        $("#coupon_use_condition").parent().show();
     } 
  }).on("click",".coupon_use_scope_type",function(){
     // 使用范围添加点击事件
     var self = $(this);
     var coupon_use_scope_type = self.val();
     if(coupon_use_scope_type == "0"){
        // 全场
        $("#coupon_use_scope").parent().hide();
     } else if(coupon_use_scope_type == "1"){
     	// 数据加载中提示
        var devDialog = new Dialog(null, {
			title: '',
			width: '300',
			height: '150',
			needDestroy: false,
			hasBtn: false,
			btnText: ["确定"],
			btnRole: [''],
			content: '<div class="app-row-center-layout"><image style="width:15px;height:15px;margin-right:10px;" src="${base}/img/loading01.gif" />数据加载中...</div>'
	    });
	    $(".js-dialog-close").hide();
	    
        // 查询商品分类
        var request_param = {};
        $.ajaxPost("${base}/product/ywProductClassify/getClassifyLevel.json",request_param,function(data){
            
		   // 多品类
           $("#coupon_use_scope").parent().show();
           
           var classifyList = data.classifyList;
           var classifyArr = [];
           classifyArr.push("<option value=' '>请选择商品分类</option>");
           $.each(classifyList,function(index,item){
               classifyArr.push("<option value='"+ item.classify_id +"'>"+ item.classify_name +"</option>");
           })
           
           if(classifyArr.length > 0){
           	  devDialog.hide();
           	  $("#coupon_use_scope").html(classifyArr.join(""));
           }
        })
     } else if(coupon_use_scope_type == "2"){
        // 数据加载中提示
        var devDialog = new Dialog(null, {
			title: '',
			width: '300',
			height: '150',
			needDestroy: false,
			hasBtn: false,
			btnText: ["确定"],
			btnRole: [''],
			content: '<div class="app-row-center-layout"><image style="width:15px;height:15px;margin-right:10px;" src="${base}/img/loading01.gif" />数据加载中...</div>'
	    });
	    $(".js-dialog-close").hide();
     
        // 多商品
        $("#coupon_use_scope").parent().show();
        
        // 查询商品
        var request_param = {};
        $.ajaxPost("${base}/product/ywProduct/getLists.json",request_param,function(data){
           var resultList = data.resultList;
           var productArr = [];
           productArr.push("<option value=' '>请选择商品</option>");
           $.each(resultList,function(index,item){
               productArr.push("<option value='"+ item.id +"'>"+ item.title +"</option>");
           })
           if(productArr.length > 0){
               devDialog.hide();
               $("#coupon_use_scope").html(productArr.join(""));
           }
        })
     }
  }).on("click",".coupon_collection_condition_type",function(){
     // 领取条件添加点击事件
     var self = $(this);
     var coupon_collection_condition_type = self.val();
     if(coupon_collection_condition_type == "0"){
        // 游客
        $("#coupon_collection_condition").parent().hide();
     } else if(coupon_collection_condition_type == "1"){
        // 会员
        $("#coupon_collection_condition").parent().show();
        
        // 查询会员等级
        var request_param = {};
        $.ajaxPost("${base}/member/ywMemberRole/getLists.json",request_param,function(data){
           var memberRoleList = data.resultList;
           var memberRoleArr = [];
           memberRoleArr.push("<option value=' '>请选择会员等级</option>");
           $.each(memberRoleList,function(index,item){
               memberRoleArr.push("<option value='"+ item.id +"'>"+ item.levelname +"</option>");
           })
           $("#coupon_collection_condition").html(memberRoleArr.join(""));
        })
     } 
  }).on("click",".coupon_limit_count_type",function(){
     // 每人限领添加点击事件
     var self = $(this);
     var coupon_limit_count_type = self.val();
     if(coupon_limit_count_type == "0"){
        // 不限制
        $("#coupon_limit_count").val("").parent().hide();
        $("#coupon_limit_day").val("");
        $("#coupon_limit_count").val("");
     } else if(coupon_limit_count_type == "1"){
        // 限制
        $("#coupon_limit_count").parent().show();
        $("#coupon_limit_count").val("1");
     } 
  }).on("change","#coupon_starttime",function(){
     // 生效时间添加点击事件
     var self = $(this);
     var coupon_starttime = self.val();
     var coupon_endtime = $("#coupon_endtime").val();
     if(new Date(coupon_starttime).getTime() > new Date(coupon_endtime).getTime()){
        self.val("");
        devShowDialog("生效时间不能大于过期时间");
     }
  }).on("change","#coupon_endtime",function(){
     // 过期时间添加点击事件
     var self = $(this);
     var coupon_starttime = $("#coupon_starttime").val();
     var coupon_endtime = self.val();
     if(new Date(coupon_starttime).getTime() > new Date(coupon_endtime).getTime()){
        self.val("");
        devShowDialog("过期时间不能小于生效时间");
     }
  }).on("click",".coupon_time_type",function(){
     // 过期时间添加点击事件
     var self = $(this);
     var coupon_time_type = self.val();
     if(coupon_time_type == "0"){
       // 固定时间
       $(".coupon_time_day").attr("readonly","readonly").val("").removeAttr("valid").removeAttr("name");
       $("#coupon_starttime").parent().show();
       $("#coupon_endtime").parent().show();
     } else if(coupon_time_type == "1"){
       // 单日有效
       $("#coupon_starttime").attr("readonly","readonly").val(""); 
       $("#coupon_endtime").attr("readonly","readonly").val("");
       $(".coupon_time_day").attr("readonly","readonly").val("").removeAttr("valid").removeAttr("name");
       self.next().attr("valid","NotBlank,Digital").removeAttr("readonly").attr("name","coupon_time_day");
       $("#coupon_starttime").parent().hide();
       $("#coupon_endtime").parent().hide();
     } else if(coupon_time_type == "2"){
       // 次日有效
       $("#coupon_starttime").attr("readonly","readonly").val(""); 
       $("#coupon_endtime").attr("readonly","readonly").val("");
       $(".coupon_time_day").attr("readonly","readonly").val("").removeAttr("valid").removeAttr("name");
       self.next().attr("valid","NotBlank,Digital").removeAttr("readonly").attr("name","coupon_time_day");
       $("#coupon_starttime").parent().hide();
       $("#coupon_endtime").parent().hide();
     }
  })
 
  
})
</@script>
</@screen>