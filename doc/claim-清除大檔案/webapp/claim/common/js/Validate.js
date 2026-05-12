/***
 * 理赔系统校验初始化配置
 * 
 */
/***
 * 调用ValidateData.js的getSchemaColumn函数获取配置的校验对象
 */
var frame_fraTitle = findIframe("fraTitle");
function getSchemaColumn(name) {
	if(name != undefined && frame_fraTitle != undefined){
		var schemaColumn = frame_fraTitle.parent.getSchemaColumn(name);
		if(schemaColumn != undefined){
			return schemaColumn;
		}
	}
	return null;
}
/**
 * 初始化整个页面的方法
 * 设置域的maxLenght属性.
 */
function initPage() {
	$(":input").each(function(){
		initElement(this);
	});
}

/**
 * 默认的校验Form的方法
 * @param form form
 * @param mulLineTableIdList 多行输入域的数据页的table ID,被克隆复制的那个。如果是多个，中间用逗号分隔。
 * @param skipFieldPrefixs 字段名符合上述前缀的即不进行校验。如果是多个，中间用逗号分隔。
 * @return 通过true/不通过false
 */
function validateForm(form, mulLineTableIdList, skipFieldPrefixs) {
	var formid = form.id;
	if(form.id==undefined || form.id==""){
		if(form.name !=undefined && form.name!=""){
			form.id = form.name;
		}else{
			form.id = "validateForm";
		}
	}
	var $elements = $("#"+form.id +" :input").not(":button,:disabled,:image");
	if(mulLineTableIdList != undefined && mulLineTableIdList !=null){
		var tableIds = mulLineTableIdList.split(","); 
		if(tableIds.length > 0){//过滤不需要校验的
			for(var i = 0;i < tableIds.length;i++){
				$elements = $elements.not("#"+tableIds[i]+" :input");
			}
		}
	}
	if(skipFieldPrefixs != undefined && skipFieldPrefixs !=null){
		var prefixs = skipFieldPrefixs.split(","); 
		if(prefixs.length > 0){//过滤不需要校验的
			for(var i = 0;i < prefixs.length;i++){
				$elements = $elements.not(":input[name^="+prefixs[i]+"]");
			}
		}
	}
	var message = "";
	var focusField = null;
	$elements.each(function(){
		var schemaColumn = getSchemaColumn(this.name);
		if(schemaColumn != null && schemaColumn != undefined){
			var checkRequire = checkRequired(this,schemaColumn);
			if(typeof(checkRequire) == "boolean"){
				if(checkRequire){
					var checkFlag = checkDataType(this,schemaColumn);
					if(typeof(checkFlag) != "boolean"){
						message += checkFlag + "\n";
					}
				}
			}else{
				message += checkRequire + "\n";
			}
			if(focusField ==null && message != ""){
				focusField = this;
			}
		}
	});
	if(message == ""){
		return true;
	}else{
		alert(message);
		if(focusField!=null && $(focusField).is(":visible")){
			try {
				focusField.focus();
			} catch (e){}
		}
		return false;	
	}
}

/**
 * 校验栏位的必输性
 * @param field
 * @returns {boolean}
 */
function checkRequired(field,schemaColumn){
	if (field.type == 'text' || field.type == 'file' || field.type == 'textarea' ||
			field.type == 'select-one' || field.type == 'password') {
		if (!schemaColumn.allowNulls) {//存在不为空校验
			if($.trim($(field).val())==""){
				return schemaColumn.desc + i18n.js.notAllowedEmpty;//不允许为空
			}
		}
	}
	return false;
}

/***
 * 检查数据的录入类型是否正确
 * @returns
 */
function checkDataType(field,schemaColumn){
	var property = schemaColumn.dataType.toLowerCase();
	if (property == "text") { // text类型无需校验
		return true;
	} else if (property == ("byte")) { // byte类型无需校验
		return true;
	} else if (property == "integer") {
		return isValidateIntegerField(field, schemaColumn);
	} else if (property == "smallint") {
		return isValidateSmallintField(field, schemaColumn);
	} else if (property == "date" ) {// 现在日期全部用控件了，可以只做非空检查即可
		return true;
	} else if (property.indexOf("datetime") != -1) {
		return isValidateDateField(property,field, schemaColumn);
	} else if (property.indexOf("varchar") != -1) {
		return isValidateVarcharField(field, schemaColumn);
	} else if (property.indexOf("char") != -1) {
		return isValidateCharField(field, schemaColumn);
	} else if (property.indexOf("dec") != -1) {
		return isValidateDecimalField(field, schemaColumn);
	} else if (property.indexOf("float") != -1) {
		return isValidateDecimalField(field, schemaColumn);
	} else if (property.indexOf("number") != -1) {
		return isValidateDecimalField(field, schemaColumn);
	} else if (property.indexOf("numeric") != -1) {
		return isValidateDecimalField(field, schemaColumn);
	}
	return true;
}
/**
 * 检查是否是合法的Integer类型字段
 * @param field field
 * @param schemaColumn schemaColumn
 * @return 是返回true(boolean),否返回 提示讯息(string)
 */
function isValidateIntegerField(field, schemaColumn) {
	var strValue = trim(field.value);
	if (strValue.length == 0){
		field.value = "0";
		return true;
	}
	field.value = strValue;
	if (regExpTest(strValue, /[+|\-]?\d+/g)) {
		var value = parseInt(strValue, 10);
		if (!isNaN(value) && value >= MIN_INTEGER && value <= MAX_INTEGER ) {
			return true;
		}
	}
	var typeDesc = "整數";
	return "\""+schemaColumn.desc + "\"" +i18n.js.must + typeDesc;// false
}
/**
 * 检查是否是合法的SmallInt类型字段
 * @param field
 * @param schemaColumn schemaColumn
 * @return 是返回true(boolean),否返回 提示讯息(string)
 */
function isValidateSmallintField(field, schemaColumn) {
	var result = isValidateIntegerField(field, schemaColumn);//先校验是否整数
	if (typeof(result) != "boolean") {
		return result;
	}
	var value = parseInt(field.value, 10);
	if (value > MAX_SMALLINT || value < MIN_SMALLINT) {
		var typeDesc = "小整數";
		return "\""+schemaColumn.desc + "\"" +i18n.js.must + typeDesc;//false
	}
	return true;
}

/**
 * 检查是否是合法的varchar类型字段
 * @param field field
 * @param schemaColumn schemaColumn
 * @return 是返回true(boolean),否返回 提示讯息(string)
 */
function isValidateVarcharField(field, schemaColumn) {
	var maxLength = 0;
	var minSpace = 0;
	var temp = schemaColumn.dataType;
	var start = schemaColumn.dataType.indexOf("(", 7); //"("的位置
	var middle = schemaColumn.dataType.indexOf(",", start); //","的位置
	var end = schemaColumn.dataType.indexOf(")", start); //")"的位置
	if (middle == -1) { //没有设置最小值
		maxLength = parseInt(schemaColumn.dataType.substring(start + 1, end), 10);
		minSpace = 0;
	} else {
		maxLength = parseInt(schemaColumn.dataType.substring(start + 1, middle), 10);
		minSpace = parseInt(schemaColumn.dataType.substring(middle + 1, end), 10);
	}
	if (getByteLength(field.value) > maxLength || getByteLength(field.value) < minSpace) {
		var typeDesc = "字符串";
		typeDesc += "(最大長度為" + maxLength;
		if (minSpace > 0) {
			typeDesc += ",最小長度為" + minSpace;
		}
		typeDesc += ")"
		return "\""+schemaColumn.desc + "\"" +i18n.js.must + typeDesc;//false
	}
	return true;
}

/**
 * 检查是否是合法的char类型字段
 * @param field field
 * @param schemaColumn schemaColumn
 * @return 是返回true(boolean),否返回 提示讯息(string)
 */
function isValidateCharField(field, schemaColumn) {
	var temp = schemaColumn.dataType;
	var start = temp.indexOf("(", 4); //"("的位置
	var end = temp.indexOf(")", start); //")"的位置
	var maxLength = parseInt(schemaColumn.dataType.substring(start + 1, end), 10);
	if (getByteLength(field.value) > maxLength) {
		var typeDesc = "字符串";
		typeDesc += "(最大長度為" + maxLength + ")"
		return "\""+schemaColumn.desc + "\"" +i18n.js.must + typeDesc;//false
	}
	return true;
}

/**
 * 检查是否是decimal类型字段
 * @param field field
 * @param schemaColumn schemaColumn
 * @return 是返回true(boolean),否返回 提示讯息(string)
 */
function isValidateDecimalField(field, schemaColumn) {
	var maxLength = 0;
	var minSpace = 0;
	var temp = schemaColumn.dataType;
	var start = temp.indexOf("("); //"("的位置
	var middle = temp.indexOf(","); //","的位置
	var end = temp.indexOf(")"); //")"的位置
	if (middle == -1) { //没有设置最小值
		maxLength = parseInt(temp.substring(start + 1, end), 10);
		minSpace = 0;
	} else {
		maxLength = parseInt(temp.substring(start + 1, middle), 10);//整数位
		minSpace = parseInt(temp.substring(middle + 1, end), 10);//小数位
	}
	var strValue = trim(field.value);
	if (strValue == "") {
		strValue = "0";
	}
	field.value = strValue;
	var typeDesc = "數值"
	if (regExpTest(strValue, /^[+|\-]?\d*[.]?\d*/g) == false) {
		return "\""+schemaColumn.desc + "\"" +i18n.js.must + typeDesc;//false
	}
	middle = strValue.indexOf("."); //"."的位置//检查精度
	if (middle > -1) {//有精度
		if (minSpace < strValue.substr(middle + 1).length) {
			typeDesc += "(精度為" + minSpace+")";
			return "\""+schemaColumn.desc + "\"" +i18n.js.must + typeDesc;//false
		}
	}
	//检查最大长度
	var addLength = 0;
	if (strValue.indexOf("+") == 0){//整数
		addLength++;
	}
	if (strValue.indexOf("-") == 0){//负值
		addLength++;
	}
	if (strValue.indexOf(".") == 0) {
		addLength--;
	} else if (strValue.indexOf(".") > 0) {
		addLength++;
	}
	if (getByteLength(strValue) > (maxLength + addLength)) {
		typeDesc += "(有效位數為" + maxLength + ")";
		return "\""+schemaColumn.desc + "\"" +i18n.js.must + typeDesc;//false
	}
	return true;
}
/**
 * 日期数据校验
 * @param property property
 * @param field field
 * @param schemaColumn schemaColumn
 * @return 是返回true(boolean),否返回 提示讯息(string)
 */
function isValidateDateField(property,field, schemaColumn){
	var strValue = field.value;
	if(property == "datetimehourtohour"){//小时
		var reg =new RegExp ("^((2[0-3])|([0-1]?\d))$"); 
		var r = reg.test(strValue); 
		if(reg.test(strValue)){
			var tempValue = parseInt(strValue,10);
			if(tempValue < 0 || tempValue >= 24){
				return schemaColumn.desc + "範圍為[0,24)";//false
			}
			return true;
		}else{
			return schemaColumn.desc + "格式不正確";//false
		}
	}
	if(property == "datetimeminutetominute"){//分鐘
		var reg =new RegExp ("^[0-5]?\d$"); 
		var r = reg.test(strValue); 
		if(reg.test(strValue)){
			var tempValue = parseInt(strValue,10);
			if(tempValue < 0 || tempValue >= 60){
				return schemaColumn.desc + "範圍為[0,60)";//false
			}
			return true;
		}else{
			return schemaColumn.desc + "格式不正確";//false
		}
	}
	return true;
}

/**
 * 处理单个元素的校验
 * @param element
 */
function initElement(element){
	if (element.name== undefined || element.name == "" || element.type == 'hidden' || element.type == 'button' 
		|| element.type == 'select-one' || element.type == 'submit') {
	}else{
		var schemaColumn = getSchemaColumn(element.name);//获取初始化元素的校验规则对象
		if (schemaColumn != null && schemaColumn != undefined) {
			if (element.type == 'text' || element.type == 'textarea'
					|| element.type == 'password') {
				var maxLength = getDataTypeMaxLength(schemaColumn.dataType.toLowerCase());
				if (maxLength > 0) {
					element.maxLength = maxLength;
				}
			}
			preventInputMethod(element,schemaColumn);//判断是否需要屏蔽输入法
			$(element).bind("keypress",keypressHandler);
		}
		preventBackspace(element);
	}
}

/***
 * 屏蔽输入法
 * @param element
 */
function preventInputMethod(element,schemaColumn){
	var dataType = schemaColumn.dataType.toLowerCase();
	if(dataType == "integer" || dataType == "smallint" 
		|| dataType == "date" || dataType == "number" || dataType =="numeric" 
		|| dataType.indexOf("dec") != -1 || dataType.indexOf("float") != -1){//数值、日期类型
		$(element).css("ime-mode","disabled");//屏蔽输入法
	}
}

/***
 * readOnly 目标屏蔽backspace
 * @param element
 */
function preventBackspace(element){
	$(element).keydown(function(e){
		//页面处理中会有js改变input域的readonly属性，放到keydown事件里面判断。
		if($(this).is(":input[readonly='readonly']")==true&&e.keyCode==8){
			//阻止readOnly文本域按下backspace鍵浏览器回退等
			e.preventDefault();
		}
	});
}
/***
 * 给校验对象绑定的onkeyPress函数，
 * @param evt
 * @returns
 */
function keypressHandler(evt) {
	var testValue = true;
	evt = (evt) ? evt : window.event;
	var schemaColumn = getSchemaColumn(this.name);
	if (schemaColumn == null) {
		testValue = true;
	} else {
		var dataType = schemaColumn.dataType.toLowerCase();
		if (dataType == "integer" || dataType == "smallint" 
			|| dataType == "number" || dataType == "numeric" 
			|| dataType == "date" || dataType.indexOf("dec") != -1 
			|| dataType.indexOf("float") != -1 
			|| dataType.indexOf("datetime") != -1) {//判断整数
			testValue = isValidateInput(evt,dataType);
		}  else if (dataType.indexOf("varchar") != -1) {
			testValue = true;
		} else if (dataType.indexOf("char") != -1) {
			testValue = true;
		} else {
			testValue = true;
		}
		if (testValue != true) {
			return false;
		}
	}
	return testValue;
}
/***
 * 获取schemaColumn对象中dataType描述的字段长
 * @param dataType
 * @returns {Number}
 */
function getDataTypeMaxLength(dataType) {
	var maxLength = 0;
	var minSpace = 0;
	var start = dataType.indexOf("("); //"("的位置
	var middle = dataType.indexOf(","); //","的位置
	var end = dataType.indexOf(")"); //")"的位置
	if (start > 0) {//存在长度限制，用逗号隔开的则是区间
		if (middle == -1) { //没有设置最小值
			maxLength = parseInt(dataType.substring(start + 1, end), 10);
			minSpace = 0;
		} else {
			maxLength = parseInt(dataType.substring(start + 1, middle), 10);
			minSpace = parseInt(dataType.substring(middle + 1, end), 10);
		}
	}
	if (dataType == "integer") {
		maxLength = DB_INT_LENGTH;
	} else if (dataType == "smallint") {
		maxLength = DB_INT_LENGTH / 4;
	} else if (dataType == "date") {
		maxLength = 10;
	}
	return maxLength;
}

/**
 * 是否是合法的dataType类型输入
 * 是返回true,否返回false.
 */
function isValidateInput(e,dataType) {
	var value;
	if (isNetscape()) {
		value = String.fromCharCode(e.charCode);
	} else {
		value = String.fromCharCode(e.keyCode);
	}
	if(dataType == "integer" || dataType == "smallint" || dataType.indexOf("datetime") != -1 ){//是否是合法的integer类型输入,即("0-9","+",'-")
		return regExpTest(e.srcElement.value+value, /^[-+]?[0-9]*$/);//regExpTest(value, /[+|\-|0-9]{1}/);/
	} else if(dataType == "date"){//是否是合法的date类型输入,即("0-9","/")
		return regExpTest(value, /[\/|0-9]| |-|:{1}/);
	} else if(dataType == "number" || dataType == "numeric" || dataType.indexOf("dec") != -1 || dataType.indexOf("float") != -1){//是否是合法的decimal类型输入,即("0-9",".","+",'-")
		return regExpTest(value, /[+|\-|\.|0-9]{1}/);
	}
	return false;
}