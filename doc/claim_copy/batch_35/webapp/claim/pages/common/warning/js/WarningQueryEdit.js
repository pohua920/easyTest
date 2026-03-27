/**
 * 提交时校验
 */
function submitForm() {
	var errorMessage = "";
	if (trim(fm.swflogRiskCode.value) == "") {
		errorMessage = errorMessage + i18n.sendUndwrt.lineCannotEmpty+"\n"; // 险种不能为空\n
	}

	if (trim(fm.swflogComCode.value) == "") {
		errorMessage = errorMessage + ""; //机构代码不能为空\n
	}

	if (trim(fm.swflogFlowInTime.value) == "") {
		errorMessage = errorMessage + i18n.sendUndwrt.timeCannotEmpty+"\n"; //预警开始时间不能为空\n
	}
	if (trim(fm.swflogSubmitTime.value) == "") {
		errorMessage = errorMessage + i18n.sendUndwrt.endCannotEmpty+"\n"; //预警结束时间不能为空\n
	}

	if (errorMessage.length > 0) {
		alert(errorMessage);
		return false;
	}

	fm.submit(); //提交
}

/**
 * 预警明细查询提交校验
 */
function submitDetailForm() {
	var errorMessage = "";
	if (trim(fm.swflogFlowInTime.value) == "") {
		errorMessage = errorMessage + i18n.sendUndwrt.timeCannotEmpty+"\n"; //预警开始时间不能为空\n
	}
	if (trim(fm.swflogSubmitTime.value) == "") {
		errorMessage = errorMessage + i18n.sendUndwrt.endCannotEmpty+"\n"; //预警结束时间不能为空\n
	}

	if (errorMessage.length > 0) {
		alert(errorMessage);
		return false;
	}

	fm.submit(); //提交
}

/**
 * 回车後提交
 */
function document.onkeydown() {
	if (event.keyCode == 13) {
		document.getElementById("button").click();
		return false;
	}
}

function getStartDateAndEndDate() {
	var damageDate = fm.DamageDate.value;

	var damageDateArray = damageDate.split("-");
	var date1Temp = new Date(damageDateArray[0], parseInt(damageDateArray[1], 10) - 1, parseInt(damageDateArray[2], 10));
	intYear = date1Temp.getFullYear(); //年
	intMonth = date1Temp.getMonth(); //月

	intDays = date1Temp.getDate();
	var startDate = new Date(intYear - 1, intMonth, intDays)
	var endDate = new Date(intYear + 1, intMonth, intDays);

	var temp1 = "";
	var temp2 = "";

	if (startDate.getMonth() < 9) temp1 = "0";
	if (startDate.getDate() < 10) temp2 = "0";

	fm.StartDate.value = startDate.getYear() + "-" + temp1 + (startDate.getMonth() + 1) + "-" + temp2 + startDate.getDate();

	temp1 = "";
	temp2 = "";

	if (endDate.getMonth() < 9) temp1 = "0";
	if (endDate.getDate() < 10) temp2 = "0";
	fm.EndDate.value = endDate.getYear() + "-" + temp1 + (endDate.getMonth() + 1) + "-" + temp2 + endDate.getDate();
}
/**
 * 导出Excel
 */
var idTmr = "";

function copy(tabid) {
	var oControlRange = document.body.createControlRange();
	oControlRange.add(tabid, 0);
	oControlRange.select();
	document.execCommand("Copy");
}

function toExcel(tabid) {
	copy(tabid);
	try {
		var xls = new ActiveXObject("Excel.Application");
	} catch (e) {
		alert(i18n.sendUndwrt.securityLevel+"\n"); //请将浏览器的安全级别设到最低
		return false;
	}
	xls.visible = true;
	var xlBook = xls.Workbooks.Add;
	var xlsheet = xlBook.Worksheets(1);
	xlBook.Worksheets(1).Activate;
	for (var i = 0; i < tabid.rows(0).cells.length; i++) {
		xlsheet.Columns(i + 1).ColumnWidth = 15;
	}
	xlsheet.Paste;
	xls = null;
	idTmr = window.setInterval("Cleanup();", 1);
}

function Cleanup() {
	window.clearInterval(idTmr);
	CollectGarbage();
}