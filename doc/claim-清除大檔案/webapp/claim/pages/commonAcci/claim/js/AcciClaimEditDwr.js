/**
 @description 计算保险损失金额
 @param       无
 @return      无
 */
//function collectClaimLoss(field) {
//	//原来通过dwr汇总金额存在精度问题，现在不存在币别问题，直接在页面用js计算，不走dwr
//	var baseCurrency = '';
//	var exchCurrency = fm.prpLclaimCurrency.value; //目标币别
//	var nowAmout = '';
//	var sumClaimLoss = 0.00;
//	for (var n = 1; n < fm.prpLclaimLossSumClaim.length; n++) {
//		baseCurrency = baseCurrency + "-" + fm.prpLclaimLossCurrency[n].value;
//		nowAmout = nowAmout + "-" + fm.prpLclaimLossSumClaim[n].value;
//		sumClaimLoss += parseFloat(fm.prpLclaimLossSumClaim[n].value);
//	}
//	fm.prpLclaimSumClaim.value = point(round(sumClaimLoss, 0), 0);
//	return true;
//}

function rollbackClaimLoss(inputObject, outputObject, returnObject) {
	var compAmout = returnObject;
	undisablebutton();
}