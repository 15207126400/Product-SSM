<#assign base=request.contextPath />
<@screen id="parameter" title="系统参数">
<#--
<div class="button-inner">
	<a class="btn btn-add btn-primary-white btn-small" href="javascript:void(0)" onclick="refreshAllCache();">刷新缓存</a>
</div>-->
<div class="main-table">
  <table width="100%" cellpadding="0" cellspacing="0" class="data-table"> 
    <thead>
      <tr>
        <th width="15%">序号</th>
        <th width="35%">分类</th>
        <th width="35%">描述</th>
        <th class="text-c" width="15%">操作</th>
      </tr>
    </thead>
    <tbody>
    <#if configlist?exists && configlist?size gt 0>
    <#list configlist as item>
      <tr>
        <td>${(item.id)!""}</td>
        <td>${(item.name)!""}</td>
        <td>${(item.description)!""}</td>
        <td class="text-c">
        <#if 0==item.status!"">
        	<a href="${base}/system/sysconfig/sysargEdit.htm?catalogId=${(item.id)!""}" class="btn btn-fill btn-small">未配置</a>
        <#elseif 1==item.status!"">
        	<a href="${base}/system/sysconfig/sysargEdit.htm?catalogId=${(item.id)!""}" class="btn btn-fill btn-small">更新</a>
        <#else>
        	<a href="${base}/system/sysconfig/sysargEdit.htm?catalogId=${(item.id)!""}" class="btn btn-fill btn-small">配置</a>		
        </#if>
        </td>
      </tr>
      </#list>
     </#if>
    </tbody>
  </table>
</div>
<@script>
    function refreshAllCache(){
        var refreshAllCache_screen = new Dialog(null, {
        	title: '',
        	width: '360',
        	needDestroy: true,
        	hasBtn: true,
        	btnText: ["取消","确定"],
       	 	btnRole: ['cancel','confirm'],
        	btnCls: ['cancel','confirm'],
        	confirm: function() {
            	$('.btn-wrap [data-role="confirm"]').attr("disabled","disabled");
            	//确定按钮事件
            	$.ajax({
					type: "post",
					dataType: "json",
					url: "${base}/cache/refreshCacheImmediately.json?rnd=" + new Date().getTime(),
					success: function (data) {
						refreshAllCache_screen.hide();
						if (data.error_no == 0) {
							devShowDialog("刷新完成");
						} else {
							devShowDialog("删除失败");
						}
					}
				});
          	},
        	content: '<h1 class="h-title">您确定要刷新缓存吗？</h1>'
      	});
    }
</@script>
</@screen>