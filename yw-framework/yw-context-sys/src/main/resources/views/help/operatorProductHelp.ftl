<@screen id="parameter" title="帮助页面" places=[{"name":"帮助","href":"#"}]>
<#assign base=request.contextPath />
<style>
.b-box{
	margin:50px;
}
.s-box{
	margin-top:20px;
}
.p-title{
	font-weight:bold;
}
.content-text{
	margin-left:20px;
	color:#7E7C7C;
}
.content-img{
	margin:20px;
	width:95%;
	height:600px;
	border:1px solid #CAC3B6;
}
.content-s-img{
	border:1px solid #CAC3B6;
	margin-left:20px;
	width:auto;
}
</style>
<body>
	<div class="b-box">
		<p class="p-title">商品管理帮助文档</p>
		<div class="s-box">
			<p class="p-title">1 . 如何上架新的商品 ?</p>
			<p class="content-text">第一步 , 点击商品管理展开商品类功能目录</p>
			<p class="content-text">第二步 , 点击商品信息进入商品列表界面</p>
			<p class="content-text">第三步 , 点击添加进入商品编辑界面</p> 
			<image class="content-img" src="${base}/image/home/help/product/help_product01.png" />
			<p class="content-text">第四步 , 按规则录入正确的数据 , 点击保存即可完成新增商品操作</p>
			<p class="content-text">⑴ 输入商品名称 </p>
			<p class="content-text">后台预览如下图所示 :  </p>
			<image class="content-s-img" src="${base}/image/home/help/product/help_product_title01.png" />
			<p class="content-text">说明 : 此处输入的是商品的标题 , 显示在对应小程序的商品标题栏目处 , 当标题过长超出小程序显示区域时以省略号代替</p>
			<p class="content-text">小程序预览如下图所示 :  </p>
			<image class="content-s-img" src="${base}/image/home/help/product/help_product_title02.png" />
			
			<p class="content-text">⑵ 选择商品分类 </p>
			<p class="content-text">后台预览如下图所示 :  </p>
			<div class="app-row-start-layout">
				<image class="content-s-img" src="${base}/image/home/help/product/help_product_title03.png" />
				<image class="content-s-img" src="${base}/image/home/help/product/help_product_title04.png" />
			</div>
			<p class="content-text">说明 : 此处可选择商品所对应的分类 , 会显示到小程序对应的分类栏目中 , 可以灵活选择只设置一级分类 , 或者设置一级分类下的二级分类</p>
			<p class="content-text">小程序预览如下图所示 :  </p>
			<image class="content-s-img" src="${base}/image/home/help/product/help_product_title05.png" />
			
			<p class="content-text">⑶ 选择商品标志 </p>
			<p class="content-text">后台预览如下图所示 :  </p>
			<image class="content-s-img" src="${base}/image/home/help/product/help_product_title06.png" />
			<p class="content-text">说明 : 此处选择的商品标志对应小程序首页的各展示栏 , 具体可参考下面的小程序预览图</p>
			<p class="content-text">小程序预览如下图所示 :  </p>
			<image class="content-s-img" src="${base}/image/home/help/product/help_product_title07.png" />
			
			<p class="content-text">⑷ 输入商品排序值 </p>
			<p class="content-text">后台预览如下图所示 :  </p>
			<image class="content-s-img" src="${base}/image/home/help/product/help_product_title08.png" />
			<p class="content-text">说明 : 排序值越小 , 小程序界面的显示顺序越靠前 , 建议初始值都设置的大一点 , 如999 , 想要哪个商品显示的靠前时再按顺序由低向高设置排序值</p>
		
			<p class="content-text">⑸ 输入商品特殊描述 </p>
			<p class="content-text">后台预览如下图所示 :  </p>
			<image class="content-s-img" src="${base}/image/home/help/product/help_product_title09.png" />
			<p class="content-text">说明 : 此处可输入商品的特色 , 提高卖点 , 可以选择不输入</p>
			<p class="content-text">小程序预览如下图所示 :  </p>
			<image class="content-s-img" src="${base}/image/home/help/product/help_product_title10.png" />
			
			<p class="content-text">⑹ 上传商品图片 </p>
			<p class="content-text">后台预览如下图所示 :  </p>
			<image class="content-s-img" src="${base}/image/home/help/product/help_product_title11.png" />
			<p class="content-text">说明 : 此处可上传商品图片</p>
			<p class="content-text">小程序预览如下图所示 :  </p>
			<div class="app-row-start-layout">
				<div class="app-column-center-layout">
					<image class="content-s-img" src="${base}/image/home/help/product/help_product_title12.png" />
					<p class="content-text">首页显示</p>
				</div>
				<div class="app-column-center-layout">
					<image class="content-s-img" src="${base}/image/home/help/product/help_product_title13.png" />
					<p class="content-text">商品详情显示</p>
				</div>
			</div>
			
			<p class="content-text">⑺ 商品详情部分 </p>
			<p class="content-text">后台预览如下图所示 :  </p>
			<image class="content-s-img" src="${base}/image/home/help/product/help_product_title14.png" />
			<p class="content-text">说明 : 此处本平台采用富文本编辑器 , 可选择上传图片或者直接编辑文本 , 建议以图片代替文本 , 这样给用户的体验度更好</p>
			<p class="content-text">小程序预览如下图所示 :  </p>
			<image class="content-s-img" src="${base}/image/home/help/product/help_product_title15.png" />
			
			<p class="content-text">⑻ 商品规格部分 </p>
			<p class="content-text">后台预览如下图所示 :  </p>
			<image class="content-s-img" src="${base}/image/home/help/product/help_product_title16.png" />
			<p class="content-text">说明 : 此处添加或者选择规格 , 并设置规格对应需要的参数 , 比如图片 , 价格 , 库存等 , 可以根据规格各自设置 , 也可以全部统一设置</p>
			<p class="content-text">小程序预览如下图所示 :  </p>
			<image class="content-s-img" src="${base}/image/home/help/product/help_product_title17.png" />
			
			<p class="content-text">⑼ 商品虚拟参数部分 </p>
			<p class="content-text">后台预览如下图所示 :  </p>
			<image class="content-s-img" src="${base}/image/home/help/product/help_product_title18.png" />
			<p class="content-text">说明 : 这些参数主要作为首页显示时的数据 , 例如虚拟价格与价格 , 当虚拟价格设置的高于价格时 , 在小程序界面
			会显示价格处于折扣状态 , 提高用户购买欲望 ; 又例如虚拟销量 , 可以根据自己需要设置一个参数显示在首页 , 当然 , 也都可以使用默认值0</p>
			<p class="content-text">小程序预览如下图所示 :  </p>
			<image class="content-s-img" src="${base}/image/home/help/product/help_product_title19.png" />
			
			<p class="content-text">⑽ 商品起拍量设置 </p>
			<p class="content-text">后台预览如下图所示 :  </p>
			<image class="content-s-img" src="${base}/image/home/help/product/help_product_title20.png" />
			<p class="content-text">说明 : 此处可设置该商品最低购买数量 , 例如设置为10 , 那么用户必须购买满足10件才能进行下单操作 , 若无此需要可使用默认值0</p>
			<p class="content-text">小程序预览如下图所示 :  </p>
			<image class="content-s-img" src="${base}/image/home/help/product/help_product_title21.png" />
			
			<p class="content-text">⑾ 商品上下架设置 </p>
			<p class="content-text">后台预览如下图所示 :  </p>
			<image class="content-s-img" src="${base}/image/home/help/product/help_product_title22.png" />
			<p class="content-text">说明 : 此处可设置该商品上下架状态</p>
			
			<p class="content-text">⑿ 商品轮播图设置 </p>
			<p class="content-text">后台预览如下图所示 :  </p>
			<image class="content-s-img" src="${base}/image/home/help/product/help_product_title23.png" />
			<p class="content-text">说明 : 此处可设置商品对应的轮播图 , 建议最少上传三张 , 否则首页部分栏目无法显示该商品</p>
			<p class="content-text">小程序预览如下图所示 :  </p>
			<image class="content-s-img" src="${base}/image/home/help/product/help_product_title24.png" />
		</div>
		
		<div class="s-box">
			<p class="p-title">2 . 如何管理商品的分类 ?</p>
			<p class="content-text">第一步 , 点击商品管理展开商品类功能目录</p>
			<p class="content-text">第二步 , 点击商品信息进入商品列表界面</p>
			<p class="content-text">第三步 , 点击分类切换至商品分类管理界面</p> 
			<p class="content-text">第四步 , 点击添加进入商品分类编辑界面</p>
			<p class="content-text">第五步 , 按规则录入正确的数据 , 点击保存即可完成新增商品分类操作</p>
			<image class="content-img" src="${base}/image/home/help/product/help_product02.png" />
			<p class="content-text">说明 : 分类管理可以对分类进行怎删改操作 , 需要注意的是添加二级分类时可以上传二级分类的图片 , 一级分类不需要传图片</p>
		</div>
		<div class="s-box">
			<p class="p-title">3 . 如何管理订单 ?</p>
			<p class="content-text">第一步 , 点击商品管理展开商品类功能目录</p>
			<p class="content-text">第二步 , 点击订单信息进入订单列表界面</p>
			<image class="content-img" src="${base}/image/home/help/product/help_product03.png" />
			<p class="content-text">说明 : 订单管理可以进行订单的查看及删除常规操作 , 并且会根据订单的当前状态显示对应的操作 , 例如订单状态为待发货时 , 会显示发货按钮 , 订单状态为待退款时 , 会显示同意与不同意按钮</p>
		</div>
		<div class="s-box">
			<p class="p-title">4 . 如何管理支付信息 ?</p>
			<p class="content-text">第一步 , 点击商品管理展开商品类功能目录</p>
			<p class="content-text">第二步 , 点击支付信息进入支付信息列表界面</p>
			<image class="content-img" src="${base}/image/home/help/product/help_product04.png" />
			<p class="content-text">说明 : 此处显示所有订单的支付流水信息 , 只提供查看与删除操作</p>
		</div>
	</div>
</body>
</@screen>