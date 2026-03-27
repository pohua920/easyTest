/*****************************************************************************
 * DESC       ：预赔登记的脚本函数页面
 * AUTHOR     ：liubvo
 * CREATEDATE ： 2004-05-13
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/
/**
 *@description 检查报案登记
 *@param       无
 *@return      通过返回true,否则返回false
 */

function checkForm() {
	return true;
}

/**
 *@description 设值页面的一些初始化信息
 *@param       无
 *@return      通过返回true,否则返回false
 */

function initSet() {
	var payFee = fm.prpLprepayFlag.value;
	var message = "";
	if (payFee == -1) {
		message = message + i18n.certainLoss.policyPremiumNoPay; //此保单保费未缴,请慎重处理！！！ \n
	} else if (payFee == -2) {
		message = message + i18n.certainLoss.policyPremiumPay; //此保单已缴未缴全,请慎重处理！！！ \n
	}
	if (message.length > 0) {
		alert(message);
	}

	return true;
}
/**
 *@description 设值页面的一些初始化信息
 *@param       无
 *@return      通过返回true,否则返回false
 */

function initShow() {
	return true;
}
/**
 *@description 提交
 *@param       无
 *@return      通过返回true,否则返回false
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
 *@description 清除
 *@param       无
 *@return      通过返回true,否则返回false
 */

function resetForm() {
	if (window.confirm("確定要清除嗎？")) {
		window.location.href = window.location.href;
		return true;
	} else {
		return false;
	}
}


/**
 *@description 根据按钮状态保存报案数据
 *@param       this
 *@param       保存状态
 *@return      通过返回true,否则返回false
 */

function saveForm(field, saveType) {
	var errorMessage = "";
	fm.buttonSaveType.value = saveType;
	if (saveType == "4") {
		//textarea文本框设置值
		var context = fm.prpLptextContextInnerHTML.value;
		if (context.length < 1) {
			errorMessage = errorMessage + "预赔报告不允许为空\n";
		}

		var caseType = fm.prpLprepayCaseType.value;
		var sumPrePaid = fm.prpLprepaySumPrePaid.value;
		var limitFeeHaveDuty = fm.limitfeeHaveDuty.value;
		var limitFeeNoneDuty = fm.limitfeeNoneDuty.value;

		if (caseType == '7') {
			if (parseFloat(sumPrePaid) > parseFloat(limitFeeHaveDuty)) {
				errorMessage = errorMessage + i18n.prepay.saveFeeAmountLimitatMedical + limitFeeHaveDuty + i18n.prepay.yuan + "\n"; //支付抢救费用金额不能超过强制保险有责医疗限额(        元) \n
				field.focus();

			}

		}
		if (caseType == '8') {
			if (parseFloat(sumPrePaid) > parseFloat(limitFeeNoneDuty)) {
				errorMessage = errorMessage + i18n.prepay.advancesRescueAmountAccountab + limitFeeNoneDuty + i18n.prepay.yuan + "\n"; // 垫付抢救费用金额不能超过强制保险无责医疗限额(                    元) \n
				field.focus();
			}
		}

		var prpLCompensateOwnership = fm.prpLCompensateOwnership.value;
		var prpLCompensateAccountCode = fm.prpLCompensateAccountCode.value;
		var prpLCompensateOwnerNameCQ = fm.prpLCompensateOwnerNameCQ.value;
		if (prpLCompensateOwnership == "B" && prpLCompensateAccountCode == "") {
			errorMessage += i18n.prepay.advancePaymentInfoNotEmpty + "\n"; //预付赔款支付方式为汇款时，支付帳户信息不允许为空！\n
		} else if (prpLCompensateOwnership != "B" && prpLCompensateOwnerNameCQ == "") {
			errorMessage = errorMessage + i18n.compensate.paymentAccountNameNotEmpt + "\n"; //当标的损失赔款支付方式为现金或支票时，对应的支付对象姓名不允许为空！\n
		}
		var sumClaim = fm.sumClaim.value;
		var percent = fm.percent.value;
		if (parseFloat(parseFloat(sumClaim) * parseFloat(percent) / 100) < parseFloat(fm.prpLprepaySumPrePaid.value)) {
			errorMessage = errorMessage + i18n.prepay.madeEstimatLossAmount + sumClaim + i18n.prepay.prestressLossAmount + percent + "%\n"; //立案估损金额为        ，预赔金额应该小於等於估损金额的
			alert(i18n.prepay.madeEstimatLossAmount + sumClaim + i18n.prepay.prestressLossAmount + percent + "%"); //立案估损金额为        ，预赔金额应该小於等於估损金额的
			return false;
		}
		var prePayFlag = fm.prePayFlag.value;
		if (prePayFlag == 0 && fm.prpLprepaySumPrePaid.value > 0 && saveType == "4") {
			errorMessage = errorMessage + i18n.prepay.insuranceCostNotSubmit + "\n"; //保费未实付，不允许预赔提交！\n
		}
		if (errorMessage.length > 0) {
			alert(errorMessage);
			return false;
		}
		//reason: ValidateData.js中的校验不起作用时，因为没有调用校验方法
		if (!validateForm(fm)) {
			return false;
		}
		//预赔金额<=估损金额的50%
		var underWriteFlag = fm.prpLprepayUnderWriteFlag.value;
		if (saveType == 4) {
			if (fm.ifInsuredName.value == "1") { //支付对象不是被保险人
				if (fm.exceptions.value == "9") {
					if (trim(fm.reason.value) == "") {
						alert(i18n.compensate.reasonsItemsEventCannotEmpty); //例外事项原因选择其他时，例外事项原因描述不能为空！
						return false;
					}
				}
				alert(i18n.compensate.SubjectEffectiveAudit); //须经领导（核赔人）审核生效！
			}
		}
	} else {
		var prpLprepayHandlerCode = document.getElementsByName("prpLprepayHandlerCode");
		if (prpLprepayHandlerCode.length > 0 && prpLprepayHandlerCode[0].value == "") {
			alert("服務人員不能為空!");
			return false;
		}
	}
	//如果是提交，判断是否不是nextNodeNo或者人员为空！！！！
	if (saveType == "4") {}

	//reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
	field.disabled = true;
	fm.submit();
	return true;
}

/**
 *@description 实赔任务复核
 *@param       无
 *@return      通过返回true,否则返回false
 */

function approveSubmit() {
	fm.buttonApprove.disabled = true;
	fm.submit();
	return true;
}

/**
 *@description 处理索赔资料清单
 *@param       businessNo
 */

function doCertifyDirect(businessNo, nodeType) {
	window.open("/claim/certifyBeforeEdit.do?RegistNo=" + businessNo + "&editType=CertifyDirect&nodeType=" + nodeType, "winName", "resizable=0,scrollbars=1,width=800,height=600");
}

/**
 *@description 特殊赔案处理中，垫付、支付抢救费用限制不能超过强制无责医疗限额
 *@param
 */

function checkBeyondLimitFee(field) {

	var caseType = fm.prpLprepayCaseType.value;
	var sumPrePaid = fm.prpLprepaySumPrePaid.value;
	var limitFeeHaveDuty = fm.limitfeeHaveDuty.value;
	var limitFeeNoneDuty = fm.limitfeeNoneDuty.value;

	if (caseType == '7') {
		if (parseFloat(sumPrePaid) > parseFloat(limitFeeHaveDuty)) {
			alert(i18n.prepay.saveFeeAmountLimitatMedical + limitFeeHaveDuty + i18n.prepay.yuan + "\n"); // 支付抢救费用金额不能超过强制保险有责医疗限额(       元)
			field.focus();
		}

	} else if (caseType == '8') {
		if (parseFloat(sumPrePaid) > parseFloat(limitFeeNoneDuty)) {
			alert(i18n.prepay.advancesRescueAmountAccountab + limitFeeNoneDuty + i18n.prepay.yuan + "\n"); //垫付抢救费用金额不能超过强制保险无责医疗限额(    元)
			field.focus();
		}
	}

}

function buttonOnClick3(fieldObject) {
	var intIndex = parseInt(getElementOrder(fieldObject) - 1);
	var spanId = 'span_Engage_Context00';
	if (isNaN(fm.button_Engage_Open_Context00.length)) {} else { //多行
		spanId = 'span_Engage_Context00' + "[" + intIndex + "]";
	}
	showSubPage3(spanId);
}

//显示输入框
//leftMove 默认值0，坐标左移leftMove

function showSubPage3(spanID, leftMove) {
	var intLeftMove = (leftMove == null ? 0 : leftMove);
	var span = eval(spanID);
	var strTemp = span.id;

	var ex = window.event.clientX + document.body.scrollLeft; //得到事件的坐标x
	var ey = window.event.clientY + document.body.scrollTop; //得到事件的坐标y

	ex = ex - 520;

	if (ex < 0) {
		ex = 0;
	}
	ex = ex - intLeftMove;

	span.style.left = ex;
	span.style.top = ey;
	span.style.display = '';
}