<@screen id="qhzCourse" title="课程详细信息页面" places=[{"name":"课程详细信息","href":"#"}]>
<@ajaxQueryTable id="qhzCourse_list" auto=true isMultiple=true multipleCondition=["id"]
	action="${base}/qhz_course/qhzCourse/getList.json" 
	paginatorUrl="${base}/qhz_course/qhzCourse/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
	       {"id":"id", "name":"编号"},
	       {"id":"title", "name":"课程标题", "width":"100px"},
	       {"id":"img", "name":"课程图片","type":"img"},
	       {"id":"curriculum_name", "name":"所属课程"},
	       {"id":"teacher_name", "name":"主讲老师"},
	       {"id":"resume", "name":"课程简述"},
	       {"id":"epis", "name":"课程集数"},
	       {"id":"unit_price", "name":"单价(现价)"},
	       {"id":"fic_price", "name":"虚拟价格(原价)"},
	       {"id":"click_num", "name":"总点击量(播放次数)"},
	       {"id":"fic_click_num", "name":"虚拟点击量"},
	       {"id":"audition", "name":"是否可以试听","dic_key":"1097"},
	       {"id":"create_name", "name":"创建人"},
	       {"id":"create_time", "name":"创建时间"},
	       {"id":"update_name", "name":"更新人"},
	       {"id":"update_time", "name":"修改时间"},
	       {"id":"status", "name":"启用状态","dic_key":"1000"},
	       {"id":"caozuo", "name":"操作", "provider":"dealStatus"}
		
	]>

	
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">所属课程：</span>
    			<label class="w-170">
      			 	<select id="curriculum_id" name="curriculum_id" class="yw-input">
						<option value="">请选择所属课程</option>
						<#list qhzCurriculums as qhzCurriculum>
						<option value="${qhzCurriculum.id}">${qhzCurriculum.title}</option>
          				</#list>
		    		</select>
    			</label>
  			</dd>
  			<dd>
  				<span class="dl-title">主讲老师：</span>
    			<label class="w-170">
      			 	<select id="teacher_id" name="teacher_id" class="yw-input">
						<option value="">请选择主讲老师</option>
						<#list qhzTeachers as qhzTeacher>
						<option value="${qhzTeacher.id}">${qhzTeacher.name}</option>
          				</#list>
		    		</select>
    			</label>
  			</dd>
  			<dd>
  				<span class="dl-title">是否可以试听：</span>
    			<label class="w-170">
      			 <@select id="audition" name="audition" class="yw-input" dic_key="1097" value="${audition!''}" />
    			</label>
  			</dd>
  			<dd>
  				<span class="dl-title">启用状态：</span>
    			<label class="w-170">
      			 <@select id="status" name="status" class="yw-input" dic_key="1000" value="${1000!''}" />
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<@innerOfQueryTable>
	<div class="right-btn-box">
	    <span class="input-group-btn" style="float: left;">
			<a href="${base}/qhz_course/qhzCourse/edit.htm?op_type=1">
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
        html.push("<a href='${base}/qhz_course/qhzCourse/edit.htm?id="+ row["id"]+"&op_type=2' class='tablelink'>修改</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(id){
 	    devActAfterConfirmAndClose("您确定要删除吗",function(){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/qhz_course/qhzCourse/delete.json",
 		     data:{"id":id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/qhz_course/qhzCourse.htm";
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
	  var qhzCourseForm = []
	   var id = []
	   qhzCourseForm = getAllCheckboxs().split(",");
	   for(var i=0;i<qhzCourseForm.length;i++){
	   		id.push(qhzCourseForm[i].split("-")[0])
	   }
	  devActAfterConfirmAndClose("您确定要批量删除吗",function(){
		   $.ajax({
				 type:"post",
				 url:"${base}/qhz_course/qhzCourse/deleteBatch.json",
				 data:{"id":id.toString()},
				 dataType:"json",
				 success:function(data){
					if(data.error_no == "0"){
					   devActAfterShowDialog(data.error_info,function(){
						  location.href = "${base}/qhz_course/qhzCourse.htm";
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