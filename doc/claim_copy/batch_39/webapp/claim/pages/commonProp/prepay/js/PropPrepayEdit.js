/*****************************************************************************
 * DESC       ：预赔登记的脚本函数页面
 * AUTHOR     ：中科軟
 * CREATEDATE ： 2004-10-11
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
	//add by liping 080709 增加保费是否实收提示
	var payFee = fm.prpLprepayFlag.value;
	var message = "";
	if (payFee == -1) {
		message = message + i18n.regist.useCaution; // 此保单保费未缴,请慎重处理！！！ \n
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
		location.href = location.href;
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
	//add by zhangyurui 2009-05-26 增加是否代付赔款判断 begin
	var isPayForOther = document.getElementsByName("isPayForOther");
	if (isPayForOther.length > 1 && isPayForOther[0].checked == false && isPayForOther[1].checked == false) {
		errorMessage = errorMessage + i18n.compensate.NotCompensation; // 请选择是否代付赔款\n
	}
	//add by zhangyurui 2009-05-26 增加是否代付赔款判断 end
	//add by luochang 2010-05-31 增加当支付方式为汇款时，支付帳户信息不能为空控制 begin
	var prpLCompensateOwnership = fm.prpLCompensateOwnership.value;
	var prpLCompensateAccountCode = fm.prpLCompensateAccountCode.value;
	var prpLCompensateOwnerNameCQ = fm.prpLCompensateOwnerNameCQ.value;
	if (prpLCompensateOwnership == "B" && prpLCompensateAccountCode == "") {
		errorMessage += i18n.prepay.advancePaymentInfoNotEmpty + "\n"; //预付赔款支付方式为汇款时，支付帳户信息不允许为空！\n
	} else if (prpLCompensateOwnership != "B" && prpLCompensateOwnerNameCQ == "") {
		errorMessage = errorMessage + i18n.compensate.paymentAccountNameNotEmpt + "\n"; //当标的损失赔款支付方式为现金或支票时，对应的支付对象姓名不允许为空！\n;
	}
	//add by luochang 2010-05-31 增加当支付方式为汇款时，支付帳户信息不能为空控制 end
	//textarea文本框设置值
	var context = fm.prpLptextContextInnerHTML.value;
	if (context.length < 1) {
		errorMessage = errorMessage + i18n.prepay.reportNotEmpty; //预赔报告不允许为空\n
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
	var sumClaim = fm.sumClaim.value;
	var percent = fm.percent.value;
	var underWriteFlag = fm.prpLprepayUnderWriteFlag.value;
	if (saveType == 4) {}
	//reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
	field.disabled = true;
	fm.submit();

	return true;
}



/**
 *@description 弹出查看留言页面
 *@param       无
 *@return      通过返回true,否则返回false
 */

function openWinQuery() {
	var win;
	var messagedo = "/claim/messageQueryList.do?claimNo=" + fm.prpLprepayClaimNo.value;
	win = window.showModalDialog(messagedo, "NewWindow", "status=no,resizable=yes,scrollbars=yes,width=500,Height=400");
}

//Modify By sunhao add begin 2004-09-06

/**
 *@description 弹出关联页面
 *@param       无
 *@return      通过返回true,否则返回false
 */
function relate() {
	var policyNo = fm.prpLprepayPolicyNo.value;
	var claimNo = fm.prpLprepayClaimNo.value;
	var newWindow = window.open("/claim/RelateBusinessNo.do?policyNo=" + policyNo + "&claimNo=" + claimNo, "NewWindow", "width=640,height=300,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
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

//按钮单击事件，用於相同保单号码多报案的显示

function buttonOnClick(strSubPageCode) {
	var sameCount = parseInt(fm.PerilCount.value);

	if (sameCount < 1) {
		fm.button_Peril_Open_Context.disabled = true;
		return;
	}
	showSubPage1(strSubPageCode);

}

//显示输入框
//leftMove 默认值0，坐标左移leftMove

function showSubPage1(spanID, leftMove) {
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