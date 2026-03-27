/****************************************************************************
 * DESC       ：公用校验JavaScript(兼容IE5/NN6)--平台中心组控制，项目组不得修改，否则後果自负
 *              如果发现有问题或需求，请通知提供者
 * 最後更新   : 2005-02-23
 * AUTHOR     ：zhouxianli@sinosoft.com.cn
 *          ------------------------------------------------------
 *
 ************************************************************************************/
/**
    结构为 字段名，字段描述，原始类型，是否允许为空
           4项都是必需的
 */
var schemaColumns = new Array(); //存储字段结构的数组
var mulLineDataFields = new Array(); //临时存储Form中多行Data字段的数组
var skipFieldPrefixs = new Array(); //临时存储Form中跳过的字段前缀的数组

//初始化数组
var private_columnIndex = 0; //序号
/**
 * 添加SchemaColumn
 */
function addSchemaColumn(schemaColumn) {
	if (schemaColumns[schemaColumn.name] == null) {
		schemaColumns[schemaColumn.name] = schemaColumn;
	}
}

/**
 * schemaColumn定义
 */
function schemaColumn(name, desc, dataType, allowNulls) {
	this.name = name;
	this.desc = desc;
	this.dataType = dataType;
	this.allowNulls = allowNulls;
}
/**
 * 得到SchemaColumn
 * @param name SchemaColumn的name属性
 * @return 查到返回SchemaColumn,否则返回null
 */
function getSchemaColumn(name) {
	if (isSkipFieldName(name)) {
		return null;
	}
	return schemaColumns[name];
}

/**
 * 初始化多行输入域
 * mulLineTableIdList 以逗号为分割符组合的多行输入域表的ID
 */
function initMulLineFields(mulLineTableIdList) {
	mulLineDataFields = new Array();
	if (mulLineTableIdList == null || mulLineTableIdList == "") {
		return;
	}
	var tableIdArray = new Array();
	if (mulLineTableIdList.indexOf(",") > -1) {
		tableIdArray = mulLineTableIdList.split(",");
	} else {
		tableIdArray[0] = mulLineTableIdList;
	}

	var index = 0;
	var tableIdCount = tableIdArray.length;
	for (var i = 0; i < tableIdCount; i++) {
		var elements = new Array();
		elements = getTableElements(tableIdArray[i]);
		var elementsCount = elements.length;
		for (var j = 0; j < elementsCount; j++) {
			mulLineDataFields[index++] = elements[j];
		}
	}
}


/**
 * 初始化跳过字段前缀的列表
 * skipFieldPrefixsList 以逗号为分割符组合的跳过字段前缀的列表
 */
function initSkipFieldPrefixs(skipFieldPrefixsList) {
	skipFieldPrefixs = new Array();
	if (skipFieldPrefixsList == null || skipFieldPrefixsList == "") {
		return;
	}

	var skipFieldNameArray = new Array();
	if (skipFieldPrefixsList.indexOf(",") > -1) {
		skipFieldNameArray = skipFieldPrefixsList.split(",");
	} else {
		skipFieldNameArray[0] = skipFieldPrefixsList;
	}

	var index = 0;
	var skipFieldNameCount = skipFieldNameArray.length;
	for (var i = 0; i < skipFieldNameCount; i++) {
		skipFieldPrefixs[index++] = skipFieldNameArray[i];
	}
}

/**
 * 是否是多行输入域
 * @param field 元素
 * @return 是则返回true,否则返回false
 */
function isMulLineField(field) {
	var mulLineDataFieldsCount = mulLineDataFields.length;
	for (var i = 0; i < mulLineDataFieldsCount; i++) {
		if (field == mulLineDataFields[i]) {
			return true;
		}
	}
	return false;
}

/**
 * 是否是跳过的域的名字（无需处理）
 * @param name 元素名字
 * @return 是则返回true,否则返回false
 */
function isSkipFieldName(name) {
	var skipFieldPrefixsCount = skipFieldPrefixs.length;
	for (var i = 0; i < skipFieldPrefixsCount; i++) {
		if (name.indexOf(skipFieldPrefixs[i]) == 0) {
			return true;
		}
	}
	return false;
}

/**
 * 验证必须输入项(覆盖struts中相应方法)
 * @param form form
 * @return 是返回true,否返回false
 */
function validateRequired(form) {
	var bValid = true;
	var focusField = null;
	var i = 0;
	var field = null;
	var schemaColumn = null;
	var fields = new Array(); //存放schemaColumns
	var count = 0;
	var elementsCount = form.elements.length;
	for (i = 0; i < elementsCount; i++) {
		field = form.elements[i];
		if (field.type == 'text' ||
			field.type == 'file' ||
			field.type == 'textarea' ||
			field.type == 'select-one' ||
			field.type == 'radio' ||
			field.type == 'password') {

			if (isMulLineField(field)) {
				if (isFirstElement(field)) {
					continue;
				}
			}
			schemaColumn = getSchemaColumn(field.name);
			if (schemaColumn == null) {
				//log("字段" + field.name + "没有在ValidateData.js中定义");
				continue;
			}
			//跳过可以为空的输入域
			if (schemaColumn.allowNulls == true) {
				continue;
			}

			var value;
			// get field's value
			if (field.type == "select-one") {
				var si = field.selectedIndex;
				value = field.options[si].value;
			} else {
				value = field.value;
			}

			if (value == '') {
				if (focusField == null) {
					focusField = field;
				}
				fields[fields.length] = schemaColumn;
				bValid = false;
			}
		}
	}

	var fieldsCount = fields.length;
	if (fieldsCount > 0) {
		var message = "";
		for (i = 0; i < fieldsCount; i++) {
			message = message + fields[i].desc + "不允许为空\n";
		}
		showMessage(message);
		setFocus(focusField);
	}
	return bValid;
}

/**
 * 检查是否是合法的varchar类型字段
 * @param form form
 * @param schemaColumn schemaColumn
 * @return 是返回true,否返回false
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

	if (getByteLength(field.value) > maxLength) {
		return false;
	}
	if (getByteLength(field.value) < minSpace) {
		return false;
	}
	return true;
}

/**
 * 检查是否是合法的char类型字段
 * @param form form
 * @param schemaColumn schemaColumn
 * @return 是返回true,否返回false
 */
function isValidateCharField(field, schemaColumn) {
	var maxLength = 0;
	var temp = schemaColumn.dataType;
	var start = schemaColumn.dataType.indexOf("(", 4); //"("的位置
	var end = schemaColumn.dataType.indexOf(")", start); //")"的位置
	maxLength = parseInt(schemaColumn.dataType.substring(start + 1, end), 10);

	if (getByteLength(field.value) > maxLength) {
		return false;
	}
	return true;
}

/**
 * 检查是否是合法的Integer类型字段
 * @param form form
 * @param schemaColumn schemaColumn
 * @return 是返回true,否返回false
 */
function isValidateIntegerField(field, schemaColumn) {
	var strValue = field.value;
	if (strValue.length == 0)
		return true;

	field.value = trim(field.value);
	var strValue = field.value;
	if (strValue == "")
		strValue = "0";
	field.value = strValue

	if (regExpTest(strValue, /[+|\-]?\d+/g) == false) {
		return false;
	}

	var value = parseInt(strValue, 10);

	if (isNaN(value) || value > MAX_INTEGER || value < MIN_INTEGER) {
		return false;
	}
	return true;
}

/**
 * 检查是否是合法的SmallInt类型字段
 * @param form form
 * @param schemaColumn schemaColumn
 * @return 是返回true,否返回false
 */
function isValidateSmallintField(field, schemaColumn) {
	if (isValidateIntegerField(field, schemaColumn) == false) {
		return false;
	}
	var strValue = field.value;
	var value = parseInt(strValue, 10);
	if (value > MAX_SMALLINT || value < MIN_SMALLINT) {
		return false;
	}
	return true;
}

/**
 * 检查是否是decimal类型字段
 * @param form form
 * @param schemaColumn schemaColumn
 * @return 是返回true,否返回false
 */
function isValidateDecimalField(field, schemaColumn) {
	var length = 0;
	var scale = 0;
	var temp = schemaColumn.dataType;
	var start = schemaColumn.dataType.indexOf("("); //"("的位置
	var middle = schemaColumn.dataType.indexOf(","); //","的位置
	var end = schemaColumn.dataType.indexOf(")"); //")"的位置
	if (middle == -1) { //没有设置最小值
		length = parseInt(schemaColumn.dataType.substring(start + 1, end), 10);
		scale = 0;
	} else {
		length = parseInt(schemaColumn.dataType.substring(start + 1, middle), 10);
		scale = parseInt(schemaColumn.dataType.substring(middle + 1, end), 10);
	}
	field.value = trim(field.value); //去掉输入域头尾空格
	var strValue = field.value;
	if (strValue == "") {
		strValue = "0";
	}
	field.value = strValue;

	if (regExpTest(strValue, /^[+|\-]?\d*[.]?\d*/g) == false) {
		return false;
	}
	//检查精度
	middle = strValue.indexOf("."); //"."的位置
	if (middle > -1) {
		if (scale < strValue.substr(middle + 1).length) {
			return false;
		}
	}

	//检查最大长度
	var addLength = 0;
	if (strValue.indexOf("+") > -1) {
		addLength++;
	}
	if (strValue.indexOf("-") > -1) {
		addLength++;
	}

	if (strValue.indexOf(".") == 0) {
		addLength--;
	} else if (strValue.indexOf(".") > 0) {
		addLength++;
	}

	if (getByteLength(strValue) > (length + addLength)) {
		return false;
	}

	return true;
}

/**
 * 检查是否是合法的date类型字段
 * @param form form
 * @param schemaColumn schemaColumn
 * @return 是返回true,否返回false
 */
function isValidateDateField(field, schemaColumn) {
	field.value = replace(trim(field.value), "/", DATE_DELIMITER);
	var strValue = field.value;
	//如果是全数字,则转换为日期型数据
	if (regExpTest(strValue, /\d+/g) == true) {
		//只接受YYYYMMDD格式
		if (getByteLength(strValue) != 8) {
			return false;
		}
		strValue = strValue.substring(0, 4) + DATE_DELIMITER +
			strValue.substring(4, 6) + DATE_DELIMITER +
			strValue.substring(6);
	}
	var dateArr = strValue.split(DATE_DELIMITER);
	if (dateArr.length != 3) {
		return false;
	}
	var year = parseInt(dateArr[0], 10);
	var month = parseInt(dateArr[1], 10);
	var date = parseInt(dateArr[2], 10);

	//js月是从0-11
	var testDate = new Date(year, month - 1, date);
	if (testDate.getFullYear() != year || testDate.getMonth() != (month - 1) || testDate.getDate() != date) {
		return false;
	}
	field.value = dateArr[0] + DATE_DELIMITER + dateArr[1] + DATE_DELIMITER + dateArr[2];
	return true;
}

/**
 * 检查是否是合法的datetime类型字段
 * @param form form
 * @param schemaColumn schemaColumn
 * @return 是返回true,否返回false
 */
function isValidateDateTimeField(field, schemaColumn) {
	property = schemaColumn.dataType.toLowerCase();
	//如果是年到日,则调用isValidateDateField校验
	if (property == "datetime year to day") {
		return isValidateDateField(field, schemaColumn);
	}

	field.value = trim(field.value);
	var strValue = field.value;

	if (property == "datetime year to year") { //从年开始
		var reg = new RegExp("^(\\d+)$");
		var r = strValue.match(reg);
		if (r == null) return false;
		var d = new Date(r[1], 0, 1);
		if (d.getFullYear() != r[1]) return false;
	} else if (property == "datetime year to month") {
		var reg = new RegExp("^(\\d+)" + DATE_DELIMITER + "(\\d{1,2})$");
		var r = strValue.match(reg);
		if (r == null) return false;
		r[2] = r[2] - 1;
		var d = new Date(r[1], r[2], 1);
		if (d.getFullYear() != r[1]) return false;
		if (d.getMonth() != r[2]) return false;
	} else if (property == "datetime year to day") {
		var reg = new RegExp("^(\\d+)" + DATE_DELIMITER + "(\\d{1,2})" + DATE_DELIMITER + "(\\d{1,2})$");
		var r = strValue.match(reg);
		if (r == null) return false;
		r[2] = r[2] - 1;
		var d = new Date(r[1], r[2], r[3]);
		if (d.getFullYear() != r[1]) return false;
		if (d.getMonth() != r[2]) return false;
		if (d.getDate() != r[3]) return false;
	} else if (property == "datetime year to hour") {
		var reg = new RegExp("^(\\d+)" + DATE_DELIMITER + "(\\d{1,2})" + DATE_DELIMITER + "(\\d{1,2}) (\\d{1,2})$");
		var r = strValue.match(reg);
		if (r == null) return false;
		r[2] = r[2] - 1;
		var d = new Date(r[1], r[2], r[3], r[4], 1, 1);
		if (d.getFullYear() != r[1]) return false;
		if (d.getMonth() != r[2]) return false;
		if (d.getDate() != r[3]) return false;
		if (d.getHours() != r[4]) return false;
	} else if (property == "datetime year to minute") {
		var reg = new RegExp("^(\\d+)" + DATE_DELIMITER + "(\\d{1,2})" + DATE_DELIMITER + "(\\d{1,2}) (\\d{1,2}):(\\d{1,2})$");
		var r = strValue.match(reg);
		if (r == null) return false;
		r[2] = r[2] - 1;
		var d = new Date(r[1], r[2], r[3], r[4], r[5], 1);
		if (d.getFullYear() != r[1]) return false;
		if (d.getMonth() != r[2]) return false;
		if (d.getDate() != r[3]) return false;
		if (d.getHours() != r[4]) return false;
		if (d.getMinutes() != r[5]) return false;
	} else if (property == "datetime year to second") {
		var reg = new RegExp("^(\\d+)" + DATE_DELIMITER + "(\\d{1,2})" + DATE_DELIMITER + "(\\d{1,2}) (\\d{1,2}):(\\d{1,2}):(\\d{1,2})$");
		var r = strValue.match(reg);
		if (r == null) return false;
		r[2] = r[2] - 1;
		var d = new Date(r[1], r[2], r[3], r[4], r[5], r[6]);
		if (d.getFullYear() != r[1]) return false;
		if (d.getMonth() != r[2]) return false;
		if (d.getDate() != r[3]) return false;
		if (d.getHours() != r[4]) return false;
		if (d.getMinutes() != r[5]) return false;
		if (d.getSeconds() != r[6]) return false;
	} else if (property == "datetime year to millisecond") {
		var reg = new RegExp("^(\\d+)" + DATE_DELIMITER + "(\\d{1,2})" + DATE_DELIMITER + "(\\d{1,2}) (\\d{1,2}):(\\d{1,2}):(\\d{1,2}).(\\d{1,3})$");
		var r = strValue.match(reg);
		if (r == null) return false;
		r[2] = r[2] - 1;
		var d = new Date(r[1], r[2], r[3], r[4], r[5], r[6]);
		if (d.getFullYear() != r[1]) return false;
		if (d.getMonth() != r[2]) return false;
		if (d.getDate() != r[3]) return false;
		if (d.getHours() != r[4]) return false;
		if (d.getMinutes() != r[5]) return false;
		if (d.getSeconds() != r[6]) return false;
	} else if (property == "datetime month to month") { //从月开始
		var reg = new RegExp("^(\\d{1,2})$");
		var r = strValue.match(reg);
		if (r == null) return false;
		r[1] = r[1] - 1;
		var d = new Date(2004, r[1], 1);
		if (d.getMonth() != r[1]) return false;
	} else if (property == "datetime month to day") {
		var reg = new RegExp("^(\\d{1,2})" + DATE_DELIMITER + "(\\d{1,2})$");
		var r = strValue.match(reg);
		if (r == null) return false;
		r[1] = r[1] - 1;
		var d = new Date(2004, r[1], r[2]);
		if (d.getMonth() != r[1]) return false;
		if (d.getDate() != r[2]) return false;
	} else if (property == "datetime month to hour") {
		var reg = new RegExp("^(\\d{1,2})" + DATE_DELIMITER + "(\\d{1,2}) (\\d{1,2})$");
		var r = strValue.match(reg);
		if (r == null) return false;
		r[1] = r[1] - 1;
		var d = new Date(2004, r[1], r[2], r[3], 1, 1);
		if (d.getMonth() != r[1]) return false;
		if (d.getDate() != r[2]) return false;
		if (d.getHours() != r[3]) return false;
	} else if (property == "datetime month to minute") {
		var reg = new RegExp("^(\\d{1,2})" + DATE_DELIMITER + "(\\d{1,2}) (\\d{1,2}):(\\d{1,2})$");
		var r = strValue.match(reg);
		if (r == null) return false;
		r[1] = r[1] - 1;
		var d = new Date(2004, r[1], r[2], r[3], r[4], 1);
		if (d.getMonth() != r[1]) return false;
		if (d.getDate() != r[2]) return false;
		if (d.getHours() != r[3]) return false;
		if (d.getMinutes() != r[4]) return false;
	} else if (property == "datetime month to second") {
		var reg = new RegExp("^(\\d{1,2})" + DATE_DELIMITER + "(\\d{1,2}) (\\d{1,2}):(\\d{1,2}):(\\d{1,2})$");
		var r = strValue.match(reg);
		if (r == null) return false;
		r[1] = r[1] - 1;
		var d = new Date(2004, r[1], r[2], r[3], r[4], r[5]);
		if (d.getMonth() != r[1]) return false;
		if (d.getDate() != r[2]) return false;
		if (d.getHours() != r[3]) return false;
		if (d.getMinutes() != r[4]) return false;
		if (d.getSeconds() != r[5]) return false;
	} else if (property == "datetime month to millisecond") {
		var reg = new RegExp("^(\\d{1,2})" + DATE_DELIMITER + "(\\d{1,2}) (\\d{1,2}):(\\d{1,2}):(\\d{1,2}).(\\d{1,3})$");
		var r = strValue.match(reg);
		if (r == null) return false;
		r[1] = r[1] - 1;
		var d = new Date(2004, r[1], r[2], r[3], r[4], r[5]);
		if (d.getMonth() != r[1]) return false;
		if (d.getDate() != r[2]) return false;
		if (d.getHours() != r[3]) return false;
		if (d.getMinutes() != r[4]) return false;
		if (d.getSeconds() != r[5]) return false;
	} else if (property == "datetime day to day") { //从日开始
		var reg = new RegExp("^(\\d{1,2})$");
		var r = strValue.match(reg);
		if (r == null) return false;
		var d = new Date(2004, 1, r[1]);
		if (d.getDate() != r[1]) return false;
	} else if (property == "datetime day to hour") {
		var reg = new RegExp("^(\\d{1,2}) (\\d{1,2})$");
		var r = strValue.match(reg);
		if (r == null) return false;
		var d = new Date(2004, 1, r[1], r[2], 1, 1);
		if (d.getDate() != r[1]) return false;
		if (d.getHours() != r[2]) return false;
	} else if (property == "datetime day to minute") {
		var reg = new RegExp("^(\\d{1,2}) (\\d{1,2}):(\\d{1,2})$");
		var r = strValue.match(reg);
		if (r == null) return false;
		var d = new Date(2004, 1, r[1], r[2], r[3], 1);
		if (d.getDate() != r[1]) return false;
		if (d.getHours() != r[2]) return false;
		if (d.getMinutes() != r[3]) return false;
	} else if (property == "datetime day to second") {
		var reg = new RegExp("^(\\d{1,2}) (\\d{1,2}):(\\d{1,2}):(\\d{1,2})$");
		var r = strValue.match(reg);
		if (r == null) return false;
		var d = new Date(2004, 1, r[1], r[2], r[3], r[4]);
		if (d.getDate() != r[1]) return false;
		if (d.getHours() != r[2]) return false;
		if (d.getMinutes() != r[3]) return false;
		if (d.getSeconds() != r[4]) return false;
	} else if (property == "datetime day to millisecond") {
		var reg = new RegExp("^(\\d{1,2}) (\\d{1,2}):(\\d{1,2}):(\\d{1,2}).(\\d{1,3})$");
		var r = strValue.match(reg);
		if (r == null) return false;
		var d = new Date(2004, 1, r[1], r[2], r[3], r[4]);
		if (d.getDate() != r[1]) return false;
		if (d.getHours() != r[2]) return false;
		if (d.getMinutes() != r[3]) return false;
		if (d.getSeconds() != r[4]) return false;
	} else if (property == "datetime hour to hour") { //从小时开始
		var reg = new RegExp("^(\\d{1,2})$");
		var r = strValue.match(reg);
		if (r == null) return false;
		var d = new Date(2004, 1, 1, r[1], 1, 1);
		if (d.getHours() != r[1]) return false;
	} else if (property == "datetime hour to minute") {
		var reg = new RegExp("^(\\d{1,2}):(\\d{1,2})$");
		var r = strValue.match(reg);
		if (r == null) return false;
		var d = new Date(2004, 1, 1, r[1], r[2], 1);
		if (d.getHours() != r[1]) return false;
		if (d.getMinutes() != r[2]) return false;
	} else if (property == "datetime hour to second") {
		var reg = new RegExp("^(\\d{1,2}):(\\d{1,2}):(\\d{1,2})$");
		var r = strValue.match(reg);
		if (r == null) return false;
		var d = new Date(2004, 1, 1, r[1], r[2], r[3]);
		if (d.getHours() != r[1]) return false;
		if (d.getMinutes() != r[2]) return false;
		if (d.getSeconds() != r[3]) return false;
	} else if (property == "datetime hour to millisecond") {
		var reg = new RegExp("^(\\d{1,2}):(\\d{1,2}):(\\d{1,2}).(\\d{1,3})$");
		var r = strValue.match(reg);
		if (r == null) return false;
		var d = new Date(2004, 1, 1, r[1], r[2], r[3]);
		if (d.getHours() != r[1]) return false;
		if (d.getMinutes() != r[2]) return false;
		if (d.getSeconds() != r[3]) return false;
	} else if (property == "datetime minute to minute") { //从分开始
		var reg = new RegExp("^(\\d{1,2})$");
		var r = strValue.match(reg);
		if (r == null) return false;
		var d = new Date(2004, 1, 1, 1, r[1], 1);
		if (d.getMinutes() != r[1]) return false;
	} else if (property == "datetime minute to second") {
		var reg = new RegExp("^(\\d{1,2}):(\\d{1,2})$");
		var r = strValue.match(reg);
		if (r == null) return false;
		var d = new Date(2004, 1, 1, 1, r[1], r[2]);
		if (d.getMinutes() != r[1]) return false;
		if (d.getSeconds() != r[2]) return false;
	} else if (property == "datetime minute to millisecond") {
		var reg = new RegExp("^(\\d{1,2}):(\\d{1,2}).(\\d{1,3})$");
		var r = strValue.match(reg);
		if (r == null) return false;
		var d = new Date(2004, 1, 1, 1, r[1], r[2]);
		if (d.getMinutes() != r[1]) return false;
		if (d.getSeconds() != r[2]) return false;
	} else if (property == "datetime second to second") { //从秒开始
		var reg = new RegExp("^(\\d{1,2})$");
		var r = strValue.match(reg);
		if (r == null) return false;
		var d = new Date(2004, 1, 1, 1, 1, r[1]);
		if (d.getSeconds() != r[1]) return false;
	} else if (property == "datetime second to millisecond") {
		var reg = new RegExp("^(\\d{1,2}).(\\d{1,3})$");
		var r = strValue.match(reg);
		if (r == null) return false;
		var d = new Date(2004, 1, 1, 1, 1, r[1]);
		if (d.getSeconds() != r[1]) return false;
	} else if (property == "datetime millisecond to millisecond") { //从毫秒开始
		var reg = new RegExp("^(\\d{1,3})$");
		var r = strValue.match(reg);
		if (r == null) return false;
	}

	return true;
}

/**
 * 验证字段类型
 * @param form form
 * @return 是返回true,否返回false
 */
function validateType(form) {
	var bValid = true;
	var bResult = true;
	var focusField = null;
	var i = 0;
	var field = null;
	var schemaColumn = null;
	var fields = new Array(); //存放schemaColumns
	var count = 0;
	var type = "";
	var property = "";
	var elementsCount = form.elements.length;
	for (i = 0; i < elementsCount; i++) {
		field = form.elements[i];
		if (field.type == 'text' ||
			field.type == 'password' ||
			field.type == 'textarea') {

			if (field.value == "") {
				continue;
			}
			if (isMulLineField(field)) {
				if (isFirstElement(field)) {
					continue;
				}
			}

			schemaColumn = getSchemaColumn(field.name);
			if (schemaColumn == null) {
				//log("字段" + field.name + "没有在ValidateData.js中定义");
				continue;
			}
			property = schemaColumn.dataType.toLowerCase();

			if (property == "text") { //text类型无需校验
				bResult = true;
			} else if (property == ("byte")) { //byte类型无需校验
				bResult = true;
			} else if (property == "integer" || property == "int") {
				bResult = isValidateIntegerField(field, schemaColumn);
			} else if (property == "smallint") {
				bResult = isValidateSmallintField(field, schemaColumn);
			} else if (property == "date") {
				bResult = isValidateDateField(field, schemaColumn);
			} else if (property.indexOf("datetime") != -1) {
				bResult = isValidateDateTimeField(field, schemaColumn);
			} else if (property.indexOf("varchar") != -1) {
				bResult = isValidateVarcharField(field, schemaColumn);
			} else if (property.indexOf("char") != -1) {
				bResult = isValidateCharField(field, schemaColumn);
			} else if (property.indexOf("dec") != -1) {
				bResult = isValidateDecimalField(field, schemaColumn);
			} else if (property.indexOf("float") != -1) {
				bResult = isValidateDecimalField(field, schemaColumn);
			} else if (property.indexOf("number") != -1) {
				bResult = isValidateDecimalField(field, schemaColumn);
			} else if (property.indexOf("numeric") != -1) {
				bResult = isValidateDecimalField(field, schemaColumn);
			} else {
				//log("警告:"+field.name+"类型为" +property + ".在validateType中没有处理,请联系提供者!");
				bResult = true;
			}

			if (bResult == false) {
				if (focusField == null) {
					focusField = field;
				}
				fields[fields.length] = schemaColumn;
				bValid = false;
			}
		}
	}
	var fieldsCount = fields.length;
	if (fieldsCount > 0) {
		var message = "";
		for (i = 0; i < fieldsCount; i++) {
			message = message + getErrorMessage(fields[i]) + "\n";
		}
		showMessage(message);
		setFocus(focusField);
		setSelect(focusField);
	}
	return bValid;
}

function getErrorMessage(field) {
	var typeDesc = "";
	var maxLength = 0;
	var minSpace = 0;
	var start = 0; //"("的位置
	var middle = 0; //","的位置
	var end = 0; //")"的位置
	var property;

	start = field.dataType.indexOf("("); //"("的位置
	middle = field.dataType.indexOf(","); //","的位置
	end = field.dataType.indexOf(")"); //")"的位置
	if (middle == -1) { //没有设置最小值
		maxLength = parseInt(field.dataType.substring(start + 1, end), 10);
		minSpace = 0;
	} else {
		maxLength = parseInt(field.dataType.substring(start + 1, middle), 10);
		minSpace = parseInt(field.dataType.substring(middle + 1, end), 10);
	}

	property = field.dataType.toLowerCase();
	if (property == "integer" || property == "int") {
		typeDesc = "整数";
		//                typeDesc = typeDesc + "(最大为" + MAX_INTEGER + ",最小为" + MIN_INTEGER + ")";
	} else if (property == "smallint") {
		typeDesc = "小整数";
		//                typeDesc = typeDesc + "(最大为" + MAX_SMALLINT + ",最小为" + MIN_SMALLINT + ")";
	} else if (property == "date") {
		typeDesc = "有效日期，且格式为YYYYMMDD或YYYY" + DATE_DELIMITER + "MM" + DATE_DELIMITER + "DD)";
	} else if (property.indexOf("datetime") != -1) {
		typeDesc = "日期时间格式(格式为" + trim(property.substring(8)) + ",其中日期分割符为" + DATE_DELIMITER + ")";
	} else if (property.indexOf("varchar") != -1) {
		typeDesc = "字符串";
		typeDesc = typeDesc + "(最大长度为" + maxLength;
		if (minSpace > 0) {
			typeDesc = typeDesc + ",最小长度为" + minSpace;
		}
		typeDesc = typeDesc + ")"
	} else if (property.indexOf("char") != -1) {
		typeDesc = "字符串";
		typeDesc = typeDesc + "(最大长度为" + maxLength;
		if (minSpace > 0) {
			typeDesc = typeDesc + ",最小长度为" + minSpace;
		}
		typeDesc = typeDesc + ")"
	} else if (property.indexOf("dec") != -1 || property.indexOf("float") != -1 || property.indexOf("number") != -1 || property.indexOf("numeric") != -1) {
		typeDesc = "数值";
		typeDesc = typeDesc + "(有效位数为" + maxLength;
		if (minSpace > 0) {
			typeDesc = typeDesc + ",精度为" + minSpace;
		}
		typeDesc = typeDesc + ")"
	}
	return "\"" + field.desc + "\"必须是" + typeDesc;
}

/**
 * 默认的校验Form的方法
 * @param form form
 * @param mulLineTableIdList 多行输入域的数据页的table ID,如果是多个，中间用逗号分隔。
 * @param skipFieldPrefixs 字段名符合上述前缀即不进行校验。如果是多个，中间用逗号分隔。
 * @return 通过true/不通过false
 */
function validateForm(form, mulLineTableIdList, skipFieldPrefixs) {
	if (bCancel == true) {
		return true;
	}
	//   if(!confirm("确认要保存信息？")){
	//     return false;
	//   }

	initMulLineFields(mulLineTableIdList);
	initSkipFieldPrefixs(skipFieldPrefixs);

	return validateRequired(form) && validateType(form);
}

function getDataTypeMaxLength(dataType) {
	var maxLength = 0;
	var minSpace = 0;
	var start = 0; //"("的位置
	var middle = 0; //","的位置
	var end = 0; //")"的位置

	start = dataType.indexOf("("); //"("的位置
	middle = dataType.indexOf(","); //","的位置
	end = dataType.indexOf(")"); //")"的位置
	//存在"("
	if (start > 0) {
		if (middle == -1) { //没有设置最小值
			maxLength = parseInt(dataType.substring(start + 1, end), 10);
			minSpace = 0;
		} else {
			maxLength = parseInt(dataType.substring(start + 1, middle), 10);
			minSpace = parseInt(dataType.substring(middle + 1, end), 10);
		}
	}

	if (dataType == "integer" || dataType == "int") {
		maxLength = DB_INT_LENGTH;
	} else if (dataType == "smallint") {
		maxLength = DB_INT_LENGTH / 4;
	} else if (dataType == "date") {
		maxLength = 10;
	}
	return maxLength;
}

/**
 * 初始化整个页面的方法
 * 设置域的maxLenght属性.
 * @param mulLineTableIdList 多行输入域的数据页的table ID,如果是多个，中间用逗号分隔。
 */
function initPage(mulLineTableIdList) {
	var i = 0;
	var j = 0;
	var maxLength = 0;
	var element;
	var schemaColumn;
	var method;
	var formsCount = document.forms.length;
	initMulLineFields(mulLineTableIdList);
	for (i = 0; i < formsCount; i++) {
		var form = document.forms[i];
		var elementsCount = form.elements.length;
		for (j = 0; j < elementsCount; j++) {
			element = form.elements[j];

			if (element.name == "" || element.type == 'hidden' || element.type == 'button' || element.type == 'submit' || element.type == 'reset' || element.type == 'img') {
				continue;
			}
			schemaColumn = getSchemaColumn(element.name);
			if (schemaColumn == null) {
				//log("字段" + element.name + "没有在ValidateData.js中定义");
				continue;
			}

			//设置maxLength
			if (element.type == 'text' ||
				element.type == 'textarea' ||
				element.type == 'password') {
				maxLength = getDataTypeMaxLength(schemaColumn.dataType);
				if (maxLength > 0) {
					element.maxLength = maxLength;
				}
			}
			//设置title
			if (element.type == 'text' ||
				element.type == 'textarea') {
				if (element.title == "") {
					var property = schemaColumn.dataType.toLowerCase();
					var typeDesc = "";
					if (property == "date") {
						typeDesc = "日期(格式为YYYY" + DATE_DELIMITER + "MM" + DATE_DELIMITER + "DD),例如:2005" + DATE_DELIMITER + "12" + DATE_DELIMITER + "28";
					} else if (property.indexOf("datetime") != -1) {
						typeDesc = "日期时间格式(格式为" + trim(property.substring(8)) + ",其中日期分割符为" + DATE_DELIMITER + ")";
					}

					if (typeDesc != "") {
						element.title = typeDesc + "\r\n如果是查询，可以在中间加入冒号':'进行区间查询";
					}
				}
			}

			//添加onkeypress处理事件
			method = null;
			if (element.onkeypress != null) {
				method = element.onkeypress;
			}
			element.onkeypress = keypressHandler;
			if (method != null) {
				eval("element.onkeypress.prototype." + element.name + "=" + method);
			}
			//添加onblur处理事件
			method = null;
			if (element.onblur != null) {
				method = element.onblur;
			}
			element.onblur = blurHandler;
			if (method != null) {
				eval("element.onblur.prototype." + element.name + "=" + method);
			}
		}
	}
}

/**
 * 是否是合法的decimal类型输入,即("0-9",".","+",'-")
 * 是返回true,否返回false.
 */
function isValidateDecimalInput(e) {
	var value;
	if (isNetscape()) {
		value = String.fromCharCode(e.charCode);
	} else {
		value = String.fromCharCode(e.keyCode);
	}
	if (regExpTest(value, /[+|\-|\.|0-9]{1}/) == true) {
		return true;
	} else {
		return false;
	}
}

/**
 * 是否是合法的integer类型输入,即("0-9","+",'-")
 * 是返回true,否返回false.
 */
function isValidateIntegerInput(e) {
	var value;
	if (isNetscape()) {
		value = String.fromCharCode(e.charCode);
	} else {
		value = String.fromCharCode(e.keyCode);
	}
	if (regExpTest(value, /[+|\-|0-9]{1}/) == true) {
		return true;
	} else {
		return false;
	}
}

/**
 * 是否是合法的date类型输入,即("0-9","/")
 * 是返回true,否返回false.
 */
function isValidateDateInput(e) {
	var value;
	if (isNetscape()) {
		value = String.fromCharCode(e.charCode);
	} else {
		value = String.fromCharCode(e.keyCode);
	}
	if (regExpTest(value, /[\/|0-9]| |-|:{1}/) == true) {
		return true;
	} else {
		return false;
	}
}


function keypressHandler(evt) {
	var testValue;
	testValue = true;
	evt = (evt) ? evt : window.event;
	var schemaColumn = getSchemaColumn(this.name);
	if (schemaColumn == null) {
		//log("字段" + this.name + "没有在ValidateData.js中定义2");
		testValue = true;
	} else {
		var dataType = schemaColumn.dataType.toLowerCase();
		if (dataType == "integer" || dataType == "int") {
			testValue = isValidateIntegerInput(evt);
		} else if (dataType == "smallint") {
			testValue = isValidateIntegerInput(evt);
		} else if (dataType == "date") {
			testValue = isValidateDateInput(evt);
		} else if (dataType.indexOf("varchar") != -1) {
			testValue = true;
		} else if (dataType.indexOf("char") != -1) {
			testValue = true;
		} else if (dataType.indexOf("dec") != -1 || dataType.indexOf("float") != -1 || dataType.indexOf("number") != -1 || dataType.indexOf("numeric") != -1) {
			testValue = isValidateDecimalInput(evt);
		} else {
			//log("警告"+this.name+"类型为" +dataType + ".在keypressHandler中没有处理");
			testValue = true;
		}
		if (testValue != true) {
			return false;
		}
	}

	try {
		var obj;
		obj = eval("this.onkeypress.prototype." + this.name);
		if (obj != null) {
			if (obj.apply(obj, arguments) == false) {
				return false;
			}
		}
	} catch (E) {
		log(E);
		testValue = false;
	}
	return testValue;
}

function blurHandler(evt) {
	var testValue = true;
	var obj;
	evt = (evt) ? evt : window.event;
	obj = eval("window.customBlurHandler");
	if (obj != null) {
		//自定义方法,形如
		//  /**
		//   * 焦点离开时调用
		//   * @param field 焦点离开域
		//   * @return 允许离开返回true，否则返回false
		//   */
		//  function customBlurHandler(field){
		//    return true;
		//  }
		if (customBlurHandler(this) == false) { //调用自定义方法
			return false;
		}
	}

	try {
		obj = eval("this.onblur.prototype." + this.name);
		if (obj != null) {
			if (obj.apply(obj, arguments) == false) {
				return false;
			}
		}
	} catch (E) {
		log(E);
		testValue = false;
	}

	var field = this;
	if (field == null || trim(field.value) == "") {
		return testValue;
	}

	var schemaColumn = getSchemaColumn(this.name);
	if (schemaColumn == null) {
		log("資料欄位" + this.name + "没有在ValidateData.js中定义2");
		testValue = true;
	} else {
		var bResult = true;
		property = schemaColumn.dataType.toLowerCase();
		if (property == "text") { //text类型无需校验
			bResult = true;
		} else if (property == ("byte")) { //byte类型无需校验
			bResult = true;
		} else if (property == "integer" || property == "int") {
			bResult = isValidateIntegerField(field, schemaColumn);
		} else if (property == "smallint") {
			bResult = isValidateSmallintField(field, schemaColumn);
		} else if (property == "date") {
			bResult = isValidateDateField(field, schemaColumn);
		} else if (property.indexOf("datetime") != -1) {
			bResult = isValidateDateTimeField(field, schemaColumn);
		} else if (property.indexOf("varchar") != -1) {
			bResult = isValidateVarcharField(field, schemaColumn);
		} else if (property.indexOf("char") != -1) {
			bResult = isValidateCharField(field, schemaColumn);
		} else if (property.indexOf("dec") != -1) {
			bResult = isValidateDecimalField(field, schemaColumn);
		} else if (property.indexOf("float") != -1) {
			bResult = isValidateDecimalField(field, schemaColumn);
		} else if (property.indexOf("number") != -1) {
			bResult = isValidateDecimalField(field, schemaColumn);
		} else if (property.indexOf("numeric") != -1) {
			bResult = isValidateDecimalField(field, schemaColumn);
		} else {
			//log("警告:"+field.name+"类型为" +property + ".在validateType中没有处理,请联系提供者!");
			bResult = true;
		}

		if (bResult == false) {
			showMessage(getErrorMessage(schemaColumn));
			return bResult;
		}
	}
	return testValue;
}

/**
 * 默认的提交Form来保存数据的方法
 * @param form form
 * @param mulLineTableIdList 多行输入域的数据页的table ID,如果是多个，中间用逗号分隔。
 * @param skipFieldPrefixs 字段名符合上述前缀即不进行校验。如果是多个，中间用逗号分隔。
 * @return 通过true/不通过false
 * @since 2004-11-25
 */
function submitFormToSave(form, mulLineTableIdList, skipFieldPrefixs) {
	if (!confirm("确认要儲存信息？")) {
		return false;
	}

	return validateForm(form, mulLineTableIdList, skipFieldPrefixs);
}

/**
 * 无需确认的提交Form来保存数据的方法
 * @param form form
 * @param mulLineTableIdList 多行输入域的数据页的table ID,如果是多个，中间用逗号分隔。
 * @param skipFieldPrefixs 字段名符合上述前缀即不进行校验。如果是多个，中间用逗号分隔。
 * @return 通过true/不通过false
 * @since 2005-01-12
 */
function submitFormWithoutConfirm(form, mulLineTableIdList, skipFieldPrefixs) {
	return validateForm(form, mulLineTableIdList, skipFieldPrefixs);
}