/**
 * 
 */
(function () {

	window.CrhWebsocket = new Constructor();

	function Constructor() {

		this.listeners = {};
	}

	/**
	 * 建立websocket连接
	 */
	Constructor.prototype.openWebsocket = function (operator_no, domain) {

		if (!this.isNotEmpty(operator_no)) {
			return;
		}

		newWebSocket.call(this, operator_no, domain);
		this.operator_no = operator_no;
	};

	/**
	 * 注册websocket监听器，websocket事件发生时回调，回调时第一个参数总是
	 * websocket事件的字符串（"onopen", "onclose", "onmessage", "onerror"）
	 * 后面的参数与websocket本身回调函数参数列表一致
	 */
	Constructor.prototype.registerListener = function (name, listener) {

		this.listeners[name] = listener;
	};

	/**
	 * 注销websocket监听器
	 */
	Constructor.prototype.unRegisterListener = function (name) {

		delete this.listeners[name];
	};

	/**
	 * 更新数字提醒
	 * @param type
	 * @param count
	 */
	Constructor.prototype.updateTips = function (data) {
		
		var count = this.validNumber(data.count);

		if (count === false) {
			console.log("非数字，更新数字提醒中断，count=" + count);
			return;
		}

		// 支持根据class和menu_id找，menu_id优先
		var li_class = $("ul ." + data.li_class).find("a");
		var li_menu_id = $("a[menu_id=" + data.menu_id + "]");
		if (li_menu_id.length > 0) {
			showTaskCount(li_menu_id, count);
		} else if (li_class.length > 0) {
			showTaskCount(li_class, count);
		}

		function showTaskCount(obj, count) {
			
			if (obj.length > 1) {
				console.log("multiple objects find, selector = " + obj.selector + ", length = " + obj.length);
			}

			if (count <= 0) {
				$(obj).find("small").remove();
			} else {
				$(obj).find("small").remove();
				$(obj).append("<small>" + count + "</small>");
			}
			
			// 父级菜单显示子菜单消息数的总和
			if (obj.length == 1 && $(obj).attr("islaft") != 1) {
				return;
			}
			if (obj.length > 1) {
				var islaft_flag = false;
				for (var i = 0; i < obj.length; i++) {
					if ($(obj[i]).attr("islaft") == 1) {
						islaft_flag = true;
						break;
					}
				}
				if (islaft_flag == false) {
					return;
				}
			}

			if (obj.length > 1) {
				obj = obj[i];
			}

			var total_count = 0;
			var parent = $(obj).parent().parent();
			$(parent[0].children).each(function (index, element) {
				var small = $(element).find("small");
				if (small.length == 1 && Constructor.prototype.validNumber(small[0].innerText) != false) {
					total_count += Constructor.prototype.validNumber(small[0].innerText);
				}
			});

			$($(parent).parent()[0].children).each(function (index, element) {
				if (element.tagName == "SMALL") {
					$(element).remove();
				}
			});
			if (total_count > 0) {
				$(parent).parent().append("<small>" + total_count + "</small>");
			}
		}
	};

	Constructor.prototype.updateMesssysnotice = function(data) {
 		var notice_content = data.notice_content;
 		var notice_id = data.notice_id;
 		var div = $("#msg-box").html();
 		var addDiv ='<div class="msg-item">'+
     		'<div class="msg-top">系统提示(编号：'+notice_id+')</div>'+
     		'<div class="msg-delete">╳</div>'+
     		'<div class="msg-content"><a href="javascript:void(0)" onclick="showContent(\''+notice_id+'\',\''+notice_content+'\')">'+
     		notice_content+
     		'</a>'+
	
     		'</div>'+
     	  '</div>';
 		div = addDiv+div;
 		$("#msg-box").html(div);
 	};
	
	
	/**
	 * 判断一个字符串不为空
	 * @param obj
	 */
	Constructor.prototype.isNotEmpty = function (obj) {

		if (this.isNotNull(obj) && obj != "") {
			return true;
		}
		return false;
	};

	/**
	 * 判断一个对象不为空
	 * @param obj
	 */
	Constructor.prototype.isNotNull = function (obj) {

		if (obj != null) {
			return true;
		}
		return false;
	};

	/**
	 * 验证是否是数字
	 * @param number
	 * @returns 返回false，如果不是数字。否则返回对应的数字
	 */
	Constructor.prototype.validNumber = function (number) {

		if (number == null) {
			return false;
		}

		var n = Number(number);

		if (isNaN(n)) {
			return false;
		}

		return n;
	};

	/**
	 * 获取当前时间字符串
	 */
	Constructor.prototype.getNow = function () {

		var date = new Date();

		var year = date.getFullYear();
		var month = (date.getMonth() + 1) < 10 ? ("0" + (date.getMonth() + 1)) : (date.getMonth() + 1);
		var day = date.getDate() < 10 ? ("0" + date.getDate()) : date.getDate();
		var hour = date.getHours() < 10 ? ("0" + date.getHours()) : date.getHours();
		var minute = date.getMinutes() < 10 ? ("0" + date.getMinutes()) : date.getMinutes();
		var second = date.getSeconds() < 10 ? ("0" + date.getSeconds()) : date.getSeconds();

		var dateStr = hour + ":" + minute + ":" + second;

		return dateStr;
	};

	/**
	 * 日志输出
	 */
	Constructor.prototype.log = function (str) {

		if (typeof console != "undefined" && console.log != "undefined") {
			console.log(str);
		}
	};

	/**
	 * 带有时间的日志输出
	 * @param str
	 */
	Constructor.prototype.logWithTime = function (str) {

		this.log(this.getNow() + ":" + str);
	};

	/**
	 * 带user_id的日志打印
	 */
	Constructor.prototype.logWithUserId = function (operator_no, str) {

		this.logWithTime("[operator_no = " + operator_no + "]" + str);
	};

	//---------------------------------------private--------------------------------------------//		
	/**
	 * 触发监听器
	 * @param type 类型 onopen onmessage等等
	 * @param args 参数，类型为数组
	 */
	function triggerListener(type, args) {

		if (args === undefined) {
			args = [];
		}
		args.unshift(type);

		var listenerArray = Object.getOwnPropertyNames(CrhWebsocket.listeners);
		for (var i = 0; i < listenerArray.length; i++) {

			var listenerName = listenerArray[i];
			CrhWebsocket.listeners[listenerName].apply(null, args);
		}
	}

	/**
	 * 新建一个websocket连接
	 * @return 建立的websocket对象
	 */
	function newWebSocket(operator_no, domain) {

		with (this) {

			var protocol = window.location.protocol,
				wsUrl = "",
				ws,
				intervalId;

			if (typeof (domain) == "undefined" || domain == "") {
				domain = "xcx.whywxx.com";
			}
			if (protocol == "https:") {
				wsUrl = "wss://" + domain + "/system/websocket/usercustomer.ws";
			} else {
				wsUrl = "ws://" + domain + "/system/websocket/usercustomer.ws";
			}

			if ('WebSocket' in window) {
				ws = new WebSocket(wsUrl);
			} else if ('MozWebSocket' in window) {
				ws = new MozWebSocket(wsUrl);
			} else {
				alertMsg("您的浏览器版本不支持消息推送，无法展示任务数量");
				return false;
			}

			if (isNotNull(ws)) {
				logWithUserId(operator_no, "websocket已经初始化,正在连接中...!");
			}

			ws.onopen = function () {

				triggerListener("onopen");

				var params = { operator_no: operator_no };
				if (isNotNull(ws)) {
					ws.send(JSON.stringify(params));
				}

				logWithUserId(operator_no, "已连接上websocket服务器!");
				intervalId = setInterval(function () {

					console.log("send heartbeat...");
					ws.send("heartbeat");
				}, 50000);
			};

			ws.onmessage = function (evt) {

				triggerListener("onmessage", [evt]);

				var data = JSON.parse(evt.data);

//				switch (data.type) {
//
//					case "task_count":
//						updateTips(data);
//						break;
//						
//					case "messsysnotice":
// 						updateMesssysnotice(data);
// 						break;	
//
//					default:
//				}
				// 页面右下角弹框显示(调用同级iframe中的函数)
				$("#message-count").html(data.order_count);// 订单待发货总数量
				if(data.order_sn != undefined){
					self.parent.frames['rightFrame'].initMessage("您有新的订单["+ data.order_sn +"]待发货处理,请及时处理");
				}
				self.parent.frames['rightFrame'].document.getElementById("order-count").innerText=data.order_count+"条";
				
				// 判断订单导入数据是否存在
				if(data.rowCount != undefined){
					self.parent.frames['rightFrame'].document.getElementById("product_exportExcel_rowCount").innerText="成功导出的数据条数 : " + data.rowCount + "条 , 耗时 : " + data.timeMillis + "秒";
				}
				// 判断订单导入完成标识是否存在
				if(data.exportFlag != undefined && data.exportFlag == 1 ){
					self.parent.frames['rightFrame'].document.getElementById("product_exportExcel_msg").innerText= "导出完成!";
					self.parent.frames['rightFrame'].changeDisabled();
				} 
			};

			ws.onerror = function () {

				triggerListener("onerror");
				logWithUserId(operator_no, "websocket连接异常!");
			};

			ws.onclose = function (closeEevent) {

				triggerListener("onclose");
				logWithUserId(operator_no, 'Connection closed. code=' + closeEevent.code);
				window.clearInterval(intervalId);
				setTimeout(function () {
					newWebSocket.call(window["CrhWebsocket"], operator_no, domain);
				}, 5000);
			};
		}
	}
})();

// 兼容
function isNotNull(obj) {
	if (obj != null) {
		return true;
	}
	return false;
}