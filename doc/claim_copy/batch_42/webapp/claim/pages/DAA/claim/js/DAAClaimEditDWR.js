/**
 *立案计算责任估损金额
 *@author 中科软
 */

function calculateDutySum(field) {
	var allKindLoss = fm.prpLclaimSumClaim1.value;
	var outputObject;
	var inputObject = field;
	var indemnityDutyRate = $('prpLclaimIndemnityDutyRate').value;
	var inputArgs = {
		allKindLoss: allKindLoss,
		indemnityDutyRate: indemnityDutyRate
	};
	var param = DWRUtil.getValues(inputArgs);
	DWREngine.setAsync(false);
	dwrInvokeData("getDutySum", param, "rollbackDutySum", inputObject, outputObject);
	DWREngine.setAsync(true);
}

/**
 * 刷新险别估损金额信息
 */

function flashSumClaim() {
	var sumClaimNum = fm.all("prpLclaimLossKindCode").length;
	if (sumClaimNum != 'undefined' && sumClaimNum > 1) {
		for (var i = 1; i < sumClaimNum; i++) {
			calculateSumClaim(fm.all("prpLclaimLossKindCode")[i]);
		}
	}
}

/**
 * 立案计算险别估损金额
 * @author 中科软
 */

function calculateSumClaim(field) {
	collectClaimLoss(field); //保险损失金额
	var allKindLoss = fm.prpLclaimSumClaim1.value;
	var outputObject;
	var inputObject = field;
	var indemnityDutyRate = $(":input[name='prpLclaimIndemnityDutyRate']").val();
	var policyNo = $(":input[name='prpLclaimPolicyNo']").val();
	var riskCode = $(":input[name='prpLclaimRiskCode']").val();
	var indemnityDuty = $(":input[name='indemnityDuty']").val();
	var registNo = $(":input[name='prpLclaimRegistNo']").val();
	var startDate = $(":input[name='prpLclaimStartDate']").val();
	var damageDate = $(":input[name='prpLclaimDamageStartDate']").val();

	var fieldname = inputObject.name;
	var findex = 0;
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == inputObject) {
			findex = i;
			break;
		}
	}
	var feeType = fm.all('prpLclaimLossLossFeeType')[findex].value;
	var kindname = fm.all('prpLclaimLossKindName')[findex].value;
	var kindloss = 0;
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all('prpLclaimLossDangerNo')[i].value == fm.all('prpLclaimLossDangerNo')[findex].value &&
			fm.all('prpLclaimLossLossFeeType')[i].value == 'P' &&
			fm.all('prpLclaimLossKindCode')[i].value == fm.all('prpLclaimLossKindCode')[findex].value) {
			kindloss += parseFloat(fm.all('prpLclaimLossKindLoss')[i].value);
		}
	}
	var limitType = fm.all('prpLclaimLossFeeCategory')[findex].value;
	var kindCode = fm.all('prpLclaimLossKindCode')[findex].value;
	if (kindCode == "M") { //选择不计免赔率特约上报估损金额不需要，默认为零
		alert(i18n.claim.notAllowedChooseArrange); //不允许选择不计免赔率特约！
		fm.all('prpLclaimLossKindCode')[findex].value = "";
		fm.all('prpLclaimLossKindName')[findex].value = "";
		fm.all('prpLclaimLossKindLoss')[findex].value = 0;
		collectClaimLoss(field); //保险损失金额
		return false;
	}
	inputObject = fm.all('prpLclaimLossSumClaim')[findex];
	if (checkLoss()) {
		var inputArgs1 = {
			allKindLoss: allKindLoss,
			kindCode: kindCode,
			kindName: kindname,
			policyNo: policyNo,
			riskCode: riskCode,
			indemnityDuty: indemnityDuty,
			kindLoss: kindloss,
			limitType: limitType,
			startDate: startDate,
			damageDate: damageDate
		};
		var param1 = DWRUtil.getValues(inputArgs1);
		DWREngine.setAsync(false);
		dwrInvokeData("checkBeyondAmount", param1, "rollbackCheckResult", inputObject, outputObject);
		DWREngine.setAsync(true);
	} else {
		fm.all('prpLclaimLossSumClaim')[findex].value = 0;
		fm.all('prpLclaimLossKindLoss')[findex].value = 0;
	}

}

function rollbackDutySum(inputObject, outputObject, returnObject) {

	var dutySum = returnObject;

	if ("error" == dutySum) {
		DWRUtil.setValue("prpLclaimDutySumClaim", 0);
		alert(i18n.claim.dutyRatioNoZero); //责任比例不能大於100或小於0!
		fm.prpLclaimDutySumClaim.focus();
	} else
		DWRUtil.setValue("prpLclaimDutySumClaim", pointTwo(dutySum));
	undisablebutton();

}


function rollbackCheckResult(inputObject, outputObject, returnObject) {
	var result = returnObject;
	if (result == "true" || result == null) {
		//start
		var fieldname = inputObject.name;
		var findex = 0;

		for (i = 1; i < fm.all(fieldname).length; i++) {
			if (fm.all(fieldname)[i] == inputObject) {
				findex = i;
				break;
			}
		}
		var feeType = fm.all('prpLclaimLossLossFeeType')[findex].value;
		var kindloss = fm.all('prpLclaimLossKindLoss')[findex].value;
		var kindCode = fm.all('prpLclaimLossKindCode')[findex].value;
		inputObject = fm.all('prpLclaimLossSumClaim')[findex];
		var riskCode = $('prpLclaimRiskCode').value;
		var indemnityDuty = $('indemnityDuty').value;
		var registNo = fm.prpLclaimRegistNo.value;
		var indemnityDutyRate = fm.prpLclaimIndemnityDutyRate.value;
		var inputArgs2 = {
			feeType: feeType,
			kindLoss: kindloss,
			riskCode: riskCode,
			kindCode: kindCode,
			indemnityDutyRate: indemnityDutyRate,
			registNo: registNo,
			indemnityDuty: indemnityDuty
		};
		var param2 = DWRUtil.getValues(inputArgs2);
		DWREngine.setAsync(false);
		dwrInvokeData("getSumClaim", param2, "rollbackSumClaim", inputObject, outputObject);
		DWREngine.setAsync(true);
		undisablebutton();
	} else
		alert(result);
}


function rollbackSumClaim(inputObject, outputObject, returnObject) {

	var fieldname = inputObject.name;
	var findex = 0;

	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == inputObject) {
			findex = i;
			break;
		}
	}
	//差异化，四舍五入至整数位
	fm.prpLclaimLossSumClaim[findex].value = round(returnObject, 0);
	calculateExceptDeductiblePay(findex);
	collectClaimLoss(inputObject);
	undisablebutton();
}

/**
 @description 计算保险损失金额
 @param       无
 @return      无
 */

function collectClaimLoss(field) {
	alert("prop11111111111...........");
	var baseCurrency = '';
	var exchCurrency = fm.prpLclaimCurrency.value; //目标币别
	var nowAmout = '';

	for (var n = 1; n < fm.prpLclaimLossKindLoss.length; n++) {
		baseCurrency = baseCurrency + "," + fm.prpLclaimLossCurrency[n].value;
		if (fm.prpLclaimLossKindLoss[n].value == "") {
			fm.prpLclaimLossKindLoss[n].value = 0;
		}
		nowAmout = nowAmout + "," + fm.prpLclaimLossKindLoss[n].value;
	}
	var inputObject = field;
	var outputObject;
	var inputArgs = {
		baseCurrency: baseCurrency,
		exchCurrency: exchCurrency,
		nowAmout: nowAmout
	};
	var param = DWRUtil.getValues(inputArgs);
	DWREngine.setAsync(false);
	dwrInvokeData("collectClaimLoss", param, "rollbackClaimLoss", inputObject, outputObject);
	DWREngine.setAsync(true);

	return true;
}

function collectClaimLossNew(field) {
	var baseCurrency = '';
	var exchCurrency = fm.prpLclaimCurrency.value; //目标币别
	var nowAmout = '';

	for (var n = 1; n < fm.prpLclaimLossKindLoss.length; n++) {
		baseCurrency = baseCurrency + "," + fm.prpLclaimLossCurrency[n].value;
		if (fm.prpLclaimLossKindLoss[n].value == "") {
			fm.prpLclaimLossKindLoss[n].value = 0;
		}
		nowAmout = nowAmout + "," + fm.prpLclaimLossKindLoss[n].value;
	}
	var inputObject = field;
	var outputObject;
	var inputArgs = {
		baseCurrency: baseCurrency,
		exchCurrency: exchCurrency,
		nowAmout: nowAmout
	};
	var param = DWRUtil.getValues(inputArgs);
	DWREngine.setAsync(false);
	dwrInvokeData("collectClaimLoss", param, "rollbackClaimLossNew", inputObject, outputObject);
	DWREngine.setAsync(true);

	return true;
}
/**
 @description 计算责任估损金额
 @param       无
 @return      无
 */

function collectClaimDutyLoss(field) {
	var baseCurrency = '';
	var exchCurrency = fm.prpLclaimCurrency.value; //目标币别
	var nowAmout = '';

	for (var n = 1; n < fm.prpLclaimLossSumClaim.length; n++) {
		baseCurrency = baseCurrency + "," + fm.prpLclaimLossCurrency[n].value;
		if (fm.prpLclaimLossSumClaim[n].value == "") {
			fm.prpLclaimLossSumClaim[n].value = 0;
		}
		nowAmout = nowAmout + "," + fm.prpLclaimLossSumClaim[n].value;
	}

	for (var i = 1; i < fm.exceptDeductiblePay.length; i++) {
		baseCurrency = baseCurrency + "," + CURRENCYINFO.LOCAL_CURRENCY;
		if (fm.exceptDeductiblePay[i].value == "") {
			fm.exceptDeductiblePay[i].value = 0;
		}
		nowAmout = nowAmout + "," + fm.exceptDeductiblePay[i].value;
	}

	var inputObject = field;
	var outputObject;
	var inputArgs = {
		baseCurrency: baseCurrency,
		exchCurrency: exchCurrency,
		nowAmout: nowAmout
	};
	var param = DWRUtil.getValues(inputArgs);
	DWREngine.setAsync(false);
	dwrInvokeData("collectClaimLoss", param, "rollbackClaimDutyLoss", inputObject, outputObject);
	DWREngine.setAsync(true);

	return true;
}

function collectClaimDutyLossNew(field) {
	var baseCurrency = '';
	var exchCurrency = fm.prpLclaimCurrency.value; //目标币别
	var nowAmout = '';

	for (var n = 1; n < fm.prpLclaimLossSumClaim.length; n++) {
		baseCurrency = baseCurrency + "," + fm.prpLclaimLossCurrency[n].value;
		if (fm.prpLclaimLossSumClaim[n].value == "") {
			fm.prpLclaimLossSumClaim[n].value = 0;
		}
		nowAmout = nowAmout + "," + fm.prpLclaimLossSumClaim[n].value;
	}

	for (var i = 1; i < fm.exceptDeductiblePay.length; i++) {
		baseCurrency = baseCurrency + "," + CURRENCYINFO.LOCAL_CURRENCY;
		if (fm.exceptDeductiblePay[i].value == "") {
			fm.exceptDeductiblePay[i].value = 0;
		}
		nowAmout = nowAmout + "," + fm.exceptDeductiblePay[i].value;
	}

	var inputObject = field;
	var outputObject;
	var inputArgs = {
		baseCurrency: baseCurrency,
		exchCurrency: exchCurrency,
		nowAmout: nowAmout
	};
	var param = DWRUtil.getValues(inputArgs);
	DWREngine.setAsync(false);
	dwrInvokeData("collectClaimLoss", param, "rollbackClaimDutyLossNew", inputObject, outputObject);
	DWREngine.setAsync(true);

	return true;
}

function rollbackClaimLoss(inputObject, outputObject, returnObject) {
	var compAmout = returnObject;
	DWRUtil.setValue("prpLclaimSumClaim1", pointTwo(compAmout));
	collectClaimDutyLoss(inputObject);
	undisablebutton();
}

function rollbackClaimLossNew(inputObject, outputObject, returnObject) {
	var compAmout = returnObject;
	DWRUtil.setValue("prpLclaimSumClaim1", pointTwo(compAmout));
	collectClaimDutyLossNew(inputObject);
}

function rollbackClaimDutyLoss(inputObject, outputObject, returnObject) {
	var compAmout = returnObject;
	DWRUtil.setValue("prpLclaimSumClaim", pointTwo(compAmout));
	DWRUtil.setValue("prpLclaimDutySumClaim", pointTwo(compAmout));
	DWRUtil.setValue("prpLdangerRiskSumClaim", pointTwo(compAmout));
	undisablebutton();
}

function rollbackClaimDutyLossNew(inputObject, outputObject, returnObject) {
	var compAmout = returnObject;
	DWRUtil.setValue("prpLclaimSumClaim", pointTwo(compAmout));
	DWRUtil.setValue("prpLclaimDutySumClaim", pointTwo(compAmout));
	DWRUtil.setValue("prpLdangerRiskSumClaim", pointTwo(compAmout));
}

/**
 * 车险限额检查
 */

//function checkLimit() {
//	fm.buttonSubmitFlag.value = "Y";
//	var sumClaimNum = fm.all("prpLclaimLossKindCode").length;
//	if (sumClaimNum != 'undefined' && sumClaimNum > 1) {
//		for (var i = 1; i < sumClaimNum; i++) {
//			checkSumClaim(fm.all("prpLclaimLossKindCode")[i]);
//		}
//	}
//}

//function checkSumClaim(field) {
//	var allKindLoss = fm.prpLclaimSumClaim.value;
//	var outputObject;
//	var inputObject = field;
//	var indemnityDutyRate = $('prpLclaimIndemnityDutyRate').value;
//	var policyNo = $('prpLclaimPolicyNo').value;
//	var riskCode = $('prpLclaimRiskCode').value;
//	var indemnityDuty = $('indemnityDuty').value;
//	var registNo = fm.prpLclaimRegistNo.value;
//	var startDate = $('prpLclaimStartDate').value;
//	var damageDate = $('prpLclaimDamageStartDate').value;
//	var fieldname = inputObject.name;
//	var findex = 0;
//
//	for (i = 1; i < fm.all(fieldname).length; i++) {
//		if (fm.all(fieldname)[i] == inputObject) {
//			findex = i;
//			break;
//		}
//	}
//	var feeType = fm.all('prpLclaimLossLossFeeType')[findex].value;
//	var kindname = fm.all('prpLclaimLossKindName')[findex].value;
//	var kindloss = 0;
//	for (i = 1; i < fm.all(fieldname).length; i++) {
//		if (fm.all('prpLclaimLossDangerNo')[i].value == fm.all('prpLclaimLossDangerNo')[findex].value &&
//			fm.all('prpLclaimLossLossFeeType')[i].value == 'P' &&
//			fm.all('prpLclaimLossKindCode')[i].value == fm.all('prpLclaimLossKindCode')[findex].value) {
//			kindloss += parseFloat(fm.all('prpLclaimLossKindLoss')[i].value);
//		}
//	}
//	var limitType = fm.all('prpLclaimLossFeeCategory')[findex].value;
//	var kindCode = fm.all('prpLclaimLossKindCode')[findex].value;
//	inputObject = fm.all('prpLclaimLossSumClaim')[findex];
//	if (checkLoss()) {
//		var inputArgs1 = {
//			allKindLoss: allKindLoss,
//			kindCode: kindCode,
//			kindName: kindname,
//			policyNo: policyNo,
//			riskCode: riskCode,
//			indemnityDuty: indemnityDuty,
//			kindLoss: kindloss,
//			limitType: limitType,
//			startDate: startDate,
//			damageDate: damageDate
//		};
//		var param1 = DWRUtil.getValues(inputArgs1);
//		DWREngine.setAsync(false);
//		dwrInvokeData("checkBeyondAmount", param1, "rollbackCheckResult2", inputObject, outputObject);
//		DWREngine.setAsync(true);
//	}
//}

function rollbackCheckResult2(inputObject, outputObject, returnObject) {
	var result = returnObject;
	if (result != null && result != "true") {
		fm.buttonSubmitFlag.value = "N";
		alert(result);
	}
}

function flashSumLossAmount() {
	var baseCurrency = '';
	var exchCurrency = fm.prpLclaimCurrency.value; //目标币别
	var nowAmout = '';
	for (var n = 1; n < fm.prpLclaimLossKindLoss.length; n++) {
		baseCurrency = baseCurrency + "," + fm.prpLclaimLossCurrency[n].value;
		if (fm.prpLclaimLossKindLoss[n].value == "") {
			fm.prpLclaimLossKindLoss[n].value = 0;
		}

		nowAmout = nowAmout + "," + fm.prpLclaimLossKindLoss[n].value;
	}

	var inputObject;
	var outputObject;
	var inputArgs = {
		baseCurrency: baseCurrency,
		exchCurrency: exchCurrency,
		nowAmout: nowAmout
	};
	var param = DWRUtil.getValues(inputArgs);
	DWREngine.setAsync(false);
	dwrInvokeData("collectClaimLoss", param, "rollbackSumLossAmount", inputObject, outputObject);
	DWREngine.setAsync(true);
	return true;
}

function rollbackSumLossAmount(inputObject, outputObject, returnObject) {
	var compAmout = returnObject;
	DWRUtil.setValue("prpLclaimSumClaim1", pointTwo(compAmout));
	undisablebutton();
}

/**验证该险种是否已经购买了不计免赔*/

function checkExceptDeductible(kind, field) {
	var policyno = fm.policyno.value;
	var riskCode = $('prpLclaimRiskCode').value;
	var registNo = fm.prpLclaimRegistNo.value;
	var indemnityDuty = $('indemnityDuty').value;
	var indemnityDutyRate = $('prpLclaimIndemnityDutyRate').value;
	if (policyno != null || policyno != "") {
		var inputArgs = {
			kind: kind,
			policyno: policyno,
			indemnityDuty: indemnityDuty,
			indemnityDutyRate: indemnityDutyRate,
			riskCode: riskCode,
			registNo: registNo
		};
		var inputObject = field;
		var outputObject;
		var param = DWRUtil.getValues(inputArgs);
		DWREngine.setAsync(false);
		dwrInvokeData("checkExceptDeductible", param, "rollbackExceptDeductible", inputObject, outputObject);
		DWREngine.setAsync(true);
	}
}

function rollbackExceptDeductible(inputObject, outputObject, returnObject) {
	var prpCitemKindDto = returnObject;
	if (prpCitemKindDto.kindCode != "") {
		insertRow('exceptLoss1');
		var index = fm.exceptDeductibleKindCode.length;
		fm.exceptDeductibleKindCode[index - 1].value = prpCitemKindDto.kindCode;
		fm.exceptDeductibleKindName[index - 1].value = prpCitemKindDto.kindName;
		fm.exceptDeductibleRate[index - 1].value = prpCitemKindDto.deductibleRate;
		fm.exceptDeductiblePay[index - 1].value = 0;
	}
	undisablebutton();
}

/**计算免赔额*/

function calculateExceptDeductiblePay(index) {
	var feeType = fm.prpLclaimLossLossFeeType[index].value;
	if ("Z" == feeType) { //费用
		deleteRow2(fm.prpLclaimLossLossFeeType[index]);
		//险别不计免赔率
		fm.prpLclaimLossAcciDeductibleRate[index].value = 0;
		//险别不计免赔额
		fm.prpLclaimLossAcciDeductiblePay[index].value = 0;
	} else {
		var kindCode = fm.prpLclaimLossKindCode[index].value;
		var indemnityDutyRate = fm.prpLclaimIndemnityDutyRate.value;
		var indemnityDuty = $('indemnityDuty').value;
		var riskCode = $('prpLclaimRiskCode').value;
		var registNo = fm.prpLclaimRegistNo.value;
		var kindLoss = fm.prpLclaimLossKindLoss[index].value;
		var sumKindLoss = 0;
		for (var i = 1; i < fm.prpLclaimLossKindCode.length; i++) {
			if (kindCode == fm.prpLclaimLossKindCode[i].value && "P" == fm.prpLclaimLossLossFeeType[i].value) {
				sumKindLoss = sumKindLoss + parseFloat(fm.prpLclaimLossKindLoss[i].value);
			}
		}

		var inputArgs = {
			sumKindLoss: sumKindLoss,
			kindLoss: kindLoss,
			indemnityDuty: indemnityDuty,
			indemnityDutyRate: indemnityDutyRate,
			riskCode: riskCode,
			registNo: registNo,
			kindCode: kindCode
		};
		var param = DWRUtil.getValues(inputArgs);
		var inputObject = index;
		var outputObject;
		DWREngine.setAsync(false);
		dwrInvokeData("getExceptDeductiblePay", param, "rollbackExceptDeductible1", inputObject, outputObject);
		DWREngine.setAsync(true);
	}
}

function rollbackExceptDeductible1(inputObject, outputObject, returnObject) {
	var index = inputObject;
	var exceptDeductibleRateAll = 0;
	var account = returnObject.split("|");
	var kindCode = fm.prpLclaimLossKindCode[index].value;
	for (var j = 1; j < fm.exceptDeductibleKindCode.length; j++) {
		if (kindCode == fm.exceptDeductibleKindCode[j].value) {
			//不计免赔栏中的赔偿金额
			fm.exceptDeductiblePay[j].value = round(account[0], 0);
			//险别不计免赔率
			fm.prpLclaimLossAcciDeductibleRate[index].value = fm.exceptDeductibleRate[j].value;
			//险别不计免赔额
			fm.prpLclaimLossAcciDeductiblePay[index].value = round(account[1], 0);
			fm.prpLclaimLossKindCode1[index].value = kindCode;
		}
		//不计免赔栏中的总赔偿金额
		exceptDeductibleRateAll = exceptDeductibleRateAll + parseFloat(fm.exceptDeductiblePay[j].value);
	}

	fm.exceptDeductibleRateAll.value = round(exceptDeductibleRateAll, 0);
	undisablebutton();
}
