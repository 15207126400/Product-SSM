/**
 * Extend jQuery Function
 */
jQuery.extend({
	/**
	 * ajax 同步提交请求(post提交返回json)
	 * @param url 发送请求地址
	 * @param data 待发送 Key/value 参数(JSON对象)
	 * @param dataType 返回数据类型 html、json、jsonp、script或者text[默认json]
	 * @param showError 调用出错是否弹出错误信息[默认true]
	 * @returns 请求返回结果
	 */
	sysajax : function(url, data, dataType, showError) {
		// 默认值
		if (dataType == null)
			dataType = 'json';
		if (showError == null)
			showError = true;
		// 功能
		var result = null;
		$.ajax({
			url : url,
			data : data,
			async : false,
			type : 'POST',
			dataType : dataType,
			success : function(response) {
				result = response;
			},
			error : function(response) {
				if (showError) {
					alert('请求：\n' + url + '\n' + $.encode(data) + '\n\n返回：\n' + response.responseText);
				}
			}
		});
		return result;
	},
	/**
	 * 动态Form提交请求(post提交跳转页面)
	 * @param url 发送请求地址
	 * @param [data] 待发送 Key/value 参数(JSON对象)
	 * @param [validate] 表单提交前的验证方法
	 * @returns 请求返回结果
	 */
	postform : function(url, data, validate) {
		var form = $("<form action='" + url + "' method='post'></form>");
		if (data) {
			for ( var key in data) {
				var input = $("<input type='hidden' name='" + key + "'/>").val(data[key]);
				form.append(input);
			}
		}
		$('body').append(form);
		if (validate && typeof (validate) == 'function') {
			if (false == validate()) {
				form.remove();
				return;
			}
		}
		form.submit();
	},
	/**
	 * 把对象转换为字符串
	 * @param object 对象
	 * @returns 转换结果
	 */
	encode : function(object) {
		return (function() {
			var _this = this;
			if (_this == null || _this == undefined) {
				return "";
			}
			if (_this instanceof Function || _this instanceof RegExp) {
				return _this.toString();
			}
			if (_this instanceof String) {
				return '"' + _this.toString().replace(/\"/, "\\\"") + '"';
			}
			if (_this instanceof Boolean || _this instanceof Number) {
				return _this.toString();
			}
			if (_this instanceof Date) {
				return _this.getTime();
			}
			if (_this instanceof Object || _this instanceof Array) {
				var tempArr = new Array();
				if (_this instanceof Array) {
					for ( var i = 0; i < _this.length; i++) {
						tempArr.push(arguments.callee.call(_this[i]));
					}
				} else {
					for ( var fieldname in _this) {
						if (_this[fieldname] == null || _this[fieldname] == undefined) {
							tempArr.push(arguments.callee.call(fieldname) + ':' + '""');
							continue;
						}
						if (jQuery.type(_this[fieldname]) == 'function' || jQuery.type(_this[fieldname]) == 'regexp') {
							continue;
						}
						if (_this[fieldname] instanceof Object) {
							tempArr.push(arguments.callee.call(fieldname) + ':' + arguments.callee.call(_this[fieldname]));
							continue;
						}
						tempArr.push(arguments.callee.call(fieldname) + ':' + arguments.callee.call(_this[fieldname]));
					}
				}
				var retString = tempArr.join(';');
				if (_this instanceof Array) {
					retString = '[' + tempArr.join(',') + ']';
				} else {
					retString = '{' + tempArr.join(',') + '}';
				}
				return retString;
			} else {
				return _this.toString();
			}
		}).call(object);
	},
	/**
	 * 把字符串转换成对象
	 * @param string 字符串
	 * @returns 转换结果
	 */
	decode : function(string) {
		return jQuery.parseJSON(string);
	},
	/**
	 * 把对象进行URL编码
	 * @param object 对象
	 * @returns URL编码结果
	 */
	urlEncode : function(object) {
		var params = '';
		var index = 0;
		for ( var key in object) {
			try {
				if (index == 0)
					eval('params += "' + key + '=' + object[key] + '"');
				else
					eval('params += "&' + key + '=' + object[key] + '"');
				index++;
			} catch (e) {
				if (window.console && window.console.warn) {
					window.console.warn(new Date().toLocaleString() + '[jQueryExtend.js:urlEncode][errorCode:' + e.number + '][' + e.name + ':' + e.message + ']');
				}
			}
		}
		return encodeURI(params);
	},
	/**
	 * 休眠
	 * @param numberMillis 休眠时间(毫秒数)
	 */
	sleep : function(numberMillis) {
		var now = new Date();
		var exitTime = now.getTime() + numberMillis;
		while (true) {
			now = new Date();
			if (now.getTime() >= exitTime) {
				return;
			}
		}
	},
	/**
	 * @form from元素 获取from可用元素的值（）
	 */
	getValues : function(form) {
		var serValues = $(form).serializeArray();
		var values = {};
		for ( var i = 0; i < serValues.length; i++) {
			var name = serValues[i]['name'];
			var value = serValues[i]['value'];
			if (values[name] != undefined && values[name] != "" && values[name] != null) {
				values[name] = values[name] + "";
				if (values[name]) {
					values[name] = values[name] + "," + value;
				} else {
					values[name] = value;
				}
			} else {
				values[name] = value;
			}
		}
		return values;
	},
	/**
	 * 动态加载js、css路径
	 */
	includePath : '',
	/**
	 * 动态加载js、css文件
	 * @param file
	 */
	include : function(file) {
		var files = typeof file == "string" ? [ file ] : file;
		for ( var i = 0; i < files.length; i++) {
			var name = files[i].replace(/^\s|\s$/g, "");
			var att = name.split('.');
			var ext = att[att.length - 1].toLowerCase();
			var isCSS = ext == "css";
			var isVbs = ext == "vbs";
			var tag = isCSS ? "link" : "script";
			var attr = isCSS ? " type='text/css' rel='stylesheet' " : (isVbs ? " language='vbscript' type='text/vbscript' " : " language='javascript' type='text/javascript' ");
			var link = (isCSS ? "href" : "src") + "='" + $.includePath + name + "'";
			if ($(tag + "[" + link + "]").length == 0) {
				$('head').append("<" + tag + attr + link + "></" + tag + ">");
			}
		}
	}
});