/**
 @description 汇总估损金额
 @param       无
 @return      无
 */

function collectCurrency1() {
	var compAmout = 0;
	var nowAmout = 0;
	var exchCurrency = fm.prpLclaimCurrency.value; //得到当前币别类型
	var exchRate = 1; //兑换率
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

//汇总险别估损信息 ,根据币别汇总

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