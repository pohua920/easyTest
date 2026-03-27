/**
 * 公用JavaScript(兼容IE5/NN6)--平台中心组控制，项目组不得修改，否则後果自负
 * 如果发现有问题或需求，请通知提供者
 * 如果方法没有用private开头,则方法是公开的,且保证向後兼容.
 * 最新加的方法在最後面
 */
/** 全局变量bCancel; */
var bCancel = false;
var DATE_DELIMITER = "-";
var DB_INT_LENGTH = 64; //数据库位数,即整数长度，默认为64位
var MAX_INTEGER = Math.pow(2, DB_INT_LENGTH - 1) - 1;
var MIN_INTEGER = -Math.pow(2, DB_INT_LENGTH - 1);
var MAX_SMALLINT = Math.pow(2, DB_INT_LENGTH / 4 - 1) - 1;
var MIN_SMALLINT = -Math.pow(2, DB_INT_LENGTH / 4 - 1);
var VERBOSE = false; //显示所有明细，开发环境中可以调用setVerbose(true)
var FIELD_SEPARATOR = "_FIELD_SEPARATOR_"; //字段之间的分割符
var GROUP_SEPARATOR = "_GROUP_SEPARATOR_"; //一组代码之间的分割符

/**
 * 設定日期分割符，默认为'/'
 * @param delimiter 日期分割符
 */
function setDateDelimiter(delimiter) {
	DATE_DELIMITER = delimiter;
}
/**
 * 設定数据库整数长度，默认为64
 * @param len 整数长度
 */
function setDBIntLength(len) {
	DB_INT_LENGTH = len;
	MAX_INTEGER = Math.pow(2, DB_INT_LENGTH - 1) - 1;
	MIN_INTEGER = -Math.pow(2, DB_INT_LENGTH - 1);
	MAX_SMALLINT = Math.pow(2, DB_INT_LENGTH / 4 - 1) - 1;
	MIN_SMALLINT = -Math.pow(2, DB_INT_LENGTH / 4 - 1);
}

/**
 * 設定是否显示明细，默认为不显示
 * @param verbose 日期分割符
 */
function setVerbose(verbose) {
	VERBOSE = verbose;
}

/**
 * 检查是否显示明细
 * @return 是否显示明细
 */
function isVerbose() {
	return VERBOSE;
}

/**
 * 列印日志信息
 */
function log(value) {
	if (isVerbose()) {
		//add print liudaoping 2013-04-15
        //alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
        return false;
		window.status = value;
	}
}

/**
 * 判断客户端浏览器是否为Netscape
 * @return 客户端浏览器为Netscape则返回true,否则返回false;
 */
function isNetscape() {
	if (navigator.appName == "Netscape") {
		return true;
	} else {
		return false;
	}
}

/**
 * 得到传入element在Document中的name相同的elements中的顺序(从1开始)
 * @param field element
 * @return 传入element在Document中的name相同的elements中的顺序(从1开始)
 */
function getElementOrder(field) {
	var i = 0;
	var order = 0;
	var elements = document.getElementsByName(field.name);
	var elementsCount = elements.length;
	for (i = 0; i < elementsCount; i++) {
		order++;
		if (elements[i] == field) {
			break;
		}
	}
	return order;
}

/**
 * 传入element是否是Document中的name相同的elements中的第0个
 * @param field element
 * @return 是返回true，否则返回false
 */
function isFirstElement(field) {
	var elements = document.getElementsByName(field.name);
	if (elements[0] == field) {
		return true;
	} else {
		return false;
	}
}

/**
 * 查找在Document中的element的name属性等如传入值的element个数，没有则返回0
 * @param fieldName 元素名称
 * @return 在Document中的element的name属性等如传入值的element个数
 */
function getElementCount(fieldName) {
	var count = 0;
	count = document.getElementsByName(fieldName).length;
	return count;
}

/**
 * 得到字符串的字节长度
 * @param value 字符串
 * @return 字符串的字节长度
 */
function getByteLength(value) {
	var str;
	var count = 0;
	var valueCount = value.length;
	for (var i = 0; i < valueCount; i++) {
		str = escape(value.charAt(i));
		if (str.length == 6) {
			count = count + 2;
		} else {
			count = count + 1;
		}
	}

	return count;
}

/**
 * 得到Table的所有元素
 * @param tableId 表名称
 * @return table的所有元素
 */
function getTableElements(tableId) {
	var i = 0;
	var elements = new Array();
	var tempElements = null;
	var tbody;
	var index = 0;
	var tbodies = document.getElementById(tableId).tBodies;
	var tbodiesCount = tbodies.length;
	var tempElementsCount = 0;
	for (i = 0; i < tbodiesCount; i++) {
		tbody = tbodies.item(i);
		tempElements = tbody.getElementsByTagName("INPUT"); //加入INPUT域
		tempElementsCount = tempElements.length;
		for (var j = 0; j < tempElementsCount; j++) {
			elements[index++] = tempElements[j];
		}

		tempElements = tbody.getElementsByTagName("SELECT"); //加入SELECT域
		tempElementsCount = tempElements.length;
		for (var j = 0; j < tempElementsCount; j++) {
			elements[index++] = tempElements[j];
		}

		tempElements = tbody.getElementsByTagName("TEXTAREA"); //加入TEXTAREA域
		tempElementsCount = tempElements.length;
		for (var j = 0; j < tempElementsCount; j++) {
			elements[index++] = tempElements[j];
		}
	}
	return elements;
}

/**
 * 去掉字符串头空格
 * @param value 传人字符串
 * @return 去掉头空格後的字符串
 */
function leftTrim(value) {
	var re = /^\s*/;
	if (value == null) {
		return null;
	}
	return value.replace(re, "");
}

/**
 * 去掉字符串尾空格
 * @param value 传人字符串
 * @return 去掉尾空格後的字符串
 */
function rightTrim(value) {
	var re = /\s*$/;
	if (value == null) {
		return null;
	}
	return value.replace(re, "");
}

/**
 * 去掉字符串头尾空格
 * @param value 传人字符串
 * @return 去掉头尾空格後的字符串
 */
function trim(value) {
	return leftTrim(rightTrim(value));
}


/**
 * 正则表达式测试
 * @param source 传人字符串
 * @param re 正则表达式
 * @return 正则表达式测试结果
 */
function regExpTest(resource, re) {
	var result = false;

	if (resource == null || resource == "") {
		return false;
	}
	if (resource == re.exec(resource)) {
		result = true;
	}
	return result;
}

/**
 * 替换字符串函数
 * @param str 原串
 * @param strFind 查找串
 * @param strReplaceWith 替换串
 * @return 返回替换後的字符串
 */
function replace(str, strFind, strReplaceWith) {
	var strReturn;
	var re = new RegExp(strFind, "g");
	if (str == null) {
		return null;
	}
	strReturn = str.replace(re, strReplaceWith);
	return strReturn;
}

/**
 * 检查输入域是否为空
 * @param field 输入域
 * @return 如果输入域的值为null或空，则返回true，否则返回false
 */
function isEmptyField(field) {
	if (field.value == null || trim(field.value) == "") {
		return true;
	}
	return false;
}

//Note:2004-08-05  以下三个方法由再保项目组提供，等待实践检验

/**
 * 将千分号分割的字符串转换为对应字符串
 * @param strValue：格式化字符串
 * @return 去掉逗号的字符串
 */
function formatNumberToString(strValue) {
	strValue = trim(strValue);
	strValue = replace(strValue, ",", "");
	return strValue;
}

/**
 * 将千分号分割的字符串转换为对应double型数值
 * @param strValue：格式化字符串
 * @return 对应double型数值
 */
function formatNumberToDouble(strValue) {
	var dblValue = 0;
	strValue = formatNumberToString(strValue);
	dblValue = parseFloat(strValue);
	if (isNaN(dblValue)) {
		dblValue = 0;
	}
	return dblValue;
}

/**
 * This code fixes:
 * the 2.4999999999999999999 when you really expected 2.5 BUG and...
 * the 3.0000000000000000001 when you were expecting 3 BUG.
 * I can't believe someone would release a math library that works this way.
 *
 * You will probably want to change this to do what you need but
 * I'm sure you get the idea.
 * @param cellStr
 * @return 修复後的字符串
 */
function fixNumber(cellStr) {
	if (parseFloat(cellStr) == 0) {
		return '0';
	}
	if (cellStr.indexOf('.') == -1) {
		return cellStr;
	}

	var x = parseFloat(cellStr);
	x += (parseFloat(cellStr) < 0) ? -0.000000005 : 0.000000005;

	// Chop the string to 4 decimal places.
	cellStr = "" + x;
	cellStr = cellStr.substring(0, 5 + cellStr.indexOf('.'));

	// Remove trailing zeros beyond the decimal point.
	cellStrArr = cellStr.split("");

	for (k = cellStrArr.length - 1; k > 0 && cellStrArr[k] == '0'; --k)
		cellStrArr[k] = 'X';

	cellStr = "";
	for (k = 0; k < cellStrArr.length && cellStrArr[k] != 'X'; ++k)
		cellStr += cellStrArr[k];

	return cellStr;
}

/**
 * 設定元素获取焦点，設定失败时不会出错
 * @since 2004-10-28
 */
function setFocus(field) {
	if (field.type != "hidden") {
		try {
			field.focus();
		} catch (E) {}
	}
}

/**
 * 显示信息提示
 * @since 2004-10-28
 */
function showMessage(message) {
	alert("系統訊息:\r\n" + message);
}

/**
 * 控制一排checkbox,常用於查询结果中选择所有选择框,通常加在onpropertychange方法上
 * 例:<input type=checkbox name=selectButton onpropertychange="boundCheckBox(this,fm.checkboxSelect)" >
 * @since 2004-11-22
 */
function boundCheckBox(controlField, checkBoxField) {
	var count = 0;
	try {
		count = checkBoxField.length;
	} catch (E) {}
	if (isNaN(count)) {
		checkBoxField.checked = controlField.checked;
	} else {
		for (var i = 0; i < count; i++) {
			checkBoxField[i].checked = controlField.checked;
		}
	}
}

/**
 * 查找元素在Form中的顺序，没有则返回-1
 * @since 2004-11-22
 */
function getElementIndexInForm(form, field) {
	var intElementIndex = -1;
	var elementsCount = form.elements.length;
	for (var i = 0; i < elementsCount; i++) //查找fm里的元素
	{
		if (form.elements[i] == field) {
			intElementIndex = i;
			break;
		}
	}
	return intElementIndex;
}

/**
 * 設定元素选择内容，設定失败时不会出错
 * @since 2004-11-30
 */
function setSelect(field) {
	if (field.type != "hidden") {
		try {
			field.select();
		} catch (E) {}
	}
}
/**
 * 是否是IE6
 * @since 2004-12-07
 * @return 是返回ture，否则返回false
 */
function isIE6() {
	if (navigator.appVersion.indexOf("MSIE 6") > -1) {
		return true;
	} else {
		return false;
	}
}

/**
 * 绑定值
 * @since 2004-12-08
 */
function bindField(source, target) {
	target.value = source.value;
}

/**
 * @param str 填充字符/字符串
 * @param times 填充次数
 * @return 填充後的字符串
 * @since 2004-12-12
 */
function newString(str, times) {
	var value = "";
	for (var i = 0; i < times; i++) {
		value += str;
	}
	return value;
}

/**
 * 除了传入的对象外，其他都列印
 * @param obj 传入的对象
 * @since 2004-12-29
 */
function printExcept(obj) {
	obj.style.display = "none";
	//add print liudaoping 2013-04-15
    //alert("【列印】功能屬於客制化需求，暫未開發，請知悉！");
    return false;
	window.print();
	obj.style.display = "";
}

/**
 * 提交链接
 * @param form 传入的form对象
 * @param action 传入的action对象,通常为连接
 * @param target 目标
 * @since 2005-03-01
 */
function postAction(form, action, target) {
	var oldTarget = form.target;
	if (target != null) {
		form.target = target;
	}
	form.action = action;
	form.submit();
	if (target != null) {
		form.target = oldTarget;
	}
	return true;
}

/**
 * 如果确认，则提交链接
 * @param form 传入的form对象
 * @param action 传入的action对象,通常为连接
 * @param message 显示的消息
 * @param target 目标
 * @since 2005-03-02
 */
function postActionWithConfirm(form, action, message, target) {
	if (confirm(message)) {
		var oldTarget = form.target;
		if (target != null) {
			form.target = target;
		}
		form.action = action;
		form.submit();
		if (target != null) {
			form.target = oldTarget;
		}
		return true;
	} else {
		return false;
	}
}


/**
 * 给所有的text,textarea設定为readonly,select-one变成只保留当前选项
 * @param form 传入的form对象
 * @since 2005-08-29
 */
function setFormDisabled(form) {
	if (form == null) {
		form = fm;
	}
	var element;
	for (var i = 0; i < form.elements.length; i++) {
		element = form.elements[i];
		if (element.type == "hidden") { //不处理hidden域
			continue;
		}
		if (element.name.indexOf("buttonControl") == 0) { //忽略控制按钮，如查看明细等
			continue;
		}
		if (element.name == "buttonSubmit") { //隐藏提交按钮
			element.style.display = "none";
			continue;
		}
		element.disabled = true;
	}
}
/**
 * 返回第一个同名元素的值
 * @param fieldName 传入的字段名
 * @since 2005-08-29 
 */
function getFirstElementValue(fieldName) {
	var value = "";
	if (getElementCount(fieldName) > 0) {
		var field = document.getElementsByName(fieldName)[0];
		value = field.value;
	}
	return value;
}

/**
 * 打开一个带名字的窗口
 * @param urlString 地址
 * @param windowName 窗口名称
 * @param optionString 选项字符串
 * @since 2005-12-30
 */
function openWindow(urlString, windowName, optionString) {
	var newWindow = window.open(urlString, windowName, optionString);
	try {
		newWindow.focus();
	} catch (E) {}
	return newWindow;
}

/**
 * 拷贝指定对象到EXCEL
 * @object object的名称
 * @since 2005-12-31
 */
function copyObjectToExcel(object) {
	var oXL;
	try {
		oXL = GetObject("", "Excel.Application");
	} catch (E) {
		try {
			oXL = new ActiveXObject("Excel.Application");
		} catch (E2) {
			showMessage("请确认:\n1.本机安装了Excel軟體\n2.Internet选项中的安全設定\"对没有註記为安全的ActiveX进行初始化和脚本运行\"設定为启用");
			return;
		}
	}

	var oWB = oXL.Workbooks.Add();
	var oSheet = oWB.ActiveSheet;
	var sel = document.body.createTextRange();
	sel.moveToElementText(object);
	sel.select();
	sel.execCommand("Copy");
	oSheet.Paste();
	sel.moveToPoint(1, 0);
	sel.select();
	oXL.Visible = true;
}

/**
 * 拷贝指定结果列表对象到EXCEL
 * @table 结果表的名称
 * @since 2005-12-31
 */
function copyResultDataToExcel(table) {
	var oXL;
	try {
		oXL = GetObject("", "Excel.Application");
	} catch (E) {
		try {
			oXL = new ActiveXObject("Excel.Application");
		} catch (E2) {
			showMessage("请确认:\n1.本机安装了Excel軟體\n2.Internet选项中的安全設定\"对没有註記为安全的ActiveX进行初始化和脚本运行\"設定为启用");
			return;
		}
	}

	var oWB = oXL.Workbooks.Add();
	var oSheet = oWB.ActiveSheet;
	var sel = document.body.createTextRange();
	sel.moveToElementText(table.tHead.rows(0));
	sel.select();
	sel.execCommand("Copy");
	oSheet.Range("A1").select();
	oSheet.Paste();
	sel.moveToElementText(table.tBodies(0));
	sel.select();
	sel.execCommand("Copy");
	oSheet.Range("A2").select();
	oSheet.Paste();
	sel.moveToPoint(1, 0);
	sel.select();
	oXL.Visible = true;
}

/**
 * 导出指定结果列表对象到EXCEL(只保留数字)
 * @table 结果表的名称
 * @since 2005-12-31
 */
function exportResultDataToExcel(table) {
	var oXL;
	try {
		oXL = GetObject("", "Excel.Application");
	} catch (E) {
		try {
			oXL = new ActiveXObject("Excel.Application");
		} catch (E2) {
			showMessage("请确认:\n1.本机安装了Excel軟體\n2.Internet选项中的安全設定\"对没有註記为安全的ActiveX进行初始化和脚本运行\"設定为启用");
			return;
		}
	}

	var oWB = oXL.Workbooks.Add();
	var oSheet = oWB.ActiveSheet;
	var Lenr = 1;
	var maxColumn = 0; //最大Column号，从0开始
	for (var i = 0; i < Lenr; i++) {
		var Lenc = table.tHead.rows(i).cells.length;
		for (j = 0; j < Lenc; j++) {
			if (maxColumn < j) {
				maxColumn = j;
			}
			oSheet.Columns(j + 1).EntireColumn.NumberFormatLocal = "@";
			oSheet.Cells(i + 1, j + 1).value = table.tHead.rows(i).cells(j).innerText;
		}
	}

	Lenr = table.tBodies(0).rows.length;
	for (var i = 0; i < Lenr; i++) {
		var Lenc = table.tBodies(0).rows(i).cells.length;
		var j = 0;
		var value = table.tBodies(0).rows(i).cells(j).innerText;
		var pos = value.indexOf(" ");
		if (pos > -1) {
			value = trim(value.substring(pos));
		}
		oSheet.Cells(i + 2, j + 1).value = value;
		for (j = 1; j < Lenc; j++) {
			oSheet.Cells(i + 2, j + 1).value = table.tBodies(0).rows(i).cells(j).innerText;
		}
	}
	for (var i = 0; i < maxColumn; i++) {
		oSheet.Columns(i + 1).EntireColumn.AutoFit;
	}
	oXL.Visible = true;
}

/**
 * 設定所有同名元素的值
 * @param fieldName 传入的字段
 * @since 2005-08-29
 */
function setSameElementValue(field) {
	if (getElementCount(field.name) > 1) {
		var fields = document.getElementsByName(field.name);
		for (var i = 0; i < fields.length; i++) {
			fields[i].value = field.value;
		}
	}
}

function readCookie(name) {
	var cookieValue = "";
	var search = name + "=";
	if (document.cookie.length > 0) {
		offset = document.cookie.indexOf(search);
		if (offset != -1) {
			offset += search.length;
			end = document.cookie.indexOf(";", offset);
			if (end == -1) {
				end = document.cookie.length;
			}
			cookieValue = unescape(document.cookie.substring(offset, end));
		}
	}
	return cookieValue;
}

function writeCookie(name, value, hours) {
	var expire = "";
	if (hours != null) {
		expire = new Date((new Date()).getTime() + hours * 3600000);
		expire = ";  expires=" + expire.toGMTString();
	}
	document.cookie = name + "=" + escape(value) + expire;
}
/**
 * Cross-browser XMLHttpRequest instantiation.
 */
if (typeof XMLHttpRequest == "undefined") {
	XMLHttpRequest = function () {
		var msxmls = ["MSXML3", "MSXML2", "Microsoft"];
		for (var i = 0; i < msxmls.length; i++) {
			try {
				return new ActiveXObject(msxmls[i] + ".XMLHTTP");
			} catch (e) {}
		}
		throw new Error("No XML component installed!");
	};
}

function createXMLHttpRequest() {
	try {
		// Attempt to create it "the Mozilla way" 
		if (window.XMLHttpRequest) {
			return new XMLHttpRequest();
		}
		// Guess not - now the IE way
		if (window.ActiveXObject) {
			return new ActiveXObject(getXMLPrefix() + ".XmlHttp");
		}
	} catch (ex) {}
	return false;
}


//a xml http request variable.
var xmlRequest;
/**
 * request server mode sync
 */
function syncRequest(url) {
	xmlRequest = createXMLHttpRequest();
	xmlRequest.onreadystatechange = processStateChange;
	try {
		xmlRequest.open("POST", url, false);
	} catch (e) {
		alert(e);
	}
	xmlRequest.send(null);
}

function asyncRequest(url) {
	xmlRequest = createXMLHttpRequest();
	xmlRequest.onreadystatechange = processStateChange;
	try {
		xmlRequest.open("POST", url, true);
	} catch (e) {
		alert(e);
	}
	xmlRequest.send(null);
}

function processStateChange() {
	if (xmlRequest.readyState == 4) { // Complete
		if (xmlRequest.status == 200) { // OK response
		} else {
			alert("Problem: " + xmlRequest.statusText);
		}
	}
}