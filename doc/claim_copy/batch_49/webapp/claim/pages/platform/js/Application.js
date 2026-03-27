/****************************************************************************
 * DESC       ：应用级JavaScript定义--Application Project控制(兼容IE5/NN6)
 * AUTHOR     ：zhouxianli
 * CREATEDATE ：2003-05-04
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 *
 ************************************************************************************/
//////////////////////////////////////////////////////////
////////////////////    推荐使用的方法   /////////////////
//////////////////////////////////////////////////////////
/**
 * 隐藏输入框
 * @param field 元素
 * @param tableName tableName
 * @return 无
 */
function hideSubPage(field, tableName) {
	var order = parseInt(getElementOrder(field));
	var obj = document.getElementsByName(tableName)[order - 1];
	obj.style.display = 'none';
}

/**
 * 显示输入框
 * @param field 元素
 * @param tableName tableName
 * @param leftMove 坐标左移偏移量，默认值0
 * @return 无
 */
function showSubPage(field, tableName, evt, leftMove) {
	var order = parseInt(getElementOrder(field));
	var obj = document.getElementsByName(tableName)[order - 1];
	var ex = evt.clientX + document.body.scrollLeft;
	var ey = evt.clientY + document.body.scrollTop;
	var intLeftMove = (leftMove == null ? 0 : leftMove);

	strTemp = field.name;
	var strCompare = "Context"; //比较字符串，条款的最後几个字符是Context
	if (strTemp.indexOf(strCompare) > -1) {
		strTemp = strTemp.substring(strTemp.length - strCompare.length);
	} else {
		strTemp = "";
	}

	if (strTemp == strCompare) {
		ex = ex - 520;
	}

	if (strTemp == strCompare) {
		ex = ex - 520;
	}
	ex = ex - intLeftMove;

	obj.style.display = '';
	obj.style.left = ex;
	obj.style.top = ey;
}

/**
 * 显示/隐藏页
 * @param fieldId
 * @param tableId tableId
 * @return 无
 */
function showPage(fieldId, tableId) {
	var field = document.getElementById(fieldId);
	var table = document.getElementById(tableId);
	if (table.style.display == "") {
		//关闭
		table.style.display = "none";
		field.value = "(+)";
	} else {
		//打开
		table.style.display = "";
		field.value = "(-)";
	}
}

//显示错误信息

function errorMessage(strErrMsg) {
	var strMsg = i18n.platform.systemInformation + strErrMsg; //系统信息:\n\n
	alert(strMsg);
}

/**
 * 默认的校验Form的方法
 * @return 通过true/不通过false
 */
function validateQueryForm(form) {
	if (bCancel == true) {
		return true;
	}
	return validateType(form) && validateDate(form);
}

/**
 * 根据字段名自动取出描述，显示信息错误提示，並定位
 * @since 2004-10-28
 */
function showErrorMessage(field, message) {
	showMessage(getSchemaColumn(field.name).desc + message);
	field.select();
	setFocus(field);
}

/**
 * 输入域必须全部輸入或全部不輸入
 * @return 校验成功返回true ,否则返回false
 * @since 2004-10-28
 */
function checkAllInputOrNotInput(field1, field2) {
	if (isEmptyField(field1) && isEmptyField(field2)) {
		return true;
	}

	if ((!isEmptyField(field1)) && (!isEmptyField(field2))) {
		return true;
	}

	if (isEmptyField(field1)) {
		showMessage("輸入" + getSchemaColumn(field2.name).desc + "的同时必须輸入" + getSchemaColumn(field1.name).desc);
		setFocus(field1);
	} else {
		showMessage("輸入" + getSchemaColumn(field1.name).desc + "的同时必须輸入" + getSchemaColumn(field2.name).desc);
		setFocus(field2);
	}

	return false;
}

//////////////////////////////////////////////////////////
////////////////////    Run   ////////////////////////////
//////////////////////////////////////////////////////////

function customBlurHandler(field) {
	return true;
}

function setReadonlyWhileHasValue(fields) {
	var i = 0;
	for (i = 0; i < fields.length; i++) {
		if (isEmptyField(fields[i]) == false) {
			fields[i].readOnly = true;
			fields[i].className = "readonly";
		}
	}
}




/**
 * 分割代码並放在select域里
 * 的格式: 值FIELD_SEPARATOR文本GROUP_SEPARATOR值FIELD_SEPARATOR文本...
 * @param selectName Select域的名字
 * @param strValue 传入的对象
 * @since 2004-12-29
 */
function setOption(selectName, strValue) {
	//查不到代码返回
	if (strValue == null || trim(strValue) == "") {
		return;
	}

	var arrayField = strValue.split(GROUP_SEPARATOR);
	var i = 0;
	var j = 0;
	var intCount = getElementCount(selectName);

	if (intCount > 1) {
		for (j = 0; j < intCount; j++) {
			fm.all(selectName)[j].options.length = 0;
		}
	} else {
		fm.all(selectName).options.length = 0;
	}

	while (i < arrayField.length) {
		if (intCount > 1) {
			for (j = 0; j < intCount; j++) {
				var option = document.createElement("option");
				var arrayTemp = arrayField[i].split(FIELD_SEPARATOR);
				var strFieldName = arrayTemp[0];
				var strFieldValue = unescape(arrayTemp[1]);
				option.value = strFieldName;
				option.text = strFieldValue;

				fm.all(selectName)[j].add(option);
			}
		} else {
			var option = document.createElement("option");
			var arrayTemp = arrayField[i].split(FIELD_SEPARATOR);
			var strFieldName = arrayTemp[0];
			var strFieldValue = unescape(arrayTemp[1]);
			option.value = strFieldName;
			option.text = strFieldValue;
			fm.all(selectName).add(option);
		}
		i++;
	}
}

function processMenuClick(theHREF) {
	window.parent.findIframe("topFrame").setCommand(theHREF.title);
}

setVerbose(true);

if (isVerbose() == false) {
	document.oncontextmenu = new Function('event.returnValue=false;');
	document.onselectstart = new Function('event.returnValue=false;');
}
