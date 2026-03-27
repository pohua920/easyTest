/*****************************************************************************
 * DESC       ：调度登记的脚本函数页面
 * AUTHOR     ：中科軟
 * CREATEDATE ： 2004-07-26
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/
/**
 *@description 提交
 *@param       无
 *@return      通过返回true,否则返回false
 */

function submitForm() {
	if (checkForm() == false) {
		return false;
	}

	fm.buttonSaveFinish.disabled = true;
	fm.buttonSaveFinishSubmit.disabled = true;
	fm.buttonSave.disabled = true;
	fm.submit();
	return true;
}

/**
 *@description 清除
 *@param       无
 *@return      通过返回true,否则返回false
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
 *@description 设值页面的一些初始化訊息
 *@param       无
 *@return      通过返回true,否则返回false
 */

function initSet() {
	return true;
}

/**
 *@description 根据按钮状态保存调度数据
 *@param       this
 *@param       保存状态
 *@return      通过返回true,否则返回false
 */

function saveForm(field) {
	if (!validateForm(fm)) {
		return false;
	}
	if (fm.editType.value != "GETBACKEDIT" && (fm.editType.value == "EDIT" || fm.isChecked.value == "true")) {
		var maxRow = fm.maxrow.value;
		if ((maxRow == 0) || !(fm.checkYesNo.length > 0))
			if (fm.checkYesNo.checked == false) {
				alert(i18n.schedule.mustSelectScheduleInfo) //必须选中一条调度的标的訊息，才可以保存调度訊息！
				return false;
			}

	}

	//调用特殊检查
	if (fm.saveType.value == "GETBACKEDIT") {
		if (!CheckGetBackSchedule()) return false;

	} else {
		if (!checkscheduleForm(fm)) {
			return false;
		}
	}
	//add by liyanjie 2005-12-12 start 双代案件,调度必须全部处理才能提交
	if (fm.prpLscheduleMainWFDtoCommiFlag.value == "1") {
		if (!allDeal(fm)) {
			alert(i18n.schedule.generationCaseSubmitAgain); //此案件为双代案件,请全部处理所有项目再提交!
			return false;
		}
	}
	//reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
	field.disabled = true;
	fm.submit();
	return true;
}
/**
 *@description 检查调度数据
 *@param       this
 *@param       检查状态
 *@return      通过返回true,否则返回false
 */

function checkscheduleForm(field) {
	//1.检查是否选择了一个checkBox
	//2.检查选择了checkbox的查勘訊息和出险地址都需要填内容。	
	var i = 0;
	var j = 0;
	var selectCount = 0; //选择的行数

	var maxRow = fm.maxrow.value; //定损标的的数目
	var saveType = fm.saveType.value; //是取回还是直接调度
	var scheduleObjectID = "";
	//1。首先检查的是定损调度是不是有选择的内容
	//只有一行，判断是不是选择了查勘调度
	//没有查勘，只需要判断定损就可以了
	if (fm.nocheck.value == "1") {

		fm.scheduleType.value = "schel"; //只有定损
		if ((maxRow == 1) || !(fm.checkYesNo.length > 0)) {
			//判断是否被选中，若没有选中，那么就可以直接进行下一条记录的判断。
			if (fm.checkYesNo.checked == false) {
				errorMessage(i18n.schedule.mustSelectScheduleInfo) //必须选中一条调度的标的訊息，才可以保存调度訊息！
				return false;
			}
			return CheckOnlySchel();
		}

		//多条的定损调度的判断
		return CheckMulitSchel();

	}
	//以下都是有查勘参与的页面
	if (fm.checkScheduleCheckYesNo.checked == true) {
		//表示被选中，需要进行查勘调度
		fm.checkSelectSend.value = "1";

		if (((fm.prpLscheduleMainWFScheduleObjectID.value).toString().length) < 1) {
			errorMessage("查勘處理單位編碼不能為空!");
			return false;
		}

		if (((fm.prpLscheduleMainWFScheduleObjectName.value).toString().length) < 1) {
			errorMessage("查勘處理單位名稱不能為空!");
			return false;
		}

	}
	if (fm.checkYesNo == null || fm.checkScheduleCheckYesNo == null) {
		return true;
	} else {
		if ((maxRow == 1) || !(fm.checkYesNo.length > 0)) {
			//判断是否被选中，若没有选中，那么就可以直接进行下一条记录的判断。
			if ((fm.checkYesNo.checked == false) && (fm.checkScheduleCheckYesNo.checked == false)) {
			errorMessage("必須選中一條分案的標的訊息，才可以保存分案訊息！")
				return false;
			}
			//只选择了定损调度，判断，请先进行查勘调度！
		var $checkYesNos = $("input:checked[name='checkYesNo']");
		if (($checkYesNos.length > 0) && (fm.checkScheduleCheckYesNo.checked == false)) {
			errorMessage("定損分案時，也需要選擇查勘分案！")
				return false;
			}
			if (fm.checkYesNo.checked == true) {
				//单条定损调度的判断
				return CheckOnlySchel();

			}

			return true;
		}
	}
	//多条的定损调度的判断
	return CheckMulitSchel();

}


//将变化了的现场类型保存到响应字段中

function setSurveyType(fieldObject) {

	var intIndex = parseInt(fieldObject.num);
	var recordCount = fm.maxrow.value;
	if (recordCount <= 1) {
		fm.prpLscheduleItemSurveyType.value = fieldObject.value;
	} else {
		fm.prpLscheduleItemSurveyType[intIndex].value = fieldObject.value;

	}

}

/**
 *检查单条的定损调度的内容是不是可以输入的内容为空
 *
 */

function CheckOnlySchel() {
	//设置selectSend的内容
	fm.prpLscheduleItemSelectSend.value = "1";
	//默认设置节点类型为定损
	//判断调度对象和调度名称的
	if (((fm.prpLscheduleItemScheduleObjectID.value).toString().length) < 1) {
		errorMessage(i18n.schedule.feeProcessCodeCannotEmpty); //定损处理单位编码不能为空!
		return false;
	}

	if (((fm.prpLscheduleItemScheduleObjectName.value).toString().length) < 1) {
		errorMessage(i18n.schedule.feeProcessNameCannotEmpty); //定损处理单位名称不能为空!
		return false;
	}

	//判断定损地址
	if (((fm.prpLscheduleItemCheckSite.value).toString().length) < 1) {
		errorMessage(i18n.schedule.feeAddressNotEmpty); //定损地址不能为空!
		return false;
	}
	//add by liping 查勘，定损都已经调度，再无新调度任务情况下的控制
	if (fm.checkYesNo.checked == true && fm.checkYesNo.disabled && fm.nocheck.value == "0") {
		var message = "系統訊息:\n\n";
		message = message + i18n.schedule.noNewFeeNotAllowSchedule; //没有新的定损调度任务，不允许再进行调度！
		alert(message);
		return false;
	}

	return true;
}

//选中复选框後，设置prpLCheckSelectSend值为1

function setSmcSend() {
	var intCount = fm.txtScheduleSmcSend.length;
	if (isNaN(fm.txtScheduleSmcSend.length)) {
		fm.prpLCheckSelectSend.value = "1";
	} else {
		for (i = 0; i < intCount; i++) {
			if (fm.txtScheduleSmcSend[i].checked == true) {
				fm.prpLCheckSelectSend[i].value = "1";
			} else {
				fm.prpLCheckSelectSend[i].value = "0";
			}
		}
	}
}

/**
 *多条定损是否选择正确的判断
 */

function CheckMulitSchel() {
	var i = 0;
	var j = 0;
	var selectCount = 0; //选择的行数

	var maxRow = fm.maxrow.value; //定损标的的数目
	var saveType = fm.saveType.value; //是取回还是直接调度
	var scheduleObjectID = "";
	//多条的定损调度的判断

	for (i = 0; i < maxRow; i++) {
		//判断是否被选中，若没有选中，那么就可以直接进行下一条记录的判断。只要有没选中，就认为是没调度完的

		if (fm.checkYesNo[i].checked == false) {
			scheduleObjectID = fm.prpLscheduleItemScheduleObjectID[i].value;
			if (scheduleObjectID.toString().length < 1) {}
			fm.prpLscheduleItemSelectSend[i].value = "0";

			continue;
		}
		//一定要写清楚呀。。。。
		if (fm.checkYesNo[i].disabled == true) continue; //说明已经调度过了

		selectCount++;

		//设置selectSend的内容
		fm.prpLscheduleItemSelectSend[i].value = "1";
		if (fm.prpLscheduleItemItemNo[i].value == "1" && fm.nocheck.value == "0") {
			//如果选则了主车，判断是不是选择查看了。。

			if (fm.checkScheduleCheckYesNo.checked == false) {
				errorMessage("標的車輛，選擇定損分案時，也需要選擇查勘分案！")
				return false;
			}
		}

		if (((fm.prpLscheduleItemScheduleObjectID[i].value).toString().length) < 1) {

			errorMessage("定損處理單位編碼不能為空!");
			return false;
		}

		if (((fm.prpLscheduleItemScheduleObjectName[i].value).toString().length) < 1) {
			errorMessage("定損處理單位名稱不能為空!");
			return false;
		}

		//判断查勘地址
		if (((fm.prpLscheduleItemCheckSite[i].value).toString().length) < 1) {
			if (fm.nextNodeNo[i].value == "wound") {
//				return true;
				//errorMessage("第" + (i + 1) + "條,就醫醫院不能為空!");
			} else {
				errorMessage("第" + (i + 1) + "條,定損地址不能為空!");
				return false;
			}
		}

	}

	//判断是不是没有选择一个checkbox
	var finishSchedule = fm.prpLscheduleItemFinishSchedule.value;

	if (fm.nocheck.value == "1") {
		if (selectCount < 1 && finishSchedule == "" && (saveType == "")) {
			errorMessage("必須選中一條分案的標的訊息，才可以保存分案訊息！")
			return false;
		}
	} else {

		//查勘没有调度的情况，进行判断
		if ((selectCount < 1) && (finishSchedule == "") && (fm.checkScheduleCheckYesNo.checked == false) && (saveType == "")) {
			errorMessage("必須選中一條分案的標的訊息，才可以保存分案訊息！")
			return false;
		}
		//查勘调度完毕的情况,没有选择定损调度
		if ((selectCount < 1) && (finishSchedule == "") && (fm.checkScheduleCheckYesNo.disabled == true) && (saveType == "")) {
			errorMessage("必須選中一條分案的標的訊息，才可以保存分案訊息！")
			return false;
		}
	}
	var $checkYesNos = $("input:checked[name='checkYesNo']");
	if (($checkYesNos.length > 0) && (fm.checkScheduleCheckYesNo.checked == false)) {
		errorMessage("定損分案時，也需要選擇查勘分案！")
		return false;
	}
	return true;
}

/**
 *检查调度改派
 */

function CheckGetBackSchedule() {
	if (fm.getbackNodeType.value == "check") {
		if (((fm.prpLscheduleMainWFScheduleObjectID.value).toString().length) < 1) {

			errorMessage("查勘處理單位編碼不能為空!");
			return false;
		}

		if (((fm.prpLscheduleMainWFScheduleObjectName.value).toString().length) < 1) {

			errorMessage("查勘處理單位名稱不能為空!");
			return false;
		}

		return true;
	} else {
		return CheckOnlySchel();
	}
}


//用来改派调度用的

function setNewHandlerCode(fieldObject) {
	fm.newHandlerCode.value = fieldObject.value
}

//用来筛选调度的经办人用的

function setNewComCode(fieldObject) {
	fm.selectcomcode.value = fieldObject.value
	//判断是选择了查勘的处理单位，还是定损调度的处理单位
	var intIndex = parseInt(fieldObject.num);
	//选择查勘处理单位
	if (intIndex < 0) {
		fm.nextHandlerCode1.value = "";
		fm.nextHandlerName1.value = "";
		return
	}

	var maxRow = fm.maxrow.value
	//选择定损处理单位，並且定损调度只一条记录
	if (maxRow == '1') {
		fm.nextHandlerCode.value = "";
		fm.nextHandlerName.value = "";
		return
	}
	fm.nextHandlerCode[intIndex].value = "";
	fm.nextHandlerName[intIndex].value = "";

}

//用来筛选调度的经办人用的

function setNewHandlerComCode(fieldObject) {

	//判断是选择了查勘的处理单位，还是定损调度的处理单位
	var intIndex = parseInt(fieldObject.num);

	//选择查勘处理单位
	if (intIndex < 0) {
		fm.selectcomcode.value = fm.prpLscheduleMainWFScheduleObjectID.value;
		return
	}
	var maxRow = fm.maxrow.value
	//选择定损处理单位，並且定损调度只一条记录
	if (maxRow == '1') {
		fm.selectcomcode.value = fm.prpLscheduleItemScheduleObjectID.value;
		return
	}
	fm.selectcomcode.value = fm.prpLscheduleItemScheduleObjectID[intIndex].value;
}

/**
 *@description 弹出报案的画面
 *@param       无
 *@return      通过返回true,否则返回false
 */

function relateRegist() {
	var registNo = fm.prpLscheduleMainWFRegistNo.value;
	var linkURL = "/claim/registFinishQueryList.do?prpLregistRegistNo=" + registNo + "&editType=SHOW&riskCode=" + fm.prpLscheduleMainWFRiskCode.value;
	var newWindow = window.open(linkURL, "NewWindow", "width=640,height=500,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
}

/**
 *@description 申请双代  add by liyanjie 2005-12-06
 *@param       无
 *@return      通过返回true,否则返回false
 */

function applyCommi(field) {

	if (!confirm("您確認要將案件提交雙代人員處理嗎？Y/N 提交後，將清空您做的查勘定損分案的內容.")) {
		return false;
	}
	fm.CheckBoxCommiFlag.checked = true;
	fm.checkScheduleCheckYesNo.checked = false;
	var count = getElementCount('checkYesNo');
	if (count == 0) {} else if (count == 1) {
		fm.checkYesNo.checked = false;
	} else {
		for (var i = 0; i < fm.checkYesNo.length; i++) {
			fm.checkYesNo[i].checked = false;
		}
	}
	//将双代标识置为已选择,将查勘定损的调度清空
	field.disabled = true;

	fm.action = "/claim/processCommiCase.do?actionType=apply";
	fm.submit();
}


//判断是否所有项目都做了调度,对於双代的案件,必须全部处理才能提交

function allDeal(fm) {
	var allDealFlag = true;
	var maxRow = fm.maxrow.value; //定损标的的数目

	//没有查勘，只需要判断定损就可以了
	if (fm.nocheck.value == "1") {

	} else {
		if (fm.checkScheduleCheckYesNo.checked == false) {
			allDealFlag = false;
			return allDealFlag;
		}
	}
	if ((maxRow == 1) || !(fm.checkYesNo.length > 0)) {
		if (fm.checkYesNo.checked == false) {
			allDealFlag = false;
			return allDealFlag;
		}
	} else {
		for (i = 0; i < maxRow; i++) {
			if (fm.checkYesNo[i].checked == false) {
				allDealFlag = false;
				return allDealFlag;
			}
		}
	}

	return allDealFlag;
}

//生成查勘重点提示

function generateCheckText() {
	if (fm.prpLdrivername != null) {
		var DriverName = fm.prpLdrivername.value; //标的车驾驶员姓名
		var DriverLicenseNo = fm.prpLdriverLicenseNo.value; //标的车车牌号
		var PolicyNo = fm.prpLscheduleMainWFPolicyNo.value;
		var CheckDate = fm.prpLscheduleMainWFInputDate.value; //调度日期
		var DamageDate = fm.prpLregistDamageStartDate.value; //出险日期
		var CheckHour = parseInt(fm.prpLscheduleInputHour.value) + 1;
		var DamageHour = fm.prpLregistDamageStartHour.value; //出现时间
		var SchedHour = fm.prpLscheduleInputHour.value;
		var SchedMinit = fm.prpLscheduleInputMinute.value;
		var CheckMinit = parseInt(fm.prpLscheduleInputMinute.value) + 30;
		var CheckAddress = fm.prpLscheduleMainWFCheckSite.value;
		var LinkerName = fm.prpLscheduleMainWFLinkerName.value;
		var LinkerPhoneNo = fm.prpLscheduleMainWFPhoneNumber.value;
		var prplCheckText1 = DriverName + "駕駛標的車" + 　DriverLicenseNo + "於" + DamageDate + "日" + DamageHour + "時發生事故，請於" + CheckDate + "日" + CheckHour + "時前抵達" + CheckAddress + "進行現場查勘。";
		fm.prpLscheduleMainWFCheckInfo.value = prplCheckText1;
	}
}