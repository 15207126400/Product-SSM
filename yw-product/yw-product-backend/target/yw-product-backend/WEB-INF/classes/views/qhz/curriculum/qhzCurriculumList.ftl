<@screen id="qhzCurriculum" title="沙龙注册课程信息表页面" places=[{"name":"沙龙注册课程信息表","href":"#"}]>
<@ajaxQueryTable id="qhzCurriculum_list" auto=true isMultiple=true multipleCondition=["id"]
	action="${base}/qhz_curriculum/qhzCurriculum/getList.json" 
	paginatorUrl="${base}/qhz_curriculum/qhzCurriculum/getPaginator.json"
	buttons={"查询#btn_qry":"btn btn-info btn-search"}
	headItems=[
	       {"id":"id", "name":"编号"},
	       {"id":"type", "name":"课程类型","dic_key":"1092"},
	       {"id":"scene_type", "name":"应用场景","dic_key":"1096"},
	       {"id":"img", "name":"图片","type":"img"},
	       {"id":"teacher_name", "name":"主讲老师"},
	       {"id":"title", "name":"课程名称"},
	       {"id":"price", "name":"课程价格"},
	       {"id":"old_price", "name":"课程原价"},
	       {"id":"buy_num", "name":"订阅量"},
	       {"id":"create_name", "name":"创建人"},
	       {"id":"create_time", "name":"创建时间"},
	       {"id":"update_name", "name":"更新人"},
	       {"id":"update_time", "name":"更新时间"},
	       {"id":"status", "name":"启用状态","dic_key":"1000"},
	       {"id":"caozuo", "name":"操作", "provider":"dealStatus"}
		
	]>

	
	<@innerOfQueryCondition>
	    <dl class="search-dl">
  			<dd>
  				<span class="dl-title">课程类型：</span>
    			<label class="w-170">
      			 <@select id="type" name="type" class="yw-input" dic_key="1092" value="${type!''}" />
    			</label>
  			</dd>
  			<dd>
  				<span class="dl-title">应用场景：</span>
    			<label class="w-170">
      			 <@select id="scene_type" name="scene_type" class="yw-input" dic_key="1096" value="${scene_type!''}" />
    			</label>
  			</dd>
		</dl>
	</@innerOfQueryCondition>
	
	<@innerOfQueryTable>
	<div class="right-btn-box">
	    <span class="input-group-btn" style="float: left;">
			<a href="${base}/qhz_curriculum/qhzCurriculum/edit.htm?op_type=1">
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
        html.push("<a href='${base}/qhz_curriculum/qhzCurriculum/edit.htm?id="+ row["id"]+"&op_type=2' class='tablelink'>修改</a>");
        html.push("<a href='javascript:void(0)' onclick=\"deleteOne('"+row["id"]+"')\" class='tablelink'>删除</a>");
   		return html.join(" ");
    }
	
    // 删除
 	function deleteOne(id){
 	    devActAfterConfirmAndClose("您确定要删除吗",function(){
 	    
 	       $.ajax({
 		     type:"post",
 		     url:"${base}/qhz_curriculum/qhzCurriculum/delete.json",
 		     data:{"id":id},
 		     dataType:"json",
 		     success:function(data){
 		        if(data.error_no == "0"){
 		           devActAfterShowDialog(data.error_info,function(){
	                  location.href = "${base}/qhz_curriculum/qhzCurriculum.htm";
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
	  var qhzCurriculumForm = []
	   var id = []
	   qhzCurriculumForm = getAllCheckboxs().split(",");
	   for(var i=0;i<qhzCurriculumForm.length;i++){
	   		id.push(qhzCurriculumForm[i].split("-")[0])
	   }
	  devActAfterConfirmAndClose("您确定要批量删除吗",function(){
		   $.ajax({
				 type:"post",
				 url:"${base}/qhz_curriculum/qhzCurriculum/deleteBatch.json",
				 data:{"id":id.toString()},
				 dataType:"json",
				 success:function(data){
					if(data.error_no == "0"){
					   devActAfterShowDialog(data.error_info,function(){
						  location.href = "${base}/qhz_curriculum/qhzCurriculum.htm";
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