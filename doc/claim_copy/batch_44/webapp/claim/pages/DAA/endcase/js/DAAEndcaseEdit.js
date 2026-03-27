/*****************************************************************************
 * DESC       ：结案登记的脚本函数页面
 * AUTHOR     ：中科软
 * CREATEDATE ： 2013-02-04
 * MODIFYLIST ：   Name       Date            Reason/Contents
 ****************************************************************************/
/**
 * @description 检查结案登记
 * @param 无
 * @return 通过返回true,否则返回false
 */

function checkForm() {
	return true;
}

/**
 * @description 提交
 * @param 无
 * @return 通过返回true,否则返回false
 */

function submitForm() {
	if (checkForm() == false) {
		return false;
	}

	fm.buttonSave.disabled = true;
	fm.submit();
	return true;
}

/**
 * @description 清除
 * @param 无
 * @return 通过返回true,否则返回false
 */

function resetForm() {
	if (window.confirm("確定要清除嗎？")) {
		location.href = location.href;
		return true;
	} else {
		return false;
	}
}

/**
 * @description 设值页面的一些初始化信息
 * @param 无
 * @return 通过返回true,否则返回false
 */

function initSet() {
	return true;
}
/**
 * @description 根据按钮状态保存结案数据
 * @param this
 * @param 保存状态
 * @return 通过返回true,否则返回false
 */

function saveForm(field, saveType) {
	if (!confirm("結案後不能再修改。\n確定結案嗎？"))
		return false;

	var errorMessage = "";
	fm.buttonSaveType.value = saveType;
	if (errorMessage.length > 0) {
		alert(errorMessage);
		return false;
	}
	// reason: ValidateData.js中的校验不起作用时，因为没有调用校验方法
	if (!validateForm(fm)) {
		return false;
	}
	// reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
	field.disabled = true;
	fm.submit();

	return true;
}

/**
 * @description 弹出关联页面
 * @param 无
 * @return 通过返回true,否则返回false
 */

function relate2() {
	var policyNo = fm.prpLclaimPolicyNo.value;
	var registNo = fm.prpLclaimRegistNo.value;
	var newWindow = window.open("/claim/RelateBusinessNo.do?policyNo=" + policyNo + "&registNo=" + registNo, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
}
// start-----------------------------------------------------------------------
// 结案中计算书、附页列印需先展示列表
function relate3(field,printType) {
	// 列印赔款计算书、附页时弹出页面,领取赔款通知书
	var claimNo = document.getElementsByName("prpLendcaseClaimNo1")[0].value;
	var newWindow = window.open("/claim/compensate/compensateQuery.do?ClaimNo=" + claimNo + "&editType=PRINT&PrintType=" + printType, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
}

function printForm(filed, type) {
	var registNo = fm.prpLclaimRegistNo.value;
	var claimNo = fm.prpLclaimClaimNo.value;
	var policyNo = fm.prpLclaimPolicyNo.value;
	var preCompensateNo = fm.prpLendcasepreCompensateNo.value;
	var compensateNo = fm.prpLendcasecompensateNo.value;

	if (type == "Regist") { // 1.机动车辆保险报案记录（代抄单）
		var newWindow = window.open("/claim/print/claimPrint.do?printType=" + type + "&RegistNo=" + registNo, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
		return newWindow;
	}
	if (type == "ClaimStatement") {
		var newWindow = window.open("/claim/print/claimPrint.do?printType=" + type + "&CompensateNo=" + compensateNo, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
		return newWindow;
	}
	if (type == "BzPay") { // 16.预付赔款审批表
		if (preCompensateNo == "") {
			alert(i18n.endcase.caseWithoutTable); //该案件无此表
			return null;
		} else {
			var newWindow = window.open("/claim/print/claimPrint.do?printType=" + type + "&PrepayNo=" + preCompensateNo, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
			return newWindow;
		}
	}
	if (type == "Cancelnotice") { // 3.拒赔通知书
		var newWindow = window.open("/claim/ClaimPrint.do?printType=" + type + "&ClaimNo=" + claimNo, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
		return newWindow;
	}
	if (type == "HistoryFile") { // 4.原始保单及出险时批单
		var newWindow = window.open("/claim/ClaimPrint.do?printType=" + type + "&PolicyNo=" + policyNo, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
		return newWindow;
	}
	if (type == "FileOnRisk") { // 5.出险时保单
		var endDate = trim(fm.prpLendcaseDamageStartDate.value);
		endDate = endDate.substr(0, 10);
		var endDateHour = "";
		var newWindow = window.open("/claim/ClaimPrint.do?printType=" + type + "&PolicyNo=" + policyNo + "&EndDate=" + endDate + "&EndDateHour=" + endDateHour, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
		return newWindow;
	}
	if (type == "Canceltrans") { // 6.拒赔案件报告书
		var newWindow = window.open("/claim/ClaimPrint.do?printType=" + type + "&ClaimNo=" + claimNo, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
		return newWindow;
	}
	if (type == "LossSimple") { // 7.损失情况简易确认书
		var newWindow = window.open("/claim/ClaimPrint.do?printType=" + type + "&ClaimNo=" + claimNo, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
		return newWindow;
	}
	if (type == "Loss") { // 8.损失情况确认书
		var newWindow = window.open("/claim/ClaimPrint.do?printType=" + type + "&ClaimNo=" + claimNo + "&RegistNo=" + registNo, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
		return newWindow;
	}
	if (type == "ComponentList") { // 9.零部件更换项目清单
		var newWindow = window.open("/claim/ClaimPrint.do?printType=" + type + "&ClaimNo=" + claimNo, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
		return newWindow;
	}
	if (type == "RepairList") { // 10.修理项目清单
		var newWindow = window.open("/claim/ClaimPrint.do?printType=" + type + "&ClaimNo=" + claimNo, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
		return newWindow;
	}
	if (type == "RepairAdd") { // 11.修理项目清单附表
		var newWindow = window.open("/claim/ClaimPrint.do?printType=" + type + "&ClaimNo=" + claimNo, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
		return newWindow;
	}
	if (type == "PropList") { // 12.财产损失确认书
		var newWindow = window.open("/claim/ClaimPrint.do?printType=" + type + "&ClaimNo=" + claimNo, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
		return newWindow;
	}

	if (type == "Pressnotice") { // 17.注销通知书
		var newWindow = window.open("/claim/ClaimPrint.do?printType=" + type + "&ClaimNo=" + claimNo, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
		return newWindow;
	}
	if (type == "PressnoticeEnd") { // 17.结案催告通知书
		var newWindow = window.open("/claim/ClaimPrint.do?printType=" + type + "&ClaimNo=" + claimNo, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
		return newWindow;
	}
	if (type == "Endcase") { // 18.结案报告书
		var newWindow = window.open("/claim/ClaimPrint.do?printType=" + type + "&ClaimNo=" + claimNo, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
		return newWindow;
	}
	if (type == "EndcaseAdd") { // 19.结案报告书附页
		var newWindow = window.open("/claim/ClaimPrint.do?printType=" + type + "&ClaimNo=" + claimNo, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
		return newWindow;
	}

}

function printForm1(filed, s, type) {
	if (type == "ClaimStatement") {
		var newWindow = window.open("/claim/print/claimPrint.do?printType=" + type + "&CompensateNo=" + s, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
		return newWindow;
	}
	if (type == "CompensateAdd") {
		var newWindow = window.open("/claim/ClaimPrint.do?printType=" + type + "&CompensateNo=" + s, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
		return newWindow;
	}
	if (type == "Drawnotice") {
		var newWindow = window.open("/claim/ClaimPrint.do?printType=" + type + "&CompensateNo=" + s, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
		return newWindow;
	}
}
