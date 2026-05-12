//缓存input获得焦点时的值 onfocus事件,作用是:当前校验项失去焦点时，若本次录入不通过校验则还原其本次值

function cacheData(field) {
	$(field).data(field.name, field.value);
}
//还原 field 域的值，作用是：本次录入通不过校验，则还原域的值为其获得焦点时的值

function recoveryData(field) {
	$(field).val($(field).data(field.name));
}
//判断值域是否改变

function isChange(field) {
	return field.value != $(field).data(field.name);
}
//理算校验比例录入 最好在失去焦点时校验

function validatePercent(field, min, max) {
	if (isNaN(field.value) || parseFloat(field.value) < parseFloat(min) || parseFloat(field.value) > parseFloat(max)) {
		recoveryData(field);
		return alertMessage(field, $(field).attr("title") + "必須在" + min + "%與" + max + "%之間 !");
	}
	return true;
}

function deletePrpLclaimLossObject(field) { //增加一个估损信息
	$(field).parents("tr[name='prpLclaimLossObject']").remove();
}

function insertPrpLclaimLossObject() { //删除一个估损信息
	$("#ClaimLoss_Data").find("tr[name='prpLclaimLossObject']").clone(true).appendTo("#ClaimLoss > tbody");
}

function collectClaimLossNew() { //汇总 保險損失金額 和 責任估損金額 需要统一汇率
	var dutyLoss = 0; //責任估損金額
	var prpLclaimCurrency = $(":input[name='prpLclaimCurrency']").val();
	$("#ClaimLoss").find("tr[name='prpLclaimLossObject']").each(function () {
		var currency = $(this).find(":input[name='prpLclaimLossCurrency']").val(); //损失币别
		var kindCode = $(this).find(":input[name='prpLclaimLossKindCode']").val(); //险别
		var sumLoss = $(this).find(":input[name='prpLclaimLossSumClaim']").val(); //险别估损金额
		if ($.trim(kindCode) != "" && parseFloat(sumLoss) != 0) {
			dutyLoss += (isNaN(sumLoss) ? 0 : parseFloat(getAmount(currency, prpLclaimCurrency, sumLoss)));
		}
	});
	$(":input[name='prpLclaimSumClaim']").val(Math.round(dutyLoss));
}
//获取金额

function getAmount(baseCurrency, exchCurrency, lossAmount) {
	if (baseCurrency == exchCurrency) {
		return lossAmount;
	}
	$.getJSON("getExchangeRate.do", {
		baseCurrency: baseCurrency,
		exchCurrency: exchCurrency
	}, function (data) {
		return parseFloat(data.exchangeRate);
	});
}
//计算立案估损金额

function calculateSumClaimNew(field) {
	var $prpLclaimLossObject = $(field).parents("tr[name='prpLclaimLossObject']");
	var kindLoss = $prpLclaimLossObject.find(":input[name='prpLclaimLossKindLoss']").val(); //当前上报估损金额
	var kindCode = $prpLclaimLossObject.find(":input[name='prpLclaimLossKindCode']").val(); //当前险别
	var indemnityDutyRate = $(":input[name='prpLclaimIndemnityDutyRate']").val(); //事故责任比例
	if(!$.isNumeric(kindLoss)){
		kindLoss = 0;
		$prpLclaimLossObject.find(":input[name='prpLclaimLossKindLoss']").val(kindLoss);
	}
	if( parseFloat(kindLoss) == 0){
		$prpLclaimLossObject.find(":input[name='prpLclaimLossSumClaim']").val(Math.round(kindLoss));
		collectClaimLossNew(); //先汇总损失金额 估损金额
	}
	if ($.trim(kindCode) != "" && parseFloat(kindLoss) != 0) {
		collectClaimLossNew(); //先汇总损失金额 估损金额
		var allKindLoss = $(":input[name='prpLclaimSumClaim']").val(); //保险损失金额
		var policyNo = $(":input[name='policyno']").val();
		var riskCode = $(":input[name='prpLclaimRiskCode']").val();
		var indemnityDuty = $(":input[name='indemnityDuty']").val();
		var registNo = $(":input[name='prpLclaimRegistNo']").val();
		var startDate = $(":input[name='prpLclaimStartDate']").val();
		var damageDate = $(":input[name='prpLclaimDamageStartDate']").val();
		var feeType = $prpLclaimLossObject.find(":input[name='prpLclaimLossLossFeeType']").val(); //
		var limitType = $prpLclaimLossObject.find(":input[name='prpLclaimLossFeeCategory']").val(); //范围
		var kindname = $prpLclaimLossObject.find(":input[name='prpLclaimLossKindName']").val();
		var dangerNo = $prpLclaimLossObject.find(":input[name='prpLclaimLossDangerNo']").val();
		var kindLossSum = 0; //险别损失金额之和
		var prpLclaimCurrency = $(":input[name='prpLclaimCurrency']").val(); //目标币别
		var prpLclaimCurrencyName = $(":input[name='prpLclaimCurrencyName']").val();
		$("#ClaimLoss").find("tr[name='prpLclaimLossObject']").each(function () {
			var lossDangerNo = $(this).find(":input[name='prpLclaimLossDangerNo']").val(); //危险单位
			var lossCurrency = $(this).find(":input[name='prpLclaimLossCurrency']").val(); //损失币别
			var lossKindCode = $(this).find(":input[name='prpLclaimLossKindCode']").val(); //险别
			var lossKindLoss = $(this).find(":input[name='prpLclaimLossKindLoss']").val(); //上报估损金额
			var lossFeeType = $(this).find(":input[name='prpLclaimLossLossFeeType']").val(); //损失类别
			if (dangerNo == lossDangerNo && lossFeeType == "P" && lossKindCode == kindCode) {
				kindLossSum += (isNaN(lossKindLoss) ? 0 : parseFloat(getAmount(lossCurrency, prpLclaimCurrency, lossKindLoss)));
			}
		});
		var inputArgs = {
			feeType: feeType,
			registNo: registNo,
			allKindLoss: allKindLoss,
			kindCode: kindCode,
			kindName: encodeURI(kindname),
			policyNo: policyNo,
			riskCode: riskCode,
			indemnityDuty: indemnityDuty,
			indemnityDutyRate: indemnityDutyRate,
			kindLossSum: kindLossSum,
			kindLoss: kindLoss,
			limitType: limitType,
			startDate: startDate,
			damageDate: damageDate,
			prpLclaimCurrencyName: encodeURI(prpLclaimCurrencyName)
		};
		$.getJSON("getSumClaim.do", inputArgs, function (data) {
			if (data.resultFlag) {
				//mantis：CLM0217，處理人員：DP0713，需求單編號：新核心-車險更正預估卡控異常 START
//				$prpLclaimLossObject.find(":input[name='prpLclaimLossSumClaim']").val(Math.round(data.sumClaim));
				
				var $SumClaim = $prpLclaimLossObject.find(":input[name='prpLclaimLossSumClaim']");
				cacheData($SumClaim[0]); //缓存其本次计算前的值（超限则还原）
				$SumClaim.val(Math.round(data.sumClaim));
				//P -赔款，Z-费用
				var lossFeeType = $prpLclaimLossObject.find(":input[name='prpLclaimLossLossFeeType']").val();
				//車損-C,物損-G,醫療-M,殘廢-H,死亡-D,其他-O
				var feeCategory = $prpLclaimLossObject.find(":input[name='prpLclaimLossFeeCategory']").val();
				var type = "";
				if(lossFeeType=="P"){
					if("C,G".indexOf(feeCategory)>-1){
						type = "0";
					}else if("M,H,D".indexOf(feeCategory)>-1){
						type = "1";
					}
				}
				if(type==""){
					collectClaimLossNew();
				}else{
					if (checkLimit(kindCode, type, Math.round(data.sumClaim),feeCategory)) { //超出限额
						recoveryData(field); //还原当前改变项
						recoveryData($SumClaim[0]); //还原当前赔付额
					} else {
						collectClaimLossNew();
					}
				}
//				collectClaimLossNew();
				//mantis：CLM0217，處理人員：DP0713，需求單編號：新核心-車險更正預估卡控異常 END
			} else {
				$prpLclaimLossObject.find(":input[name='prpLclaimLossKindLoss']").val(0);
				$prpLclaimLossObject.find(":input[name='prpLclaimLossSumClaim']").val(0);
				collectClaimLossNew();
				alert(data.errorMessage);
			}
		});
	}
}

/**
 *缓存input获得焦点时的值 onfocus事件,作用是:当前校验项失去焦点时，若本次录入不通过校验则还原其本次值
 *來源:\claim\webapp\claim\pages\DAA\claim\js\DAAClaimEditNew.js
 * mantis：CLM0217，處理人員：DP0713，需求單編號：新核心-車險更正預估卡控異常
 * @param field
 */
function cacheData(field) {
	$(field).data(field.name, field.value);
}

/**
 * 判断车物损、人伤是否超出险别限额
 * 
 * 來源:\claim\webapp\claim\pages\DAA\claim\js\DAAClaimEditNew.js
 * mantis：CLM0217，處理人員：DP0713，需求單編號：新核心-車險更正預估卡控異常
 * 
 * @param kindCode 险别
 * @param type 0 车物损 ； 1 人伤
 * @param identifyNumber 当前受害人身份证
 * @returns {Boolean} true 超限
 * 校验规则：
 * limitMeter 计次：不能超过赔付次数 -1 本次赔付无次数限制
 * limitResidue 保险期间累计：的历次含本次总赔付不能超限额 -1非累计
 * limitType 0：每次事故：车物损、人伤总赔款不得超过限额limitAmount
 * limitType 2：每次事故：可对车物损、人伤分别进行赔付。每次事故赔车物损部分总额不得超过限额limitPropAmount
 * 			   每次事故赔人伤部分每人不得超过限额limitAmount - limitPropAmount
 * limitType 1：每次事故：人伤赔付且每次事故不得超过限额limitAmount,每人不得超过限额limitPersonAmount
 */
function checkLimit(kindCode, type, payAmount,identifyNumber) {
	if (payAmount == 0) { //当前计算赔付为0 默认不会超出限额
		return false;
	}
	var riskCode = $(":input[name='prpLclaimRiskCode']").val();
	if(riskCode == RISKINFO.RISKCODE_DAZ){
		return checkLimitDAZ(kindCode,identifyNumber);
	}
	var $limitObject = $("#limitList").find(":input[name='limitKindCode'][value='" + kindCode + "']").parents("div[name='limitObject']");
	var messges = "";
	if ($limitObject.length > 0) {
		var limitFlag = $limitObject.find("input[name='limitFlag']").val(); //true 接受限额控制的险别
		if (limitFlag == "0") {
			var limitKindCode = $limitObject.find("input[name='limitKindCode']").val();
			var limitKindName = $limitObject.find("input[name='limitKindName']").val();
			var limitAmount = Math.round(parseFloat($limitObject.find("input[name='limitAmount']").val()));
			var limitPastPay = Math.round(parseFloat($limitObject.find("input[name='limitPastPay']").val()));
			var limitMeter = $limitObject.find("input[name='limitMeter']").val();
			var limitType = $limitObject.find("input[name='limitType']").val();
			var limitResidue = parseFloat($limitObject.find("input[name='limitResidue']").val());
			var checkPerson = false; //是否需要校验每人是否超限额的标志
			if (limitMeter == "0") {
				messges += limitKindName + "賠付次數已達上限!(可賠付次數：" + $limitObject.find("input[name='limitMaxNum']").val() + ")\n";
			} else if (limitResidue == 0) {
				messges += limitKindName + "累計賠付已達上限!(已累計賠付：" + $limitObject.find("input[name='limitTotalPay']").val() + ")\n";
			} else if (limitType == 0 || limitType == 1) { //每次事故 人伤\车\财产总和不得超过limitAmount
				var sumPay = getPrpLlossPayAmount(kindCode) + getPrpLpersonLossPayAmount(kindCode); //每事故赔付合计
				if ((sumPay + limitPastPay) > limitAmount) { //每事故是否超出限额
					messges += limitKindName + "本案估損超出" + (sumPay + limitPastPay - limitAmount) + "元!(限額：" + limitAmount + ")\n";
				} else { //校验每人的情况
					if (limitType == 1 && type == "1") {
						checkPerson = true;
					}
				}
			} else if (limitType == 2) {
				var limitPersonPastPay = Math.round(parseFloat($limitObject.find("input[name='limitPersonPastPay']").val()));
				var limitPropAmount = Math.round(parseFloat($limitObject.find("input[name='limitPropAmount']").val()));
				var limitPropPastPay = limitPastPay - limitPersonPastPay;
				if (type == "0") { //赔付车物损的险别、单独又计算每人每事故的情况
					var sumPropPay = getPrpLlossPayAmount(kindCode);
					if ((sumPropPay + limitPropPastPay) > limitPropAmount) {
						messges += limitKindName + "本案估損超出" + (sumPropPay + limitPropPastPay - limitPropAmount) + "元!(車物損估損限額：" + limitPropAmount + ")\n";
					}
				} else if (type == "1") { //赔付人伤，人伤单独限制
					var personAmount = limitAmount - limitPropAmount;
					var sumPersonPay = getPrpLpersonLossPayAmount(kindCode); //每事故赔付合计
					if ((sumPersonPay + limitPersonPastPay) > personAmount) { //每事故人伤赔付合计否超出限额
						messges += limitKindName + "本案估損超出" + (sumPersonPay + limitPersonPastPay - personAmount) + "元!(人傷估損限額：" + personAmount + ")\n";
					} else {
						checkPerson = true;
					}
				}
			}
//			if (checkPerson) {
//				var limitPersonAmount = Math.round(parseFloat($limitObject.find("input[name='limitPersonAmount']").val()));
//				var personPay = getPersonPayAmount(kindCode, identifyNumber); //该受害人赔付合计
//				var $pastPay = $("#limitList").find(":input[name='" + identifyNumber + "_" + kindCode + "']");
//				if ($pastPay.length > 0) { //该受害人在本案已审核通过的计算书中有赔付
//					personPay += ($pastPay.val() == "" || isNaN($pastPay.val()) ? 0 : parseFloat($pastPay.val()));
//				}
//				if (personPay > limitPersonAmount) {
//					messges += limitKindName + "受害人" + identifyNumber + "本案賠付超出" + (personPay - limitPersonAmount) + "元!(限額：" + limitPersonAmount + "元/人)\n";
//				}
//			}
		}
	}
	if (messges.length > 0) {
		alert(messges);
		return true;
	}
	return false;;
}


/***
 * 获取当前险别的车物损赔付总额
 * 來源:\claim\webapp\claim\pages\DAA\claim\js\DAAClaimEditNew.js
 * mantis：CLM0217，處理人員：DP0713，需求單編號：新核心-車險更正預估卡控異常
 * @param currKindCode
 */
function getPrpLlossPayAmount(currKindCode) {
	var sumPay = 0; //当前的险别赔款总额
	$("#ClaimLoss").find("tr[name='prpLclaimLossObject']").each(function () {
		//P -赔款，Z-费用
		var lossFeeType = $(this).find(":input[name='prpLclaimLossLossFeeType']").val();
		//車損-C,物損-G,醫療-M,殘廢-H,死亡-D,其他-O
		var feeCategory = $(this).find(":input[name='prpLclaimLossFeeCategory']").val();
		if(lossFeeType=="P"&&"C,G".indexOf(feeCategory)>-1){
			var $kindCode = $(this).find(":input[name='prpLclaimLossKindCode'][value='" + currKindCode + "']");
			if ($kindCode.length > 0) {
				var sumRealPay = $(this).find(":input[name='prpLclaimLossSumClaim']").val();
				sumPay += (sumRealPay == "" || isNaN(sumRealPay) ? 0 : parseFloat(sumRealPay));
			}
		}
	});
	return sumPay;
}


/***
 * 获取当前险别的人伤赔款金额
 * 來源:\claim\webapp\claim\pages\DAA\claim\js\DAAClaimEditNew.js
 * mantis：CLM0217，處理人員：DP0713，需求單編號：新核心-車險更正預估卡控異常
 * @param currKindCode
 * @returns {Number}
 */
function getPrpLpersonLossPayAmount(currKindCode) {
	var sumPay = 0; //当前的险别赔款总额
	$("#ClaimLoss").find("tr[name='prpLclaimLossObject']").each(function () {
		//P -赔款，Z-费用
		var lossFeeType = $(this).find(":input[name='prpLclaimLossLossFeeType']").val();
		//車損-C,物損-G,醫療-M,殘廢-H,死亡-D,其他-O
		var feeCategory = $(this).find(":input[name='prpLclaimLossFeeCategory']").val();
		if(lossFeeType=="P"&&"M,H,D".indexOf(feeCategory)>-1){
			var $kindCode = $(this).find(":input[name='prpLclaimLossKindCode'][value='" + currKindCode + "']");
			if ($kindCode.length > 0) {
				var sumRealPay = $(this).find(":input[name='prpLclaimLossSumClaim']").val();
				sumPay += (sumRealPay == "" || isNaN(sumRealPay) ? 0 : parseFloat(sumRealPay));
			}
		}
	});
	return sumPay;
}
/**
 * 來源:\claim\webapp\claim\pages\DAA\claim\js\DAAClaimEditNew.js
 * mantis：CLM0217，處理人員：DP0713，需求單編號：新核心-車險更正預估卡控異常
 * @param kindCode
 * @param feeCategory
 * @returns {Boolean}
 */
function checkLimitDAZ(kindCode,feeCategory) {
	return false;//强制险没有限额。
}