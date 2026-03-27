/*****************************************************************************
 * DESC       ：报案登记的脚本函数页面
 * AUTHOR     ：中科軟
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
	//add by qinyongli 增加保单注销,，股东业务，等提示； 2005-7-28
	var coinsFlag = fm.coinsFlag.value;
	var tempReinsFlag = fm.tempReinsFlag.value;
	var shareHolderFlag = fm.shareHolderFlag.value;
	var message = "";

	var othFlag = fm.prpLclaimOthFlag.value;
	if (coinsFlag != 0) {
		message = message + i18n.claim.totalPolicy; //本保单为联/共保单！\n
		message = message + i18n.claim.estimateAmount; //估损金额请輸入分摊前的总估损金额！\n
	}
	if (tempReinsFlag != 0) {
		message = message + i18n.check.proBusinessTask; //此保单有临分业务！\n
	}

	if (othFlag.substring(3, 4) == "1") {
		message = message + i18n.regist.orderOff; //此保单已注销！\n
	}
	var underWriteEndDate = fm.underWriteEndDate.value;
	var prpLclaimStartDate = fm.prpLclaimStartDate.value;
	var payFee = parseInt(fm.payFee.value);
	var delinquentfeeCase = fm.delinquentfeeCase.value;
	if (payFee == -1) {
		message = message + i18n.regist.useCaution; //此保单保费未缴,请慎重处理！！！ \n
	} else if (payFee == 0) {
		message = message + i18n.certainLoss.policyPremiumPay; //此保单已缴未缴全,请慎重处理！！！ \n
		message = message + delinquentfeeCase + "\n";
	}
	var reportTimeMessage = $(":input[name='reportTimeMessage']").val();
	if(reportTimeMessage != ""){
		message += reportTimeMessage;
	}
	if (message.length > 0) {
		alert(message);
	}
	if (fm.BaseCurrency1.value != CURRENCYINFO.LOCAL_CURRENCY) {
		alert(i18n.claim.singleCurrency + fm.BaseCurrency1.value + i18n.claim.useSparingly + fm.ExchRate1.value); //此案件签单币别为   //，不是CNY，请慎重处理！\n当前兑换率为
	}
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
	if (window.confirm(i18n.prompt.regist.isClear)) {//確定要清除嗎？
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
	//提交时对送审进行判断
	if (saveType == "4" && checkUndwrt() == false) {
		return false;
	}
	var errorMessage = "";
	fm.buttonSaveType.value = saveType;
	// 立案提交增加险别不能为空的控制 begin
	var kindList = document.getElementsByName("prpLclaimLossKindCode");
	var kindNameList = document.getElementsByName("prpLclaimLossKindName");
	for (var i = 1; i < kindList.length; i++) {
		if (kindList[i].value == "" || kindNameList[i].value == "") {
			errorMessage = errorMessage + i18n.claim.risksCannotEmpty //险别不能为空!\n
			break;
		}
	}
	//立案环节提交增加危险单位不能为空的控制 begin
	var dangerNoList = document.getElementsByName("prpLclaimLossDangerNo");
	for (var i = 0; i < dangerNoList.length; i++) {
		if (dangerNoList[i].value == null || dangerNoList[i].value == "") {
			errorMessage = errorMessage + i18n.claim.dangerousCannotEmpty //危险单位不能为空!\n
			break;
		}
	}
	//textarea文本框设置值
	var context = fm.prpLltextContextInnerHTML.value;
	if (context.length < 1) {
		errorMessage = errorMessage + i18n.claim.dangerNotAllowEmpty; //出险摘要不允许为空\n
	}
	if (errorMessage.length > 0) {
		alert(errorMessage);
		return false;
	}
	var claim_days = fm.claim_days.value;
	var standardDays = fm.standardDays.value;
	if (claim_days == 0) {
		if (!confirm(i18n.claim.caseGreaterSystemTime + standardDays + i18n.claim.dayWhetherSubmit)) {
			return false;
		}
	}
	if (fm.all("prpLclaimLossKindCode").length == undefined || fm.all("prpLclaimLossKindCode").length < 1) {
		alert(i18n.claim.enterInformation); //请输入估损信息！
		return false;
	}
	var SumClaim = parseFloat(fm.prpLclaimSumClaim.value);
	if (SumClaim < 0) {
		alert(i18n.claim.lessZero); //估损金额不能小於零！
		return false;
	}
	//估损金额不能大於保额！
	if(!checkBeyondSumAmount()){
		return false;
	}
	for(i=1;i<fm.prpLpersonTraceJobName2.length;i++){
		fm.prpLpersonTraceJobCode[i].value = fm.prpLpersonTraceJobCode2[i].value;
		fm.prpLpersonTraceJobName[i].value = fm.prpLpersonTraceJobName2[i].value;
	}
	for (i = 1; i < fm.prpLpersonTracePersonNo.length; i++) {
		if (fm.prpLpersonTraceJobCode1[i].value != "") {
			if (fm.prpLpersonTraceJobCode2[i].value == "") {
				alert(i18n.certainLoss.pleaseSelectIndustry2); //请选择二级行业！
				return false;
			}
		}
	}
	//reason: ValidateData.js中的校验不起作用时，因为没有调用校验方法
	if (!validateForm(fm, "ClaimLoss_Data")) {
		return false;
	}
	//reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
	field.disabled = true;
	fm.submit();

	return true;
}

//装载数据

function loadForm() {}

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


//得到一页的多行纪录的记录数
//页名称

function getRowsCount(PageCode) {
	var oTBODY = document.all(PageCode).tBodies.item(0);
	var intCount = oTBODY.rows.length;
	return intCount;
}

/**
 @description 校验索赔金额
 @param       无
 @return      boolean
 */

function checkLoss() {

	var riskCode = fm.prpLclaimRiskCode.value

	//1.检查必须要有一条记录
	if (getRowsCount("ClaimLoss") == 0) {
		errorMessage(i18n.claim.oneRecord); //估损金额金额信息至少要有一条记录!

		return false;
	}
	//2币别不能为空的

	for (var j = 1; j < fm.prpLclaimLossCurrency.length; j++) {
		if (isEmptyField(fm.prpLclaimLossCurrency[j])) {
			errorMessage(i18n.compel.first + j + i18n.certainLoss.amountCannot); //第       //条估损金额中币别不能为空!
			fm.prpLclaimLossCurrency[j].focus();
			return false;
		}

		if (riskCode != "0905") {
			if (isEmptyField(fm.prpLclaimLossKindCode[j])) {

				errorMessage(i18n.compel.first + j + i18n.certainLoss.risksCode); //第		//条事故估损金额中险别代码不能为空!
				//fm.prpLclaimLossKindCode[j].focus();
				return false;
			}
		}

		if (isEmptyField(fm.prpLclaimLossSumClaim[j])) {
			errorMessage(i18n.compel.first + j + i18n.certainLoss.NotEmpty); //第		//条事故估损金额中金额不能为空!
			fm.prpLclaimLossSumClaim[j].focus();
			return false;
		}

	}

	return true;
}

function collectCurrency() {
	var collectCurr = "";
	var collectTemp = new Array();
	collectCurr = i18n.modifySumClaim.summary + "\n"; //分币别汇总结果:\n
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
		collectCurr = collectCurr + currency + "  " + currencyName + "  " + sumLossAmount + i18n.modifySumClaim.money + "\n"; //.00元\n
	}
	if (collectCurr.length > 0) {
		alert(collectCurr);
		return false;
	}
}

/**
 *立案估损增加估损不允许大於保额控制
 */

function checkBeyondSumAmount() {
	var flag = true;
	var errorMessage = "";
	var $Amount = $("#ClaimLoss").find(":input[name='prpLclaimLossAmount']");
	var $LossFeeType = $("#ClaimLoss").find(":input[name='prpLclaimLossLossFeeType']");
	var $KindCode = $("#ClaimLoss").find(":input[name='prpLclaimLossKindCode']");
	var $ItemCode = $("#ClaimLoss").find(":input[name='prpLclaimLossItemCode']");
	
	var amountArray = new Array();
	var kindCodeArray = new Array();
	var sumClaimArray = new Array();
	var kindCodes = "";
	$("#ClaimLoss").find(":input[name='prpLclaimLossSumClaim']").each(function(i,n){
		if (parseFloat(n.value) < 0) {
			errorMessage += "第"+(i+1)+"條"+i18n.claim.lessZero; // 估损金额不能小於零
			flag = false;
			return flag;
		}
		if($LossFeeType.get(i).value=="P"){
			kindCodes = $KindCode.get(i).value+"_"+$ItemCode.get(i).value;
			var index = $.inArray(kindCodes, kindCodeArray);
			if(index>-1){
				sumClaimArray[index] += parseFloat(n.value);
			}else{
				kindCodeArray.push(kindCodes);
				amountArray.push(parseFloat($Amount.get(i).value));
				sumClaimArray.push(parseFloat(n.value));
			}
		}
	});
	$.each(kindCodeArray,function(i,n){
		if(sumClaimArray[i]>amountArray[i]){
			kindCodes = kindCodeArray[i].split("_");
			errorMessage += "險別"+kindCodes[0]+",標的物"+kindCodes[1]+i18n.prompt.claim.sumInsured; //估损金额不能大於保额
			flag = false;
			return false;
		}
	});
	if(errorMessage != ""){
		alert(errorMessage);
	}
	return flag;
}

/**
 * 險別估損金額=損失金額-自負額-剔除金額/殘值
 * 通過計算產生的‘險別估損金額’不可以修正。
 */
function calculateSumClaim(field){
	for (var i = 0; i < fm.all("prpLclaimLossKindLoss").length; i++) {
		var prpLclaimLossKindLoss = nullToZero(fm.prpLclaimLossKindLoss[i].value);//損失金額
		var prpLclaimLossDeductible = nullToZero(fm.prpLclaimLossDeductible[i].value);//自負額
		var prpLclaimLossKindRest = nullToZero(fm.prpLclaimLossKindRest[i].value);//殘值 
		//險別估損金額
		fm.prpLclaimLossSumClaim[i].value =  prpLclaimLossKindLoss - prpLclaimLossDeductible - prpLclaimLossKindRest
	}
	checkBeyondSumAmount();
	collectClaimLoss(field);
}
/**
 * 将空值转化为0
 * @param field
 * @returns
 */
function nullToZero(field){
	var res = parseFloat(field);
	if(isNaN(res)){
		res = 0;
	}
	return res;
}

//add by luochang 2010-06-22 根据出现地址所属国内或国外判断双击域是否显示 begin

function countryFlag_change(countryFlag) {
	if (countryFlag != "1") {
		fm.countryCName.style.display = "none";
		//fm.provinceName.style.display = "none";
		//fm.cityName.style.display = "";
		fm.prpLclaimAddressCode.style.display = "";
		fm.prpLclaimAddressName.style.display = "";
	} else {
		fm.countryCName.style.display = "";
		//fm.provinceName.style.display = "none";
		//fm.cityName.style.display = "none";
		fm.prpLclaimAddressCode.style.display = "none";
		fm.prpLclaimAddressName.style.display = "none";
	}
}

/** 修改危险分类总项，清空危险分类细项  */
function dangerousClassItem(field){
	$(":input[name='prpLclaimDangerousClassSubItem']").val("");
	$(":input[name='prpLclaimDangerousClassSubItemName']").val("");
}
$(document).ready(function(){
	initDamageDate();
});