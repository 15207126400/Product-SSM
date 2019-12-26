<@screen id="qhzTeacher" title="老师信息模块页面" places=[{"name":"老师信息模块","href":"#"}]>
<@ajaxQueryTable id="qhzTeacher_list" auto=true isMultiple=true multipleCondition=["id"]
	action="${base}/qhz_teacher/qhzTeacher/getList.json" 
	paginatorUrl="${base}/qhz_teacher/qhzTeacher/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
	       {"id":"id", "name":"编号"},
	       {"id":"name", "name":"姓名"},
	       {"id":"photo", "name":"照片","type":"img"},
	       {"id":"card", "name":"身份证"},
	       {"id":"age", "name":"年龄"},
	       {"id":"sex", "name":"性别","dic_key":"1011"},
	       {"id":"phone", "name":"手机号"},
	       {"id":"address", "name":"住址"},
	       {"id":"post", "name":"职位"},
	       {"id":"dept", "name":"部门"},
	       {"id":"caozuo", "name":"操作", "provider":"dealStatus"}
		
	]>

	
	<@innerOfQueryCondition>
	    <dl class="search-dl">
	    	<dd>
  				<span class="dl-title">身份证：</span>
    			<label class="w-170">
      			 <input style="width:170px;" id="card" name="card" type="text" class="yw-input" value="" placeholder="请输入身份证"/ >
    			</label>
  			</dd>
  			<dd>
  				<span class="dl-title">性别：</span>
    			<label class="w-170">
      			 <@select id="sex" name="sex" class="yw-input" dic_key="1011" value="${sex!''}" />
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<@innerOfQueryTable>
	<div class="right-btn-box">
	    <span class="input-group-btn" style="float: left;">
			<a href="${base}/qhz_teacher/qhzTeacher/edit.htm?op_type=1">
				<button class="btn btn-success btn_text" type="button">添加</button>
			</a>
		</span>
		<span class="input-group-btn">
			<a href="javascript:void(0)" >
				<button class="btn btn-success btn_text" type="button" onclick="deleteBatch()">批量删除</button>
			</a>
		</span>
	</div>
   </@innerOfQueryTable>
</@ajaxQueryTable>
<@script>

    function dealStatus(row, head){
        var html = [];
        html.push("<a href='${base}/qhz_teacher/qhzTeacher/edit.htm?id="+ row["id"]+"&op_type=2' class='tablelink'>修改</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(id){
 	    devActAfterConfirmAndClose("您确定要删除吗",function(){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/qhz_teacher/qhzTeacher/delete.json",
 		     data:{"id":id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/qhz_teacher/qhzTeacher.htm";
	                },"suc");
 		        } else {
 		           isTimeOutdevShowDialog(data.error_info,data.infos);
 		        }
 		     }
 		  })
 	   });
 	}
 	
 	// 批量删除
	function deleteBatch(){
	  var qhzTeacherForm = []
	   var id = []
	   qhzTeacherForm = getAllCheckboxs().split(",");
	   for(var i=0;i<qhzTeacherForm.length;i++){
	   		id.push(qhzTeacherForm[i].split("-")[0])
	   }
	  devActAfterConfirmAndClose("您确定要批量删除吗",function(){
		   $.ajax({
				 type:"post",
				 url:"${base}/qhz_teacher/qhzTeacher/deleteBatch.json",
				 data:{"id":id.toString()},
				 dataType:"json",
				 success:function(data){
					if(data.error_no == "0"){
					   devActAfterShowDialog(data.error_info,function(){
						  location.href = "${base}/qhz_teacher/qhzTeacher.htm";
						},"suc");
					} else {
					   isTimeOutdevShowDialog(data.error_info,data.infos);
					}
				 }
			  })
		});  
	 }
 	
</@script>
</@screen>