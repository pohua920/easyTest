/**
 @description 计算保险损失金额
 @param       无
 @return      无
 */
function collectClaimLoss(field) {
	//modify by caozhigang 2009-04-22 start
	//原来通过dwr汇总金额存在精度问题，现在不存在币别问题，直接在页面用js计算，不走dwr
	var baseCurrency = '';
	var exchCurrency = fm.prpLclaimCurrency.value; //目标币别
	var nowAmout = '';
	var sumClaimLoss = 0.00;
	for (var n = 1; n < fm.prpLclaimLossSumClaim.length; n++) {
		baseCurrency = baseCurrency + "-" + fm.prpLclaimLossCurrency[n].value;
		if (fm.prpLclaimLossSumClaim[n].value == "") {
			fm.prpLclaimLossSumClaim[n].value = 0;
		}
		nowAmout = nowAmout + "-" + fm.prpLclaimLossSumClaim[n].value;
		sumClaimLoss += parseFloat(fm.prpLclaimLossSumClaim[n].value);
	}
	fm.prpLclaimSumClaim.value = point(round(sumClaimLoss, 0), 0);
	//var inputObject = field;
	//var outputObject;
	//var inputArgs = {baseCurrency:baseCurrency,exchCurrency:exchCurrency,nowAmout:nowAmout};
	//  var param = DWRUtil.getValues(inputArgs);
	//dwrInvokeData("collectClaimLoss",param,"rollbackClaimLoss",inputObject,outputObject);
	//modify by caozhigang 2009-04-22 end
	return true;
}

function rollbackClaimLoss(inputObject, outputObject, returnObject) {
	var compAmout = returnObject;
	//add by liping 08-04-24 
	undisablebutton();
	//DWRUtil.setValue("prpLclaimSumClaim",pointTwo(compAmout));
	//DWRUtil.setValue("prpLdangerRiskSumClaim",pointTwo(compAmout));
	//calculateDutySum(inputObject);
}


function checkBeyondAmount(field) {
	var kindCode = $('prpLclaimLossKindCode').value;
	var kindName = $('prpLclaimLossKindName').value;
	var policyNo = $('prpLclaimPolicyNo').value;
	var riskCode = $('prpLclaimRiskCode').value;
	var allKindLoss = $('prpLclaimSumClaim').value;
	var inputObject = field;
	var outputObject;
	var inputArgs = {
		kindCode: kindCode,
		kindName: kindName,
		policyNo: policyNo,
		riskCode: riskCode,
		allKindLoss: allKindLoss
	};
	var param = DWRUtil.getValues(inputArgs);
	dwrInvokeData("checkBeyondAmount", param, "rollbackCheckBeyondAmount", inputObject, outputObject);
}

function rollbackCheckBeyondAmount(inputObject, outputObject, returnObject) {
	var result = returnObject;
	if (result == "true" || result == null) {} else
		alert(result);
	//add by liping 08-04-24 
	undisablebutton();
}