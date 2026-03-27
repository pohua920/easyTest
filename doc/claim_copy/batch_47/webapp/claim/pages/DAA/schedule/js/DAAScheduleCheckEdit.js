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
 *@description 设值页面的一些初始化信息
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
	//调用特殊检查
	if (!checkscheduleForm(fm)) {
		return false;
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
	var saveType = fm.saveType.value; //是取回还是直接调度
	//判断调度对象和调度名称的
	if (((fm.prpLscheduleMainWFScheduleObjectID.value).toString().length) < 1) {
		errorMessage("查勘处理单位编码不能为空!");
		fm.prpLscheduleMainWFScheduleObjectID.focus();
		return false;
	}
	if (((fm.prpLscheduleMainWFScheduleObjectName.value).toString().length) < 1) {
		errorMessage("查勘处理单位名称不能为空!");
		fm.prpLscheduleMainWFScheduleObjectName.focus();
		return false;
	}

	//如果不是取回的方式，则需要判断是不是选择了下一个节点上的信息
	if (saveType == "schedule") {
		if (((fm.nextNodeNo.value).toString().length) < 1) {
			errorMessage("提交的下一个节点信息不能为空!");
			return false;
		}

		if (((fm.nextHandlerCode.value).toString().length) < 1) {
			errorMessage("提交的下一个人信息不能为空!");
			return false;
		}
	}
	return true;
}

/**
 *@description 弹出查看留言页面
 *@param       无
 *@return      通过返回true,否则返回false
 */

function openWinQuery() {
	var win;
	var messagedo = "/claim/messageQueryList.do?registNo=" + fm.prpLscheduleMainWFRegistNo.value;
	win = window.showModalDialog(messagedo, "NewWindow", "status=no,resizable=yes,scrollbars=yes,width=500,Height=400");
}