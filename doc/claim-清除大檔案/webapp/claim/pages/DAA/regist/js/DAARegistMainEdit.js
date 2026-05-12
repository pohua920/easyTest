//检查日期输入域

function checkFullDate(field) {
	field.value = trim(field.value);
	var strValue = field.value;
	var desc = field.description;
	//如果description属性不存在，则用name属性
	if (desc == null)
		desc = field.name;
	if (strValue == "") {
		return false;
	}
	if (strValue.length != 8 && strValue.length != 10) {
		errorMessage("请输入合法的" + desc + "\n类型为日期，格式为YYYY-MM-DD 或者YYYYMMDD，不足的位要补0");
		field.focus();
		field.select();
		return false;
	}
	if (isNumeric(strValue)) {
		if (strValue.length == 8) {
			strValue = strValue.substring(0, 4) + DATE_DELIMITER + strValue.substring(4, 6) + DATE_DELIMITER + strValue.substring(6);
			field.value = strValue;
		} else {
			errorMessage("请输入合法的" + desc + "\n类型为日期，格式为YYYY-MM-DD 或者YYYYMMDD");
			field.value = "";
			field.focus();
			field.select();
			return false;
		}
	}
	if (!isDate(strValue, DATE_DELIMITER) && !isDate(strValue) || strValue.substring(0, 1) == "0") {
		errorMessage("请输入合法的" + desc + "\n类型为日期，格式为YYYY-MM-DD 或者YYYYMMDD");
		field.value = "";
		field.focus();
		field.select();
		return false;
	}
	return true;
}

//对输入域是否是数字的校验

function isNumeric(strValue) {
	var result = regExpTest(strValue, /\d*[.]?\d*/g);
	return result;
}

//RegExt Test

function regExpTest(source, re) {
	var result = false;

	if (source == null || source == "")
		return false;

	if (source == re.exec(source))
		result = true;

	return result;
}

//对输入域按键时的日期校验

function pressFullDate(e) {
	var value = String.fromCharCode(e.keyCode);
	if ((value >= 0 && value <= 9) || value == "/" || value == "-")
		return true;
	else
		return false;
}

//对输入域是否是日期的校验，splitChar参数缺省为"-"

function isDate(date, splitChar) {
	var charSplit = (splitChar == null ? "-" : splitChar);
	var strValue = date.split(charSplit);

	if (strValue.length != 3) return false;

	var intYear = parseInt(strValue[0], 10);
	var intMonth = parseInt(strValue[1], 10) - 1;
	var intDay = parseInt(strValue[2], 10);

	var dt = new Date(intYear, intMonth, intDay);
	if (dt.getFullYear() != intYear ||
		dt.getMonth() != intMonth ||
		dt.getDate() != intDay) {
		return false;
	}
	return true;
}

function changeLxr() {
	var prpLregistReportorName = fm.prpLregistReportorName.value; //備案人

	if (trim(fm.prpLregistLinkerName.value).length == 0) { //驾驶人姓名
		fm.prpLregistLinkerName.value = prpLregistReportorName;
		fm.prpLregistLinkerName.fireEvent('onchange');
	}
}

function changeReportorPhoneNumber() {
	var prpLregistReportorPhoneNumber = document.getElementById("prpLregistReportorPhoneNumber");

	if (trim(fm.prpLregistPhoneNumber.value).length == 0) {
		if (prpLregistReportorPhoneNumber != null) {
			fm.prpLregistPhoneNumber.value = prpLregistReportorPhoneNumber.value;
			fm.prpLregistPhoneNumber.fireEvent('onchange');
		}
	}
}

function changeReportorMobile() {
	var prpLregistReportorMobile = document.getElementById("prpLregistReportorMobile");

	if (trim(fm.prpLregistDriverMobile.value).length == 0) {
		if (prpLregistReportorMobile != null) {
			fm.prpLregistDriverMobile.value = prpLregistReportorMobile.value;
			fm.prpLregistDriverMobile.fireEvent('onchange');
		}
	}
}
