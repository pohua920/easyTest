/**
 * 立案计算险别估损金额
 * @author 中科软
 */

function calculateSumClaim(field) {
	var allKindLoss = $('prpLclaimSumClaim').value;
	var outputObject;
	var inputObject = field;
	var indemnityDutyRate = $('prpLclaimIndemnityDutyRate').value;
	var policyNo = $('policyno').value;
	var riskCode = $('prpLclaimRiskCode').value;
	var indemnityDuty = $('indemnityDuty').value;
	var registNo = $('prpLclaimRegistNo').value;
	var startDate = $('prpLclaimStartDate').value;
	var damageDate = $('prpLclaimDamageStartDate').value;

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
	//add by luochang begin 加总同危险单位、同险别赔款，以用作和赔偿限额比较
	var kindloss = 0;
	/**add by chenjie 2013-03-13 交强险 start*/
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all('prpLclaimLossKindCode')[findex].value == RISKINFO.KINDCODE_D_BZ) { //交强险
			if (fm.all('prpLclaimLossDangerNo')[i].value == fm.all('prpLclaimLossDangerNo')[findex].value &&
				fm.all('prpLclaimLossLossFeeType')[i].value == 'P' &&
				fm.all('prpLclaimLossKindCode')[i].value == fm.all('prpLclaimLossKindCode')[findex].value) {
				if (fm.all('prpLclaimLossFeeCategory')[i].value != fm.all('prpLclaimLossFeeCategory')[findex].value) {
					if ((fm.all('prpLclaimLossFeeCategory')[findex].value == "C" && fm.all('prpLclaimLossFeeCategory')[i].value == "G") ||
						(fm.all('prpLclaimLossFeeCategory')[findex].value == "G" && fm.all('prpLclaimLossFeeCategory')[i].value == "C")) {
						kindloss += parseFloat(fm.all('prpLclaimLossKindLoss')[i].value);
					}
				} else {
					kindloss += parseFloat(fm.all('prpLclaimLossKindLoss')[i].value);
				}
			}
		} else {
			if (fm.all('prpLclaimLossDangerNo')[i].value == fm.all('prpLclaimLossDangerNo')[findex].value &&
				fm.all('prpLclaimLossLossFeeType')[i].value == 'P' &&
				fm.all('prpLclaimLossKindCode')[i].value == fm.all('prpLclaimLossKindCode')[findex].value) {
				kindloss += parseFloat(fm.all('prpLclaimLossSumClaim')[i].value);
			}
		}
	}
	//add by luochang end 加总同危险单位、同险别赔款，以用作和赔偿限额比较
	var limitType = fm.all('prpLclaimLossFeeCategory')[findex].value;
	var kindCode = fm.all('prpLclaimLossKindCode')[findex].value;
	if (kindCode == "M") { //选择不计免赔率特约上报估损金额不需要，默认为零
		alert(i18n.claim.notAllowedChooseArrange); // 不允许选择不计免赔率特约！
		fm.all('prpLclaimLossKindCode')[findex].value = "";
		fm.all('prpLclaimLossKindName')[findex].value = "";
		fm.all('prpLclaimLossKindLoss')[findex].value = 0;
		collectClaimLoss(field); //保险损失金额
		return false;
	}
	inputObject = fm.all('prpLclaimLossSumClaim')[findex];
	if (checkLossKindCode()) {
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

function rollbackCheckResult(inputObject, outputObject, returnObject) {
	var result = returnObject;
	if (result == "true" || result == null) {
		var fieldname = inputObject.name;
		var findex = 0;
		for (i = 1; i < fm.all(fieldname).length; i++) {
			if (fm.all(fieldname)[i] == inputObject) {
				findex = i;
				break;
			}
		}
		var feeType = fm.all('prpLclaimLossLossFeeType')[findex].value; //损伤类别
		var kindloss = fm.all('prpLclaimLossKindLoss')[findex].value; //上报估损金额
		var kindCode = fm.all('prpLclaimLossKindCode')[findex].value; //险别
		inputObject = fm.all('prpLclaimLossSumClaim')[findex]; //险别估损金额
		var riskCode = $('prpLclaimRiskCode').value; //险种
		var indemnityDuty = $('indemnityDuty').value; //事故责任
		var registNo = $('prpLclaimRegistNo').value; //报案号
		var indemnityDutyRate = $('prpLclaimIndemnityDutyRate').value; //责任比例
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
	} else {
		alert(result);
		var fieldname = inputObject.name;
		var findex = 0;
		for (i = 1; i < fm.all(fieldname).length; i++) {
			if (fm.all(fieldname)[i] == inputObject) {
				findex = i;
				break;
			}
		}
		fm.all('prpLclaimLossSumClaim')[findex].value = 0;
		fm.all('prpLclaimLossKindLoss')[findex].value = 0;
	}
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
	fm.prpLclaimLossSumClaim[findex].value = pointTwo(returnObject);
	calculateExceptDeductiblePay(findex);
	collectClaimDutyLoss(inputObject);
	undisablebutton();
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

function rollbackClaimDutyLoss(inputObject, outputObject, returnObject) {
	var compAmout = returnObject;
	DWRUtil.setValue("prpLclaimSumClaim", pointTwo(compAmout));
	undisablebutton();
}

function checkLossKindCode() {
	for (var j = 1; j < fm.prpLclaimLossCurrency.length; j++) {
		if (isEmptyField(fm.prpLclaimLossKindCode[j])) {
			errorMessage("第" + j + "条估损金额中险别代码不能为空!");
			return false;
		}

		if (isEmptyField(fm.prpLclaimLossSumClaim[j])) {
			return false;
		}
	}
	return true;
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
		var claimLossSumClaim = 0;
		//循环分币别统计
		for (var ii = 1; ii < fm.prpLclaimLossCurrency.length; ii++) {
			if (currency == fm.prpLclaimLossCurrency[ii].value) {
				claimLossSumClaim = parseFloat(fm.prpLclaimLossSumClaim[ii].value);
				sumLossAmount = sumLossAmount + claimLossSumClaim;
			}
		}

		if (currency == CURRENCYINFO.LOCAL_CURRENCY) { //获取不计免赔额
			sumLossAmount = sumLossAmount + parseFloat(fm.exceptDeductibleRateAll.value);
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
		collectCurr = collectCurr + currency + "  " + currencyName + "  " + pointTwo(round(sumLossAmount, 0), 0) + i18n.modifySumClaim.yuan + "\n"; //元\n
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
		var indemnityDutyRate = $('prpLclaimIndemnityDutyRate').value;
		var indemnityDuty = $('indemnityDuty').value;
		var riskCode = $('prpLclaimRiskCode').value;
		var registNo = $('prpLclaimRegistNo').value;
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
			fm.exceptDeductiblePay[j].value = pointTwo(account[0]);
			//险别不计免赔率
			fm.prpLclaimLossAcciDeductibleRate[index].value = fm.exceptDeductibleRate[j].value;
			//险别不计免赔额
			fm.prpLclaimLossAcciDeductiblePay[index].value = pointTwo(account[1]);
			fm.prpLclaimLossKindCode1[index].value = kindCode;
		}
		//不计免赔栏中的总赔偿金额
		exceptDeductibleRateAll = exceptDeductibleRateAll + parseFloat(fm.exceptDeductiblePay[j].value);
	}

	fm.exceptDeductibleRateAll.value = pointTwo(exceptDeductibleRateAll);
	undisablebutton();
}

/**add by liuwei at 2011-07-18 用於在不计免赔栏中添加数据*/

function insertRow2(field) {
	var flag = 0;
	var kind;
	var fieldname = field.name;
	var findex = 0;
	for (var i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == field) {
			findex = i;
			break;
		}
	}

	kind = fm.prpLclaimLossKindCode[findex].value;
	if (isEmptyField(field)) { //如果还未确定险别，放弃添加
		flag = 2;
	} else {
		if (fm.exceptDeductibleKindCode) { //判断不计免赔栏中是否已经有了这个险别
			for (var index = 0; index < fm.exceptDeductibleKindCode.length; index++) {
				if (fm.exceptDeductibleKindCode[index].value != "") {
					if (fm.exceptDeductibleKindCode[index].value == kind) {
						flag = 2;
					}
				}
			}
		}
	}

	if (flag == 0) {
		checkExceptDeductible(kind, field);
	}
}

/**用於在不计免赔栏中删除数据*/

function deleteRow2(field) {
	var fieldname = field.name;
	var fieldValue = "";
	var lossKind = "";
	var flag = 0;
	var flag1 = 0;
	var countLoss = 0;
	var findexExcept = 0;
	var findexLoss = 0;
	var exceptDeductibleRateAll = 0;
	var order = getElementOrder(field);
	if (fm.exceptDeductibleKindCode) {
		fieldValue = fm.prpLclaimLossKindCode[order - 1].value;
		lossKind = "fm.prpLclaimLossAcciDeductiblePay";
	}
	//判断不计免赔栏中是否有该险别
	for (var index = 0; index < fm.exceptDeductibleKindCode.length; index++) {
		if (fm.exceptDeductibleKindCode[index].value != "") {
			if (fm.exceptDeductibleKindCode[index].value == fieldValue) {
				flag = 2;
				findexExcept = index;
			}
		}
	}

	if (flag == 2) { //如果没有该险种的不计免赔就放弃以下操作
		if (fm.prpLclaimLossKindCode) { //判断该险别是否还有其它损失
			for (var index1 = 0; index1 < fm.prpLclaimLossKindCode.length; index1++) {
				flag1 = 0;
				if (fm.prpLclaimLossKindCode[index1].value == fieldValue) {
					if (fm.all(fieldname)[index1] == field) {
						findexLoss = index1;
					}
					if ("P" == fm.prpLclaimLossLossFeeType[index1].value && flag1 == 0) {
						countLoss++;
					}
				}
			}
		}

		if (countLoss > 0) {
			if (eval(lossKind + "[" + findexLoss + "]")) {
				if (fm.exceptDeductiblePay[findexExcept].value != "" && eval(lossKind + "[" + findexLoss + "].value") != "") {
					fm.exceptDeductiblePay[findexExcept].value = pointTwo(parseFloat(fm.exceptDeductiblePay[findexExcept].value) - parseFloat(eval(lossKind + "[" + findexLoss + "].value")));
				}
			}
		} else {
			deleteRow(fm.exceptDeductibleKindCode[findexExcept], 'exceptLoss1');
		}

		for (var j = 1; j < fm.exceptDeductibleKindCode.length; j++) {
			exceptDeductibleRateAll = exceptDeductibleRateAll + parseFloat(fm.exceptDeductiblePay[j].value);
		}

		fm.exceptDeductibleRateAll.value = pointTwo(exceptDeductibleRateAll);
	}
}

/**用於检测不计免赔栏中的险种是否和受损险种一致*/

function checkExcept(field) {
	var order = getElementOrder(field);
	var flag = 0;
	if (fm.exceptDeductibleKindCode) {
		for (var index = 1; index < fm.exceptDeductibleKindCode.length; index++) {
			if (fm.exceptDeductibleKindCode[index].value != "") {
				if (fm.prpLclaimLossKindCode) {
					for (var index1 = 1; index1 < fm.prpLclaimLossKindCode.length; index1++) {
						if (fm.prpLclaimLossKindCode[index1].value != "" && "P" == fm.prpLclaimLossLossFeeType[index1].value) {
							if (fm.prpLclaimLossKindCode[index1].value == fm.exceptDeductibleKindCode[index].value) {
								flag = 1;
							}
						}
					}
				}
			}

			if (flag == 0 && fm.exceptDeductibleKindCode[index].value != "") {
				if (parseFloat(fm.exceptDeductibleRateAll.value) != 0) {
					fm.exceptDeductibleRateAll.value = pointTwo(parseFloat(fm.exceptDeductibleRateAll.value) - parseFloat(fm.exceptDeductiblePay[index].value));
				}
				deleteRow(fm.exceptDeductibleKindCode[index], 'exceptLoss1');
			}

			if (flag == 1 && fm.prpLclaimLossKindCode1[order - 1].value == fm.exceptDeductibleKindCode[index].value && fm.prpLclaimLossKindCode[order - 1].value == "") {
				fm.exceptDeductiblePay[index].value = pointTwo(parseFloat(fm.exceptDeductiblePay[index].value) - parseFloat(fm.prpLclaimLossAcciDeductiblePay[order - 1].value));
				fm.exceptDeductibleRateAll.value = pointTwo(parseFloat(fm.exceptDeductibleRateAll.value) - parseFloat(fm.prpLclaimLossAcciDeductiblePay[order - 1].value));
				fm.prpLclaimLossKindCode1[order - 1].value = "";
				if ("P" == fm.prpLclaimLossLossFeeType[order - 1].value) {
					fm.prpLclaimLossAcciDeductiblePay[order - 1].value = 0;
					fm.prpLclaimLossAcciDeductibleRate[order - 1].value = 0;
				}
			}
			flag = 0;
		}
	}
}

/**验证该险种是否已经购买了不计免赔*/

function checkExceptDeductible(kind, field) {
	var policyno = fm.policyno.value;
	var riskCode = $('prpLclaimRiskCode').value;
	var registNo = $('prpLclaimRegistNo').value;
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

/** 刷新总免赔额 */

function flashExceptDeductibleRateAll() {
	var sumAccount = 0;
	for (var i = 1; i < fm.exceptDeductibleKindCode.length; i++) {
		sumAccount = sumAccount + parseFloat(fm.exceptDeductiblePay[i].value);
	}
	fm.exceptDeductibleRateAll.value = pointTwo(sumAccount);
}
