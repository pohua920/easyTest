/*****************************************************************************
 * DESC       ：95519报案完善功能的脚本
 * AUTHOR     ：中科軟
 * CREATEDATE ：2009-07-29
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/
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

function KeyDown() {
	if (event.keyCode == 13 && fm.queryButton.disabled == false) {
		fm.queryButton.click();
	}
}

function clickable() {
	fm.queryButton.disabled = false;
}



//查询

function callCenterQuery() {

	fm.action = "/claim/callcenterPerfect.do?actionType=query";
	fm.target = "QueryResultFrame";
	fm.queryButton.disabled = true;
	fm.submit();
}

//查询

function update() {
	fm.action = "/claim/callcenterPerfect.do?actionType=update";
	fm.submit();
}