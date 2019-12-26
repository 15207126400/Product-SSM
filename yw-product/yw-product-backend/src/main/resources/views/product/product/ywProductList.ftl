<#assign base=request.contextPath />
<@screen id="ywProduct" title="商品列表页面" places=[{"name":"商品信息","href":"#"}]>	

<@ajaxQueryTable id="ywProduct_list" auto=true isMultiple=true multipleCondition=["id"]
	action="${base}/product/ywProduct/getList.json" 
	paginatorUrl="${base}/product/ywProduct/getPaginator.json"
	updateCellUrl="${base}/product/ywProduct/updateCell.json" updateCellCondition=["id"]
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
		{"id":"id", "name":"编号"},
		{"id":"classify_name", "name":"商品分类"},
		{"id":"title", "name":"商品标题","width":"200px","edit":true},
		{"id":"product_url", "name":"商品图片","type":"img"},
		{"id":"price", "name":"价格(单位:元)","width":"200px","edit":true},
		{"id":"product_virtualprice", "name":"虚拟价格(单位:元)","width":"200px","edit":true},
		{"id":"stock", "name":"库存","width":"200px","edit":true},
		{"id":"market_code", "name":"商品标志","dic_key":"1040"},
		{"id":"product_sort", "name":"排序"},
		{"id":"starting_amount", "name":"起拍量"},
		{"id":"status", "name":"商品状态","dic_key":"1073"},
		{"id":"caozuo", "name":"操作", "provider":"dealStatus"}
	] querySub={"querySubCondition":"id","subCallback":"queryPorductSkus","updateSubCellUrl":"${base}/product/ywProduct/updateSkuCell.json","updateSubCellCondition":["sku_id"]}>
	
	<#-- 查询条件 -->
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">商品分类：</span>
    			<label class="w-170">
      			<select id="shopType" name="shopType" class="yw-input">
					<option value="">请选择商品分类</option>
					<#list ywProductClassifys as ywProductClassify>
						<option value="${ywProductClassify.classify_id}">${ywProductClassify.classify_name}</option>
          			</#list>
		    	</select>
    			</label>
  			</dd>
		</dl>
		<dl class="search-dl">
  			<dd>
  				<span class="dl-title">商品名称：</span>
    			<label class="w-170">
      			<input id="title" name="title" type="text" class="yw-input" value="" placeholder="请输入商品名称查找"/>
    			</label>
  			</dd>
  		</dl>
	</@innerOfQueryCondition>
	
	<#-- 其他 -->
	<@innerOfQueryTable>
	<div class="right-btn-box">
		<a style="margin-right:10px;height:30px;" class="btn btn-add btn-primary-white btn-small" href="javascript:void(0)" onclick="importuserfile()">导入商品数据</a>
		<a style="margin-right:10px;height:30px;" class="btn btn-add btn-primary-white btn-small" href="javascript:void(0)" onclick="exportuserfile()">文件模版下载</a>
	    <span class="input-group-btn">
			<a href="${base}/product/ywProduct/edit.htm?op_type=1">
				<button class="btn btn-success" type="button"><span class="glyphicon glyphicon-plus"></span><span>添加</span></button>
			</a>
		</span>
		<span class="input-group-btn">
			<a href="${base}/product/ywProductClassify.htm">
				<button class="btn btn-success" type="button"><span class="glyphicon glyphicon-th-large"></span><span>分类管理</span></button>
			</a>
		</span>
		<span class="input-group-btn">
			<a href="javascript:void(0);" >
				<button class="btn btn-success" type="button" onClick="operationBatchManagement()"><span class="glyphicon glyphicon-folder-open"></span><span>批量管理</span></button>
			</a>
		</span>
		<span class="input-group-btn">
			<a href="javascript:void(0);" >
				<button class="btn btn-success" type="button" onClick="operationCondition()"><span class="glyphicon glyphicon-trash"></span><span>批量删除</span></button>
			</a>
		</span>
		<span class="input-group-btn">
			<button id="btnExport" class="btn btn-success" type="button"><span class="glyphicon glyphicon-export"></span><span>导出Excel</span></button>
		</span>
	</div>
    </@innerOfQueryTable>
</@ajaxQueryTable>
<script src="${base}/js/ajaxfileupload.js" type="text/javascript"></script>
<@script>
	
	// 子内容回调函数
    function queryPorductSkus(id,$td){
        var tableHtml = [];
	        tableHtml.push("<table style='text-align:center' class='createTable table table-striped table-bordered table-hover'>");
	        tableHtml.push("<thead>");
	        tableHtml.push("<tr style='width:100%'>");
	        tableHtml.push("<th style='width:80px'></th>");
	        tableHtml.push("<th style='width:80px'>编号</th>");
	        tableHtml.push("<th style='width:150px'>规格图片</th>");
	        tableHtml.push("<th style='width:300px'>商品规格</th>");
	        tableHtml.push("<th style='width:100px'>价格<span class='glyphicon glyphicon-pencil cell-edit cell-edit-pencil-sub' title='批量列修改'></span></th>");
	        tableHtml.push("<th style='width:100px'>虚拟价格<span class='glyphicon glyphicon-pencil cell-edit cell-edit-pencil-sub' title='批量列修改'></span></th>");
	        tableHtml.push("<th style='width:100px'>库存<span class='glyphicon glyphicon-pencil cell-edit cell-edit-pencil-sub' title='批量列修改'></span></th>");
	        tableHtml.push("</tr>");
	        tableHtml.push("</thead>");
		    $.ajaxPost("${base}/product/ywProduct/querySkus.json",{product_id:id},function(data){
		         var ywProductSkus = data.resultList;
		         tableHtml.push("<tbody>");
                 $.each(ywProductSkus,function(index,item){
	                 tableHtml.push("<tr style='width:100%'>");
	                 tableHtml.push("<td style='width:80px'><span class='glyphicon glyphicon-pencil cell-edit-row cell-edit-pencil-sub' title='批量行修改'></span></td>");
	                 tableHtml.push("<td style='width:80px' name_id ='sku_id'>" + item.sku_id + "</td>");
	                 tableHtml.push("<td style='width:150px' name_id ='sku_image'>");
	                 tableHtml.push("<image style='width:50px;height:50px;' src='"+item.sku_image+"' />");
	                 tableHtml.push("</td>");
	                 tableHtml.push("<td>" + item.sku_attr + "</td>");
	                 tableHtml.push("<td style='width:200px' class='edit-dbclick edit-dbclick-sub' name_id ='sku_price' title='双击可编辑'>"+ item.sku_price +"</td>");
	                 tableHtml.push("<td style='width:200px' class='edit-dbclick edit-dbclick-sub' name_id ='sku_virtualprice' title='双击可编辑'>"+ item.sku_virtualprice +"</td>");
	                 tableHtml.push("<td style='width:200px' class='edit-dbclick edit-dbclick-sub' name_id ='sku_stock' title='双击可编辑'>"+ item.sku_stock +"</td>");
	                 tableHtml.push("</tr>");
				 })
				 tableHtml.push("</tbody>");
				 tableHtml.push("</table>");
                 $td.html(tableHtml.join(" "));
		    });
    }

	$("#btnExport").click(function(){
		
        var dialogoptions = {
			width: '450',
			height:'250',
			title: "导出商品数据",
			content: '<div><text id="product_exportExcel_rowCount">是否开始导出数据 ?</text></div>'
					+'<div><text style="margin-top:50px;color:green" class="app-column-center-layout" id="product_exportExcel_msg"></text></div>'
				   ,
			hasBtn: true,
			afterHide:function(){
				location.reload();
			},
			confirm: function (dialog) {
			    $(".js-dialog-close").hide();
				$(".btn-confirm").attr("disabled","true");
				$(".btn-deny").attr("disabled","true");
				var formData = $("#ywProduct_list").serializeArray();
				location.href = "${base}/product/ywProduct/product_exportExcel.htm"+formDateConvertUrlParam(formData); 
				$("#product_exportExcel_rowCount").text("正在查询数据库...请耐心等候...");
			},
			needDestroy:true
		
		}
		var dialog=new Dialog(null,dialogoptions);
		
	});	
	
	// 设置按钮恢复可点击
	function changeDisabled(){
		//$(".btn-confirm").removeAttr("disabled");
		$(".btn-deny").text('关闭');
		$(".btn-deny").removeAttr("disabled");
	}

    function dealStatus(row, head){
        var html = [];
        //html.push("<image src='${base}/image/spread.png'  class='tablelink product-sku-table' style='width:25px;height:25px' />");
        //html.push("<span class='glyphicon glyphicon-plus table-sub sub-list' data-product_id="+ row["id"] +"></span>");
        html.push("<a href='${base}/product/ywProduct/edit.htm?id="+ row["id"] +"&op_type=2' class='tablelink'>修改</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(id,mu_subid){
 	    devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/product/ywProduct/delete.json",
 		     data:{"id":id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/product/ywProduct.htm";
	                },"suc");
 		        } else {
 		           isTimeOutdevShowDialog(data.error_info,data.infos);
 		        }
 		     }
 		  })
 	   })
 	}
 	

 	
 	// 批量删除
	function operationCondition(){
	   var ywProductForm = []
	   var ids = []
	   ywProductForm = getAllCheckboxs().split(",");
	   for(var i=0;i<ywProductForm.length;i++){
	   		ids.push(ywProductForm[i].split("-")[0])
	   }
	   devActAfterConfirmAndClose("您确定要删除吗 ?",function(){
	   $.ajax({
			 type:"post",
			 url:"${base}/product/ywProduct/deleteBatch.json",
			 data:{"ids":ids.toString()},
			 dataType:"json",
			 success:function(data){
				if(data.error_no == "0"){
				   devActAfterShowDialog(data.error_info,function(){
					  location.href = "${base}/product/ywProduct.htm";
					},"suc");
				} else {
				   isTimeOutdevShowDialog(data.error_info,data.infos);
				}
			 }
		  })
	  })
	}
		
		// 导入excl文件
		function exportuserfile(){
			location.href="${base}/product/ywProduct/downloadtemplatefile.htm";
		};
		function importuserfile(){
			var dialogoptions = {
				width: '560',
				height:'300',
				title: "导入商品",
				content: '<form class="form"  id="uploadForm"  method="post" enctype="multipart/form-data" >'
						+'<div class="form-group"><label for="level" class="label"><em style="color:red;">*</em>导入文件:</label> '
					   +'<input type="file" id="import_file" name="import_file" /></div>'
					   +"<div class='main-table' style='height:100px;overflow:auto;padding-left:100px;color:green;'>"
					   +'<span id="import_info"  style="width:300px;height:80px;"></span>'
					   + '<div style="display:none;" id="import_error_div"><label for="level" style="color:red;width:80px;">【错误提示】:</label><span style="color:red;" id="import_error_info" ></span></div></form>'
					   +'</div>'
				       ,
				hasBtn: true,
				afterHide:function(){
					location.reload();
				},
				confirm: function (dialog) {
					if(!(CheckFile("import_file","导入文件"))){
						return;
					}
					$("#import_info").show();
					$("#import_info").html("导入中...");
					$(".btn-confirm").attr("disabled","true");
					$(".btn-cancel").text("返回");
					$.ajaxFileUpload({
			            url:'${base}/product/ywProduct/importoperatorinfo.htm',  
			            type: 'post',
			            secureuri:false,
			            fileElementId:['import_file'],		//file标签的id
			            dataType: 'json',//返回数据的类型  
			            data:[],//一同上传的数据  
			            success: function (data, status) {
			            	$("#import_info").html(data.resultInfo);
			            },  
			            error: function (data, status, e) {  
			            	$(".btn-confirm").removeAttr("disabled");
			            	$("#import_info").html(data.resultInfo);
			            }  
			        });  
				},
    			needDestroy:true
			};
			var dialog=new Dialog(null,dialogoptions);
		}
		
		// 检测文件格式
		function CheckFile(id,field_name){
			var f = $("#"+id).val(); 
			if(f!=null&&f.length>0){
			 	var extname = f.substring(f.lastIndexOf(".")+1,f.length);  
	       		extname = extname.toLowerCase();//处理了大小写  
	             if(extname!= "xlsx"&&extname!= "xls"){  
	             	$("#import_error_div").show();
	             	$("#import_error_info").text( field_name+"格式不正确,支持的文件格式为：xls、xlsx！");
	                 return false;  
	             }  
			}else{
				$("#import_error_div").show();
			    $("#import_error_info").text("文件不能为空");
	            return false;  
			}
			$("#import_error_div").hide();
			return true;
		}
		
		
 	// 批量管理
 	function operationBatchManagement(){
 		var ywProductForm = []
	    var ids = []
	    ywProductForm = getAllCheckboxs().split(",");
	    for(var i=0;i<ywProductForm.length;i++){
	   		ids.push(ywProductForm[i].split("-")[0])
	    }
	    $.ajax({
			 type:"post",
			 url:"${base}/product/ywProduct/updateBatchToView.json",
			 data:{"ids":ids.toString()},
			 dataType:"json",
			 success:function(data){
				if(data.error_no == "0"){
				   // 展示用户勾选的商品数据
				   operationShowBatchProduct(data);
				} else {
				   isTimeOutdevShowDialog(data.error_info,data.infos);
				}
			 }
		  })
 	}
 	 
	 // 处理数据(多数据修改相同值)
 	 function operationShowBatchProduct(data){
 	 	var productArray = data.productArray;
 	 	var columnNameArr = [];
 	 	
 	 	columnNameArr.push("<div class='app-column-center-layout'>");
 	    columnNameArr.push("<form id='updateBatchForm' action='updateBatch.json' method='post' class='form'>");
 	    columnNameArr.push("<div style='width:100%;padding-top:10px;' class='app-row-around-layout'>");
 	    columnNameArr.push("<label style='width:50px;line-height:45px' class='label'>修改项:</label>");
 	    columnNameArr.push("<select id='changeVal' name='changeVal' valid='NotBlank' placeholder='请选择修改项' style='width:150px;height:30px;border:1px solid #A7B5BC'>");
 	    columnNameArr.push("<option value=''>请选择修改项</option>");
 	    columnNameArr.push("<option value='1'>商品分类</option>");
 	    columnNameArr.push("<option value='2'>商品上下架状态</option>");
 	    columnNameArr.push("<option value='3'>商品库存</option>");
 	    columnNameArr.push("</select>");
 	    columnNameArr.push("</div>");
 	    columnNameArr.push("<div id='dataTable' style='width:100%;padding-top:10px;' class='app-row-around-layout'>");
 	    columnNameArr.push("</div>");
 	    columnNameArr.push("</form>");
 	    columnNameArr.push("</div>");
 	    
 	    var arr = columnNameArr.join(" ").toString();
	    var dialog = new Dialog(undefined, {
			title : '商品批量管理',
			content : arr,
			needDestroy : true,
			hasBtn : true,
			width : 600,
			btnText : [ '确定','取消' ],
			btnRole : [ 'confirm','cancel' ],
			confirm : function(){
			    // 获取所有选择的商品的编号
			    var product_ids = [];
			    // 获取表单页面数据
			    var changeVal = $("#changeVal").val();
			    var n_shopType = $('#n_shopType option:selected').val();
			    var n_shopType2 = $('#n_shopType2 option:selected').val();
			    var status = $("#status").val();
			    var stock = $("#stock").val();
			    
			    for(var i=0 ; i< productArray.length ; i++){
			    	product_ids.push(productArray[i].id);
			    }
			    
			    // 判断是否有选择选项值
			    if((n_shopType == '' || n_shopType == null) && (status == '' || status == null) && (stock == '' || stock == null)){
			    	alert("请选择修改项");
			    } else {
			    	if(changeVal != '' && changeVal != null){
				        $.ajax({
						 type:"post",
						 url:"${base}/product/ywProduct/updateBatch.json",
						 data:{
						     "product_ids":product_ids.toString(), 
						     "shopType":n_shopType,
						     "shopType2":n_shopType2,
						     "status":status,
						     "stock":stock,
						     },
						 dataType:"json",
						 success:function(data){
							if(data.error_no == "0"){
							   // 关闭弹出层
							   dialog.hide();
							   // 批量修改返回的结果
							   devActAfterShowDialog(data.msg,function(){
					              location.href="${base}/product/ywProduct.htm";
					           },"suc");
							} else {
							   isTimeOutdevShowDialog(data.error_info,data.infos);
							}
						 }
					  })
				    } else {
				        alert("请选择修改项");
				    }
				}
			}
 	 	});
 	 	
		// 选中项后续处理
		$(document).on('change','#changeVal',function(){
			var changeVal = $(this).val();
			var self = this;
		    
			$.ajax({
			 type:"post",
			 url:"${base}/product/ywProduct/changeValBackData.json",
			 data:{"changeVal" : changeVal},
			 dataType:"json",
			 success:function(data){
				if(data.error_no == "0"){
				   var change_falg = data.change_falg;
				   if(change_falg == '1'){
				       var ywProductClassify1 = data.ywProductClassify1;
				       var ywProductClassify2 = data.ywProductClassify2;
				       var classifyArr = [];
				       classifyArr.push("<label style='width:50px;line-height:45px' class='label'>修改值:</label>");
				       classifyArr.push("<div class='app-column-start-layout'>");
				 	   classifyArr.push("<select id='n_shopType' name='n_shopType' valid='NotBlank' placeholder='请选择一级分类' style='width:150px;height:30px;border:1px solid #A7B5BC'>");
				 	   classifyArr.push("<option value=''>请选择一级分类</option>");
				 	   $.each(ywProductClassify1,function(index,item){
				 	       classifyArr.push("<option value='"+ item.classify_id +"'>"+ item.classify_name +"</option>");
				 	   })
				 	   classifyArr.push("</select>");
				 	   
				 	   classifyArr.push("<select id='n_shopType2' name='n_shopType2' placeholder='请选择二级分类' style='width:150px;height:30px;border:1px solid #A7B5BC'>");
				 	   classifyArr.push("<option value=''>请选择二级分类</option>");
				 	   classifyArr.push("</select>");
				 	   
				 	   classifyArr.push("</div>");
				 	   
				 	   $("#dataTable").html(classifyArr.join(" "));
				   } else if(change_falg == '2'){
				       var statusArr = [];
				       statusArr.push("<label style='width:50px;line-height:45px' class='label'>修改值:</label>");
				 	   statusArr.push("<select id='status' name='status' valid='NotBlank' placeholder='请选择上下架状态' style='width:150px;height:30px;border:1px solid #A7B5BC'>");
				 	   statusArr.push("<option value=''>请选择上下架状态</option>");
				 	   statusArr.push("<option value='1'>上架</option>");
				 	   statusArr.push("<option value='2'>下架</option>");
				 	   statusArr.push("</select>");
				 	   
				 	   $("#dataTable").html(statusArr.join(" "));
				   } else if(change_falg == '3'){
				       var stockArr = [];
				       stockArr.push("<label style='width:50px;line-height:45px' class='label'>修改值:</label>");
				       stockArr.push("<input style='width:150px' id='stock' name='stock' type='number' class='dfinput' value=''></input>");
				       
				       $("#dataTable").html(stockArr.join(" "));
				   } else {
				       var defaultText = [];
				       defaultText.push("<text style='color:red'>请选择您需要修改的修改项</text>");
				       $("#dataTable").html(defaultText.join(" "));
				   }
				} else {
				   isTimeOutdevShowDialog(data.error_info,data.infos);
				}
			 }
		  })
		})
		
		// 点击一级分类下拉框读取二级数据
		$(document).on('change','#n_shopType',function(){
			var n_shopType = $(this).val();
			var self = this;
		    
			$.ajax({
			 type:"post",
			 url:"${base}/product/ywProduct/changeShopType.json",
			 data:{"shopType" : n_shopType},
			 dataType:"json",
			 success:function(data){
				if(data.error_no == "0"){
				   var ywProductClassifys = data.ywProductClassifys;
				   var classifyArray = [];
				   if(ywProductClassifys.length > 0){
				       $.each(ywProductClassifys,function(index,item){
				           classifyArray.push("<option value='"+ item.classify_id +"'>"+ item.classify_name +"</option>");
				       })
				   }else {
				       classifyArray.push("<option value=''>没有二级分类</option>");
				   }
				   $("#n_shopType2").html(classifyArray.join(" "));
				} else {
				   isTimeOutdevShowDialog(data.error_info,data.infos);
				}
			 }
		  })
		})
	}

 	
</@script>
</@screen>
