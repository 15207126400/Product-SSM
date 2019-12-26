<#assign base=request.contextPath />
<#assign ywRoleCache = SpringContext.getBean("ywRoleCache") />
<#assign ywDictionary = SpringContext.getBean("ywDictionaryCache")/>
<@screen id="ywProduct" title="商品编辑页面" places=[{"name":"商品信息","href":"${base}/product/ywProduct.htm"},{"name":"添加/修改","href":"#"}]>
	<form id="ywProductForm" action="insertOrUpdate.json" method="post" enctype="multipart/form-data" class="form">
	   <input type="hidden" id="op_type" name="op_type" value="${op_type!''}"/>
	   <input id="id" name="id" type="hidden" class="dfinput" <#if op_type == "2">readonly="readonly"</#if> maxlength="18" value="${ywProduct.id!''}"/>
	   <input id="product_carriage" name="product_carriage" type="hidden" value="${ywProduct.product_carriage!'0'}" />
	   <div class="app-row-start-layout">
			<div id="formspan1" class="formtitle tab-switch app-row-center-layout active">
				<span class="tab-span">商品信息</span>
			</div>
			<div id="formspan2" class="formtitle tab-switch app-row-center-layout">
				<span class="tab-span">商品详情</span>
			</div>
			<div id="formspan3" class="formtitle tab-switch app-row-center-layout">
				<span class="tab-span">商品轮播图</span>
			</div>
		</div>
		<div id="formTable1">
		    
		<div class="form-group">
			<label for="level" class="label"><em style="color:red;">*</em>商品名称</label>
			<input id="title" name="title" valid="NotBlank" placeholder="请输入商品名称" type="text" class="dfinput" value="${ywProduct.title!''}" maxlength="30"/>
		
		    <label for="level" class="label">特色描述</label>
			<input id="keyword" name="keyword" placeholder="请输入特色描述" type="text" class="dfinput" value="${ywProduct.keyword!''}" maxlength="15" />
		</div>
		<div class="form-group product_common_attr">
			<@fileSyncUpload isMust=true title="商品大图" imageUrl = ywProduct.product_url/>
		</div>
		<div class="form-group">
			<label for="level" class="label">商品标志</label>
			<@select placeholder="请选择商品标志" name="market_code" dic_key="1040" value=ywProduct.market_code!''/>
		
			<label for="level" class="label">排序</label>
			<input valid="Digital" id="product_sort" name="product_sort" placeholder="请输入排序(整数【1，2，3，4...】)" type="text" class="dfinput" value="${ywProduct.product_sort!'0'}" maxlength="17" />
		</div>
		<div class="form-group product_common_attr">
			<label for="level" class="label">虚拟价格</label>
			<input id="product_virtualprice" valid="NotBlank,float" placeholder="请输入虚拟价格(单位：元)" name="product_virtualprice" type="text" class="dfinput" value="${ywProduct.product_virtualprice!'0'}" maxlength="8"/>
		
			<label for="level" class="label">价格</label>
			<input id="price" name="price" valid="NotBlank,float" placeholder="请输入价格(单位：元)" type="text" class="dfinput" value="${ywProduct.price!'0'}" maxlength="18"/>
		</div>
		<div class="form-group product_common_attr">
			<label for="level" class="label">虚拟销量</label>
			<input id="product_virtualsales" name="product_virtualsales" valid="NotBlank,Digital" placeholder="请输入虚拟销量(单位:件)" type="text" class="dfinput" value="${ywProduct.product_virtualsales!'0'}" maxlength="8"/>
		
			<label for="level" class="label">库存</label>
			<input id="stock" name="stock" valid="NotBlank,Digital" placeholder="请输入库存(单位:件)" type="text" class="dfinput" value="${ywProduct.stock!'0'}" maxlength="18"/>
		</div>
		<div class="form-group product_common_attr">
			<label for="level" class="label">起拍量</label>
			<input id="starting_amount" name="starting_amount" valid="NotBlank,Digital" placeholder="请输入起拍量(单位:件)" type="text" class="dfinput" value="${ywProduct.starting_amount!'0'}" maxlength="4"/>
		
			<label for="level" class="label">商品状态</label>
			<@select placeholder="请选择商品状态" name="status" dic_key="1073" value=ywProduct.status!'1'/>
		</div>
		<div class="form-group" style="display:flex;align-items:center;">
			<label for="level" class="label">商品规格</label>
			<div class="field-group" style="width:260;margin-right: 10;">
				<input type="button" id="addProductSpc" class="btn btn-success" value="添加规格" data-toggle="modal" data-target="#goodsSpecificationModal">
		    </div>
		</div>
		<#-- 商品规格 -->
		<div class="field-group" style="margin-left: 190;">
			<div class="specification-detail-container">
			    <#-- 规格名称和值显示 -->
				<ul class="list-unstyled specification-selected-list">
				    <#assign productAttrs = ywProduct.ywProductAttributenames>
					<#if productAttrs?? && productAttrs?size gt 0>
					   <#list productAttrs as productAttr>
						<li data-model="31">
							<span class="specification-case">
								<span class="specification-name">${productAttr.attrname_name!''} </span>
								<span class="glyphicon glyphicon-remove specification-remove"></span>
							</span>
							<div class="sub-specification-list">
							   <#assign attrValues = productAttr.ywProductAttributevalues>
							   <#if attrValues?? && attrValues?size gt 0>
							    <#list attrValues as attrValue>
								<div class="specification-case" data-id="33">
									<#--<input class="form-control" type="text">-->
									<span class="sub-specification-value">${attrValue.attrvalue_name!''}</span>
									<span class="glyphicon glyphicon-ok sub-specification-confirm" style="display:none"></span>
									<span class="glyphicon glyphicon-pencil sub-specification-edit" style="display:block"></span>
									<span class="glyphicon glyphicon-remove sub-specification-remove" style="margin-left:65px"></span>
								</div>
								</#list>
								</#if>
								<input type = "button" class="btn btn-default btn-sm add-sub-specification" value="添加类型">
							</div>
						</li>
						</#list>
					</#if>
				</ul>
				<#-- 规格表格 -->
				<div class="specification-detail-table">
				   <#assign productSkus = ywProduct.ywProductSkus>
				   <#if productAttrs?? && productAttrs?size gt 0 && productSkus?? && productSkus?size gt 0>
				   <div class="specification-detail-thead">
				       <#list productAttrs as productAttr>
				           <span>${productAttr.attrname_name!''}</span>
				       </#list>
				        <span>图片</span>
					    <span>价格: <input class="spe-all-price form-control input-sm" type="text" placeholder="统一价格"></span>
					    <span>库存: <input class="spe-all-stock form-control input-sm" type="text" placeholder="统一库存"></span>
					    <span style="min-width: 160px;">虚拟价格: <input class="spe-all-virtualPrice form-control input-sm" type="text" placeholder="统一虚拟价格"></span>
				   </div>
				   <div class="specification-detail-tbody">
				      <#list productSkus as productSku>
				            <#assign skuAttrs = productSku.sku_attr?split(";")>
				         	<div data-models="
				         	    <#assign attrString = "">
				         	    <#list skuAttrs as skuAttr>
					         	    <#assign sku_attr = skuAttr?split("-")>
					         	    <#assign attrValue = sku_attr[1]>
               						<#if skuAttr_index == (skuAttrs?size - 1)>
               						   <#assign attrString = attrString + attrValue>
               						<#else>
               						   <#assign attrString = attrString + attrValue + "-">   
               						</#if>
           					    </#list> 
           					    ${attrString}
				         	">
				         	<#list skuAttrs as skuAttr>
				         	    <#assign sku_attr = skuAttr?split("-")>
				         	    <#assign attrValue = sku_attr[1]>
           						<span>${attrValue!''}</span>
           					</#list>
           					<#--  图片暂时不上传-->
				            <span><b class="specification-img-wrap thumbnail"><img class="specification-img" src="${productSku.sku_image!''}" image_id="${productSku.image_id!''}" alt=""></b>
						    <a class="specification-img-change field-upload-img">更换</a>
						    </span>
						    <span class="yw-specification-span"><input class="specification-price form-control" type="text" value="${productSku.sku_price!''}"></span>
						    <span class="yw-specification-span"><input class="specification-stock form-control" type="text" value="${productSku.sku_stock!''}"></span>
						    <span class="yw-specification-span"><input class="specification-virtualPrice form-control" type="text" value="${productSku.sku_virtualprice!''}"></span>
				            </div>
				      </#list>
				   </div>
				   </#if>
				</div>
			</div>
		 </div>	
		</div>
		<div id="formTable2" style="display:none">
			<div class="form-group">
				<@editor name="content" title="商品详情" content=ywProduct.content />
			</div>
		</div>
		<div id="formTable3" style="display:none">
		   <div class="form-group">
				<label for="level" class="label">商品轮播图</label>
				<@multipartFileUpload formId="ywProductForm" previewImageList=imgList uploadUrl="${base}/product/ywProduct/uploadProductCarouselImage.json" deleteUrl="${base}/product/ywProduct/deleteProductCarouselImage.json"/>
		   </div>
		</div>
		<div class="button-content">
		    <input type="button" class="btn btn-success btn_text" value="确认" id="formid"/>
			<input type="button" class="btn btn-success btn_text" value="取消" id="btn_ywProduct_back"/>
		</div>
	</form>
	<div class="modal fade" id="goodsSpecificationModal" tabindex="-1" role="dialog" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title">商品规格项目</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
	      </div>
	      <div class="modal-body">
	        <div class="goods-modal-content">
	          <h5>我的规格</h5>
	          <div>
	            <!-- <span class="btn btn-sm btn-default goods-specification-input"><input class="form-control" type="text"><span class="glyphicon glyphicon-ok"></span></span> 
	            <span class="btn btn-sm btn-default goods-specification active" data-index="30">123</span>
	            <span class="btn btn-sm btn-default goods-specification active" data-index="31">123</span>
	            <span class="btn btn-sm btn-default goods-specification-input">
	               <input class="form-control" type="text"><span class="glyphicon glyphicon-ok"></span>
	            </span>-->
	            <button class="btn btn-sm btn-default add-custom-specification">
	            	<span class="glyphicon glyphicon-plus"></span>添加标签
	            </button>
	          </div>
	          <hr>
	          <h5>常规规格</h5>
	          <div>
	          <#assign dicList = ywDictionary.getDictionaryList("1029")>
	          <#if dicList?? && dicList?size gt 0 >
	            <#list dicList as dic>
	            <span class="btn btn-sm btn-default goods-specification" data-index="${dic.dic_subkey!''}">${dic.dic_subvalue!''}</span>
	            <#if dic_index == (dicList?size -1)>
	                <input type="hidden" id="yw-specification-id" value="${dic.dic_subkey!''}">
	            </#if>
	            </#list>
	          </#if>  
	          </div>
	        </div>
	      </div>
	      <div class="modal-footer" style="text-align:center;">
	        <button type="button" class="btn btn-success goods-specification-confirm">确定</button>
	      </div>
	    </div>
	  </div>
	</div>
	<#--<@dialog id="dialogForm" title ="新增分类" action="${base}/product/ywProductClassify/insertOrUpdate.json?op_type=1" 
	         formInputArray=[{"id":"classify_name","name":"名称","required":"true"}] backUrl="${base}/product/ywProductClassify/getList.json"/>-->

<@script>

function pMessage() {
	var price = $("#price").val();
	var reg = new RegExp(/^[0-9]*$/);
	if(price==""){
		$("#p").text("请输入价格");
		$("#p").css("color","#7F7F7F");
	}else{
		$("#p").text("输入正确");
		$("#p").css("color","green");
	}
}

$(function(){
   $("#addClassify").click(function(){
        location.href="${base}/product/ywProductClassify.htm"
   });
   
   $(".tab-switch").click(function(){
	    $(this).addClass("active").siblings().removeClass("active");
	    if($(this).attr("id") == "formspan1"){
	       $("#formTable1").show();
		   $("#formTable2").hide();
		   $("#formTable3").hide();
	    } else if($(this).attr("id") == "formspan2") {
	       $("#formTable2").show();
		   $("#formTable1").hide();
		   $("#formTable3").hide();
	    } else if($(this).attr("id") == "formspan3"){
	      $("#formTable3").show();
		  $("#formTable1").hide();
		  $("#formTable2").hide(); 
	    }
	})
   
   // 选择一级分类
   $("#shopType").change(function(){
       var parent_id = $(this).val();
       
       $.ajax({
          url:"getClassify.json",
          type:"post",
          data:{"parent_id":parent_id},
          dataType:"json",
          success:function(data){
             if (data.error_no == 0) {
                if(data.code == "001"){
                	var classifyList = data.classifyList;
                	var classifyListArray = [];
                	
				    $.each(classifyList,function(index,item){
				       classifyListArray.push("<option value='"+ item.classify_id +"'>"+ item.classify_name +"</option>");
				    })
				    $("#shopType2").show();
					$("#shopType2").html(classifyListArray.join(" "));
                }else{
                	$("#shopType2").val("");
                	$("#shopType2").hide();
                }
	          }else if(data.error_no == "-1"){
	                isTimeOutdevShowDialog(data.error_info,data.infos);
	          }else{
	                isTimeOutdevShowDialog(data.error_info,data.infos);
	          }
          },
          error:function(){
             
          }      
        });
   })
   
   
   
   // 取消
  $("#btn_ywProduct_back").click(function(){
      location.href = "${base}/product/ywProduct.htm";
  })
  
  // 提交
  $("#formid").click(function(){
      
      // 表单校验
      if (!formValidate("ywProductForm")) {
		 return false;
	  }
	   
      // 获取轮播图片
      addBatchImage();
      // 获取规格数量
      printSpecificationTable.TbodyDate();
      var formData = new FormData($("#ywProductForm")[0]);
      console.info(formData);
      //var formData = $("#ywProductForm").serializeArray();
      $.ajax({  
          url: $("#ywProductForm").attr("action") ,  
          type: 'POST',  
          data: formData,  
          async: false,  
          cache: false,  
          contentType: false,  
          processData: false,  
          success: function (data) {  
           if (data.error_no == 0) {
                devActAfterShowDialog(data.error_info,function(){
                  location.href = "${base}/product/ywProduct.htm";
                },"suc");
            } else {
                isTimeOutdevShowDialog(data.error_info,data.infos);
            };
          },  
          error: function (data) {
              console.log(data);  
              devShowDialog("系统异常");
          }  
     });  
  })

<#-- ************ 弹框上规格操作开始 ************* -->  
 // 规格自增id  
  var index = parseInt($("#yw-specification-id").val());
  $(".goods-modal-content ").on("click",".add-custom-specification",function(){
    //添加规格元素
      $(this).before("<span class='btn btn-sm btn-default goods-specification-input'><input class='form-control' type='text'><span class='glyphicon glyphicon-ok'></span></span>");
      // 保存新添加的规格元素
      $(this).prev().find("span").click(function(){
         if($(this).prev().val().trim().length == 0){
            alert("规格不能为空！");
         } else {
	         $(this).parent().before("<span class='btn btn-sm btn-default goods-specification' data-index='"+ ++index +"'>"+ $(this).prev().val() +"</span>");
	         // 移除添加元素节点
	         $(this).parent().remove();
         }
      })
  }).on("click",".goods-specification",function(){
   // 选择规格
      if($(this).hasClass("active")){
         $(this).removeClass("active");
      } else {
         if($(".goods-modal-content .goods-specification.active").length == 5){
            messageAlert("规格最多设置5项");
         } else {
            $(this).addClass("active");
         }
      }
  })
  
  // 点击确定按钮规格显示在页面上
  $(".goods-specification-confirm").click(function(){
     var html = [];
     // 判断页面上是否有已经存在规格元素
     var indexArr = []
     if($(".specification-selected-list li").length > 0){
       $(".specification-selected-list li").each(function(){
          indexArr.push($(this).attr("data-model"));
       })
     }
     $(".goods-modal-content .goods-specification").each(function(){
       if($(this).hasClass("active") && indexArr.indexOf($(this).attr("data-index")) == -1){
           html.push('<li data-model="'+ $(this).attr("data-index") +'">')
		   html.push('<span class="specification-case">')
		   html.push('<span class="specification-name">'+ $(this).text() +'</span>')
		   html.push('<span class="glyphicon glyphicon-remove specification-remove"></span></span>')
		   html.push('<div class="sub-specification-list">')
		   //html.push('<div class="specification-case" data-id="32">')
		   //html.push('<input class="form-control" type="text">')
		   //html.push('<label>xl</label>')
		   //html.push('<span class="glyphicon glyphicon-ok sub-specification-confirm"></span>')
		   //html.push('<span class="glyphicon glyphicon-pencil sub-specification-edit"></span>')
		   //html.push('<span class="glyphicon glyphicon-remove sub-specification-remove"></span>')
		   //html.push('</div>')
		   html.push('<input type="button" class="btn btn-default btn-sm add-sub-specification" value="添加类型">')
		   html.push('</div>')
		   html.push('</li>')
        }
     })
     $(".specification-selected-list").append(html.join(" "))
     
     $('#goodsSpecificationModal').modal('hide');
  })
  
<#-- ************ 弹框上规格操作完成 ************* -->    
  
<#-- ************ 页面上规格操作开始 ************* -->  

  // 在规格名称后面增加规格值
  $(".specification-selected-list").on("click",".add-sub-specification",function(){
      var html = [];
       html.push('<div class="specification-case" data-id="32">')
	   html.push('<input class="form-control" type="text">')
	   //html.push('<label>xl</label>')
	   html.push('<span class="glyphicon glyphicon-ok sub-specification-confirm"></span>')
	   html.push('<span class="glyphicon glyphicon-pencil sub-specification-edit"></span>')
	   html.push('<span class="glyphicon glyphicon-remove sub-specification-remove" style="margin-left: 0px;"></span>')
	   html.push('</div>')
      $(this).before(html.join(" "));
  })
 
  $(".specification-selected-list").on("click",".specification-remove",function(){
       // 删除页面上的规格名称
      $(this).parent().parent().remove();
      // 刷新表格
      printSpecificationTable.printTableThead();
  }).on("click",".sub-specification-remove",function(){
      // 删除规格值
      $(this).parent().remove();
      // 刷新表格
      printSpecificationTable.printTableThead();
  }).on("click",".sub-specification-edit",function(){
      // 编辑规格值  
       var valueSpc = $(this).prev().prev().text();
       $(this).prev().prev().remove();
       $(this).prev().before('<input class="form-control" type="text" value="'+ valueSpc +'">');
       $(this).prev().show();
       $(this).hide();
       $(this).next().css({"margin-left":"0px"});
  }).on("click",".sub-specification-confirm",function(){
      // 提交规格值
      var valueSpc = $(this).prev().val().trim();
      if(valueSpc.length == 0){
         messageAlert("类型不能为空");
         return false;
      }
      $(this).prev().remove();
      $(this).before('<span class="sub-specification-value">'+ valueSpc +'</span>');
      // 隐藏提交按钮，显示编辑按钮
      $(this).hide();
      $(this).next().show();
      $(this).next().next().css({"margin-left":"65px"});
      // 刷新表格
      printSpecificationTable.printTableThead();
      
  })
  
})

// 规格表格对象
var printSpecificationTable = {
    tableTbodyArr:[],
    // 表头渲染
    printTableThead:function(){
      var html = [];
      // 重置表体内容素组，防止重复
      printSpecificationTable.tableTbodyArr = [];
      if($(".specification-selected-list li").length > 0){
         $(".specification-selected-list li").each(function(){
	        if($(this).find(".sub-specification-value").length > 0){
	            // 获取表体内容
	            var temArr = [];
	            $(this).find(".sub-specification-value").each(function(){
		            temArr.push($(this).text());
		        });
		        debugger;
	           printSpecificationTable.tableTbodyArr.push(temArr);
	           // 获取表头
	           html.push("<span>"+ $(this).find(".specification-name").text() +"</span>");
	        }
	      })
      }
      // 将表单清空
      $(".specification-detail-table").html("");
      
      // 显示通用属性
     // $(".product_common_attr").show();
      if(printSpecificationTable.tableShowFlag()){
      // 隐藏通用属性
       //$(".product_common_attr").hide();
        $(".specification-detail-table").html('<div class="specification-detail-thead"></div><div class="specification-detail-tbody"></div>');
        html.push('<span>图片</span>');
	    html.push('<span>价格: <input class="spe-all-price form-control input-sm" type="text" placeholder="统一价格"></span>');
	    html.push('<span>库存: <input class="spe-all-stock form-control input-sm" type="text" placeholder="统一库存"></span>');
	    html.push('<span style="min-width: 160px;">虚拟价格: <input class="spe-all-virtualPrice form-control input-sm" type="text" placeholder="统一虚拟价格"></span>');
        html.push('</div>');
        $(".specification-detail-table .specification-detail-thead").html(html.join(" "));
        printSpecificationTable.printTableTbody();
      }
    },
    
    // 判断表头数据是否存在
    tableShowFlag:function(){
      var showFlag = false;
      $(".specification-selected-list li").each(function(){
        if($(this).find(".sub-specification-value").length > 0){
            showFlag = true;
        }
      })
      return showFlag;
    },
    
    // 表体渲染
    printTableTbody:function(){
        // 得到排列组合的各种情况
       var res =  printSpecificationTable.combine(printSpecificationTable.tableTbodyArr.reverse());
       debugger;
       var tbodyArr = printSpecificationTable.printCombine(res,printSpecificationTable.tableTbodyArr);
       $(".specification-detail-table .specification-detail-tbody").html(tbodyArr.join(" "));
    },
    
    // 得到表体组合情况
    combine:function (arr) {  
	    var r = [];  
	    (function f(t, a, n) {  
	        if (n == 0) return r.push(t);  
	        for (var i = 0; i < a[n-1].length; i++) {  
	            f(t.concat(a[n-1][i]), a, n - 1);  
	        }  
	    })([], arr, arr.length);  
	    return r;  
	},
	
	// 将组合情况打印成表体内容
	printCombine:function(res,arr){
	    //合并单元格  
        var row = [];  
        var rowspan = res.length;  
        for(var n=arr.length-1; n>-1; n--) {  
            row[n] = parseInt(rowspan/arr[n].length);  
            rowspan = row[n];  
        }  
        row.reverse();  
          
        //table tr td  
        //var str = "";
        var tmpArr = [];  
        var len = res[0].length;
        for (var i=0; i<res.length; i++) {  
            //var tmp = "";
            tmpArr.push('<div data-models="'+ res[i].join("-") +'">');
            for(var j=0; j<len; j++) {  
               // if(i%row[j]==0 && row[j]>1) {  
                 //   tmpArr.push('<td rowspan="'+ row[j] +'">'+res[i][j]+'</td>');  
                //}else if(row[j]==1){  
                    tmpArr.push('<span>'+res[i][j]+'</span>');  
                //}  
            }  
        
            tmpArr.push('<span><b class="specification-img-wrap thumbnail"><img class="specification-img" src="#" alt="" image_id=""></b>');
		    tmpArr.push('<a class="specification-img-change field-upload-img">更换</a>');
		    tmpArr.push('</span>');
		    tmpArr.push('<span class="yw-specification-span"><input class="specification-price form-control" type="text" value=""></span>');
		    tmpArr.push('<span class="yw-specification-span"><input class="specification-stock form-control" type="text" value=""></span>');
		    tmpArr.push('<span class="yw-specification-span"><input class="specification-virtualPrice form-control" type="text" value=""></span>');
            tmpArr.push('</div>');  
        }
		return tmpArr;
        
	},
	// 获取动态属性名称
	getSpecificationAttrNames:function(){
	   var tableTheadDynamicArr = [];
	   if($(".specification-selected-list li").length > 0){
         $(".specification-selected-list li").each(function(){
	        // 保存动态表头到数组中
	        tableTheadDynamicArr.push($(this).find(".specification-name").text().trim());
	      })
       }
       debugger;
       return tableTheadDynamicArr;
	},
	// 获取动态属性值
	getSpecificationAttrValues:function(){
	   var tableTbodyArr = [];
	   if($(".specification-selected-list li").length > 0){
         $(".specification-selected-list li").each(function(){
	        if($(this).find(".sub-specification-value").length > 0){
	            // 获取表体内容
	            var temArr = [];
	            $(this).find(".sub-specification-value").each(function(){
		            temArr.push($(this).text().trim());
		        });
	           tableTbodyArr.push(temArr.join("-"));
	        }
	      })
        }
        debugger;
        return tableTbodyArr;
	},
	// 获取表体数据
	TbodyDate:function(){
	   $(".sku_values_form").remove();
	   $(".attr_name_form").remove();
	   $(".attr_value_form").remove();
	   if ($('.specification-detail-tbody').length) {
            $.each($('.specification-detail-tbody > div'), function() {
                var sku_dates_string = "";
                specData = $(this).attr('data-models').trim();
                subImg = $(this).find('.specification-img').attr("image_id").trim();
                subPrice = $(this).find('.specification-price').val().trim();
                subStock = $(this).find('.specification-stock').val().trim();
                subVirtualPrice = $(this).find('.specification-virtualPrice').val().trim();
                
                debugger;
	                sku_dates_string += specData;
	                sku_dates_string += ";";
	                sku_dates_string += subImg+"-";
	                sku_dates_string += subPrice+"-";
	                sku_dates_string += subVirtualPrice+"-";
	                sku_dates_string += subStock+"-";
                  $("#ywProductForm").append("<input type='hidden' class='sku_values_form' name='sku_values' value='"+ sku_dates_string +"'>");
            });
            
            // 属性值
            $.each(printSpecificationTable.getSpecificationAttrValues(),function(index,item){
               $("#ywProductForm").append("<input type='hidden' class='attr_value_form' name='attr_value' value='"+ item.toString() +"'>");
            })
        } 
        // 属性名称
        $("#ywProductForm").append("<input type='hidden' class='attr_name_form' name='attr_name' value='"+ printSpecificationTable.getSpecificationAttrNames().toString() +"'>");
	}  
}

// 动态表格操作
$(".specification-detail-table").on("keyup",".spe-all-price",function(){
    // 统一价格修改
    var $self = $(this);
    $(".specification-detail-table").find(".specification-price").each(function(){
       $(this).val($self.val());
    })
}).on("keyup",".spe-all-stock",function(){
    // 统一库存修改
    var $self = $(this);
    $(".specification-detail-table").find(".specification-stock").each(function(){
       $(this).val($self.val());
    })
}).on("keyup",".spe-all-virtualPrice",function(){
    // 统一虚拟价格修改
    var $self = $(this);
    $(".specification-detail-table").find(".specification-virtualPrice").each(function(){
       $(this).val($self.val());
    })
}).on("click",".field-upload-img",function(){
    
    // 弹框异步上传图片
    fileAsynUpload.upImage(this,function(ele,t,arg){
        $(ele).prev().find("img").attr("src", arg[0].src);
        $(ele).prev().find("img").attr("image_id", arg[0].title);
    });
})
<#-- ************ 页面上规格操作完成 ************* -->
</@script> 
</@screen>
<@fileAsynUpload name="fileAsynUpload" uploadUrl="${base}/product/ywProduct/uploadProductSkuImage.json"/>