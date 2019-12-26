<#assign base=request.contextPath />
<link rel="stylesheet" href="${base}/css/easyui/themes/bootstrap/tree.css" type="text/css">
<style>
   .panel{
      overflow: initial;
    }
</style>
<@screen id="ywRoleInsert" title="系统字典增加页面" places=[{"name":"用户角色","href":"${base}/system/ywRole.htm"},{"name":"添加/修改","href":"#"}]>
<form id="ywRoleForm" action="insertOrUpdate.json" method="post" class="form">
    <input type="hidden" id="op_type" name="op_type" value="${op_type!''}"/>
    <input id="ro_id" name="ro_id" type="hidden" class="dfinput" <#if op_type == "2">readonly="readonly"</#if> maxbytelength="18" value="${ywRole.ro_id!''}"/>
    <div class="formtitle">
		<span class="formspan">用户角色</span>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>角色名称</label>
		<input id="ro_name" name="ro_name" type="text" class="dfinput" valid="NotBlank" placeholder="请输入角色名称" maxbytelength="18" value="${ywRole.ro_name!''}"/>
	
		<label for="level" class="label">角色备注</label>
		<input id="ro_remark" name="ro_remark" type="text" placeholder="请输入角色备注" class="dfinput" maxlength="11" value="${ywRole.ro_remark!''}"/>
	</div>
	<div class="form-group">
		<label for="level" class="label"><em style="color:red;">*</em>角色状态</label>
		<@select valid="NotBlank" placeholder="请选择角色状态" name = "ro_status" dic_key="1004" value="${ywRole.ro_status!'1'}"/>
	</div>
	<div class="form-group" style="display:flex">
		<label for="level" class="label">菜单字符串</label>
		<#-- 树形菜单 -->
		<div class="easyui-panel" style="padding:5px;width:260px;margin-left: 4px;border-radius: 4px;">
			<ul id="tt" class="easyui-tree"></ul>
		</div>
		<input id="mu_ids" name="mu_ids" type="hidden" class="dfinput" maxlength="255" value="${ywRole.mu_ids!''}"/>
	</div>
	<div class="button-content">
	    <input type="button" class="btn btn_submit_text btn-confirm" value="确认" id="btn_ywRole_submit"/>
		<input type="button" class="btn btn_submit_text btn-cancel" value="取消" id="btn_ywRole_back"/>
	</div>
</form>
<#-- easyui -->
<@script src="${base}/js/jquery.easyui.min.js"/>
<@script>
     
$(function(){
  // 确认
  $("#btn_ywRole_submit").click(function(){
      // 表单校验
      if (!formValidate("ywRoleForm")) {
		 return false;
	  }
      getChecked();
      var formData = $("#ywRoleForm").serializeArray();
      $.post($("#ywRoleForm").attr("action"), formData, function(data){
            var openType=${op_type};
            var operateType={"0":"添加","1":"修改"};
            if (data.error_no == 0) {
                alert(data.error_info);
                location.href = "${base}/system/ywRole.htm";
            } else {
              isTimeOutdevShowDialog(data.error_info,data.infos);
            };
        }).error(function(){
            devShowDialog("系统异常");
        });
  })
  
   
  // 取消
  $("#btn_ywRole_back").click(function(){
      location.href = "${base}/system/ywRole.htm";
  })
  
	//此处是easyui调用tree组件的方法(使用easyui的童鞋自然懂得，只需修改请求json的api即可)  
	$('#tt').tree({  
	    url: 'getMenus.json',
	    animate:true,
	    checkbox:true,  
	    loadFilter: function(data){
	        console.log(data);
	        return jsonbl(data);   
	    }
	});
})

//此处是easyui的json格式  
	var tree = {  
	    id:'',  
	    text:'',  
	    state:'',  
	    checked:'',  
	    attributes:'',  
	    children:''  
	}  

function jsonbl(data){  
	    var easyTree = [];
	    if(data instanceof Array){
	       $.each(data,function(index,item){  
	         easyTree[index] = bl(item);  
	       }); 
	    } else {
	        easyTree.push(bl(data));
	    } 
	    return easyTree;  
	}

//此处是把后台传过来的json数据转成easyui规定的格式  
	function bl(item){  
	    var tree = new Object();  
	    tree.id = item.mu_id;  
	    tree.text = item.mu_name;
	    // 修改时判断是否已经勾选
	    var mu_ids = $("#mu_ids").val();
	    debugger;
	    if(mu_ids != ""){
	       // 排除父菜单勾选
	       if((mu_ids.split(",").indexOf(item.mu_id) != -1) && item.mu_level != "1" && item.mu_level != ""){
	          tree.checked = true;
	       }
	    }  
	    if(item.child_menus.length != 0){
	        tree.state = 'closed';
	        tree.children = jsonbl(item.child_menus);  
	    }
	    return tree;  
	}  

//获取树形菜单勾选值树形菜单
  function getChecked(){
        debugger;
		var nodes = $('#tt').tree('getChecked');
		var s = '';
		console.log(nodes.length);
		for(var i=0; i<nodes.length; i++){
		    console.log(nodes[i].id);
		    if (nodes[i].id == '0') continue;
			if (s != '') s += ',';
			s += nodes[i].id;
		}
		$("#mu_ids").val(s);
	}
	
 	
</@script>
</@screen>