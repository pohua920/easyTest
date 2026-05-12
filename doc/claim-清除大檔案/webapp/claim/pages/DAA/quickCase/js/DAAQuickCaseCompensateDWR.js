//全局变量用来控制dwr的进度
var deepth11 = -1;
var deepth22 = -1;
var deepth2 = -1;
var deepth1 = -1;
/**
 *计算变更险种时的人员赔付金额
 */

function getThisField(Field) {
	var fieldname = field.name;
	var findex = 0;
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == field) {
			findex = i;
			break;
		}
	}
}

function changeIndemnityDuty() {
	var indemnityDuty = ""; //设置的值
	var i = 0; //循环使用
	switch (fm.indemnityDuty.value) {
	case "0": //全责
		indemnityDuty = "100";
		break;
	case "1": //主责
		indemnityDuty = "70";
		break;
	case "2": //同责
		indemnityDuty = "50";
		break;
	case "3": //次责
		indemnityDuty = "30";
		break;
	case "4": //无责
		indemnityDuty = "0.0";
		break;
	case "9": //其它
		if (fm.prpLcompensateIndemnityDutyRate.value != "")
			indemnityDuty = parseInt(fm.prpLcompensateIndemnityDutyRate.value);
		else
			indemnityDuty = "0.0";
		break;
	}
	fm.prpLcompensateIndemnityDutyRate.value = indemnityDuty;
	if (fm.all("prpLlossDtoIndemnityDutyRate").length > 1) {
		deepth11 = 1;
		deepth1 = fm.all("prpLlossDtoIndemnityDutyRate").length;
	}
	for (var index = 1; index < fm.all("prpLlossDtoIndemnityDutyRate").length; index++) {
		fm.all("prpLlossDtoIndemnityDutyRate")[index].value = indemnityDuty;
		calRealpay(fm.all("prpLlossDtoIndemnityDutyRate")[index]);
	}
}

/**
 *设置免赔条件
 */

function initDeductCond() {
	if (fm.all("prpLlossDtoIndemnityDutyRate").length > 1) {
		deepth11 = 1;
		deepth1 = fm.all("prpLlossDtoIndemnityDutyRate").length;
	}
	//将物损进行调整
	for (var index = 1; index < fm.all("prpLlossDtoIndemnityDutyRate").length; index++) {
		calRealpay(fm.all("prpLlossDtoIndemnityDutyRate")[index]);
	}
}

function calRealpayAll() {
	//将物损进行调整
	for (var index = 1; index < fm.all("prpLlossDtoIndemnityDutyRate").length; index++) {
		calRealpay(fm.all("prpLlossDtoIndemnityDutyRate")[index]);
	}
}

function calRealpay(field) {
	disablebutton();
	if (field.value != "") {
		var fieldname = field.name;
		var findex = 0;
		for (i = 1; i < fm.all(fieldname).length; i++) {
			if (fm.all(fieldname)[i] == field) {
				findex = i;
				break;
			}
		}
		if (findex != 'undefined')
			if (fm.prpLlossDtoKindCode[findex].value != 'C5' && fm.prpLlossDtoKindCode[findex].value != ConstantCodes.KINDCODE_D_BZ) {
				var count = getElementCount("deductName");
				var deductCondArray = new Array();
				for (var j = 0; j < count; j++) {
					deductCondArray[j] = ({
						id: {
							deductCondCode: fm.all("deductConditionTemp")[j].value
						},
						deductCondName: fm.all("deductName")[j].value,
						times: parseInt(fm.all("Times")[j].value)
					});
				}

				DWREngine.setAsync(false);
				dwrInvokeDataAction.getDeductCondList(deductCondArray);
				DWREngine.setAsync(true);
				var licenseNo = "";
				var licenseNoA = "";
				var kindCode;
				var SumDefPay; //核定赔偿金额
				var purchasePrice; //新车购置价（出险时）
				var factValue; //实际价值（出险时）
				var SumLoss; //核损金额
				var SumRest; //残值
				var CompelPay; //交强险赔款
				var ClaimRate; //承保比例
				var DutyDeductibleRate; //事故责任免赔率
				var DeductibleRate; //免赔率
				var DriverDeductibleRate; //驾驶员免赔率
				var mainKindCode; //主险代码
				var DeductibleRateOfMainKind; //主险的绝对免赔率
				var Deductible; //可选免赔额
				var Deductibletemp; //免赔
				var DutyRate; //责任比例
				var ArrangeRate; //协商赔偿比例
				var Realpay; //赔偿金额
				var temp;
				var unitPrice;
				var Amount;
				var outputObject;
				var inputObject = field;
				var prpLlossDtoIsLossAll = "";
				var policyno = fm.policyNo.value;
				var exceptDeductiblePay;
				var exceptDeductibleRate;
				var riskCode = fm.riskCode.value;
				var indemnityDuty = fm.indemnityDuty.value;
				var registNo = fm.registNo.value;
				var flagn = '0';
				var kindCodeTemp;

				//出险日期
				var damageStartDate = fm.prpLcheckDamageStartDate.value;
				//出险小时
				var damageStartHour = "";
				//标的车牌照
				licenseNoA = "";
				for (var i = 0; i < fm.all("checkPrpLthirdPartyDtoLicenseNo").length; i++) {
					if (fm.all("checkPrpLthirdPartyDtoInsureCarFlag")[i].value == "1") {
						licenseNoA = fm.all("checkPrpLthirdPartyDtoLicenseNo")[i].value;
					}
				}
				//车辆牌照
				licenseNo = fm.licenseNo[findex].value;
				//标的车新车购置价（出险时）
				purchasePrice = fm.purchasePrice.value;
				//标的车的实际价值（出险时）
				factValue = fm.factValue.value;
				//实际损失
				SumLoss = fm.all("prpLlossDtoSumLoss")[findex].value;
				//残值
				SumRest = fm.all("prpLlossDtoSumRest")[findex].value;
				//交强险赔款
				CompelPay = fm.all("prpLlossDtoCompelPay")[findex].value;
				//核定赔偿
				SumDefPay = fm.all("prpLlossDtoSumDefPay")[findex].value;
				//承保比例
				ClaimRate = fm.all("prpLlossDtoClaimRate")[findex].value;
				//事故责任比例
				DutyRate = fm.all("prpLcompensateIndemnityDutyRate").value;
				//协商赔偿比例
				ArrangeRate = 100;
				//事故责任免赔率
				DutyDeductibleRate = fm.all("prpLlossDtoDutyDeductibleRate")[findex].value;
				//绝对免赔率
				DeductibleRate = fm.all("prpLlossDtoDeductibleRate")[findex].value;
				unitPrice = fm.all("prpLlossDtoUnitPrice")[findex].value;
				kindCode = fm.all("prpLlossDtoKindCode")[findex].value;
				kindCodeTemp = kindCode;
				Deductible = fm.prpLDeductible.value;
				if (kindCode == 'A' || kindCode == 'G' || ((kindCode == 'Y' || kindCode == 'S') && licenseNoA == licenseNo) || kindCode == 'T' || kindCode == 'E') {
					prpLlossDtoIsLossAll = fm.escapeFlag2.value;
				}

				if (kindCode == 'Y' || kindCode == 'S') //教练车和附加出境都是和车损三者互斥的险别
				{
					if (licenseNoA == licenseNo) {
						kindCode = RISKINFO.KINDCODE_D_A;
						isLicenseNo = 1;
					} else
						kindCode = RISKINFO.KINDCODE_D_B;
				}
				if (kindCode == 'X1' || (riskCode == '0502' && (kindCodeTemp == 'K1' || kindCodeTemp == 'K2'))) //发动机损失险和K1、K2都是走A险的
				{
					kindCode = RISKINFO.KINDCODE_D_A;
				}
				if (kindCode != ConstantCodes.KINDCODE_D_BZ) {　　
					if (fm.prpLlossDtoKindCodeShow.length == undefined) {
						fm.prpLlossDtoKindCodeShow.value == kindCode;
						Amount = fm.all("kindAmount").value;
						flagn = fm.flag.value;
					} else {
						for (var index = 0; index < fm.prpLlossDtoKindCodeShow.length; index++) {
							if (fm.prpLlossDtoKindCodeShow[index].value == kindCode) {
								Amount = fm.all("kindAmount")[index].value;
								flagn = fm.flag[index].value;
								break;
							}
						}
					}
					if (kindCode != kindCodeTemp) {
						if (fm.prpLlossDtoKindCodeShow.length == undefined) {
							fm.prpLlossDtoKindCodeShow.value == kindCode;
							Amount = fm.all("kindAmount").value;
							flagn = fm.flag.value;
						} else {
							for (var index = 0; index < fm.prpLlossDtoKindCodeShow.length; index++) {
								if (fm.prpLlossDtoKindCodeShow[index].value == kindCodeTemp) {
									flagn = fm.flag[index].value;
								}
								if (fm.prpLlossDtoKindCodeShow[index].value == kindCodeTemp && (kindCode == 'B' || (parseInt(fm.all("kindAmount")[index].value) == 0))) {
									fm.all("kindAmount")[index].value = Amount;
									break;
								}
							}
						}
					}
					if (kindCode == 'AB') { //提车保险赔偿主车为车损险，赔偿三者车或者财时为三者险
						if (licenseNoA == licenseNo) {
							kindCode = RISKINFO.KINDCODE_D_A;
						} else {
							kindCode = RISKINFO.KINDCODE_D_B;
						}
					}
				}
				if (purchasePrice == '' || isNaN(purchasePrice)) {
					purchasePrice = '0';
				}
				if (factValue == 0 && kindCode == 'A') {
					alert(i18n.compensate.vehicleNotActualValue); //车辆实际价值未带出，折旧率未取到！
				}
				if (kindCodeTemp == 'X1') {
					kindCode = kindCodeTemp;
				}
				if (SumDefPay == '' || isNaN(SumDefPay))
					SumDefPay = '0';
				if (SumRest == '' || isNaN(SumRest))
					SumRest = '0';
				if (CompelPay == '' || isNaN(CompelPay))
					CompelPay = '0';
				if (ClaimRate == '' || isNaN(ClaimRate))
					ClaimRate = '100';
				if (DutyRate == '' || isNaN(DutyRate))
					DutyRate = '0';
				if (ArrangeRate == '' || isNaN(ArrangeRate))
					ArrangeRate = '100';
				if (DutyDeductibleRate == '' || isNaN(DutyDeductibleRate))
					DutyDeductibleRate = '0';
				if (DeductibleRate == '' || isNaN(DeductibleRate))
					DeductibleRate = '0'
				if (isNaN(unitPrice) || unitPrice == '')
					unitPrice = '0';
				if (isNaN(Amount) || Amount == '')
					Amount = '0';
				if (isNaN(Deductible) || Deductible == '')
					Deductible = '0';
				if (policyno != "") {
					var inputArgs = {
						SumLoss: SumLoss,
						SumRest: SumRest,
						SumDefPay: SumDefPay,
						ClaimRate: ClaimRate,
						DutyRate: DutyRate,
						ArrangeRate: ArrangeRate,
						DutyDeductibleRate1: DutyDeductibleRate,
						DeductibleRate: DeductibleRate,
						unitPrice: unitPrice,
						Amount: Amount,
						riskCode1: riskCode,
						kindCode: kindCode,
						indemnityDuty0: indemnityDuty,
						registNo: registNo,
						flagn: flagn,
						prpLlossDtoIsLossAll: prpLlossDtoIsLossAll,
						policyno: policyno,
						CompelPay: CompelPay,
						purchasePrice: purchasePrice,
						factValue: factValue,
						Deductible: Deductible,
						damageStartDate: damageStartDate,
						damageStartHour: damageStartHour
					};
					var param = DWRUtil.getValues(inputArgs);
					DWREngine.setAsync(false);
					dwrInvokeData("getPrpLlossDto", param, "rollbackPrpLlossDto", inputObject, outputObject);
					DWREngine.setAsync(true);
				}
			} else if (fm.prpLlossDtoKindCode[findex].value == ConstantCodes.KINDCODE_D_BZ) {
			var sumRealPay = 0.0;
			var sumRest = 0.0;
			var sumDefPay = 0.0;
			sumRest = fm.prpLlossDtoSumRest[findex].value;
			sumDefPay = fm.prpLlossDtoSumDefPay[findex].value;
			if (isNaN(sumRest) || sumRest == '')
				sumRest = '0';
			if (isNaN(sumDefPay) || sumDefPay == '')
				sumDefPay = '0';
			sumRealpay = sumDefPay - sumRest;
			fm.prpLlossDtoSumRealPay[findex].value = sumRealpay;
			initExceptDeductible();
			calFund();
			undisablebutton();
		}
	} //end if field.value!=""

}


function rollbackPrpLlossDto(inputObject, outputObject, returnObject) {
	var prpLlossDto = returnObject;
	var fieldname = inputObject.name;
	var findex;
	for (var i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == inputObject) {
			findex = i;
			break;
		}
	}
	fm.prpLlossDtoClaimRate[findex].value = prpLlossDto.claimRate;
	fm.prpLlossDtoIndemnityDutyRate[findex].value = prpLlossDto.indemnityDutyRate;
	fm.prpLlossDtoDutyDeductibleRate[findex].value = prpLlossDto.dutyDeductibleRate;
	fm.prpLlossDtoDeductibleRate[findex].value = prpLlossDto.deductiblerate;
	fm.prpLlossDtoExceptDeductiblePay[findex].value = prpLlossDto.exceptDeductiblePay;
	fm.prpLlossDtoExceptDeductibleRate[findex].value = prpLlossDto.exceptDeductibleRate;
	fm.prpLlossDtoFlag[findex].value = prpLlossDto.flag;
	fm.prpLlossDtoSumRealPay[findex].value = point(round(prpLlossDto.sumRealPay, 0), 0);
	if (fm.prpLchargeKindCode) {
		for (var i = 1; i < fm.prpLchargeKindCode.length; i++) {
			if (fm.prpLchargeKindCode[i].value == RISKINFO.KINDCODE_D_A) {
				calChargeAmount(fm.prpLchargeChargeReport[i]);
			}
		}
	}
	initExceptDeductible();

	initEvryTypeRealPay();
	if (deepth1 != -1 || deepth2 != -1) {
		deepth11++;
		if (deepth11 == deepth1 && deepth22 == deepth2) {
			checkAmountn();
			showMessage5();
			deepth11 = -1;
			deepth1 = -1;
			deepth22 = -1;
			deepth2 = -1;
		}
	}
	calFund();
	var count = getElementCount("deductName");
	for (var j = 0; j < count; j++) {
		fm.all("deductCondition")[j].disabled = false;

	}
	undisablebutton();
}

function calPersonRealpay(Field) {
	if (Field.value != "") {
		var count = getElementCount("deductName");
		var deductCondArray = new Array();
		for (var j = 0; j < count; j++) {

			deductCondArray[j] = ({
				id: {
					deductCondCode: fm.all("deductConditionTemp")[j].value
				},
				deductCondName: fm.all("deductName")[j].value,
				times: parseInt(fm.all("Times")[j].value)
			});
		}

		DWREngine.setAsync(false);
		dwrInvokeDataAction.getDeductCondList(deductCondArray);
		DWREngine.setAsync(true);
		var fieldname = Field.name; //域名
		var i = 0;
		var findex = 0; //定位序号
		var SumLoss = 0; //核损金额
		var ClaimRate; //赔偿比例
		var CompelPay; //强制保险赔款
		var DutyDeductibleRate; //事故责任免赔率
		var DriverDeductibleRate; //驾驶员免赔率
		var DeductibleRate; //绝对免赔率
		var MainKindDeductibleRate; //所在主险的绝对免赔率
		var DutyRate; //责任比例
		var ArrangeRate; //协商赔偿比例
		var Realpay; //赔付金额
		var temp;
		var Amount;
		var outputObject;
		var inputObject = Field;
		var SumDefPay = 0;
		var indemnityDuty = fm.indemnityDuty.value;
		var kindCode;
		var riskCode = fm.prpLcompensateRiskCode.value;
		var registNo = fm.prpLregistExtRegistNo.value;
		var flagn = "0";
		for (i = 1; i < fm.all(fieldname).length; i++) {
			if (fm.all(fieldname)[i] == Field) {
				findex = i;
				break;
			}
		}
		//============给变量赋值
		if (fm.all("prpLpersonLossSumLoss")[findex] == undefined) {} else {
			SumLoss = parseFloat(fm.all("prpLpersonLossSumLoss")[findex].value);
			SumDefPay = parseFloat(fm.all("prpLpersonLossSumDefPay")[findex].value);
			ClaimRate = parseFloat(fm.all("prpLpersonLossClaimRate")[findex].value);
			CompelPay = parseFloat(fm.all("prpLpersonLossCompelPay")[findex].value);
		}
		SumRest = 0;
		DeductibleRate = 0;
		DutyRate = 0;
		ArrangeRate = 0;
		Realpay = 0;
		temp = 0;
		var findex1 = 0;
		for (i = 1; i < fm.all("prpLpersonLossIndemnityDutyRate").length; i++) {
			if (fm.all("personLossSerialNo")[findex] == undefined) {
				findex1 = i;
				break;
			} else {
				if (fm.all("prpLpersonLossSerialNo")[i].value == fm.all("personLossSerialNo")[findex].value) {
					findex1 = i;
					break;
				}
			}
		}
		kindCode = fm.all("prpLpersonLossKindCode")[findex1].value;
		DutyRate = parseFloat(fm.all("prpLcompensateIndemnityDutyRate").value);
		ArrangeRate = parseFloat(fm.all("prpLpersonLossArrangeRate")[findex1].value);
		DeductibleRate = parseFloat(fm.all("prpLpersonLossDeductibleRate")[findex1].value);
		DutyDeductibleRate = parseFloat(fm.all("prpLpersonLossDutyDeductibleRate")[findex1].value);
		MainKindDeductibleRate = parseFloat(fm.all("prpLpersonLossMainKindDeductibleRate")[findex1].value);
		var kindCodeTemp = kindCode;
		if (kindCode == 'Y' || kindCode == 'S') //附加出境保险和教练车是有人伤的
		{
			kindCode = RISKINFO.KINDCODE_D_B;
		}
		if (isNaN(SumLoss)) SumLoss = 0; //
		if (isNaN(SumDefPay)) SumDefPay = 0; //LYM 20060620	  
		if (isNaN(CompelPay))
			CompelPay = 0;
		if (isNaN(DeductibleRate))
			DeductibleRate = 0;
		if (isNaN(DutyDeductibleRate))
			DutyDeductibleRate = 0;
		if (isNaN(MainKindDeductibleRate))
			MainKindDeductibleRate = 0;
		else
			MainKindDeductibleRate = MainKindDeductibleRate / 100;
		if (isNaN(DutyRate))
			DutyRate = 0;
		if (isNaN(ArrangeRate))
			ArrangeRate = '100';
		if (isNaN(ClaimRate))
			ClaimRate = '100';
		var size = parseInt(fm.size.value);
		if (kindCode != ConstantCodes.KINDCODE_D_BZ) {
			for (var index = 0; index < size; index++) {
				if (fm.prpLlossDtoKindCodeShow[index].value == kindCode) {
					flagn = fm.flag[index].value;
					Amount = fm.kindAmount[index].value;
					break;
				}
			}
			if (kindCode != kindCodeTemp) {
				for (var index = 0; index < fm.prpLlossDtoKindCodeShow.length; index++) {
					if (fm.prpLlossDtoKindCodeShow[index].value == kindCodeTemp) {
						flagn = fm.flag[index].value;
						break;
					}
				}
			}
		}
		var inputArgs = {
			SumLoss: SumLoss,
			SumRest: SumRest,
			SumDefPay: SumDefPay,
			DutyRate: DutyRate,
			ArrangeRate: ArrangeRate,
			DutyDeductibleRate: DutyDeductibleRate,
			DeductibleRate: DeductibleRate,
			Amount: Amount,
			riskCode1: riskCode,
			kindCode: kindCode,
			indemnityDuty: indemnityDuty,
			registNo: registNo,
			flagn: flagn,
			CompelPay: CompelPay
		};
		var param = DWRUtil.getValues(inputArgs);
		DWREngine.setAsync(false);
		dwrInvokeData("getPrpLpersonLossDto", param, "rollbackPrpLpersonLossDto", inputObject, outputObject);
		DWREngine.setAsync(true);
	}
}

function rollbackPrpLpersonLossDto(inputObject, outputObject, returnObject) {
	var prpLpersonLossDto = returnObject;
	var fieldname = inputObject.name;
	var findex;
	for (i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == inputObject) {
			findex = i;
			break;
		}
	}
	var findex1 = 0;
	for (i = 1; i < fm.all("prpLpersonLossIndemnityDutyRate").length; i++) {
		if (fm.all("personLossSerialNo")[findex] == undefined) {
			findex1 = i;
			break;
		} else {
			if (fm.all("prpLpersonLossSerialNo")[i].value == fm.all("personLossSerialNo")[findex].value) {
				findex1 = i;
				break;
			}
		}
	}

	fm.prpLpersonLossDutyDeductibleRate[findex1].value = prpLpersonLossDto.dutyDeductibleRate;
	fm.prpLpersonLossDeductibleRate[findex1].value = prpLpersonLossDto.deductiblerate;

	if (fm.prpLpersonLossSumRealPay[findex] == undefined) {} else {
		fm.prpLpersonLossSumRealPay[findex].value = point(round(prpLpersonLossDto.sumRealPay, 0), 0);
		fm.prpLpersonLossFlag[findex].value = prpLpersonLossDto.flag;
	}
	for (var index = 0; index < fm.prpLlossDtoKindCodeShow.length; index++) {
		if (fm.prpLlossDtoKindCodeShow[index].value == fm.all("prpLpersonLossKindCode")[findex1].value) {
			if (fm.flag[index].value == '1') {
				fm.prpLpersonLossExceptDeductibleRate1[findex1].value = prpLpersonLossDto.exceptDeductibleRate;
				fm.prpLpersonLossExceptDeductiblePay[findex].value = prpLpersonLossDto.exceptDeductiblePay;
				fm.prpLpersonLossExceptDeductibleRate[findex].value = prpLpersonLossDto.exceptDeductibleRate;
				initExceptDeductible();
				break;
			}
		}
	}
	calSumRealpay(inputObject);
	initEvryTypeRealPay();
	if (deepth1 != -1 || deepth2 != -1) {
		deepth22++;
		if (deepth11 == deepth1 && deepth22 == deepth2) {
			checkAmountn();
			showMessage5();
			deepth11 = -1;
			deepth1 = -1;
			deepth22 = -1;
			deepth2 = -1;
		}
	}
	//计算赔付人员中的赔付合计     
	calFund();
}

function displayTimes(obj) {
	var timesArray = document.getElementsByName("Times");
	var deductConditionArray = document.getElementsByName("deductCondition");
	var findex;
	for (var i = 0; i < fm.all(obj.name).length; i++) {
		if (fm.all(obj.name)[i] == obj) {
			findex = i;
		} else {
			//如果当前行是3个责任免赔之一，需要使另外两个为0的。
			var othervalue = fm.all(obj.name)[i].value;
			if ((obj.value == '110' || obj.value == '120' || obj.value == '121') && (othervalue == '110' || othervalue == '120' || othervalue == '121')) {
				timesArray[i].value = "0";
				fm.all(obj.name)[i].checked = false;
			}
		}
	}
	if (obj.checked == true) {
		timesArray[findex].value = "1";
	} else {
		timesArray[findex].value = "0";
	}
	var timeflag = document.getElementsByName("timesFlag");
	if (obj.checked == true && timeflag[findex].value == "1") {
		timesArray[findex].style.display = "";
	} else {
		timesArray[findex].style.display = "none";
	}
}


//车险理算计算费用

function calChargeAmount(field) {

	var outputObject;
	var inputObject = field;
	var serialNo = 0;
	var exceptDeductiblePay = 0;
	var exceptDeductibleRate = 0;
	for (var i = 0; i < fm.all(field.name).length; i++) {
		if (fm.all(field.name)[i] == field) {
			serialNo = i;
			break;
		}
	}
	var chargeReport = fm.all('prpLchargeChargeReport')[serialNo].value; //费用金额
	var chargeAmount = 0.0;
	var kindCode = fm.all('prpLchargeKindCode')[serialNo].value; //险别
	var chargeCode = fm.all('prpLchargeChargeCode')[serialNo].value; //费用类型
	if ((kindCode == 'A' || kindCode == 'Z' || kindCode == 'X1' || kindCode == 'K1' || kindCode == 'K2' || kindCode == 'Y' || kindCode == 'S' || kindCode == 'E') && chargeCode == '03') {

		var claimRate;
		var deductibleRate;
		var dutyDeductibleRate;
		var indemnityDutyRate;
		for (var index1 = 0; index1 < fm.prpLlossDtoSerialNo.length; index1++) {
			if (kindCode == fm.prpLlossDtoKindCode[index1].value) {
				claimRate = fm.prpLlossDtoClaimRate[index1].value;
				dutyDeductibleRate = fm.prpLlossDtoDutyDeductibleRate[index1].value;
				indemnityDutyRate = fm.prpLlossDtoIndemnityDutyRate[index1].value;
				deductibleRate = fm.prpLlossDtoDeductibleRate[index1].value;
				exceptDeductibleRate = parseInt(fm.prpLlossDtoExceptDeductibleRate[index1].value);
				break;
			}
		}
		if (kindCode == 'Z' || kindCode == 'X1' || kindCode == 'K1' || kindCode == 'K2' || kindCode == 'E')
			indemnityDutyRate = 100;
		chargeAmount = chargeReport * parseInt(claimRate) / 100 * parseInt(indemnityDutyRate) / 100 * (1 - (parseInt(dutyDeductibleRate) / 100 + parseInt(deductibleRate) / 100));
		if (kindCode == 'E')
			chargeAmount = chargeReport * (1 - parseInt(deductibleRate) / 100);
		fm.all("prpLchargeSumRealPay")[serialNo].value = point(round(chargeAmount, 0), 0);
		fm.all("prpLchargeChargeAmount")[serialNo].value = point(round(chargeAmount, 0), 0);
		if (exceptDeductibleRate != 0) //说明已经买了不计免赔
		{
			exceptDeductiblePay = chargeReport * parseInt(claimRate) / 100 * parseInt(indemnityDutyRate) / 100 * parseInt(exceptDeductibleRate) / 100;
			fm.all("prpLchargeExceptDeductiblePay")[serialNo].value = point(round(exceptDeductiblePay, 0), 0);
			fm.all("prpLchargeExceptDeductibleRate")[serialNo].value = exceptDeductibleRate;
			initExceptDeductible();
		}
		initEvryTypeRealPay();
	} else {
		fm.all("prpLchargeChargeAmount")[serialNo].value = point(round(chargeReport, 0), 0);
		fm.all("prpLchargeSumRealPay")[serialNo].value = 0.00;
		fm.all("prpLchargeExceptDeductiblePay")[serialNo].value = 0.00;
		fm.all("prpLchargeExceptDeductibleRate")[serialNo].value = 0;
		initExceptDeductible();
	}
	calFund();
	calLoss();
	undisablebutton();
}

function checkExceptDeductible(kind, field) {
	var policyno = fm.policyNo.value;
	if (policyno != null || policyno != "") {
		var inputArgs = {
			kind: kind,
			policyno: policyno
		};
		var inputObject = field;
		var outputObject;
		var param = DWRUtil.getValues(inputArgs);
		DWREngine.setAsync(false);
		dwrInvokeData("checkExceptDeductible", param, "rollbackExceptDeductible1", inputObject, outputObject);
		DWREngine.setAsync(true);
	}
}

function rollbackExceptDeductible1(inputObject, outputObject, returnObject) {
	var prpCitemKindDto = returnObject;
	if (prpCitemKindDto.kindCode != "") {

		insertRow('exceptLoss1');
		var index = fm.exceptDeductibleKindCode.length;
		fm.exceptDeductibleKindCode[index - 1].value = prpCitemKindDto.kindCode;
		fm.exceptDeductibleKindName[index - 1].value = prpCitemKindDto.kindName;
		fm.exceptDeductiblePay[index - 1].value = 0;
	}
	undisablebutton();
}

function getAllDeductibleRate(field) {
	var count = getElementCount("deductName");
	var deductCondArray = new Array();
	//count 为当前获取的所有的免赔条件的数目
	for (var j = 0; j < count; j++) {

		if (fm.deductCondition[j].checked == true) {
			if (fm.all("Times")[j].value == 0 || fm.all("Times")[j].value == '') {
				fm.all("Times")[j].value = 1;
			}
		} else {
			fm.all("Times")[j].value = 0;
		}
		//如果免赔被选中的话，Times=1，没有被选中的话 Times=0；
		//组织deductCondArray的数组，作为後面进行判断逻辑的基础数据
		if (fm.all("prpLlossDtoIndemnityDutyRate").length == undefined && fm.all("prpLpersonLossIndemnityDutyRate").length == undefined) {} else {
			fm.all("deductCondition")[j].disabled = true;
		}
		deductCondArray[j] = ({
			id: {
				deductCondCode: fm.all("deductConditionTemp")[j].value
			},
			deductCondName: fm.all("deductName")[j].value,
			times: parseInt(fm.all("Times")[j].value)
		});
	}

	//将组织好的数组条件，传入到 dwrInvokeDataAction中进行整理prpLdeductCondList变量
	DWREngine.setAsync(false);
	dwrInvokeDataAction.getDeductCondList(deductCondArray);
	DWREngine.setAsync(true);
	var findex;
	var fieldname = field.name;
	for (var i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == field) {
			findex = i;
			break;
		}
	}
	var kindCode = fm.prpLpersonLossKindCode[findex].value;
	var indemnityDuty = fm.indemnityDuty.value;
	var riskCode = fm.prpLcompensateRiskCode.value;
	var registNo = fm.prpLregistExtRegistNo.value;
	var kindCodeTemp = kindCode;
	var Amount;
	var flag;
	if (kindCode == 'Y' || kindCode == 'S') //附加出境保险和教练车是有人伤的
	{
		kindCode = RISKINFO.KINDCODE_D_B;
	}
	if (kindCode != ConstantCodes.KINDCODE_D_BZ) {
		for (var index = 0; index < fm.prpLlossDtoKindCodeShow.length; index++) {
			if (fm.prpLlossDtoKindCodeShow[index].value == kindCode) {
				Amount = fm.all("kindAmount")[index].value;
				flag = fm.flag[index].value;
				break;
			}
		}
		if (kindCode != kindCodeTemp) {
			for (var index = 0; index < fm.prpLlossDtoKindCodeShow.length; index++) {
				if (fm.prpLlossDtoKindCodeShow[index].value == kindCodeTemp && ((parseInt(fm.all("kindAmount")[index].value) > Amount) || (parseInt(fm.all("kindAmount")[index].value) == 0))) {
					fm.all("kindAmount")[index].value = Amount;
					break;
				}
			}
		}
	}
	if (kindCode == 'R') {
		fm.prpLpersonLossDutyDeductibleRate[findex].value = 0;
		fm.prpLpersonLossDeductibleRate[findex].value = 20;
		if (flag == '1') {
			fm.prpLpersonLossExceptDeductibleRate1[findex].value = 20;
		}
		return true;
	}
	if (isNaN(indemnityDuty))
		indemnityDuty = 0;
	var outputObject;
	var inputObject = field;
	if (kindCode != "" && registNo != "" && riskCode != "") {
		var inputArgs = {
			kindCode: kindCode,
			indemnityDuty: indemnityDuty,
			riskCode1: riskCode,
			registNo: registNo
		};
		var param = DWRUtil.getValues(inputArgs);
		DWREngine.setAsync(false);
		dwrInvokeData("getAllDeductibleRate", param, "rollbackAllDeductibleRate", inputObject, outputObject);
		DWREngine.setAsync(true);
	}
}

function rollbackAllDeductibleRate(inputObject, outputObject, returnObject) {
	var fieldname = inputObject.name;
	var findex = 0;
	for (var i = 1; i < fm.all(fieldname).length; i++) {
		if (fm.all(fieldname)[i] == inputObject) {
			findex = i;
			break;
		}
	}
	var exceptDeductibleRateDto = returnObject;
	fm.prpLpersonLossDutyDeductibleRate[findex].value = exceptDeductibleRateDto.afterDeductibleRate;
	fm.prpLpersonLossDeductibleRate[findex].value = exceptDeductibleRateDto.deductibleRate;
	fm.prpLpersonLossExceptDeductibleRate1[findex].value = exceptDeductibleRateDto.exceptDeductibleRatePay;
}
//结案信息回写

function getInfoFromPlatForm() {
	var comCode = fm.comCode.value;
	var nullReportNo = fm.registNo.value;
	var message = '';
	if (nullReportNo == '' || nullReportNo == null) message += i18n.quickCase.reportNotPickValue + "\n"; //报案号没有取到值！\n
	if (comCode == '' || comCode == null) message += i18n.quickCase.agencyNotValue; //机构号没有取到值！\n
	if (message != '') {
		alert(message);
		return false;
	}
	var inputObject;
	var outputObject;
	var inputArgs = {
		comCode1: comCode,
		nullReportNo1: nullReportNo
	};
	var param = DWRUtil.getValues(inputArgs);
	DWREngine.setAsync(false);
	dwrInvokeData("getEndCaseResponseReturn", param, "rollbackEndCaseResponseReturn", inputObject, outputObject);
	DWREngine.setAsync(true);
}
//结案信息回写

function rollbackEndCaseResponseReturn(inputObject, outputObject, returnObject) {
	var returnInfo = returnObject;
	var message = "";
	if (returnInfo.returnType == '1') //查询信息成功
	{
		fm.checkType.value = 'D';
		fm.prpLcheckChecker1.value = '';
		var m = '';
		if (fm.buttonRepairFee.length != undefined && fm.buttonRepairFee.length == 3) //说明有两辆车辆信息
		{
			for (var index = 1; index < fm.prpLcarLossInsureCarFlag.length; index++) {
				if (fm.prpLcarLossInsureCarFlag[index].value != '1')
					m = index;
			}
			if (m != '') {
				if (fm.prpLRepairFeeSerialNo.length == undefined) {
					insertRowTableOfCertainLossRepair('RepairFee', 'RepairFee_Data', fm.buttonRepairFee[m]);
					fm.prpLrepairFeeKindCode[1].value = ConstantCodes.KINDCODE_D_BZ;
					fm.prpLrepairFeeKindName[1].value = '机动车交通事故责任强制险';
					fm.prpLrepairFeeSumDefLoss[1].value = returnInfo.endCaseResponseReturnDto.estimatedAmount;
					fm.prpLrepairFeeManHourUnitPrice[1].value = returnInfo.endCaseResponseReturnDto.estimatedAmount;
					fm.prpLrepairFeeManHour[1].value = '1';
				} else
					alert(i18n.quickCase.pleaseDeletePageLossFee + "\n"); //请删除定损损失页面的其余的损失信息再获取平台信息！\n
			}
		} else if (fm.buttonRepairFee.length != undefined && fm.buttonRepairFee.length == 2) //说明没有三者车的信息
		{
			insertRowTableOfCertainLossCar('CertainLossCar', 'CertainLossCar_Data', fm.buttonCertainLossCarInsert);
			insertRowTableOfCertainLossRepair('RepairFee', 'RepairFee_Data', fm.buttonRepairFee[2]);
			fm.prpLrepairFeeKindCode[1].value = ConstantCodes.KINDCODE_D_BZ;
			fm.prpLrepairFeeKindName[1].value = '机动车交通事故责任强制险';
			fm.prpLrepairFeeSumDefLoss[1].value = returnInfo.endCaseResponseReturnDto.estimatedAmount;
			fm.prpLrepairFeeManHourUnitPrice[1].value = returnInfo.endCaseResponseReturnDto.estimatedAmount;
			fm.prpLrepairFeeManHour[1].value = '1';
		}
		//理算信息
		if (fm.prpLlossDtoKindCode.length == undefined) {
			insertRow('CompensateLoss');
			fm.prpLlossDtoKindCode[1].value = ConstantCodes.KINDCODE_D_BZ;
			fm.prpLlossDtoKindName[1].value = '机动车交通事故责任强制险';
			fm.licenseNo[1].value = returnInfo.endCaseResponseReturnDto.fullCarMark;
			fm.prpLlossDtoItemKindNo[1].value = '0';
			fm.prpLlossDtoLossName[1].value = '车辆';
			fm.prpLlossDtoFeeTypeName[1].value = '修理费';
			fm.prpLlossDtoFeeTypeCode[1].value = '01';
			fm.prpLlossDtoSumLoss[1].value = returnInfo.endCaseResponseReturnDto.estimatedAmount;
			fm.prpLlossDtoSumDefPay[1].value = returnInfo.endCaseResponseReturnDto.settlementAmount;
			fm.prpLlossDtoSumRest[1].value = 0;
			fm.prpLlossDtoSumRealPay[1].value = returnInfo.endCaseResponseReturnDto.settlementAmount;
			calFund();
		} else {
			for (var i = 1; i < fm.prpLlossDtoKindCode.length; i++) {
				deleteRow(fm.prpLlossDtoKindCode[fm.prpLlossDtoKindCode.length - 1], 'CompensateLoss');
			}
			insertRow('CompensateLoss');
			fm.prpLlossDtoKindCode[1].value = ConstantCodes.KINDCODE_D_BZ;
			fm.prpLlossDtoKindName[1].value = '机动车交通事故责任强制险';
			fm.licenseNo[1].value = returnInfo.endCaseResponseReturnDto.nullCarMark;
			fm.prpLlossDtoItemKindNo[1].value = '0';
			fm.prpLlossDtoLossName[1].value = '车辆';
			fm.prpLlossDtoFeeTypeName[1].value = '修理费';
			fm.prpLlossDtoFeeTypeCode[1].value = '01';
			fm.prpLlossDtoSumLoss[1].value = returnInfo.endCaseResponseReturnDto.estimatedAmount;
			fm.prpLlossDtoSumDefPay[1].value = returnInfo.endCaseResponseReturnDto.settlementAmount;
			fm.prpLlossDtoSumRest[1].value = 0;
			fm.prpLlossDtoSumRealPay[1].value = returnInfo.endCaseResponseReturnDto.settlementAmount;
			calFund();
		}
		fm.advanceCaseStatus.value = '20';
	} else
		alert(returnInfo.errorMessage);
}
//上传事故信息,上传影像资料信息

function uploadToPlatForm(uploadType) {
	var inputObject = uploadType;
	var outputObject;
	var registNo = fm.registNo.value;
	var comCode = fm.comCode.value;
	fm.displayUpload.value = '事故信息上传中...';

	var inputArgs = {
		uploadType1: uploadType,
		registNo1: registNo,
		comCode1: comCode
	};
	var param = DWRUtil.getValues(inputArgs);
	DWREngine.setAsync(false);
	dwrInvokeData("getPrpLagentDto", param, "rollbackPrpLagentDto", inputObject, outputObject);
	DWREngine.setAsync(true);
}

function rollbackPrpLagentDto(inputObject, outputObject, returnObject) {
	var returnInfo = returnObject;
	var uploadType = inputObject;
	if (uploadType == 'DA') {
		if (!window.confirm(returnInfo.errorMessage)) //给操作员反馈信息
		{
			fm.displayUpload.style.display = "";
			fm.displayUploadImage.style.display = "none";
			fm.displayGetConfirm.style.display = "none";
			fm.advanceCaseStatus.value = "05";
		}
		fm.displayUploadImage.value = '上传影像信息';
	} else if (uploadType == 'D5') {
		if (returnInfo.returnType == '1') {
			fm.advanceCaseStatus.value = "10";
			fm.displayUpload.style.display = "none";
			fm.displayGetConfirm.style.display = "";
		} else {
			alert(returnInfo.errorMessage);
		}
		fm.displayUpload.value = '上传事故信息';
	}
}
//全责方获取确认信息

function getNullConfirm() {
	var inputObject;
	var outputObject;
	var registNo = fm.registNo.value;
	var comCode = fm.comCode.value;
	var inputArgs = {
		registNo1: registNo,
		comCode1: comCode
	};
	var param = DWRUtil.getValues(inputArgs);
	DWREngine.setAsync(false);
	dwrInvokeData("nullConfirmInfo", param, "rollbackGetNullConfirm", inputObject, outputObject);
	DWREngine.setAsync(true);
}

function rollbackGetNullConfirm(inputObject, outputObject, returnObject) {
	var returnInfo = returnObject;
	if (returnInfo.returnType == 1 && returnInfo.advanceResponseReturnDto.nullComments == '') {
		alert(i18n.compel.noResponsiblePartyThrough) //无责方已经确认通过！
		fm.advanceCaseStatus.value = "20";
		fm.displayUpload.style.display = "none";
		fm.displayGetConfirm.style.display = "";
		fm.displayGetConfirm.disabled = true;
	} else {
		alert(returnInfo.errorMessage);
	}
}