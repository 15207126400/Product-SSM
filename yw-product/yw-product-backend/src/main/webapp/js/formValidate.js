/**
 * 表单校验，按valid属性中的顺序校验,NotBlank优先校验。 依赖yunw-dev-validate.js、yunw-dev-utils.js
 * @param formName 表单form的id
 * @returns {Boolean} 校验成功，返回true，否则返回false
 */
function formValidate(formName) {
	var validate = true;
	var defaultValueCallback=[];
	$("#" + formName + " :input").each(function() {
		//表单校验脚本问题修改，排除disabled状态的元素 张家浩(排除隐藏元素的校验)
		if($(this).attr("disabled") || ($(this).is(":hidden") && this.type != "checkbox")){
			return;
		}
		var thisArg = this;
		var element = $(this).val();
		var valid = $(this).attr("valid");
		var maxbytelength = $(this).attr("maxbytelength");
		var elementname = $(this).prev().html();
		// 对空值赋值，字符串trim去除空格保存
		if (this.localName=="textarea" || ($(this).attr("type") == "text" || $(this)[0].tagName=="SELECT") && (element != null && !(element instanceof Array))) {
			if (element.trim().length>0) {
				$(this).val(element.trim());
			} else {
				if(valid && valid.indexOf("Digital")!=-1){
					if($(this)[0].tagName=="SELECT"){
						defaultValueCallback.push(function(){
							$(thisArg).find("option:selected").val("0");
						});
					}else {
						defaultValueCallback.push(function(){
							$(thisArg).val("0");
						});
					}
				}else{
					if($(this)[0].tagName=="SELECT"){
						defaultValueCallback.push(function(){
							$(thisArg).find("option:selected").val(" ");
						});
					}else{
						defaultValueCallback.push(function(){
							$(thisArg).val(" ");
						});
					}

				}
			}
		}
		/*兼容iframe框架*/
        databaseCode = "3" || parent.databaseCode;
		databaseCode=parseInt(databaseCode);
		// 校验字符串字节数
		if (maxbytelength != undefined && maxbytelength != null && maxbytelength.trim() != "" && element != null) {
			var bytesCount = 0;
			for (var i = 0; i < element.length; i++) {
				var c = element.charAt(i);
				if (/^[\u0000-\u00ff]$/.test(c)) { // 匹配双字节
					bytesCount += 1;
				} else {
					bytesCount += databaseCode;      //56服务器gbk编码
				}
			}
			if (bytesCount > maxbytelength) {
				devActAfterShowDialog(elementname + "输入的内容超过了"+maxbytelength+"字节(一个汉字占"+databaseCode+"字节)", function() {
					thisArg.focus();
				});
				validate = false;
			}
		}
		if (valid == undefined || valid == null || valid.trim().length == 0) {
			return validate;
		}
		if ((valid.indexOf("NotBlank") != "-1") && (element == null || (!(element instanceof Array) && element.trim().length==0))) {
			devActAfterShowDialog($(this).attr("placeholder"), function() {
				thisArg.focus();
			});
			validate = false;
			return false;
		}
		// 检验checkbox多选框
		if (thisArg.type == "checkbox" && $("#" + formName + " :input:checkbox[name=\"" + thisArg.name + "\"]:checked").length == 0) {
			devActAfterShowDialog($(this).attr("placeholder"));
			validate = false;
			return false;
		}
		// 校验radio单选框
		if (thisArg.type == "radio" && $("#" + formName + " :input:radio[name=\"" + thisArg.name + "\"]:checked").length == 0) {
			devActAfterShowDialog($(this).attr("placeholder"));
			validate = false;
			return false;
		}
		var formArray = valid.split(",");
		formArray = formArray.filter(formArrayFilter);

		for (var i = 0; i < formArray.length; i++) {
			var executeFunc = formValidateMap[formArray[i]];
			if (executeFunc == undefined) {
				console.log("No corresponding function : " + formArray[i]);
				continue;
			}

			var result = executeFunc((element instanceof Array) ? (element) : (element.trim()), this);
			if (result != "success") {
				var errorTip = $(this).attr("errorTip");
				if ((errorTip != undefined) && (errorTip.trim().length > 0)) {
					devActAfterShowDialog(errorTip, function() {
						thisArg.focus();
					});
				} else {
					devActAfterShowDialog($(this).prev().html()+result, function() {
						thisArg.focus();
					});
				}
				validate = false;
				return false;
			}
		}
	});
	//若验证通过则加入默认值处理
	execdefaultValueCallback(validate,defaultValueCallback);
	return validate;
}

function execdefaultValueCallback(validate,defaultValueCallback){
	if(validate){
		for(var p in defaultValueCallback){
			defaultValueCallback[p]();
		}
	}
}


/**
 * decimal: "小数" float："整数或者浮点" Digital : "数字" Mobile : "手机号码" ValidateCode : "验证码" Name : "姓名" CID : "身份证号码" CIDaddr : "身份证地址" Address : "地址"
 * PostCode : "邮政编码" Email : "邮箱" Tel : "固定电话" Pwd : "密码" FixedLength : "固定长度" MinLength : "最小长度"
 */
var formValidateMap = {
	decimal	: devValidatedecimal,
	float : devValidatefloat,
	Digital : devValidateDigit,
	DigitalFloatNum : devValidateFloatNum,
	Mobile : devValidateMobile,
	ValidateCode : devValidateCode,
	Name : devValidateName,
	CID : devValidateCID,
	CIDaddr : devValidateCIDAddr,
	Address : devValidateAddress,
	PostCode : devValidatePostCode,
	Email : devValidateEmail,
	Tel : devValidateTel,
	Pwd : devValidatePwd,
	FixedLength : limitFixedLength,
	MinLength : limitMinLength,
	brokerCode:devValidateBrokerCode
};

function formArrayFilter(item, index, array) {
	return (item != "NotBlank");
}

/**
 * 检查输入长度为指定长度，maxlength属性是必须的
 * @param input
 * @param thisArg
 * @returns {String}
 */
function limitFixedLength(input, thisArg) {
	var maxlength = $(thisArg).attr("maxlength");
	if (input.trim().length != maxlength) {
		return "指定长度为" + maxlength;
	}
	return "success";
}

/**
 * 检查输入大于或等于最小长度，最小长度的限制定义在minlength属性中
 * @param input
 * @param thisArg
 * @returns {String}
 */
function limitMinLength(input, thisArg) {
	var minlength = $(thisArg).attr("minlength");
	if (input.trim().length < minlength) {
		return "最小长度为" + minlength;
	}
	return "success";
}
