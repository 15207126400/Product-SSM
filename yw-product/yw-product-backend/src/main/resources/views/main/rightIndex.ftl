<#assign base=request.contextPath />
<@script src="${base}/js/highcharts.js" />
<@script src="${base}/js/jquery.highchartTable.js" />
<@screen id="ywRightIndex" title="首页" places=[{"name":"首页","href":"#"}]>
<style>
.big-box{
   display: flex;
   flex-direction: row;
   justify-content: space-around;
   align-items: center;
}
</style>
	<image style="width:100%;height:78%;" src="${base}/img/qhz/index.jpg" />
<#--
<div class="big-box">
	<div style="width:40%">
		<table class="highchart1" data-graph-container-before="1" data-graph-type="column" style="display:none">
		  <caption>平台用户流量统计报表</caption>
		  <thead>
		      <tr>
		          <th>季度</th>
		          <th>访问量</th>
		          <th>注册量</th>
		      </tr>
		   </thead>
		   <tbody>
		      <tr>
		          <td>一季度</td>
		          <td>100</td>
		          <td>60</td>
		      </tr>
		      <tr>
		          <td>二季度</td>
		          <td>200</td>
		          <td>120</td>
		      </tr>
		      <tr>
		          <td>三季度</td>
		          <td>300</td>
		          <td>180</td>
		      </tr>
		      <tr>
		          <td>四季度</td>
		          <td>600</td>
		          <td>400</td>
		      </tr>
		  </tbody>
		</table>
	</div>

	<div style="width:40%">
		<table class="highchart2" data-graph-container-before="1" data-graph-type="column" style="display:none">
		  <caption>平台盈利统计报表</caption>
		  <thead>
		      <tr>
		          <th>季度</th>
		          <th>成交量</th>
		          <th>交易成功</th>
		          <th>交易失败</th>
		      </tr>
		   </thead>
		   <tbody>
		      <tr>
		          <td>一季度</td>
		          <td>798</td>
		          <td>762</td>
		          <td>36</td>
		      </tr>
		      <tr>
		          <td>二季度</td>
		          <td>2050</td>
		          <td>1987</td>
		          <td>63</td>
		      </tr>
		      <tr>
		          <td>三季度</td>
		          <td>382</td>
		          <td>382</td>
		          <td>0</td>
		      </tr>
		      <tr>
		          <td>四季度</td>
		          <td>763</td>
		          <td>761</td>
		          <td>2</td>
		      </tr>
		  </tbody>
		</table>
	</div>
</div>
-->
<@script>
    $(document).ready(function(){
          $('table.highchart1').highchartTable();
          $('table.highchart2').highchartTable();
    });
</@script>
</@screen>

