/*****************************************************************************
 * DESC       ：报案登记的脚本函数页面
 * AUTHOR     ：weishixin
 * CREATEDATE ： 2013-09-10
 * MODIFYLIST ：   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/
/**
 *@description 设值页面的一些初始化信息
 *@param       无
 *@return      通过返回true,否则返回false
 */
function initSet() {
	//判断是否是共保、临分、股东业务信息
	var coinsFlag = fm.coinsFlag.value;
	var shareHolderFlag = fm.shareHolderFlag.value;
	var tempReinsFlag = fm.tempReinsFlag.value;
	var message = "";
	//add by qinyongli 增加保单注销股东业务，出险次数等提示； 2005-7-28
	var coinsFlag = fm.coinsFlag.value;
	var tempReinsFlag = fm.tempReinsFlag.value;
	var shareHolderFlag = fm.shareHolderFlag.value;
	var message = "";

	var othFlag = fm.prpLclaimOthFlag.value;
	if (othFlag.substring(3, 4) == "1") {
		message = message + i18n.claim.policyCancelled; //此保单已注销！\n
	}
	var payFee = parseInt(fm.payFee.value);
	var delinquentfeeCase = fm.delinquentfeeCase.value;

	if (payFee == -1) {
		alert(i18n.claim.premiumNotPaid); //此保单保费未缴！\n
	} else if (payFee == 0) {
		alert(i18n.claim.premiumNotComplete); //此保单保费未缴全！\n
	} else if (payFee == -2) {
		message = message + i18n.certainLoss.policyPremiumPay; //此保单已缴未缴全,请慎重处理！！！ \n
		message = message + delinquentfeeCase + "\n";
	}
	if (coinsFlag != 0) {
		message = message + i18n.claim.totalPolicy; //本保单为联/共保单！\n
		message = message + i18n.claim.estimateAmount; //估损金额请輸入分摊前的总估损金额！\n
	}
	if (message.length > 0) {
		alert(message);
	}
	//   add by wzy 20090410  start 
	if (fm.BaseCurrency1.value != CURRENCYINFO.LOCAL_CURRENCY) {
		alert(i18n.claim.singleCurrency + fm.BaseCurrency1.value + i18n.claim.useSparingly + fm.ExchRate1.value); //此案件签单币别为   //，不是CNY，请慎重处理！\n当前兑换率为
	}
	//   add by wzy 20090410  end 
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
	if (window.confirm(i18n.button.confirmClear)) {//確定要清除嗎？
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
	//add by luochang at 2010-10-08 提交时对送审进行判断
	if (saveType == "4" && checkUndwrt() == false) {
		return false;
	}

	var errorMessage = "";
	fm.buttonSaveType.value = saveType;
	//add by zhangyurui 2009-03-26 立案提交增加险别不能为空的控制 begin
	var kindList = document.getElementsByName("prpLclaimLossKindCode");
	var kindNameList = document.getElementsByName("prpLclaimLossKindName");
	for (var i = 1; i < kindList.length; i++) {
		if (kindList[i].value == "" || kindNameList[i].value == "") {
			errorMessage = errorMessage + i18n.claim.risksCannotEmpty // 险别不能为空!\n
			break;
		}
	}
	//add by zhangyurui 2009-03-26 立案提交增加险别不能为空的控制 end
	//add by zhangyurui 2009-03-23 立案环节提交增加危险单位不能为空的控制 begin
	var dangerNoList = document.getElementsByName("prpLclaimLossDangerNo");
	for (var i = 0; i < dangerNoList.length; i++) {
		if (dangerNoList[i].value == null || dangerNoList[i].value == "") {
			errorMessage = errorMessage + i18n.claim.dangerousCannotEmpty //危险单位不能为空!\n
			break;
		}
	}
	//add by zhangyurui 2009-03-23 立案环节提交增加危险单位不能为空的控制 end
	//add by caozhigang 2009-03-17 如果輸入了一级巨灾代码，那么二级代码也必须輸入 begin
	var prpCatastropheCode1 = fm.prpCatastropheCode1.value;
	var prpCatastropheName1 = fm.prpCatastropheName1.value;
	var prpCatastropheCode2 = fm.prpCatastropheCode2.value;
	var prpCatastropheName2 = fm.prpCatastropheName2.value;
	if (!((prpCatastropheCode1.length > 0 && prpCatastropheName1.length > 0 && prpCatastropheCode2.length > 0 && prpCatastropheName2.length > 0) //一级二级都輸入
	|| (prpCatastropheCode1.length < 1 && prpCatastropheCode2.length < 1 && prpCatastropheName1.length < 1 && prpCatastropheName2.length < 1))) //一级二级都不輸入
	{
		errorMessage = errorMessage + i18n.certify.selectOther; //如果要輸入巨灾代码，请将一级代码和二级代码都录完整，没有二级代码的，请选择“其他”\n
	}
	if (prpCatastropheCode2.length > 0 && prpCatastropheCode1) {
		if (prpCatastropheCode2.length < 12) {
			if (prpCatastropheCode2.length == 7 && prpCatastropheCode2.substring(0, 2) == prpCatastropheCode1) {
				//允许选择“其他”
			} else {
				errorMessage = errorMessage + i18n.certify.accidentCases; //今年出险的案件，不能选择以前的巨灾代码！
				//以前的编码不规范，只能模糊提示
			}
		} else {
			var year = prpCatastropheCode2.substring(0, 4);
			var prpLclaimDamageStartDate = fm.prpLclaimDamageStartDate.value.substring(0, 4);
			if (year != prpLclaimDamageStartDate) {
				errorMessage = errorMessage + prpLclaimDamageStartDate + i18n.compensate.select + year + i18n.compensate.codes; //  年出险的案件，不能选择    //年的巨灾代码！
			}
		}
	}
	//add by caozhigang 2009-03-17 如果輸入了一级巨灾代码，那么二级代码也必须輸入 end
	//textarea文本框设置值
	var context = fm.prpLltextContextInnerHTML.value;
	if (context.length < 1) {
		errorMessage = errorMessage + i18n.claim.dangerNotAllowEmpty; //出险摘要不允许为空\n
	}

	if (isEmptyField(fm.prpLextSalvor)) {
		errorMessage = errorMessage + i18n.claim.notAllowedEmpty; //货主名称不允许为空，请先完成查勘任务\n
	}

	if (errorMessage.length > 0) {
		alert(errorMessage);
		return false;
	}
	//add by qingyongli 2005/07/22 
	var claim_days = fm.claim_days.value;
	var standardDays = fm.standardDays.value;
	if (claim_days == 0) {
		if (!confirm(i18n.claim.caseGreaterSystemTime + standardDays + i18n.claim.dayWhetherSubmit)) {//立案天数大於系统规定时间		天，是否提交？
			return false;
		}
	}
	if (fm.all("prpLclaimLossKindCode").length == undefined || fm.all("prpLclaimLossKindCode").length < 1) {
		alert(i18n.claim.enterInformation); //请输入估损信息！
		return false;
	}
	//modify by weishixin add begin 20040616

	//add by zhulei begin 20050716 出险时间不能早於起运时间的比较校验
	if (fm.prpLclaimDamageStartDate.value < fm.prpLclaimStartDate.value) {
		alert(i18n.claim.error); //错误！出险时间不能早於起运时间
		return false;
	}
	//add by zhulei end 20050716 出险时间不能早於起运时间的比较校验

	//reason: ValidateData.js中的校验不起作用时，因为没有调用校验方法
	//modify by qinyongli  ClaimFee_Data 改为 ClaimLoss_Data
	if (!validateForm(fm, "ClaimLoss_Data")) {
		return false;
	}
	//计算估损金额
	if (!collectClaimFee()) {
		return false;
	}
	//add by caozhigang 20090415 start
	var sumAmount = parseFloat(fm.prpLclaimSumAmount.value);
	var sumClaim = parseFloat(fm.prpLclaimSumClaim.value);
	if (sumClaim > sumAmount) {
		alert(i18n.claim.sumInsured); //估损金额不能大於保额！
		return false;
	}
	if (sumClaim < 0) {
		alert(i18n.claim.lessZero); //估损金额不能小於零！
		return false;
	}
	//add by caozhigang 20090415 start

	//reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
	field.disabled = true;
	//modify by weishixin add end 20040616
	fm.submit();

	return true;
}

//装载数据

function loadForm() {
	//alert("set cadsfsdfl");
	//fm.carKindCode.disabled = true;
	//fm.carKindCode.readOnly = true;
	//alert("set carKindCode");
}

/**
 *@description 弹出留言保存页面
 *@param       无
 *@return      通过返回true,否则返回false
 */

function openWinSave() {

	var businessNo = fm.prpLclaimRegistNo.value;
	var policyNo = fm.prpLclaimPolicyNo.value;
	var riskCode = fm.prpLclaimRiskCode.value;
	var claimNo = fm.prpLclaimClaimNo.value;
	msg = window.open("/claim/messageQueryInfo.do?businessNo=" + businessNo + "&nodeType=claim&policyNo=" + policyNo + "&riskCode=" + riskCode + "&claimNo=" + claimNo, "NewWindow", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,width=500,Height=300");
}

/**
 *@description 弹出查看留言页面
 *@param       无
 *@return      通过返回true,否则返回false
 */

function openWinQuery() {
	var win;
	var messagedo = "/claim/messageQueryList.do?registNo=" + fm.prpLclaimRegistNo.value;
	win = window.showModalDialog(messagedo, "NewWindow", "status=no,resizable=no,scrollbars=yes,width=600,Height=300");
}

//Modify By sunhao add begin 2004-09-06
/**
 *@description 弹出关联页面
 *@param       无
 *@return      通过返回true,否则返回false
 */
function relate() {
	var policyNo = fm.prpLclaimPolicyNo.value;
	var registNo = fm.prpLclaimRegistNo.value;
	var newWindow = window.open("/claim/RelateBusinessNo.do?policyNo=" + policyNo + "&registNo=" + registNo, "NewWindow", "width=640,height=300,top=0,left=0,toolbar=yes,location=no,directories=no,menubar=no,scrollbars=yes,resizable=yes,status=no");
}


/**
 @description 校验索赔金额
 @param       无
 @return      boolean
 */
function checkFee() {

	if (getRowsCount("ClaimLoss") == 0) {
		errorMessage(i18n.claim.oneRecord); //估损金额金额信息至少要有一条记录!

		return false;
	}
	//alert (fm.prpLclaimFeeCurrency.length);

	for (var j = 1; j < fm.prpLclaimLossCurrency.length; j++) {
		if (isEmptyField(fm.prpLclaimLossCurrency[j])) {
			errorMessage(i18n.claim.cannotEmpty); //第"+ j+"条估损金额中币别不能为空!
			fm.prpLclaimLossCurrency[j].focus();
			return false;
		}

		for (var n = j + 1; n < fm.prpLclaimLossCurrency.length; n++) {
			if ((fm.prpLclaimLossKindCode[n].value == fm.prpLclaimLossKindCode[j].value) && (fm.prpLclaimLossCurrency[n].value == fm.prpLclaimLossCurrency[j].value) && (fm.prpLclaimLossItemCode[n].value == fm.prpLclaimLossItemCode[j].value) && (fm.prpLclaimLossLossFeeType[n].value == fm.prpLclaimLossLossFeeType[j].value)) {
				errorMessage(i18n.compel.first + n + i18n.commonShip.claim.estimatLossAmountCost + j + i18n.commonShip.claim.estimatLossAmountCost2); //第 //条估损金额中险别、项目、币别、费用类别和第     // 条估损金额中险别、项目、币别、费用类别一致，请合並这2条记录

				//fm.prpLclaimLossCurrency[n].focus();
				return false;
			}
		}

		if (isEmptyField(fm.prpLclaimLossSumClaim[j])) {
			errorMessage(i18n.compel.first + j + i18n.certainLoss.NotEmpty); //第     //条事故估损金额中金额不能为空!
			fm.prpLclaimLossSumClaim[j].focus();
			return false;
		}
	}
	return true;
}

//得到一页的多行纪录的记录数
//页名称

function getRowsCount(PageCode) {
	var oTBODY = document.all(PageCode).tBodies.item(0);
	var intCount = oTBODY.rows.length;
	return intCount;
}

/**
 @description 汇总估损金额
 @param       无
 @return      无
 */
function collectClaimFee() {
	var compAmout = 0;
	var nowAmout = 0;
	if (!checkFee()) return false;
	//循环计算 估金额
	for (var n = 1; n < fm.prpLclaimLossSumClaim.length; n++) {
		nowAmout = parseFloat(fm.prpLclaimLossSumClaim[n].value)
		compAmout = compAmout + nowAmout;
	}

	fm.prpLclaimSumClaim.value = pointTwo(compAmout);
	fm.prpLdangerRiskSumClaim.value = pointTwo(compAmout);

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


//Modify By dongcl remark begin 20050623
/**
 @description 汇总估损金额
 @param       无
 @return      无
 */
function collectClaimLoss() {
	var compAmout = 0;
	var nowAmout = 0;
	var exchCurrency = fm.prpLclaimCurrency.value; //得到当前币别类型
	var exchRate = 1; //兑换率

	if (!checkLoss()) return false;
	//循环计算 估金额
	for (var n = 1; n < fm.prpLclaimLossSumClaim.length; n++) {
		nowAmout = parseFloat(fm.prpLclaimLossSumClaim[n].value)

		for (j = 1; j < fm.baseCurrency.length; j++) {
			if (fm.baseCurrency[j].value == fm.prpLclaimLossCurrency[n].value && fm.exchCurrency[j].value == exchCurrency) {
				exchRate = fm.exchRate[j].value;
			}
		}
		compAmout = compAmout + nowAmout * exchRate;
	}

	fm.prpLclaimSumClaim.value = pointTwo(compAmout);
	fm.prpLdangerRiskSumClaim.value = pointTwo(compAmout);
	return true;
}


/**
 @description 校验索赔金额
 @param       无
 @return      boolean
 */
function checkLoss() {
	//1.检查必须要有一条记录
	if (getRowsCount("ClaimLoss") == 0) {

		errorMessage(i18n.claim.oneRecord); //估损金额金额信息至少要有一条记录!

		return false;
	}
	//2币别不能为空的

	for (var j = 1; j < fm.prpLclaimLossCurrency.length; j++) {
		if (isEmptyField(fm.prpLclaimLossCurrency[j])) {
			errorMessage(i18n.compel.first + j + i18n.certainLoss.amountCannot); // 第  //  条估损金额中币别不能为空!
			fm.prpLclaimLossCurrency[j].focus();
			return false;
		}

		if (isEmptyField(fm.prpLclaimLossKindCode[j])) {
			errorMessage(i18n.compel.first + j + i18n.certainLoss.risksCode); // 第      //条事故估损金额中险别代码不能为空!
			//fm.prpLclaimLossKindCode[j].focus();
			return false;
		}

		//险别和币别相同的，必须合並
		for (var n = j + 1; n < fm.prpLclaimLossCurrency.length; n++) {
			if ((fm.prpLclaimLossKindCode[n].value == fm.prpLclaimLossKindCode[j].value) && (fm.prpLclaimLossCurrency[n].value == fm.prpLclaimLossCurrency[j].value) && (fm.prpLclaimLossItemCode[n].value == fm.prpLclaimLossItemCode[j].value) && (fm.prpLclaimLossLossFeeType[n].value == fm.prpLclaimLossLossFeeType[j].value)) {
				errorMessage(i18n.compel.first + n + i18n.commonShip.claim.estimatLossAmountCost + j + i18n.commonShip.claim.estimatLossAmountCost2); //第 //条估损金额中险别、项目、币别、费用类别和第     // 条估损金额中险别、项目、币别、费用类别一致，请合並这2条记录

				fm.prpLclaimLossKindCode[n].focus();
				return false;
			}
		}

		if (isEmptyField(fm.prpLclaimLossSumClaim[j])) {
			errorMessage(i18n.compel.first + j + i18n.certainLoss.NotEmpty); //第     //条事故估损金额中金额不能为空!
			fm.prpLclaimLossSumClaim[j].focus();
			return false;
		}
	}
	return true;
}
//Modify By dongcl remark end 20050623
//汇总险别估损信息 ,根据币别汇总
//add by qinyongli 2005-8-31 

function collectCurrency() {
	var collectCurr = "";
	var collectTemp = new Array();
	collectCurr = i18n.modifySumClaim.summary+"\n";//分币别汇总结果:
	for (var i = 1; i < fm.prpLclaimLossCurrency.length; i++) {
		var hasElement = false;
		var currency = fm.prpLclaimLossCurrency[i].value;
		var currencyName = fm.prpLclaimLossCurrencyName[i].value;
		var sumLossAmount = 0;
		//循环分币别统计
		for (var ii = 1; ii < fm.prpLclaimLossCurrency.length; ii++) {
			if (currency == fm.prpLclaimLossCurrency[ii].value) {
				sumLossAmount = sumLossAmount + parseFloat(fm.prpLclaimLossSumClaim[ii].value);
			}
		}
		//先判断当前数组中是否已有此币别,如果没有再进行保存
		for (var j = 0; j < collectTemp.length; j++) {
			if (collectTemp[j] == currency) {
				hasElement = true;
			}
		}
		//如果当前数组中有此元素，不再进行统计

		if (hasElement) {
			continue;
		}
		//存入数组中
		collectTemp[i - 1] = currency;
		collectCurr = collectCurr + currency + "  " + currencyName + "  " + sumLossAmount + i18n.modifySumClaim.money+"\n";//.00元
	}
	if (collectCurr.length > 0) {
		//alert(collectCurr);
		return false;
	}
}
// add by qinyongli end 2005-8-31


//add by qinyongli 查看出险时保单信息,在业务系统中进行保单还原
/**function backWardPolicy(){
     var SHOWTYPE  ="SHOW";
     var BizNo     =fm.prpLclaimPolicyNo.value;
     var RiskCode  =fm.prpLclaimRiskCode.value;
     var damageDate=fm.damageDate.value;
     var vURL = '/prpall/' + RiskCode + '/tbcbpg/UIPrPoEn' + RiskCode + 'Show.jsp?BIZTYPE=POLICY&SHOWTYPE=SHOW&BizNo='+ BizNo+'&RiskCode='+ RiskCode+'&damageDate='+ damageDate;
     window.open(vURL,'详细信息','width=750,height=500,top=15,left=10,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}(*/



/**
 *立案估损增加估损不允许大於保额控制
 */
function checkBeyondSumAmount(field) {
	var sumAmount = 0;
	var feeSumClaim = 0;
	var errorMessage = "";
	for (var i = 0; i < fm.all("prpLclaimLossSumClaim").length; i++) {
		if (fm.all("prpLclaimLossSumClaim")[i].value == "") {
			fm.all("prpLclaimLossSumClaim")[i].value = "0";
		}
		if (parseFloat(fm.all("prpLclaimLossSumClaim")[i].value) < 0) {
			errorMessage = i18n.claim.lessZero; // 估损金额不能小於零
			alert(errorMessage);
			fm.all("prpLclaimLossSumClaim")[i].value = "0";
			field.select();
			field.focus();
			return false;
		}
		feeSumClaim += parseFloat(fm.all("prpLclaimLossSumClaim")[i].value);
	}
	sumAmount = parseFloat(fm.prpLclaimSumAmount.value);
	var index = $(":input[name='"+field.name+"']").index(field);
	if (feeSumClaim > sumAmount) {
		errorMessage = $(":input[name='prpLclaimLossKindCode']").get(index).value+i18n.claim.cannotGreater; //估损金额不能大於保额
		alert(errorMessage);
		field.select();
		field.focus();
		return false;
	}
	return true;

}
//add by zhaolu 20060908 end 

function countryFlag_change(countryFlag) {
	if (countryFlag == "0") {
		fm.foreignCountryName.style.display = "none";
	} else {
		fm.foreignCountryName.style.display = "";
	}

	fm.portCode.value = "";
	fm.portCName.value = "";
	fm.prpLclaimDamageAddress.value = "";
}

function showPort(field) {
	var port = "^(" + fm.portCName.value + ")"
	port = new RegExp(port);
	if (field.value.search(port) != -1 && fm.portCName.value != "") {

	} else {
		field.value = "";
		if (fm.countryFlag.value == "0") {
			field.value = fm.portCName.value;
		} else {
			if (fm.language.value == "E") {
				field.value = fm.portCName.value + " " + fm.foreignCountryName.value;
			} else {
				field.value = fm.foreignCountryName.value + " " + fm.portCName.value;
			}
		}
	}
}

function clearPortCode() {
	fm.portCode.value = "";
	fm.portCName.value = "";
	fm.prpLclaimDamageAddress.value = "";
}