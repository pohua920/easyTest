/*****************************************************************************
 * DESC       ：报案登记的脚本函数页面
 * AUTHOR     ：中科软
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
	if (fm.BaseCurrency1.value != CURRENCYINFO.LOCAL_CURRENCY) {
		alert(i18n.claim.singleCurrency + fm.BaseCurrency1.value + i18n.claim.useSparingly + fm.ExchRate1.value); //此案件签单币别为     ，不是CNY，请慎重处理！\n当前兑换率为
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
	if (saveType == "4" && checkUndwrt() == false) {
		return false;
	}
	var errorMessage = "";
	fm.buttonSaveType.value = saveType;
	// 立案环节提交增加危险单位不能为空的控制 begin
	var dangerNoList = document.getElementsByName("prpLclaimLossDangerNo");
	for (var i = 0; i < dangerNoList.length; i++) {
		if (dangerNoList[i].value == null || dangerNoList[i].value == "") {
			errorMessage = errorMessage + "危险单位不能为空!\n"
			break;
		}
	}
	// 立案环节提交增加危险单位不能为空的控制 end
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
		if (!confirm(i18n.claim.caseGreaterSystemTime + standardDays + i18n.claim.dayWhetherSubmit)) { //立案天数大於系统规定时间      天，是否提交？
			return false;
		}
	}
	if (!validateForm(fm, "ClaimLoss_Data")) {
		return false;
	}
	//计算估损金额
	collectClaimFee();
	if (saveType == "4"&&!checkLoss()){
		return false;
	}
	//reason:当按下某一按钮时请将这个按钮变灰，否则用户可能多按引发错误
	field.disabled = true;
	fm.submit();
	return true;
}

//装载数据

function loadForm() {
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


///**
// @description 校验索赔金额
// @param       无
// @return      boolean
// */
//function checkFee() {
//	if (getRowsCount("ClaimLoss") == 0) {
//		errorMessage(i18n.claim.oneRecord); //估损金额金额信息至少要有一条记录!
//
//		return false;
//	}
//	for (var j = 1; j < fm.prpLclaimLossCurrency.length; j++) {
//		if (isEmptyField(fm.prpLclaimLossCurrency[j])) {
//			errorMessage(i18n.compel.first + j + i18n.commonAcci.claim.estimatLossAmountNoEmpty); //第   条估损金额中币别不能为空!
//			fm.prpLclaimLossCurrency[j].focus();
//			return false;
//		}
//		for (var n = j + 1; n < fm.prpLclaimLossCurrency.length; n++) {
//			if ((fm.prpLclaimLossCurrency[n].value == fm.prpLclaimLossCurrency[j].value) && (fm.prpLclaimLossKindCode[n].value == fm.prpLclaimLossKindCode[j].value) && (fm.prpLclaimLossLossFeeType[n].value == fm.prpLclaimLossLossFeeType[j].value)) {
//				errorMessage(i18n.compel.first + n + i18n.commonShip.claim.estimatLossAmountCost + j + i18n.commonShip.claim.estimatLossAmountCost2); //第   条估损金额中险别、币别、费用类别和第     条估损金额中险别、币别、费用类别一致，请合並这2条记录
//
//				fm.prpLclaimLossCurrency[n].focus();
//				return false;
//			}
//		}
//
//		if (isEmptyField(fm.prpLclaimLossSumClaim[j])) {
//			errorMessage("第" + j + "条事故估损金额中金额不能为空!");
//			fm.prpLclaimLossSumClaim[j].focus();
//			return false;
//		}
//	}
//	return true;
//}

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
	$("#ClaimLoss").find(":input[name='prpLclaimLossSumClaim']").each(function(i,n){
		compAmout += parseFloat(n.value);
	});
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

/**
 @description 校验索赔金额
 @param       无
 @return      boolean
 */
function checkLoss() {
	//1.检查必须要有一条记录
	if (getRowsCount("ClaimLoss") == 0) {
		errorMessage("估损金额金额信息至少要有一条记录!");
		return false;
	}
	//2币别不能为空的
	for (var j = 1; j < fm.prpLclaimLossCurrency.length; j++) {
		if (isEmptyField(fm.prpLclaimLossCurrency[j])) {
			errorMessage("第" + j + "条估损金额中币别不能为空!");
			return false;
		}

		if (isEmptyField(fm.prpLclaimLossKindCode[j])) {
			errorMessage("第" + j + "条事故估损金额中险别代码不能为空!");
			return false;
		}

		//险别和币别相同的，必须合並
		for (var n = j + 1; n < fm.prpLclaimLossCurrency.length; n++) {
			if ((fm.prpLclaimLossKindCode[n].value == fm.prpLclaimLossKindCode[j].value) && (fm.prpLclaimLossCurrency[n].value == fm.prpLclaimLossCurrency[j].value) && (fm.prpLclaimLossLossFeeType[n].value == fm.prpLclaimLossLossFeeType[j].value)) {
				errorMessage("第" + n + "条估损金额中险别、项目、币别、费用类别和第" + j + "条估损金额中险别、项目、币别、费用类别一致，请合並这2条记录");
				return false;
			}
		}
		if (isEmptyField(fm.prpLclaimLossSumClaim[j])) {
			errorMessage("第" + j + "条事故估损金额中金额不能为空!");
			return false;
		}
	}
	return checkBeyondSumAmount();
}
//汇总险别估损信息 ,根据币别汇总

function collectCurrency() {
	var collectCurr = "";
	var collectTemp = new Array();
	collectCurr = "分币别汇总结果:\n";
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
		collectCurr = collectCurr + currency + "  " + currencyName + "  " + sumLossAmount + ".00元\n";
	}
	if (collectCurr.length > 0) {
		alert(collectCurr);
		return false;
	}
}

function checkBeyondSumAmount() {
	var flag = true;
	var errorMessage = "";
	var $Amount = $("#ClaimLoss").find(":input[name='prpLclaimLossAmount']");
	var $LossFeeType = $("#ClaimLoss").find(":input[name='prpLclaimLossLossFeeType']");
	var $KindCode = $("#ClaimLoss").find(":input[name='prpLclaimLossKindCode']");
	
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
			kindCodes = $KindCode.get(i).value;
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
			errorMessage += kindCodeArray[i]+i18n.prompt.claim.sumInsured; //估损金额不能大於保额
			flag = false;
			return false;
		}
	});
	if(errorMessage != ""){
		alert(errorMessage);
	}
	return flag;
}


//根据出现地址所属国内或国外判断双击域是否显示 begin

function countryFlag_change(countryFlag) {
	if (countryFlag == "0") {
		fm.foreignCountryName.style.display = "none";
		fm.prpLclaimAddressCode.style.display = "";
		fm.prpLclaimAddressName.style.display = "";
	} else {
		//fm.foreignCountryName.style.display = "";
		fm.prpLclaimAddressCode.style.display = "none";
		fm.prpLclaimAddressName.style.display = "none";
	}
	fm.prpLclaimDamageAddress.value = "";
}

function clearPortCode() {
	fm.prpLclaimDamageAddress.value = "";
}

//刷新批改信息
function refreshEndorseInfo(field) {
	if(field.options.length==1) {//无有效批单时候，不做响应
		return false;
	}
	var endorseNo = $(field).val();
	var damageDate = $("input[name='prpLclaimDamageStartDate']").val();
	var damageHour = $("input[name='damageStartHour']").val();
	var policyNo = $("input[name='prpLclaimPolicyNo']").val();
	$.ajax({
		type:"POST",
		url:"${ctx}/refreshEndorseInfo.do",
		dataType: "json",
		data:"damageDate="+damageDate+"&damageHour="+damageHour+"&policyNo="+policyNo+"&endorseNo="+endorseNo,
		success:function(d){
			var recordsReturned = d.data.length;
			if (recordsReturned != 0) {
				$.each(d.data, function (idx, prpLclaim) {
					// 保險金額、貨物名稱、船名、開行日期、航程,規則：只有貨物運輸險
					var sailStartDateMG=formatDate(prpLclaim.sailStartDate, "yyy-MM-dd");
					$("input[name='prpLextUnloadDate']").val(prpLclaim.sailStartDate);//開行日期
					$("input[name^='prpLextUnloadDate']:first").attr("realValue",prpLclaim.sailStartDate);
					$("input[name^='prpLextUnloadDate']:first").val(sailStartDateMG);
					$("input[name='prpLclaimCargoNo']").val(prpLclaim.cargoNo);
					$("input[name='prpLclaimCargoName']").val(prpLclaim.cargoName);
					$("input[name='prpLclaimSumAmount']").val(prpLclaim.sumAmount);
					$("select[name='prpLclaimImportType']").val(prpLclaim.importType);
				});
			}
		}
	});
}
/**
 * 險別估損金額=損失金額-自負額-剔除金額/殘值
 * 通過計算產生的‘險別估損金額’不可以修正。
 */
function calculateSumClaim(field){
	var $KindRest = $(":input[name='prpLclaimLossKindRest']");
	$(":input[name='prpLclaimLossSumClaim']").each(function(i,n){
		if(!$.isNumeric(n.value)){
			n.value = "0";
		}
		if(!$.isNumeric($KindRest.get(i).value)){
			$KindRest.get(i).value = "0";
		}
	});
	checkBeyondSumAmount();
	collectClaimFee();
}
$(document).ready(function(){
	initDamageDate();
});
